CREATE TABLE tb_hospede (
    id BIGSERIAL PRIMARY KEY,
    nome_hospede VARCHAR(255) NOT NULL,
    telefone_hospede VARCHAR(50),
    cpf VARCHAR(14) NOT NULL UNIQUE
);
