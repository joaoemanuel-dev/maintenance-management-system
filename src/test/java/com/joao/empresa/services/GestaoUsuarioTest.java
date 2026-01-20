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


    /*
    @Test
    public void quandoAtualizarEmpresaForChamadoDeveAtualizarOsDadosDaEmpresaCadastrada(){

        GestaoEmpresa gestaoEmpresa = new GestaoEmpresa();
        Empresa empresaNova = new Empresa(1, "Gerdau Açominas", "2023018977", "Ouro Branco", "Produtora de aço");
        gestaoEmpresa.cadastrarEmpresa(empresaNova);
        Empresa empresaAlterada = new Empresa(1, "Vale do Rio Doce", "2023018977", "Congonhas", "Produtora de chapas");

        gestaoEmpresa.atualizarEmpresa(empresaAlterada);

        // não compara os dois objetos de uma vez pq é arriscado: depende de equals e se algum falhar não sabe qual
        assertAll( // aqui os erros aparecem tudo de uma vez (sem isso, no primeiro erro o teste parava)
                () -> assertEquals(empresaAlterada.getNome(), empresaNova.getNome()),
                () -> assertEquals(empresaAlterada.getCnpj(), empresaNova.getCnpj()),
                () -> assertEquals(empresaAlterada.getEndereco(), empresaNova.getEndereco()),
                () -> assertEquals(empresaAlterada.getSegmento(), empresaNova.getSegmento())
        );
    }

    @Test
    public void quandoOMetodoExcluirEmpresaForChamadoAEmpresaDeveSerExcluidaDoSistema(){
        GestaoEmpresa gestaoEmpresa = new GestaoEmpresa();
        Empresa empresaNova = new Empresa(1, "Gerdau Açominas", "2023018977", "Ouro Branco", "Produtora de aço");
        gestaoEmpresa.cadastrarEmpresa(empresaNova);

        gestaoEmpresa.excluirEmpresa(1);

        assertFalse(gestaoEmpresa.listarEmpresas().contains(empresaNova));
    }*/

}
