# 🏨 API REST - Sistema de Gestão de Pousada

<div align="center">

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit5-Tested-25A162?style=for-the-badge&logo=junit5&logoColor=white)

**API REST completa para gerenciamento de pousadas com controle de hóspedes, quartos e reservas**

[Instalação](#-instalação) • [Endpoints](#-endpoints) • [Arquitetura](#-arquitetura) • [Testes](#-testes)

</div>

---

## ⚠️ Status do Projeto

**🚧 EM DESENVOLVIMENTO 🚧**

| Feature | Status |
|---------|--------|
| ✅ CRUD de Hóspedes | Concluído |
| ✅ CRUD de Quartos | Concluído |
| ✅ Sistema de Reservas | Concluído |
| ✅ Testes Unitários (Services) | Concluído |
| ✅ Testes de Integração (Repositories) | Concluído |
| ⏳ Autenticação/Autorização | Pendente |
| ⏳ Documentação Swagger | Pendente |

---

## 🎯 Sobre o Projeto

API REST desenvolvida com Spring Boot para gerenciamento completo de pousadas, automatizando processos operacionais de estabelecimentos hoteleiros de pequeno e médio porte.

### Principais Features

- 👥 **Gestão de Hóspedes** - Cadastro completo com validação de CPF
- 🛏️ **Controle de Quartos** - Tipos, preços e status (disponível/ocupado/manutenção)
- 📅 **Sistema de Reservas** - Ciclo completo: criação → check-in → check-out
- 💰 **Processamento de Pagamentos** - Múltiplos métodos (PIX, cartão, dinheiro)
- 📊 **Métricas e Relatórios** - Taxa de ocupação e receita por período
- 🔐 **Validações Robustas** - Bean Validation com annotations customizadas
- 🗄️ **Persistência** - Spring Data JPA + PostgreSQL
- ⚠️ **Exception Handling** - Tratamento centralizado de erros
- 🧪 **Testes Completos** - JUnit 5 + Mockito + AssertJ

---

## 🚀 Instalação

### Pré-requisitos

```bash
☕ Java 17+
🐘 PostgreSQL 16+
📦 Maven 3.9+
```

### Setup Rápido

**1. Clone o repositório**
```bash
git clone https://github.com/YanAlmeida/sistema-gestao-pousada.git
cd sistema-gestao-pousada
```

**2. Configure o banco de dados**
```sql
CREATE DATABASE pousada_db;
```

**3. Configure `application.properties`**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/pousada_db
spring.datasource.username=postgres
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

**4. Execute**
```bash
mvn clean install
mvn spring-boot:run
```

**5. Acesse a API**
```
🌐 Base URL: http://localhost:8080
```

---

## 🛠️ Stack Tecnológica

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| **Java** | 17+ | Linguagem principal |
| **Spring Boot** | 3.x | Framework core |
| **Spring Data JPA** | 3.x | Camada de persistência |
| **Hibernate** | 6.x | ORM |
| **PostgreSQL** | 16 | Banco de dados relacional |
| **Hibernate Validator** | 8.x | Validações Bean Validation |
| **Lombok** | 1.18+ | Redução de boilerplate |
| **JUnit 5** | 5.x | Framework de testes |
| **Mockito** | 5.x | Mock de dependências |
| **AssertJ** | 3.x | Assertions fluentes |
| **Maven** | 3.9+ | Build e gerenciamento de dependências |

---

## 🔌 Endpoints

### 👥 Hóspedes (`/pousada/hospedes`)

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| `POST` | `/pousada/hospedes` | Cadastrar novo hóspede | 201, 400, 409 |
| `GET` | `/pousada/hospedes` | Listar todos os hóspedes | 200 |
| `GET` | `/pousada/hospedes/{id}` | Buscar hóspede por ID | 200, 404 |
| `PUT` | `/pousada/hospedes/{id}` | Atualizar dados do hóspede | 200, 404 |

## 📦 Exemplos de Requisições

**Exemplo - Cadastrar Hóspede:**
```json
POST /pousada/hospedes

{
  "nome": "João Silva",
  "telefone": "(11) 98765-4321",
  "cpf": "12345678901"
}
```

**Resposta (201):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "telefone": "(11) 98765-4321",
  "cpf": "123.456.789-01"
}
```

### 🛏️ Quartos (`/pousada/quartos`)

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| `POST` | `/pousada/quartos` | Cadastrar novo quarto | 201, 400, 409 |
| `GET` | `/pousada/quartos` | Listar todos os quartos | 200 |
| `GET` | `/pousada/quartos/{numero}` | Buscar quarto por número | 200, 404 |
| `PUT` | `/pousada/quartos/{numero}` | Atualizar dados do quarto | 200, 404 |
| `PUT` | `/pousada/quartos/{numero}/status` | Atualizar status do quarto | 200, 404 |
| `GET` | `/pousada/quartos/disponiveis` | Listar quartos disponíveis | 200 |

**Exemplo - Cadastrar Quarto:**
```json
POST /pousada/quartos

{
  "numero": 101,
  "tipo": "CASAL",
  "precoPorNoite": 150.00
}
```

**Resposta (201):**
```json
{
  "id": 1,
  "numero": 101,
  "tipo": "CASAL",
  "precoPorNoite": 150.00,
  "status": "DISPONIVEL"
}
```

### 📅 Reservas (`/pousada/reservas`)

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| `POST` | `/pousada/reservas` | Criar nova reserva | 201, 400, 409 |
| `PUT` | `/pousada/reservas/{id}/check-in` | Realizar check-in | 200, 404 |
| `PUT` | `/pousada/reservas/{id}/devolucao-chave` | Registrar devolução de chave | 200, 404 |
| `PUT` | `/pousada/reservas/{id}/check-out` | Realizar check-out | 200, 404 |
| `PUT` | `/pousada/reservas/{id}/cancelar` | Cancelar reserva | 200, 404 |
| `PUT` | `/pousada/reservas/{id}/pagamento` | Processar pagamento | 200, 404 |
| `GET` | `/pousada/reservas` | Listar todas as reservas | 200 |
| `GET` | `/pousada/reservas/hoje` | Listar reservas do dia | 200 |
| `GET` | `/pousada/reservas/quarto/{numeroQuarto}` | Listar reservas por quarto | 200 |
| `GET` | `/pousada/reservas/receita` | Calcular receita por período | 200 |
| `GET` | `/pousada/reservas/taxa-ocupacao` | Consultar taxa de ocupação | 200 |

**Exemplo - Criar Reserva:**
```json
POST /pousada/reservas

{
  "hospedeId": 1,
  "numeroQuarto": 101,
  "dataCheckIn": "2026-01-15",
  "dataCheckOut": "2026-01-18",
  "metodoPagamento": "CARTAO_CREDITO"
}
```

**Resposta (201):**
```json
{
  "id": 1,
  "nomeHospede": "João Silva",
  "cpfHospede": "123.456.789-01",
  "telefoneHospede": "(11) 98765-4321",
  "numeroQuarto": 101,
  "tipoQuarto": "CASAL",
  "dataCheckIn": "2026-01-15",
  "dataCheckOut": "2026-01-18",
  "valorTotal": 450.00,
  "statusReserva": "ATIVA",
  "metodoPagamento": "CARTAO_CREDITO",
  "statusPagamento": "PENDENTE",
  "statusChave": "NAO_DEVOLVIDA"
}
```

**Exemplo - Processar Pagamento:**
```
PUT /pousada/reservas/1/pagamento?metodoPagamento=PIX
```

**Exemplo - Calcular Receita:**
```
GET /pousada/reservas/receita?inicio=2026-01-01&fim=2026-01-31
```

---

## 🏗️ Arquitetura

### Estrutura em Camadas

```
┌──────────────┐
│  Controller  │  ← REST API (JSON) + Validações (@Valid)
└──────┬───────┘
       │
┌──────▼───────┐
│   Service    │  ← Regras de negócio + Lógica de reservas
└──────┬───────┘
       │
┌──────▼───────┐
│  Repository  │  ← Spring Data JPA (Query Methods)
└──────┬───────┘
       │
┌──────▼───────┐
│  PostgreSQL  │  ← Banco de dados
└──────────────┘
```

### Estrutura de Diretórios

```
src/
├── main/
│   └── java/dev/YanAlmeida/SistemaDeGestaoDePousada/
│       ├── 📁 controller/
│       │   ├── HospedeController.java
│       │   ├── QuartoController.java
│       │   └── ReservaController.java
│       │
│       ├── 📁 service/
│       │   ├── HospedeService.java
│       │   ├── QuartoService.java
│       │   └── ReservaService.java
│       │
│       ├── 📁 repository/
│       │   ├── HospedeRepository.java
│       │   ├── QuartoRepository.java
│       │   └── ReservaRepository.java
│       │
│       ├── 📁 entity/
│       │   ├── HospedeModel.java
│       │   ├── QuartoModel.java
│       │   └── ReservaModel.java
│       │
│       ├── 📁 dto/
│       │   ├── hospede/
│       │   │   ├── HospedeCreateDTO.java
│       │   │   └── HospedeResponseDTO.java
│       │   ├── quarto/
│       │   │   ├── QuartoCreateDTO.java
│       │   │   └── QuartoResponseDTO.java
│       │   └── reserva/
│       │       ├── ReservaCreateDTO.java
│       │       ├── ReservaResponseDTO.java
│       │       └── PagamentoDTO.java
│       │
│       ├── 📁 mapper/
│       │   ├── HospedeMapper.java
│       │   ├── QuartoMapper.java
│       │   └── ReservaMapper.java
│       │
│       ├── 📁 exception/
│       │   ├── global/
│       │   │   ├── GlobalExceptionHandler.java
│       │   │   └── ErrorResponse.java
│       │   ├── hospede/
│       │   ├── quarto/
│       │   └── reserva/
│       │
│       └── 📁 enums/
│           ├── quarto/
│           │   ├── QuartoStatus.java
│           │   └── TipoQuarto.java
│           └── reserva/
│               ├── StatusReserva.java
│               ├── StatusPagamento.java
│               ├── StatusChave.java
│               └── MetodoPagamento.java
│
└── test/
    └── java/dev/YanAlmeida/SistemaDeGestaoDePousada/
        ├── 📁 repository/
        │   ├── HospedeRepositoryTest.java
        │   ├── QuartoRepositoryTest.java
        │   └── ReservaRepositoryTest.java
        │
        ├── 📁 service/
        │   ├── HospedeServiceTest.java
        │   ├── QuartoServiceTest.java
        │   └── ReservaServiceTest.java
        │
        └── SistemaDeGestaoDePousadaApplicationTests.java
```

### Padrões de Projeto

- ✅ **Layered Architecture** - Controller → Service → Repository
- ✅ **DTO Pattern** - Separação entre objetos de transferência e entidades
- ✅ **Repository Pattern** - Abstração de acesso a dados
- ✅ **Mapper Pattern** - Conversão entre DTOs e Entities
- ✅ **Exception Handling** - `@ControllerAdvice` para tratamento global
- ✅ **Bean Validation** - Validações declarativas (@Valid, @CPF, @NotBlank)
- ✅ **Transaction Management** - `@Transactional` para operações atômicas

---

## 🗄️ Modelo de Dados

```sql
┌────────────────────┐     ┌────────────────────┐     ┌─────────────────────┐
│    TB_HOSPEDE      │     │     TB_QUARTO      │     │     TB_RESERVA      │
├────────────────────┤     ├────────────────────┤     ├─────────────────────┤
│ id (PK)   BIGSERIAL│     │ id (PK)   BIGSERIAL│     │ id (PK)    BIGSERIAL│
│ nome      VARCHAR  │     │ numero    INTEGER  │     │ nome_hosp  VARCHAR  │
│ cpf       VARCHAR  │     │ tipo      VARCHAR  │     │ cpf_hosp   VARCHAR  │
│ telefone  VARCHAR  │     │ preco     NUMERIC  │     │ tel_hosp   VARCHAR  │
└────────────────────┘     │ status    VARCHAR  │     │ num_quarto INTEGER  │
   UNIQUE: cpf             └────────────────────┘     │ tipo_quarto VARCHAR │
                              UNIQUE: numero          │ dt_checkin  DATE    │
                                                       │ dt_checkout DATE    │
                                                       │ valor_total NUMERIC │
                                                       │ status_res  VARCHAR │
                                                       │ metodo_pag  VARCHAR │
                                                       │ status_pag  VARCHAR │
                                                       │ status_chave VARCHAR│
                                                       └─────────────────────┘
```

**Relacionamentos:**
- Hóspede → Reservas (1:N conceitual)
- Quarto → Reservas (1:N histórico)
- Reservas armazenam dados desnormalizados para histórico

**Constraints:**
- CPF único por hóspede
- Número único por quarto
- Validação de conflito de datas nas reservas

---

## ⚙️ Regras de Negócio

### Validações (Bean Validation)

```java
// HospedeCreateDTO
@NotBlank(message = "Nome do hóspede é obrigatório")
private String nome;

@NotBlank(message = "Telefone é obrigatório")
private String telefone;

@NotBlank(message = "CPF é obrigatório")
@CPF(message = "CPF inválido")
private String cpf;

// QuartoCreateDTO
@NotNull(message = "Número do quarto é obrigatório")
private Integer numero;

@NotNull(message = "Tipo do quarto é obrigatório")
private TipoQuarto tipo;

@NotNull(message = "Preço por noite é obrigatório")
@DecimalMin(value = "0.0", inclusive = false)
private BigDecimal precoPorNoite;

// ReservaCreateDTO
@NotNull(message = "ID do hóspede é obrigatório")
private Long hospedeId;

@NotNull(message = "Data de check-in é obrigatória")
private LocalDate dataCheckIn;

@NotNull(message = "Data de check-out é obrigatória")
private LocalDate dataCheckOut;
```

### Hóspedes

- ✅ CPF único no sistema com formatação automática
- ✅ Validação de CPF via Hibernate Validator
- ✅ Campos obrigatórios: nome, telefone, CPF

### Quartos

- ✅ Número único por quarto
- ✅ Status inicial: DISPONÍVEL
- ✅ Tipos: SOLTEIRO, CASAL, TRIPLA
- ✅ Preço deve ser maior que zero

### Reservas - Ciclo Completo

#### 1️⃣ Criação de Reserva
```
✓ Data check-out > check-in
✓ Verificação de conflitos de datas
✓ Múltiplas reservas futuras permitidas (datas distintas)
✓ Quarto permanece DISPONÍVEL
✓ Cálculo automático: valorTotal = dias × precoPorNoite
✓ Status inicial: ATIVA | PENDENTE | NAO_DEVOLVIDA
```

#### 2️⃣ Check-in
```
✓ Reserva deve estar ATIVA
✓ Deve ser a data de check-in ou posterior
✓ Quarto muda para OCUPADO ✅
```

#### 3️⃣ Devolução de Chave
```
✓ Reserva deve estar ATIVA
✓ Status muda para: DEVOLVIDA
```

#### 4️⃣ Check-out
```
✓ Reserva deve estar ATIVA
✓ Pagamento deve estar PAGO
✓ Chave deve estar DEVOLVIDA
✓ Quarto volta para DISPONÍVEL ✅
✓ Reserva muda para FINALIZADA
```

#### 5️⃣ Cancelamento
```
✓ Apenas reservas ATIVAS
✓ Se check-in feito → libera quarto (DISPONÍVEL)
✓ Se check-in não feito → quarto já está disponível
```

### Fluxo Ideal

```
1. Criar Reserva     → Quarto: DISPONÍVEL
2. Processar Pag.    → Status: PAGO
3. Fazer Check-in    → Quarto: OCUPADO ✅
4. Devolver Chave    → Chave: DEVOLVIDA
5. Fazer Check-out   → Quarto: DISPONÍVEL ✅ | Reserva: FINALIZADA
```

---

## 🧪 Testes

O projeto possui **cobertura completa de testes** para garantir qualidade e confiabilidade.

### Executar Todos os Testes
```bash
mvn test
```

### Executar Testes Específicos
```bash
# Testes de Repositório
mvn test -Dtest=HospedeRepositoryTest
mvn test -Dtest=QuartoRepositoryTest
mvn test -Dtest=ReservaRepositoryTest

# Testes de Serviço
mvn test -Dtest=HospedeServiceTest
mvn test -Dtest=QuartoServiceTest
mvn test -Dtest=ReservaServiceTest
```

### Estrutura dos Testes

#### 📦 Testes de Repositório (@DataJpaTest)
**Cobertura:** Operações de persistência e queries customizadas

```java
@DataJpaTest
@ActiveProfiles("test")
class HospedeRepositoryTest {
    // Testa findByCpf, existsByCpf, save, update, delete
    // Usa banco H2 em memória
}
```

**Cenários testados:**
- ✅ Busca por CPF/número/ID
- ✅ Verificação de existência
- ✅ Validação de unicidade
- ✅ Operações CRUD completas
- ✅ Queries customizadas (findByDataCheckIn, findByStatus)

#### 🎯 Testes de Serviço (@ExtendWith(MockitoExtension))
**Cobertura:** Regras de negócio e validações

```java
@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {
    @Mock
    private ReservaRepository reservaRepository;
    
    @InjectMocks
    private ReservaService reservaService;
    
    // Testa lógica de negócio com mocks
}
```

**Cenários testados:**
- ✅ Criação com sucesso e validações
- ✅ Conflitos de datas
- ✅ Status de reserva/pagamento/chave
- ✅ Cálculos (receita, taxa de ocupação)
- ✅ Exceções customizadas

### Perfil de Teste

**`src/test/resources/application-test.properties`**
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
```

---

## 🧪 Testando a API

### Via cURL

```bash
# Criar hóspede
curl -X POST http://localhost:8080/pousada/hospedes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "telefone": "(11) 98765-4321",
    "cpf": "12345678901"
  }'

# Criar quarto
curl -X POST http://localhost:8080/pousada/quartos \
  -H "Content-Type: application/json" \
  -d '{
    "numero": 101,
    "tipo": "CASAL",
    "precoPorNoite": 150.00
  }'

# Criar reserva
curl -X POST http://localhost:8080/pousada/reservas \
  -H "Content-Type: application/json" \
  -d '{
    "hospedeId": 1,
    "numeroQuarto": 101,
    "dataCheckIn": "2026-01-15",
    "dataCheckOut": "2026-01-18",
    "metodoPagamento": "PIX"
  }'

# Processar pagamento
curl -X PUT "http://localhost:8080/pousada/reservas/1/pagamento?metodoPagamento=PIX"

# Fazer check-in
curl -X PUT http://localhost:8080/pousada/reservas/1/check-in

# Taxa de ocupação
curl http://localhost:8080/pousada/reservas/taxa-ocupacao

# Receita do mês
curl "http://localhost:8080/pousada/reservas/receita?inicio=2026-01-01&fim=2026-01-31"
```

### Via Postman/Insomnia

Importe a coleção de endpoints disponível no repositório.

---

## 🔒 Tratamento de Erros

### Exception Handler Global

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ReservaNotFoundExceptionById.class)
    public ResponseEntity<ErrorResponse> handleReservaNotFound() {
        return ResponseEntity.status(404).body(...);
    }
    
    @ExceptionHandler(QuartoOcupadoException.class)
    public ResponseEntity<ErrorResponse> handleQuartoOcupado() {
        return ResponseEntity.status(409).body(...);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation() {
        return ResponseEntity.status(400).body(...);
    }
}
```

### Códigos de Status HTTP

| Status | Descrição |
|--------|-----------|
| `200` | Sucesso (GET, PUT) |
| `201` | Criado com sucesso (POST) |
| `204` | Deletado com sucesso (DELETE) |
| `400` | Validação falhou |
| `404` | Recurso não encontrado |
| `409` | Conflito (CPF/quarto duplicado, datas conflitantes) |
| `500` | Erro interno do servidor |

---

## 🚀 Melhorias Futuras

### Prioridade Alta
- [ ] **Autenticação e Autorização** (Spring Security + JWT)
- [ ] **Documentação Swagger** (SpringDoc OpenAPI)

### Prioridade Média
- [ ] Sistema de notificações (email/SMS)
- [ ] Relatórios avançados (PDF/Excel)

### Prioridade Baixa
- [ ] Dashboard administrativo
- [ ] Logs estruturados (ELK Stack)
- [ ] Métricas com Actuator + Prometheus
- [ ] CI/CD (GitHub Actions)
- [ ] Containerização (Docker + Docker Compose)
- [ ] Deploy na nuvem (AWS/Azure)

---

## 👨‍💻 Autor

**Yan Almeida**

- 🐱 GitHub: [@YanAlmeida](https://github.com/YanAlmeida)

---

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

<div align="center">

**Desenvolvido com ☕ por Yan Almeida**

⭐ Se este projeto foi útil para você, considere dar uma estrela no repositório!

</div>
