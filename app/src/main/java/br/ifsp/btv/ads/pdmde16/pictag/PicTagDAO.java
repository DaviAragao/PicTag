package br.ifsp.btv.ads.pdmde16.pictag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PicTagDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public PicTagDAO(Context contexto) {
        dbHelper = new DBHelper(contexto);
    }

    public void createTag(String nome) {
        ContentValues tag = new ContentValues();

        tag.put("nome", nome);

        db = dbHelper.getWritableDatabase();
        db.insert("TAG", null, tag);
        db.close();
    }

    public List<String> getAllTags(){
        db = dbHelper.getReadableDatabase();
        String select = "SELECT nome FROM TAG;";
        Cursor cursor = db.rawQuery(select, null);

        List<String> tags = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                tags.add(cursor.getString(cursor.getColumnIndex("nome")));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tags;
    }

    public String getTagById(int id){
        String tag = null;
        db = dbHelper.getReadableDatabase();
        String select = "SELECT nome FROM TAG where nome ='"+ id +"';";
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst())
            tag = cursor.getString(cursor.getColumnIndex("nome"));

        cursor.close();
        db.close();
        return tag;
    }

    public String getTagByName(String nome){
        String tag = null;
        db = dbHelper.getReadableDatabase();
        String select = "SELECT nome FROM TAG where nome ='"+ nome +"';";
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst())
            tag = cursor.getString(cursor.getColumnIndex("nome"));

        cursor.close();
        db.close();
        return tag;
    }

    public void createPic(String caminho) {
        ContentValues tag = new ContentValues();

        tag.put("caminho", caminho);

        db = dbHelper.getWritableDatabase();
        db.insert("TAG", null, tag);
        db.close();
    }

}

