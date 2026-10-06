package com.joao.empresa.repository;

import com.joao.empresa.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// o JPA trabalha só com usuário, mas ele conhece todos os subtipos -> ele mapeia tudo
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // todos tratados como usuário, mas preservando seu tipo concreto
    List<Usuario> findAllByOrderByIdAsc();

    Optional<Usuario> findByEmail(String email); // isso é pro Spring Security

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Integer id);

}
