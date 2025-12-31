CREATE TABLE tb_quarto (
    id BIGSERIAL PRIMARY KEY,
    numero_quarto INTEGER NOT NULL UNIQUE,
    tipo_de_quarto VARCHAR(50) NOT NULL,
    preco_por_noite NUMERIC(10,2) NOT NULL,
    status_quarto VARCHAR(30) NOT NULL
);
