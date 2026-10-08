package com.joao.empresa.services;

import com.joao.empresa.exceptions.UsuarioNaoEncontradoException;
import com.joao.empresa.model.Usuario;
import com.joao.empresa.repository.ManutencaoRepository;
import com.joao.empresa.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ManutencaoRepository manutencaoRepository;

    public Usuario buscarPorId(Integer id) {

        validarId(id);

        return usuarioRepository.findById(id)
                .orElseThrow(
                        () -> new UsuarioNaoEncontradoException(
                                "Usuário com ID " + id + " não encontrado."
                        )
                );
    }

    public Usuario buscarPorEmail(String email) {

        return usuarioRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsuarioNaoEncontradoException(
                                "Usuário com e-mail " + email + " não encontrado."
                        )
                );
    }



    private void validarId(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID do usuário deve ser um número positivo."
            );
        }
    }




}
