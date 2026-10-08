package com.joao.empresa.services;

import com.joao.empresa.exceptions.EquipamentoNaoEncontradoException;
import com.joao.empresa.exceptions.ManutencaoNaoEncontradaException;
import com.joao.empresa.exceptions.UsuarioNaoEncontradoException;
import com.joao.empresa.model.Manutencao;
import com.joao.empresa.model.Tecnico;
import com.joao.empresa.model.Usuario;
import com.joao.empresa.repository.EquipamentoRepository;
import com.joao.empresa.repository.ManutencaoRepository;
import com.joao.empresa.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManutencaoService {

    private final ManutencaoRepository manutencaoRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public Manutencao buscarPorId(Integer id) {

        validarId(id);

        return manutencaoRepository.findById(id)
                .orElseThrow(
                        () -> new ManutencaoNaoEncontradaException(
                                "Manutenção com ID " + id + " não encontrada."
                        )
                );
    }

    public Manutencao buscarAtivaPorId(Integer id) {

        Manutencao manutencao = buscarPorId(id);

        if (manutencao.getStatus() != Manutencao.Status.ANDAMENTO) {

            throw new ManutencaoNaoEncontradaException(
                    "Não existe manutenção ativa com ID "
                            + id + "."
            );
        }

        return manutencao;
    }

    public Manutencao buscarEncerradaPorId(Integer id) {

        Manutencao manutencao = buscarPorId(id);

        if (manutencao.getStatus() == Manutencao.Status.ANDAMENTO) {

            throw new ManutencaoNaoEncontradaException(
                    "Não existe manutenção encerrada com ID "
                            + id + "."
            );
        }

        return manutencao;
    }

    public List<Manutencao> listar() {
        return manutencaoRepository.findAllByOrderByIdAsc();
    }

    public List<Manutencao> listarAtivas() {
        return manutencaoRepository.findByStatusOrderByIdAsc(Manutencao.Status.ANDAMENTO);
    }

    public List<Manutencao> listarConcluidas() {
        return manutencaoRepository.findByStatusOrderByIdAsc(Manutencao.Status.CONCLUIDA);
    }

    public List<Manutencao> listarCanceladas() {
        return manutencaoRepository.findByStatusOrderByIdAsc(Manutencao.Status.CANCELADA);
    }

    public List<Manutencao> listarPorEquipamento(Integer equipamentoId) {

        validarId(equipamentoId);

        if (!equipamentoRepository.existsById(equipamentoId)) {
            throw new EquipamentoNaoEncontradoException(
                    "Equipamento com ID " + equipamentoId + " não encontrado."
            );
        }

        return manutencaoRepository.findByEquipamento_IdOrderByIdAsc(equipamentoId);

    }

    private Tecnico buscarTecnico(Integer tecnicoId) {

        validarId(tecnicoId);

        Usuario usuario = usuarioRepository
                .findById(tecnicoId)
                .orElseThrow(
                        () -> new UsuarioNaoEncontradoException(
                                "Usuário com ID " + tecnicoId + " não encontrado."
                        )
                );

        if (!(usuario instanceof Tecnico tecnico)) {
            throw new IllegalArgumentException(
                    "O usuário de ID " + tecnicoId + " não é um técnico."
            );
        }

        return tecnico;
    }

    private void validarId(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID deve ser um número positivo."
            );
        }
    }

}
