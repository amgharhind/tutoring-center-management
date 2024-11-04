package entities;

public class Affectation {
	private int idAffectation;
    private Eleve eleve; 
    private Groupe groupe; 
    private Matiere matiere;
    
    
	public Affectation() {}

	public int getIdAffectation() {
		return idAffectation;
	}
	
	public Eleve getEleve() {
		return eleve;
	}
	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}
	public Groupe getGroupe() {
		return groupe;
	}
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
	public Matiere getMatiere() {
		return matiere;
	}
	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	} 
    

}
