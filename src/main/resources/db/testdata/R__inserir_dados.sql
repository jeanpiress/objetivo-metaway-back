USE objetivo_back_metaway;

set foreign_key_checks = 0;
SET sql_safe_updates = 0;

delete from usuario;
delete from permissao;
delete from usuario_permissao;
delete from cliente;
delete from endereco;
delete from contato;
delete from atendimento;
delete from pet;
delete from raca;

SET foreign_key_checks = 1;
SET sql_safe_updates = 1;

alter table usuario auto_increment = 1;
alter table permissao auto_increment = 1;
alter table usuario_permissao auto_increment = 1;
alter table cliente auto_increment = 1;
alter table endereco auto_increment = 1;
alter table contato auto_increment = 1;
alter table atendimento auto_increment = 1;
alter table pet auto_increment = 1;
alter table raca auto_increment = 1;


INSERT INTO permissao (nome) VALUES ('ADMIN'), ('CLIENTE');

INSERT INTO usuario (cpf, nome, senha, perfil) VALUES
                                           ('12345678900', 'Admin User', "$2a$12$yXbDuQ0PtltBXPiP8wnhrOYfY647RoyNOOcD70kBnSzwgESEISFE.", 'ADMIN'),
                                           ('98765432100', 'Cliente User', "$2a$12$yXbDuQ0PtltBXPiP8wnhrOYfY647RoyNOOcD70kBnSzwgESEISFE.", 'CLIENTE');

INSERT INTO usuario_permissao (usuario_id, permissao_id) VALUES
                                                             (1, 1),
                                                             (1, 2),
                                                             (2, 2);

INSERT INTO cliente (id, nome, data_cadastro, cpf) VALUES (1, 'Admin User', CURDATE(), '12345678900'),
                                                          (2, 'Cliente User', CURDATE(), '98765432100');

INSERT INTO endereco (cliente_id, logradouro, cidade, bairro, complemento, tag) VALUES (1, 'Rua A, 123', 'Uberlandia', 'Centro', 'Apto 101', 'Residencial'),
                                                                                       (2, 'Rua B, 321', 'Uberlandia', 'Centro', 'Apto 202', 'Residencial');

INSERT INTO contato (cliente_id, tag, data_cadastro, tipo, valor) VALUES (1, 'tag', CURDATE(), 'TELEFONE', '(34) 98765-4321'),
                                                                         (2, 'tag', CURDATE(), 'EMAIL', 'cliente@metaway.com');

INSERT INTO raca (descricao) VALUES('Labrador'),
                                   ('Poodle');

INSERT INTO pet (cliente_id, raca_id, data_nascimento, nome) VALUES (1, 1, '2020-05-10', 'Rex'),
                                                                    (2, 1, '2022-02-05', 'Bob');


INSERT INTO atendimento (pet_id, descricao, valor, data) VALUES
                                                             (1, 'Banho e Tosa', 50.00, NOW());

