package com.yannfigueiredo.myannotations.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yannfigueiredo.myannotations.R;
import com.yannfigueiredo.myannotations.model.Nota;

import java.util.ArrayList;
import java.util.List;

public class AdapterNotas extends RecyclerView.Adapter<AdapterNotas.MyViewHolder> {

    private List<Nota> listaNotas;
    private String categoria = null;

    public AdapterNotas(List<Nota> notas, String categoria_selecionada) {
        categoria = categoria_selecionada;

        this.listaNotas = notas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Nota nota = listaNotas.get(position);

        if(nota.getCategoria() == categoria){
                holder.itemLista.setText(nota.getTitulo());
        }
    }

    @Override
    public int getItemCount() {
        return listaNotas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView itemLista;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemLista = itemView.findViewById(R.id.textItemLista);
        }
    }
}
