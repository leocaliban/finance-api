CREATE TABLE estado (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO estado 
(codigo, nome) values 
(1, 'Acre'),
(2, 'Alagoas'),
(3, 'Amazonas'),
(4, 'Amapá'),
(5, 'Bahia'),
(6, 'Ceará'),
(7, 'Distrito Federal'),
(8, 'Espírito Santo'),
(9, 'Goiás'),
(10, 'Maranhão'),
(11, 'Minas Gerais'),
(12, 'Mato Grosso do Sul'),
(13, 'Mato Grosso'),
(14, 'Pará'),
(15, 'Paraíba'),
(16, 'Pernambuco'),
(17, 'Piauí'),
(18, 'Paraná'),
(19, 'Rio de Janeiro'),
(20, 'Rio Grande do Norte'),
(21, 'Rondônia'),
(22, 'Roraima'),
(23, 'Rio Grande do Sul'),
(24, 'Santa Catarina'),
(25, 'Sergipe'),
(26, 'São Paulo'),
(27, 'Tocantins');

CREATE TABLE cidade (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
  codigo_estado BIGINT(20) NOT NULL,
  FOREIGN KEY (codigo_estado) REFERENCES estado(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO cidade 
(codigo, nome, codigo_estado) VALUES 
(1, 'Belo Horizonte', 11),
(2, 'Uberlândia', 11),
(3, 'Uberaba', 11),
(4, 'São Paulo', 26),
(5, 'Campinas', 26),
(6, 'Rio de Janeiro', 19),
(7, 'Angra dos Reis', 19),
(8, 'Goiânia', 9),
(9, 'Caldas Novas', 9);

ALTER TABLE pessoa DROP COLUMN cidade;
ALTER TABLE pessoa DROP COLUMN estado;
ALTER TABLE pessoa ADD COLUMN codigo_cidade BIGINT(20);
ALTER TABLE pessoa ADD CONSTRAINT fk_pessoa_cidade FOREIGN KEY (codigo_cidade) REFERENCES cidade(codigo);

UPDATE pessoa SET codigo_cidade = 2;