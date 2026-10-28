package com.joao.empresa.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

// os métodos que permaneceram nessa classe são regras de negócio, não tinha haver com jdbc

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "manutencao")
public class Manutencao extends Entidade {

    public enum TipoManutencao {

        PREVENTIVA("Manutenção preventiva"),
        CORRETIVA("Manutenção corretiva");

        private final String descricao;

        TipoManutencao(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    public enum Status {

        ANDAMENTO("Manutenção em andamento"),
        CONCLUIDA("Manutenção concluída"),
        CANCELADA("Manutenção cancelada");

        private final String descricao;

        Status(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_manutencao", nullable = false, length = 20)
    private TipoManutencao tipoManutencao;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal custo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    // tem que colocar dos dois lados o relacionamento (1 pra n // n pra 1)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "equipamento_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_manutencao_equipamento"
            )
    )
    private Equipamento equipamento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "tecnico_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_manutencao_tecnico"
            )
    )
    private Tecnico tecnicoResponsavel;

    public Manutencao(
            TipoManutencao tipoManutencao,
            String descricao,
            LocalDate dataInicio,
            Equipamento equipamento,
            Tecnico tecnicoResponsavel
    ) {

        validarDadosBasicos(
                tipoManutencao,
                descricao,
                dataInicio,
                equipamento,
                tecnicoResponsavel
        );

        this.tipoManutencao = tipoManutencao;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.equipamento = equipamento;
        this.tecnicoResponsavel = tecnicoResponsavel;

        this.custo = BigDecimal.ZERO.setScale(2);
        this.status = Status.ANDAMENTO;
        this.dataFim = null;
    }

    public void atualizarDados(
            TipoManutencao tipoManutencao,
            String descricao,
            LocalDate dataInicio,
            Equipamento equipamento,
            Tecnico tecnicoResponsavel
    ) {

        exigirEmAndamento();

        validarDadosBasicos( // esse aqui é pra ver se é nulo
                tipoManutencao,
                descricao,
                dataInicio,
                equipamento,
                tecnicoResponsavel
        );

        this.tipoManutencao = tipoManutencao;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.equipamento = equipamento;
        this.tecnicoResponsavel = tecnicoResponsavel;
    }

    // em vez do service fazer tudo, chama aqui pq é exclusivo da classe manutenção
    public void finalizar(BigDecimal custo, LocalDate dataConclusao) {

        exigirEmAndamento();

        validarCusto(custo);
        validarDataFim(dataConclusao);

        this.custo = custo;
        this.dataFim = dataConclusao;
        this.status = Status.CONCLUIDA;
    }

    public void cancelar(LocalDate dataCancelamento) {

        exigirEmAndamento();
        validarDataFim(dataCancelamento);

        this.dataFim = dataCancelamento;
        this.status = Status.CANCELADA;
    }

    private void exigirEmAndamento() {

        if (status != Status.ANDAMENTO) {
            throw new IllegalStateException(
                    "Somente uma manutenção em andamento pode ser alterada."
            );
        }
    }

    private static void validarDadosBasicos(
            TipoManutencao tipoManutencao,
            String descricao,
            LocalDate dataInicio,
            Equipamento equipamento,
            Tecnico tecnicoResponsavel
    ) {

        if (tipoManutencao == null) {
            throw new IllegalArgumentException(
                    "O tipo da manutenção é obrigatório."
            );
        }

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException(
                    "A descrição da manutenção é obrigatória."
            );
        }

        if (dataInicio == null) {
            throw new IllegalArgumentException(
                    "A data inicial é obrigatória."
            );
        }

        if (equipamento == null) {
            throw new IllegalArgumentException(
                    "O equipamento é obrigatório."
            );
        }

        if (tecnicoResponsavel == null) {
            throw new IllegalArgumentException(
                    "O técnico responsável é obrigatório."
            );
        }
    }

    private static void validarCusto(BigDecimal custo) {

        if (custo == null) {
            throw new IllegalArgumentException(
                    "O custo é obrigatório."
            );
        }

        if (custo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "O custo não pode ser negativo."
            );
        }
    }

    private void validarDataFim(LocalDate dataFim) {

        if (dataFim == null) {
            throw new IllegalArgumentException(
                    "A data final é obrigatória."
            );
        }

        if (dataFim.isBefore(dataInicio)) {
            throw new IllegalArgumentException(
                    "A data final não pode ser anterior à data inicial."
            );
        }
    }
}