insert into tb_dia(id_dia, nm_dia) values (1, "Domingo");
insert into tb_dia(id_dia, nm_dia) values (2, "Segunda-feira");
insert into tb_dia(id_dia, nm_dia) values (3, "Terça-feira");
insert into tb_dia(id_dia, nm_dia) values (4, "Quarta-feira");
insert into tb_dia(id_dia, nm_dia) values (5, "Quinta-feira");
insert into tb_dia(id_dia, nm_dia) values (6, "Sexta-feira");
insert into tb_dia(id_dia, nm_dia) values (7, "Sábado");

insert into tb_refeicao values (1, "Almoço");
insert into tb_refeicao values (2, "Jantar");

INSERT INTO tb_curso (nm_curso) VALUES
('Integrado em Informatica'),
('Integrado em Mineração');

INSERT INTO tb_aluno (nm_matricula, nm_aluno, fk_id_curso) VALUES
('20151234567', 'Maria da Conceição', 1);

INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '1', '1');
INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '1', '2');
INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '2', '1');
INSERT INTO tb_dia_refeicao (fk_id_aluno, fk_id_dia, fk_id_refeicao) VALUES ('1', '2', '2');

