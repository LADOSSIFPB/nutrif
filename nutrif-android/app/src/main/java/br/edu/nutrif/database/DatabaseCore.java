package br.edu.nutrif.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.edu.nutrif.R;
import br.edu.nutrif.database.dao.AlunoDAO;
import br.edu.nutrif.database.dao.DiaRefeicaoDAO;

/**
 * Created by juan on 14/03/16.
 */
public class DatabaseCore extends SQLiteOpenHelper {
    private static final int VERSION = 3;

    public DatabaseCore(Context context) {
        super(context, context.getString(R.string.app_name), null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("Nutrif"," create database");
        db.execSQL(AlunoDAO.CREATE_TABLE);
        db.execSQL(DiaRefeicaoDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("drop table "+ AlunoDAO.ALUNO_TABLE +" ;");
        db.execSQL("drop table tb_pessoa ;");
        onCreate(db);
    }
}
