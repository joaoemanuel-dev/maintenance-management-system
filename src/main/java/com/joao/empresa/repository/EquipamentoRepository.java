package com.joao.empresa.repository;

import com.joao.empresa.model.Equipamento;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer> {

    boolean existsByCodigoPatrimonio(String codigoPatrimonio);

    boolean existsByCodigoPatrimonioAndIdNot(String codigoPatrimonio, Integer id);

    // verifica se existe algum equipamento associado a essa empresa
    boolean existsByEmpresaId(Integer empresaId);

    //busca o equipamento, busca a empresa junto e controller consegue acessar empresa.nome
    @Override
    @EntityGraph(attributePaths = "empresa") // vai fazer um join pegando todos
    List<Equipamento> findAll(Sort sort);

    @Override
    @EntityGraph(attributePaths = "empresa")
    Optional<Equipamento> findById(Integer id);

}



