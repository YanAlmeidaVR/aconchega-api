# 🏨 Sistema de Gestão de Pousada

API REST desenvolvida em Spring Boot para gerenciamento completo de pousadas, incluindo controle de hóspedes, quartos, reservas e métricas operacionais.

## ⚠️ Status do Projeto

**🚧 EM DESENVOLVIMENTO 🚧**

Este projeto está em fase de desenvolvimento ativo. As seguintes funcionalidades ainda precisam ser implementadas:

- [ ] **Autenticação e Autorização** (Spring Security + JWT)
- [ ] **Documentação da API** (Swagger/OpenAPI)
- [ ] Deploy em ambiente de produção

✅ **Funcionalidades Implementadas:**
- Sistema completo de gestão de hóspedes, quartos e reservas
- Testes unitários (Services) com Mockito
- Testes de integração (Repositories) com DataJpaTest
- Cobertura de testes para todas as camadas de repositório e serviço

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Funcionalidades](#-funcionalidades)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Instalação e Configuração](#-instalação-e-configuração)
- [Testes](#-testes)
- [Endpoints da API](#-endpoints-da-api)
- [Modelos de Dados](#-modelos-de-dados)
- [Regras de Negócio](#-regras-de-negócio)
- [Próximos Passos](#-próximos-passos)
- [Contribuindo](#-contribuindo)

## 🎯 Sobre o Projeto

O Sistema de Gestão de Pousada é uma aplicação completa para automatizar processos operacionais de estabelecimentos hoteleiros de pequeno e médio porte. O sistema oferece controle total sobre:

- Cadastro e gerenciamento de hóspedes
- Gestão de quartos e disponibilidade
- Controle completo do ciclo de reservas (check-in, check-out, cancelamento)
- Processamento de pagamentos
- Métricas e relatórios (taxa de ocupação, receita por período)

## 🛠 Tecnologias Utilizadas

### Backend
- **Java 17+**
- **Spring Boot 3.x**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- **Maven** - Gerenciamento de dependências
- **Banco de Dados** - JPA/Hibernate (compatível com PostgreSQL, MySQL, H2)
- **Lombok** - Redução de código boilerplate

### Testes
- **JUnit 5** - Framework de testes
- **Mockito** - Mock de dependências
- **AssertJ** - Assertions fluentes
- **Spring Boot Test** - Testes de integração
- **H2 Database** - Banco em memória para testes

## ✨ Funcionalidades

### 👤 Gestão de Hóspedes
- Cadastro de novos hóspedes com validação de CPF
- Listagem de todos os hóspedes
- Busca por ID
- Atualização de dados cadastrais
- Validação automática de CPF duplicado

### 🛏️ Gestão de Quartos
- Cadastro de quartos com tipo e preço
- Listagem de todos os quartos
- Busca por número do quarto
- Atualização de dados e preços
- Controle de status (DISPONÍVEL, OCUPADO, MANUTENÇÃO)
- Listagem de quartos disponíveis

### 📅 Gestão de Reservas
- Criação de reservas com validação de disponibilidade
- **Suporte a múltiplas reservas futuras no mesmo quarto**
- Processo completo de check-in (marca quarto como OCUPADO)
- Registro de devolução de chaves
- Processo completo de check-out (libera quarto para DISPONÍVEL)
- Cancelamento de reservas (com liberação automática de quarto se necessário)
- Processamento de pagamentos (múltiplos métodos)
- Listagem de todas as reservas
- Consulta de reservas do dia
- Histórico de reservas por quarto

### 📊 Métricas e Relatórios
- Cálculo de receita por período
- Taxa de ocupação em tempo real

## 📁 Estrutura do Projeto

```
src/
├── main/
│   └── java/dev/YanAlmeida/SistemaDeGestaoDePousada/
│       ├── controller/          # Controladores REST
│       │   ├── HospedeController.java
│       │   ├── QuartoController.java
│       │   └── ReservaController.java
│       │
│       ├── dto/                 # Data Transfer Objects
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
│       ├── entity/              # Entidades JPA
│       │   ├── HospedeModel.java
│       │   ├── QuartoModel.java
│       │   └── ReservaModel.java
│       │
│       ├── enums/               # Enumerações
│       │   ├── quarto/
│       │   │   ├── QuartoStatus.java
│       │   │   └── TipoQuarto.java
│       │   └── reserva/
│       │       ├── StatusReserva.java
│       │       ├── StatusPagamento.java
│       │       ├── StatusChave.java
│       │       └── MetodoPagamento.java
│       │
│       ├── exception/           # Exceções customizadas
│       │   ├── global/
│       │   │   ├── GlobalExceptionHandler.java
│       │   │   └── ErrorResponse.java
│       │   ├── hospede/
│       │   ├── quarto/
│       │   └── reserva/
│       │
│       ├── mapper/              # Conversores DTO ↔ Entity
│       │   ├── HospedeMapper.java
│       │   ├── QuartoMapper.java
│       │   └── ReservaMapper.java
│       │
│       ├── repository/          # Repositórios JPA
│       │   ├── HospedeRepository.java
│       │   ├── QuartoRepository.java
│       │   └── ReservaRepository.java
│       │
│       └── service/             # Lógica de negócio
│           ├── HospedeService.java
│           ├── QuartoService.java
│           └── ReservaService.java
│
└── test/
    └── java/dev/YanAlmeida/SistemaDeGestaoDePousada/
        ├── repository/          # Testes de integração com @DataJpaTest
        │   ├── HospedeRepositoryTest.java
        │   ├── QuartoRepositoryTest.java
        │   └── ReservaRepositoryTest.java
        │
        ├── service/             # Testes unitários com Mockito
        │   ├── HospedeServiceTest.java
        │   ├── QuartoServiceTest.java
        │   └── ReservaServiceTest.java
        │
        └── SistemaDeGestaoDePousadaApplicationTests.java
```

## 🚀 Instalação e Configuração

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- Banco de dados (PostgreSQL, MySQL ou H2)

### Passos para Instalação

1. **Clone o repositório**
```bash
git clone https://github.com/YanAlmeida/sistema-gestao-pousada.git
cd sistema-gestao-pousada
```

2. **Configure o banco de dados**

Edite o arquivo `src/main/resources/application.properties`:

```properties
# Configuração do banco de dados
spring.datasource.url=jdbc:postgresql://localhost:5432/pousada_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

3. **Compile o projeto**
```bash
mvn clean install
```

4. **Execute a aplicação**
```bash
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

## 🧪 Testes

O projeto possui cobertura completa de testes para as camadas de repositório e serviço.

### Executar todos os testes
```bash
mvn test
```

### Executar testes específicos
```bash
# Testes de repositório
mvn test -Dtest=HospedeRepositoryTest
mvn test -Dtest=QuartoRepositoryTest
mvn test -Dtest=ReservaRepositoryTest

# Testes de serviço
mvn test -Dtest=HospedeServiceTest
mvn test -Dtest=QuartoServiceTest
mvn test -Dtest=ReservaServiceTest
```

### Estrutura dos Testes

#### 📦 Testes de Repositório (@DataJpaTest)
Testes de integração com banco H2 em memória, validando:
- Operações CRUD completas
- Queries customizadas
- Relacionamentos entre entidades
- Validações de dados
- Comportamento do JPA/Hibernate

**Exemplos de cenários testados:**
- `HospedeRepositoryTest`: findByCpf, existsByCpf, validações de CPF único
- `QuartoRepositoryTest`: findByNumeroQuarto, findByQuartoStatus, validações de número único
- `ReservaRepositoryTest`: findByDataCheckIn, findByDataCheckOut, findByCpfHospede, findByNumeroQuarto

#### 🎯 Testes de Serviço (@ExtendWith(MockitoExtension))
Testes unitários com mocks, validando:
- Lógica de negócio
- Tratamento de exceções
- Fluxos de operações
- Validações de regras

**Exemplos de cenários testados:**
- Cadastro com sucesso e com CPF/número duplicado
- Validações de dados obrigatórios
- Operações de busca, atualização e exclusão
- Cálculos (receita, taxa de ocupação)
- Fluxo completo de reservas (criação → check-in → check-out)

### Perfil de Teste

O projeto utiliza um perfil de teste separado:

**`src/test/resources/application-test.properties`**
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
```

## 📡 Endpoints da API

### 👤 Hóspedes (`/pousada/hospedes`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/pousada/hospedes` | Cadastrar novo hóspede |
| GET | `/pousada/hospedes` | Listar todos os hóspedes |
| GET | `/pousada/hospedes/{id}` | Buscar hóspede por ID |
| PUT | `/pousada/hospedes/{id}` | Atualizar dados do hóspede |

**Exemplo de requisição - Cadastrar hóspede:**
```json
POST /pousada/hospedes
{
  "nome": "João Silva",
  "telefone": "(11) 98765-4321",
  "cpf": "123.456.789-00"
}
```

### 🛏️ Quartos (`/pousada/quartos`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/pousada/quartos` | Cadastrar novo quarto |
| GET | `/pousada/quartos` | Listar todos os quartos |
| GET | `/pousada/quartos/{numero}` | Buscar quarto por número |
| PUT | `/pousada/quartos/{numero}` | Atualizar dados do quarto |
| PUT | `/pousada/quartos/{numero}/status` | Atualizar status do quarto |
| GET | `/pousada/quartos/disponiveis` | Listar quartos disponíveis |

**Exemplo de requisição - Cadastrar quarto:**
```json
POST /pousada/quartos
{
  "numero": 101,
  "tipo": "CASAL",
  "precoPorNoite": 150.00
}
```

### 📅 Reservas (`/pousada/reservas`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/pousada/reservas` | Criar nova reserva |
| PUT | `/pousada/reservas/{id}/check-in` | Realizar check-in |
| PUT | `/pousada/reservas/{id}/devolucao-chave` | Registrar devolução de chave |
| PUT | `/pousada/reservas/{id}/check-out` | Realizar check-out |
| PUT | `/pousada/reservas/{id}/cancelar` | Cancelar reserva |
| PUT | `/pousada/reservas/{id}/pagamento` | Processar pagamento |
| GET | `/pousada/reservas` | Listar todas as reservas |
| GET | `/pousada/reservas/hoje` | Listar reservas do dia |
| GET | `/pousada/reservas/quarto/{numeroQuarto}` | Listar reservas por quarto |
| GET | `/pousada/reservas/receita?inicio=data&fim=data` | Calcular receita por período |
| GET | `/pousada/reservas/taxa-ocupacao` | Consultar taxa de ocupação |

**Exemplo de requisição - Criar reserva:**
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

**Exemplo de requisição - Processar pagamento:**
```
PUT /pousada/reservas/1/pagamento?metodoPagamento=PIX
```

## 📊 Modelos de Dados

### Hóspede
```java
{
  "id": Long,
  "nome": String,
  "telefone": String,
  "cpf": String (formato: 000.000.000-00)
}
```

### Quarto
```java
{
  "id": Long,
  "numero": Integer,
  "tipo": Enum (SOLTEIRO, CASAL, TRIPLA),
  "precoPorNoite": BigDecimal,
  "status": Enum (DISPONIVEL, OCUPADO, MANUTENÇÃO)
}
```

### Reserva
```java
{
  "id": Long,
  "nomeHospede": String,
  "cpfHospede": String,
  "telefoneHospede": String,
  "numeroQuarto": Integer,
  "tipoQuarto": Enum,
  "dataCheckIn": LocalDate,
  "dataCheckOut": LocalDate,
  "valorTotal": BigDecimal,
  "statusReserva": Enum (ATIVA, CANCELADA, NAO_APARECEU, FINALIZADA),
  "metodoPagamento": Enum (DINHEIRO, CARTAO_CREDITO, CARTAO_DEBITO, PIX),
  "statusPagamento": Enum (PENDENTE, PAGO, REEMBOLSADO),
  "statusChave": Enum (NAO_DEVOLVIDA, DEVOLVIDA)
}
```

## 📜 Regras de Negócio

### Hóspedes
- CPF deve ser único no sistema
- Validação automática de formato de CPF
- Campos obrigatórios: nome, telefone, CPF

### Quartos
- Número do quarto deve ser único
- Status inicial: DISPONÍVEL
- Preço por noite deve ser maior que zero

### Reservas

#### Criação de Reserva
- Data de check-out deve ser posterior ao check-in
- Não é permitido criar reservas conflitantes no mesmo quarto
- **Múltiplas reservas futuras são permitidas no mesmo quarto** (datas não conflitantes)
- O quarto **NÃO** é marcado como OCUPADO na criação (permanece DISPONÍVEL)
- Valor total calculado automaticamente: (dias × preço por noite)

#### Check-in
- Só pode ser realizado em reservas ATIVAS
- Deve ser executado na data de check-in ou após
- **O quarto é marcado como OCUPADO apenas no check-in**

#### Check-out
- Requer reserva em status ATIVA
- Pagamento deve estar processado (status PAGO)
- Chave deve estar devolvida
- **O quarto é liberado (DISPONÍVEL) após check-out bem-sucedido**
- Reserva muda para status FINALIZADA

#### Cancelamento
- Apenas reservas ATIVAS podem ser canceladas
- **Se check-in já foi feito, o quarto é liberado automaticamente**
- **Se check-in não foi feito, o quarto permanece DISPONÍVEL**

#### Pagamentos
- Métodos aceitos: DINHEIRO, CARTAO_CREDITO, CARTAO_DEBITO, PIX
- Status inicial: PENDENTE
- Somente reservas ATIVAS podem processar pagamento

### Fluxo Ideal de Reserva

```
1. Criar Reserva
   └─> Quarto: DISPONÍVEL
   └─> Status: ATIVA | Pagamento: PENDENTE | Chave: NAO_DEVOLVIDA

2. Processar Pagamento
   └─> Status: ATIVA | Pagamento: PAGO | Chave: NAO_DEVOLVIDA

3. Fazer Check-in (na data)
   └─> Quarto: OCUPADO ✅
   └─> Status: ATIVA | Pagamento: PAGO | Chave: NAO_DEVOLVIDA

4. Devolver Chave
   └─> Status: ATIVA | Pagamento: PAGO | Chave: DEVOLVIDA

5. Fazer Check-out
   └─> Quarto: DISPONÍVEL ✅
   └─> Status: FINALIZADA | Pagamento: PAGO | Chave: DEVOLVIDA
```

## 🔜 Próximos Passos

### Funcionalidades Prioritárias

1. **Autenticação e Segurança** 🔒
   - Implementar Spring Security
   - Autenticação JWT
   - Controle de acesso por roles (ADMIN, RECEPCIONISTA, GERENTE)
   - Auditoria de ações

2. **Documentação da API** 📚
   - Integrar Swagger/OpenAPI
   - Documentar todos os endpoints
   - Adicionar exemplos de requisições e respostas
   - Gerar documentação interativa

## 🤝 Contribuindo

Contribuições são bem-vindas! Para contribuir:

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

### Padrões de Código

- Siga as convenções Java
- Escreva testes para novas funcionalidades
- Mantenha a cobertura de testes acima de 80%
- Use nomes descritivos para variáveis e métodos
- Documente código complexo

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👨‍💻 Autor

**Yan Almeida**

- GitHub: [@YanAlmeida](https://github.com/YanAlmeida)

## 📞 Suporte

Para reportar bugs ou sugerir melhorias, abra uma [issue](https://github.com/YanAlmeida/sistema-gestao-pousada/issues) no GitHub.

---

⭐ Se este projeto foi útil para você, considere dar uma estrela no repositório!
