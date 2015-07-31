package br.com.compracombinada.sqlite;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

/**
 * Created by brunolemgruber on 17/06/15.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_PRODUTOS = "produtos";
    public static final String TABLE_PRODUTO = "produto";
    public static final String COLUMN_ID_PRODUTO = "_id";
    public static final String COLUMN_ID_PRODUTOS = "_id";
    public static final String COLUMN_QUANT = "quantidade";
    public static final String COLUMN_PRECO = "preco";
    public static final String COLUMN_USUARIONOME = "usuarioNome";
    public static final String COLUMN_NAOCONTEM = "naoContem";
    public static final String COLUMN_DELETOU = "deletou";
    public static final String COLUMN_PRODUTO = "produto_id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_DESCRICAO = "descricao";
    public static final String COLUMN_FOTO = "foto";

    private static final String DATABASE_NAME = "compracombinada.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_PRODUTOS = "create table "
            + TABLE_PRODUTOS + "(" +
            COLUMN_ID_PRODUTOS + " integer primary key autoincrement, " +
            COLUMN_QUANT + " text not null, " +
            COLUMN_PRECO + " text not null, " +
            COLUMN_USUARIONOME + " text not null, " +
            COLUMN_NAOCONTEM + " integer not null, " +
            COLUMN_DELETOU + " integer not null, " +
            COLUMN_PRODUTO + " integer, " +
            "FOREIGN KEY(" + COLUMN_PRODUTO + ") REFERENCES " + TABLE_PRODUTO + "(" + COLUMN_ID_PRODUTO + ") ON DELETE CASCADE);";

    private static final String DATABASE_CREATE_PRODUTO = "create table "
            + TABLE_PRODUTO + "(" +
            COLUMN_ID_PRODUTO + " integer primary key autoincrement, " +
            COLUMN_NOME + " text not null, " +
            COLUMN_DESCRICAO + " text not null, " +
            COLUMN_FOTO + " text null "+
            ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_PRODUTO);
        database.execSQL(DATABASE_CREATE_PRODUTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTOS);
        onCreate(db);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }
}

