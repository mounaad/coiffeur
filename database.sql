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
