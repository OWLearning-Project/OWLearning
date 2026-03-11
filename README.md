# OWLearning

[![Java Version](https://img.shields.io/badge/Java-25-blue.svg)](#)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen.svg)](#)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](#)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED.svg)](#)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](#)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](#)

> **OWLearning** est une API REST Backend crée pour la gestion d'une plateforme d'apprentissage en ligne (E-learning). Elle gère la création de cours, l'inscription des élèves, le suivi automatique de la progression et intègre un système de messagerie en temps réel.

---

## 📑 Table des matières

1. [Type de projet](#-type-de-projet)
2. [Stack Technique](#-stack-technique)
3. [Fonctionnalités Principales](#-fonctionnalités-principales)
4. [Prérequis](#-prérequis)
5. [Installation et Lancement (Docker)](#-installation-et-lancement-docker)
6. [Variables d'Environnement](#-variables-denvironnement)
7. [Liste Complète des Endpoints](#-liste-complète-des-endpoints)
8. [Structure du Projet](#-structure-du-projet)
9. [Contributeurs et Licence](#-contributeurs-et-licence)

---

## 🎯 Type de projet

**API REST Backend** avec support WebSocket pour la communication temps réel.

---

## 🛠 Stack Technique

| Composant | Technologie | Version / Détails |
| :--- | :--- | :--- |
| **Langage** | Java | 25 |
| **Framework Web** | Spring Boot | 4.0.1 |
| **Base de données** | PostgreSQL | 16 (Image `postgres:16-alpine`) |
| **Conteneurisation** | Docker | via `docker-compose.yaml` |
| **Sécurité** | Spring Security & JWT | `io.jsonwebtoken` 0.11.5 |
| **WebSockets** | Spring Boot Starter Websocket | Intégré à Spring Boot |
| **Documentation** | Springdoc OpenAPI (Swagger) | 2.8.0 |
| **Outil de Build** | Maven | Modèle POM 4.0.0 |
| **Logging** | Log4j2 / SLF4J | 2.25.2 |

---

## ✨ Fonctionnalités Principales

- **Gestion des Utilisateurs & Rôles** : Inscription, authentification JWT, séparation stricte entre "Créateurs" et "Élèves".
- **Catalogue Pédagogique** : Création de cours complets (chapitres, catégories, niveaux de difficulté).
- **Ressources Modulaires** : Attachement de fichiers, vidéos ou liens (Ressources) aux chapitres d'un cours.
- **Tracking de Progression** : Calcul automatisé (%) de la progression d'un élève via des triggers SQL basés sur les chapitres terminés.
- **Messagerie & WebSockets** : Espace de discussion persistant avec possibilité d'envoyer des pièces jointes (ressources).

---

## ⚙️ Prérequis

Pour lancer le projet localement, vous devez avoir installé :

- **JDK 25**
- **Maven** (3.8+)
- **Docker** & **Docker Compose** (pour la base de données)
- **Git**

---

## 🚀 Installation et Lancement (Docker)

Le projet utilise **Docker Compose** pour simplifier le déploiement de la base de données PostgreSQL avec le script d'initialisation intégré (`init.sql`).

**1. Cloner le projet**

```bash
git clone https://github.com/OWLearning-Project/OWLearning.git
cd owlearning
git checkout master
```

**2. Compiler le projet Java**
> Prérequis: instalation de [maven](https://maven.apache.org/download.cgi)

```bash
mvn clean package -DskipTests
```

**3. Lancer la base de données via Docker**

Cette commande télécharge l'image PostgreSQL 16, monte le volume de données et exécute le script `init.sql` puis peuple la base avec le script `seed.sql`.

```bash
docker-compose up --build -d
```

> La base de données sera exposée sur `localhost:5433`.

L'API sera accessible sur `http://localhost:8080` et le Swagger UI sur `http://localhost:8080/swagger-ui/index.html`.

---

## 🔐 Variables d'Environnement

Assurez-vous que le fichier `src/main/resources/application.properties` correspond à votre configuration locale (il est déjà pré-configuré pour fonctionner avec le `docker-compose.yaml`) :

```properties
# Base de données (Port 5433 mappé par Docker)
spring.datasource.url=jdbc:postgresql://localhost:5433/owlearning_bdd
spring.datasource.username=admin
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# Sécurité JWT
jwt.secret=VoiciLaCleSecretePourLeProjetOwLearningIlFaudraLaChanger
jwt.expiration=14400000

# Logs Hibernate
logging.level.org.hibernate.SQL=DEBUG
spring.jpa.properties.hibernate.format_sql=true
```

> ⚠️ **Ne commitez jamais vos identifiants réels.** Utilisez des variables d'environnement système ou un fichier `.env` en production.

---

## 📡 Liste Complète des Endpoints

> Toutes les requêtes (sauf Auth et Swagger) nécessitent un header : `Authorization: Bearer <token_jwt>`.

### 🔑 Authentification — `/api/authentification`

| Méthode | Route | Rôle requis | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/inscription` | Public | Crée un compte utilisateur. |
| `POST` | `/connexion` | Public | Authentifie l'utilisateur et retourne le token JWT. (Utilisateur test[1-7]@owlearning.com avec test6 et test7 des créateurs, le reste des éléves.)| 
| `POST` | `/deconnexion` | Authentifié | Invalide la session utilisateur côté serveur. |

### 👤 Utilisateurs — `/api/utilisateurs`

| Méthode | Route | Rôle requis | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/{id}` | Authentifié | Récupère le profil public d'un utilisateur. |
| `PUT` | `/edit_profil` | Authentifié | Modifie le pseudo, email, âge ou niveau d'étude du profil. |

### 📚 Cours — `/api/cours`

| Méthode | Route | Rôle requis | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/` | Authentifié | Liste tous les cours publiés de la plateforme. |
| `POST` | `/` | CREATEUR | Crée un nouveau cours. |
| `GET` | `/{idCours}` | Authentifié | Récupère un cours par son ID. |
| `PUT` | `/{idCours}` | CREATEUR | Modifie les informations d'un cours. |
| `DELETE` | `/{idCours}` | CREATEUR | Supprime un cours spécifique. |
| `POST` | `/{idCours}/publier` | CREATEUR | Passe l'état du cours en "publié". |
| `GET` | `/utilisateurs/publications` | CREATEUR | Liste les cours créés par l'utilisateur connecté. |
| `GET` | `/utilisateurs/inscriptions` | ELEVE | Liste les cours auxquels l'élève est inscrit. |
| `POST` | `/{idCours}/chapitres` | CREATEUR | Ajoute un nouveau chapitre à un cours. |
| `DELETE` | `/{idCours}/chapitres/{idChapitre}` | CREATEUR | Retire un chapitre d'un cours. |
| `POST` | `/{idCours}/categories` | CREATEUR | Ajoute une catégorie/tag à un cours. |
| `DELETE` | `/{idCours}/categories/{nomCategorie}` | CREATEUR | Retire une catégorie d'un cours. |

### 🔖 Chapitres — `/api/chapitres`

| Méthode | Route | Rôle requis | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/{idChapitre}` | Authentifié | Récupère le titre et la description d'un chapitre. |
| `PUT` | `/{idChapitre}` | Authentifié | Modifie un chapitre existant. |
| `POST` | `/{idChapitre}/ressources` | Authentifié | Ajoute une ressource à un chapitre. |
| `DELETE` | `/{idChapitre}/ressources/{idRessource}` | Authentifié | Retire une ressource d'un chapitre. |

### 📝 Inscription & Progression

| Méthode | Route | Rôle requis | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/inscription/etudiants/cours/{idCours}` | Authentifié | Inscrit l'utilisateur courant à un cours. |
| `GET` | `/api/inscription/cours/{idCours}/etudiants` | Authentifié | Liste les étudiants d'un cours. |
| `POST` | `/api/inscription/cours/{idCours}/etudiants/{idEtudiant}/refus` | CREATEUR | Supprime l'inscription d'un étudiant. |
| `GET` | `/api/progression/{idCours}` | ELEVE | Retourne le taux de progression (%) de l'élève. |

### 📦 Ressources — `/api/ressources`

| Méthode | Route | Rôle requis | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/` | Authentifié | Crée une ressource isolée en base de données. |
| `GET` | `/{idRessource}` | Authentifié | Récupère les données d'une ressource. |
| `PUT` | `/{idRessource}` | Authentifié | Modifie une ressource (URL, Nom, Type). |
| `DELETE` | `/{idRessource}` | Authentifié | Supprime une ressource. |

### 💬 Messagerie — `/api/messagerie`

| Méthode | Route | Rôle requis | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/mes-discussions` | Authentifié | Liste les discussions de l'utilisateur connecté. |
| `GET` | `/{idDiscussion}/messages` | Authentifié | Récupère l'historique des messages. |
| `POST` | `/{idMessage}/ressources/{idRessource}` | Authentifié | Lie une pièce jointe (Ressource) à un message. |
| `DELETE` | `/{idMessage}/ressources/{idRessource}` | Authentifié | Retire une pièce jointe d'un message. |

> 🔌 **WebSockets** : Connectez-vous sur `/ws-messagerie` pour les flux en temps réel.

---

## 📁 Structure du Projet

L'architecture est découpée par domaines (inspirée du Domain-Driven Design et de la Clean Architecture) :

```
src/main/java/app/OwLearning/
├── Api/               # API REST : Controllers (Auth, Chapitre, Cours, Messagerie...)
├── Application/       # Logique applicative : Services et cas d'utilisation
├── Domain/            # Modèles métier (Entités pures) et Ports (Interfaces)
├── Infrastructure/    # Implémentations (JPA Repositories, Config JWT, Config WebSocket)
└── Shared/            # DTOs de communication, Exceptions personnalisées
```

---

## 👥 Contributeurs et Licence

**Contributeurs** : Anthia DIALLO, Noémie CHHUN, Bryan BIGLIONE, Iraihane AKRETCHE, Gabriel VIGNON.

**Licence** : Distribué sous la licence **MIT**. Voir le fichier [LICENSE](LICENSE) pour plus d'informations.

---
