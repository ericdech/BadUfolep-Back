DELETE FROM PLATEAUJOUEUR;
DELETE FROM POULE;
DELETE FROM PLATEAUCATEGORIE;
DELETE FROM PLATEAU;
DELETE FROM JOUEUR;
DELETE FROM CATEGORIE;
DELETE FROM ADHERENT;
DELETE FROM CLUB;
DELETE FROM CHAMPIONNAT;
ALTER SEQUENCE HIBERNATE_SEQUENCE RESTART WITH 10;
INSERT INTO CHAMPIONNAT (idChampionnat, ucChampionnat, llChampionnat, nbjournee, statut) 
VALUES (1, 'UC1', 'LL1', 6, 'I');
INSERT INTO CLUB (idClub, ucClub, llClub, refFederation, nomSalle, adresse1Salle, adresse2Salle, villeSalle, email, invitationJoueurAutomatique)
VALUES (1, 'STCO', 'Saint Colomban', '001', 'Espace Yannick Noah', '', '', '', 'email@email', false);
INSERT INTO CLUB (idClub, ucClub, llClub, refFederation, nomSalle, adresse1Salle, adresse2Salle, villeSalle, email, invitationJoueurAutomatique)
VALUES (2, 'STPHIL', 'Saint Philbert', '002', 'Salle Daubié', '', '', '', 'email@email', false);
INSERT INTO ADHERENT (idAdherent, pseudo, password, nom, prenom, dateNaissance, sexe, licence, tel, email, administrateur, responsableChampionnat, responsableClub, idClub)
VALUES (1, 'COCO', 'COCO', 'DECHARTRE', 'Colin', '2003-06-16', 'M', '001', '0606060606', 'email@email', false, false, false, 1);
INSERT INTO ADHERENT (idAdherent, pseudo, password, nom, prenom, dateNaissance, sexe, licence, tel, email, administrateur, responsableChampionnat, responsableClub, idClub)
VALUES (2, 'NAEL', 'NAEL', 'DECHARTRE', 'Naël', '2000-08-01', 'M', '002', '0606060606', 'email@email', false, false, false, 1);
INSERT INTO ADHERENT (idAdherent, pseudo, password, nom, prenom, dateNaissance, sexe, licence, tel, email, administrateur, responsableChampionnat, responsableClub, idClub)
VALUES (3, 'MELIE', 'MELIE', 'DECHARTRE', 'Melie', '1998-08-24', 'F', '003', '0606060606', 'email@email', false, false, false, 1);
INSERT INTO ADHERENT (idAdherent, pseudo, password, nom, prenom, dateNaissance, sexe, licence, tel, email, administrateur, responsableChampionnat, responsableClub, idClub)
VALUES (4, 'ERIC', 'ERIC', 'DECHARTRE', 'Eric', '1966-09-29', 'M', '003', '0606060606', 'email@email', false, false, false, 1);
INSERT INTO CATEGORIE (idCategorie, idChampionnat, categorieAge, sexe, anneeNaissanceMin)
VALUES (1, 1, 'S1', 'M', 2005);
INSERT INTO CATEGORIE (idCategorie, idChampionnat, categorieAge, sexe, anneeNaissanceMin)
VALUES (2, 1, 'S2', 'M', 2003);
INSERT INTO JOUEUR (idJoueur, idChampionnat, idAdherent, idCategorie, idClub)
VALUES (1, 1, 1, 1, 1);
INSERT INTO JOUEUR (idJoueur, idChampionnat, idAdherent, idCategorie, idClub)
VALUES (2, 1, 2, 1, 1);
INSERT INTO JOUEUR (idJoueur, idChampionnat, idAdherent, idCategorie, idClub)
VALUES (3, 1, 3, 1, 1);
INSERT INTO PLATEAU (idPlateau, idChampionnat, idClub, numeroJournee, dateplateau, heuredebut, heurefin, nomsalle, adresse1salle, adresse2salle, statut)
VALUES (1, 1, 1, 1, '2020-02-01', '8:30', '13:30', 'Espace Yannick Noah', '', '', 'I');
INSERT INTO PLATEAUCATEGORIE (IDPLATEAU, IDCATEGORIE)
VALUES (1, 1);
INSERT INTO PLATEAUCATEGORIE (IDPLATEAU, IDCATEGORIE)
VALUES (1, 2);
INSERT INTO POULE (IDPOULE, IDPLATEAU, IDCATEGORIE, NUMERO)
VALUES (1, 1, 1, 1);
INSERT INTO POULE (IDPOULE, IDPLATEAU, IDCATEGORIE, NUMERO)
VALUES (2, 1, 1, 2);
INSERT INTO PLATEAUJOUEUR (idPlateauJoueur, idPlateau, idJoueur, idPlateauJoueurPaire, idPoule, presence, point, classement)
VALUES (1, 1, 1, null, 1, 'C', 0, 0);
INSERT INTO PLATEAUJOUEUR (idPlateauJoueur, idPlateau, idJoueur, idPlateauJoueurPaire, idPoule, presence, point, classement)
VALUES (2, 1, 2, 1, 1, 'C', 0, 0);
UPDATE PLATEAUJOUEUR SET idPlateauJoueurPaire = 2 WHERE idPlateauJoueur = 1;
