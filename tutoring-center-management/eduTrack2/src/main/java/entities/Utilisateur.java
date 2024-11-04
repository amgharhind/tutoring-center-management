package entities;

import java.util.Date;

public class Utilisateur {
	 private int idUtilisateur;
	    private String nom;
	    private String prenom;
	    private String email;
	    private String password;
	    private int age;
	    private String adresse;
	    private String phone;
	    private String sexe;
	    private String rolee;
	    private Date dateCreation;
	    private String typeUtil;
	    public Utilisateur() {
		}
	    
		public int getIdUtilisateur() {
			return idUtilisateur;
		}
	
		

		public String getNom() {
			return nom;
		}
		public void setNom(String nom) {
			this.nom = nom;
		}
		public String getPrenom() {
			return prenom;
		}
		public void setPrenom(String prenom) {
			this.prenom = prenom;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getAdresse() {
			return adresse;
		}
		public void setAdresse(String adresse) {
			this.adresse = adresse;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getSexe() {
			return sexe;
		}
		public void setSexe(String sexe) {
			this.sexe = sexe;
		}
		public String getRolee() {
			return rolee;
		}
		public void setRolee(String rolee) {
			this.rolee = rolee;
		}
		public Date getDateCreation() {
			return dateCreation;
		}
		public void setDateCreation(Date dateCreation) {
			this.dateCreation = dateCreation;
		}

		public String getTypeUtil() {
			return typeUtil;
		}

		public void setTypeUtil(String typeUtil) {
			this.typeUtil = typeUtil;
		}

		public void setIdUtilisateur(int idUtilisateur) {
			this.idUtilisateur = idUtilisateur;
		}

		

		
		
}
