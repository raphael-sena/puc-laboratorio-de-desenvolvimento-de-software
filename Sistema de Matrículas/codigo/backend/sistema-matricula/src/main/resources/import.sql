-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

INSERT INTO usuario (tipo, nome, senha) VALUES ('SECRETARIA', 'ICEI', '123456');
INSERT INTO usuario (tipo, nome, senha) VALUES ('ALUNO', 'Raphael Sena', '123456');
INSERT INTO usuario (tipo, nome, senha) VALUES ('PROFESSOR', 'Lesandro Ponciano', '123456');

INSERT INTO tb_disciplina (nome, status) VALUES ('Banco de Dados', 'ATIVA');
INSERT INTO tb_disciplina (nome, status) VALUES ('Teoria dos Grafos', 'ATIVA');
INSERT INTO tb_disciplina (nome, status) VALUES ('A', 'ATIVA');
INSERT INTO tb_disciplina (nome, status) VALUES ('B', 'ATIVA');
INSERT INTO tb_disciplina (nome, status) VALUES ('C', 'ATIVA');
INSERT INTO tb_disciplina (nome, status) VALUES ('D', 'ATIVA');
INSERT INTO tb_disciplina (nome, status) VALUES ('E', 'ATIVA');
INSERT INTO tb_disciplina (nome, status) VALUES ('F', 'ATIVA');
INSERT INTO tb_disciplina (nome, status) VALUES ('G', 'ATIVA');
INSERT INTO tb_disciplina (nome, status) VALUES ('H', 'ATIVA');
