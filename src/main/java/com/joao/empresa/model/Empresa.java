package com.joao.empresa.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Empresa extends Entidade {

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

        @Override
        public String toString() {
            return descricao;
        }
    }

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String segmento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Transient
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
}




