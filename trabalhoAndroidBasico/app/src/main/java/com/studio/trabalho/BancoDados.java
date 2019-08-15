package com.studio.trabalho;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BancoDados extends SQLiteOpenHelper {

    private static final int VERSAO_BANCO = 1;
    //private static final String BANCO_CLIENTE = "db_clientes";
    private static final String BANCO_CLIENTE = "db_clientes_01";

    private static final String TB_CLIENTE = "tb_clientes";
    private static final String COLUNA_CODIGO = "codigo";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_TELEFONE = "telefone";
    private static final String COLUNA_EMAIL = "email";
    private static final String COLUNA_FOTO = "foto";


    public BancoDados(Context context) {
        super(context, BANCO_CLIENTE, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_COLUNA = "create table " + TB_CLIENTE + "("
                + COLUNA_CODIGO + " INTEGER PRIMARY KEY, "
                + COLUNA_NOME + " TEXT, "
                + COLUNA_TELEFONE + " TEXT, "
                + COLUNA_EMAIL + " TEXT, "
                + COLUNA_FOTO + " BLOB)";////////////

        db.execSQL(QUERY_COLUNA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    void addCliente(Cliente cliente){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, cliente.getNome());
        values.put(COLUNA_TELEFONE, cliente.getTelefone());
        values.put(COLUNA_EMAIL, cliente.getEmail());
        values.put(COLUNA_FOTO, cliente.getImg());

        db.insert(TB_CLIENTE, null, values);
        db.close();
    }

    void removerCliente(Cliente cliente) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TB_CLIENTE, COLUNA_CODIGO + " = ?", new String[]{String.valueOf(cliente.getId())});
        db.close();
    }

    Cliente selecionaCliente(int codigo){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TB_CLIENTE, new String[]{
                COLUNA_CODIGO,
                COLUNA_NOME,
                COLUNA_TELEFONE,
                COLUNA_EMAIL,
                COLUNA_FOTO
        }, COLUNA_CODIGO + " = ?", new String[]{String.valueOf(codigo)},
                null,null,null);

        if (cursor != null)cursor.moveToFirst();

        //0: codigo, 1: nome, 2: telefone, 3: email, 4: foto
        Cliente cliente = new Cliente(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4));

        return cliente;
    }

    void atualizaCliente(Cliente cliente){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, cliente.getNome());
        values.put(COLUNA_TELEFONE, cliente.getTelefone());
        values.put(COLUNA_EMAIL, cliente.getEmail());
        values.put(COLUNA_FOTO, cliente.getImg());///////////////////

        db.update(TB_CLIENTE, values,
                COLUNA_CODIGO + " = ?", new String[]{String.valueOf(cliente.getId())});
        db.close();
    }

    public List<Cliente> listatodosClientes(){

        List<Cliente> listClientes = new ArrayList<>();

        String query = "SELECT * FROM " + TB_CLIENTE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                Cliente cliente = new Cliente();
                cliente.setId(Integer.parseInt(cursor.getString(0)));
                cliente.setNome(cursor.getString(1));
                cliente.setTelefone(cursor.getString(2));
                cliente.setEmail(cursor.getString(3));
                cliente.setImg(cursor.getBlob(4));
                listClientes.add(cliente);
            }while (cursor.moveToNext());
        }

        return listClientes;
    }
}