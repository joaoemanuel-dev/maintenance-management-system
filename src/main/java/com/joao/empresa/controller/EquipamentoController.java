package com.joao.empresa.controller;

import com.joao.empresa.dto.equipamento.EquipamentoResponse;
import com.joao.empresa.service.EquipamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/equipamentos")
@RequiredArgsConstructor
public class EquipamentoController {

    private final EquipamentoService equipamentoService;

    @GetMapping
    public ResponseEntity<List<EquipamentoResponse>> listar() {

        List<EquipamentoResponse> equipamentos =
                equipamentoService.listar()
                        .stream()
                        .map(EquipamentoResponse::from)
                        .toList(); // passo por cada um e transformo em uma response

        return ResponseEntity.ok(equipamentos);
    }


}
