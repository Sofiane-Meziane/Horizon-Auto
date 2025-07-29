-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mar. 29 juil. 2025 à 11:57
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `auto_ecole`
--

-- --------------------------------------------------------

--
-- Structure de la table `eleves`
--

CREATE TABLE `eleves` (
  `idEleve` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `telephone` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `dateInscription` date NOT NULL,
  `montantTotal` decimal(10,2) NOT NULL,
  `montantPaye` decimal(10,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `eleves`
--

INSERT INTO `eleves` (`idEleve`, `nom`, `prenom`, `adresse`, `telephone`, `email`, `dateInscription`, `montantTotal`, `montantPaye`) VALUES
(1, 'Ali', 'Ben', '123 Rue des Fleurs', '0555123456', 'ali.ben@example.com', '2024-12-01', 30000.00, 10000.00),
(2, 'Sarah', 'Meziane', '456 Rue du Soleil', '0666987654', 'sarah.meziane@example.com', '2024-12-10', 35000.00, 15000.00),
(3, 'Salah', 'Salhoun', 'Debha', '0154565445', 'salah.net', '2024-05-09', 20000.00, 12000.00),
(4, 'Meziane', 'Sofiane', 'Amizour', '0656211943', 'sofianemeziane186@gmail.com', '2024-12-30', 30000.00, 2000.00),
(5, 'sofiane', 'sss', 'ssfsf', '54654564', 'ssssssssssp', '2024-08-08', 300000.00, 4000.00),
(7, 'nonous', 'nounous', 'sosososoosso', '45454545', 'slsks', '2025-06-05', 10000.00, 2000.00);

-- --------------------------------------------------------

--
-- Structure de la table `examens`
--

CREATE TABLE `examens` (
  `idExamen` int(11) NOT NULL,
  `typeExamen` enum('Code','Créneau','Conduite') NOT NULL,
  `dateExamen` date NOT NULL,
  `lieu` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `examens`
--

INSERT INTO `examens` (`idExamen`, `typeExamen`, `dateExamen`, `lieu`) VALUES
(1, 'Code', '2024-12-20', 'Salle 2'),
(2, 'Conduite', '2024-12-25', 'Parking Auto-Ecole'),
(3, 'Code', '2024-08-08', 'marché d\'amizour'),
(4, 'Créneau', '2025-05-02', 'Amizour ville');

-- --------------------------------------------------------

--
-- Structure de la table `examens_eleves`
--

CREATE TABLE `examens_eleves` (
  `idExamEleve` int(11) NOT NULL,
  `idExamen` int(11) NOT NULL,
  `idEleve` int(11) NOT NULL,
  `resultat` enum('Réussi','Echoué') DEFAULT NULL,
  `commentaire` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `examens_eleves`
--

INSERT INTO `examens_eleves` (`idExamEleve`, `idExamen`, `idEleve`, `resultat`, `commentaire`) VALUES
(1, 1, 1, 'Réussi', 'Bonne maîtrise du code'),
(4, 1, 1, 'Réussi', 'good fella'),
(5, 2, 2, 'Réussi', 'good job son'),
(6, 1, 2, 'Echoué', 'doit refaire'),
(7, 1, 1, 'Echoué', ''),
(9, 4, 2, 'Réussi', 'good job'),
(10, 4, 7, NULL, '');

-- --------------------------------------------------------

--
-- Structure de la table `paiements`
--

CREATE TABLE `paiements` (
  `idPaiement` int(11) NOT NULL,
  `idEleve` int(11) NOT NULL,
  `datePaiement` date NOT NULL,
  `montant` decimal(10,2) NOT NULL,
  `moyenPaiement` enum('Espèce','Carte','Chèque') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `paiements`
--

INSERT INTO `paiements` (`idPaiement`, `idEleve`, `datePaiement`, `montant`, `moyenPaiement`) VALUES
(5, 5, '2024-08-04', 2000.00, 'Carte'),
(6, 5, '2025-07-01', 1000.00, 'Carte'),
(7, 5, '2025-08-06', 1000.00, 'Carte'),
(8, 4, '2025-02-03', 1000.00, 'Carte'),
(9, 4, '2025-05-03', 1000.00, 'Carte'),
(11, 7, '2025-08-06', 1000.00, 'Carte'),
(13, 7, '2025-08-08', 1000.00, 'Carte');

-- --------------------------------------------------------

--
-- Structure de la table `seances`
--

CREATE TABLE `seances` (
  `idSeance` int(11) NOT NULL,
  `idMoniteur` int(11) NOT NULL,
  `idVehicule` int(11) DEFAULT NULL,
  `idEleve` int(11) NOT NULL,
  `dateSeance` date NOT NULL,
  `duree` int(11) NOT NULL,
  `typeSeance` enum('code','créneau','conduite') NOT NULL,
  `statut` varchar(11) DEFAULT NULL,
  `observations` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `seances`
--

INSERT INTO `seances` (`idSeance`, `idMoniteur`, `idVehicule`, `idEleve`, `dateSeance`, `duree`, `typeSeance`, `statut`, `observations`) VALUES
(6, 6, 1, 1, '2024-05-05', 1, 'créneau', 'faite', 'bon eleve'),
(7, 3, 2, 1, '2024-05-04', 1, 'créneau', 'dd', 'dd'),
(9, 6, 1, 1, '2024-06-04', 1, 'créneau', 'faite', 'très bon type'),
(10, 6, 2, 1, '2024-06-08', 1, 'conduite', 'faite', 'trés bon eleve'),
(11, 6, 2, 1, '2024-06-02', 1, 'créneau', 'pas encore', 'aprends vite'),
(13, 3, NULL, 1, '2024-07-04', 1, 'code', '', ''),
(14, 3, NULL, 1, '2024-03-03', 1, 'code', '', ''),
(15, 6, 2, 4, '2025-01-01', 1, 'conduite', '', ''),
(16, 6, 2, 1, '2024-05-05', 1, 'conduite', 'done', 'good boy'),
(17, 6, NULL, 1, '2025-06-01', 1, 'code', 'done', 'good fela'),
(18, 3, 2, 4, '2025-06-08', 1, 'créneau', 'En attente', ''),
(19, 6, 1, 4, '2024-08-01', 1, 'conduite', 'En attente', ''),
(20, 6, NULL, 4, '2025-08-08', 1, 'code', 'done', 'sfsfs');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateurs`
--

CREATE TABLE `utilisateurs` (
  `idUtilisateur` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('Admin','Secretaire','Moniteur') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateurs`
--

INSERT INTO `utilisateurs` (`idUtilisateur`, `nom`, `prenom`, `username`, `password`, `role`) VALUES
(1, 'Admin', 'Principal', 'admin', '123', 'Admin'),
(2, 'Marie', 'Secretaire', 'secretaire1', 'secret123', 'Secretaire'),
(3, 'Jean', 'Dupont', 'moniteur1', 'moniteur123', 'Moniteur'),
(4, 'toto', 'toto', 'toto', '123', 'Moniteur'),
(5, 'malika', 'malika', 'malika', '123', 'Secretaire'),
(6, 'ali', 'ali', 'ali', '123', 'Moniteur'),
(7, 'sofiane', 'sss', 'sssssssssp', '123', 'Moniteur'),
(10, 'slsls', 'slssl', 'ssls', '123', 'Moniteur'),
(11, 'totot', 'totot', 'totototo', '123', 'Moniteur'),
(12, 'Meziane', 'Samy', 'Samy', '123', 'Moniteur'),
(16, 'Sofiane', 'sfsfs', 'sfsfsfsf', '123', 'Moniteur');

-- --------------------------------------------------------

--
-- Structure de la table `vehicules`
--

CREATE TABLE `vehicules` (
  `idVehicule` int(11) NOT NULL,
  `marque` varchar(100) NOT NULL,
  `modele` varchar(100) NOT NULL,
  `immatriculation` varchar(20) NOT NULL,
  `typeVehicule` enum('Voiture','Moto','Camion') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `vehicules`
--

INSERT INTO `vehicules` (`idVehicule`, `marque`, `modele`, `immatriculation`, `typeVehicule`) VALUES
(1, 'Peugeot', '208', '123-ABC-46', 'Voiture'),
(2, 'Renault', 'Clio', '456-DEF-78', 'Voiture'),
(3, 'Toyota', 'Corolla', '1644864', 'Voiture'),
(5, 'Toyota', 'corolla', '564654', 'Voiture'),
(6, 'Nissan', 'Micra', '42154852', 'Voiture');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `eleves`
--
ALTER TABLE `eleves`
  ADD PRIMARY KEY (`idEleve`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `montantPaye` (`montantPaye`);

--
-- Index pour la table `examens`
--
ALTER TABLE `examens`
  ADD PRIMARY KEY (`idExamen`);

--
-- Index pour la table `examens_eleves`
--
ALTER TABLE `examens_eleves`
  ADD PRIMARY KEY (`idExamEleve`),
  ADD KEY `idExamen` (`idExamen`),
  ADD KEY `idEleve` (`idEleve`);

--
-- Index pour la table `paiements`
--
ALTER TABLE `paiements`
  ADD PRIMARY KEY (`idPaiement`),
  ADD KEY `montant` (`montant`),
  ADD KEY `paiements_ibfk_1` (`idEleve`);

--
-- Index pour la table `seances`
--
ALTER TABLE `seances`
  ADD PRIMARY KEY (`idSeance`),
  ADD KEY `seances_ibfk_2` (`idVehicule`),
  ADD KEY `seances_ibfk_1` (`idMoniteur`),
  ADD KEY `seances_ibfk_3` (`idEleve`);

--
-- Index pour la table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  ADD PRIMARY KEY (`idUtilisateur`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Index pour la table `vehicules`
--
ALTER TABLE `vehicules`
  ADD PRIMARY KEY (`idVehicule`),
  ADD UNIQUE KEY `immatriculation` (`immatriculation`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `eleves`
--
ALTER TABLE `eleves`
  MODIFY `idEleve` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `examens`
--
ALTER TABLE `examens`
  MODIFY `idExamen` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `examens_eleves`
--
ALTER TABLE `examens_eleves`
  MODIFY `idExamEleve` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT pour la table `paiements`
--
ALTER TABLE `paiements`
  MODIFY `idPaiement` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT pour la table `seances`
--
ALTER TABLE `seances`
  MODIFY `idSeance` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT pour la table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  MODIFY `idUtilisateur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT pour la table `vehicules`
--
ALTER TABLE `vehicules`
  MODIFY `idVehicule` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `examens_eleves`
--
ALTER TABLE `examens_eleves`
  ADD CONSTRAINT `examens_eleves_ibfk_1` FOREIGN KEY (`idExamen`) REFERENCES `examens` (`idExamen`),
  ADD CONSTRAINT `examens_eleves_ibfk_2` FOREIGN KEY (`idEleve`) REFERENCES `eleves` (`idEleve`);

--
-- Contraintes pour la table `paiements`
--
ALTER TABLE `paiements`
  ADD CONSTRAINT `paiements_ibfk_1` FOREIGN KEY (`idEleve`) REFERENCES `eleves` (`idEleve`);

--
-- Contraintes pour la table `seances`
--
ALTER TABLE `seances`
  ADD CONSTRAINT `seances_ibfk_1` FOREIGN KEY (`idMoniteur`) REFERENCES `utilisateurs` (`idUtilisateur`),
  ADD CONSTRAINT `seances_ibfk_2` FOREIGN KEY (`idVehicule`) REFERENCES `vehicules` (`idVehicule`),
  ADD CONSTRAINT `seances_ibfk_3` FOREIGN KEY (`idEleve`) REFERENCES `eleves` (`idEleve`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
