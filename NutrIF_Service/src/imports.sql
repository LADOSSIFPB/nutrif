ALTER TABLE tb_refeicao_realizada CHANGE id_refeicao_realizada id_refeicao_realizada INT(10) UNSIGNED NOT NULL AUTO_INCREMENT;

insert into tb_dia(id_dia, nm_dia) values (1, "Domingo");
insert into tb_dia(id_dia, nm_dia) values (2, "Segunda-feira");
insert into tb_dia(id_dia, nm_dia) values (3, "Terça-feira");
insert into tb_dia(id_dia, nm_dia) values (4, "Quarta-feira");
insert into tb_dia(id_dia, nm_dia) values (5, "Quinta-feira");
insert into tb_dia(id_dia, nm_dia) values (6, "Sexta-feira");
insert into tb_dia(id_dia, nm_dia) values (7, "Sábado");

INSERT INTO tb_refeicao (id_refeicao, nm_tipo_refeicao, hr_fim, hr_inicio) VALUES
(1, 'Almoço', '14:00:00', '11:00:00'),
(2, 'Jantar', '19:00:00', '17:00:00');

INSERT INTO tb_curso(id_curso, nm_curso) VALUES('1', 'TELEMÁTICA');
INSERT INTO tb_curso(id_curso, nm_curso) VALUES('2', 'MINERAÇÃO');
INSERT INTO tb_curso(id_curso, nm_curso) VALUES('3', 'CONS DE EDIFÍCIOS');
INSERT INTO tb_curso(id_curso, nm_curso) VALUES('4', 'INFORMÁTICA');
INSERT INTO tb_curso(id_curso, nm_curso) VALUES('5', 'P E GÁS');
INSERT INTO tb_curso(id_curso, nm_curso) VALUES('6', 'MSI');
INSERT INTO tb_curso(id_curso, nm_curso) VALUES('7', 'MATEMÁTICA');
INSERT INTO tb_curso(id_curso, nm_curso) VALUES('8', 'SUB INF');
INSERT INTO tb_curso(id_curso, nm_curso) VALUES('9', 'FÍSICA');
INSERT INTO tb_curso(id_curso, nm_curso) VALUES('10', 'SUB MSI');
INSERT INTO tb_curso(id_curso, nm_curso) VALUES('11', 'SUB MIN');

INSERT INTO `tb_pessoa` (`tp_pessoa`, `id_pessoa`, `is_ativo`, `nm_email`, `nm_keyauth`, `nm_pessoa`, `nm_senha`) VALUES
(2, 1, b'0', 'maria.conceicao@gmail.com', '1E562D7F352A239CEAAE67381369B0030A79EEDF5CA6627D4B0A9BBDF0D154FA', 'Maria da Conceição Ferreira', 'MTIzNDU='),
(1, 2, b'1', 'admin@ifpb.edu.br', '5582389B437B31E4A59C469199DF3942CDBBC10C156193514D4EE54F474657A4', 'admin', 'aWZwYmluZm8=');

INSERT INTO `tb_aluno` (`id_aluno`, `nm_keyconfirmation`, `nm_matricula`, `id_pessoa`, `fk_id_curso`) VALUES
(1, '12345', '20151234567', 1, 1);

INSERT INTO `tb_funcionario` (`id_funcionario`, `id_pessoa`) VALUES
(2, 2);

INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '1', '1');
INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '1', '2');
INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '2', '1');
INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '2', '2');
INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '3', '1');
INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '3', '2');
INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '4', '1');
INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '4', '2');
INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '5', '1');
INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '5', '2');
INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '6', '1');
INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '6', '2');
INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '7', '1');
INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '7', '2');