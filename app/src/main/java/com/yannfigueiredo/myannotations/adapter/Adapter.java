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

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Nota> listaNotas;
    private List<String> controle = new ArrayList<>();

    public Adapter(List<Nota> notas) {
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

        if(this.verificarCategoria(nota.getCategoria()) == false){
                holder.itemLista.setText(nota.getCategoria());
                controle.add(nota.getCategoria());
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

    public boolean verificarCategoria(String categoriaTeste){
        for(int i=0;i<controle.size();i++){
            if(categoriaTeste.equals(controle.get(i))){
                return true;
            }
        }
        return false;
    }
}
