CREATE DATABASE IF NOT EXISTS barbearia
DEFAULT CHARSET utf8mb4
DEFAULT COLLATE utf8mb4_general_ci;

USE barbearia;

CREATE TABLE IF NOT EXISTS clientes (
    cpf VARCHAR(11) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    nascimento YEAR NOT NULL,
    sexo ENUM('MASCULINO', 'FEMININO', 'NÃO INFORMADO'),
    total_atendimentos int UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (cpf)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS atendimentos (
    id INT AUTO_INCREMENT NOT NULL,
    cliente_cpf VARCHAR(11) NOT NULL,
    valor DECIMAL(5, 2) NOT NULL,
    data DATETIME,
    PRIMARY KEY(id),
    FOREIGN KEY(cliente_cpf) REFERENCES clientes(cpf)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS servicos (
    id INT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(20) NOT NULL,
    valor DECIMAL(5, 2) NOT NULL,
    PRIMARY KEY(id)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS atendimentos_servicos (
    id INT AUTO_INCREMENT NOT NULL,
    id_atendimento INT NOT NULL,
    id_servico INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(id_atendimento) REFERENCES atendimentos(id),
    FOREIGN KEY(id_servico) REFERENCES servicos(id)
) DEFAULT CHARSET = utf8mb4;

