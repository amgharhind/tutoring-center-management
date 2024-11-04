package entities;

import java.util.Date;

public class Groupe {
	private int idGroupe;
    private Classe classe; 
    private int capacite;
    private int nbrEleve;
    private String descriptionGroupe;
    private Date dateDeCreation;
    
	public Groupe() {
	}

	public int getIdGroupe() {
		return idGroupe;
	}
	
	public Classe getClasse() {
		return classe;
	}
	public void setIdGroupe(int idGroupe) {
		this.idGroupe = idGroupe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}
	public int getCapacite() {
		return capacite;
	}
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	public int getNbrEleve() {
		return nbrEleve;
	}
	public void setNbrEleve(int nbrEleve) {
		this.nbrEleve = nbrEleve;
	}
	public String getDescriptionGroupe() {
		return descriptionGroupe;
	}
	public void setDescriptionGroupe(String descriptionGroupe) {
		this.descriptionGroupe = descriptionGroupe;
	}
	public Date getDateDeCreation() {
		return dateDeCreation;
	}
	public void setDateDeCreation(Date dateDeCreation) {
		this.dateDeCreation = dateDeCreation;
	}
    
    
}
