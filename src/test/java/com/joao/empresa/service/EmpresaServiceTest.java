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

        Empresa empresa = new Empresa(
                "Empresa Teste",
                "12.345.678/0001-90",
                "Ouro Branco",
                "Industrial",
                Empresa.Status.ATIVADA
        );

        when(empresaRepository.findById(1))
                .thenReturn(Optional.of(empresa));

        Empresa resultado =
                empresaService.buscarPorId(1);

        assertSame(empresa, resultado);

        verify(empresaRepository).findById(1);
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

        when(empresaRepository.existsByCnpj(
                "12.345.678/0001-90"
        )).thenReturn(false);

        // Isso faz o mock do empresaRepository.save(...) retornar exatamente a mesma Empresa que
        // foi passada como argumento para o save
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
                .existsByCnpj(
                        "12.345.678/0001-90"
                );

        verify(empresaRepository)
                .save(any(Empresa.class));
    }

    @Test
    void deveImpedirCadastroComCnpjDuplicado() {

        when(empresaRepository.existsByCnpj(
                "12.345.678/0001-90"
        )).thenReturn(true);

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

        Empresa empresa = new Empresa(
                "Nome Antigo",
                "11.111.111/0001-11",
                "Endereço Antigo",
                "Industrial",
                Empresa.Status.ATIVADA
        );

        when(empresaRepository.findById(1))
                .thenReturn(Optional.of(empresa));

        when(empresaRepository
                .existsByCnpjAndIdNot(
                        "22.222.222/0001-22",
                        1
                ))
                .thenReturn(false);

        Empresa resultado =
                empresaService.atualizar(
                        1,
                        "Nome Novo",
                        "22.222.222/0001-22",
                        "Endereço Novo",
                        "Tecnologia",
                        Empresa.Status.ATIVADA
                );

        assertEquals(
                "Nome Novo",
                resultado.getNome()
        );

        assertEquals(
                "22.222.222/0001-22",
                resultado.getCnpj()
        );
    }

    @Test
    void deveImpedirExclusaoDeEmpresaComEquipamentos() {

        Empresa empresa = new Empresa(
                "Empresa Teste",
                "12.345.678/0001-90",
                "Ouro Branco",
                "Industrial",
                Empresa.Status.ATIVADA
        );

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
}
