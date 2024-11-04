use dbgestioncentrefinal;
describe eleve;
describe utilisateur;
select d.* from document d inner join enseignant_groupe_matiere e where e.fk_enseignant =1 and e.fk_groupe=2 and e.fk_matiere = 1;
select d.* from document d inner join  affectation a  where  a.fk_matiere=classed.fk_matiere and d.typeDoc = 'devoir' and  a.fk_eleve = 1 and d.fk_matiere = 1  order by d.date_de_creation;
alter table prof_schedule add column reason varchar(255);
show create table affectation;
use dbgestioncentrefinal;
select e.* from seance e inner join affectation a on e.fk_groupe = a.fk_groupe;
select * from groupe;
describe affectation;
select * from affectation;
show create table affectation;

DELIMITER //
CREATE TRIGGER update_nbr_eleve_after_insert
AFTER INSERT ON affectation
FOR EACH ROW
BEGIN
    DECLARE groupId INT;

    -- Retrieve the group ID for the affected record
    SELECT fk_groupe INTO groupId FROM affectation WHERE id_affectation = NEW.id_affectation;

    -- Increment nbr_eleve in the groupe table for the corresponding group
    UPDATE groupe SET nbr_eleve = nbr_eleve + 1 WHERE id_groupe = groupId;
END;
//
DELIMITER ;

SHOW CREATE TRIGGER update_nbr_eleve_after_insert;
select * from niveau;
insert into niveau (descriptionNiveau) values ('math');
select * from centre;
insert into centre (nomCentre,localisation , contact,main_centre) values ('eduTrack','casa','0655555555',true);
select * from matiere;
select * from utilisateur;
select * from eleve;



select * from affectation;
describe utilisateur;
select * from paiement;
select * from groupe;
SET SQL_SAFE_UPDATES = 1;
select * from classe;
delete from groupe;
select * from niveau;
delete  from utilisateur ;
alter table utilisateur MODIFY COLUMN sexe varchar(25);
select * from eleve;
describe eleve ;
alter table enseignant modify column avec_experience  Boolean;
alter table enseignant_specialite  modify column id_enseignant_specialite INT AUTO_INCREMENT;
describe enseignant;
select * from enseignant;
select * from enseignant_specialite;

select  * from  secretaire;
select * from utilisateur ;
select * from salle;
select *  from eleve;

select * from matiere;
select * from affectation;
select * from parent;
select * from  enseignant_groupe_matiere;
ALTER TABLE enseignant_groupe_matiere MODIFY COLUMN  id_e_g_m int AUTO_INCREMENT;

select * from groupe;
select * from enseignant;
UPDATE `dbgestioncentrefinal`.`enseignant` SET `date_validation` = NULL WHERE (`id_enseignant` = '30');
SELECT a.fk_groupe,egm.* FROM affectation a INNER JOIN groupe g ON a.fk_groupe = g.id_groupe left JOIN enseignant_groupe_matiere egm ON g.id_groupe = egm.fk_groupe 
               AND egm.fk_matiere = a.fk_matiere where egm.id_e_g_m is null and  a.fk_matiere =3;
select * from classe;
SELECT a.*,egm.* FROM affectation a INNER JOIN groupe g ON a.fk_groupe = g.id_groupe left JOIN enseignant_groupe_matiere egm ON g.id_groupe = egm.fk_groupe 
               AND egm.fk_matiere = a.fk_matiere where    a.fk_matiere=2;
               
select * from salle;
select * from seance;
select * from prof_schedule;
select  * from utilisateur;
select count(*), Month(dateCreation) from utilisateur group by  Month(dateCreation);
select count(*) from utilisateur  where type_util="agent" or type_util="directeur" ;
select count(*), Month(dateCreation) from utilisateur  where type_util<>"agent" and type_util<>"directeur" group by  Month(dateCreation);
select * from parent;
select distinct(type_util) from utilisateur;

select sum(m.prix)from paiement p inner join matiere m on m.id_matiere = p.fk_matiere where year(p.date_paiment) =2024;
select sum(m.prix)from paiement p inner join matiere m on m.id_matiere = p.fk_matiere ;

select * from matiere;
select count(*),  monthname(dateCreation) from utilisateur  where type_util<>"agent" and type_util<>"directeur" and Year(dateCreation)=2024 group by  monthname(dateCreation);
select count(*), type_util from utilisateur  where  Year(dateCreation)=2024 group by   type_util;
select * from niveau;
select * from affectation;
select * from matiere;
select * from paiement;
select * from utilisateur where type_util = "student" ;
select sum(m.prix) , n.descriptionNiveau from paiement p inner join matiere m inner join niveau  n on m.id_matiere = p.fk_matiere  and n.id_niveau = m.fk_niveau group by descriptionNiveau;
select COUNT(DISTINCT a.fk_eleve) AS number_of_inscriptions , n.descriptionNiveau  from affectation a inner join  niveau n inner join matiere m inner join eleve e  on n.id_niveau = m.fk_niveau and m.id_matiere=a.fk_matiere and a.fk_eleve= e.id_eleve where e.date_validation is not Null group by  n.descriptionNiveau ;
select * from utilisateur u inner join eleve e on e.id_eleve= u.id_utilisateur where type_util = "student" ;
select * from utilisateur u inner join eleve e  on e.id_eleve= u.id_utilisateur where type_util = "student" ;
select *from document ;

select count(*) from eleve where date_validation is not Null;
select count(*) from enseignant where date_validation is not Null;
select count(*) from enseignant ;

select * from seance s inner join enseignant_groupe_matiere egm  on s.fk_groupe = egm.fk_groupe where egm.fk_enseignant=30;
select * from enseignant_groupe_matiere;
select * from utilisateur where id_utilisateur =30;
select * from seance;
select * from document;
select * from prof_schedule;
select * from prof_schedule where fk_enseignant=30;
select * from enseignant_groupe_matiere;
select * from enseignant_groupe_matiere  where fk_enseignant=30;
select * from absence;
SHOW CREATE TABLE absence;
delete from absence where absence.id_absence =7;
select * from absence where fk_seance = 1 and fk_eleve=2;
select * from seance s inner join affectation a on a.fk_groupe = s.fk_groupe;
select * from seance;
select distinct(fk_matiere) from affectation where fk_groupe = 6;
select  * from utilisateur u inner join eleve e on e.id_eleve = u.id_utilisateur;
select * from utilisateur where id_utilisateur=24;
select * from affectation;
select * from utilisateur where type_util like "s%";
select * from eleve;
select * from utilisateur ;
select count(*) from document;
select * from matiere m  left join affectation a on a.fk_matiere = m.id_matiere where a.fk_eleve = 23;
select * from matiere;
select * from affectation;
select * from matiere m where   m.fk_niveau= (select distinct(n.id_niveau) from niveau n inner join matiere  m inner join affectation a on m.fk_niveau=n.id_niveau and a.fk_matiere= m.id_matiere where a.fk_eleve=15) and  m.id_matiere not in ( select a.fk_matiere from affectation a inner join matiere m on m.id_matiere = a.fk_matiere  where a.fk_eleve=15);
select * from matiere;
select distinct(n.id_niveau) from niveau n inner join matiere  m inner join affectation a on m.fk_niveau=n.id_niveau and a.fk_matiere= m.id_matiere where a.fk_eleve=23;
select * from seance;
select * from utilisateur;
SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS  where  TABLE_NAME = 'utilisateur' AND COLUMN_NAME NOT IN (SELECT COLUMN_NAME FROM utilisateur WHERE COLUMN_NAME IS NOT NULL AND COLUMN_NAME is not null AND COLUMN_NAME != '0');
select * from utilisateur where email = 'admin@gmail.com' and passwordd = 'admin';
select * from matiere;
select * from niveau;
select * from paiement;
select * from absence;