package br.ifsp.btv.ads.pdmde16.pictag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public void createPic(String caminho, SQLiteDatabase db) {
        ContentValues tag = new ContentValues();

        tag.put("caminho", caminho);

        db.insert("FOTO", null, tag);
    }

    public void createPicTag(String caminhoPic, List<String> lstLags, SQLiteDatabase db){
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;

        for (i = 0; i < lstLags.size(); i++)
            stringBuilder.append("? ,");

        String params = stringBuilder.toString();
        params = params.substring(0, params.length()-2);

        SQLiteStatement stmt = db.compileStatement("INSERT INTO FOTO_TAG (id_foto, id_tag) SELECT f.id, t.id FROM FOTO f, TAG t WHERE f.caminho = ? AND t.nome in ("+params+");");

        stmt.bindString(1, caminhoPic);

        i = 1;
        for (String tag: lstLags) {
            i++;
            stmt.bindString(i, tag);
        }

        stmt.executeInsert();
    }

    public void createTags(List<String> tags, SQLiteDatabase db) {
        ContentValues cvTag = new ContentValues();

        for (String tag: tags){
            cvTag.put("nome", tag);
            db.insert("TAG", null, cvTag);
        }
    }

    public void createCompletePicTag(String caminhoPic, List<String> lstLags){
        db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        createTags(lstLags, db);
        createPic(caminhoPic, db);
        createPicTag(caminhoPic, lstLags, db);

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public Map<String, String> getPicTagsByTagName(String tag){
        db = dbHelper.getReadableDatabase();

        Map<String, String> mapa = new LinkedHashMap<>();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                    "f.caminho, GROUP_CONCAT(t.nome) as tags " +
                "FROM " +
                    "FOTO f " +
                "JOIN " +
                    "FOTO_TAG ft ON ft.id_foto = f.id " +
                "JOIN " +
                    "TAG t ON t.id = ft.id_tag " +
                "WHERE " +
                    "f.id IN " +
                        "(SELECT " +
                            "tf.id_foto " +
                        "FROM " +
                            "TAG t " +
                        "JOIN " +
                            "FOTO_TAG tf ON t.id = tf.id_tag " +
                        "WHERE " +
                            "t.nome = ?)" +
                "GROUP BY " +
                    "f.id, f.caminho", new String[]{tag});

        String caminho, tagfoto;

        //cursor.moveToFirst();
        while (cursor.moveToNext()) {
            caminho = cursor.getString(cursor.getColumnIndex("caminho"));
            tagfoto = cursor.getString(cursor.getColumnIndex("tags"));

            Log.i("TAG_TESTE", caminho);
            Log.i("TAG_TESTE", tagfoto);

            mapa.put(caminho, tagfoto);
        }

        return mapa;
    }

}

