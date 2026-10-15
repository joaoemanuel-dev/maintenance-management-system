package com.joao.empresa.controller;

import com.joao.empresa.dto.usuario.AdministradorRequest;
import com.joao.empresa.dto.usuario.GestorRequest;
import com.joao.empresa.dto.usuario.TecnicoRequest;
import com.joao.empresa.dto.usuario.UsuarioResponse;
import com.joao.empresa.model.Administrador;
import com.joao.empresa.model.Gestor;
import com.joao.empresa.model.Tecnico;
import com.joao.empresa.model.Usuario;
import com.joao.empresa.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {

        List<UsuarioResponse> usuarios =
                usuarioService.listar()
                        .stream()
                        .map(UsuarioResponse::from)
                        .toList();

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(
            @PathVariable Integer id
    ) {

        Usuario usuario = usuarioService.buscarPorId(id);

        // converto a entidade em DTO (padrão do JSON) e retorno via http
        return ResponseEntity.ok(UsuarioResponse.from(usuario));
    }

    @PostMapping("/administradores")
    public ResponseEntity<UsuarioResponse> cadastrarAdministrador(
            @Valid @RequestBody AdministradorRequest request
    ) {

        Administrador administrador =
                usuarioService.cadastrarAdministrador(
                        request.nome(),
                        request.email(),
                        request.departamento()
                );

        URI location = URI.create("/api/usuarios/" + administrador.getId());

        return ResponseEntity.created(location).body(UsuarioResponse.from(administrador));

    }

    @PostMapping("/gestores")
    public ResponseEntity<UsuarioResponse> cadastrarGestor(
            @Valid @RequestBody GestorRequest request
    ) {

        Gestor gestor =
                usuarioService.cadastrarGestor(
                        request.nome(),
                        request.email(),
                        request.areaResponsavel()
                );

        URI location = URI.create("/api/usuarios/" + gestor.getId());

        return ResponseEntity
                .created(location).body(UsuarioResponse.from(gestor));
    }

    @PostMapping("/tecnicos")
    public ResponseEntity<UsuarioResponse> cadastrarTecnico(
            @Valid @RequestBody TecnicoRequest request
    ) {

        Tecnico tecnico =
                usuarioService.cadastrarTecnico(
                        request.nome(),
                        request.email(),
                        request.especialidade()
                );

        URI location = URI.create("/api/usuarios/" + tecnico.getId());

        return ResponseEntity.created(location).body(UsuarioResponse.from(tecnico));

    }

    public ResponseEntity<UsuarioResponse> atualizarAdministrador(
            @PathVariable Integer id,
            @Valid @RequestBody AdministradorRequest request
    ) {

        Administrador administrador =
                usuarioService.atualizarAdministrador(
                        id,
                        request.nome(),
                        request.email(),
                        request.departamento()
                );

        return ResponseEntity.ok(UsuarioResponse.from(administrador));
    }

}