CREATE TABLE tb_reserva (
    id BIGSERIAL PRIMARY KEY,

    nome_hospede VARCHAR(255) NOT NULL,
    cpf_hospede VARCHAR(14) NOT NULL,
    telefone_hospede VARCHAR(50),

    numero_quarto INTEGER NOT NULL,
    tipo_quarto VARCHAR(50) NOT NULL,

    data_check_in DATE NOT NULL,
    data_check_out DATE NOT NULL,

    valor_total NUMERIC(10,2),

    status_reserva VARCHAR(30) NOT NULL,
    status_pagamento VARCHAR(30) NOT NULL,
    metodo_pagamento VARCHAR(30),
    status_chave VARCHAR(30) NOT NULL
);
