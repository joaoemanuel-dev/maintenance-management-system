package com.joao.empresa.dao;

import com.joao.empresa.model.Usuario;

public class TesteAtualizarUsuario {

    public static void main(String[] args){

        UsuarioDAO dao = new UsuarioDAO();

        Usuario usuario = dao.buscarPorId(1);

        usuario.setNome("Nome Atualizado");

        dao.atualizar(usuario);

    }

}
