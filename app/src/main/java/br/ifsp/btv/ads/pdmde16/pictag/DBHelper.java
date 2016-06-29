package br.ifsp.btv.ads.pdmde16.pictag;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "pictag.db";
    public static final Integer VERSAO_BANCO = 1;

    public static final String CREATE_TABLE_FOTO =
            "CREATE TABLE FOTO(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "caminho VARCHAR(100) NOT NULL UNIQUE ON CONFLICT IGNORE);";

    public static final String DROP_TABLE_FOTO =
            "DROP TABLE IF EXISTS FOTO;";

    public static final String CREATE_TABLE_TAG =
            "CREATE TABLE TAG(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome VARCHAR(100) NOT NULL UNIQUE ON CONFLICT IGNORE);";

    public static final String DROP_TABLE_TAG =
            "DROP TABLE IF EXISTS TAG;";

    public static final String CREATE_TABLE_FOTO_TAG =
            "CREATE TABLE FOTO_TAG(" +
                    "id_foto INTEGER, " +
                    "id_tag INTEGER," +
                    "PRIMARY KEY (id_foto, id_tag) ON CONFLICT IGNORE," +
                    "FOREIGN KEY(id_foto) REFERENCES FOTO(id)," +
                    "FOREIGN KEY(id_tag) REFERENCES TAG(id));";

    public static final String DROP_TABLE_FOTO_TAG =
            "DROP TABLE IF EXISTS FOTO_TAG;";

    public DBHelper(Context contexto)
    {
        super(contexto, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FOTO);
        db.execSQL(CREATE_TABLE_TAG);
        db.execSQL(CREATE_TABLE_FOTO_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_FOTO_TAG);
        db.execSQL(DROP_TABLE_FOTO);
        db.execSQL(DROP_TABLE_TAG);
        onCreate(db);
    }
}
