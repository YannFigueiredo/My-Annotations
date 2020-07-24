package com.yannfigueiredo.myannotations.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static String NOME_DB = "DB_notas";
    public static String NOME_TABELA = "notas";
    public static int VERSION = 1;

    public DBHelper(Context context) {
        super(context, NOME_TABELA, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "+NOME_TABELA+" (id INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, titulo VARCHAR NOT NULL, conteudo TEXT NOT NULL);";

        try{
            db.execSQL(sql);
            Log.i("INFO", "Sucesso ao criar a tabela!");
        }catch (Exception e){
            Log.i("INFO", "Erro ao criar a tabela!" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS "+NOME_TABELA+";";

        try {
            db.execSQL(sql);
            onCreate(db);
            Log.i("INFO", "Sucesso ao atualizar a tabela!");
        }catch (Exception e){
            Log.i("INFO", "Erro ao atualizar a tabela!" + e.getMessage());
        }
    }
}
