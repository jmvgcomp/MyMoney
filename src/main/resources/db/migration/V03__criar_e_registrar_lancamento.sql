CREATE TABLE IF NOT EXISTS lancamento(
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

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Sea Bass - Fillets', '2018-08-18', '2019-01-25', '3134.87', 'Varicose veins of unsp lower extremity with ulcer of thigh', 'RECEITA', 1, 3);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Smoked Tongue', '2018-09-20', '2018-12-08', '1497.09', 'Retained (old) foreign body in vitreous body, bilateral', 'DESPESA', 3, 1);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Juice - Orangina', '2018-07-25', '2019-04-01', '4401.72', 'Other specified diseases of inner ear', 'RECEITA', 3, 9);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Cream Of Tartar', '2019-01-31', '2019-05-15', '653.99', 'Malignant neoplasm of upper lobe, right bronchus or lung', 'RECEITA', 4, 2);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Beef Flat Iron Steak', '2019-05-31', '2019-03-26', '918.51', 'Sltr-haris Type III physl fx upr end unsp tibia, 7thD', 'DESPESA', 3, 8);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Wine - Pinot Noir Mondavi Coastal', '2019-03-10', '2019-06-20', '1996.26', 'Torus fracture of upper end of right ulna, sequela', 'RECEITA', 1, 6);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Hold Up Tool Storage Rack', '2018-08-23', '2018-11-08', '1289.43', 'Unsp athscl nonaut bio bypass of the extrm, bilateral legs', 'RECEITA', 4, 3);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Cheese - Marble', '2019-02-25', '2018-09-27', '2277.68', 'Newborn small for gestational age, less than 500 grams', 'DESPESA', 2, 4);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Apple - Custard', '2018-08-25', '2019-05-09', '2464.15', 'LeFort III fracture, subs encntr for fracture with nonunion', 'RECEITA', 2, 4);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Mushroom - Lg - Cello', '2018-08-04', '2019-05-02', '4587.27', 'Oth injury of plantar artery of left foot, sequela', 'DESPESA', 3, 1);