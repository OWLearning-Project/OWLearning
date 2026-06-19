# OWLearning

OWLearning est une plateforme d'E-Learning conçue pour s'inscrire à des cours, OWLearning facile l'apprentissage et les échanges entre les élèves et les créateurs de cours. Ce Git contient l'intégralité du code du projet, réparti entre le frontend et le backend.

* [Notre boîte à outils](#notre-boîte-à-outils)
* [Architecture du Backend](#architecture-du-backend)
* [Fonctionnalités Principales](#fonctionnalités-principales)
* [Application Mobile](#application-mobile)
* [Comment lancer le projet localement](#comment-lancer-le-projet-localement)
* [Liste Complète des Endpoints](#liste-complète-des-endpoints)
    * [Authentification](#authentification--apiauthentification)
    * [Utilisateurs](#utilisateurs--apiutilisateurs)
    * [Cours](#cours--apicours)
    * [Chapitres](#chapitres--apichapitres)
    * [Inscriptions & Progression](#inscriptions--progression)
    * [Ressources](#ressources--apiressources)
    * [Messagerie](#messagerie--apimessagerie)
    * [WebSockets (Temps réel)](#websockets-temps-réel)

## Notre boîte à outils

L'applicztion repose sur une architecture moderne et séparée :

**Frontend**
* Framework : Vue.js 3 (utilisé avec la Composition API et les balises script setup)
* Interface : BootstrapVueNext pour les composants UI
* Temps réel : WebSockets pour gérer la messagerie instantanée

**Backend**
* Langage : Java 25
* Framework : Spring Boot 4.0.1
* Base de données : PostgreSQL 16 pour la production
* Sécurité : Spring Security avec une implémentation de tokens JWT

## Architecture du Backend

Suite à notre refactorisation (Sprint 2), le backend suit une architecture stricte découpée en 4 couches principales sous le package app.OwLearning :
* Api : Contient nos contrôleurs. C'est ici que l'on gère les requêtes HTTP et que l'on utilise les DTO (Data Transfer Objects) couplés à des mappers pour ne jamais exposer nos entités de base de données au frontend.
* Domaine : Le cœur de notre application il est pur (nos modèles métiers comme Utilisateur, Discussion, etc.), totalement découplé de la persistance JPA.
* Infrastructure : Gère tout ce qui est technique (configuration de la base de données, sécurité, websockets).
* Services : Contient la logique métier (annotée avec @Service).

## Fonctionnalités Principales

* Gestion des cours : Aperçu (avec barre de progression, liste des personnes inscrites au cours), suivi et inscription aux cours créés par les professeurs.
* Messagerie temps réel : Discussions privées de maximum 2 personnes avec la possibilité d'envoyer des pièces jointes (images, vidéo, PDF, ZPI).
* Annuaire des utilisateurs : Liste des créateurs avec un système de filtrage sécurisé côté front et back.

## Application Mobile

En complément du client web, un prototype d'application mobile a été initié durant le dernier sprint du projet afin de proposer une expérience adaptée aux smartphones.

### Objectif

L'objectif principal de l'application mobile est de permettre aux élèves de consulter les cours de la plateforme OWLearning depuis un appareil mobile.

### État d'avancement

Le développement du client mobile n'a pas été finalisé avant la fin du projet. Néanmoins, plusieurs éléments ont été réalisés :

#### Fonctionnalités disponibles

* Inscription utilisateur
* Connexion utilisateur
* Consultation et modification du profil
* Structure générale de navigation de l'application

#### Interfaces réalisées

* Page de connexion
* Page d'inscription
* Page de profil
* Layout principal de l'application

#### Fonctionnalités incomplètes

* Catalogue des cours (présence d'une erreur empêchant son utilisation)
* Consultation des cours
* Messagerie (pages créées mais non fonctionnelles)

### Limites

L'application mobile doit être considérée comme un prototype. Le client web reste la version principale et la plus complète du projet OWLearning.

## Comment lancer le projet localement

```bash
git clone https://github.com/OWLearning-Project/OWLearning.git
cd OWLearning
git checkout develop
```

**2. Compiler le projet Java**
> Prérequis: instalation de [maven](https://maven.apache.org/download.cgi)

```bash
mvn clean package -DskipTests
```

**3. Lancer les images sur pour les avoir dans le Docker**

Ouvrez un nouveau terminal à la racine du projet, ou remontez d'un dossier avec `cd ..`. Cette commande télécharge et installe les images dans Docker :
```bash
docker-compose up --build -d
```
**4. Lancer l'application sur un navigateur**

allez dans un navigateur et ecrire :
```bash
localhost:80
```
## Profils Tests

Le mot de passe est le même pour tous les utilisateurs : Password1234

Profils élèves: 

test1@owlearning.com
test2@owlearning.com
test3@owlearning.com
test4@owlearning.com
test5@owlearning.com

Profils créateurs:

test6@owlearning.com
test7@owlearning.com

## Liste Complète des Endpoints

Toutes les requêtes (sauf celles d'authentification) nécessitent de passer le token JWT dans le header HTTP : `Authorization: Bearer <token>`.

### Authentification — `/api/authentification`

| Méthode | Route | Rôle requis | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/inscription` | Public | Inscrit un nouvel utilisateur. |
| `POST` | `/connexion` | Public | Authentifie un utilisateur et retourne son token JWT. |
| `POST` | `/deconnexion` | Authentifié | Déconnecte l'utilisateur en invalidant son token côté serveur. |

### Utilisateurs — `/api/utilisateurs`

| Méthode | Route | Rôle requis | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/` | Authentifié | Récupère la liste de tous les utilisateurs. |
| `GET` | `/createurs` | Authentifié | Récupère uniquement la liste des créateurs (pour l'annuaire). |
| `GET` | `/{id}` | Authentifié | Récupère le profil public d'un utilisateur ciblé. |
| `PUT` | `/edit_profil` | Authentifié | Modifie les informations du profil de l'utilisateur connecté. |

### Cours — `/api/cours`

| Méthode | Route | Rôle requis | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/` | Authentifié | Liste tous les cours publiés sur la plateforme. |
| `POST` | `/` | CREATEUR | Crée un nouveau cours (brouillon). |
| `GET` | `/{idCours}` | Authentifié | Récupère les détails complets d'un cours. |
| `PUT` | `/{idCours}` | CREATEUR | Modifie le titre, la description ou la difficulté d'un cours. |
| `DELETE` | `/{idCours}` | CREATEUR | Supprime un cours existant. |
| `POST` | `/{idCours}/publier` | CREATEUR | Publie un cours pour le rendre visible aux élèves. |
| `GET` | `/utilisateurs/publications` | CREATEUR | Liste tous les cours créés par le professeur connecté. |
| `GET` | `/utilisateurs/inscriptions` | ELEVE | Liste tous les cours suivis par l'élève connecté. |
| `POST` | `/{idCours}/chapitres` | CREATEUR | Ajoute un nouveau chapitre à un cours spécifique. |
| `DELETE` | `/{idCours}/chapitres/{idChapitre}` | CREATEUR | Supprime un chapitre d'un cours. |
| `POST` | `/{idCours}/categories` | CREATEUR | Ajoute un tag/catégorie à un cours. |
| `DELETE` | `/{idCours}/categories/{nomCategorie}` | CREATEUR | Retire une catégorie d'un cours. |

### Chapitres — `/api/chapitres`

| Méthode | Route | Rôle requis | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/{idChapitre}` | Authentifié | Récupère les données d'un chapitre précis. |
| `PUT` | `/{idChapitre}` | CREATEUR | Modifie le titre et la description d'un chapitre. |
| `POST` | `/{idChapitre}/ressources` | CREATEUR | Lie une ressource existante ou en crée une nouvelle pour ce chapitre. |
| `DELETE` | `/{idChapitre}/ressources/{idRessource}` | CREATEUR | Retire une ressource associée à un chapitre. |
| `POST` | `/{idChapitre}/terminer` | ELEVE | Marque un chapitre comme terminé pour l'élève connecté. |

### Inscriptions & Progression

| Méthode | Route | Rôle requis | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/inscription/etudiants/cours/{idCours}` | Authentifié | Inscrit l'utilisateur connecté à un cours. |
| `GET` | `/api/inscription/cours/{idCours}/etudiants` | Authentifié | Liste tous les étudiants inscrits à un cours donné. |
| `POST` | `/api/inscription/cours/{idCours}/etudiants/{idEtudiant}/refus` | CREATEUR | Permet au créateur de retirer un étudiant de son cours. |
| `GET` | `/api/progression/{idCours}` | ELEVE | Retourne le taux d'avancement d'un élève sur un cours (en %). |

### Ressources — `/api/ressources`

| Méthode | Route | Rôle requis | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/` | CREATEUR | Crée une ressource standard en base de données. |
| `POST` | `/upload` | Authentifié | Upload un fichier physique sur le serveur. |
| `GET` | `/{idRessource}` | Authentifié | Récupère les détails d'une ressource. |
| `PUT` | `/{idRessource}` | CREATEUR | Modifie les données d'une ressource existante. |
| `DELETE` | `/{idRessource}` | CREATEUR | Supprime définitivement une ressource. |

### Messagerie — `/api/messagerie`

| Méthode | Route | Rôle requis | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/mes-discussions` | Authentifié | Liste l'historique des discussions de l'utilisateur. |
| `POST` | `/discussions/createurs/{idCreateur}` | Authentifié | Ouvre une discussion ciblée avec un créateur. |
| `POST` | `/discussions/eleves/{idEleve}` | Authentifié | Ouvre une discussion ciblée avec un élève. |
| `POST` | `/discussions/utilisateurs/{idUtilisateur}`| Authentifié | Ouvre une discussion générique avec un utilisateur. |
| `GET` | `/{idDiscussion}/messages` | Authentifié | Récupère tous les messages d'une conversation spécifique. |
| `POST` | `/{idMessage}/ressources/{idRessource}` | Authentifié | Attache une pièce jointe (ressource) à un message. |
| `DELETE` | `/{idMessage}/ressources/{idRessource}` | Authentifié | Retire une pièce jointe d'un message. |

### WebSockets (Temps réel)

| Endpoint | Action WebSocket | Description |
| :--- | :--- | :--- |
| `/messagerie/{idDiscussion}/envoyer` | `MessageMapping` | Point d'entrée pour envoyer un message en direct. |
| `/topic/discussion/{idDiscussion}` | `SendTo` | Canal d'écoute pour recevoir les nouveaux messages de la discussion. |