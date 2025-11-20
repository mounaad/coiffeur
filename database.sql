-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 20, 2025 at 02:29 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Database: `coiffdb`
--
CREATE DATABASE IF NOT EXISTS coiffdb;
USE coiffdb;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id_admin` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `mot_de_passe` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_admin`, `username`, `mot_de_passe`) VALUES
(1, 'admin1', '1234');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `id_client` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `mot_de_passe` varchar(255) DEFAULT NULL,
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `client`
--

INSERT INTO client (id_client, nom, prenom, email, telephone, mot_de_passe) VALUES
(1, 'first', 'client', 'client1@example.com', '0628303546', '1234'),
(4, 'bouzidi', 'sarah', 'sarah@example.com', '0619293344', '1234'),
(5, 'Elalami', 'Fatima', 'fatima.elalami@example.com', '0612345678', '1234'),
(6, 'Salma', 'Cherkaoui', 'salma.cherkaoui@example.com', '0622554477', '1234'),
(7, 'Bouhaddou', 'Rachida', 'rachida.bouhaddou@example.com', '0699773311', '1234'),
(8, 'Imane', 'Fikri', 'imane.fikri@example.com', '0677553322', '1234'),
(9, 'Hassania', 'Elidrissi', 'hassania.elidrissi@example.com', '0655123488', '1234'),
(10, 'Fatima', 'Ouardi', 'fatima.ouardi@example.com', '0644665577', '1234');


-- --------------------------------------------------------

--
-- Table structure for table `coiffeur`
--

CREATE TABLE `coiffeur` (
  `id_coiffeur` int(11) NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `services` varchar(255) DEFAULT NULL,
  `mot_de_passe` varchar(255) DEFAULT NULL,
  `statut` enum('pending','valide') DEFAULT 'pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `coiffeur`
--

INSERT INTO `coiffeur` (`id_coiffeur`, `nom`, `adresse`, `email`, `telephone`, `services`, `mot_de_passe`, `statut`) VALUES
INSERT INTO coiffeur (id_coiffeur, nom, adresse, email, telephone, mot_de_passe, statut) VALUES
(1, 'coiff', 'rabat', 'coiff@example.com', '0623849302', '1234', 'valide'),
(1, 'coiff3', 'rabat', 'coiff3@example.com', '0623800000', '1234', 'valide'),
(3, 'idrissi azami doha', 'rabat', 'dohaazami@gmail.com', '0648200234', '1234', 'valide'),
(4, 'Salima El Amrani', 'Rabat', 'salima.elamrani@example.com', '0612345678', '1234', 'valide'),
(5, 'Karima Bouziane', 'Rabat', 'karima.bouziane@example.com', '0622334455', '1234', 'valide'),
(6, 'Saliha Benali', 'Rabat', 'saliha.benali@example.com', '0677889900', '1234', 'valide'),
(7, 'Halima Essafi', 'Rabat', 'halima.essafi@example.com', '0655443322', '1234', 'valide'),
(8, 'Rajae Akrim', 'Rabat', 'rajae.akrim@example.com', '0699887766', '1234', 'valide');


-- --------------------------------------------------------

--
-- Table structure for table `coiffeur_service`
--

CREATE TABLE `coiffeur_service` (
  `id_coiffeur` int(11) NOT NULL,
  `id_service` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `coiffeur_service`
--

INSERT INTO `coiffeur_service` (`id_coiffeur`, `id_service`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 2),
(2, 4),
(3, 1),
(3, 3),
(4, 3),
(4, 4),
(5, 5),
(5, 6);

-- --------------------------------------------------------

--
-- Table structure for table `fidelite`
--

CREATE TABLE `fidelite` (
  `id_fidelite` int(11) NOT NULL,
  `id_client` int(11) DEFAULT NULL,
  `points` int(11) DEFAULT 0,
  `reduction_applicable` decimal(5,2) DEFAULT 0.00,
  `source` enum('rendezvous','anniversaire','autre') DEFAULT 'rendezvous',
  `date_operation` date DEFAULT curdate(),
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `fidelite`
--


INSERT INTO fidelite (id_fidelite, id_client, points, reduction_applicable, source, date_operation, description) VALUES
(1, 2, 30, 0, 'rendezvous', '2025-11-19', 'Points ajoutés après confirmation'),
(3, 4, 20, 0, 'rendezvous', '2025-11-20', 'Points ajoutés après confirmation'),
(4, 5, 0, 0, 'rendezvous', '2025-11-20', 'Points ajoutés après confirmation'),
(5, 6, 40, 0, 'rendezvous', '2025-11-20', 'Points ajoutés après confirmation'),
(6, 7, 100, 1, 'rendezvous', '2025-11-20', 'Points ajoutés après confirmation'),
(7, 8, 60, 0, 'rendezvous', '2025-11-20', 'Points ajoutés après confirmation'),
(8, 9, 50, 0, 'rendezvous', '2025-11-20', 'Points ajoutés après confirmation'),
(9, 10, 0, 0, 'rendezvous', '2025-11-20', 'Points ajoutés après confirmation');

-- --------------------------------------------------------

--
-- Table structure for table `rendezvous`
--

CREATE TABLE `rendezvous` (
  `id_rdv` int(11) NOT NULL,
  `id_client` int(11) DEFAULT NULL,
  `id_coiffeur` int(11) DEFAULT NULL,
  `date_rdv` date DEFAULT NULL,
  `heure_rdv` time DEFAULT NULL,
  `statut` enum('en_attente','confirme','annule') DEFAULT 'en_attente',
  `id_service` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rendezvous`
--

INSERT INTO `rendezvous` (`id_rdv`, `id_client`, `id_coiffeur`, `date_rdv`, `heure_rdv`, `statut`, `id_service`) VALUES
(4, 1, 1, '2025-11-15', '10:30:00', 'confirme', 1),
(5, 1, 1, '2025-11-16', '14:00:00', 'confirme', 3),
(6, 1, 1, '2025-11-17', '09:00:00', 'annule', 4),
(8, 2, 3, '2025-11-20', '14:00:00', 'confirme', 1),
(9, 2, 2, '2025-11-28', '14:45:00', 'en_attente', 2),
(10, 2, 4, '2025-11-22', '10:30:00', 'en_attente', 4),
(11, 2, 4, '2025-11-25', '11:30:00', 'en_attente', 4),
(13, 2, 3, '2025-11-25', '16:15:00', 'en_attente', 3),
(14, 2, 1, '2025-11-28', '10:30:00', 'en_attente', 1),
(15, 6, 4, '2025-11-23', '16:45:00', 'en_attente', 3),
(16, 2, 2, '2025-11-26', '17:15:00', 'en_attente', 2),
(17, 5, 3, '2025-11-21', '10:00:00', 'confirme', 8),
(18, 4, 3, '2025-11-22', '11:30:00', 'annule', 9),
(19, 6, 3, '2025-11-23', '14:00:00', 'en_attente', 10),
(20, 8, 3, '2025-11-24', '15:30:00', 'confirme', 11);

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE `service` (
  `id_service` int(11) NOT NULL,
  `nom_service` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `duree` int(11) NOT NULL,
  `prix` double NOT NULL,
  `photo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`id_service`, `nom_service`, `description`, `duree`, `prix`, `photo`) VALUES
(1, 'Coupe Femme', 'Coupe moderne ou classique, brushing inclus.', 45, 80, 'https://img.freepik.com/photos-premium/coiffeuse-fait-coupe-cheveux-outils-coiffure-professionnels-equipement_214864-114.jpg'),
(2, 'Coloration', 'Coloration complète avec produits professionnels.', 90, 150, 'https://i.pinimg.com/736x/f3/96/9f/f3969fa8e0e4d8c9533fbd9fb9fcc411.jpg'),
(3, 'Meches / Balayage', 'Éclaircissement partiel ou total selon la demande.', 120, 200, 'https://i.pinimg.com/736x/77/51/2f/77512f6a4ba46da5aff53899f9d3a09f.jpg'),
(4, 'Soin Capillaire', 'Masque nourrissant, hydratation profonde.', 30, 40, 'https://i.pinimg.com/1200x/33/5d/60/335d60b4559e4623f2406bc3b0e30ffd.jpg'),
(5, 'Brushing', 'Brushing lisse ou wavy – cheveux courts à longs.', 40, 60, 'https://i.pinimg.com/736x/3d/71/f2/3d71f21349661a87a4dfaaf1fafbdd1b.jpg'),
(6, 'Lissage', 'Lissage permanent ou brésilien selon le type de cheveux.', 180, 300, 'https://i.pinimg.com/736x/9a/1c/42/9a1c4261dcbfe567daf400cd7565f3ee.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admin`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id_client`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `coiffeur`
--
ALTER TABLE `coiffeur`
  ADD PRIMARY KEY (`id_coiffeur`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `coiffeur_service`
--
ALTER TABLE `coiffeur_service`
  ADD PRIMARY KEY (`id_coiffeur`,`id_service`),
  ADD KEY `id_service` (`id_service`);

--
-- Indexes for table `fidelite`
--
ALTER TABLE `fidelite`
  ADD PRIMARY KEY (`id_fidelite`),
  ADD KEY `id_client` (`id_client`);

--
-- Indexes for table `planning`
--
ALTER TABLE `planning`
  ADD PRIMARY KEY (`id_planning`),
  ADD KEY `id_coiffeur` (`id_coiffeur`);

--
-- Indexes for table `rendezvous`
--
ALTER TABLE `rendezvous`
  ADD PRIMARY KEY (`id_rdv`),
  ADD KEY `id_client` (`id_client`),
  ADD KEY `id_coiffeur` (`id_coiffeur`),
  ADD KEY `fk_service` (`id_service`);

--
-- Indexes for table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`id_service`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id_admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `id_client` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `coiffeur`
--
ALTER TABLE `coiffeur`
  MODIFY `id_coiffeur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `fidelite`
--
ALTER TABLE `fidelite`
  MODIFY `id_fidelite` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `planning`
--
ALTER TABLE `planning`
  MODIFY `id_planning` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rendezvous`
--
ALTER TABLE `rendezvous`
  MODIFY `id_rdv` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
  MODIFY `id_service` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `coiffeur_service`
--
ALTER TABLE `coiffeur_service`
  ADD CONSTRAINT `coiffeur_service_ibfk_1` FOREIGN KEY (`id_coiffeur`) REFERENCES `coiffeur` (`id_coiffeur`),
  ADD CONSTRAINT `coiffeur_service_ibfk_2` FOREIGN KEY (`id_service`) REFERENCES `service` (`id_service`);

--
-- Constraints for table `fidelite`
--
ALTER TABLE `fidelite`
  ADD CONSTRAINT `fidelite_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`) ON DELETE CASCADE;

--
-- Constraints for table `planning`
--
ALTER TABLE `planning`
  ADD CONSTRAINT `planning_ibfk_1` FOREIGN KEY (`id_coiffeur`) REFERENCES `coiffeur` (`id_coiffeur`) ON DELETE CASCADE;

--
-- Constraints for table `rendezvous`
--
ALTER TABLE `rendezvous`
  ADD CONSTRAINT `fk_service` FOREIGN KEY (`id_service`) REFERENCES `service` (`id_service`) ON DELETE CASCADE,
  ADD CONSTRAINT `rendezvous_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`) ON DELETE CASCADE,
  ADD CONSTRAINT `rendezvous_ibfk_2` FOREIGN KEY (`id_coiffeur`) REFERENCES `coiffeur` (`id_coiffeur`) ON DELETE CASCADE;
COMMIT;


