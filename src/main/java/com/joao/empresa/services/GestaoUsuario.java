package com.joao.empresa.services;

import com.joao.empresa.dao.UsuarioDAO;
import com.joao.empresa.model.Usuario;
import com.joao.empresa.exceptions.*;
import java.util.*;

public class GestaoUsuario {

    private UsuarioDAO usuarioDAO = new UsuarioDAO(); // poder mexer no banco daqui mesmo

    public Usuario buscarPorId(int id){
        Usuario usuario = usuarioDAO.buscarPorId(id);

        if(usuario == null){
            throw new UsuarioNaoEncontradoException("Usuario com ID \" + id + \" não encontrado.");
        }

        return usuario;
    }

    public void cadastrarUsuario(Usuario usuario){
        usuarioDAO.salvar(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listar();
    }

    public void atualizarUsuario(Usuario alterado){
        Usuario existente = buscarPorId(alterado.getId()); // aqui já lança exceção

        existente.atualizarDados(alterado);
        existente.atualizarEspecifico(alterado);
    }

    public void removerUsuario(int id){
        Usuario usr = buscarPorId(id); // aqui lança exceção
        usuarios.remove(usr);
    }

}
