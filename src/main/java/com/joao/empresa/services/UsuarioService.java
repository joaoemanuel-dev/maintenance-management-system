package com.joao.empresa.services;

import com.joao.empresa.exceptions.UsuarioJaCadastradoException;
import com.joao.empresa.exceptions.UsuarioNaoEncontradoException;
import com.joao.empresa.model.Administrador;
import com.joao.empresa.model.Gestor;
import com.joao.empresa.model.Tecnico;
import com.joao.empresa.model.Usuario;
import com.joao.empresa.repository.ManutencaoRepository;
import com.joao.empresa.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<Usuario> listar() {
        return usuarioRepository.findAllByOrderByIdAsc();
    }

    private void validarEmailDisponivel(String email) {

        if (usuarioRepository.existsByEmail(email)) {
            throw new UsuarioJaCadastradoException(
                    "Já existe um usuário cadastrado com o e-mail " + email + "."
            );
        }
    }

    @Transactional
    public Administrador cadastrarAdministrador(
            String nome,
            String email,
            String departamento
    ) {

        validarEmailDisponivel(email);

        Administrador administrador =
                new Administrador(
                        nome,
                        email,
                        departamento
                );

        return usuarioRepository.save(administrador);
    }

    @Transactional
    public Gestor cadastrarGestor(
            String nome,
            String email,
            String areaResponsavel
    ) {

        validarEmailDisponivel(email);

        Gestor gestor =
                new Gestor(
                        nome,
                        email,
                        areaResponsavel
                );

        return usuarioRepository.save(gestor);
    }

    @Transactional
    public Tecnico cadastrarTecnico(
            String nome,
            String email,
            String especialidade
    ) {

        validarEmailDisponivel(email);

        Tecnico tecnico =
                new Tecnico(
                        nome,
                        email,
                        especialidade
                );

        return usuarioRepository.save(tecnico);
    }

    private void validarEmailDisponivelNaAtualizacao(String email, Integer id) {

        // se fosse buscar ia achar ele próprio
        if (usuarioRepository.existsByEmailAndIdNot(email, id)) {

            throw new UsuarioJaCadastradoException(
                    "Já existe outro usuário cadastrado com o e-mail " + email + "."
            );
        }
    }

    private Administrador buscarAdministrador(Integer id) {

        Usuario usuario = buscarPorId(id);

        if (!(usuario instanceof Administrador administrador)) {
            throw new IllegalArgumentException(
                    "O usuário de ID " + id + " não é um administrador."
            );
        }

        return administrador;
    }

    private Gestor buscarGestor(Integer id) {

        Usuario usuario = buscarPorId(id);

        if (!(usuario instanceof Gestor gestor)) {
            throw new IllegalArgumentException(
                    "O usuário de ID " + id + " não é um gestor."
            );
        }

        return gestor;
    }

    private Tecnico buscarTecnico(Integer id) {

        Usuario usuario = buscarPorId(id);

        if (!(usuario instanceof Tecnico tecnico)) {
            throw new IllegalArgumentException(
                    "O usuário de ID " + id + " não é um técnico."
            );
        }

        return tecnico;
    }

    private void validarId(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID do usuário deve ser um número positivo."
            );
        }
    }

}
