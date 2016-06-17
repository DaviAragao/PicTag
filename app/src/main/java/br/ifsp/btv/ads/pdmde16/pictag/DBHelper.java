package br.ifsp.btv.ads.pdmde16.pictag;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "pictag.db";
    public static final Integer VERSAO_BANCO = 1;

    public static final String CREATE_TABLE_FOTO =
            "CREATE TABLE FOTO (" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "caminho VARCHAR(100) NOT NULL," +
                    "primary key(id));";

    public static final String DROP_TABLE_FOTO =
            "DROP TABLE IF EXISTS FOTO;";

    public static final String CREATE_TABLE_TAG =
            "CREATE TABLE TAG(" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "nome VARCHAR(100) NOT NULL, " +
                "primary key(id));";

    public static final String DROP_TABLE_TAG =
            "DROP TABLE IF EXISTS TAG;";

    public DBHelper(Context contexto)
    {
        super(contexto, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FOTO);
        db.execSQL(CREATE_TABLE_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_FOTO);
        db.execSQL(DROP_TABLE_TAG);
        onCreate(db);
    }
}
