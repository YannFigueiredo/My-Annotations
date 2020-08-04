package com.yannfigueiredo.myannotations.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.yannfigueiredo.myannotations.R;
import com.yannfigueiredo.myannotations.helper.NotaDAO;
import com.yannfigueiredo.myannotations.helper.RecyclerClickListener;
import com.yannfigueiredo.myannotations.model.Nota;

import java.util.ArrayList;

public class InserirNotaActivity extends AppCompatActivity {

    //private ConstraintLayout telaInserirNota;
    private EditText editTitulo;
    private EditText editConteudo;
    private EditText editCategoria;
    private Spinner spinnerCores;
    private Nota notaAtual;
    //private ArrayList<String> listaCores = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_nota);

        this.editTitulo = findViewById(R.id.editTitulo);
        this.editConteudo = findViewById(R.id.editNota);
        this.editCategoria = findViewById(R.id.editCategoria);
        //this.telaInserirNota = findViewById(R.id.telaInserirNota);
        this.spinnerCores = findViewById(R.id.spinnerCores);

        this.notaAtual = (Nota) getIntent().getSerializableExtra("notaSelecionada");

        //Configuração Lista suspensa de cores
        this.listaSuspensaCores();

        //this.listaCores = getResources().getStringArray(R.array.)

        if(notaAtual != null){
            editTitulo.setText(notaAtual.getTitulo());
            editConteudo.setText(notaAtual.getConteudo());
            editCategoria.setText(notaAtual.getCategoria());
            //telaInserirNota.setBackgroundResource(notaAtual.getCor());
        }
    }

    private void listaSuspensaCores(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.coresCategorias,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerCores.setAdapter(adapter);

        this.spinnerCores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        String categoria = this.editCategoria.getText().toString();
        int cor = R.color.colorBackground;
        NotaDAO notaDAO = new NotaDAO(getApplicationContext());
        Nota nota = new Nota();

        if(categoria == null || categoria.length() == 0)
            categoria = "Nenhuma categoria";

        nota.setTitulo(titulo);
        nota.setConteudo(conteudo);
        nota.setCategoria(categoria);
        nota.setCor(cor);

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
