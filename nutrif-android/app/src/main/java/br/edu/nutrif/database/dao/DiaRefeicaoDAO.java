package br.edu.nutrif.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.edu.nutrif.entitys.Dia;
import br.edu.nutrif.entitys.DiaRefeicao;

/**
 * Created by juan on 25/03/16.
 */
public class DiaRefeicaoDAO extends GenericDAO<DiaRefeicao> {
    public static final String DIAREFEICAO_TABLE = "tb_diarefeicao";
    public static final String CREATE_TABLE = "create table IF NOT EXISTS " + DIAREFEICAO_TABLE + "(" +
            "_id integer primary key unique, " +
            "tipo text not null, " +
            "diadasemana text not null, " +
            "keyAccess text, " +
            "horarios text not null" +
            "_email text not null);";

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
        values.put("keyAccess", diaRefeicao.getCodigo());
        values.put("horarios", diaRefeicao.getRefeicao().getHoraInicio()
                .concat("/")
                .concat(diaRefeicao.getRefeicao().getHoraFinal()));
        values.put("_email", diaRefeicao.getAluno().getEmail());
        insert(DIAREFEICAO_TABLE, values);
    }

    public void delete(DiaRefeicao pretensaoRefeicao) {
        db.delete(DIAREFEICAO_TABLE, null, null);
    }

    public void update(DiaRefeicao diaRefeicao) {
        ContentValues values = new ContentValues();
        values.put("_id", diaRefeicao.getId());
        values.put("tipo", diaRefeicao.getRefeicao().getTipo());
        values.put("diadasemana", diaRefeicao.getDia().getNome());
        values.put("keyAccess", diaRefeicao.getCodigo());
        values.put("horarios", diaRefeicao.getRefeicao().getHoraInicio()
                .concat("/")
                .concat(diaRefeicao.getRefeicao().getHoraFinal()));
        db.update(DIAREFEICAO_TABLE, values, "_id = " + diaRefeicao.getId(), null);

    }

    public DiaRefeicao find(DiaRefeicao pretensaoRefeicao) {
        String[] colums = new String[]{"_id", "tipo", "diadasemana", "keyAccess", "horarios"};
        Cursor cursor = db.query(DIAREFEICAO_TABLE, colums, null, null, null, null, "_id");

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            DiaRefeicao pretensao = new DiaRefeicao();
            pretensao.setId(cursor.getInt(0));
            pretensao.getRefeicao().setTipo(cursor.getString(1));
            pretensao.getRefeicao().setHoraInicio(cursor.getString(4));
            pretensao.getRefeicao().setHoraFinal(cursor.getString(4));
            pretensao.setCodigo(cursor.getString(3));
            cursor.close();
            return pretensao;
        }
        cursor.close();
        return null;
    }

    public List<DiaRefeicao> findAll() {
        String[] colums = new String[]{"_id", "tipo", "diadasemana", "keyAccess", "horarios"};
        Cursor cursor = db.query(DIAREFEICAO_TABLE, colums, null, null, null, null, "_id");

        if (cursor.getCount() != 0) {
            List<DiaRefeicao> lista = new ArrayList<>();
            cursor.move(cursor.getCount() + 1);
            for (int i = cursor.getCount(); i != 0; i--) {

                cursor.moveToPrevious();

                DiaRefeicao pretensao = new DiaRefeicao();
                pretensao.setId(cursor.getInt(0));
                pretensao.getRefeicao().setTipo(cursor.getString(1));
                pretensao.getRefeicao().setHoraInicio(cursor.getString(4));
                pretensao.getRefeicao().setHoraFinal(cursor.getString(4));
                pretensao.setCodigo(cursor.getString(3));
                lista.add(pretensao);
            }
            cursor.close();
            return lista;
        }
        cursor.close();
        return null;

    }

}
