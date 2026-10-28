package com.joao.empresa.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // pro JPA reconstruir
@Entity
@Table(name = "usuario",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_usuario_email", columnNames = "email")
        }
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(
        name = "tipo_usuario",
        discriminatorType = DiscriminatorType.STRING,
        length = 20
)
public abstract class Usuario extends Entidade {

    public enum TipoUsuario {

        ADMINISTRADOR("Administrador"),
        TECNICO("Técnico"),
        GESTOR("Gestor");

        private final String descricao;

        TipoUsuario(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 150)
    private String email;

    protected Usuario(
            String nome,
            String email
    ) {
        this.nome = nome;
        this.email = email;
    }

    // antigos setters
    public void atualizarDados(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public abstract TipoUsuario getTipo(); // cada uma das subclasses tem que implementar o seu

}