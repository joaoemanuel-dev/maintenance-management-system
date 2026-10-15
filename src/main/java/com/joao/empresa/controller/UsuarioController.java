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



}