# 📦 Sistema de Estoque API

Uma API REST completa, desenvolvida em **Java** e **Spring Boot**, projetada para o gerenciamento inteligente, seguro e padronizado de produtos e suas respectivas categorias. O projeto aplica conceitos modernos de arquitetura de software, como o isolamento completo de camadas, uso de DTOs (`records`), validações de dados rigorosas e movimentação de estoque protegida contra inconsistências.

---

## 🚀 Tecnologias Utilizadas

* **Java 21** (Uso de recursos modernos da linguagem, como Records estáveis e melhorias gerais de performance)
* **Spring Boot 4.1.1** (Aproveitando as atualizações mais recentes de performance e segurança do ecossistema)
* **Spring Data JPA** (Persistência, mapeamentos relacionais e comunicação abstrata com o banco de dados)
* **Jakarta Validation / Hibernate Validator** (Garantia de integridade e blindagem dos dados de entrada)
* **Lombok** (Produtividade e eliminação de códigos boilerplates através de anotações como `@RequiredArgsConstructor`)
* **MySQL** (Banco de dados relacional robusto para a persistência segura de todas as entidades do sistema)

---

## 🛠️ Funcionalidades e Diferenciais Técnicos

* **CRUD Completo de Categorias e Produtos**: Criação, listagem geral, busca por ID, atualização e deleção para ambas as entidades.
* **Busca por Relacionamento**: Endpoint dedicado no controlador de produtos para filtrar e buscar todos os itens que pertencem a uma categoria específica (`/categories/{id}`).
* **Fluxo de Movimentação de Estoque Automatizado**: Endpoints de atualização parcial (`PATCH`) para adicionar ou remover itens do estoque.
* **Blindagem de Regras de Negócio**: Validações customizadas no serviço de estoque que impedem a remoção de quantidades superiores às disponíveis em estoque, lançando uma exceção personalizada (`InsufficientStockException`).
* **Isolamento de Camadas com DTOs**: Uso estratégico de Java `records` para desacoplar as entidades de banco de dados (`ProductEntity` e `CategoryEntity`) das estruturas de requisição (`RequestDto`) e resposta (`ResponseDto`).
* **Tratamento Global de Erros**: Captura centralizada de exceções através de um `@RestControllerAdvice` corporativo. Garante que erros de validação de parâmetros (`ConstraintViolationException`), dados inválidos do corpo ou IDs não encontrados (`ResourceNotFoundException`) retornem estruturas limpas e padronizadas no formato RFC 7807 (`ProblemDetail`).

---

## 🗺️ Endpoints da API (V1)

### Categorias (`/v1/categories`)

| Método | Endpoint | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- |
| `POST` | `/v1/categories` | Cria uma nova categoria de produto | `201 Created` |
| `GET` | `/v1/categories` | Lista todas as categorias cadastradas | `200 OK` |
| `GET` | `/v1/categories/{id}` | Busca uma categoria específica pelo ID | `200 OK` |
| `PUT` | `/v1/categories/{id}` | Atualiza o nome/dados de uma categoria | `200 OK` |
| `DELETE` | `/v1/categories/{id}` | Exclui uma categoria do sistema | `204 No Content` |

### Produtos (`/v1/products`)

| Método | Endpoint | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- |
| `POST` | `/v1/products` | Cria um novo produto (inicializado com estoque zerado) | `201 Created` |
| `GET` | `/v1/products` | Lista todos os produtos cadastrados | `200 OK` |
| `GET` | `/v1/products/{id}` | Busca um produto específico pelo ID | `200 OK` |
| `GET` | `/v1/products/categories/{id}` | Filtra e lista todos os produtos de uma categoria | `200 OK` |
| `PUT` | `/v1/products/{id}` | Atualiza todas as informações cadastrais do produto | `200 OK` |
| `PATCH` | `/v1/products/add-stock/{id}?quantity=...` | Adiciona uma quantidade positiva ao estoque do produto | `200 OK` |
| `PATCH` | `/v1/products/remove-stock/{id}?quantity=...` | Remove de forma segura uma quantidade do estoque | `200 OK` |
| `DELETE` | `/v1/products/{id}` | Exclui um produto do sistema | `204 No Content` |

---

## 📋 Exemplos de Payload (JSON)

### Criar Produto (`POST /v1/products`)

**Corpo da Requisição (Request Body):**
```json
{
  "name": "Teclado Mecânico RGB",
  "description": "Teclado mecânico Switch Blue com layout ABNT2",
  "price": 249.90,
  "categoryId": 1
}
```

**Corpo da Resposta (Response Body):**
```json
{
  "id": 1,
  "name": "Teclado Mecânico RGB",
  "description": "Teclado mecânico Switch Blue com layout ABNT2",
  "price": 249.90,
  "quantity": 0,
  "categoryName": "Informática",
  "createdAt": "2026-09-16T21:00:00Z"
}
```

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
* Ter o **Java 21** instalado em sua máquina.
* Ter o **MySQL** instalado e rodando localmente.
* Ter criado um schema/banco de dados vazio no seu MySQL.

### Passos para execução

1. **Clonar o repositório:**
   ```bash
   git clone https://github.com/Ewerton-Bruno12/sistema-de-estoque
   ```

2. **Configurar o banco de dados:**
   Abra o arquivo `src/main/resources/application.properties` (ou `.yml`) e ajuste a URL com o nome do seu banco e insira suas credenciais do MySQL (usuário e senha):
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/NOME_DO_SEU_BANCO
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   
   # Configuração para criação automática das tabelas pelo Hibernate
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Entrar na pasta raiz do projeto:**
   ```bash
   cd sistema-de-estoque
   ```

4. **Executar a aplicação via Maven Wrapper:**
   ```bash
   ./mvnw spring-boot:run
   ```
