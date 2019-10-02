package com.caravanas.Dominio.Repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.caravanas.Dominio.Entidades.Excursao;

import java.util.ArrayList;
import java.util.List;

public class ExcursaoRepositorio {

    private SQLiteDatabase conexao;

    public ExcursaoRepositorio(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public void inserir(Excursao excursao){

        ContentValues contentValues = new ContentValues();
        contentValues.put("Nome", excursao.nome);
        contentValues.put("LocalSaida", excursao.saidaLocal);
        contentValues.put("Data", excursao.data);
        contentValues.put("Telefone", excursao.telefone);

        conexao.insertOrThrow("EXCURSAO", null, contentValues);

    }

    public void excluir(int id){

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);

        conexao.delete("EXCURSAO", "id = ? ", parametros);

    }

    public void alterar(Excursao excursao){

        ContentValues contentValues = new ContentValues();
        contentValues.put("Nome", excursao.nome);
        contentValues.put("LocalSaida", excursao.saidaLocal);
        contentValues.put("Data", excursao.data);
        contentValues.put("Telefone", excursao.telefone);

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(excursao.id);

        conexao.update("EXCURSAO", contentValues, "id = ? ", parametros);

    }

    public List<Excursao> buscarTodos(){

        List<Excursao> excursao = new ArrayList<Excursao>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, Nome, LocalSaida, Data, Telefone ");
        sql.append("FROM EXCURSAO");

        Cursor resultado = conexao.rawQuery(sql.toString(), null);

        if(resultado.getCount() > 0) {
            resultado.moveToFirst();

            do{

                Excursao exc = new Excursao();
                exc.id = resultado.getInt(resultado.getColumnIndexOrThrow("id"));
                exc.nome = resultado.getString(resultado.getColumnIndexOrThrow("Nome"));
                exc.saidaLocal = resultado.getString(resultado.getColumnIndexOrThrow("LocalSaida"));
                exc.data = resultado.getString(resultado.getColumnIndexOrThrow("Data"));
                exc.telefone = resultado.getString(resultado.getColumnIndexOrThrow("Telefone"));

                excursao.add(exc);


            }while (resultado.moveToNext());
        }

        return excursao;

    }

    public Excursao buscarCliente(int id){

        Excursao excursao = new Excursao();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, Nome, LocalSaida, Data, Telefone ");
        sql.append("FROM EXCURSAO ");
        sql.append("WHERE id = ? ");

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);

        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);

        if(resultado.getCount() > 0) {

            resultado.moveToFirst();

            excursao.id = resultado.getInt(resultado.getColumnIndexOrThrow("id"));
            excursao.nome = resultado.getString(resultado.getColumnIndexOrThrow("Nome"));
            excursao.saidaLocal = resultado.getString(resultado.getColumnIndexOrThrow("LocalSaida"));
            excursao.data = resultado.getString(resultado.getColumnIndexOrThrow("Data"));
            excursao.telefone = resultado.getString(resultado.getColumnIndexOrThrow("Telefone"));

            return excursao;
        }

        return null;
    }





}
