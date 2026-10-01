package com.joao.empresa.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Entidade é a classe pai que fornece o ID e o mapeamento básico comum às entidades do sistema.

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // construtor sem argumentos o JPA precisa
public abstract class Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // o JPA coloca esse valor
    private Integer id;

}


