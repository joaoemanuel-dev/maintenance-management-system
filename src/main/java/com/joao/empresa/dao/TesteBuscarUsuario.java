package com.joao.empresa.dao;

import com.joao.empresa.model.Usuario;

public class TesteBuscarUsuario {
    public static void main(String[] args) {

        UsuarioDAO dao = new UsuarioDAO();

        Usuario usuario = dao.buscarPorId(2);

        if (usuario != null) {
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("Email: " + usuario.getEmail());
            System.out.println("Tipo: " + usuario.getTipo());
        } else {
            System.out.println("Usuário não encontrado");
        }
    }
}
