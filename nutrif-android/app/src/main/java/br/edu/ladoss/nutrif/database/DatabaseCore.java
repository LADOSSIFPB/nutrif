package br.edu.ladoss.nutrif.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.database.dao.AlunoDAO;
import br.edu.ladoss.nutrif.database.dao.DiaRefeicaoDAO;
import br.edu.ladoss.nutrif.database.dao.PretensaoRefeicaoDAO;

/**
 * Created by juan on 14/03/16.
 */
public class DatabaseCore extends SQLiteOpenHelper {
    private static final int VERSION = 4;

    public DatabaseCore(Context context) {
        super(context, context.getString(R.string.app_name), null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("Nutrif"," create database");
        db.execSQL(AlunoDAO.CREATE_TABLE);
        db.execSQL(DiaRefeicaoDAO.CREATE_TABLE);
        db.execSQL(PretensaoRefeicaoDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DiaRefeicaoDAO.DROP_TABLE);
        db.execSQL(AlunoDAO.DROP_TABLE);
        onCreate(db);
    }
}
