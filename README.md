# Products Service

> API Restful desenvolvida a fim de pôr em prática os meus conhecimentos em Spring Security com Autenticação JWT.

## ⚙️ Funcionalidades

- ✅ **Cadastro de usuários**
    - Qualquer pessoa pode se registrar.
    - Usuários são cadastrados com a role padrão: `BASIC`.

- 📦 **Cadastro de produtos**
    - Acesso restrito a usuários com a role `ADMIN`.

- 💰 **Cadastro de vendas**
    - Apenas usuários com a role `ADMIN` têm permissão para cadastrar vendas.

- 🛒 **Listagem de vendas**
    - Disponível para qualquer usuário autenticado (`BASIC` ou `ADMIN`).

- 📄 **Listagem de produtos**
    - Acessível a todos os usuários autenticados.

> 🔐 Usuário padrão: Ao inicializar a aplicação é adicionado um usuário ADMIN padrão com:
> - Login: admin
> - Senha: admin

## 📚 Endpoints

| Método | Rota                  | Descrição                       | Permissão   |
|--------|-----------------------|---------------------------------|-------------|
| POST   | /api/v1/auth/login    | Autenticação e geração de token | Pública     |
| POST   | /api/v1/auth/register | Cadastro de novo usuário        | Pública     |
| GET    | /api/v1/products      | Listar produtos                 | Autenticado |
| POST   | /api/v1/products      | Cadastrar produto               | ADMIN       |
| GET    | /api/v1/sales         | Listar vendas                   | Autenticado |
| POST   | /api/v1/sales         | Cadastrar venda                 | ADMIN       |

## 🧪 Exemplos de Uso

### 🔐 Autenticação

**Requisição:**
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "login": "admin",
  "password": "admin"
}
```

**Resposta Esperada:**
```
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6Ikp..."
}
```

Obs: o token é meramente ilustrativo

### 📦 Cadastro de Produto (ADMIN)

**Requisição:**
```http
POST /api/v1/products
Authorization: Bearer {seu-token}
Content-Type: application/json

{
  "name": "Notebook Lenovo",
  "unitPrice": 3500.00
}
```

**Resposta Esperada:**
```
{
  "id": 1,
  "name": "Notebook Lenovo",
  "unitPrice": 3500.00
}
```

### 💰 Cadastro de vendas (ADMIN)

**Requisição:**
```http
POST /api/v1/sales
Authorization: Bearer {seu-token}
Content-Type: application/json

{
  "productId": 1,
  "quantity": 10
}
```

**Resposta Esperada:**
```
{
  "id": 1,
  "product": {
    "id": 1,
    "name": "Notebook Lenovo",
    "unitPrice": 3500.00
  },
  "quantity": 10,
  "totalValue": 35000.00
}
```

### 🛒 Listagem de vendas

**Requisição:**
```http
GET /api/v1/sales
Authorization: Bearer {seu-token}
```

**Resposta esperada:**
```
[
  {
    "id": 1,
    "product": {
      "id": 1,
      "name": "Notebook Lenovo",
      "unitPrice": 3500.00
    },
    "quantity": 10,
    "totalValue": 35000.00
  }
]
```

## 📄 Listagem de produtos

**Requisição:**
```http
GET /api/v1/products
Authorization: Bearer {seu-token}
```

**Resposta esperada:**
```
[
  {
    "id": 1,
    "name": "Notebook Lenovo",
    "unitPrice": 3500.00
  } 
]
```

## 💻 Tecnologias Utilizadas

- **Spring Boot** – Framework principal para criação da API REST.
- **Spring Security** – Implementação da autenticação e autorização com JWT.
- **Spring Data JPA** – Integração com banco de dados usando repositórios e consultas com JPA.
- **Hibernate** – Implementação ORM (mapeamento objeto-relacional) apenas para a validação do Schema.
- **MySQL** – Banco de dados relacional utilizado na aplicação.
- **Docker** – Containerização do banco de dados.
- **Maven** – Gerenciador de dependências e automação de build.

## 🚀 Como Rodar a Aplicação

### Requisitos
- Git
- Docker 
- Maven
- Java 21

### 1. Clone o projeto
- Para clonar o projeto e acessar o diretório na sua máquina, execute:
```
git clone https://github.com/mbranches/products-service.git
cd products-service
```

### 2. Popule as variáveis de ambiente
- Variáveis Docker
   - Renomeie o arquivo da raíz do repositório `.envTemplate` para `.env` e preencha a variável com o valor desejado para o seu ambiente de desenvolvimento.
   ```env
    MYSQL_ROOT_PASSWORD=suasenha
- Variáveis Spring Boot
   - Navegue até o diretório `src/main/resources`.
   - Renomeie o arquivo `.envTemplate` para `.env`.
   - Configure `ENV_MYSQL_PASSWORD` igual o definido no `.env` da raíz do projeto.
   - Adicione sua secret key à `ENV_JWT_SECRET_KEY`.
   ```env
    MYSQL_ROOT_PASSWORD=suasenha
    ENV_JWT_SECRET_KEY=sua-secret-key
  ```

### 3. Execute o container
- Para executar o container com o Banco de Dados MySQL, rode o seguinte comando:
```
docker-compose up -d
```

### 4. Rodar a API
- Para executar a aplicação com o maven, execute:
```
mvn spring-boot:run
```

## 📂 Testando a API com Postman

> É necessário ter o Postman instalado, caso não tenha você pode baixá-lo gratuitamente no [site oficial](https://www.postman.com/downloads/)

1. **Baixar o Arquivo da Collection**:  
   Na pasta `data` do repositório, você encontrará um arquivo chamado `Product_Service.postman_collection`. Esse arquivo contém todas as rotas da API, prontas para serem usadas no **Postman**.

---

2. **Importar a Collection**:
   - Abra o **Postman**.
   - Clique em **Import** no canto superior esquerdo da tela.
   - Selecione o arquivo `Product_Service.postman_collection` que você baixou da pasta `data`.
   - Após a importação, todas as rotas estarão disponíveis no **Postman**.

---

3. **Consumindo a API**:  
    - Dentro da Collection, vá até a pasta **Auth** e execute a requisição de **Login** para se autenticar.
    - Um **script automático** irá armazenar o token JWT retornado em uma **variável de ambiente** chamada: `accessToken`
   - Todas as requisições protegidas já estão configuradas para usar essa variável no cabeçalho e prontas para serem testadas