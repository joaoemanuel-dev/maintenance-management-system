package com.joao.empresa.dto.usuario;

import com.joao.empresa.model.Administrador;
import com.joao.empresa.model.Gestor;
import com.joao.empresa.model.Tecnico;
import com.joao.empresa.model.Usuario;

public record UsuarioResponse(

        Integer id,
        String nome,
        String email,
        Usuario.TipoUsuario tipo,
        String departamento,
        String areaResponsavel,
        String especialidade

) {

    public static UsuarioResponse from(Usuario usuario) {

        if (usuario instanceof Administrador administrador) {

            return new UsuarioResponse(
                    administrador.getId(),
                    administrador.getNome(),
                    administrador.getEmail(),
                    administrador.getTipo(),
                    administrador.getDepartamento(),
                    null,
                    null
            );
        }

        if (usuario instanceof Gestor gestor) {

            return new UsuarioResponse(
                    gestor.getId(),
                    gestor.getNome(),
                    gestor.getEmail(),
                    gestor.getTipo(),
                    null,
                    gestor.getAreaResponsavel(),
                    null
            );
        }

        if (usuario instanceof Tecnico tecnico) {

            return new UsuarioResponse(
                    tecnico.getId(),
                    tecnico.getNome(),
                    tecnico.getEmail(),
                    tecnico.getTipo(),
                    null,
                    null,
                    tecnico.getEspecialidade()
            );
        }

        throw new IllegalArgumentException(
                "Tipo de usuário não reconhecido."
        );
    }
}
