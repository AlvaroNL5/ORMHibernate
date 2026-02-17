-- =====================================================
-- Script SQL para creación de base de datos
-- Proyecto: Registro de Héroes de la Tierra Media
-- Motor: PostgreSQL
-- =====================================================

-- 1. Crear la base de datos
CREATE DATABASE "RegistroTierraMedia";

-- Conectarse a la BD antes de continuar:
-- En psql: \c "RegistroTierraMedia"

-- 2. Tabla de razas
CREATE TABLE razas (
                       id BIGSERIAL PRIMARY KEY,
                       nombre VARCHAR(50) NOT NULL,
                       habilidad_especial VARCHAR(150),
                       reino_origen VARCHAR(100),
                       esperanza_vida INTEGER
);

-- 3. Tabla de personajes
CREATE TABLE personajes (
                            id BIGSERIAL PRIMARY KEY,
                            nombre VARCHAR(100) NOT NULL,
                            edad INTEGER,
                            arma_principal VARCHAR(100),
                            nivel_poder DOUBLE PRECISION,
                            fecha_aparicion DATE,
                            raza_id BIGINT,
                            CONSTRAINT fk_personajes_raza FOREIGN KEY (raza_id) REFERENCES razas(id) ON DELETE SET NULL,
                            CONSTRAINT chk_nivel_poder CHECK (nivel_poder >= 1 AND nivel_poder <= 100)
);

-- 4. Datos iniciales
INSERT INTO razas (nombre, habilidad_especial, reino_origen, esperanza_vida) VALUES
                                                                                 ('Hobbit', 'Sigilo y resistencia', 'La Comarca', 100),
                                                                                 ('Elfo', 'Inmortalidad y vista aguda', 'Rivendel', 999999),
                                                                                 ('Humano', 'Adaptabilidad y valentía', 'Gondor', 80),
                                                                                 ('Enano', 'Fuerza y maestría en forja', 'Erebor', 250);

INSERT INTO personajes (nombre, edad, arma_principal, nivel_poder, fecha_aparicion, raza_id) VALUES
                                                                                                 ('Frodo Bolsón', 50, 'Dardo', 45.0, '2968-09-22', 1),
                                                                                                 ('Samsagaz Gamyi', 38, 'Espada corta', 42.0, '2980-04-06', 1),
                                                                                                 ('Legolas', 2931, 'Arco élfico', 80.0, '0087-01-01', 2),
                                                                                                 ('Arwen', 2700, 'Hadhafang', 78.0, '0241-01-01', 2),
                                                                                                 ('Aragorn', 87, 'Andúril', 85.0, '2931-03-01', 3),
                                                                                                 ('Éowyn', 24, 'Espada de Rohan', 65.0, '2995-01-01', 3),
                                                                                                 ('Gimli', 139, 'Hacha de batalla', 75.0, '2879-01-01', 4),
                                                                                                 ('Thorin Escudo de Roble', 195, 'Orcrist', 88.0, '2746-01-01', 4);