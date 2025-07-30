# Logiciel de Gestion d'Auto-École en JavaFX

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

![Bannière de présentation du logiciel](https://github.com/Sofiane-Meziane/Horizon-Auto/blob/f074b68dcb176d98055e8699dfd88e5561efafc9/src/images/Design%20sans%20titre%20(6).png)

---

## 🎯 À propos du projet

Ce projet est une application desktop complète pour la gestion d'une auto-école, développée en **JavaFX** en respectant une architecture **Modèle-Vue-Contrôleur (MVC)**. Le logiciel a été conçu pour digitaliser et automatiser les processus internes, offrant une interface intuitive et structurée, répartie en trois espaces distincts selon les rôles utilisateurs.

---

## ✨ Aperçu de l'application

L'interface est conçue pour être ergonomique et adaptée à chaque rôle au sein de l'auto-école.

| Écran de connexion | Espace Administrateur | Espace Secrétaire | Espace Moniteur |
| :---: | :---: | :---: | :---: |
| ![Écran de connexion](https://github.com/Sofiane-Meziane/Horizon-Auto/blob/a94a04181eae2332f561656615ade7dd5bd7e8d0/src/images/Design%20sans%20titre%20(7).png) | ![Tableau de bord de l'administrateur](https://github.com/Sofiane-Meziane/Horizon-Auto/blob/ea93dfa1e1a65e3fe6f5c0cec73cc9d2254a6da5/src/images/ecran%20admin.png) | ![Interface de la secrétaire](https://github.com/Sofiane-Meziane/Horizon-Auto/blob/a94a04181eae2332f561656615ade7dd5bd7e8d0/src/images/Design%20sans%20titre%20(5).png) | ![Vue du moniteur](https://github.com/Sofiane-Meziane/Horizon-Auto/blob/a94a04181eae2332f561656615ade7dd5bd7e8d0/src/images/Design%20sans%20titre%20(6).png) |

---

## 🚀 Fonctionnalités par rôle

Le logiciel offre des fonctionnalités dédiées à chaque type d'utilisateur pour une gestion optimale.

### 👑 Espace Administrateur
* Gestion complète des utilisateurs (création, modification, suppression).
* Gestion de la flotte de véhicules (ajout, maintenance, assignation).
* Supervision générale de l'activité de l'auto-école.

###  секретаire Espace Secrétaire
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
* **Base de données :** [Précisez votre SGBD, ex: MySQL, PostgreSQL, SQLite] pour la persistance des données.

---

## ⚙️ Installation et Lancement

Instructions pour compiler et lancer le projet localement.

**Pré-requis :**
* JDK 11 ou supérieur
* [Maven/Gradle]
* [Votre SGBD]

**Étapes :**
1.  Clonez le repository :
    ```sh
    git clone [URL_DE_VOTRE_REPO]
    ```
2.  Configurez la connexion à la base de données dans `[chemin/vers/fichier/de/config.properties]`.
3.  Compilez le projet :
    ```sh
    # Si vous utilisez Maven
    mvn clean install
    ```
4.  Lancez l'application :
    ```sh
    # Si vous utilisez Maven
    mvn javafx:run
    ```

---

## 📄 Licence

Distribué sous la licence MIT. Voir le fichier `LICENSE` pour plus d'informations.
