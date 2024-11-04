package entities;

import java.time.LocalDateTime;
import java.util.List;

public class Eleve extends Utilisateur {
	    private int idEleve;
	    private LocalDateTime dateValidation;
	    private Parent parent;
	    private List<Affectation> affectation;
	    public Eleve() {super(); }
		public int getIdEleve() {
			return idEleve;
		}
		
		
		
		public void setIdEleve(int idEleve) {
			this.idEleve = idEleve;
		}
		public LocalDateTime getDateValidation() {
			return dateValidation;
		}
		public void setDateValidation(LocalDateTime dateValidation) {
			this.dateValidation = dateValidation;
		}
		public Parent getParent() {
			return parent;
		}
		public void setParent(Parent parent) {
			this.parent = parent;
		}
		public List<Affectation> getAffectation() {
			return affectation;
		}
		public void setAffectation(List<Affectation> affectation) {
			this.affectation = affectation;
		}
	    
}
