package com.joao.empresa.dto.empresa;

import com.joao.empresa.model.Empresa;

// Essa classe representa o JSON que sai da API
public record EmpresaResponse(

        Integer id,
        String nome,
        String cnpj,
        String endereco,
        String segmento,
        Empresa.Status status

) {

    // Transforma entidade JPA Empresa -> DTO EmpresaResponse
    public static EmpresaResponse from(Empresa empresa) {

        return new EmpresaResponse(
                empresa.getId(),
                empresa.getNome(),
                empresa.getCnpj(),
                empresa.getEndereco(),
                empresa.getSegmento(),
                empresa.getStatus()
        );
    }
}
