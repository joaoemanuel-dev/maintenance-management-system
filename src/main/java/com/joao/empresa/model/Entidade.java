package com.joao.empresa.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // JPA precisa de um construtor vazio
    protected Entidade() {

    }

    // Métodos de validação do id gerado pelo banco

    protected Entidade(Integer id) {
        if (id != null) {
            validarIdPositivo(id);
        }

        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void definirId(Integer id) {
        validarIdPositivo(id);

        if (this.id != null) {
            throw new IllegalStateException(
                    "A entidade já possui um ID."
            );
        }

        this.id = id;
    }

    private static void validarIdPositivo(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID deve ser um número positivo."
            );
        }
    }

    @Override
    public final boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        Entidade outraEntidade = (Entidade) objeto;

        return id != null && id.equals(outraEntidade.id);
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Entidade{" +
                "id=" + id +
                '}';
    }
}
