# Logiciel de Gestion d'Auto-École en JavaFX

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

![Bannière de présentation du logiciel](placeholder_image_banniere.png)
> **Note :** Pour la bannière, créez une image en mode paysage. Elle pourrait montrer le nom de votre application, un logo (même simple comme un volant ou une voiture), et une capture d'écran estompée en arrière-plan. C'est très professionnel.

---

## 🎯 À propos du projet

Ce projet est une application desktop complète pour la gestion d'une auto-école, développée en **JavaFX** en respectant une architecture **Modèle-Vue-Contrôleur (MVC)**. Le logiciel a été conçu pour digitaliser et automatiser les processus internes, offrant une interface intuitive et structurée, répartie en trois espaces distincts selon les rôles utilisateurs.

---

## ✨ Aperçu de l'application

L'interface est conçue pour être ergonomique et adaptée à chaque rôle au sein de l'auto-école.

| Écran de connexion | Espace Administrateur | Espace Secrétaire | Espace Moniteur |
| :---: | :---: | :---: | :---: |
| ![Écran de connexion](placeholder_image_login.png) | ![Tableau de bord de l'administrateur](placeholder_image_admin.png) | ![Interface de la secrétaire](placeholder_image_secretaire.png) | ![Vue du moniteur](placeholder_image_moniteur.png) |

> ### Où et quelles images mettre ?
>
> 1.  **placeholder_image_banniere.png**: Votre bannière principale (créez-la sur un outil comme Canva).
> 2.  **placeholder_image_login.png**: La fenêtre de connexion où l'utilisateur choisit son rôle.
> 3.  **placeholder_image_admin.png**: Une capture de l'espace Admin, montrant par exemple la **liste des utilisateurs ou des véhicules**. C'est le point fort de cet espace.
> 4.  **placeholder_image_secretaire.png**: La vue la plus importante de cet espace, probablement la **planification des séances** (un calendrier ou un agenda) ou le **formulaire d'ajout d'un candidat**.
> 5.  **placeholder_image_moniteur.png**: L'écran principal du moniteur, affichant son **planning de la journée/semaine** ou la liste de ses élèves avec leur progression.

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
* **Build System :** [Précisez votre outil, ex: Maven, Gradle]

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
