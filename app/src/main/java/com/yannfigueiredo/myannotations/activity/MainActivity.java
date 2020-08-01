package com.yannfigueiredo.myannotations.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.yannfigueiredo.myannotations.R;
import com.yannfigueiredo.myannotations.adapter.Adapter;
import com.yannfigueiredo.myannotations.helper.NotaDAO;
import com.yannfigueiredo.myannotations.helper.RecyclerClickListener;
import com.yannfigueiredo.myannotations.model.Nota;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        this.recyclerView = findViewById(R.id.recyclerView);

        this.recyclerView.addOnItemTouchListener(new RecyclerClickListener(getApplicationContext(),
                recyclerView, new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, InserirNotaActivity.class);
                intent.putExtra("notaSelecionada", listaNotas.get(position));
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                final Nota nota = listaNotas.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                dialog.setCancelable(false);
                dialog.setTitle("Deletion confirmation");
                dialog.setMessage("Do you want to delete the note " + nota.getTitulo() + "?");

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NotaDAO notaDAO = new NotaDAO(getApplicationContext());
                        if (notaDAO.deletar_nota(nota)) {
                            carregarDados();
                            Toast.makeText(getApplicationContext(), "Note successfully deleted!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error deleting note!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.setNegativeButton("No", null);

                dialog.create();
                dialog.show();
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
