package com.joao.empresa.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "gestor")
@PrimaryKeyJoinColumn(
        name = "usuario_id",
        foreignKey = @ForeignKey(
                name = "fk_gestor_usuario"
        )
)
@DiscriminatorValue("GESTOR")
public class Gestor extends Usuario {

    @Column(name = "area_responsavel", nullable = false, length = 100)
    private String areaResponsavel;

    public Gestor(
            String nome,
            String email,
            String areaResponsavel
    ) {
        super(nome, email);
        this.areaResponsavel = areaResponsavel;
    }

    @Override
    public TipoUsuario getTipo() {
        return TipoUsuario.GESTOR;
    }

    public void atualizarDados(String nome, String email, String areaResponsavel) {
        super.atualizarDados(nome, email);
        this.areaResponsavel = areaResponsavel;
    }

}
