package com.joao.empresa.repository;

import com.joao.empresa.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer> {

    boolean existsByCodigoPatrimonio(String codigoPatrimonio);

    boolean existsByCodigoPatrimonioAndIdNot(String codigoPatrimonio, Integer id);

    boolean existsByEmpresaId(Integer empresaId);

}
