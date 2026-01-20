package com.joao.empresa.services;

import com.joao.empresa.builders.AdministradorBuilder;
import com.joao.empresa.builders.GestorBuilder;
import com.joao.empresa.exceptions.UsuarioJaCadastradoException;
import com.joao.empresa.exceptions.UsuarioNaoEncontradoException;
import com.joao.empresa.model.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GestaoUsuarioTest {

    @Test
    public void quandoMetodoBuscarPorIdForChamadoEExistirUsuarioComIdBuscadoDeveRetornarEsseUsuario(){

        GestaoUsuario gestaoUsuario = new GestaoUsuario();
        Usuario usuarioNovo = AdministradorBuilder.builder().build();
        gestaoUsuario.cadastrarUsuario(usuarioNovo);

        Usuario usuario = gestaoUsuario.buscarPorId(1);

        assertEquals(1, usuario.getId());
    }

    @Test
    public void quandoMetodoBuscarPorIdForChamadoENaoExistirUsuarioComOIdBuscadoDeveLancarExcecao(){

        GestaoUsuario gestaoUsuario = new GestaoUsuario();
        Usuario usuarioNovo = AdministradorBuilder.builder().build();
        gestaoUsuario.cadastrarUsuario(usuarioNovo);

        assertThrows(UsuarioNaoEncontradoException.class, () ->{
            gestaoUsuario.buscarPorId(2);
        });
    }

    @Test
    public void quandoOMetodoCadastrarUsuarioForChamadoSeJaExistirUmUsuarioComOMesmoIdDeveLancarExcecao(){

        GestaoUsuario gestaoUsuario = new GestaoUsuario();
        Usuario usuarioNovo1 = AdministradorBuilder.builder().build();
        Usuario usuarioNovo2 = AdministradorBuilder.builder().build();
        gestaoUsuario.cadastrarUsuario(usuarioNovo1);

        assertThrows(UsuarioJaCadastradoException.class, () -> {
            gestaoUsuario.cadastrarUsuario(usuarioNovo2);
        });
    }

    @Test
    public void quandoCadastrarUsuarioForChamadoSemConflitosDeIdDeveAdicionarUsuarioAoSistema(){

        GestaoUsuario gestaoUsuario = new GestaoUsuario();
        Usuario usuarioNovo = AdministradorBuilder.builder().build();
        gestaoUsuario.cadastrarUsuario(usuarioNovo);

        assertTrue(gestaoUsuario.listarUsuarios().contains(usuarioNovo));
    }

    @Test
    public void quandoOMetodoExcluirUsuarioForChamadoOUsuarioDeveSerExcluidoDoSistema(){

        GestaoUsuario gestaoUsuario = new GestaoUsuario();
        Usuario usuarioNovo = AdministradorBuilder.builder().build();
        gestaoUsuario.cadastrarUsuario(usuarioNovo);

        gestaoUsuario.removerUsuario(1);

        assertFalse(gestaoUsuario.listarUsuarios().contains(usuarioNovo));
    }

}
