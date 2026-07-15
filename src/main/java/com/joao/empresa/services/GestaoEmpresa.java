package com.joao.empresa.services;

import com.joao.empresa.dao.EmpresaDAO;
import com.joao.empresa.exceptions.EmpresaNaoEncontradaException;
import com.joao.empresa.model.Empresa;
import com.joao.empresa.exceptions.EmpresaJaCadastradaException;
import java.util.Collections;
import java.util.Set;

public class GestaoEmpresa {

    EmpresaDAO empresaDAO = new EmpresaDAO();

    public Empresa buscarPorId(int id) {
        Empresa empresa = empresaDAO.buscarPorId(id);

        if(empresa == null){
            throw new EmpresaNaoEncontradaException("Empresa com ID " + id + " não encontrada.");
        }

        return empresa;
    }

    public void cadastrarEmpresa(Empresa empresa){
        empresaDAO.salvar(empresa);
    }

    public Set<Empresa> listarEmpresas(){
        return Collections.unmodifiableSet(empresas); // retorna uma cópia da lista e não deixa ninguém alterar
    }

    public void atualizarEmpresa(Empresa alterada){
        Empresa existente = buscarPorId(alterada.getId());

        if(alterada.getNome() != null){
            existente.setNome(alterada.getNome());
        }
        if(alterada.getCnpj() != null){
            existente.setCnpj(alterada.getCnpj());
        }
        if(alterada.getEndereco() != null){
            existente.setEndereco(alterada.getEndereco());
        }
        if(alterada.getSegmento() != null){
            existente.setSegmento(alterada.getSegmento());
        }

    }

    public void excluirEmpresa(int id){
        Empresa emp = buscarPorId(id);
        empresas.remove(emp);
    }

}


