package com.yannfigueiredo.myannotations.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yannfigueiredo.myannotations.R;
import com.yannfigueiredo.myannotations.adapter.Adapter;
import com.yannfigueiredo.myannotations.adapter.AdapterNotas;
import com.yannfigueiredo.myannotations.helper.NotaDAO;
import com.yannfigueiredo.myannotations.helper.RecyclerClickListener;
import com.yannfigueiredo.myannotations.model.Nota;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class NotasActivity extends AppCompatActivity {

    private List<Nota> listaNotas = new ArrayList<>();
    private List<Nota> listaNotasCategoriaSelecionada = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterNotas adapter;
    private String categoriaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        Bundle bundle = getIntent().getExtras();
        this.categoriaSelecionada = bundle.getString("categoriaSelecionada");

        this.recyclerView = findViewById(R.id.recyclerViewNotas);

        this.recyclerView.addOnItemTouchListener(new RecyclerClickListener(getApplicationContext(),
                recyclerView, new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(NotasActivity.this, InserirNotaActivity.class);
                intent.putExtra("notaSelecionada", listaNotasCategoriaSelecionada.get(position));
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                final Nota nota = listaNotasCategoriaSelecionada.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(NotasActivity.this);

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
    }

    @Override
    protected void onStart() {
        this.carregarDados();
        super.onStart();
    }

    public void carregarDados(){
        NotaDAO notaDAO = new NotaDAO(getApplicationContext());
        this.listaNotas = notaDAO.listar_notas();
        this.listaNotasCategoriaSelecionada = this.determinarExibicaoNotas();
        this.adapter = new AdapterNotas(listaNotasCategoriaSelecionada);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
    }

    public List<Nota> determinarExibicaoNotas(){
        List<Nota> lista = new ArrayList<>();
        for(int i=0;i<this.listaNotas.size();i++){
            if(this.listaNotas.get(i).getCategoria().equals(this.categoriaSelecionada)){
                lista.add(this.listaNotas.get(i));
            }
        }
        return lista;
    }
}
