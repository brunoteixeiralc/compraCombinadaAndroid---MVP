package br.com.compracombinada.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.compracombinada.model.Produto;
import br.com.compracombinada.model.Produtos;

/**
 * Created by brunolemgruber on 17/06/15.
 */
public class CompraCombindaDS {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumnsProdutos = { MySQLiteHelper.COLUMN_ID_PRODUTOS,
            MySQLiteHelper.COLUMN_DELETOU, MySQLiteHelper.COLUMN_NAOCONTEM,
            MySQLiteHelper.COLUMN_PRECO,MySQLiteHelper.COLUMN_QUANT,
            MySQLiteHelper.COLUMN_USUARIONOME,MySQLiteHelper.COLUMN_PRODUTO};

    private String[] allColumnsProduto = { MySQLiteHelper.COLUMN_ID_PRODUTO,
            MySQLiteHelper.COLUMN_NOME, MySQLiteHelper.COLUMN_DESCRICAO,
            MySQLiteHelper.COLUMN_FOTO};


    public CompraCombindaDS(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        if (!database.isReadOnly()) {
            database.execSQL("PRAGMA foreign_keys = ON;");
        }
    }

    public void close() {
        dbHelper.close();
    }

    public void createProdutos(Produtos produtos) {

        ContentValues valuesProduto = new ContentValues();
        valuesProduto.put(MySQLiteHelper.COLUMN_NOME, produtos.getProduto().getNome());
        valuesProduto.put(MySQLiteHelper.COLUMN_DESCRICAO, produtos.getProduto().getDescricao());
        valuesProduto.put(MySQLiteHelper.COLUMN_FOTO, produtos.getProduto().getFoto());
        long insertIdProduto = database.insert(MySQLiteHelper.TABLE_PRODUTO, null,
                valuesProduto);

        ContentValues valuesProdutos = new ContentValues();
        valuesProdutos.put(MySQLiteHelper.COLUMN_QUANT, produtos.getQuantidade());
        valuesProdutos.put(MySQLiteHelper.COLUMN_PRECO, produtos.getPreco());
        valuesProdutos.put(MySQLiteHelper.COLUMN_USUARIONOME, produtos.getUsuarioNome());
        valuesProdutos.put(MySQLiteHelper.COLUMN_NAOCONTEM, produtos.isNaoContem()?1:0);
        valuesProdutos.put(MySQLiteHelper.COLUMN_DELETOU, produtos.isDeletou()?1:0);
        valuesProdutos.put(MySQLiteHelper.COLUMN_PRODUTO, insertIdProduto);
        database.insert(MySQLiteHelper.TABLE_PRODUTOS, null,
                valuesProdutos);
    }

    public void deleteProdutos() {
        database.delete(MySQLiteHelper.TABLE_PRODUTO, null, null);
        database.delete(MySQLiteHelper.TABLE_PRODUTOS, null, null);
        System.out.println("Produtos deleted");
    }

    public List<Produtos> getAllProdutos() {
        List<Produtos> listProdutos = new ArrayList<Produtos>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PRODUTOS,
                allColumnsProdutos, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Produtos p = cursorToProdutos(cursor);
            listProdutos.add(p);
            cursor.moveToNext();
        }
        cursor.close();
        return listProdutos;
    }

    private Produtos cursorToProdutos(Cursor cursor) {
        Produtos produtos = new Produtos();
        produtos.setId(cursor.getInt(0));
//        produtos.setDeletou(cursor.getInt(1) > 0);
//        produtos.setNaoContem(cursor.getInt(2) >0);
//        produtos.setPreco(cursor.getString(3));
//        produtos.setQuantidade(cursor.getInt(4));
//        produtos.setUsuarioNome(cursor.getString(5));
        return produtos;

    }
}

