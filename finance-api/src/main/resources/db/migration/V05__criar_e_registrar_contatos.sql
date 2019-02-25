CREATE TABLE contato (
	codigo BIGINT(20) PRIMARY KEY,
	codigo_pessoa BIGINT(20) NOT NULL,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(100) NOT NULL,
	telefone VARCHAR(20) NOT NULL,
	FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO contato 
(codigo, codigo_pessoa, nome, email, telefone) values 
(1, 1, 'Administrador', 'leocaliban@finance.com', '83 93598-9569'),
(2, 2, 'Maria Silva', 'maria@finance.com', '00 00000-0000');