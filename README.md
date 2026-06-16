# 🔧 Maintenance Management System

Sistema de Gestão de Manutenção desenvolvido em Java com foco em boas práticas de Back-End, Programação Orientada a Objetos, arquitetura em camadas e persistência de dados.

Projeto desenvolvido como portfólio para aprofundamento em Java, JDBC, Banco de Dados Relacionais e futuramente Spring Boot.

---

## 🚀 Sobre o Projeto

O sistema simula uma plataforma SaaS (Software as a Service) de gestão de manutenção industrial.

Cada empresa possui seus próprios usuários, equipamentos e manutenções, garantindo isolamento de dados e controle de acesso baseado em perfis.

O objetivo é permitir o gerenciamento completo de empresas, equipamentos, usuários e manutenções preventivas e corretivas.

---

## 🏗️ Arquitetura

```text
Interface
    ↓
Serviços / Gestão
    ↓
Entidades
    ↓
Persistência (JDBC - Em desenvolvimento)
```

O projeto foi estruturado seguindo separação de responsabilidades para facilitar manutenção, escalabilidade e evolução futura.

---

## 📦 Principais Entidades

### 🏢 Empresa

* Nome
* CNPJ
* Endereço
* Equipamentos vinculados

### 👤 Usuários

* Administrador
* Gestor
* Técnico

Cada perfil possui permissões e responsabilidades específicas.

### ⚙️ Equipamento

* Nome
* Código patrimonial
* Data de aquisição
* Histórico de manutenções

### 🔧 Manutenção

* Preventiva
* Corretiva
* Status da manutenção
* Técnico responsável
* Equipamento associado
* Custos e histórico

---

## ✨ Funcionalidades

### Empresas

* Cadastro
* Consulta
* Atualização
* Exclusão
* Validação de CNPJ único

### Equipamentos

* Cadastro
* Consulta
* Atualização
* Exclusão
* Controle de status

### Usuários

* Cadastro de administradores
* Cadastro de gestores
* Cadastro de técnicos
* Controle de permissões

### Manutenções

* Registro de manutenção preventiva
* Registro de manutenção corretiva
* Atualização de status
* Finalização de manutenção
* Histórico por equipamento

---

## 🧠 Regras de Negócio

* Empresas não podem possuir CNPJ duplicado
* Técnicos possuem e-mail único
* Equipamentos não podem receber manutenções simultâneas
* Equipamentos em manutenção alteram automaticamente seu status
* Manutenções finalizadas não podem ser alteradas
* Data de conclusão não pode ser anterior à data de abertura
* Técnicos possuem permissões restritas às suas atribuições

---

## 🧪 Testes

O projeto utiliza:

* JUnit 5
* Mockito

para validação das regras de negócio e testes unitários.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Finalidade                    |
| ---------- | ----------------------------- |
| Java       | Linguagem principal           |
| Maven      | Gerenciamento de dependências |
| JUnit 5    | Testes unitários              |
| Mockito    | Mock de dependências          |
| Git        | Controle de versão            |
| GitHub     | Hospedagem do projeto         |
| JDBC       | Em desenvolvimento            |
| MySQL      | Em desenvolvimento            |

---

## 📈 Próximas Implementações

* [ ] Persistência utilizando JDBC
* [ ] Integração com MySQL
* [ ] DAO Pattern
* [ ] Relatórios gerenciais
* [ ] Logs de auditoria
* [ ] Soft Delete
* [ ] API REST com Spring Boot
* [ ] Controle de autenticação
* [ ] Dashboard de indicadores

---

## 🎯 Objetivos de Aprendizado

Este projeto foi criado para consolidar conhecimentos em:

* Programação Orientada a Objetos
* Arquitetura de Software
* Banco de Dados Relacionais
* JDBC
* Testes Automatizados
* Boas práticas de desenvolvimento Back-End

---

## 👨‍💻 Autor

João Emanuel Pereira do Nascimento

📧 [pnjoaoemanuel@gmail.com](mailto:pnjoaoemanuel@gmail.com)

💼 LinkedIn:
https://www.linkedin.com/in/joão-emanuel-5b268b22b

🐙 GitHub:
https://github.com/joaoemanuel-dev
