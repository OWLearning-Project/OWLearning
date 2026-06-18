BEGIN;

INSERT INTO Categorie (categorie) VALUES ('PROGRAMMATION_ALGORITHMIQUE');
INSERT INTO Categorie (categorie) VALUES ('DEVELOPPEMENT_WEB');
INSERT INTO Categorie (categorie) VALUES ('BASE_DE_DONNEES');
INSERT INTO Categorie (categorie) VALUES ('SYSTEMES_RESEAUX');
INSERT INTO Categorie (categorie) VALUES ('IA_DATASCIENCES');
INSERT INTO Categorie (categorie) VALUES ('DEVELOPPEMENT_MOBILE');
INSERT INTO Categorie (categorie) VALUES ('HISTOIRE_INFORMATIQUE');
INSERT INTO Categorie (categorie) VALUES ('MANAGEMENT_GESTION');
INSERT INTO Categorie (categorie) VALUES ('MATHEMATIQUES');
INSERT INTO Categorie (categorie) VALUES ('ARCHITECTURE');

INSERT INTO Utilisateur (nom, prenom, email, pseudo, mot_de_passe, date_inscription, derniere_activite)
VALUES ('Akretche', 'Iraihane', 'test1@owlearning.com', 'iraihane', '$2a$10$tFuEYeZbsfKH8nLE4.fmwecFZ.0OexJ6dOWzdVwqq83npZkpf41NK', '2026-03-10 17:18:41.01+01', NULL);

INSERT INTO Utilisateur (nom, prenom, email, pseudo, mot_de_passe, date_inscription, derniere_activite)
VALUES ('Chhun', 'Noémie', 'test2@owlearning.com', 'noémie', '$2a$10$ullSqyAlnbs/TYTLCxvTf.OW1lQdcsyTiR2zXeLmld92ZMBgSvWTu', '2026-03-10 17:19:02.186+01', NULL);

INSERT INTO Utilisateur (nom, prenom, email, pseudo, mot_de_passe, date_inscription, derniere_activite)
VALUES ('Vignon', 'Gabriel', 'test3@owlearning.com', 'gabriel', '$2a$10$3xBrgM3YYBNsklAsUTziHuNAG2UDYvQpOoRqyZuEgHMBy45LtSf4O', '2026-03-10 17:19:16.709+01', NULL);

INSERT INTO Utilisateur (nom, prenom, email, pseudo, mot_de_passe, date_inscription, derniere_activite)
VALUES ('Biglione', 'Bryan', 'test4@owlearning.com', 'bryan', '$2a$10$3TbQ1Iloylnos6RyJJpF/OYPg1z2D8hgRnYp/2qMZj8.jRQL63rZq', '2026-03-10 17:19:32.666+01', NULL);

INSERT INTO Utilisateur (nom, prenom, email, pseudo, mot_de_passe, date_inscription, derniere_activite)
VALUES ('Diallo', 'Anthia', 'test5@owlearning.com', 'anthia', '$2a$10$HJ3kBcVmTuvzMzEFwQ09N.doK3IsbmBUt08uLzZd7Zu9idgXOtDDC', '2026-03-10 17:20:38.272+01', NULL);

INSERT INTO Utilisateur (nom, prenom, email, pseudo, mot_de_passe, date_inscription, derniere_activite)
VALUES ('Dupont', 'Jean', 'test6@owlearning.com', 'Jean.createur', '$2a$10$uJweB1MBHrQNXgZEskjIHOEL9j9l2DZEAmvw07ZfjTDq2mdNAcipG', '2026-03-10 17:22:44.283+01', NULL);

INSERT INTO Utilisateur (nom, prenom, email, pseudo, mot_de_passe, date_inscription, derniere_activite)
VALUES ('Lavanne', 'Marie', 'test7@owlearning.com', 'Marie.createur', '$2a$10$iQgBVhLB.dMvmgEDeszkvuLHiHdVrORtkdGQ/vIP28UY8XFVfd1T2', '2026-03-10 17:23:20.545+01', NULL);

INSERT INTO Createur (id_utilisateur)
SELECT id_utilisateur
FROM Utilisateur
WHERE email = 'test6@owlearning.com';

INSERT INTO Createur (id_utilisateur)
SELECT id_utilisateur
FROM Utilisateur
WHERE email = 'test7@owlearning.com';

INSERT INTO Eleve (id_utilisateur, niveau_etude, age)
SELECT id_utilisateur, 'L1 Informatique', 19
FROM Utilisateur
WHERE email = 'test1@owlearning.com';

INSERT INTO Eleve (id_utilisateur, niveau_etude, age)
SELECT id_utilisateur, 'BTS SIO 1', 20
FROM Utilisateur
WHERE email = 'test2@owlearning.com';

INSERT INTO Eleve (id_utilisateur, niveau_etude, age)
SELECT id_utilisateur, 'Terminale STI2D', 18
FROM Utilisateur
WHERE email = 'test3@owlearning.com';

INSERT INTO Eleve (id_utilisateur, niveau_etude, age)
SELECT id_utilisateur, 'BUT Informatique 1', 21
FROM Utilisateur
WHERE email = 'test4@owlearning.com';

INSERT INTO Eleve (id_utilisateur, niveau_etude, age)
SELECT id_utilisateur, 'Licence Pro Web', 22
FROM Utilisateur
WHERE email = 'test5@owlearning.com';

INSERT INTO Cours (description, titre, date_creation, est_prive, est_publie, difficulte, id_createur)
SELECT
    'Une introduction progressive aux bases de données relationnelles. Vous apprendrez à modéliser des données, à écrire des requêtes SELECT et à combiner des tables avec des jointures.',
    'SQL pour débutants',
    '2026-03-01 09:00:00+01',
    false,
    true,
    'DEBUTANT',
    id_utilisateur
FROM Utilisateur
WHERE email = 'test6@owlearning.com';

INSERT INTO Cours (description, titre, date_creation, est_prive, est_publie, difficulte, id_createur)
SELECT
    'Apprenez à construire une API REST complète avec Spring Boot. Ce cours couvre la création de contrôleurs, la persistance avec Spring Data JPA et les bonnes pratiques de structuration d''un projet backend.',
    'Créer une API avec Spring Boot',
    '2026-03-02 10:00:00+01',
    false,
    true,
    'INTERMEDIAIRE',
    id_utilisateur
FROM Utilisateur
WHERE email = 'test6@owlearning.com';

INSERT INTO Cours (description, titre, date_creation, est_prive, est_publie, difficulte, id_createur)
SELECT
    'Maîtrisez les trois piliers du web : HTML, CSS et JavaScript. À la fin du cours, vous serez capable de créer une page web responsive de A à Z.',
    'Fondamentaux du développement web',
    '2026-03-03 11:00:00+01',
    false,
    true,
    'DEBUTANT',
    id_utilisateur
FROM Utilisateur
WHERE email = 'test7@owlearning.com';

INSERT INTO Cours (description, titre, date_creation, est_prive, est_publie, difficulte, id_createur)
SELECT
    'Développez votre logique de programmation en partant de zéro. Ce cours vous enseigne les structures fondamentales : variables, conditions, boucles et fonctions.',
    'Algorithmique essentielle',
    '2026-03-04 14:00:00+01',
    true,
    true,
    'DEBUTANT',
    id_utilisateur
FROM Utilisateur
WHERE email = 'test7@owlearning.com';

INSERT INTO categorie_cours (id_cours, categorie)
SELECT id_cours, 'BASE_DE_DONNEES'
FROM Cours
WHERE titre = 'SQL pour débutants';

INSERT INTO categorie_cours (id_cours, categorie)
SELECT id_cours, 'DEVELOPPEMENT_WEB'
FROM Cours
WHERE titre = 'Créer une API avec Spring Boot';

INSERT INTO categorie_cours (id_cours, categorie)
SELECT id_cours, 'BASE_DE_DONNEES'
FROM Cours
WHERE titre = 'Créer une API avec Spring Boot';

INSERT INTO categorie_cours (id_cours, categorie)
SELECT id_cours, 'DEVELOPPEMENT_WEB'
FROM Cours
WHERE titre = 'Fondamentaux du développement web';

INSERT INTO categorie_cours (id_cours, categorie)
SELECT id_cours, 'PROGRAMMATION_ALGORITHMIQUE'
FROM Cours
WHERE titre = 'Algorithmique essentielle';

INSERT INTO categorie_cours (id_cours, categorie)
SELECT id_cours, 'MATHEMATIQUES'
FROM Cours
WHERE titre = 'Algorithmique essentielle';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT 'Le modèle relationnel',
       'Comprenez comment organiser avec une base de données : tables, colonnes, clés primaires et clés étrangères. Vous apprendrez à lire et à interpréter un schéma entité-relation.',
       id_cours
FROM Cours
WHERE titre = 'SQL pour débutants';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT 'Requêtes SELECT et filtres',
       'Écrivez vos premières requêtes SQL : sélectionner des colonnes, filtrer avec WHERE, trier avec ORDER BY et limiter les résultats avec LIMIT.',
       id_cours
FROM Cours
WHERE titre = 'SQL pour débutants';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT 'Jointures et agrégations',
       'Combinez plusieurs tables avec INNER JOIN et LEFT JOIN. Calculez des statistiques avec GROUP BY et les fonctions COUNT, SUM, AVG.',
       id_cours
FROM Cours
WHERE titre = 'SQL pour débutants';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT 'Premiers pas avec Spring Boot',
       'Initialisez un projet Spring Boot avec Spring Initializr, créez votre premier contrôleur REST et testez vos endpoints.',
       id_cours
FROM Cours
WHERE titre = 'Créer une API avec Spring Boot';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT 'Persistance avec Spring Data JPA',
       'Reliez votre API à une base de données. Créez des entités JPA, configurez un repository et effectuez des opérations CRUD sans écrire une seule requête SQL.',
       id_cours
FROM Cours
WHERE titre = 'Créer une API avec Spring Boot';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT 'HTML : structurer le contenu',
       'Découvrez les balises HTML5 essentielles et la sémantique web. Vous construirez la structure d''une page complète avec en-tête, navigation, contenu principal et pied de page.',
       id_cours
FROM Cours
WHERE titre = 'Fondamentaux du développement web';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT 'CSS : mise en forme et mise en page',
       'Appliquez des styles avec les sélecteurs CSS, maîtrisez le modèle de boîte et créez des mises en page modernes avec Flexbox. Introduction aux media queries pour le responsive design.',
       id_cours
FROM Cours
WHERE titre = 'Fondamentaux du développement web';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT 'JavaScript : rendre la page interactive',
       'Ajoutez du comportement à vos pages : manipulation du DOM, gestion des événements (clic, saisie) et modification dynamique du contenu sans rechargement.',
       id_cours
FROM Cours
WHERE titre = 'Fondamentaux du développement web';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT 'Variables, conditions et boucles',
       'Posez les bases de tout algorithme : déclarer des variables, utiliser les if/else et répéter des actions avec while et for.',
       id_cours FROM Cours WHERE titre = 'Algorithmique essentielle';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT 'Fonctions et décomposition',
       'Apprenez à découper un problème complexe en petites fonctions réutilisables.',
       id_cours FROM Cours WHERE titre = 'Algorithmique essentielle';

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('Diagramme entité-relation','https://upload.wikimedia.org/wikipedia/commons/thumb/7/72/ER_Diagram_MMORPG.png/800px-ER_Diagram_MMORPG.png','IMAGE');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('Cours complet SQL – freeCodeCamp','https://www.youtube.com/watch?v=HXV3zeQKqGY','VIDEO');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('Documentation officielle PostgreSQL','https://www.postgresql.org/files/documentation/pdf/17/postgresql-17-A4.pdf','FICHIER_PDF');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('Illustration des types de JOIN','https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/SQL_Joins.svg/800px-SQL_Joins.svg.png','IMAGE');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('Spring Boot Tutorial – Amigoscode','https://www.youtube.com/watch?v=9SGDpanrc8U','VIDEO');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('Projet starter API Spring Boot','https://github.com/spring-guides/gs-rest-service/archive/refs/heads/main.zip','FICHIER_ZIP');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('Spring Data JPA – Vidéo complète','https://www.youtube.com/watch?v=8SGI_XS5OPw','VIDEO');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('Projet starter Spring Data JPA','https://github.com/spring-guides/gs-accessing-data-jpa/archive/refs/heads/main.zip','FICHIER_ZIP');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('HTML en 1 heure – freeCodeCamp','https://www.youtube.com/watch?v=pQN-pnXPaVg','VIDEO');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('Schéma balises sémantiques HTML5','https://upload.wikimedia.org/wikipedia/commons/thumb/9/9a/HTML5_elements_flowchart.svg/800px-HTML5_elements_flowchart.svg.png','IMAGE');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('CSS Flexbox en 20 minutes – Traversy Media','https://www.youtube.com/watch?v=fYq5PXgSsbE','VIDEO');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('JavaScript DOM Crash Course – Traversy Media','https://www.youtube.com/watch?v=0ik6X4DJKCc','VIDEO');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('Exemple de flowchart algorithmique','https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/LampFlowchart.svg/500px-LampFlowchart.svg.png','IMAGE');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('Introduction à l''algorithmique – CS50 Harvard','https://www.youtube.com/watch?v=IDDmrzzB14M','VIDEO');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('Illustration du tri rapide (quicksort)','https://upload.wikimedia.org/wikipedia/commons/thumb/6/6a/Sorting_quicksort_anim.gif/280px-Sorting_quicksort_anim.gif','IMAGE');

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r JOIN Chapitre ch ON ch.titre = 'Le modèle relationnel'
WHERE r.nom = 'Diagramme entité-relation';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r JOIN Chapitre ch ON ch.titre = 'Le modèle relationnel'
WHERE r.nom = 'Cours complet SQL – freeCodeCamp';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r JOIN Chapitre ch ON ch.titre = 'Requêtes SELECT et filtres'
WHERE r.nom = 'Documentation officielle PostgreSQL';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r JOIN Chapitre ch ON ch.titre = 'Jointures et agrégations'
WHERE r.nom = 'Illustration des types de JOIN';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r JOIN Chapitre ch ON ch.titre = 'Premiers pas avec Spring Boot'
WHERE r.nom = 'Spring Boot Tutorial – Amigoscode';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r JOIN Chapitre ch ON ch.titre = 'Premiers pas avec Spring Boot'
WHERE r.nom = 'Projet starter API Spring Boot';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r JOIN Chapitre ch ON ch.titre = 'Persistance avec Spring Data JPA'
WHERE r.nom = 'Spring Data JPA – Vidéo complète';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r JOIN Chapitre ch ON ch.titre = 'Persistance avec Spring Data JPA'
WHERE r.nom = 'Projet starter Spring Data JPA';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r JOIN Chapitre ch ON ch.titre = 'HTML : structurer le contenu'
WHERE r.nom = 'HTML en 1 heure – freeCodeCamp';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r JOIN Chapitre ch ON ch.titre = 'HTML : structurer le contenu'
WHERE r.nom = 'Schéma balises sémantiques HTML5';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r JOIN Chapitre ch ON ch.titre = 'CSS : mise en forme et mise en page'
WHERE r.nom = 'CSS Flexbox en 20 minutes – Traversy Media';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r JOIN Chapitre ch ON ch.titre = 'JavaScript : rendre la page interactive'
WHERE r.nom = 'JavaScript DOM Crash Course – Traversy Media';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r JOIN Chapitre ch ON ch.titre = 'Variables, conditions et boucles'
WHERE r.nom = 'Exemple de flowchart algorithmique';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r JOIN Chapitre ch ON ch.titre = 'Variables, conditions et boucles'
WHERE r.nom = 'Introduction à l''algorithmique – CS50 Harvard';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r JOIN Chapitre ch ON ch.titre = 'Fonctions et décomposition'
WHERE r.nom = 'Illustration du tri rapide (quicksort)';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
             JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c JOIN Utilisateur u ON u.email = 'test2@owlearning.com'
             JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c JOIN Utilisateur u ON u.email = 'test3@owlearning.com'
             JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c JOIN Utilisateur u ON u.email = 'test4@owlearning.com'
             JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c JOIN Utilisateur u ON u.email = 'test2@owlearning.com'
             JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Créer une API avec Spring Boot';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c JOIN Utilisateur u ON u.email = 'test4@owlearning.com'
             JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Créer une API avec Spring Boot';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
             JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Créer une API avec Spring Boot';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
             JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c JOIN Utilisateur u ON u.email = 'test3@owlearning.com'
             JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
             JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
             JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c JOIN Utilisateur u ON u.email = 'test2@owlearning.com'
             JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c JOIN Utilisateur u ON u.email = 'test3@owlearning.com'
             JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
             JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle';

-- test1 : SQL terminé (3/3), dev web 2/3, algo 2/2 (100%)
INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants' AND ch.titre = 'Le modèle relationnel';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants' AND ch.titre = 'Requêtes SELECT et filtres';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants' AND ch.titre = 'Jointures et agrégations';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web' AND ch.titre = 'HTML : structurer le contenu';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web' AND ch.titre = 'CSS : mise en forme et mise en page';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle' AND ch.titre = 'Variables, conditions et boucles';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle' AND ch.titre = 'Fonctions et décomposition';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test2@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants' AND ch.titre = 'Le modèle relationnel';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test2@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants' AND ch.titre = 'Requêtes SELECT et filtres';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test2@owlearning.com'
                                                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Créer une API avec Spring Boot' AND ch.titre = 'Premiers pas avec Spring Boot';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test2@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle' AND ch.titre = 'Variables, conditions et boucles';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test3@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants' AND ch.titre = 'Le modèle relationnel';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test3@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web' AND ch.titre = 'HTML : structurer le contenu';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Créer une API avec Spring Boot' AND ch.titre = 'Premiers pas avec Spring Boot';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web' AND ch.titre = 'HTML : structurer le contenu';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web' AND ch.titre = 'CSS : mise en forme et mise en page';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web' AND ch.titre = 'JavaScript : rendre la page interactive';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle' AND ch.titre = 'Variables, conditions et boucles';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch JOIN Cours c ON ch.id_cours = c.id_cours
                 JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
                 JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle' AND ch.titre = 'Fonctions et décomposition';

WITH new_discussion AS (
    INSERT INTO Discussion DEFAULT VALUES
    RETURNING id_discussion
),
participants AS (
    INSERT INTO participation_discussion (id_utilisateur, id_discussion)
    SELECT u.id_utilisateur, d.id_discussion
    FROM Utilisateur u
    CROSS JOIN new_discussion d
    WHERE u.email IN ('test1@owlearning.com', 'test2@owlearning.com', 'test3@owlearning.com')
),
msg1 AS (
    INSERT INTO Message (date_creation, contenu, statut, id_discussion, id_utilisateur)
    SELECT
        '2026-03-10 18:00:00+01',
        'Salut, vous avancez sur le cours SQL ?',
        'ENVOYE',
        d.id_discussion,
        u.id_utilisateur
    FROM new_discussion d
    CROSS JOIN Utilisateur u
    WHERE u.email = 'test1@owlearning.com'
    RETURNING id_message
),
msg2 AS (
    INSERT INTO Message (date_creation, contenu, statut, id_discussion, id_utilisateur)
    SELECT
        '2026-03-10 18:02:00+01',
        'Oui, j''ai terminé les deux premiers chapitres, les jointures demandent un peu de pratique.',
        'ENVOYE',
        d.id_discussion,
        u.id_utilisateur
    FROM new_discussion d
    CROSS JOIN Utilisateur u
    WHERE u.email = 'test2@owlearning.com'
    RETURNING id_message
),
msg3 AS (
    INSERT INTO Message (date_creation, contenu, statut, id_discussion, id_utilisateur)
    SELECT
        '2026-03-10 18:05:00+01',
        'Je suis encore sur le premier chapitre, mais le PDF sur le modèle relationnel aide bien.',
        'ENVOYE',
        d.id_discussion,
        u.id_utilisateur
    FROM new_discussion d
    CROSS JOIN Utilisateur u
    WHERE u.email = 'test3@owlearning.com'
    RETURNING id_message
)
INSERT INTO piece_jointe (id_message, id_ressource)
SELECT m.id_message, r.id_ressource
FROM msg3 m
CROSS JOIN Ressource r
WHERE r.nom = 'MCD et schéma relationnel - PDF';

WITH new_discussion AS (
    INSERT INTO Discussion DEFAULT VALUES
    RETURNING id_discussion
),
participants AS (
    INSERT INTO participation_discussion (id_utilisateur, id_discussion)
    SELECT u.id_utilisateur, d.id_discussion
    FROM Utilisateur u
    CROSS JOIN new_discussion d
    WHERE u.email IN ('test6@owlearning.com', 'test7@owlearning.com')
),
msg1 AS (
    INSERT INTO Message (date_creation, contenu, statut, id_discussion, id_utilisateur)
    SELECT
        '2026-03-10 18:10:00+01',
        'Je propose de publier d''abord le cours SQL puis l''API Spring Boot.',
        'ENVOYE',
        d.id_discussion,
        u.id_utilisateur
    FROM new_discussion d
    CROSS JOIN Utilisateur u
    WHERE u.email = 'test6@owlearning.com'
    RETURNING id_message
),
msg2 AS (
    INSERT INTO Message (date_creation, contenu, statut, id_discussion, id_utilisateur)
    SELECT
        '2026-03-10 18:12:00+01',
        'Bonne idée. Je finalise aussi les ressources du cours web pour ce soir.',
        'ENVOYE',
        d.id_discussion,
        u.id_utilisateur
    FROM new_discussion d
    CROSS JOIN Utilisateur u
    WHERE u.email = 'test7@owlearning.com'
    RETURNING id_message
),
msg3 AS (
    INSERT INTO Message (date_creation, contenu, statut, id_discussion, id_utilisateur)
    SELECT
        '2026-03-10 18:14:00+01',
        'Parfait, je t''envoie aussi le starter ZIP pour le chapitre Spring Boot.',
        'ENVOYE',
        d.id_discussion,
        u.id_utilisateur
    FROM new_discussion d
    CROSS JOIN Utilisateur u
    WHERE u.email = 'test6@owlearning.com'
    RETURNING id_message
)
INSERT INTO piece_jointe (id_message, id_ressource)
SELECT m.id_message, r.id_ressource
FROM msg3 m
CROSS JOIN Ressource r
WHERE r.nom = 'Starter API Spring Boot';

WITH new_discussion AS (
    INSERT INTO Discussion DEFAULT VALUES
    RETURNING id_discussion
),
participants AS (
    INSERT INTO participation_discussion (id_utilisateur, id_discussion)
    SELECT u.id_utilisateur, d.id_discussion
    FROM Utilisateur u
    CROSS JOIN new_discussion d
    WHERE u.email IN ('test6@owlearning.com', 'test4@owlearning.com')
),
msg1 AS (
    INSERT INTO Message (date_creation, contenu, statut, id_discussion, id_utilisateur)
    SELECT
        '2026-03-10 18:20:00+01',
        'Bonjour, j''ai une question sur le chapitre JavaScript du cours web.',
        'ENVOYE',
        d.id_discussion,
        u.id_utilisateur
    FROM new_discussion d
    CROSS JOIN Utilisateur u
    WHERE u.email = 'test4@owlearning.com'
    RETURNING id_message
),
msg2 AS (
    INSERT INTO Message (date_creation, contenu, statut, id_discussion, id_utilisateur)
    SELECT
        '2026-03-10 18:22:00+01',
        'Bonjour Bryan, commence par bien relire la ressource HTML/CSS avant de passer aux événements JavaScript.',
        'ENVOYE',
        d.id_discussion,
        u.id_utilisateur
    FROM new_discussion d
    CROSS JOIN Utilisateur u
    WHERE u.email = 'test6@owlearning.com'
    RETURNING id_message
),
msg3 AS (
    INSERT INTO Message (date_creation, contenu, statut, id_discussion, id_utilisateur)
    SELECT
        '2026-03-10 18:24:00+01',
        'Merci, je vais reprendre ça dans l''ordre et refaire les exercices.',
        'ENVOYE',
        d.id_discussion,
        u.id_utilisateur
    FROM new_discussion d
    CROSS JOIN Utilisateur u
    WHERE u.email = 'test4@owlearning.com'
    RETURNING id_message
)
INSERT INTO piece_jointe (id_message, id_ressource)
SELECT m.id_message, r.id_ressource
FROM msg2 m
CROSS JOIN Ressource r
WHERE r.nom = 'Cheatsheet HTML CSS';

COMMIT;