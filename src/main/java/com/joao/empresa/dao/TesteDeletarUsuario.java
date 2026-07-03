package com.joao.empresa.dao;

public class TesteDeletarUsuario {

    public static void main(String[] args){

        UsuarioDAO dao = new UsuarioDAO();

        dao.deletar(1);

    }

}
