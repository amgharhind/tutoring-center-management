package dao;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import entities.Absence;
import entities.Classe;
import entities.Document;
import entities.Eleve;
import entities.Enseignant;
import entities.EnseignantSpecialite;
import entities.Groupe;
import entities.Matiere;
import entities.Niveau;
import entities.Parent;
import entities.Salle;
import entities.Seance;
import entities.Utilisateur;
import entities.profSchedule;

public interface IDao {
	 Utilisateur authentifier(String email, String password);
	 int saveAbsence(Absence absence);
	 List<Absence> getAbsencesByEleveId(int eleveId);
	 Eleve getEleveById(int id);
	 int insertDocument(Document document, int idMatiere, int idProf) ;
	 List<Document> getDocumentsByTeacherId(int teacherId);
	 //this is to get the all docs to showw them to prof 
	 List<Document> getDocumentsByMatiereIdAndProfId(int matiereId,int ProfId );
	 //this is to get the all docs to showw them to Student by matieres 
	 List<Document> getCoursDocumentsByMatiereIdAndStudent(int matiereId,int studentId );
	 List<Document> getDevoirsDocumentsByMatiereIdAndStudent(int matiereId,int studentId );
	 List<String> getDevoirDocumentNamesByMatiereId(int matiereId);
	 List<String> getCoursDocumentNamesByMatiereId(int matiereId);
	 Salle getSalleById(int salleId);
	 List<Salle> getAllSalles();
	 int addSalle(Salle salle);
	 //this function to make salle not available , or available depending in the current availability
	 int notAvailableSalle(int salleId,boolean newAvailability);
	 List<Matiere> getAllMatieresOfEleve(int idEleve);
	 List<Matiere> getAllMatieresOfProf(int idProf);
	 List<Matiere> getAllMatieres();
	 List<Matiere> getAllMatieresExcept(int idMatiere);
	 int addSpecialitiesToProf(Map<Matiere,List<Matiere>> specialities,int idProf);
	 int addEleve(Map<String,Object> attributes);
	 int addProf(Map<String,Object> attributes);
	 int deleteMatiere(int matiereId);
	 boolean validerEleve(int eleveId);
	 Map<Integer, String> validerProf(int idProf);
	 List<Enseignant> getAllProfs();
	 List<Eleve> getAllEleves();
	 int deleteProf(int profId);
	 int deleteEleve(int eleveId);
     int payMatiere(int matiereId, int eleveId);
     List<Eleve> getAllElevesByCriteria(Integer niveau, Integer matiere, Integer group);
     //int addSeance(Seance seance,Groupe group,Salle salle );
     int updateSeance(int idSession);
     int deleteSeance(int idSession);
     int closeSeance(int idSession,String reason,int idProf);
     //this for the prof
     int closeDayOrHours(int  prof, profSchedule profSchedule);
     //for prof 
     int closeDayOrHours(int  prof, String day,String startTime,String endTime,Boolean isWholeDay);
     //this for agent
     int closeDayOrHours(String day,String startTime,String endTime);

     List<Seance> getAllSeanceByCurrentWeek();
     List<Seance> getAllSeanceByDay(String day);
     List<Seance> getAllSeancesByEleve(int idEleve);
     int updateProfile(Map<String,Object> values);
	 Parent getParentByEleve(int idEleve);
     Utilisateur getUtilisateurById(int idUtilisateur);	 
	 Parent getParentById(int idParent);
	 List<Eleve> getElevesByIdParent(int idParent);
	 int isPayThisMonth(int idEleve , int idMatiere);
	 int isExistEmail(String email);
	 //this is return day name and the seances  of the current week 
	 Map<String ,List< Seance>> getAllSeanceByCurrentWeekMap() ;
	 
	 Map<String ,List< Seance>> getAllSeanceByCurrentWeekMapByIdProf(int idProf) ;
	 Map<String ,List< Seance>> getAllSeanceByCurrentWeekMapByIdStudent(int idStudent) ;

     Seance getSeanceById(int idSeance);	 
     Salle getSalleByIdSeance(int idSeance);
     Groupe getGroupeByIdSeance(int idSeance );
     Groupe getGroupeById(int idGroupe);
     Classe getClasseById(int idClasse);
     Niveau getNiveauById(int idNiveau);
     List<Niveau> getAllNiveaus();
     int saveNiveau(Niveau niveau);
     int getMainCentreId();
     Matiere getMatiereById(int idMatiere);
     public Map<Integer,Object> addSeance(Seance seance, Groupe group, Salle salle);
     public boolean isSalleAvailable(Connection connection, Salle salle, LocalDateTime dateSeance,
				LocalTime heureDebut, LocalTime heureFin) ;
    public boolean  isGroupAvailable( Connection connection, Groupe group,  LocalDateTime dateSeance, LocalTime heureDebut, LocalTime heureFin);
    public boolean areStudentsAvailable(Connection connection, Groupe group, LocalDateTime dateSeance, LocalTime heureDebut, LocalTime heureFin); 
    public  int getEnseignantIdByGroupe(Connection connection, int idGroupe);
    public  List<EnseignantSpecialite> getEnseignantSpecialitesByEnseignantId(int idEnseignant) ;
    Enseignant getEnseignantById(int idEnseignant);
    
    int createOrGenerateRandomGroupFromLevel(int levelId,Connection connection,int matiereId);
	List<Matiere> getMatieresByNiveau(String selectedNiveau);
	List<Matiere> getMatieresByNiveauId(int selectedNiveau);
	List<Groupe> getAllGroupesOfProf(int idProf);
    List<Groupe> getAllGroupes();
    //this for the dashboard
    Map<String , Long> inscriptionsOfCurrentYearByMonth();
    Double incomeOfCurrentYear();
    // by type of user
    Map<String, Long> inscriptionsOfcurrentYear();
    //levelName , numberOfInscription
    Map<String, Long>  inscriptionsByLevel();
    Map<String, Object>  incomeByLevel();
    Long numberOfValidateStudent();
    Long numberOfValidateProfs();
    
    Long numberOfDocuments();
    List<Utilisateur> getAllElevesByIdGroup(int idGroup);
    List<Seance> getAllSeancesByGroupe(int idGroup);
    boolean isAbsent(int idStudent,int idSeance);
    Matiere getMatiereByGroupeId(int idGroup);
    Map<Integer,Matiere > getAllMatieresAndGroupsIdOfEleve(int idEleve);
    
    List<Seance> getAllSeancesDepuisInscriptionOfAstudent(int idGroup, int idStudent);
    Utilisateur getUtilisateurByEmait(String email);
    Utilisateur updatePassword(int userId, String newPassword);
    List<Matiere> getAllMatieresNotAssignedToAstudent(int studentId);
}

