# 🔧 Maintenance Management System

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.16-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

API REST para gerenciamento de **empresas, equipamentos, usuários e manutenções**, desenvolvida com **Java 17 e Spring Boot**.

O projeto nasceu inicialmente como uma aplicação Java com **JDBC puro e MySQL**. Depois de consolidar os fundamentos de persistência, SQL, transações e arquitetura em camadas, a aplicação foi migrada para **Spring Boot, Spring Data JPA, Hibernate e PostgreSQL**.

A proposta não é apenas implementar um CRUD, mas aplicar conceitos reais de desenvolvimento Back-End: regras de negócio, relacionamentos entre entidades, DTOs, validações, transações, tratamento centralizado de exceções e respostas HTTP adequadas.

> **Status:** versão Spring Boot funcional e em evolução. A API principal já está implementada; testes automatizados, documentação OpenAPI, containerização, CI/CD e análise estática fazem parte da próxima etapa do projeto.

---

## 🎯 Objetivo

Este projeto foi criado para desenvolver e consolidar conhecimentos de Back-End Java em uma aplicação com domínio, persistência e regras de negócio reais.

Entre os principais conceitos aplicados estão:

- desenvolvimento de API REST com Spring Boot;
- arquitetura em camadas;
- orientação a objetos e modelagem de domínio;
- persistência com Spring Data JPA e Hibernate;
- banco de dados PostgreSQL;
- relacionamentos JPA `@OneToMany` e `@ManyToOne`;
- herança JPA com estratégia `JOINED`;
- DTOs de entrada e saída;
- Bean Validation;
- transações com `@Transactional`;
- consultas derivadas do Spring Data JPA;
- tratamento global de exceções com `@RestControllerAdvice`;
- respostas HTTP com `ResponseEntity`;
- restrições de unicidade e integridade de dados;
- Maven para gerenciamento de dependências e build;
- Lombok para redução de código repetitivo;
- JaCoCo configurado para análise de cobertura de testes.

---

## 🚀 Evolução do projeto

Uma das principais características deste repositório é a evolução da aplicação a partir de uma implementação com JDBC puro para uma API REST utilizando o ecossistema Spring.

| Conceito | V1 | V2 atual |
| --- | --- | --- |
| Linguagem | Java 17 | Java 17 |
| Interface | Console | API REST |
| Persistência | JDBC | Spring Data JPA |
| ORM | Mapeamento manual | Hibernate |
| Banco | MySQL | PostgreSQL |
| Acesso a dados | DAO | Repository |
| Transações | `commit` / `rollback` manual | `@Transactional` |
| Entrada e saída | Console | JSON / HTTP |
| Validação | Regras manuais | Bean Validation + domínio |
| Tratamento de erros | Exceções da aplicação | `@RestControllerAdvice` + respostas HTTP |
| Injeção de dependência | Manual | Spring IoC / DI |

Essa migração foi feita de forma intencional para compreender primeiro os mecanismos de persistência e transações em baixo nível e, posteriormente, entender quais responsabilidades são abstraídas pelo Spring e pelo Hibernate.

---

## 🛠️ Tecnologias utilizadas

### Back-End

- Java 17
- Spring Boot 3.5.16
- Spring Web
- Spring Data JPA
- Hibernate
- Bean Validation / Jakarta Validation
- Lombok

### Banco de dados

- PostgreSQL

### Build e qualidade

- Maven
- Spring Boot Starter Test
- JUnit 5
- Mockito
- JaCoCo

### Ferramentas de desenvolvimento

- Git e GitHub
- Postman
- IntelliJ IDEA
- DBeaver / ferramentas de administração PostgreSQL

---

## 🏗️ Arquitetura

A aplicação segue uma arquitetura em camadas:

```text
Cliente HTTP / Postman
        ↓
   Controllers
        ↓
      DTOs
        ↓
     Services
        ↓
   Repositories
        ↓
Spring Data JPA / Hibernate
        ↓
    PostgreSQL
```

### Responsabilidade de cada camada

| Camada | Responsabilidade |
| --- | --- |
| `controller` | Recebe requisições HTTP e devolve respostas HTTP |
| `dto` | Define os dados que entram e saem da API |
| `service` | Coordena casos de uso, regras de negócio e transações |
| `repository` | Realiza o acesso aos dados utilizando Spring Data JPA |
| `model` | Representa as entidades e comportamentos do domínio |
| `handler` | Centraliza o tratamento das exceções da API |
| `exceptions` | Contém exceções específicas de negócio e recursos |

---

## 📁 Estrutura do projeto

```text
src/main/java/com/joao/empresa
├── controller
│   ├── EmpresaController.java
│   ├── EquipamentoController.java
│   ├── ManutencaoController.java
│   └── UsuarioController.java
│
├── dto
│   ├── empresa
│   ├── equipamento
│   ├── error
│   ├── manutencao
│   └── usuario
│
├── exceptions
│
├── handler
│   └── GlobalExceptionHandler.java
│
├── model
│   ├── Entidade.java
│   ├── Empresa.java
│   ├── Equipamento.java
│   ├── Usuario.java
│   ├── Administrador.java
│   ├── Gestor.java
│   ├── Tecnico.java
│   └── Manutencao.java
│
├── repository
│   ├── EmpresaRepository.java
│   ├── EquipamentoRepository.java
│   ├── ManutencaoRepository.java
│   └── UsuarioRepository.java
│
├── service
│   ├── EmpresaService.java
│   ├── EquipamentoService.java
│   ├── ManutencaoService.java
│   └── UsuarioService.java
│
└── MaintenanceManagementApplication.java
```

---

## 🧩 Modelo de domínio

O sistema trabalha com quatro grupos principais de entidades:

```text
Empresa
   │
   └── 1:N ── Equipamento
                 │
                 └── 1:N ── Manutencao ── N:1 ── Tecnico

Usuario
   ├── Administrador
   ├── Gestor
   └── Tecnico
```

### Herança de usuários

`Usuario` é uma entidade abstrata e possui três especializações:

- **Administrador** — possui departamento;
- **Gestor** — possui área responsável;
- **Técnico** — possui especialidade e pode ser responsável por manutenções.

A hierarquia é persistida utilizando:

```java
@Inheritance(strategy = InheritanceType.JOINED)
```

Dessa forma, os dados comuns permanecem na tabela de usuários e os dados específicos de cada tipo são armazenados em suas respectivas tabelas.

---

## ✨ Funcionalidades

### 🏢 Empresas

- cadastrar empresa;
- buscar empresa por ID;
- listar empresas;
- atualizar dados;
- excluir empresa;
- controlar status `ATIVADA` e `DESATIVADA`;
- garantir unicidade de CNPJ;
- impedir exclusão de empresa que possua equipamentos vinculados.

### 🖥️ Equipamentos

- cadastrar equipamento associado a uma empresa;
- buscar equipamento por ID;
- listar equipamentos;
- atualizar dados do equipamento;
- excluir equipamento;
- garantir unicidade do código de patrimônio;
- validar a existência da empresa associada;
- impedir exclusão quando existirem manutenções vinculadas.

### 👤 Usuários

O sistema possui três tipos de usuário:

- Administrador;
- Gestor;
- Técnico.

Funcionalidades disponíveis:

- cadastro por tipo de usuário;
- busca por ID;
- listagem geral;
- atualização respeitando o tipo concreto do usuário;
- exclusão;
- unicidade de e-mail;
- proteção contra atualização de um usuário utilizando um tipo incorreto;
- bloqueio da exclusão de técnico associado a manutenções.

### 🔧 Manutenções

Tipos de manutenção:

```text
PREVENTIVA
CORRETIVA
```

Estados possíveis:

```text
             ┌────────────→ CONCLUIDA
ANDAMENTO ───┤
             └────────────→ CANCELADA
```

Funcionalidades:

- cadastrar manutenção;
- associar equipamento e técnico responsável;
- listar todas as manutenções;
- listar por status;
- listar por equipamento;
- listar por técnico;
- buscar por ID;
- atualizar manutenção enquanto estiver em andamento;
- finalizar informando o custo;
- registrar automaticamente a data de finalização;
- cancelar manutenção;
- impedir alteração de manutenção encerrada;
- impedir exclusão de manutenção em andamento.

Ao cadastrar uma manutenção, o próprio domínio define automaticamente:

```text
status  = ANDAMENTO
custo   = 0.00
dataFim = null
```

O cliente da API não controla diretamente esses campos.

---

## 🌐 Endpoints da API

Base URL:

```text
http://localhost:8080/api
```

### Empresas

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `GET` | `/empresas` | Lista todas as empresas |
| `GET` | `/empresas/{id}` | Busca uma empresa por ID |
| `POST` | `/empresas` | Cadastra uma empresa |
| `PUT` | `/empresas/{id}` | Atualiza uma empresa |
| `DELETE` | `/empresas/{id}` | Exclui uma empresa |

### Equipamentos

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `GET` | `/equipamentos` | Lista todos os equipamentos |
| `GET` | `/equipamentos/{id}` | Busca um equipamento por ID |
| `POST` | `/equipamentos` | Cadastra um equipamento |
| `PUT` | `/equipamentos/{id}` | Atualiza um equipamento |
| `DELETE` | `/equipamentos/{id}` | Exclui um equipamento |

### Usuários

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `GET` | `/usuarios` | Lista todos os usuários |
| `GET` | `/usuarios/{id}` | Busca um usuário por ID |
| `POST` | `/usuarios/administradores` | Cadastra um administrador |
| `POST` | `/usuarios/gestores` | Cadastra um gestor |
| `POST` | `/usuarios/tecnicos` | Cadastra um técnico |
| `PUT` | `/usuarios/administradores/{id}` | Atualiza um administrador |
| `PUT` | `/usuarios/gestores/{id}` | Atualiza um gestor |
| `PUT` | `/usuarios/tecnicos/{id}` | Atualiza um técnico |
| `DELETE` | `/usuarios/{id}` | Exclui um usuário |

### Manutenções

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `GET` | `/manutencoes` | Lista todas as manutenções |
| `GET` | `/manutencoes/{id}` | Busca uma manutenção por ID |
| `GET` | `/manutencoes/ativas` | Lista manutenções em andamento |
| `GET` | `/manutencoes/concluidas` | Lista manutenções concluídas |
| `GET` | `/manutencoes/canceladas` | Lista manutenções canceladas |
| `GET` | `/manutencoes/equipamento/{equipamentoId}` | Lista por equipamento |
| `GET` | `/manutencoes/tecnico/{tecnicoId}` | Lista por técnico |
| `POST` | `/manutencoes` | Cadastra uma manutenção |
| `PUT` | `/manutencoes/{id}` | Atualiza uma manutenção em andamento |
| `PATCH` | `/manutencoes/{id}/finalizar` | Finaliza uma manutenção |
| `PATCH` | `/manutencoes/{id}/cancelar` | Cancela uma manutenção |
| `DELETE` | `/manutencoes/{id}` | Exclui uma manutenção encerrada |

---

## 📦 DTOs

As entidades JPA não são utilizadas diretamente como contrato da API.

A aplicação utiliza DTOs específicos para entrada e saída, como:

```text
EmpresaRequest       → dados recebidos no cadastro/atualização
EmpresaResponse      → representação devolvida pela API

EquipamentoRequest   → dados recebidos
EquipamentoResponse  → dados retornados + informações resumidas da empresa

ManutencaoRequest    → dados controlados pelo cliente
ManutencaoResponse   → dados da manutenção + equipamento + técnico

AdministradorRequest
GestorRequest
TecnicoRequest
UsuarioResponse
```

Essa separação evita expor diretamente as entidades persistidas e permite controlar quais dados fazem parte do contrato HTTP.

---

## ✅ Validação de dados

Os dados recebidos pela API são validados utilizando Jakarta Bean Validation.

Entre as validações utilizadas estão:

```java
@NotBlank
@NotNull
@Email
@Size
@Positive
@PositiveOrZero
```

Exemplos de regras:

- nome obrigatório;
- e-mail obrigatório e válido;
- IDs devem ser positivos;
- custo da manutenção não pode ser negativo;
- CNPJ é obrigatório;
- código de patrimônio é obrigatório;
- tipo e descrição da manutenção são obrigatórios.

Além das validações de entrada, regras que pertencem ao domínio permanecem nas próprias entidades ou na camada de serviço.

---

## ⚙️ Regras de negócio

Algumas das regras implementadas:

- CNPJ de empresa deve ser único;
- código de patrimônio do equipamento deve ser único;
- e-mail de usuário deve ser único;
- equipamento só pode ser cadastrado para uma empresa existente;
- manutenção só pode utilizar equipamento existente;
- responsável pela manutenção deve ser um usuário do tipo `Tecnico`;
- manutenção recém-criada começa com status `ANDAMENTO`;
- apenas manutenção em andamento pode ser atualizada;
- apenas manutenção em andamento pode ser finalizada ou cancelada;
- custo não pode ser negativo;
- data final não pode ser anterior à data inicial;
- manutenção em andamento não pode ser excluída;
- empresa com equipamentos associados não pode ser excluída;
- equipamento com manutenções associadas não pode ser excluído;
- técnico com manutenções associadas não pode ser excluído.

---

## 🔄 Transações

Os serviços utilizam `@Transactional` para definir os limites transacionais da aplicação.

A classe de serviço é configurada por padrão para leitura:

```java
@Transactional(readOnly = true)
```

Operações que alteram o estado do banco recebem uma transação de escrita:

```java
@Transactional
public Empresa cadastrar(...) {
    // ...
}
```

Isso mantém a responsabilidade transacional na camada de serviço e separa operações de consulta das operações que modificam dados.

---

## ⚠️ Tratamento global de exceções

A API centraliza o tratamento de erros utilizando:

```java
@RestControllerAdvice
```

O `GlobalExceptionHandler` converte exceções da aplicação em respostas HTTP padronizadas.

Principais códigos utilizados:

| HTTP | Situação |
| --- | --- |
| `200 OK` | Consulta ou atualização realizada com sucesso |
| `201 Created` | Recurso criado com sucesso |
| `204 No Content` | Exclusão realizada com sucesso |
| `400 Bad Request` | Dados ou argumentos inválidos |
| `404 Not Found` | Recurso não encontrado |
| `409 Conflict` | Conflito com regra de negócio ou integridade |
| `500 Internal Server Error` | Erro inesperado |

Exemplo de resposta de erro:

```json
{
  "timestamp": "2026-10-27T11:40:00",
  "status": 404,
  "error": "Not Found",
  "message": "Empresa com ID 999 não encontrada.",
  "path": "/api/empresas/999"
}
```

---

## 🧪 Exemplos de requisições

### 1. Cadastrar empresa

`POST /api/empresas`

```json
{
  "nome": "Indústria Alfa",
  "cnpj": "12.345.678/0001-90",
  "endereco": "Rua das Indústrias, 100",
  "segmento": "Metalurgia",
  "status": "ATIVADA"
}
```

### 2. Cadastrar técnico

`POST /api/usuarios/tecnicos`

```json
{
  "nome": "Carlos Silva",
  "email": "carlos@empresa.com",
  "especialidade": "Elétrica"
}
```

### 3. Cadastrar equipamento

`POST /api/equipamentos`

```json
{
  "nome": "Torno CNC",
  "codigoPatrimonio": "PAT-001",
  "dataAquisicao": "2026-01-15",
  "empresaId": 1
}
```

### 4. Cadastrar manutenção

`POST /api/manutencoes`

```json
{
  "tipoManutencao": "PREVENTIVA",
  "descricao": "Inspeção e lubrificação geral do equipamento",
  "dataInicio": "2026-10-28",
  "equipamentoId": 1,
  "tecnicoId": 1
}
```

Resposta esperada:

```json
{
  "id": 1,
  "tipoManutencao": "PREVENTIVA",
  "descricao": "Inspeção e lubrificação geral do equipamento",
  "dataInicio": "2026-10-28",
  "dataFim": null,
  "custo": 0.00,
  "status": "ANDAMENTO",
  "equipamentoId": 1,
  "equipamentoNome": "Torno CNC",
  "tecnicoId": 1,
  "tecnicoNome": "Carlos Silva"
}
```

### 5. Finalizar manutenção

`PATCH /api/manutencoes/1/finalizar`

```json
{
  "custo": 350.00
}
```

A data final é definida automaticamente pela aplicação.

---

## ▶️ Como executar o projeto

### Pré-requisitos

Tenha instalado:

- Java 17;
- Maven;
- PostgreSQL;
- Git.

### 1. Entre na pasta do projeto

Após clonar o repositório:

```bash
cd maintenance-management-system
```

### 2. Crie o banco de dados

No PostgreSQL:

```sql
CREATE DATABASE manutencao_db;
```

### 3. Configure a conexão

Ajuste o arquivo:

```text
src/main/resources/application.properties
```

Exemplo:

```properties
spring.application.name=maintenance-management-system

spring.datasource.url=jdbc:postgresql://localhost:5432/manutencao_db
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.jpa.open-in-view=false
```

### 4. Execute a aplicação

```bash
mvn spring-boot:run
```

Ou execute `MaintenanceManagementApplication` diretamente pela IDE.

A API ficará disponível em:

```text
http://localhost:8080
```

---

## 🧪 Testes e cobertura

O projeto utiliza `spring-boot-starter-test`, que fornece suporte para JUnit 5, Mockito e ferramentas de teste do Spring.

O JaCoCo já está configurado no `pom.xml` para geração de relatório de cobertura durante a fase `verify` do Maven.

Para executar os testes:

```bash
mvn test
```

Para executar a verificação e gerar o relatório de cobertura:

```bash
mvn verify
```

O relatório do JaCoCo será gerado em:

```text
target/site/jacoco/index.html
```

> A suíte de testes automatizados da versão Spring Boot está sendo expandida como parte da etapa atual do projeto.

---

## 🗺️ Roadmap

- [x] Modelagem do domínio
- [x] V1 utilizando JDBC e SQL
- [x] Migração para Spring Boot
- [x] API REST
- [x] Spring Data JPA e Hibernate
- [x] PostgreSQL
- [x] DTOs de request e response
- [x] Bean Validation
- [x] Tratamento global de exceções
- [x] Regras de negócio e integridade
- [x] Transações com `@Transactional`
- [x] JaCoCo configurado
- [ ] Ampliar testes unitários com JUnit e Mockito
- [ ] Adicionar testes de integração
- [ ] Documentar API com Swagger / OpenAPI
- [ ] Containerizar aplicação e banco com Docker / Docker Compose
- [ ] Criar pipeline de CI/CD
- [ ] Integrar análise de qualidade com SonarQube

---

## 💡 Principais aprendizados

O desenvolvimento deste projeto permitiu praticar a evolução de uma aplicação Java de baixo nível para uma aplicação baseada em framework.

Na primeira versão, responsabilidades como conexão com banco, SQL, mapeamento de objetos e controle transacional eram tratadas manualmente com JDBC. Na versão atual, essas responsabilidades passaram a utilizar abstrações do Spring e do Hibernate.

Essa comparação ajudou a consolidar conceitos como:

- diferença entre DAO e Repository;
- persistência manual versus ORM;
- ciclo de vida de entidades JPA;
- injeção de dependências;
- transações declarativas;
- separação entre entidade e DTO;
- validação na fronteira da aplicação;
- regras de negócio no domínio e nos serviços;
- semântica dos principais códigos HTTP;
- organização de uma API REST em camadas.

---

## 👨‍💻 Autor

Desenvolvido por **João Emanuel** como projeto de estudo e evolução em desenvolvimento Back-End com Java e Spring Boot.

---

## 📌 Observação

Este projeto está em evolução contínua. Novas etapas serão adicionadas ao repositório conforme o avanço dos estudos em testes automatizados, documentação de APIs, containerização, integração contínua e qualidade de código.
