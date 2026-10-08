package com.joao.empresa.services;

import com.joao.empresa.model.Equipamento;
import com.joao.empresa.repository.EmpresaRepository;
import com.joao.empresa.repository.EquipamentoRepository;
import com.joao.empresa.repository.ManutencaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final EmpresaRepository empresaRepository;
    private final ManutencaoRepository manutencaoRepository;

    public Equipamento buscarPorId(Integer id){



    }

    private void validarId(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID do equipamento deve ser um número positivo."
            );
        }
    }

}
