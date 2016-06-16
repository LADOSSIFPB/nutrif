package br.edu.ladoss.nutrif.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.edu.ladoss.nutrif.database.DatabaseCore;

/**
 * Created by juan on 15/03/16.
 */
public abstract class GenericDAO<T> {
    protected SQLiteDatabase db;
    protected Context context;

    public GenericDAO(Context context){
        db = new DatabaseCore(context).getWritableDatabase();
        this.context = context;
    }

    protected void insert(String table, ContentValues values){
        if(db.insert(table, null, values)==-1)
            Log.i("Nutrif","Ocorreu um erro");
    }
}
