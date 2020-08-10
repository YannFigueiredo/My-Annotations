package com.yannfigueiredo.myannotations.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yannfigueiredo.myannotations.R;
import com.yannfigueiredo.myannotations.adapter.Adapter;
import com.yannfigueiredo.myannotations.helper.NotaDAO;
import com.yannfigueiredo.myannotations.helper.RecyclerClickListener;
import com.yannfigueiredo.myannotations.model.Nota;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Nota> listaNotas = new ArrayList<>();
    private List<Nota> listaCategoriasNotas = new ArrayList<>();
    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        this.recyclerView = findViewById(R.id.recyclerViewMain);

        this.recyclerView.addOnItemTouchListener(new RecyclerClickListener(getApplicationContext(),
                recyclerView, new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, NotasActivity.class);
                intent.putExtra("categoriaSelecionada", listaCategoriasNotas.get(position).getCategoria());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InserirNotaActivity.class);
                startActivity(intent);
            }
        });
    }

    public void carregarDados(){
        NotaDAO notaDAO = new NotaDAO(getApplicationContext());
        this.listaNotas = notaDAO.listar_notas();
        this.listaCategoriasNotas = this.determinarExibicaoCategorias();
        this.adapter = new Adapter(listaCategoriasNotas);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

    }

    @Override
    protected void onStart() {
        carregarDados();
        super.onStart();
    }

    public List<Nota> determinarExibicaoCategorias(){
        List<Nota> lista = new ArrayList<>();
        boolean cont = false;
        for(int i=0;i<this.listaNotas.size();i++){
            for(int j=0;j<lista.size();j++){
                if(this.listaNotas.get(i).getCategoria().equals(lista.get(j).getCategoria())){
                    cont = true;
                    break;
                }
            }
            if(cont == false){
                lista.add(this.listaNotas.get(i));
            }
            cont = false;
        }
        return lista;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
