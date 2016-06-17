package br.ifsp.btv.ads.pdmde16.pictag;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String NOME_BANCO ="pictag.db";
    public static final Integer VERSAO_BANCO = 1;

    public static final String CREATE_TABLE_FOTO =
            "CREATE TABLE FOTO (" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "caminho VARCHAR(100) NOT NULL);";

    public static final String DROP_TABLE_FOTO =
            "DROP TABLE IF EXISTS FOTO;";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FOTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_FOTO);
        db.execSQL(CREATE_TABLE_FOTO);
    }
}
