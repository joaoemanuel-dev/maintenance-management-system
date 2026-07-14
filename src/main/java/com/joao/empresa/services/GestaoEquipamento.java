package com.joao.empresa.services;

import com.joao.empresa.dao.EquipamentoDAO;
import com.joao.empresa.exceptions.EquipamentoJaCadastradoException;
import com.joao.empresa.exceptions.EquipamentoNaManutencaoException;
import com.joao.empresa.exceptions.EquipamentoNaoEncontradoException;
import com.joao.empresa.model.Equipamento;
import java.util.Collections;
import java.util.Set;

public class GestaoEquipamento {

    private EquipamentoDAO equipamentoDAO = new EquipamentoDAO();

    private GestaoManutencao gestaoManutencao;

    public GestaoEquipamento() {
        this.gestaoManutencao = new GestaoManutencao();
    }

    // Injeção de dependência: construtor recebe a referência para eu acessar
    // os métodos da manutenção (aqui, acessar as listas de equipamento em manutenção).
    public GestaoEquipamento(GestaoManutencao gestaoManutencao) {
        this.gestaoManutencao = gestaoManutencao;
    }

    public Equipamento buscarPorId(int id){
        Equipamento equipamento = equipamentoDAO.buscarPorId(id);

        if(equipamento == null){
            throw new EquipamentoNaoEncontradoException("Equipamento com ID " + id + " não encontrado.");
        }

        return equipamento;
    }

    public void cadastrarEquipamento(Equipamento equipamento){
        equipamentoDAO.salvar(equipamento);
    }

    public Set<Equipamento> listarEquipamentos(){
        return Collections.unmodifiableSet(equipamentos); // devolve uma visão somente-leitura do conjunto, impedindo alterações externas
    }

    public void atualizarEquipamento(Equipamento alterado){
        Equipamento existente = buscarPorId(alterado.getId());

        if (alterado.getNome() != null) {
            existente.setNome(alterado.getNome());
        }
        if (alterado.getCodigoPatrimonio() != null) {
            existente.setCodigoPatrimonio(alterado.getCodigoPatrimonio());
        }
        if (alterado.getDataAquisicao() != null) {
            existente.setDataAquisicao(alterado.getDataAquisicao());
        }
    }

    public void excluirEquipamento(int id) { //só exclui se não tiver manutenção aberta com ele

        Equipamento existente = buscarPorId(id); // vejo se existe, caso contrário já lança a exceção

        if (gestaoManutencao.existeManutencaoDoEquipamento(id)) {
            throw new EquipamentoNaManutencaoException(
                    "Não é possível excluir. Equipamento possui manutenção associada.");
        }

        equipamentos.remove(existente);
    }

}
