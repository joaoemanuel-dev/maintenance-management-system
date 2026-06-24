package com.joao.empresa.dao;

import com.joao.empresa.model.Usuario;

import java.util.List;

public class TesteListarUsuarios {

    public static void main(String[] args) {

        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> usuarios = dao.listar();

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
        } else {
            System.out.println("=== LISTA DE USUÁRIOS ===");

            for (Usuario u : usuarios) {
                System.out.println("------------------------");
                System.out.println("ID: " + u.getId());
                System.out.println("Nome: " + u.getNome());
                System.out.println("Email: " + u.getEmail());
                System.out.println("Tipo: " + u.getTipoUsuario());
            }
        }
    }
}

