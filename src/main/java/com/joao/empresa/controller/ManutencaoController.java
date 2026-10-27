package com.joao.empresa.controller;

import com.joao.empresa.dto.manutencao.ManutencaoResponse;
import com.joao.empresa.service.ManutencaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
