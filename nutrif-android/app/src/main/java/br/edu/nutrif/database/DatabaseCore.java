package br.edu.nutrif.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.edu.nutrif.R;
import br.edu.nutrif.database.dao.AlunoDAO;
import br.edu.nutrif.entitys.Pessoa;

/**
 * Created by juan on 14/03/16.
 */
public class DatabaseCore extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    public DatabaseCore(Context context) {
        super(context, context.getString(R.string.app_name), null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AlunoDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table "+ AlunoDAO.ALUNO_TABLE +" ;");
        onCreate(db);
    }
}
