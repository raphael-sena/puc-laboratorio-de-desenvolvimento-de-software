-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

INSERT INTO usuario (tipo, nome, senha) VALUES ('secretaria', 'ICEI', '123456');
INSERT INTO usuario (tipo, nome, senha) VALUES ('aluno', 'Raphael Sena', '123456');
INSERT INTO usuario (tipo, nome, senha) VALUES ('professor', 'Lesandro Ponciano', '123456');

INSERT INTO tb_disciplina (nome, status, tipo) VALUES ('Banco de Dados', 'ATIVA', 'OPTATIVA');
INSERT INTO tb_disciplina (nome, status, tipo) VALUES ('Teoria dos Grafos', 'ATIVA', 'OBRIGATORIA');
INSERT INTO tb_disciplina (nome, status, tipo) VALUES ('A', 'ATIVA', 'OPTATIVA');
INSERT INTO tb_disciplina (nome, status, tipo) VALUES ('B', 'ATIVA', 'OBRIGATORIA');
INSERT INTO tb_disciplina (nome, status, tipo) VALUES ('C', 'ATIVA', 'OPTATIVA');
INSERT INTO tb_disciplina (nome, status, tipo) VALUES ('D', 'ATIVA', 'OBRIGATORIA');
INSERT INTO tb_disciplina (nome, status, tipo) VALUES ('E', 'ATIVA', 'OPTATIVA');
INSERT INTO tb_disciplina (nome, status, tipo) VALUES ('F', 'ATIVA', 'OBRIGATORIA');
INSERT INTO tb_disciplina (nome, status, tipo) VALUES ('G', 'ATIVA', 'OPTATIVA');
INSERT INTO tb_disciplina (nome, status, tipo) VALUES ('H', 'ATIVA', 'OBRIGATORIA');
