CREATE TABLE lancamento (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL,
	data_vencimento DATE NOT NULL,
	data_pagamento DATE,
	valor DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(100),
	tipo VARCHAR(20) NOT NULL,
	codigo_categoria BIGINT(20) NOT NULL,
	codigo_pessoa BIGINT(20) NOT NULL,
	FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo),
	FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO lancamento 
(descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values 
('Salário mensal', '2019-02-27', null, 6500.00, 'Distribuição de lucros', 'RECEITA', 1, 1),
('Supermercado', '2019-02-10', '2019-02-01', 100.32, null, 'DESPESA', 2, 2),
('Academia', '2019-03-10', null, 120, null, 'DESPESA', 3, 3),
('Conta de luz', '2019-02-10', '2019-02-10', 110.44, null, 'DESPESA', 3, 4),
('Conta de água', '2019-02-15', null, 200.30, null, 'DESPESA', 3, 5),
('Restaurante', '2019-02-14', '2019-02-14', 1010.32, null, 'DESPESA', 4, 6),
('Venda vídeo game', '2019-02-01', null, 500, null, 'RECEITA', 1, 7),
('Clube', '2019-02-07', '2019-02-05', 400.32, null, 'DESPESA', 4, 8),
('Impostos', '2019-03-10', null, 123.64, 'Multas', 'DESPESA', 3, 9),
('Multa', '2019-03-10', null, 665.33, null, 'DESPESA', 5, 10),
('Padaria', '2019-02-28', null, 8.32, null, 'DESPESA', 1, 5),
('Papelaria', '2019-02-10', '2019-03-10', 2100.32, null, 'DESPESA', 5, 4),
('Almoço', '2019-02-09', null, 1040.32, null, 'DESPESA', 4, 3),
('Café', '2019-02-20', '2019-02-18', 4.32, null, 'DESPESA', 4, 2),
('Lanche', '2019-03-10', null, 10.20, null, 'DESPESA', 4, 1);