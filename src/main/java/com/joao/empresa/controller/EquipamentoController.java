package com.joao.empresa.controller;

import com.joao.empresa.dto.equipamento.EquipamentoRequest;
import com.joao.empresa.dto.equipamento.EquipamentoResponse;
import com.joao.empresa.model.Equipamento;
import com.joao.empresa.service.EquipamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoResponse> buscarPorId(@PathVariable Integer id) {

        Equipamento equipamento = equipamentoService.buscarPorId(id);

        return ResponseEntity.ok(EquipamentoResponse.from(equipamento));
    }

    @PostMapping
    public ResponseEntity<EquipamentoResponse> cadastrar(
            @Valid @RequestBody EquipamentoRequest request
    ) {

        Equipamento equipamento =
                equipamentoService.cadastrar(
                        request.nome(),
                        request.codigoPatrimonio(),
                        request.dataAquisicao(),
                        request.empresaId()
                );

        // crio uma nova URI pode foi adicionado um novo recurso
        URI location = URI.create("/api/equipamentos/" + equipamento.getId());

        return ResponseEntity
                .created(location)
                .body(EquipamentoResponse.from(equipamento));

    }


}
