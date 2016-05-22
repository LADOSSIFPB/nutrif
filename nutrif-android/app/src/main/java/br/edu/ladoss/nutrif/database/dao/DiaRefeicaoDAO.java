package br.edu.ladoss.nutrif.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.edu.ladoss.nutrif.entitys.DiaRefeicao;

/**
 * Created by juan on 25/03/16.
 */
public class DiaRefeicaoDAO extends GenericDAO<DiaRefeicao> {
    public static final String DIAREFEICAO_TABLE = "tb_diarefeicao";
    public static final String CREATE_TABLE = "create table IF NOT EXISTS " + DIAREFEICAO_TABLE + "(" +
            "_id integer primary key unique, " +
            "tipo text not null, " +
            "diadasemana text not null, " +
            "hora_init text not null," +
            "hora_final text not null," +
            "_email text not null);";
    public static final String DROP_TABLE = "drop table " + DIAREFEICAO_TABLE + ";";

    public static DiaRefeicaoDAO getInstance(Context context) {
        return new DiaRefeicaoDAO(context);
    }

    public DiaRefeicaoDAO(Context context) {
        super(context);
    }

    public void insert(DiaRefeicao diaRefeicao) {
        ContentValues values = new ContentValues();
        values.put("_id", diaRefeicao.getId());
        values.put("tipo", diaRefeicao.getRefeicao().getTipo());
        values.put("diadasemana", diaRefeicao.getDia().getNome());
        values.put("hora_init", diaRefeicao.getRefeicao().getHoraInicio());
        values.put("hora_final", diaRefeicao.getRefeicao().getHoraFinal());
        values.put("_email", diaRefeicao.getAluno().getEmail());
        insert(DIAREFEICAO_TABLE, values);
    }

    public void delete(DiaRefeicao pretensaoRefeicao) {
        db.delete(DIAREFEICAO_TABLE, " _id ='"+pretensaoRefeicao.getId()+"';", null);
    }

    public void deleteAll(){
        db.delete(DIAREFEICAO_TABLE, null , null);
    }

    public void update(DiaRefeicao diaRefeicao) {
        ContentValues values = new ContentValues();
        values.put("_id", diaRefeicao.getId());
        values.put("tipo", diaRefeicao.getRefeicao().getTipo());
        values.put("diadasemana", diaRefeicao.getDia().getNome());
        values.put("hora_init", diaRefeicao.getRefeicao().getHoraInicio());
        values.put("hora_final", diaRefeicao.getRefeicao().getHoraFinal());
        db.update(DIAREFEICAO_TABLE, values, "_id = " + diaRefeicao.getId(), null);

    }

    public DiaRefeicao find(DiaRefeicao pretensaoRefeicao) {
        String[] colums = new String[]{"_id", "tipo", "diadasemana", "hora_init", "hora_final", "_email"};
        Cursor cursor = db.query(DIAREFEICAO_TABLE, colums, null, null, null, null, "_id");

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            DiaRefeicao pretensao = new DiaRefeicao();
            pretensao.setId(cursor.getInt(0));
            pretensao.getRefeicao().setTipo(cursor.getString(1));
            pretensao.getDia().setNome(cursor.getString(2));
            pretensao.getRefeicao().setHoraInicio(cursor.getString(3));
            pretensao.getRefeicao().setHoraFinal(cursor.getString(4));
            pretensao.getAluno().setEmail(cursor.getString(5));
            cursor.close();
            return pretensao;
        }
        cursor.close();
        return null;
    }

    public List<DiaRefeicao> findAll() {
        String[] colums = new String[]{"_id", "tipo", "diadasemana", "hora_init", "hora_final", "_email"};
        Cursor cursor = db.query(DIAREFEICAO_TABLE, colums, null, null, null, null, "_id");

        if (cursor.getCount() != 0) {
            List<DiaRefeicao> lista = new ArrayList<>();
            cursor.moveToFirst();
            do {
                DiaRefeicao pretensao = new DiaRefeicao();
                pretensao.setId(cursor.getInt(0));
                pretensao.getRefeicao().setTipo(cursor.getString(1));
                pretensao.getDia().setNome(cursor.getString(2));
                pretensao.getRefeicao().setHoraInicio(cursor.getString(3));
                pretensao.getRefeicao().setHoraFinal(cursor.getString(4));
                pretensao.getAluno().setEmail(cursor.getString(5));
                lista.add(pretensao);
            } while (cursor.moveToNext());
            cursor.close();
            return lista;
        }
        cursor.close();
        return null;

    }

}
