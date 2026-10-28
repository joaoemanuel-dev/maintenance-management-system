package com.joao.empresa.controller;

import com.joao.empresa.dto.empresa.EmpresaRequest;
import com.joao.empresa.dto.empresa.EmpresaResponse;
import com.joao.empresa.model.Empresa;
import com.joao.empresa.service.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor //Inj Dep
public class EmpresaController {

    private final EmpresaService empresaService; //Inj Dep

    // Esse método é um endpoint GET que busca todas as empresas,
    //  transforma cada Empresa em EmpresaResponse e devolve a lista de DTOS no HTTP
    @GetMapping
    public ResponseEntity<List<EmpresaResponse>> listar() {

        List<EmpresaResponse> empresas =
                empresaService.listar()
                        .stream()
                        .map(EmpresaResponse::from)
                        .toList();

        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponse> buscarPorId(@PathVariable Integer id) {

        Empresa empresa = empresaService.buscarPorId(id);

        return ResponseEntity.ok(EmpresaResponse.from(empresa));
    }

    @PostMapping
    public ResponseEntity<EmpresaResponse> cadastrar(@Valid @RequestBody EmpresaRequest request) {

        Empresa empresa =
                empresaService.cadastrar(
                        request.nome(),
                        request.cnpj(),
                        request.endereco(),
                        request.segmento(),
                        request.status()
                );

        URI location =
                URI.create(
                        "/api/empresas/" + empresa.getId()
                );

        return ResponseEntity
                .created(location)
                .body(EmpresaResponse.from(empresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponse> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody EmpresaRequest request
    ) {

        Empresa empresa =
                empresaService.atualizar(
                        id,
                        request.nome(),
                        request.cnpj(),
                        request.endereco(),
                        request.segmento(),
                        request.status()
                );

        return ResponseEntity.ok(EmpresaResponse.from(empresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {

        empresaService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
