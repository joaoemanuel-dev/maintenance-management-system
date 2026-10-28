package com.joao.empresa.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "administrador")
@PrimaryKeyJoinColumn(
        name = "usuario_id",
        foreignKey = @ForeignKey(
                name = "fk_administrador_usuario"
        )
)
@DiscriminatorValue("ADMINISTRADOR")
public class Administrador extends Usuario {

    @Column(nullable = false, length = 100)
    private String departamento;

    public Administrador(String nome, String email, String departamento) {
        super(nome, email);
        this.departamento = departamento;
    }

    @Override
    public TipoUsuario getTipo() {
        return TipoUsuario.ADMINISTRADOR;
    }

    public void atualizarDados(String nome, String email, String departamento) {
        super.atualizarDados(nome, email);
        this.departamento = departamento;
    }

}
