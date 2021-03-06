DROP TABLE IF EXISTS MATCHSIMPLE;
DROP TABLE IF EXISTS MATCHDOUBLE;
DROP TABLE IF EXISTS PLATEAUJOUEUR;
DROP TABLE IF EXISTS POULE;
DROP TABLE IF EXISTS PLATEAUCATEGORIE;
DROP TABLE IF EXISTS PLATEAU;
DROP TABLE IF EXISTS JOUEUR;
DROP TABLE IF EXISTS CATEGORIE;
DROP TABLE IF EXISTS CHAMPIONNAT;
DROP TABLE IF EXISTS ADHERENT;
DROP TABLE IF EXISTS CLUB;
DROP SEQUENCE IF EXISTS HIBERNATE_SEQUENCE;

CREATE SEQUENCE HIBERNATE_SEQUENCE;

CREATE TABLE CHAMPIONNAT (
    IDCHAMPIONNAT INTEGER NOT NULL,
    UCCHAMPIONNAT VARCHAR(20) NOT NULL,
    LLCHAMPIONNAT VARCHAR(40),
    LOGOCHAMPIONNAT BYTEA,
    NBJOURNEE INTEGER,
    STATUT CHAR(1) NOT NULL DEFAULT 'I',
    PRIMARY KEY (IDCHAMPIONNAT),
    UNIQUE (UCCHAMPIONNAT)
);

CREATE TABLE ADHERENT (
    IDADHERENT INTEGER NOT NULL,
    PSEUDO VARCHAR(20) NOT NULL,
    PASSWORD VARCHAR(20),
    NOM VARCHAR(40),
    PRENOM VARCHAR(40),
    DATENAISSANCE DATE,
    SEXE CHAR(1),
    LICENCE VARCHAR(40),
    TEL VARCHAR(20),
    EMAIL VARCHAR(80),
    ADMINISTRATEUR BOOLEAN NOT NULL DEFAULT FALSE,
    RESPONSABLECHAMPIONNAT BOOLEAN NOT NULL DEFAULT FALSE,
    RESPONSABLECLUB BOOLEAN NOT NULL DEFAULT FALSE,
    IDCLUB INTEGER,
    PRIMARY KEY (IDADHERENT),
    UNIQUE (PSEUDO)
);

CREATE TABLE CLUB (
    IDCLUB INTEGER NOT NULL,
    UCCLUB VARCHAR(20) NOT NULL,
    LLCLUB VARCHAR(40),
    REFFEDERATION VARCHAR(40),
    NOMSALLE VARCHAR(40),
    ADRESSE1SALLE VARCHAR(40),
    ADRESSE2SALLE VARCHAR(40),
    VILLESALLE VARCHAR(40),
    EMAIL VARCHAR(80),
    INVITATIONJOUEURAUTOMATIQUE BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (IDCLUB),
    UNIQUE (UCCLUB)
);

CREATE TABLE PLATEAU (
    IDPLATEAU INTEGER NOT NULL,
    IDCHAMPIONNAT INTEGER NOT NULL,
    IDCLUB INTEGER NOT NULL,
    NUMEROJOURNEE INTEGER NOT NULL,
    DATEPLATEAU DATE,
    HEUREDEBUT TIME,
    HEUREFIN TIME,
    NOMSALLE VARCHAR(40),
    ADRESSE1SALLE VARCHAR(40),
    ADRESSE2SALLE VARCHAR(40),
    VILLESALLE VARCHAR(40),
    STATUT CHAR(1) NOT NULL DEFAULT 'I',
    PRIMARY KEY (IDPLATEAU),
    UNIQUE (IDCHAMPIONNAT, IDCLUB, NUMEROJOURNEE)
);

CREATE TABLE CATEGORIE (
    IDCATEGORIE INTEGER NOT NULL,
    IDCHAMPIONNAT INTEGER NOT NULL,
    CATEGORIEAGE VARCHAR(2) NOT NULL,
    SEXE CHAR(1) NOT NULL,
    ANNEENAISSANCEMIN INTEGER NOT NULL,
    PRIMARY KEY (IDCATEGORIE),
    UNIQUE (IDCHAMPIONNAT, CATEGORIEAGE, SEXE)
);

CREATE TABLE PLATEAUJOUEUR (
    IDPLATEAUJOUEUR INTEGER NOT NULL,
    IDPLATEAU INTEGER NOT NULL,
    IDJOUEUR INTEGER NOT NULL,
    IDPLATEAUJOUEURPAIRE INTEGER,
    IDPOULE INTEGER,
    PRESENCE CHAR(1) NOT NULL DEFAULT 'C',
    POINT INTEGER NOT NULL DEFAULT 0,
    CLASSEMENT INTEGER,
    PRIMARY KEY (IDPLATEAUJOUEUR),
    UNIQUE (IDPLATEAU, IDJOUEUR)
);

CREATE TABLE JOUEUR (
    IDJOUEUR INTEGER NOT NULL,
    IDCHAMPIONNAT INTEGER NOT NULL,
    IDADHERENT INTEGER NOT NULL,
    IDCATEGORIE INTEGER NOT NULL,
    IDCLUB INTEGER,
    IDJOUEURPAIRE INTEGER,
    POINTMEILLEURSPLATEAUX INTEGER DEFAULT 0,
    POINTTOUSPLATEAUX INTEGER DEFAULT 0,
    NIVEAU DECIMAL DEFAULT 0,
    CLASSEMENT INTEGER,
    PRIMARY KEY (IDJOUEUR),
    UNIQUE (IDCHAMPIONNAT, IDADHERENT, IDCATEGORIE)
);

CREATE TABLE MATCHSIMPLE (
    IDMATCHSIMPLE INTEGER NOT NULL,
    IDPLATEAUJOUEUR1 INTEGER NOT NULL,
    IDPLATEAUJOUEUR2 INTEGER NOT NULL,
    IDPOULE INTEGER NOT NULL,
    SCOREJOUEUR1 INTEGER NOT NULL DEFAULT 0,
    SCOREJOUEUR2 INTEGER NOT NULL DEFAULT 0,
    BONUSJOUEUR1 INTEGER NOT NULL DEFAULT 0,
    BONUSJOUEUR2 INTEGER NOT NULL DEFAULT 0,
    ANNOTATION VARCHAR(80),
    PRIMARY KEY (IDMATCHSIMPLE)
);

CREATE TABLE MATCHDOUBLE (
    IDMATCHDOUBLE INTEGER NOT NULL,
    IDPLATEAUJOUEUR1PAIRE1 INTEGER NOT NULL,
    IDPLATEAUJOUEUR1PAIRE2 INTEGER NOT NULL,
    IDPLATEAUJOUEUR2PAIRE1 INTEGER NOT NULL,
    IDPLATEAUJOUEUR2PAIRE2 INTEGER NOT NULL,
    IDPOULE INTEGER NOT NULL,
    SCOREPAIRE1 INTEGER NOT NULL DEFAULT 0,
    SCOREPAIRE2 INTEGER NOT NULL DEFAULT 0,
    BONUSPAIRE1 INTEGER NOT NULL DEFAULT 0,
    BONUSPAIRE2 INTEGER NOT NULL DEFAULT 0,
    ANNOTATION VARCHAR(80),
    PRIMARY KEY (IDMATCHDOUBLE)
);

CREATE TABLE POULE (
    IDPOULE INTEGER NOT NULL,
    IDPLATEAU INTEGER NOT NULL,
    IDCATEGORIE INTEGER NOT NULL,
    NUMERO INTEGER NOT NULL,
    FEUILLEMATCH BYTEA,
    PRIMARY KEY (IDPOULE),
    UNIQUE (IDPLATEAU, IDCATEGORIE, NUMERO)
);

CREATE TABLE PLATEAUCATEGORIE (
    IDPLATEAU INTEGER NOT NULL,
    IDCATEGORIE INTEGER NOT NULL,
    PRIMARY KEY (IDPLATEAU, IDCATEGORIE)
);

ALTER TABLE ADHERENT ADD FOREIGN KEY (IDCLUB) REFERENCES CLUB(IDCLUB);
ALTER TABLE PLATEAU ADD FOREIGN KEY (IDCHAMPIONNAT) REFERENCES CHAMPIONNAT(IDCHAMPIONNAT);
ALTER TABLE PLATEAU ADD FOREIGN KEY (IDCLUB) REFERENCES CLUB(IDCLUB);
ALTER TABLE CATEGORIE ADD FOREIGN KEY (IDCHAMPIONNAT) REFERENCES CHAMPIONNAT(IDCHAMPIONNAT);
ALTER TABLE PLATEAUJOUEUR ADD FOREIGN KEY (IDPLATEAU) REFERENCES PLATEAU(IDPLATEAU);
ALTER TABLE PLATEAUJOUEUR ADD FOREIGN KEY (IDPLATEAUJOUEURPAIRE) REFERENCES PLATEAUJOUEUR(IDPLATEAUJOUEUR);
ALTER TABLE PLATEAUJOUEUR ADD FOREIGN KEY (IDJOUEUR) REFERENCES JOUEUR(IDJOUEUR);
ALTER TABLE PLATEAUJOUEUR ADD FOREIGN KEY (IDPOULE) REFERENCES POULE(IDPOULE);
ALTER TABLE JOUEUR ADD FOREIGN KEY (IDCHAMPIONNAT) REFERENCES CHAMPIONNAT(IDCHAMPIONNAT);
ALTER TABLE JOUEUR ADD FOREIGN KEY (IDADHERENT) REFERENCES ADHERENT(IDADHERENT);
ALTER TABLE JOUEUR ADD FOREIGN KEY (IDCATEGORIE) REFERENCES CATEGORIE(IDCATEGORIE);
ALTER TABLE JOUEUR ADD FOREIGN KEY (IDCLUB) REFERENCES CLUB(IDCLUB);
ALTER TABLE JOUEUR ADD FOREIGN KEY (IDJOUEURPAIRE) REFERENCES JOUEUR(IDJOUEUR);
ALTER TABLE MATCHSIMPLE ADD FOREIGN KEY (IDPLATEAUJOUEUR1) REFERENCES PLATEAUJOUEUR(IDPLATEAUJOUEUR);
ALTER TABLE MATCHSIMPLE ADD FOREIGN KEY (IDPLATEAUJOUEUR2) REFERENCES PLATEAUJOUEUR(IDPLATEAUJOUEUR);
ALTER TABLE MATCHSIMPLE ADD FOREIGN KEY (IDPOULE) REFERENCES POULE(IDPOULE);
ALTER TABLE MATCHDOUBLE ADD FOREIGN KEY (IDPLATEAUJOUEUR1PAIRE1) REFERENCES PLATEAUJOUEUR(IDPLATEAUJOUEUR);
ALTER TABLE MATCHDOUBLE ADD FOREIGN KEY (IDPLATEAUJOUEUR1PAIRE2) REFERENCES PLATEAUJOUEUR(IDPLATEAUJOUEUR);
ALTER TABLE MATCHDOUBLE ADD FOREIGN KEY (IDPLATEAUJOUEUR2PAIRE1) REFERENCES PLATEAUJOUEUR(IDPLATEAUJOUEUR);
ALTER TABLE MATCHDOUBLE ADD FOREIGN KEY (IDPLATEAUJOUEUR2PAIRE2) REFERENCES PLATEAUJOUEUR(IDPLATEAUJOUEUR);
ALTER TABLE MATCHDOUBLE ADD FOREIGN KEY (IDPOULE) REFERENCES POULE(IDPOULE);
ALTER TABLE POULE ADD FOREIGN KEY (IDPLATEAU) REFERENCES PLATEAU(IDPLATEAU);
ALTER TABLE POULE ADD FOREIGN KEY (IDCATEGORIE) REFERENCES CATEGORIE(IDCATEGORIE);
ALTER TABLE PLATEAUCATEGORIE ADD FOREIGN KEY (IDPLATEAU) REFERENCES PLATEAU(IDPLATEAU);
ALTER TABLE PLATEAUCATEGORIE ADD FOREIGN KEY (IDCATEGORIE) REFERENCES CATEGORIE(IDCATEGORIE);