CREATE DATABASE "curso-jsp"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       CONNECTION LIMIT = -1;
	   
	   
	   
CREATE SEQUENCE usersequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 8
  CACHE 1;
ALTER TABLE usersequence
  OWNER TO postgres;
	   
	   
CREATE TABLE usuario
(
  id bigint NOT NULL DEFAULT nextval('usersequence'::regclass),
  login character varying(500),
  senha character varying(20),
  CONSTRAINT usuario_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuario
  OWNER TO postgres;
  
 
ALTER TABLE usuario ADD COLUMN nome character varying(500); 
ALTER TABLE usuario ADD COLUMN telefone character varying(20); 
  
  INSERT INTO usuario(
            id, login, senha)
    VALUES (1, 'alex', 'alex');

INSERT INTO usuario(
            id, login, senha)
    VALUES (2, 'admin', 'admin');

    
     CREATE SEQUENCE produtosequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 8
  CACHE 1;
ALTER TABLE produtosequence
  OWNER TO postgres;
	      
  
  CREATE TABLE produto
(
  id bigint NOT NULL DEFAULT nextval('produtosequence'::regclass),
  nome character varying(500),
  quantidade numeric(10,4),
  valor numeric(10,4),
  CONSTRAINT produto_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE produto
  OWNER TO postgres;

ALTER TABLE public.usuario ADD COLUMN cep character varying(200);
ALTER TABLE public.usuario ADD COLUMN rua character varying(200);
ALTER TABLE public.usuario ADD COLUMN bairro character varying(200);
ALTER TABLE public.usuario ADD COLUMN cidade character varying(200);
ALTER TABLE public.usuario ADD COLUMN estado character varying(200);
ALTER TABLE public.usuario ADD COLUMN ibge character varying(200); 


CREATE TABLE telefone
(
  id bigint NOT NULL DEFAULT nextval('fonesequence'::regclass),
  numero character varying(500),
  tipo character varying(500),

  usuario bigint NOT NULL, 
  
  CONSTRAINT fone_pkey PRIMARY KEY (id)
) 
WITH(
	OIDS=FALSE
);
ALTER TABLE telefone
OWNER TO postgres;

ALTER TABLE usuario ADD COLUMN fotobase64 text;
ALTER TABLE usuario ADD COLUMN contenttype text;

ALTER TABLE usuario ADD COLUMN contenttypeCurriculo text;
ALTER TABLE usuario ADD COLUMN curriculoBase64 text;
ALTER TABLE usuario ADD COLUMN fotobase64Miniatura text;
ALTER TABLE usuario ADD COLUMN ativo boolean;
ALTER TABLE usuario ADD COLUMN sexo character varying (50);
ALTER TABLE usuario ADD COLUMN perfil character varying (20);

CREATE SEQUENCE categoriasequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1;
  
  
  
   
CREATE TABLE categoria
(
  id bigint NOT NULL DEFAULT nextval('categoriasequence'::regclass),
  nome character varying(500),
  CONSTRAINT categoria_pkey PRIMARY KEY (id)
);


INSERT INTO public.categoria(nome) VALUES ('Bebida');
INSERT INTO public.categoria(nome) VALUES ('Higiene');
INSERT INTO public.categoria(nome) VALUES ('Quimico');
INSERT INTO public.categoria(nome) VALUES ('Grãos');
INSERT INTO public.categoria(nome) VALUES ('Chocolate');


ALTER TABLE produto ADD COLUMN categoria_id bigint;


ALTER TABLE produto ADD constraint categoria_fk foreign key (categoria_id) references categoria (id)