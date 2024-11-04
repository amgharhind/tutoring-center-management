package entities;

import java.util.List;

public class Centre {
	private int idCentre;
    private String nomCentre;
    private String localisation;
    private String contact;
    private List<Niveau> niveaux;
    private boolean mainCentre;

	public String getNomCentre() {
		return nomCentre;
	}
	public void setNomCentre(String nomCentre) {
		this.nomCentre = nomCentre;
	}
	public String getLocalisation() {
		return localisation;
	}
	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public int getIdCentre() {
		return idCentre;
	}
	public Centre() {
	}
	public List<Niveau> getNiveaux() {
		return niveaux;
	}
	public void setNiveaux(List<Niveau> niveaux) {
		this.niveaux = niveaux;
	}
	public boolean isMainCentre() {
		return mainCentre;
	}
	public void setMainCentre(boolean mainCentre) {
		this.mainCentre = mainCentre;
	}
    
}
