package com.joao.empresa.services;

import com.joao.empresa.dao.ManutencaoDAO;
import com.joao.empresa.exceptions.ManutencaoJaCadastradaException;
import com.joao.empresa.exceptions.ManutencaoNaoEncontradaException;
import com.joao.empresa.model.Manutencao;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class GestaoManutencao {

    ManutencaoDAO manutencaoDAO = new ManutencaoDAO();

    public Manutencao buscarPorId(int id) {

        Manutencao manutencao = manutencaoDAO.buscarPorId(id);

        if (manutencao == null) {
            throw new ManutencaoNaoEncontradaException("Manutenção com ID " + id + " não encontrada.");
        }

        return manutencao;
    }

    public Manutencao buscarAtivasPorId(int id) {

        Manutencao manutencao = manutencaoDAO.buscarPorId(id);

        if (manutencao.getStatus() != Manutencao.Status.ANDAMENTO){ // esta comparando somente com a String retornada
            throw new ManutencaoNaoEncontradaException(
                    "Não existe manutenção ativa com ID " + id + "."
            );
        }

        return manutencao;
    }

    public Manutencao buscarFinalizadasPorId(int id){

        Manutencao manutencao = manutencaoDAO.buscarPorId(id);

        if (manutencao.getStatus() == Manutencao.Status.ANDAMENTO){ // não finalizou, está em andamento
            throw new ManutencaoNaoEncontradaException(
                    "Não existe manutenção finalizada com ID " + id + "."
            );
        }

        return manutencao;
    }

    // o próprio banco não deixa cadastrar duplicado. Ele lança erro, assim, justamente para não
    // aparecer aquele erro feio na cara do usuário, a gente trata esse erro sem quebrar o programa
    public void cadastrarManutencao(Manutencao manutencao) {
        manutencaoDAO.salvar(manutencao);
    }

    public Set<Manutencao> listarManutencoesAtivas() {
        return manutencaoDAO.listarPorStatus(Manutencao.Status.ANDAMENTO);
    }

    public Set<Manutencao> listarManutencoesConcluidas() {
        return manutencaoDAO.listarPorStatus(Manutencao.Status.CONCLUIDA);
    }

    public Set<Manutencao> listarTodasManutencoes() {
        Set<Manutencao> todas = new LinkedHashSet<>();
        todas.addAll(manutencoesAtivas);
        todas.addAll(manutencoesFinalizadas);
        return Collections.unmodifiableSet(todas);
    }

    public void atualizarManutencao(Manutencao alterada){ // recebo objeto somente com o campos que quero alterar, os demais ficam null
        Manutencao existente = buscarAtivasPorId(alterada.getId()); //lança exceção

        if(alterada.getTipoManutencao() != null){
            existente.setTipoManutencao(alterada.getTipoManutencao());
        }
        if(alterada.getDataInicio() != null){
            existente.setDataInicio(alterada.getDataInicio());
        }
        if(alterada.getDescricao() != null){
            existente.setDescricao(alterada.getDescricao());
        }
        if(alterada.getTecnicoResponsavel() != null){
            existente.setTecnicoResponsavel(alterada.getTecnicoResponsavel());
        }
        if(alterada.getEquipamento() != null){
            existente.setEquipamento(alterada.getEquipamento());
        }
    }

    public void cancelarManutencao(int id){ // remove das manutenções ativas
        Manutencao mnt = buscarAtivasPorId(id);
        mnt.setStatus(Manutencao.Status.CANCELADA);
        manutencoesAtivas.remove(mnt);
        manutencoesFinalizadas.add(mnt);
    }

    public void finalizarManutencao(int id) { // encerra ativa e joga pra finalizadas
        Manutencao mnt = buscarAtivasPorId(id);
        mnt.setStatus(Manutencao.Status.CONCLUIDA);
        manutencoesAtivas.remove(mnt);
        manutencoesFinalizadas.add(mnt);
        mnt.getEquipamento().adicionarManutencao(mnt); // joga pro histórico do equipamento
        mnt.getTecnicoResponsavel().adicionarManutencao(mnt); // joga pro histórico do técnico
    }

    public void excluirManutencao(int id){ // excluir do sistema (finalizadas)
        Manutencao mnt = buscarFinalizadasPorId(id);
        manutencoesFinalizadas.remove(mnt);
    }

}