package br.edu.nutrif.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.edu.nutrif.R;

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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
