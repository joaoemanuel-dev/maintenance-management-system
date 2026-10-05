package com.joao.empresa.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "equipamento",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_equipamento_codigo_patrimonio",
                        columnNames = "codigo_patrimonio"
                )
        }
)
public class Equipamento extends Entidade {

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(name = "codigo_patrimonio", nullable = false, length = 100)
    private String codigoPatrimonio;

    @Column(name = "data_aquisicao", nullable = false)
    private LocalDate dataAquisicao;

    // Os relacionamento ficam expostos aqui
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false, // coluna que vai fazer a ligação
            foreignKey = @ForeignKey(name = "fk_equipamento_empresa") // só o nome da fk criada pelo bd
    )
    private Empresa empresa;

    @OneToMany(mappedBy = "equipamento") // equipamento existirá em "manutencao"
    private Set<Manutencao> historicoManutencoes = new LinkedHashSet<>();

    public Equipamento(
            String nome,
            String codigoPatrimonio,
            LocalDate dataAquisicao,
            Empresa empresa
    ) {
        this.nome = nome;
        this.codigoPatrimonio = codigoPatrimonio;
        this.dataAquisicao = dataAquisicao;
        this.empresa = empresa;
    }

    // aqueles setters é substituída para ter mais segurança e controle
    public void atualizarDados(
            String nome,
            String codigoPatrimonio,
            LocalDate dataAquisicao
    ) {
        this.nome = nome;
        this.codigoPatrimonio = codigoPatrimonio;
        this.dataAquisicao = dataAquisicao;
    }

    void adicionarManutencao(Manutencao manutencao) {
        historicoManutencoes.add(manutencao);
    }
}