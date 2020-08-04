package com.yannfigueiredo.myannotations.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yannfigueiredo.myannotations.model.Nota;

import java.util.ArrayList;
import java.util.List;

public class NotaDAO implements iNotaDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public NotaDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);

        this.escreve = dbHelper.getWritableDatabase();
        this.le = dbHelper.getReadableDatabase();
    }

    @Override
    public boolean inserir_nota(Nota nota) {
        ContentValues cv = new ContentValues();
        cv.put("titulo", nota.getTitulo());
        cv.put("conteudo", nota.getConteudo());
        cv.put("categoria", nota.getCategoria());
        cv.put("cor", nota.getCor());

        try{
            escreve.insert(DBHelper.NOME_TABELA, null, cv);
            Log.i("INFO", "Sucesso ao salvar nota!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao salvar nota!" + e.getMessage());

            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar_nota(Nota nota) {
        ContentValues cv = new ContentValues();
        cv.put("titulo", nota.getTitulo());
        cv.put("conteudo", nota.getConteudo());
        cv.put("categoria", nota.getCategoria());
        cv.put("cor", nota.getCor());

        String[] args = {nota.getId().toString()};

        try{
            escreve.update(DBHelper.NOME_TABELA, cv, "id=?", args);
            Log.i("INFO", "Sucesso ao atualizar a nota!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizar a nota!" + e.getMessage());

            return false;
        }

        return true;
    }

    @Override
    public boolean deletar_nota(Nota nota) {
        try {
            String[] args = {nota.getId().toString()};
            escreve.delete(DBHelper.NOME_TABELA, "id=?", args);
            Log.i("INFO", "Sucesso ao deletar a nota!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao deletar a nota!" + e.getMessage());

            return false;
        }

        return true;
    }

    @Override
    public List<Nota> listar_notas() {
        List<Nota> listaNotas = new ArrayList<>();
        Cursor cursor = le.rawQuery("SELECT * FROM " + DBHelper.NOME_TABELA + ";", null);

        while(cursor.moveToNext()){
            Nota nota = new Nota();

            long id = cursor.getLong(0);
            String titulo = cursor.getString(1);
            String conteudo = cursor.getString(2);
            String categoria = cursor.getString(3);
            int cor = cursor.getInt(4);

            nota.setId(id);
            nota.setTitulo(titulo);
            nota.setConteudo(conteudo);
            nota.setCategoria(categoria);
            nota.setCor(cor);

            listaNotas.add(nota);
        }

        return listaNotas;
    }
}
