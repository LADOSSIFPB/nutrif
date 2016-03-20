package br.edu.nutrif.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import br.edu.nutrif.entitys.Aluno;

/**
 * Created by juan on 15/03/16.
 */
public class AlunoDAO extends GenericDAO {
    public static final String ALUNO_TABLE = "tb_aluno";
    public static final String CREATE_TABLE = "create table IF NOT EXISTS " + ALUNO_TABLE + " (" +
            "_matricula text primary key unique, " +
            "email text not null, " +
            "senha text not null);";

    public AlunoDAO(Context context) {
        super(context);
    }

    public static AlunoDAO getInstance(Context context){
        return new AlunoDAO(context);
    }

    public void insertAluno(Aluno pessoa) {
        ContentValues values = new ContentValues();
        values.put("_matricula", pessoa.getMatricula());
        values.put("email", pessoa.getEmail());
        values.put("senha", pessoa.getSenha());
        insert(ALUNO_TABLE, values);
    }

    public  Aluno find() {
        String[] colums = new String[]{"_matricula", "email", "senha"};
        Cursor cursor = db.query(ALUNO_TABLE, colums, null, null, null, null, "_matricula");

        for (int i = cursor.getCount(); i != 0; i--) {
            cursor.moveToFirst();
            Aluno u = new Aluno();
            u.setMatricula(cursor.getString(0));
            u.setEmail(cursor.getString(1));
            u.setSenha(cursor.getString(2));
            return u;
        }

        return null;
    }

    public void delete() {
        db.delete(ALUNO_TABLE, null, null);
    }
}
