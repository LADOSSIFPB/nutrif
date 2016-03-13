--ALTER TABLE tb_refeicao_realizada CHANGE id_refeicao_realizada itemid INT(10) UNSIGNED NOT NULL AUTO_INCREMENT;

insert into tb_dia(id_dia, nm_dia) values (1, "Domingo");
insert into tb_dia(id_dia, nm_dia) values (2, "Segunda-feira");
insert into tb_dia(id_dia, nm_dia) values (3, "Terça-feira");
insert into tb_dia(id_dia, nm_dia) values (4, "Quarta-feira");
insert into tb_dia(id_dia, nm_dia) values (5, "Quinta-feira");
insert into tb_dia(id_dia, nm_dia) values (6, "Sexta-feira");
insert into tb_dia(id_dia, nm_dia) values (7, "Sábado");

INSERT INTO tb_refeicao (id_refeicao, nm_tipo_refeicao, hr_fim, hr_inicio) VALUES
(1, 'Almoço', '15:00:00', '11:00:00'),
(2, 'Jantar', '20:00:00', '17:00:00');

INSERT INTO tb_curso (nm_curso) VALUES
('Integrado em Informatica'),
('Integrado em Mineração');

INSERT INTO tb_aluno (nm_matricula, nm_aluno, fk_id_curso) VALUES
('20151234567', 'Maria da Conceição', 1);

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