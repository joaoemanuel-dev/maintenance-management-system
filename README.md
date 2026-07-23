# 🔧 Maintenance Management System

Sistema de gestão de manutenção desenvolvido em **Java 17**, com persistência de dados utilizando **JDBC e MySQL**.

O projeto está em desenvolvimento e faz parte do meu processo de aprendizado em Back-End Java. Nesta etapa, o foco está na modelagem do domínio, na separação de responsabilidades, no acesso manual ao banco de dados e na compreensão dos fundamentos que serão utilizados futuramente em uma migração para Spring Boot.

> **Status atual:** versão JDBC em desenvolvimento. O projeto ainda não possui interface gráfica nem API REST.

---

## 📌 Sobre o projeto

O sistema representa o domínio de manutenção de equipamentos de empresas.

A aplicação possui estruturas para gerenciar:

* empresas;
* equipamentos vinculados a empresas;
* usuários com diferentes especializações;
* manutenções preventivas e corretivas;
* técnicos responsáveis pelas manutenções;
* estados de uma manutenção: em andamento, concluída ou cancelada.

O objetivo desta versão é praticar Java e banco de dados sem utilizar frameworks de persistência, implementando manualmente as consultas SQL, o mapeamento dos resultados e a comunicação entre as camadas da aplicação.

---

## ✅ Funcionalidades implementadas

### Empresas

* cadastro;
* busca por ID;
* listagem;
* atualização;
* exclusão;
* status de empresa ativada ou desativada.

### Equipamentos

* cadastro vinculado a uma empresa;
* busca por ID;
* listagem;
* atualização;
* exclusão;
* verificação de manutenção associada antes da exclusão.

### Usuários

* cadastro;
* busca por ID;
* listagem;
* atualização;
* exclusão;
* modelagem com herança e polimorfismo;
* persistência dos dados gerais e específicos de cada tipo de usuário.

Tipos de usuário existentes:

* `Administrador`, com departamento e nível de acesso;
* `Gestor`, com área responsável;
* `Tecnico`, com especialidade.

### Manutenções

* cadastro de manutenção preventiva ou corretiva;
* associação com equipamento e técnico responsável;
* busca por ID;
* listagem geral;
* listagem por status;
* busca por equipamento;
* busca por técnico;
* atualização de manutenção em andamento;
* conclusão com registro de data final e custo;
* cancelamento;
* bloqueio da exclusão de uma manutenção que ainda está em andamento.

---

## 🏗️ Estrutura do projeto

```text
src/main/java/com/joao/empresa
├── model       # Entidades e objetos do domínio
├── services    # Operações de gestão e regras da aplicação
├── dao         # Consultas SQL e persistência com JDBC
├── database    # Configuração da conexão com o MySQL
└── exceptions  # Exceções personalizadas
```

O fluxo principal da versão atual é:

```text
Services
   ↓
DAOs
   ↓
JDBC
   ↓
MySQL
```

Nesta etapa, o projeto ainda não possui camada `Controller`, endpoints HTTP ou interface para interação com o usuário.

---

## 🧠 Conceitos praticados

Durante o desenvolvimento do projeto, estão sendo praticados conceitos como:

* Programação Orientada a Objetos;
* abstração;
* herança;
* polimorfismo;
* classes abstratas;
* enums;
* coleções Java;
* arquitetura em camadas;
* padrão DAO;
* JDBC;
* `Connection`;
* `PreparedStatement`;
* `ResultSet`;
* consultas SQL parametrizadas;
* recuperação de chaves geradas pelo banco;
* relacionamento entre tabelas;
* tratamento de exceções;
* Maven;
* Git e GitHub;
* testes unitários com JUnit e Mockito.

---

## 🛠️ Tecnologias utilizadas

| Tecnologia        | Uso no projeto                          |
| ----------------- | --------------------------------------- |
| Java 17           | Linguagem principal                     |
| Maven             | Build e gerenciamento de dependências   |
| JDBC              | Comunicação manual com o banco de dados |
| MySQL             | Persistência dos dados                  |
| MySQL Connector/J | Driver de conexão JDBC                  |
| JUnit 5           | Estrutura de testes                     |
| Mockito           | Criação de mocks nos testes             |
| JaCoCo            | Geração de relatório de cobertura       |
| Git e GitHub      | Controle de versão e publicação         |

---

## 🧪 Situação dos testes

O projeto possui uma estrutura de testes utilizando **JUnit 5**, **Mockito** e builders para criação dos objetos usados nos cenários de teste.

A suíte de testes foi criada durante uma versão anterior do projeto, que utilizava coleções em memória.

Como o projeto passou a utilizar JDBC e banco de dados, os testes estão sendo atualizados para acompanhar a nova implementação.

As próximas etapas relacionadas aos testes incluem:

* atualizar os testes unitários da camada de serviço;
* corrigir os builders conforme os construtores atuais;
* criar testes de integração para os DAOs;
* testar os principais fluxos de manutenção;
* validar regras de negócio sem depender diretamente do banco de dados.

---

## ⚠️ Limitações da versão atual

Como esta é uma versão de estudo em desenvolvimento, ainda existem pontos que serão revisados antes de o projeto ser considerado finalizado.

Atualmente:

* os scripts de criação e carga inicial do banco ainda não estão incluídos no repositório;
* a configuração da conexão depende de um banco MySQL local;
* a suíte de testes ainda está sendo adaptada para a versão JDBC;
* algumas validações e exceções de domínio precisam ser integradas aos DAOs;
* operações que alteram mais de uma tabela ainda precisam utilizar transações;
* ainda não existe autenticação;
* ainda não existe autorização baseada no perfil do usuário;
* ainda não existe API REST;
* ainda não existe interface gráfica.

Esses itens estão documentados para que a evolução do projeto possa ser acompanhada de maneira transparente.

---

## 📈 Próximas etapas

### Estabilização da versão JDBC

* [ ] corrigir e padronizar os nomes das tabelas, colunas e chaves;
* [ ] atualizar os builders e testes existentes;
* [ ] garantir que o build do Maven seja executado com sucesso;
* [ ] adicionar um arquivo `schema.sql` com a estrutura do banco;
* [ ] adicionar dados opcionais para demonstração;
* [ ] mover as credenciais do banco para variáveis de ambiente;
* [ ] tratar corretamente valores opcionais, como a data de conclusão;
* [ ] utilizar transações nas operações que envolvem várias tabelas;
* [ ] mapear violações do banco para exceções de domínio;
* [ ] criar testes de integração para os DAOs;
* [ ] documentar o processo completo de execução do projeto.

### Regras de negócio planejadas

* [ ] impedir duas manutenções em andamento para o mesmo equipamento;
* [ ] validar as datas de abertura e conclusão;
* [ ] garantir unicidade de CNPJ, e-mail e código patrimonial;
* [ ] impedir alterações indevidas em manutenções concluídas ou canceladas;
* [ ] ampliar as consultas de histórico por equipamento e técnico.

### Evolução futura com Spring

Após a conclusão da versão JDBC, o projeto será evoluído para uma API utilizando:

* Spring Boot;
* Spring Web;
* Spring Data JPA;
* DTOs;
* Bean Validation;
* tratamento global de exceções;
* Flyway;
* Spring Security;
* Docker;
* documentação da API;
* testes automatizados de unidade e integração.

---

## 🎯 Objetivo de aprendizado

Este projeto não tem como objetivo apenas entregar um CRUD.

Ele está sendo utilizado para compreender o que acontece por trás de frameworks como o Spring Data JPA.

Antes da migração para Spring, a proposta é aprender na prática:

* como uma aplicação Java abre e fecha conexões;
* como os objetos são convertidos em registros no banco;
* como os resultados SQL são transformados novamente em objetos;
* como os relacionamentos entre entidades são persistidos;
* onde devem ficar as regras de negócio;
* como testar cada camada de maneira isolada;
* por que transações são importantes;
* por que validações são necessárias;
* como tratar erros de banco de dados;
* como manter uma aplicação organizada em camadas.

---

## 🚧 Status do projeto

O projeto está em desenvolvimento ativo.

A versão atual tem como foco a implementação manual da persistência utilizando JDBC. Após a estabilização dessa etapa, será criada uma nova versão utilizando Spring Boot.

---

## 👨‍💻 Autor

**João Emanuel Pereira do Nascimento**

Estudante de Ciência da Computação com foco em desenvolvimento Back-End Java.

📧 [pnjoaoemanuel@gmail.com](mailto:pnjoaoemanuel@gmail.com)
💼 [LinkedIn](https://www.linkedin.com/in/joão-emanuel-5b268b22b)
🐙 [GitHub](https://github.com/joaoemanuel-dev)
