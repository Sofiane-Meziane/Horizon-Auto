# Logiciel de Gestion d'Auto-École en JavaFX

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

![Bannière de présentation du logiciel](https://raw.githubusercontent.com/Sofiane-Meziane/Horizon-Auto/main/src/images/Design%20sans%20titre%20(6).png)

---

## 🎯 À propos du projet

Ce projet est une application desktop complète pour la gestion d'une auto-école, développée en **JavaFX** en respectant une architecture **Modèle-Vue-Contrôleur (MVC)**. Le logiciel a été conçu pour digitaliser et automatiser les processus internes, offrant une interface intuitive et structurée, répartie en trois espaces distincts selon les rôles des utilisateurs.

---

## ✨ Aperçu de l'application

L'interface est conçue pour être ergonomique et adaptée à chaque rôle au sein de l'auto-école.

| Écran de connexion | Espace Administrateur | Espace Secrétaire | Espace Moniteur |
| :---: | :---: | :---: | :---: |
| ![Écran de connexion](https://raw.githubusercontent.com/Sofiane-Meziane/Horizon-Auto/main/src/images/Design%20sans%20titre%20(7).png) | ![Tableau de bord de l'administrateur](https://raw.githubusercontent.com/Sofiane-Meziane/Horizon-Auto/main/src/images/ecran%20admin.png) | ![Interface de la secrétaire](https://raw.githubusercontent.com/Sofiane-Meziane/Horizon-Auto/main/src/images/Design%20sans%20titre%20(5).png) | ![Vue du moniteur](https://github.com/Sofiane-Meziane/Horizon-Auto/blob/a94a04181eae2332f561656615ade7dd5bd7e8d0/src/images/Design%20sans%20titre%20(6).png) |

---

## 🚀 Fonctionnalités par rôle

Le logiciel offre des fonctionnalités dédiées à chaque type d'utilisateur pour une gestion optimale.

### 👑 Espace Administrateur
* Gestion complète des utilisateurs (création, modification, suppression).
* Gestion de la flotte de véhicules (ajout, maintenance, assignation).
* Supervision générale de l'activité de l'auto-école.

###  secrétaire Espace Secrétaire
* Enregistrement et gestion des fiches candidats.
* Planification des séances de formation (code et conduite).
* Gestion des inscriptions aux examens officiels.

### 👨‍🏫 Espace Moniteur
* Consultation des plannings personnels et des séances assignées.
* Suivi détaillé des progrès de chaque candidat.
* Saisie des notes et des observations après chaque leçon.

---

## 🛠️ Stack Technique et Architecture

* **Langage :** Java
* **Framework UI :** JavaFX
* **Architecture :** Modèle-Vue-Contrôleur (MVC) stricte pour une séparation claire des responsabilités.
* **Base de données :** MySQL pour la persistance des données.
* **Gestion du projet :** IDE NetBeans avec gestion manuelle des bibliothèques (dépendances).

---

## ⚙️ Installation et Lancement

Ce projet a été développé avec l'IDE NetBeans. La manière la plus simple de le lancer est de l'ouvrir directement dans cet environnement.

### Pré-requis
* **Java Development Kit (JDK)** : Version 8 ou 11.
* **NetBeans IDE** : Version 8.2 ou plus récente.
* **Serveur de Base de Données** : MySQL (via XAMPP, WampServer ou autre) doit être installé et en cours d'exécution.

### Étapes d'installation
1.  **Clonez le repository** sur votre machine locale :
    ```sh
    git clone https://github.com/Sofiane-Meziane/Horizon-Auto.git
    ```
2.  **Importez la base de données** :
    * Démarrez votre serveur MySQL.
    * Ouvrez un outil comme `phpMyAdmin`.
    * Créez une nouvelle base de données nommée `auto_ecole`.
    * Importez le fichier `auto_ecole.sql` (situé à la racine du projet) dans cette base de données.

3.  **Ouvrez le projet dans NetBeans** :
    * Lancez NetBeans.
    * Allez dans `File > Open Project...`.
    * Naviguez jusqu'au dossier `Horizon-Auto` que vous venez de cloner et sélectionnez-le.

4.  **Vérifiez la connexion à la base de données** :
    * Ouvrez le fichier `src/dao/DatabaseConnection.java`.
    * Vérifiez que le nom d'utilisateur (`user`) et le mot de passe (`password`) correspondent à votre configuration MySQL locale. Par défaut, ils sont sur `"root"` et `""` (vide).

5.  **Lancez l'application** :
    * Faites un clic droit sur le projet dans l'explorateur de projets de NetBeans.
    * Cliquez sur **"Run"**. L'application devrait se lancer.

---

## 📄 Licence

Distribué sous la licence MIT. Voir le fichier `LICENSE` pour plus d'informations.
