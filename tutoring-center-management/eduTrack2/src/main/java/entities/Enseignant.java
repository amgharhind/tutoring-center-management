package entities;

import java.util.Date;
import java.util.List;

public class Enseignant extends Utilisateur {
	private int idEnseignant;
    private Centre centre;
    private Date dateValidation;
    private boolean avecExperience;
    private List<EnseignantSpecialite> EnseignantSpecialite;
    private List<EnseignantGroupeMatiere> matieresEnseignees;
    
	public Date getDateValidation() {
		return dateValidation;
	}

	public void setDateValidation(Date dateValidation) {
		this.dateValidation = dateValidation;
	}

	public Enseignant() {
		super();
	}

	public int getIdEnseignant() {
		return idEnseignant;
	}
	
	public List<EnseignantSpecialite> getEnseignantSpecialite() {
		return EnseignantSpecialite;
	}

	public void setEnseignantSpecialite(List<EnseignantSpecialite> enseignantSpecialite) {
		EnseignantSpecialite = enseignantSpecialite;
	}

	public List<EnseignantGroupeMatiere> getMatieresEnseignees() {
		return matieresEnseignees;
	}

	public void setMatieresEnseignees(List<EnseignantGroupeMatiere> matieresEnseignees) {
		this.matieresEnseignees = matieresEnseignees;
	}

	public void setIdEnseignant(int idEnseignant) {
		this.idEnseignant = idEnseignant;
	}

	public Centre getCentre() {
		return centre;
	}
	public void setCentre(Centre centre) {
		this.centre = centre;
	}
	
	

	public boolean isAvecExperience() {
		return avecExperience;
	}

	public void setAvecExperience(boolean avecExperience) {
		this.avecExperience = avecExperience;
	}

}
