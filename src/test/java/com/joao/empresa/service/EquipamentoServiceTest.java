package com.joao.empresa.service;

import com.joao.empresa.exceptions.EmpresaNaoEncontradaException;
import com.joao.empresa.exceptions.EntidadeEmUsoException;
import com.joao.empresa.exceptions.EquipamentoJaCadastradoException;
import com.joao.empresa.exceptions.EquipamentoNaoEncontradoException;
import com.joao.empresa.model.Empresa;
import com.joao.empresa.model.Equipamento;
import com.joao.empresa.repository.EmpresaRepository;
import com.joao.empresa.repository.EquipamentoRepository;
import com.joao.empresa.repository.ManutencaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.joao.empresa.builders.EmpresaBuilder.umaEmpresa;
import static com.joao.empresa.builders.EquipamentoBuilder.umEquipamento;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipamentoServiceTest {

    @Mock
    private EquipamentoRepository equipamentoRepository;

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private ManutencaoRepository manutencaoRepository;

    @InjectMocks
    private EquipamentoService equipamentoService;

    @Test
    void deveBuscarEquipamentoPorId() {

        Equipamento equipamento = umEquipamento();

        when(equipamentoRepository.findById(1))
                .thenReturn(Optional.of(equipamento));

        Equipamento resultado = equipamentoService.buscarPorId(1);

        assertSame(equipamento, resultado);
    }

    @Test
    void deveLancarExcecaoQuandoEquipamentoNaoExistir() {

        when(equipamentoRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                EquipamentoNaoEncontradoException.class,
                () -> equipamentoService.buscarPorId(999)
        );
    }

    @Test
    void deveCadastrarEquipamento() {

        Empresa empresa = umaEmpresa();

        when(equipamentoRepository
                .existsByCodigoPatrimonio("PAT-001"))
                .thenReturn(false);

        when(empresaRepository.findById(1))
                .thenReturn(Optional.of(empresa));

        when(equipamentoRepository.save(any(Equipamento.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0)
                );

        Equipamento resultado =
                equipamentoService.cadastrar(
                        "Compressor",
                        "PAT-001",
                        LocalDate.of(2026, 1, 10),
                        1
                );

        assertEquals(
                "Compressor",
                resultado.getNome()
        );

        assertEquals(
                "PAT-001",
                resultado.getCodigoPatrimonio()
        );

        assertSame(
                empresa,
                resultado.getEmpresa()
        );
    }

    @Test
    void deveImpedirCadastroComCodigoDuplicado() {

        when(equipamentoRepository
                .existsByCodigoPatrimonio("PAT-001"))
                .thenReturn(true);

        assertThrows(
                EquipamentoJaCadastradoException.class,
                () -> equipamentoService.cadastrar(
                        "Compressor",
                        "PAT-001",
                        LocalDate.now(),
                        1
                )
        );

        verify(
                equipamentoRepository,
                never()
        ).save(any());
    }

    @Test
    void deveImpedirCadastroQuandoEmpresaNaoExistir() {

        when(equipamentoRepository
                .existsByCodigoPatrimonio("PAT-001"))
                .thenReturn(false);

        when(empresaRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                EmpresaNaoEncontradaException.class,
                () -> equipamentoService.cadastrar(
                        "Compressor",
                        "PAT-001",
                        LocalDate.now(),
                        999
                )
        );

        verify(
                equipamentoRepository,
                never()
        ).save(any());
    }

    @Test
    void deveAtualizarEquipamento() {

        Equipamento equipamento =
                umEquipamento();

        when(equipamentoRepository.findById(1))
                .thenReturn(Optional.of(equipamento));

        when(equipamentoRepository
                .existsByCodigoPatrimonioAndIdNot(
                        "PAT-002",
                        1
                ))
                .thenReturn(false);

        Equipamento resultado =
                equipamentoService.atualizar(
                        1,
                        "Compressor Atualizado",
                        "PAT-002",
                        LocalDate.of(2026, 5, 20)
                );

        assertEquals(
                "Compressor Atualizado",
                resultado.getNome()
        );

        assertEquals(
                "PAT-002",
                resultado.getCodigoPatrimonio()
        );
    }

    @Test
    void deveImpedirExclusaoQuandoPossuirManutencao() {

        Equipamento equipamento =
                umEquipamento();

        when(equipamentoRepository.findById(1))
                .thenReturn(Optional.of(equipamento));

        when(manutencaoRepository
                .existsByEquipamento_Id(1))
                .thenReturn(true);

        assertThrows(
                EntidadeEmUsoException.class,
                () -> equipamentoService.excluir(1)
        );

        verify(
                equipamentoRepository,
                never()
        ).delete(any());
    }

    @Test
    void deveExcluirEquipamentoSemManutencao() {

        Equipamento equipamento =
                umEquipamento();

        when(equipamentoRepository.findById(1))
                .thenReturn(Optional.of(equipamento));

        when(manutencaoRepository
                .existsByEquipamento_Id(1))
                .thenReturn(false);

        equipamentoService.excluir(1);

        verify(equipamentoRepository)
                .delete(equipamento);
    }
}