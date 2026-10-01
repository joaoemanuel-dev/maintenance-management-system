package com.joao.empresa.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(
        name = "empresa",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_empresa_cnpj",
                        columnNames = "cnpj"
                )
        }
)

public class Empresa extends Entidade {

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 18)
    private String cnpj;

    @Column(length = 255)
    private String endereco;

    @Column(length = 100)
    private String segmento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @OneToMany(mappedBy = "empresa")
    private Set<Equipamento> equipamentos = new HashSet<>();

    public Empresa(
            String nome,
            String cnpj,
            String endereco,
            String segmento,
            Status status
    ) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.segmento = segmento;
        this.status = status;
    }

    // substitui os setters que era aberto para alteração, e deixa um operação com significado
    public void atualizarDados(
            String nome,
            String cnpj,
            String endereco,
            String segmento,
            Status status
    ) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.segmento = segmento;
        this.status = status;
    }

    public enum Status {

        ATIVADA("Empresa ativada"),
        DESATIVADA("Empresa desativada");

        private final String descricao;

        Status(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
}