package com.joao.empresa.repository;

import com.joao.empresa.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    boolean existsByCnpj(String cnpj); // existe empresa com esse cnpj no banco?

    boolean existsByCnpjAndIdNot(String cnpj, Integer id);
    // existe empresa com esse cnpj e cujo id seja diferente desse?
}
