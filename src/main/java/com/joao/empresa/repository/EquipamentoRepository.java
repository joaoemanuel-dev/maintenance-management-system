package com.joao.empresa.repository;

import com.joao.empresa.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer> {

    boolean existsByCodigoPatrimonio(String codigoPatrimonio);

    boolean existsByCodigoPatrimonioAndIdNot(String codigoPatrimonio, Integer id);

    // verifica se existe algum equipamento associado a essa empresa
    boolean existsByEmpresaId(Integer empresaId);

}
