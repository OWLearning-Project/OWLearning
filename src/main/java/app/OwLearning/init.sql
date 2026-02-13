CREATE TABLE Categorie(
   categorie VARCHAR(50),
   PRIMARY KEY(categorie)
);

CREATE TABLE Utilisateur(
   id_utilisateur SERIAL ,
   nom VARCHAR(50)  NOT NULL,
   prenom VARCHAR(50)  NOT NULL,
   email VARCHAR(100)  NOT NULL,
   pseudo VARCHAR(50),
   date_inscription TIMESTAMPTZ NOT NULL,
   derniere_activite TIMESTAMPTZ,
   mot_de_passe VARCHAR(255) NOT NULL,
   PRIMARY KEY(id_utilisateur),
   UNIQUE(email)
);

CREATE TABLE Createur(
   id_utilisateur INTEGER,
   PRIMARY KEY(id_utilisateur),
   FOREIGN KEY(id_utilisateur) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE
);

CREATE TABLE Eleve(
   id_utilisateur INTEGER,
   niveau_etude VARCHAR(50),
   age INTEGER,
   PRIMARY KEY(id_utilisateur),
   FOREIGN KEY(id_utilisateur) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE
);

CREATE TABLE Discussion(
   id_discussion SERIAL,
   PRIMARY KEY(id_discussion)
);

CREATE TABLE Message(
   id_message SERIAL,
   date_creation TIMESTAMPTZ,
   contenu TEXT NOT NULL,
   statut VARCHAR(50),
   id_discussion INTEGER NOT NULL,
   id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(id_message),
   FOREIGN KEY(id_discussion) REFERENCES Discussion(id_discussion) ON DELETE CASCADE,
   FOREIGN KEY(id_utilisateur) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE
);

CREATE TABLE Ressource(
   id_ressource SERIAL,
   nom VARCHAR(50)  NOT NULL,
   url VARCHAR(255) ,
   type_ressource VARCHAR(50),
   PRIMARY KEY(id_ressource)
);

CREATE TABLE Cours(
   id_cours SERIAL,
   description VARCHAR(255) ,
   titre VARCHAR(50)  NOT NULL,
   date_creation TIMESTAMPTZ,
   est_prive BOOLEAN NOT NULL,
   est_publie BOOLEAN NOT NULL,
   difficulte VARCHAR(50),
   id_createur INTEGER NOT NULL,
   PRIMARY KEY(id_cours),
   FOREIGN KEY(id_createur) REFERENCES Createur(id_utilisateur) ON DELETE CASCADE
);

CREATE TABLE Chapitre(
   id_chapitre SERIAL,
   titre VARCHAR(50)  NOT NULL,
   description VARCHAR(255) ,
   id_cours INTEGER NOT NULL,
   PRIMARY KEY(id_chapitre),
   FOREIGN KEY(id_cours) REFERENCES Cours(id_cours) ON DELETE CASCADE
);

CREATE TABLE piece_jointe(
   id_message INTEGER,
   id_ressource INTEGER,
   PRIMARY KEY(id_message, id_ressource),
   FOREIGN KEY(id_message) REFERENCES Message(id_message) ON DELETE CASCADE,
   FOREIGN KEY(id_ressource) REFERENCES Ressource(id_ressource) ON DELETE CASCADE
);

CREATE TABLE categorie_cours(
   id_cours INTEGER,
   categorie VARCHAR(50),
   PRIMARY KEY(id_cours, categorie),
   FOREIGN KEY(id_cours) REFERENCES Cours(id_cours) ON DELETE CASCADE,
   FOREIGN KEY(categorie) REFERENCES Categorie(categorie) ON DELETE CASCADE
);

CREATE TABLE ressource_chapitre(
   id_ressource INTEGER,
   id_chapitre INTEGER,
   PRIMARY KEY(id_ressource, id_chapitre),
   FOREIGN KEY(id_ressource) REFERENCES Ressource(id_ressource) ON DELETE CASCADE,
   FOREIGN KEY(id_chapitre) REFERENCES Chapitre(id_chapitre) ON DELETE CASCADE
);

CREATE TABLE participation_discussion(
   id_utilisateur INTEGER,
   id_discussion INTEGER,
   PRIMARY KEY(id_utilisateur, id_discussion),
   FOREIGN KEY(id_utilisateur) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE,
   FOREIGN KEY(id_discussion) REFERENCES Discussion(id_discussion) ON DELETE CASCADE
);

CREATE TABLE progression(
   id_cours INTEGER,
   id_eleve INTEGER,
   taux_progression NUMERIC(15,2) DEFAULT 0 NOT NULL,
   PRIMARY KEY(id_cours, id_eleve),
   FOREIGN KEY(id_cours) REFERENCES Cours(id_cours) ON DELETE CASCADE,
   FOREIGN KEY(id_eleve) REFERENCES Eleve(id_utilisateur) ON DELETE CASCADE
);

CREATE TABLE inscription(
   id_cours INTEGER,
   id_eleve INTEGER,
   etat VARCHAR(50) ,
   PRIMARY KEY(id_cours, id_eleve),
   FOREIGN KEY(id_cours) REFERENCES Cours(id_cours) ON DELETE CASCADE,
   FOREIGN KEY(id_eleve) REFERENCES Eleve(id_utilisateur) ON DELETE CASCADE
);

CREATE TABLE chapitres_termines(
   id_chapitre INTEGER,
   id_eleve INTEGER,
   PRIMARY KEY(id_chapitre, id_eleve),
   FOREIGN KEY(id_chapitre) REFERENCES Chapitre(id_chapitre) ON DELETE CASCADE,
   FOREIGN KEY(id_eleve) REFERENCES Eleve(id_utilisateur) ON DELETE CASCADE
);

-- TRIGGER pour l'insertion ou la suppression d'un chapitre termine

CREATE FUNCTION mettre_a_jour_progression()
RETURNS TRIGGER AS $$
DECLARE
   id_chapitre_2 INTEGER;
   id_eleve_2 INTEGER;
   id_cours_2 INTEGER;
   nb_total INTEGER;
   nb_fini INTEGER;
   taux NUMERIC(15,2);
BEGIN
   IF (TG_OP = 'INSERT') THEN
      id_chapitre_2 = NEW.id_chapitre;
      id_eleve_2 = NEW.id_eleve;
   ELSIF (TG_OP = 'DELETE') THEN
      id_chapitre_2 = OLD.id_chapitre;
      id_eleve_2 = OLD.id_eleve;
   END IF;
   
   SELECT id_cours
   INTO id_cours_2
   FROM Chapitre
   WHERE id_chapitre = id_chapitre_2;

   SELECT COUNT(*) 
   INTO nb_total
   FROM Chapitre
   WHERE id_cours = id_cours_2;

   SELECT COUNT(*)
   INTO nb_fini
   FROM chapitres_termines JOIN Chapitre USING(id_chapitre)
   WHERE id_eleve = id_eleve_2 AND id_cours = id_cours_2; 
   
   IF (nb_total = 0) THEN
      taux = 0;
   ELSE
      taux = (nb_fini::NUMERIC / nb_total::NUMERIC) * 100;
   END IF;

   UPDATE progression
   SET taux_progression = taux
   WHERE id_cours = id_cours_2 AND id_eleve = id_eleve_2;

   RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_mettre_a_jour_progression
AFTER INSERT OR DELETE ON chapitres_termines
FOR EACH ROW
EXECUTE FUNCTION mettre_a_jour_progression();

-- TRIGGER pour creer la progression lorsqu'on inscrit un eleve a un cours

CREATE FUNCTION inscription_eleve_cours()
RETURNS TRIGGER AS $$
BEGIN
   IF (TG_OP = 'INSERT') THEN
      INSERT INTO progression (id_cours, id_eleve)
      VALUES (NEW.id_cours, NEW.id_eleve);
      RETURN NEW;

   ELSIF (TG_OP = 'DELETE') THEN
      DELETE FROM progression 
      WHERE id_cours = OLD.id_cours AND id_eleve = OLD.id_eleve;

      DELETE FROM chapitres_termines 
      WHERE id_eleve = OLD.id_eleve 
      AND id_chapitre IN (
          SELECT id_chapitre FROM Chapitre WHERE id_cours = OLD.id_cours
      );
      
      RETURN OLD;
   END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_inscription_eleve_cours
AFTER INSERT OR DELETE ON inscription
FOR EACH ROW
EXECUTE FUNCTION inscription_eleve_cours();


