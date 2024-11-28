USE db_material_construcao;

-- Inserir Clientes
INSERT INTO TB_CLIENTE (NO_CLIENTE, NR_RG, NR_CPF, TP_CLIENTE)
VALUES 
('João Silva', 123456789, '12345678901', 'NOVO'),
('Maria Oliveira', 987654321, '10987654321', 'ANTIGO'),
('Carlos Souza', 456789123, '45678912300', 'NOVO'),
('Ana Costa', 321654987, '32165498700', 'ANTIGO'),
('Paula Lima', 789123456, '78912345600', 'NOVO');

-- Inserir Endereços de Clientes
INSERT INTO TB_ENDERECO_CLIENTE (ID_CLIENTE, SG_UF, NO_ENDERECO, NO_CIDADE)
VALUES 
(1, 'SP', 'Rua das Flores, 123', 'São Paulo'),
(2, 'RJ', 'Av. Atlântica, 456', 'Rio de Janeiro'),
(3, 'MG', 'Praça Sete de Setembro, 789', 'Belo Horizonte'),
(4, 'DF', 'Quadra 5, Bloco A', 'Brasília'),
(5, 'BA', 'Rua da Paz, 101', 'Salvador');

-- Inserir Telefones de Clientes
INSERT INTO TB_TELEFONE_CLIENTE (ID_CLIENTE, NR_TELEFONE)
VALUES 
(1, '(11) 98765-4321'),
(2, '(21) 99876-5432'),
(3, '(31) 91234-5678'),
(4, '(61) 99999-1111'),
(5, '(71) 98888-2222');


-- Inserir Produtos
INSERT INTO TB_PRODUTO (ID_LOJA, NO_PRODUTO, QT_PRODUTO_ESTOQUE, VL_PRODUTO_UNITARIO)
VALUES 
(1, 'Cimento 50kg', 100, 30.00),
(1, 'Tijolo', 500, 0.80),
(1, 'Areia Fina 20kg', 200, 12.50),
(1, 'Prego 15mm', 1000, 0.05),
(1, 'Bloco de Concreto', 300, 2.50);

-- Inserir Vendas
INSERT INTO TB_VENDA (ID_CLIENTE, DH_PAGAMENTO, VL_TOTAL_VENDA, IC_PAGO)
VALUES 
(1, '2024-11-20', 150.00, 1),
(2, NULL, 300.00, 0),
(3, '2024-11-21', 450.00, 1),
(4, '2024-11-23', 200.00, 1),
(5, '2024-11-24', 100.00, 1);

-- Inserir Produtos nas Vendas
INSERT INTO TB_PRODUTO_VENDA (ID_VENDA, CD_PRODUTO, QT_PRODUTO_RETIRADO, VL_TOTAL_PRODUTO)
VALUES 
(1, 1, 5, 150.00),
(2, 2, 300, 240.00),
(2, 3, 10, 60.00),
(4, 4, 100, 5.00),
(5, 5, 20, 50.00);


