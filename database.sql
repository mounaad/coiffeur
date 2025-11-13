CREATE DATABASE IF NOT EXISTS coiffdb;
USE coiffdb;

-- Table Client
CREATE TABLE client (
    id_client INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    telephone VARCHAR(20),
    mot_de_passe VARCHAR(255),
    points_fidelite INT DEFAULT 0
);

-- Table Coiffeur
CREATE TABLE coiffeur (
    id_coiffeur INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100),
    adresse VARCHAR(255),
    email VARCHAR(100) UNIQUE,
    mot_de_passe VARCHAR(255),
    horaires VARCHAR(100),
    statut ENUM('pending','valide') DEFAULT 'pending'
);

-- Table RendezVous
CREATE TABLE rendezvous (
    id_rdv INT AUTO_INCREMENT PRIMARY KEY,
    id_client INT,
    id_coiffeur INT,
    date_rdv DATE,
    heure_rdv TIME,
    statut ENUM('en_attente','confirme','annule') DEFAULT 'en_attente',
    FOREIGN KEY (id_client) REFERENCES client(id_client) ON DELETE CASCADE,
    FOREIGN KEY (id_coiffeur) REFERENCES coiffeur(id_coiffeur) ON DELETE CASCADE
);

-- Table Admin
CREATE TABLE admin (
    id_admin INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    mot_de_passe VARCHAR(255)
);

-- Table Fidelite
CREATE TABLE fidelite (
    id_fidelite INT AUTO_INCREMENT PRIMARY KEY,
    id_client INT,
    points INT DEFAULT 0,
    reduction_applicable DECIMAL(5,2) DEFAULT 0,
    FOREIGN KEY (id_client) REFERENCES client(id_client) ON DELETE CASCADE
);

INSERT INTO admin(username, mot_de_passe) VALUES('admin1','1234');

INSERT INTO client(nom,prenom,email,mot_de_passe) VALUES('first','client','client1@example.com','1234');


INSERT INTO coiffeur(nom,email,mot_de_passe,statut) VALUES('coiff','coiff@example.com','1234','valide');

-- modif
USE coiffdb;

ALTER TABLE coiffeur
DROP COLUMN horaires,
ADD COLUMN telephone VARCHAR(20) AFTER email,
ADD COLUMN services VARCHAR(255) AFTER telephone;


ALTER TABLE fidelite
ADD COLUMN source ENUM('rendezvous','anniversaire','autre') DEFAULT 'rendezvous' AFTER reduction_applicable,
ADD COLUMN date_operation DATE DEFAULT CURRENT_DATE AFTER source,
ADD COLUMN description VARCHAR(255) AFTER date_operation;


CREATE TABLE planning (
    id_planning INT AUTO_INCREMENT PRIMARY KEY,
    id_coiffeur INT,
    date_disponibilite DATE,
    heure_debut TIME,
    heure_fin TIME,
    statut ENUM('disponible','reserve','bloque') DEFAULT 'disponible',
    FOREIGN KEY (id_coiffeur) REFERENCES coiffeur(id_coiffeur) ON DELETE CASCADE
);




