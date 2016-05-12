package br.edu.ladoss.nutrif.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import br.edu.ladoss.nutrif.entitys.Aluno;

/**
 * Created by juan on 15/03/16.
 */
public class AlunoDAO extends GenericDAO {
    public static final String ALUNO_TABLE = "tb_aluno";
    public static final String CREATE_TABLE = "create table IF NOT EXISTS " + ALUNO_TABLE + " (" +
            "_email text primary key unique, " +
            "matricula text not null," +
            "id integer not null," +
            "photo text not null," +
            "nome text not null," +
            "senha text not null);";
    public static final java.lang.String DROP_TABLE = "drop table " + ALUNO_TABLE + ";";

    public AlunoDAO(Context context) {
        super(context);
    }

    public static AlunoDAO getInstance(Context context){
        return new AlunoDAO(context);
    }

    public void insertAluno(Aluno pessoa) {
        delete();
        ContentValues values = new ContentValues();
        values.put("matricula", pessoa.getMatricula());
        values.put("_email", pessoa.getEmail());
        values.put("senha", pessoa.getSenha());
        values.put("id", pessoa.getId());
        values.put("photo",pessoa.getPhoto());
        values.put("nome",pessoa.getNome());
        insert(ALUNO_TABLE, values);
    }

    public Aluno find() {
        String[] colums = new String[]{"_email", "senha", "matricula","id","photo", "nome"};
        Cursor cursor = db.query(ALUNO_TABLE, colums, null, null, null, null, "_email");

        for (int i = cursor.getCount(); i != 0; i--) {
            cursor.moveToLast();
            Aluno u = new Aluno();
            u.setEmail(cursor.getString(0));
            u.setSenha(cursor.getString(1));
            u.setMatricula(cursor.getString(2));
            u.setId(cursor.getInt(3));
            u.setPhoto(cursor.getString(4));
            u.setNome(cursor.getString(5));
            cursor.close();
            return u;
        }

        return null;
    }

    public void delete() {
        db.delete(ALUNO_TABLE, null, null);
    }
}
