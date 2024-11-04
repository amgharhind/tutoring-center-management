package entities;

import java.time.LocalDateTime;

public class Document {
	private int idDocument;
    private String nomDoc;
    private float taille;
    private Enseignant enseignant; 
    private String typeDoc;
    private Matiere matiere;
    private LocalDateTime date_de_creation;
	public int getIdDocument() {
		return idDocument;
	}
	
	public String getNomDoc() {
		return nomDoc;
	}
	public void setNomDoc(String nomDoc) {
		this.nomDoc = nomDoc;
	}
	public void setIdDocument(int idDocument) {
		this.idDocument = idDocument;
	}

	public float getTaille() {
		return taille;
	}
	public void setTaille(float taille) {
		this.taille = taille;
	}
	public Enseignant getEnseignant() {
		return enseignant;
	}
	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}
	public String getTypeDoc() {
		return typeDoc;
	}
	public void setTypeDoc(String typeDoc) {
		this.typeDoc = typeDoc;
	}
	public Matiere getMatiere() {
		return matiere;
	}
	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public Document() {
	}

	public void setDateDeCreation(LocalDateTime localDateTime) {
		// TODO Auto-generated method stub
		
	}

	public LocalDateTime getDate_de_creation() {
		return date_de_creation;
	}

	public void setDate_de_creation(LocalDateTime date_de_creation) {
		this.date_de_creation = date_de_creation;
	}
    
}
