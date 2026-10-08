package com.joao.empresa.services;

import com.joao.empresa.exceptions.EmpresaNaoEncontradaException;
import com.joao.empresa.exceptions.EntidadeEmUsoException;
import com.joao.empresa.exceptions.EquipamentoJaCadastradoException;
import com.joao.empresa.exceptions.EquipamentoNaoEncontradoException;
import com.joao.empresa.model.Empresa;
import com.joao.empresa.model.Equipamento;
import com.joao.empresa.repository.EmpresaRepository;
import com.joao.empresa.repository.EquipamentoRepository;
import com.joao.empresa.repository.ManutencaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final EmpresaRepository empresaRepository;
    private final ManutencaoRepository manutencaoRepository;

    public Equipamento buscarPorId(Integer id){

        validarId(id);

        return equipamentoRepository.findById(id)
                .orElseThrow( // se tiver um valor devolve ele, se não lança a exceção
                        () -> new EquipamentoNaoEncontradoException(
                                "Equipamento com ID "
                                        + id
                                        + " não encontrado."
                        )
                );
    }

    private void validarId(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID do equipamento deve ser um número positivo."
            );
        }
    }

    public List<Equipamento> listar (){

        return equipamentoRepository.findAll(
                Sort.by(Sort.Direction.ASC, "id")
        );

    }

    @Transactional
    public Equipamento cadastrar(
            String nome,
            String codigoPatrimonio,
            LocalDate dataAquisicao,
            Integer empresaId
    ){

        validarId(empresaId);

        if(equipamentoRepository.existsByCodigoPatrimonio(codigoPatrimonio){

            throw new EquipamentoJaCadastradoException(
                    "Já existe um equipamento cadastrado "
                            + "com o código de patrimônio "
                            + codigoPatrimonio
                            + "."
            );
        }

        // eu busco a empresa e associo ela ao equipamento
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(
                        () -> new EmpresaNaoEncontradaException(
                                "Empresa com ID " + empresaId + " não encontrada."
                        )
                );

        Equipamento equipamento = new Equipamento(
                nome,
                codigoPatrimonio,
                dataAquisicao,
                empresa
        );

        return equipamentoRepository.save(equipamento);

    }

    @Transactional
    public Equipamento atualizar(
            Integer id,
            String nome,
            String codigoPatrimonio,
            LocalDate dataAquisicao
    ) {

        Equipamento equipamento = buscarPorId(id);

        // caso vier um código pra atualizar só que ele já existe
        if (equipamentoRepository.existsByCodigoPatrimonioAndIdNot(codigoPatrimonio, id)) {

            throw new EquipamentoJaCadastradoException(
                    "Já existe outro equipamento cadastrado "
                            + "com o código de patrimônio "
                            + codigoPatrimonio + "."
            );
        }

        equipamento.atualizarDados(nome, codigoPatrimonio, dataAquisicao);

        return equipamento;
    }

    @Transactional
    public void excluir(Integer id) {

        Equipamento equipamento = buscarPorId(id);

        if (manutencaoRepository.existsByEquipamento_Id(id)) {

            throw new EntidadeEmUsoException(
                    "Não é possível excluir o equipamento de ID " + id
                    + " porque existem manutenções associadas a ele."
            );
        }

        equipamentoRepository.delete(equipamento);
    }

}
