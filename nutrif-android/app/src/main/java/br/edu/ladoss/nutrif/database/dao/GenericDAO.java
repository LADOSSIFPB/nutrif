package br.edu.ladoss.nutrif.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.edu.ladoss.nutrif.database.DatabaseCore;

/**
 * Created by juan on 15/03/16.
 */
public abstract class GenericDAO<T> {
    protected SQLiteDatabase db;

    public GenericDAO(Context context){
        db = new DatabaseCore(context).getWritableDatabase();
    }

    protected void insert(String table, ContentValues values){
        db.insert(table, null, values);
    }
}
