package com.yannfigueiredo.myannotations.helper;

import com.yannfigueiredo.myannotations.model.Nota;

import java.util.List;

public interface iNotaDAO {
    public boolean inserir_nota(Nota nota);
    public boolean atualizar_nota(Nota nota);
    public boolean deletar_nota(Nota nota);
    public List<Nota> listar_notas();
}
