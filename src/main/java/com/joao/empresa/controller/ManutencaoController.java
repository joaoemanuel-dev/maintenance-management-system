package com.joao.empresa.controller;

import com.joao.empresa.dto.manutencao.ManutencaoRequest;
import com.joao.empresa.dto.manutencao.ManutencaoResponse;
import com.joao.empresa.model.Manutencao;
import com.joao.empresa.service.ManutencaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/manutencoes")
@RequiredArgsConstructor
public class ManutencaoController {

    private final ManutencaoService manutencaoService;

    @GetMapping
    public ResponseEntity<List<ManutencaoResponse>> listar() {

        List<ManutencaoResponse> manutencoes =
                manutencaoService.listar()
                        .stream()
                        .map(ManutencaoResponse::from)
                        .toList();

        // vou devolver uma resposta http 200 ok e colocar a lista de manutencoes dentro do corpo da resposta.
        return ResponseEntity.ok(manutencoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManutencaoResponse> buscarPorId(@PathVariable Integer id){ // espero http com o body dentro

        Manutencao manutencao = manutencaoService.buscarPorId(id);

        return ResponseEntity.ok(ManutencaoResponse.from(manutencao));

    }

    @GetMapping("/ativas")
    public ResponseEntity<List<ManutencaoResponse>> listarAtivas() {

        List<ManutencaoResponse> manutencoes =
                manutencaoService.listarAtivas()
                        .stream()
                        .map(ManutencaoResponse::from)
                        .toList();

        return ResponseEntity.ok(manutencoes);
    }

    @GetMapping("/concluidas")
    public ResponseEntity<List<ManutencaoResponse>> listarConcluidas() {

        List<ManutencaoResponse> manutencoes =
                manutencaoService.listarConcluidas()
                        .stream()
                        .map(ManutencaoResponse::from)
                        .toList();

        return ResponseEntity.ok(manutencoes);
    }

    @GetMapping("/canceladas")
    public ResponseEntity<List<ManutencaoResponse>> listarCanceladas() {

        List<ManutencaoResponse> manutencoes =
                manutencaoService.listarCanceladas()
                        .stream()
                        .map(ManutencaoResponse::from)
                        .toList();

        return ResponseEntity.ok(manutencoes);
    }

    @GetMapping("/equipamento/{equipamentoId}")
    public ResponseEntity<List<ManutencaoResponse>> listarPorEquipamento(
            @PathVariable Integer equipamentoId
    ) {

        List<ManutencaoResponse> manutencoes =
                manutencaoService
                        .listarPorEquipamento(equipamentoId)
                        .stream()
                        .map(ManutencaoResponse::from)
                        .toList();

        return ResponseEntity.ok(manutencoes);
    }

    @GetMapping("/tecnico/{tecnicoId}")
    public ResponseEntity<List<ManutencaoResponse>> listarPorTecnico(
            @PathVariable Integer tecnicoId
    ) {

        List<ManutencaoResponse> manutencoes =
                manutencaoService
                        .listarPorTecnico(tecnicoId)
                        .stream()
                        .map(ManutencaoResponse::from)
                        .toList();

        return ResponseEntity.ok(manutencoes);
    }

    @PostMapping
    public ResponseEntity<ManutencaoResponse> cadastrar(
            @Valid @RequestBody ManutencaoRequest request
    ){

        Manutencao manutencao =
                manutencaoService.cadastrar(
                        request.tipoManutencao(),
                        request.descricao(),
                        request.dataInicio(),
                        request.equipamentoId(),
                        request.tecnicoId()
                );

        URI location = URI.create("/api/manutencoes/" + manutencao.getId());

        // Retorna um HTTP 201 Created, informa onde esse novo recurso está no header, e manda no body o
        // JSON da manutenção criada.

        return ResponseEntity
                .created(location)
                .body(ManutencaoResponse.from(manutencao));

    }

}
