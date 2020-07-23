package com.yannfigueiredo.myannotations.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.yannfigueiredo.myannotations.R;
import com.yannfigueiredo.myannotations.adapter.Adapter;
import com.yannfigueiredo.myannotations.model.Nota;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Nota> listaNotas = new ArrayList<>();
    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.recyclerView = findViewById(R.id.recyclerView);

        Nota nota = new Nota();
        nota.setTitulo("teste maroto");
        nota.setConteudo("conteudo maroto da porra");

        listaNotas.add(nota);

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
       this.adapter = new Adapter(listaNotas);

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
