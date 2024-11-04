-- Creating the database

drop database dbGestionCentreFinal ;
CREATE DATABASE IF NOT EXISTS dbGestionCentreFinal;
USE dbGestionCentreFinal;

-- Table for users
CREATE TABLE utilisateur (
   id_utilisateur INT NOT NULL AUTO_INCREMENT,
    nom VARCHAR(254),
    prenom VARCHAR(254),
    email VARCHAR(254),
    passwordd VARCHAR(254),
    age INT,
    adresse VARCHAR(254),
    phone VARCHAR(254),
    sexe CHAR(1),
    rolee VARCHAR(254) DEFAULT 'visiteur',
    dateCreation DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_utilisateur)
);

-- Table for centers
CREATE TABLE centre (
    id_centre INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id_centre),
    nomCentre VARCHAR(254),
    localisation VARCHAR(254),
    contact VARCHAR(254)
);
-- Table for levels
CREATE TABLE niveau (
    id_niveau INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id_niveau),
    descriptionNiveau VARCHAR(254),
    dateDeCreation DATETIME DEFAULT CURRENT_TIMESTAMP,
    fk_centre INT,
    FOREIGN KEY (fk_centre) REFERENCES centre (id_centre) ON DELETE CASCADE
);
-- Table for parents
CREATE TABLE parent (
    id_parent INT NOT NULL ,
    PRIMARY KEY (id_parent),
    FOREIGN KEY (id_parent) REFERENCES utilisateur (id_utilisateur) ON DELETE CASCADE
);
-- Table for students
CREATE TABLE eleve (
    id_eleve INT NOT NULL ,
    PRIMARY KEY (id_eleve),
    date_inscription DATETIME DEFAULT CURRENT_TIMESTAMP,
    fk_parent INT
);

-- Table for teachers
CREATE TABLE enseignant (
    id_enseignant INT NOT NULL ,
    PRIMARY KEY (id_enseignant),
    fk_centre INT,
    avec_experience boolean,
    date_inscription DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (fk_centre) REFERENCES centre (id_centre) ON DELETE CASCADE,
    FOREIGN KEY (id_enseignant) REFERENCES utilisateur (id_utilisateur) ON DELETE CASCADE
);

-- Table for classes
CREATE TABLE classe (
    id_classe INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id_classe),
    description_classe VARCHAR(254),
    fk_niveau INT,
    FOREIGN KEY (fk_niveau) REFERENCES niveau (id_niveau) ON DELETE CASCADE
);

-- Table for groups within a class
CREATE TABLE groupe (
    id_groupe INT NOT NULL AUTO_INCREMENT,
    fk_classe INT,
    capacite INT,
    nbr_eleve INT,
    description_groupe VARCHAR(254),
    date_de_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_groupe),
    FOREIGN KEY (fk_classe) REFERENCES classe (id_classe) ON DELETE CASCADE
);

-- Table for rooms
CREATE TABLE salle (
    id_salle INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id_salle),
    capacite INT,
    disponible BOOL,
    occuper BOOL,
    fk_centre int,
	FOREIGN KEY ( fk_centre) REFERENCES centre (id_centre) ON DELETE CASCADE
);

-- Table for secretaries
CREATE TABLE secretaire (
    id_secretaire INT NOT NULL ,
    PRIMARY KEY (id_secretaire),
    fk_centre INT,
    FOREIGN KEY (id_secretaire) REFERENCES utilisateur (id_utilisateur) ON DELETE CASCADE,
    FOREIGN KEY (fk_centre) REFERENCES centre (id_centre) ON DELETE CASCADE
);
-- Table for subjects
CREATE TABLE matiere (
    id_matiere INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id_matiere),
    description_matiere VARCHAR(254),
    prix FLOAT,
    date_de_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    fk_niveau INT,
    FOREIGN KEY (fk_niveau) REFERENCES niveau (id_niveau) ON DELETE CASCADE
);

-- Table for sessions
CREATE TABLE seance (
    id_seance INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id_seance),
    heure_debut TIME,
    heure_fin TIME,
    annule BOOL,
    mise_a_jour BOOL,
    date_seance DATETIME,
    fk_salle INT,
    fk_groupe INT,
    FOREIGN KEY (fk_salle) REFERENCES salle (id_salle) ON DELETE CASCADE,
    FOREIGN KEY (fk_groupe) REFERENCES groupe (id_groupe) ON DELETE CASCADE
);

-- Table for student assignments to groups and subjects
CREATE TABLE affectation (
    id_affectation INT NOT NULL AUTO_INCREMENT,
    fk_eleve INT,
    fk_groupe INT,
    fk_matiere INT,
    PRIMARY KEY (id_affectation),
    UNIQUE KEY unique_assignment (fk_eleve, fk_matiere),  -- Ensures uniqueness for each student in each subject
    FOREIGN KEY (fk_eleve ) REFERENCES eleve (id_eleve) ON DELETE CASCADE,
    FOREIGN KEY (fk_groupe) REFERENCES groupe (id_groupe) ON DELETE CASCADE,
    FOREIGN KEY ( fk_matiere) REFERENCES matiere (id_matiere) ON DELETE CASCADE
);


-- Table for the link between teachers and groups in different subjects
CREATE TABLE enseignant_groupe_matiere (
    id_e_g_m Int,
    fk_enseignant INT,
    fk_groupe INT,
    fk_matiere INT,
    PRIMARY KEY (id_e_g_m),
    FOREIGN KEY (fk_enseignant) REFERENCES enseignant (id_enseignant) ON DELETE CASCADE,
    FOREIGN KEY (fk_groupe) REFERENCES groupe (id_groupe) ON DELETE CASCADE,
    FOREIGN KEY (fk_matiere) REFERENCES matiere (id_matiere) ON DELETE CASCADE
);

-- Table for student absences during sessions
CREATE TABLE absence (
    id_absence INT NOT NULL AUTO_INCREMENT,
    fk_seance INT,
    fk_eleve INT,
    justification VARCHAR(254),
    est_present BOOL,
    PRIMARY KEY (id_absence),
    UNIQUE KEY unique_absence (fk_seance, fk_eleve), -- Unique constraint to prevent duplicate absences for the same student in the same session
    FOREIGN KEY (fk_seance) REFERENCES seance (id_seance) ON DELETE CASCADE,
    FOREIGN KEY (fk_eleve) REFERENCES eleve (id_eleve) ON DELETE CASCADE
);
-- Table for payments
CREATE TABLE paiement (
    id_paiement INT NOT NULL AUTO_INCREMENT,
    fk_matiere INT,
    fk_eleve INT,
    date_paiment DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_paiement),
    FOREIGN KEY (fk_eleve) REFERENCES eleve (id_eleve) ON DELETE CASCADE,
    FOREIGN KEY (fk_matiere) REFERENCES matiere (id_matiere) ON DELETE CASCADE
);
-- Table for documents
CREATE TABLE document (
    id_document INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id_document),
    nom_doc VARCHAR(254),
    taille FLOAT,
    fk_enseignant INT,
    typeDoc VARCHAR(254),
    fk_matiere INT
);
CREATE TABLE enseignant_specialite (
    id_enseignant_specialite Int,
    fk_specialite INT,
    fk_enseignant INT,
    main BOOL,
    PRIMARY KEY (id_enseignant_specialite),
    FOREIGN KEY (fk_enseignant) REFERENCES enseignant (id_enseignant) ON DELETE CASCADE,
    FOREIGN KEY (fk_specialite) REFERENCES matiere (id_matiere) ON DELETE CASCADE
);
-- Table for professor schedule preferences
CREATE TABLE prof_schedule (
    id_prof_schedule INT NOT NULL AUTO_INCREMENT,
    fk_enseignant INT,
    specific_date DATE,  -- Date for specific days
    start_time TIME,
    end_time TIME,
    is_whole_day BOOLEAN,  -- Indicates if the entry represents a whole day
    PRIMARY KEY (id_prof_schedule),
    FOREIGN KEY (fk_enseignant) REFERENCES enseignant (id_enseignant) ON DELETE CASCADE
);
-- Adding constraints to document table
ALTER TABLE document ADD CONSTRAINT FK_Document_Matiere FOREIGN KEY (fk_matiere) REFERENCES matiere (id_matiere) ON DELETE CASCADE;
ALTER TABLE document ADD CONSTRAINT FK_Document_Enseignant FOREIGN KEY (fk_enseignant) REFERENCES enseignant (id_enseignant) ON DELETE CASCADE;

-- Adding constraints to eleve table
ALTER TABLE eleve ADD CONSTRAINT FK_Eleve_Parent FOREIGN KEY (fk_parent) REFERENCES parent (id_parent) ON DELETE CASCADE;
ALTER TABLE eleve ADD CONSTRAINT FK_Eleve_Utilisateur FOREIGN KEY (id_eleve) REFERENCES utilisateur (id_utilisateur) ON DELETE CASCADE;
ALTER TABLE absence DROP COLUMN est_present;
alter table document add column   date_de_creation DATETIME DEFAULT CURRENT_TIMESTAMP;

describe eleve;
alter table enseignant change column date_inscription date_validation DATETIME DEFAULT CURRENT_TIMESTAMP;

