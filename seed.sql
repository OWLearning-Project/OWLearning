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
VALUES ('Akretche', 'Iraihane', 'test1@owlearning.com', 'test1', '$2a$10$tFuEYeZbsfKH8nLE4.fmwecFZ.0OexJ6dOWzdVwqq83npZkpf41NK', '2026-03-10 17:18:41.01+01', NULL);

INSERT INTO Utilisateur (nom, prenom, email, pseudo, mot_de_passe, date_inscription, derniere_activite)
VALUES ('Chhun', 'Noemie', 'test2@owlearning.com', 'test2', '$2a$10$ullSqyAlnbs/TYTLCxvTf.OW1lQdcsyTiR2zXeLmld92ZMBgSvWTu', '2026-03-10 17:19:02.186+01', NULL);

INSERT INTO Utilisateur (nom, prenom, email, pseudo, mot_de_passe, date_inscription, derniere_activite)
VALUES ('Vignon', 'Gabriel', 'test3@owlearning.com', 'test3', '$2a$10$3xBrgM3YYBNsklAsUTziHuNAG2UDYvQpOoRqyZuEgHMBy45LtSf4O', '2026-03-10 17:19:16.709+01', NULL);

INSERT INTO Utilisateur (nom, prenom, email, pseudo, mot_de_passe, date_inscription, derniere_activite)
VALUES ('Bigleone', 'Bryan', 'test4@owlearning.com', 'test4', '$2a$10$3TbQ1Iloylnos6RyJJpF/OYPg1z2D8hgRnYp/2qMZj8.jRQL63rZq', '2026-03-10 17:19:32.666+01', NULL);

INSERT INTO Utilisateur (nom, prenom, email, pseudo, mot_de_passe, date_inscription, derniere_activite)
VALUES ('Diallo', 'Anthia', 'test5@owlearning.com', 'test5', '$2a$10$HJ3kBcVmTuvzMzEFwQ09N.doK3IsbmBUt08uLzZd7Zu9idgXOtDDC', '2026-03-10 17:20:38.272+01', NULL);

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
    'Apprendre les bases de SQL, de la modélisation relationnelle et des requêtes courantes.',
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
    'Construire une API REST avec Spring Boot, les contrôleurs et la persistance.',
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
    'Maîtriser HTML, CSS, JavaScript et organiser un mini-projet front.',
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
    'Comprendre la logique algorithmique, les variables, les conditions, les boucles et les fonctions.',
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
SELECT
    'Découvrir le modèle relationnel',
    'Tables, relations, clés primaires et clés étrangères.',
    id_cours
FROM Cours
WHERE titre = 'SQL pour débutants';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT
    'Écrire ses premières requêtes SQL',
    'SELECT, WHERE, ORDER BY et filtres simples.',
    id_cours
FROM Cours
WHERE titre = 'SQL pour débutants';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT
    'Joindre et agréger des données',
    'JOIN, GROUP BY et fonctions d''agrégation.',
    id_cours
FROM Cours
WHERE titre = 'SQL pour débutants';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT
    'Créer une première API REST',
    'Projet Spring Boot, contrôleurs et endpoints REST.',
    id_cours
FROM Cours
WHERE titre = 'Créer une API avec Spring Boot';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT
    'Structurer une page en HTML',
    'Balises HTML principales et structure sémantique.',
    id_cours
FROM Cours
WHERE titre = 'Fondamentaux du développement web';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT
    'Mettre en forme avec CSS',
    'Sélecteurs, boîte, flexbox et responsive simple.',
    id_cours
FROM Cours
WHERE titre = 'Fondamentaux du développement web';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT
    'Ajouter de l''interaction avec JavaScript',
    'Variables, événements et manipulation du DOM.',
    id_cours
FROM Cours
WHERE titre = 'Fondamentaux du développement web';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT
    'Assembler un mini-projet vitrine',
    'Organisation d''un petit site cohérent.',
    id_cours
FROM Cours
WHERE titre = 'Fondamentaux du développement web';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT
    'Variables, conditions et entrées',
    'Notions de base pour démarrer un algorithme.',
    id_cours
FROM Cours
WHERE titre = 'Algorithmique essentielle';

INSERT INTO Chapitre (titre, description, id_cours)
SELECT
    'Boucles et fonctions',
    'Répétitions, découpage logique et réutilisation.',
    id_cours
FROM Cours
WHERE titre = 'Algorithmique essentielle';

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('MCD et schéma relationnel - PDF', 'https://owlearning.local/ressources/mcd-schema-relationnel.pdf', 'FICHIER_PDF');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('Exercices SQL corrigés', 'https://owlearning.local/ressources/exercices-sql-corriges.pdf', 'FICHIER_PDF');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('Starter API Spring Boot', 'https://owlearning.local/ressources/starter-api-springboot.zip', 'FICHIER_ZIP');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('Cheatsheet HTML CSS', 'https://owlearning.local/ressources/cheatsheet-html-css.pdf', 'FICHIER_PDF');

INSERT INTO Ressource (nom, url, type_ressource)
VALUES ('Schéma boucles et fonctions', 'https://owlearning.local/ressources/schema-boucles-fonctions.png', 'IMAGE');

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r
JOIN Chapitre ch ON ch.titre = 'Découvrir le modèle relationnel'
WHERE r.nom = 'MCD et schéma relationnel - PDF';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r
JOIN Chapitre ch ON ch.titre = 'Joindre et agréger des données'
WHERE r.nom = 'Exercices SQL corrigés';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r
JOIN Chapitre ch ON ch.titre = 'Créer une première API REST'
WHERE r.nom = 'Starter API Spring Boot';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r
JOIN Chapitre ch ON ch.titre = 'Structurer une page en HTML'
WHERE r.nom = 'Cheatsheet HTML CSS';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r
JOIN Chapitre ch ON ch.titre = 'Mettre en forme avec CSS'
WHERE r.nom = 'Cheatsheet HTML CSS';

INSERT INTO ressource_chapitre (id_ressource, id_chapitre)
SELECT r.id_ressource, ch.id_chapitre
FROM Ressource r
JOIN Chapitre ch ON ch.titre = 'Boucles et fonctions'
WHERE r.nom = 'Schéma boucles et fonctions';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c
JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c
JOIN Utilisateur u ON u.email = 'test2@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c
JOIN Utilisateur u ON u.email = 'test3@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c
JOIN Utilisateur u ON u.email = 'test4@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c
JOIN Utilisateur u ON u.email = 'test2@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Créer une API avec Spring Boot';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c
JOIN Utilisateur u ON u.email = 'test4@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Créer une API avec Spring Boot';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c
JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Créer une API avec Spring Boot';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c
JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c
JOIN Utilisateur u ON u.email = 'test3@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c
JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c
JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c
JOIN Utilisateur u ON u.email = 'test2@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c
JOIN Utilisateur u ON u.email = 'test3@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle';

INSERT INTO inscription (id_cours, id_eleve)
SELECT c.id_cours, e.id_utilisateur
FROM Cours c
JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants'
  AND ch.titre = 'Découvrir le modèle relationnel';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants'
  AND ch.titre = 'Écrire ses premières requêtes SQL';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants'
  AND ch.titre = 'Joindre et agréger des données';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test2@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants'
  AND ch.titre = 'Découvrir le modèle relationnel';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test2@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants'
  AND ch.titre = 'Écrire ses premières requêtes SQL';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test3@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'SQL pour débutants'
  AND ch.titre = 'Découvrir le modèle relationnel';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test2@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Créer une API avec Spring Boot'
  AND ch.titre = 'Créer une première API REST';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Créer une API avec Spring Boot'
  AND ch.titre = 'Créer une première API REST';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web'
  AND ch.titre = 'Structurer une page en HTML';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web'
  AND ch.titre = 'Mettre en forme avec CSS';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test3@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web'
  AND ch.titre = 'Structurer une page en HTML';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web'
  AND ch.titre = 'Structurer une page en HTML';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web'
  AND ch.titre = 'Mettre en forme avec CSS';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Fondamentaux du développement web'
  AND ch.titre = 'Ajouter de l''interaction avec JavaScript';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle'
  AND ch.titre = 'Variables, conditions et entrées';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test1@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle'
  AND ch.titre = 'Boucles et fonctions';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test2@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle'
  AND ch.titre = 'Variables, conditions et entrées';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle'
  AND ch.titre = 'Variables, conditions et entrées';

INSERT INTO chapitres_termines (id_chapitre, id_eleve)
SELECT ch.id_chapitre, e.id_utilisateur
FROM Chapitre ch
JOIN Cours c ON ch.id_cours = c.id_cours
JOIN Utilisateur u ON u.email = 'test5@owlearning.com'
JOIN Eleve e ON e.id_utilisateur = u.id_utilisateur
WHERE c.titre = 'Algorithmique essentielle'
  AND ch.titre = 'Boucles et fonctions';

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