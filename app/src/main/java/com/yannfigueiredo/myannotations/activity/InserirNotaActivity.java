package com.yannfigueiredo.myannotations.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.yannfigueiredo.myannotations.R;
import com.yannfigueiredo.myannotations.helper.NotaDAO;
import com.yannfigueiredo.myannotations.model.Nota;

public class InserirNotaActivity extends AppCompatActivity {

    private EditText editTitulo;
    private EditText editConteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_nota);

        this.editTitulo = findViewById(R.id.editTitulo);
        this.editConteudo = findViewById(R.id.editNota);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String titulo = this.editTitulo.getText().toString();
        String conteudo = this.editConteudo.getText().toString();
        NotaDAO notaDAO = new NotaDAO(getApplicationContext());
        Nota nota = new Nota();

        nota.setTitulo(titulo);
        nota.setConteudo(conteudo);

        switch (item.getItemId()){
            case R.id.itemSalvar:
                if(titulo != null && titulo.length()>0){
                    if(notaDAO.inserir_nota(nota)){
                        Toast.makeText(getApplicationContext(), "Nota salva com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Erro ao salvar a nota!", Toast.LENGTH_SHORT).show();
                    }

                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Necessário informar o título!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.itemCompartilhar:
                Toast.makeText(getApplicationContext(), "Compartilhando...", Toast.LENGTH_SHORT).show();

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
