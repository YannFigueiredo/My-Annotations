package com.yannfigueiredo.myannotations.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private Nota notaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_nota);

        this.editTitulo = findViewById(R.id.editTitulo);
        this.editConteudo = findViewById(R.id.editNota);

        this.notaAtual = (Nota) getIntent().getSerializableExtra("notaSelecionada");

        if(notaAtual != null){
            editTitulo.setText(notaAtual.getTitulo());
            editConteudo.setText(notaAtual.getConteudo());
        }
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
                if(notaAtual == null){//salvar
                    if(!titulo.isEmpty()){
                        if(notaDAO.inserir_nota(nota)){
                            Toast.makeText(getApplicationContext(), "Note successfully saved!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Error saving note!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Title required!", Toast.LENGTH_SHORT).show();
                    }
                }else{//editar
                    nota.setId(notaAtual.getId());
                    if(!titulo.isEmpty()){
                        if(notaDAO.atualizar_nota(nota)){
                            Toast.makeText(getApplicationContext(), "Note updated successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Error updating note!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Title required!", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.itemCompartilhar:
                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_SUBJECT, titulo);
                intent.putExtra(Intent.EXTRA_TEXT, conteudo);

                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent, "Share"));

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
