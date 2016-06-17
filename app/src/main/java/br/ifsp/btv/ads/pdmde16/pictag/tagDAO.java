package br.ifsp.btv.ads.pdmde16.pictag;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by aluno on 16/06/16.
 */
public class tagDAO {
    private SQLiteDatabase  dbHelper;

    public tagDAO(Context contexto)
    {
        dbHelper = new DBHelper(contexto).getWritableDatabase();
    }
}
