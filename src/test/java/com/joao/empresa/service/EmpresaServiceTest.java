package com.joao.empresa.service;

import com.joao.empresa.exceptions.EmpresaJaCadastradaException;
import com.joao.empresa.exceptions.EmpresaNaoEncontradaException;
import com.joao.empresa.exceptions.EntidadeEmUsoException;
import com.joao.empresa.model.Empresa;
import com.joao.empresa.repository.EmpresaRepository;
import com.joao.empresa.repository.EquipamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.joao.empresa.builders.EmpresaBuilder.umaEmpresa;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private EquipamentoRepository equipamentoRepository;

    @InjectMocks
    private EmpresaService empresaService;

    @Test
    void deveBuscarEmpresaPorId() {

        Empresa empresa = umaEmpresa();

        when(empresaRepository.findById(1))
                .thenReturn(Optional.of(empresa));

        Empresa resultado =
                empresaService.buscarPorId(1);

        assertSame(empresa, resultado);

        verify(empresaRepository)
                .findById(1);
    }

    @Test
    void deveLancarExcecaoQuandoEmpresaNaoExistir() {

        when(empresaRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                EmpresaNaoEncontradaException.class,
                () -> empresaService.buscarPorId(999)
        );
    }

    @Test
    void deveCadastrarEmpresa() {

        when(empresaRepository
                .existsByCnpj("12.345.678/0001-90"))
                .thenReturn(false);

        when(empresaRepository.save(any(Empresa.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0)
                );

        Empresa resultado =
                empresaService.cadastrar(
                        "Empresa Teste",
                        "12.345.678/0001-90",
                        "Ouro Branco",
                        "Industrial",
                        Empresa.Status.ATIVADA
                );

        assertEquals(
                "Empresa Teste",
                resultado.getNome()
        );

        assertEquals(
                "12.345.678/0001-90",
                resultado.getCnpj()
        );

        verify(empresaRepository)
                .save(any(Empresa.class));
    }

    @Test
    void deveImpedirCadastroComCnpjDuplicado() {

        when(empresaRepository
                .existsByCnpj("12.345.678/0001-90"))
                .thenReturn(true);

        assertThrows(
                EmpresaJaCadastradaException.class,
                () -> empresaService.cadastrar(
                        "Empresa Teste",
                        "12.345.678/0001-90",
                        "Ouro Branco",
                        "Industrial",
                        Empresa.Status.ATIVADA
                )
        );

        verify(
                empresaRepository,
                never()
        ).save(any(Empresa.class));
    }

    @Test
    void deveAtualizarEmpresa() {

        Empresa empresa = umaEmpresa();

        when(empresaRepository.findById(1))
                .thenReturn(Optional.of(empresa));

        when(empresaRepository
                .existsByCnpjAndIdNot(
                        "98.765.432/0001-10",
                        1
                ))
                .thenReturn(false);

        Empresa resultado =
                empresaService.atualizar(
                        1,
                        "Empresa Atualizada",
                        "98.765.432/0001-10",
                        "Belo Horizonte",
                        "Tecnologia",
                        Empresa.Status.ATIVADA
                );

        assertEquals(
                "Empresa Atualizada",
                resultado.getNome()
        );

        assertEquals(
                "98.765.432/0001-10",
                resultado.getCnpj()
        );
    }

    @Test
    void deveImpedirExclusaoDeEmpresaComEquipamentos() {

        Empresa empresa = umaEmpresa();

        when(empresaRepository.findById(1))
                .thenReturn(Optional.of(empresa));

        when(equipamentoRepository
                .existsByEmpresaId(1))
                .thenReturn(true);

        assertThrows(
                EntidadeEmUsoException.class,
                () -> empresaService.excluir(1)
        );

        verify(
                empresaRepository,
                never()
        ).delete(any(Empresa.class));
    }

    @Test
    void deveExcluirEmpresaSemEquipamentos() {

        Empresa empresa = umaEmpresa();

        when(empresaRepository.findById(1))
                .thenReturn(Optional.of(empresa));

        when(equipamentoRepository
                .existsByEmpresaId(1))
                .thenReturn(false);

        empresaService.excluir(1);

        verify(empresaRepository)
                .delete(empresa);
    }
}