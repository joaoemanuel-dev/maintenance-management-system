package com.joao.empresa.service;

import com.joao.empresa.exceptions.EquipamentoNaoEncontradoException;
import com.joao.empresa.exceptions.ManutencaoNaoEncontradaException;
import com.joao.empresa.model.Equipamento;
import com.joao.empresa.model.Gestor;
import com.joao.empresa.model.Manutencao;
import com.joao.empresa.model.Tecnico;
import com.joao.empresa.repository.EquipamentoRepository;
import com.joao.empresa.repository.ManutencaoRepository;
import com.joao.empresa.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.joao.empresa.builders.EquipamentoBuilder.umEquipamento;
import static com.joao.empresa.builders.GestorBuilder.umGestor;
import static com.joao.empresa.builders.ManutencaoBuilder.umaManutencao;
import static com.joao.empresa.builders.TecnicoBuilder.umTecnico;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManutencaoServiceTest {

    @Mock
    private ManutencaoRepository manutencaoRepository;

    @Mock
    private EquipamentoRepository equipamentoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ManutencaoService manutencaoService;

    @Test
    void deveBuscarManutencaoPorId() {

        Manutencao manutencao = umaManutencao();

        when(manutencaoRepository.findById(1))
                .thenReturn(Optional.of(manutencao));

        Manutencao resultado = manutencaoService.buscarPorId(1);

        assertSame(manutencao, resultado);
    }

    @Test
    void deveLancarExcecaoQuandoManutencaoNaoExistir() {

        when(manutencaoRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                ManutencaoNaoEncontradaException.class,
                () -> manutencaoService.buscarPorId(999)
        );
    }

    @Test
    void deveCadastrarManutencao() {

        Equipamento equipamento = umEquipamento();

        Tecnico tecnico = umTecnico();

        when(equipamentoRepository.findById(1))
                .thenReturn(Optional.of(equipamento));

        when(usuarioRepository.findById(2))
                .thenReturn(Optional.of(tecnico));

        when(manutencaoRepository.save(any(Manutencao.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0)
                );

        Manutencao resultado =
                manutencaoService.cadastrar(
                        Manutencao.TipoManutencao.PREVENTIVA,
                        "Revisão preventiva",
                        LocalDate.of(2026, 1, 15),
                        1,
                        2
                );

        assertEquals(
                Manutencao.Status.ANDAMENTO,
                resultado.getStatus()
        );

        assertSame(
                equipamento,
                resultado.getEquipamento()
        );

        assertSame(
                tecnico,
                resultado.getTecnicoResponsavel()
        );

        verify(manutencaoRepository)
                .save(any(Manutencao.class));
    }

    @Test
    void deveImpedirCadastroQuandoEquipamentoNaoExistir() {

        when(equipamentoRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                EquipamentoNaoEncontradoException.class,
                () -> manutencaoService.cadastrar(
                        Manutencao.TipoManutencao.PREVENTIVA,
                        "Revisão",
                        LocalDate.now(),
                        999,
                        1
                )
        );

        verify(
                manutencaoRepository,
                never()
        ).save(any());
    }

    @Test
    void deveImpedirCadastroQuandoUsuarioNaoForTecnico() {

        Equipamento equipamento = umEquipamento();

        Gestor gestor = umGestor();

        when(equipamentoRepository.findById(1))
                .thenReturn(Optional.of(equipamento));

        when(usuarioRepository.findById(2))
                .thenReturn(Optional.of(gestor));

        assertThrows(
                IllegalArgumentException.class,
                () -> manutencaoService.cadastrar(
                        Manutencao.TipoManutencao.CORRETIVA,
                        "Correção",
                        LocalDate.now(),
                        1,
                        2
                )
        );

        verify(
                manutencaoRepository,
                never()
        ).save(any());
    }

    @Test
    void deveAtualizarManutencao() {

        Manutencao manutencao = umaManutencao();

        Equipamento equipamento = umEquipamento();

        Tecnico tecnico = umTecnico();

        when(manutencaoRepository.findById(1))
                .thenReturn(Optional.of(manutencao));

        when(equipamentoRepository.findById(2))
                .thenReturn(Optional.of(equipamento));

        when(usuarioRepository.findById(3))
                .thenReturn(Optional.of(tecnico));

        Manutencao resultado =
                manutencaoService.atualizar(
                        1,
                        Manutencao.TipoManutencao.CORRETIVA,
                        "Descrição atualizada",
                        LocalDate.of(2026, 2, 1),
                        2,
                        3
                );

        assertEquals(
                Manutencao.TipoManutencao.CORRETIVA,
                resultado.getTipoManutencao()
        );

        assertEquals(
                "Descrição atualizada",
                resultado.getDescricao()
        );
    }

    @Test
    void deveFinalizarManutencao() {

        Manutencao manutencao = umaManutencao();

        when(manutencaoRepository.findById(1))
                .thenReturn(Optional.of(manutencao));

        Manutencao resultado =
                manutencaoService.finalizar(
                        1,
                        new BigDecimal("500.00")
                );

        assertEquals(
                Manutencao.Status.CONCLUIDA,
                resultado.getStatus()
        );

        assertEquals(
                new BigDecimal("500.00"),
                resultado.getCusto()
        );

        assertNotNull(
                resultado.getDataFim()
        );
    }

    @Test
    void deveImpedirFinalizacaoDeManutencaoJaFinalizada() {

        Manutencao manutencao = umaManutencao();

        manutencao.finalizar(
                new BigDecimal("100.00"),
                LocalDate.now()
        );

        when(manutencaoRepository.findById(1))
                .thenReturn(Optional.of(manutencao));

        assertThrows(
                IllegalStateException.class,
                () -> manutencaoService.finalizar(
                        1,
                        new BigDecimal("200.00")
                )
        );
    }

    @Test
    void deveCancelarManutencao() {

        Manutencao manutencao = umaManutencao();

        when(manutencaoRepository.findById(1))
                .thenReturn(Optional.of(manutencao));

        Manutencao resultado =
                manutencaoService.cancelar(1);

        assertEquals(
                Manutencao.Status.CANCELADA,
                resultado.getStatus()
        );

        assertNotNull(
                resultado.getDataFim()
        );
    }

    @Test
    void deveImpedirCancelamentoDeManutencaoFinalizada() {

        Manutencao manutencao = umaManutencao();

        manutencao.finalizar(
                new BigDecimal("100.00"),
                LocalDate.now()
        );

        when(manutencaoRepository.findById(1))
                .thenReturn(Optional.of(manutencao));

        assertThrows(
                IllegalStateException.class,
                () -> manutencaoService.cancelar(1)
        );
    }

    @Test
    void deveImpedirExclusaoDeManutencaoEmAndamento() {

        Manutencao manutencao = umaManutencao();

        when(manutencaoRepository.findById(1))
                .thenReturn(Optional.of(manutencao));

        assertThrows(
                IllegalStateException.class,
                () -> manutencaoService.excluir(1)
        );

        verify(
                manutencaoRepository,
                never()
        ).delete(any());
    }

    @Test
    void deveExcluirManutencaoConcluida() {

        Manutencao manutencao = umaManutencao();

        manutencao.finalizar(
                new BigDecimal("500.00"),
                LocalDate.now()
        );

        when(manutencaoRepository.findById(1))
                .thenReturn(Optional.of(manutencao));

        manutencaoService.excluir(1);

        verify(manutencaoRepository)
                .delete(manutencao);
    }

    @Test
    void deveBuscarManutencaoAtiva() {

        Manutencao manutencao = umaManutencao();

        when(manutencaoRepository.findById(1))
                .thenReturn(Optional.of(manutencao));

        Manutencao resultado = manutencaoService.buscarAtivaPorId(1);

        assertSame(
                manutencao,
                resultado
        );
    }

    @Test
    void deveBuscarManutencaoEncerrada() {

        Manutencao manutencao =
                umaManutencao();

        manutencao.finalizar(
                new BigDecimal("100.00"),
                LocalDate.now()
        );

        when(manutencaoRepository.findById(1))
                .thenReturn(Optional.of(manutencao));

        Manutencao resultado = manutencaoService.buscarEncerradaPorId(1);

        assertSame(
                manutencao,
                resultado
        );
    }
}
