package com.joao.empresa.repository;

import com.joao.empresa.model.Manutencao;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Integer> {

    @Override
    @EntityGraph(attributePaths = {"equipamento", "tecnicoResponsavel"})
    Optional<Manutencao> findById(Integer id);

    // Busque todas as manutenções ordenadas pelo ID de forma crescente.
    @EntityGraph(attributePaths = {"equipamento", "tecnicoResponsavel"})
    List<Manutencao> findAllByOrderByIdAsc();

    @EntityGraph(attributePaths = {"equipamento", "tecnicoResponsavel"})
    // Busque manutenções onde manutencao.equipamento.id seja igual ao ID recebido
    List<Manutencao> findByEquipamento_IdOrderByIdAsc(Integer equipamentoId); // _ significa para navegar entre objetos

    @EntityGraph(attributePaths = {"equipamento", "tecnicoResponsavel"})
    // Me dê as manutenções cujo técnico responsável possui ID
    List<Manutencao> findByTecnicoResponsavel_IdOrderByIdAsc(Integer tecnicoId);

    @EntityGraph(attributePaths = {"equipamento", "tecnicoResponsavel"})
    // Listar manutenções por status
    List<Manutencao> findByStatusOrderByIdAsc(Manutencao.Status status);

    // ver se existe somente, não buscar nada
    boolean existsByEquipamento_Id(Integer equipamentoId);

    boolean existsByTecnicoResponsavel_Id(Integer tecnicoId);
}
