package com.joao.empresa.repository;

import com.joao.empresa.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Integer> {

    // Busque todas as manutenções ordenadas pelo ID de forma crescente.
    List<Manutencao> findAllByOrderByIdAsc();

    // Busque manutenções onde manutencao.equipamento.id seja igual ao ID recebido
    List<Manutencao> findByEquipamento_IdOrderByIdAsc(Integer equipamentoId); // _ significa para navegar entre objetos

    // Me dê as manutenções cujo técnico responsável possui ID
    List<Manutencao> findByTecnicoResponsavel_IdOrderByIdAsc(Integer tecnicoId);

    // Listar manutenções por status
    List<Manutencao> findByStatusOrderByIdAsc(Manutencao.Status status);

    // ver se existe somente, não buscar nada
    boolean existsByEquipamento_Id(Integer equipamentoId);

    boolean existsByTecnicoResponsavel_Id(Integer tecnicoId);
}
