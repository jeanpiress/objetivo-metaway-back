use objetivo_back_metaway;
create table usuario (
                           id bigint not null auto_increment,
                           cpf varchar(14) not null,
                           nome varchar(256) not null,
                           senha varchar(256) not null,
                           perfil varchar(30) not null,

                           primary key (id)
) engine=InnoDB default charset=utf8;

create table permissao(
                          id bigint not null auto_increment,
                          nome varchar(80) not null,

                          PRIMARY KEY (id)
)engine=InnoDB default charset=utf8;

CREATE TABLE usuario_permissao (
                                   usuario_id bigint NOT NULL,
                                   permissao_id bigint NOT NULL,
                                   PRIMARY KEY (usuario_id, permissao_id)
)engine=InnoDB default charset=utf8;

create table cliente (
                         id bigint not null auto_increment,
                         nome varchar(60) not null,
                         data_cadastro date,
                         cpf varchar(14),


                         primary key (id)
) engine=InnoDB default charset=utf8;


create table endereco (
                         id bigint not null auto_increment,
                         cliente_id bigint not null,
                         logradouro varchar(256),
                         cidade varchar(256),
                         bairro varchar(256),
                         complemento varchar(256),
                         tag varchar(50),

                         primary key (id)
) engine=InnoDB default charset=utf8;

create table contato (
                         id bigint not null auto_increment,
                         cliente_id bigint not null,
                         tag varchar(50),
                         data_cadastro date,
                         tipo varchar(50),
                         valor varchar(50),

                         primary key (id)
) engine=InnoDB default charset=utf8;


create table atendimento (
                         id bigint not null auto_increment,
                         pet_id bigint not null,
                         descricao varchar(256),
                         valor DECIMAL(10,2),
                         data TIMESTAMP not null,

                         primary key (id)
) engine=InnoDB default charset=utf8;

create table pet (
                             id bigint not null auto_increment,
                             cliente_id bigint not null,
                             raca_id bigint not null,
                             data_nascimento date,
                             nome varchar(60) not null,

                             primary key (id)
) engine=InnoDB default charset=utf8;

create table raca (
                      id bigint not null auto_increment,
                      descricao varchar(256) not null,

                      primary key (id)
) engine=InnoDB default charset=utf8;

alter table usuario_permissao add constraint fk_usuario_permissao_usuario
    FOREIGN KEY (usuario_id) REFERENCES usuario(id);

alter table usuario_permissao add constraint fk_usuario_permissao_permissao
    FOREIGN KEY (permissao_id) REFERENCES permissao(id);

alter table endereco add constraint fk_endereco_cliente
    FOREIGN KEY (cliente_id) REFERENCES cliente(id);

alter table contato add constraint fk_contato_cliente
    FOREIGN KEY (cliente_id) REFERENCES cliente(id);

alter table atendimento add constraint fk_atendimento_pet
    FOREIGN KEY (pet_id) REFERENCES pet(id);

alter table pet add constraint fk_pet_cliente
    FOREIGN KEY (cliente_id) REFERENCES cliente(id);

alter table pet add constraint fk_pet_raca
    FOREIGN KEY (raca_id) REFERENCES raca(id);