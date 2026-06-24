package com.joao.empresa.dao;

import com.joao.empresa.model.Tecnico;
import com.joao.empresa.model.Usuario;

public class TesteUsuarioDAO {
    public static void main(String[] args) {

        Usuario usuario = new Tecnico(
                0,
                "Laura",
                "laluisa@linda",
                "3012",
                "Artista Plástica"
        );

        UsuarioDAO dao = new UsuarioDAO();
        dao.salvar(usuario);
    }
}





