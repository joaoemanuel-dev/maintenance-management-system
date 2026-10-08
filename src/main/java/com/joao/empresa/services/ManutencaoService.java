package com.joao.empresa.services;

import com.joao.empresa.exceptions.ManutencaoNaoEncontradaException;
import com.joao.empresa.model.Manutencao;
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

    private void validarId(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID deve ser um número positivo."
            );
        }
    }

}
