package entities;

public class EnseignantGroupeMatiere {
	    private int idEGM;
	    private Enseignant enseignant; 
	    private Groupe groupe; 
	    private Matiere matiere;
		public Enseignant getEnseignant() {
			return enseignant;
		}
		public void setEnseignant(Enseignant enseignant) {
			this.enseignant = enseignant;
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
		public EnseignantGroupeMatiere() {
		}
		public int getIdEGM() {
			return idEGM;
		}
		public void setIdEGM(int idEGM) {
			this.idEGM = idEGM;
		}
	    
}
