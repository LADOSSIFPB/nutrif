package br.edu.ladoss.nutrif.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;

import br.edu.ladoss.nutrif.model.Aluno;
import br.edu.ladoss.nutrif.util.ImageUtils;
import br.edu.ladoss.nutrif.util.StorageUtil;


/**
 * Created by juan on 15/03/16.
 */
public class AlunoDAO extends GenericDAO {
    public static final String ALUNO_TABLE = "tb_aluno";
    public static final String CREATE_TABLE = "create table IF NOT EXISTS " + ALUNO_TABLE + " (" +
            "_email text primary key unique, " +
            "matricula text not null," +
            "id integer not null," +
            "photo text ," +
            "nome text ," +
            "senha text not null);";
    public static final java.lang.String DROP_TABLE = "drop table " + ALUNO_TABLE + ";";

    public AlunoDAO(Context context) {
        super(context);
    }

    public static AlunoDAO getInstance(Context context) {
        return new AlunoDAO(context);
    }

    public void updatePhoto(Aluno aluno, Bitmap bitmap) {
        String adress = null;
        try {
            adress = StorageUtil.saveToInternalStorage(bitmap, context);
        } catch (Exception e) {
            Log.w("AlunoDao", "Não foi possível salvar a foto");
            return;
        }
        ContentValues values = new ContentValues();
        values.put("photo", adress);
        db.update(ALUNO_TABLE, values, "_email = '" + aluno.getEmail() + "';", null);
        Log.i(aluno.getEmail(), " atualizado");

    }

    public void insertAluno(Aluno pessoa) {
        ContentValues values = new ContentValues();
        values.put("matricula", pessoa.getMatricula());
        values.put("_email", pessoa.getEmail());
        values.put("senha", pessoa.getSenha());
        values.put("id", pessoa.getId());
        values.put("nome", pessoa.getNome());
        if (0 == db.update(ALUNO_TABLE, values, "_email = '" + pessoa.getEmail() + "';", null))
            db.insert(ALUNO_TABLE, null, values);
    }

    @Nullable
    public Aluno find() {
        String[] colums = new String[]{"_email", "senha", "matricula", "id", "nome"};
        Cursor cursor = db.query(ALUNO_TABLE, colums, null, null, null, null, "_email");

        for (int i = cursor.getCount(); i != 0; i--) {
            cursor.moveToFirst();
            Aluno u = new Aluno();
            u.setEmail(cursor.getString(0));
            u.setSenha(cursor.getString(1));
            u.setMatricula(cursor.getString(2));
            u.setId(cursor.getInt(3));
            u.setNome(cursor.getString(4));
            cursor.close();
            return u;
        }

        return null;
    }

    @Nullable
    public Aluno findWithPhoto() {
        String[] colums = new String[]{"_email", "senha", "matricula", "id", "nome", "photo"};
        Cursor cursor = db.query(ALUNO_TABLE, colums, null, null, null, null, "_email");

        for (int i = cursor.getCount(); i != 0; i--) {
            cursor.moveToFirst();
            Aluno u = new Aluno();
            u.setEmail(cursor.getString(0));
            u.setSenha(cursor.getString(1));
            u.setMatricula(cursor.getString(2));
            u.setId(cursor.getInt(3));
            u.setNome(cursor.getString(4));
            if (cursor.getString(5) != null) {
                File file = new File(cursor.getString(5));
                if (file.exists())
                    u.setPhoto(ImageUtils.convertFileToByte(file));
            }
            cursor.close();
            return u;
        }

        return null;
    }

    public void deleteAll() {
        db.delete(ALUNO_TABLE, null, null);
    }
}
