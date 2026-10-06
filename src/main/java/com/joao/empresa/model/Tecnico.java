package com.joao.empresa.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

// por causa da herança o técnico é persistido em duas tabelas

@Entity
@Table(name = "tecnico")

@PrimaryKeyJoinColumn(
        name = "usuario_id",
        foreignKey = @ForeignKey(
                name = "fk_tecnico_usuario"
        )
)

@DiscriminatorValue("TECNICO")
public class Tecnico extends Usuario {

    @Column(nullable = false, length = 100)
    private String especialidade;

    @OneToMany(mappedBy = "tecnicoResponsavel")
    private Set<Manutencao> manutencoesResponsaveis = new LinkedHashSet<>();

    public Tecnico(String nome, String email, String especialidade) {
        super(nome, email);
        this.especialidade = especialidade;
    }

    @Override
    public TipoUsuario getTipo() {
        return TipoUsuario.TECNICO;
    }

    public void atualizarDados(String nome, String email, String especialidade) {
        super.atualizarDados(nome, email);
        this.especialidade = especialidade;
    }

}
