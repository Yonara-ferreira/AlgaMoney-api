CREATE TABLE pessoa (
	codigo		BIGINT(20) 	PRIMARY KEY AUTO_INCREMENT,
	nome 		VARCHAR(50) NOT NULL,
	ativo 		BOOLEAN 	NOT NULL,
	logradouro  VARCHAR(60),
	numero		VARCHAR(10),
	complemento	VARCHAR(50),
	bairro		VARCHAR(50),
	cep			VARCHAR(10),
	cidade		VARCHAR(50),
	estado		VARCHAR(2)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) 
VALUES 
('Ricardo Santos', 1, 'Rua das Acácias', 567, 'Casa 1', 'Jardim das Oliveiras', '34567-890', 'Campinas', 'SP'),
('Carla Fernandes', 1, 'Avenida dos Coqueiros', 1010, 'Sala 2', 'Praia do Forte', '78901-234', 'Natal', 'RN'),
('Pedro Lima', 0, 'Rua dos Pinheiros', 987, 'Bloco C', 'Jardim Europa', '01234-567', 'São Paulo', 'SP'),
('Mariana Souza', 1, 'Rua das Laranjeiras', 321, '', 'Centro', '45678-901', 'Florianópolis', 'SC'),
('Lucas Alves', 1, 'Avenida dos Sabiás', 246, 'Casa 3', 'Jardim das Flores', '65432-109', 'Belo Horizonte', 'MG'),
('João da Silva', 1, 'Rua das Flores', 123, 'Apto 101', 'Centro', '12345-678', 'São Paulo', 'SP'),
('Maria Souza', 1, 'Avenida dos Pássaros', 456, '', 'Jardim Primavera', '54321-098', 'Rio de Janeiro', 'RJ'),
('José Pereira', 0, 'Rua das Árvores', 789, '', 'Vila Verde', '45678-912', 'Belo Horizonte', 'MG'),
('Ana Oliveira', 1, 'Rua das Pedras', 1010, '', 'Costa Azul', '23456-789', 'Salvador', 'BA'),
('Fernanda Santos', 1, 'Avenida dos Girassóis', 222, 'Casa 2', 'Jardim das Flores', '87654-321', 'Curitiba', 'PR');

	