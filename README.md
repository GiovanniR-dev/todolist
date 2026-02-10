# 📝 TodoList API

Gerenciador de tarefas desenvolvido com **Java 17** e **Spring Boot 3.5.4**.

Esta API permite criar, listar, atualizar e deletar tarefas (to-dos), seguindo o padrão REST. Ela recebe e responde dados no formato **JSON**, sendo ideal para ser consumida por um front-end web, aplicativo mobile ou ferramentas como Postman e Insomnia.

---

## 🚀 Tecnologias

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Spring Boot 3.5.4](https://spring.io/projects/spring-boot)
- [Spring Web](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
- [Maven](https://maven.apache.org/)

---

## 📁 Estrutura do Projeto

```
todolist/
├── src/
│   ├── main/
│   │   ├── java/br/com/danieleleao/todolist/
│   │   │   └── TodolistApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/br/com/danieleleao/todolist/
│           └── TodolistApplicationTests.java
├── pom.xml
└── README.md
```

---

## ⚙️ Pré-requisitos

Antes de rodar o projeto, certifique-se de ter instalado:

- [Java 17+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/download.cgi)

---

## ▶️ Como executar

### Clonando o repositório

```bash
git clone https://github.com/GiovanniR-dev/todolist.git
cd todolist
```

### Rodando com Maven

```bash
./mvnw spring-boot:run
```

### Rodando no Windows

```cmd
mvnw.cmd spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

---

## 🧪 Testes

Para executar os testes:

```bash
./mvnw test
```

---

## 🔄 Fluxo de Funcionamento da API

O funcionamento da API segue um fluxo simples de **requisição → processamento → resposta**:

```
Cliente (Postman / Front-end / App)
        │
        │  Envia requisição HTTP (GET, POST, PUT, DELETE)
        ▼
  [ Controller ]
  Recebe a requisição e identifica qual ação executar
        │
        ▼
  [ Service ]
  Aplica as regras de negócio (validações, lógica)
        │
        ▼
  [ Repository ]
  Acessa o banco de dados para ler ou gravar os dados
        │
        ▼
  Retorna a resposta em JSON para o cliente
```

> **Resumindo:** o cliente faz uma requisição HTTP → a API processa → responde com os dados em JSON e um código de status (200, 201, 404, etc.).

---

## 📌 Endpoints

Base URL: `http://localhost:8080`

---

### 📋 GET `/tasks` — Listar todas as tarefas

Retorna uma lista com todas as tarefas cadastradas.

**Requisição:**
```http
GET /tasks
```

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "titulo": "Estudar Spring Boot",
    "descricao": "Aprender a criar APIs REST",
    "concluida": false
  },
  {
    "id": 2,
    "titulo": "Fazer exercícios",
    "descricao": "30 minutos de caminhada",
    "concluida": true
  }
]
```

---

### 🔍 GET `/tasks/{id}` — Buscar tarefa por ID

Retorna os dados de uma tarefa específica pelo seu ID.

**Requisição:**
```http
GET /tasks/1
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "titulo": "Estudar Spring Boot",
  "descricao": "Aprender a criar APIs REST",
  "concluida": false
}
```

**Resposta (404 Not Found)** — quando o ID não existe:
```json
{
  "erro": "Tarefa não encontrada"
}
```

---

### ➕ POST `/tasks` — Criar nova tarefa

Cria uma nova tarefa no sistema. Os dados devem ser enviados no corpo da requisição em formato JSON.

**Requisição:**
```http
POST /tasks
Content-Type: application/json
```

**Corpo (body):**
```json
{
  "titulo": "Ler um livro",
  "descricao": "Ler pelo menos 20 páginas",
  "concluida": false
}
```

**Resposta (201 Created):**
```json
{
  "id": 3,
  "titulo": "Ler um livro",
  "descricao": "Ler pelo menos 20 páginas",
  "concluida": false
}
```

---

### ✏️ PUT `/tasks/{id}` — Atualizar uma tarefa

Atualiza os dados de uma tarefa existente. É necessário informar o ID na URL e os novos dados no corpo.

**Requisição:**
```http
PUT /tasks/3
Content-Type: application/json
```

**Corpo (body):**
```json
{
  "titulo": "Ler um livro",
  "descricao": "Ler pelo menos 50 páginas",
  "concluida": true
}
```

**Resposta (200 OK):**
```json
{
  "id": 3,
  "titulo": "Ler um livro",
  "descricao": "Ler pelo menos 50 páginas",
  "concluida": true
}
```

---

### 🗑️ DELETE `/tasks/{id}` — Deletar uma tarefa

Remove permanentemente uma tarefa pelo seu ID.

**Requisição:**
```http
DELETE /tasks/3
```

**Resposta (204 No Content):**
```
(sem corpo na resposta)
```

**Resposta (404 Not Found)** — quando o ID não existe:
```json
{
  "erro": "Tarefa não encontrada"
}
```

---

## 📊 Códigos de Status HTTP

| Código | Significado |
|--------|-------------|
| `200 OK` | Requisição bem-sucedida |
| `201 Created` | Recurso criado com sucesso |
| `204 No Content` | Deletado com sucesso (sem retorno) |
| `404 Not Found` | Recurso não encontrado |
| `400 Bad Request` | Dados inválidos na requisição |
| `500 Internal Server Error` | Erro interno no servidor |

---

## 👤 Autor

**Daniela Leão**  
GitHub: [@GiovanniR-dev](https://github.com/GiovanniR-dev)
