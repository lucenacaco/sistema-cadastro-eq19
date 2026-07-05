CREATE TABLE usuario (
                         id SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         senha VARCHAR(100) NOT NULL
);

CREATE TABLE cliente (
                         id SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         cpf VARCHAR(14) NOT NULL UNIQUE,
                         telefone VARCHAR(20) NOT NULL,
                         endereco VARCHAR(255) NOT NULL
);

CREATE TABLE servico (
                         id SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         valor DECIMAL(10,2) NOT NULL,
                         duracao INTEGER NOT NULL
);

CREATE TABLE agendamento (
                             id SERIAL PRIMARY KEY,
                             cliente_id INTEGER NOT NULL,
                             servico_id INTEGER NOT NULL,
                             data DATE NOT NULL,
                             hora TIME NOT NULL,
                             status VARCHAR(20) NOT NULL,
                             observacao VARCHAR(255),

                             CONSTRAINT fk_cliente
                                 FOREIGN KEY (cliente_id)
                                     REFERENCES cliente(id),

                             CONSTRAINT fk_servico
                                 FOREIGN KEY (servico_id)
                                     REFERENCES servico(id)
);