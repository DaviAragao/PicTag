package br.ifsp.btv.ads.pdmde16.pictag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by aluno on 16/06/16.
 */
public class TagDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public TagDAO(Context contexto) {
        dbHelper = new DBHelper(contexto);
    }

    public void createTag(String nome) {
        ContentValues tag = new ContentValues();
        db = dbHelper.getWritableDatabase();

        tag.put("nome", nome);

        db.insert("TAG", null, tag);
        db.close();
    }

    public List<String> getAll(){
        db = dbHelper.getReadableDatabase();
        String select = "SELECT nome FROM TAG;";
        Cursor cursor = db.rawQuery(select, null);

        List<String> tags = new ArrayList<String>();

        while (cursor.moveToNext())
            tags.add(cursor.getString(cursor.getColumnIndex("nome")));

        cursor.close();
        db.close();
        return tags;
    }


}

