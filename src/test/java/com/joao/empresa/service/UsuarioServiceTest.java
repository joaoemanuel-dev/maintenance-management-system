package com.joao.empresa.service;

import com.joao.empresa.exceptions.EntidadeEmUsoException;
import com.joao.empresa.exceptions.UsuarioJaCadastradoException;
import com.joao.empresa.exceptions.UsuarioNaoEncontradoException;
import com.joao.empresa.model.Administrador;
import com.joao.empresa.model.Gestor;
import com.joao.empresa.model.Tecnico;
import com.joao.empresa.model.Usuario;
import com.joao.empresa.repository.ManutencaoRepository;
import com.joao.empresa.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.joao.empresa.builders.AdministradorBuilder.umAdministrador;
import static com.joao.empresa.builders.GestorBuilder.umGestor;
import static com.joao.empresa.builders.TecnicoBuilder.umTecnico;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ManutencaoRepository manutencaoRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveBuscarUsuarioPorId() {

        Tecnico tecnico = umTecnico();

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(tecnico));

        Usuario resultado = usuarioService.buscarPorId(1);

        assertSame(tecnico, resultado);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {

        when(usuarioRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                UsuarioNaoEncontradoException.class,
                () -> usuarioService.buscarPorId(999)
        );
    }

    @Test
    void deveCadastrarAdministrador() {

        when(usuarioRepository
                .existsByEmail("admin@email.com"))
                .thenReturn(false);

        when(usuarioRepository.save(any(Administrador.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0)
                );

        Administrador resultado =
                usuarioService.cadastrarAdministrador(
                        "Administrador Teste",
                        "admin@email.com",
                        "Tecnologia"
                );

        assertEquals(
                "Administrador Teste",
                resultado.getNome()
        );

        assertEquals(
                "Tecnologia",
                resultado.getDepartamento()
        );
    }

    @Test
    void deveCadastrarGestor() {

        when(usuarioRepository
                .existsByEmail("gestor@email.com"))
                .thenReturn(false);

        when(usuarioRepository.save(any(Gestor.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0)
                );

        Gestor resultado =
                usuarioService.cadastrarGestor(
                        "Gestor Teste",
                        "gestor@email.com",
                        "Produção"
                );

        assertEquals(
                "Produção",
                resultado.getAreaResponsavel()
        );
    }

    @Test
    void deveCadastrarTecnico() {

        when(usuarioRepository
                .existsByEmail("tecnico@email.com"))
                .thenReturn(false);

        when(usuarioRepository.save(any(Tecnico.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0)
                );

        Tecnico resultado =
                usuarioService.cadastrarTecnico(
                        "Técnico Teste",
                        "tecnico@email.com",
                        "Elétrica"
                );

        assertEquals(
                "Elétrica",
                resultado.getEspecialidade()
        );
    }

    @Test
    void deveImpedirCadastroComEmailDuplicado() {

        when(usuarioRepository
                .existsByEmail("tecnico@email.com"))
                .thenReturn(true);

        assertThrows(
                UsuarioJaCadastradoException.class,
                () -> usuarioService.cadastrarTecnico(
                        "Técnico",
                        "tecnico@email.com",
                        "Elétrica"
                )
        );

        verify(
                usuarioRepository,
                never()
        ).save(any());
    }

    @Test
    void deveAtualizarAdministrador() {

        Administrador administrador = umAdministrador();

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(administrador));

        when(usuarioRepository
                .existsByEmailAndIdNot(
                        "novo@email.com",
                        1
                ))
                .thenReturn(false);

        Administrador resultado =
                usuarioService.atualizarAdministrador(
                        1,
                        "Novo Administrador",
                        "novo@email.com",
                        "Financeiro"
                );

        assertEquals(
                "Financeiro",
                resultado.getDepartamento()
        );

        assertEquals(
                "novo@email.com",
                resultado.getEmail()
        );
    }

    @Test
    void deveAtualizarGestor() {

        Gestor gestor = umGestor();

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(gestor));

        when(usuarioRepository
                .existsByEmailAndIdNot(
                        "novo@email.com",
                        1
                ))
                .thenReturn(false);

        Gestor resultado =
                usuarioService.atualizarGestor(
                        1,
                        "Novo Gestor",
                        "novo@email.com",
                        "Logística"
                );

        assertEquals(
                "Logística",
                resultado.getAreaResponsavel()
        );
    }

    @Test
    void deveAtualizarTecnico() {

        Tecnico tecnico = umTecnico();

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(tecnico));

        when(usuarioRepository
                .existsByEmailAndIdNot(
                        "novo@email.com",
                        1
                ))
                .thenReturn(false);

        Tecnico resultado =
                usuarioService.atualizarTecnico(
                        1,
                        "Novo Técnico",
                        "novo@email.com",
                        "Mecânica"
                );

        assertEquals(
                "Mecânica",
                resultado.getEspecialidade()
        );
    }

    @Test
    void deveImpedirAtualizacaoDeTecnicoQuandoUsuarioNaoForTecnico() {

        Gestor gestor = umGestor();

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(gestor));

        assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.atualizarTecnico(
                        1,
                        "Nome",
                        "email@email.com",
                        "Elétrica"
                )
        );
    }

    @Test
    void deveImpedirExclusaoDeTecnicoComManutencoes() {

        Tecnico tecnico = umTecnico();

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(tecnico));

        when(manutencaoRepository
                .existsByTecnicoResponsavel_Id(1))
                .thenReturn(true);

        assertThrows(
                EntidadeEmUsoException.class,
                () -> usuarioService.excluir(1)
        );

        verify(
                usuarioRepository,
                never()
        ).delete(any());
    }

    @Test
    void deveExcluirTecnicoSemManutencoes() {

        Tecnico tecnico = umTecnico();

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(tecnico));

        when(manutencaoRepository
                .existsByTecnicoResponsavel_Id(1))
                .thenReturn(false);

        usuarioService.excluir(1);

        verify(usuarioRepository)
                .delete(tecnico);
    }

    @Test
    void deveExcluirAdministrador() {

        Administrador administrador =
                umAdministrador();

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(administrador));

        usuarioService.excluir(1);

        verify(usuarioRepository)
                .delete(administrador);
    }
}
