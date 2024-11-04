package entities;

import java.time.LocalDateTime;
import java.util.List;

public class Matiere {
	private int idMatiere;
    private String descriptionMatiere;
    private float prix;
    private LocalDateTime dateDeCreation;
    private Niveau niveau;
    private List<EnseignantSpecialite> EnseignantSpecialite;
	public int getIdMatiere() {
		return idMatiere;
	}
	
	public String getDescriptionMatiere() {
		return descriptionMatiere;
	}
	public void setDescriptionMatiere(String descriptionMatiere) {
		this.descriptionMatiere = descriptionMatiere;
	}
	public List<EnseignantSpecialite> getEnseignantSpecialite() {
		return EnseignantSpecialite;
	}

	public void setEnseignantSpecialite(List<EnseignantSpecialite> enseignantSpecialite) {
		EnseignantSpecialite = enseignantSpecialite;
	}

	public void setIdMatiere(int idMatiere) {
		this.idMatiere = idMatiere;
	}

	public float getPrix() {
		return prix;
	}
	public void setPrix(float prix) {
		this.prix = prix;
	}
	public LocalDateTime getDateDeCreation() {
		return dateDeCreation;
	}
	public void setDateDeCreation(LocalDateTime dateDeCreation) {
		this.dateDeCreation = dateDeCreation;
	}
	public Niveau getNiveau() {
		return niveau;
	}
	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}
    
}
