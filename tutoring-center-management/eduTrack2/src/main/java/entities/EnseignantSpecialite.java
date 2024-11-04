package entities;

public class EnseignantSpecialite {
	    private int idEnseignantSpecialite;
	    private Matiere specialite; 
	    private Enseignant enseignant; 
	    private boolean isMain;
	    
	    
		public EnseignantSpecialite() {
		}
		public Matiere getSpecialite() {
			return specialite;
		}
		public void setSpecialite(Matiere specialite) {
			this.specialite = specialite;
		}
		public Enseignant getEnseignant() {
			return enseignant;
		}
		public void setEnseignant(Enseignant enseignant) {
			this.enseignant = enseignant;
		}
		public boolean isMain() {
			return isMain;
		}
		public void setMain(boolean isMain) {
			this.isMain = isMain;
		}
		public int getIdEnseignantSpecialite() {
			return idEnseignantSpecialite;
		}
		public void setIdEnseignantSpecialite(int idEnseignantSpecialite) {
			this.idEnseignantSpecialite = idEnseignantSpecialite;
		}
	    
}
