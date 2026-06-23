package com.joao.empresa.dao;

import com.joao.empresa.model.Administrador;
import com.joao.empresa.model.Usuario;

public class TesteUsuarioDAO {
    public static void main(String[] args) {

        Usuario usuario = new Administrador(
                0,
                "João",
                "joao@email.com",
                "123456",
                "TI"
        );

        UsuarioDAO dao = new UsuarioDAO();
        dao.salvar(usuario);
    }
}
