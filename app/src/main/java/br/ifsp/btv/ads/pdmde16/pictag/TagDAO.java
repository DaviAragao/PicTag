package br.ifsp.btv.ads.pdmde16.pictag;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by aluno on 16/06/16.
 */
public class TagDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public TagDAO(Context contexto)
    {
        dbHelper = new DBHelper(contexto);
    }

    public void createTag(String nome)
    {
        ContentValues tag = new ContentValues();
        db = dbHelper.getWritableDatabase();

        tag.put("nome", nome);

        db.insert("TAG", null, tag);
        db.close();
    }

}

