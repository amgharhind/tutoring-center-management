package entities;

import java.util.Date;
import java.util.List;

public class Niveau {
	 private int idNiveau;
	    private String descriptionNiveau;
	    private Date dateDeCreation;
	    private Centre centre;
	    private List<Classe> classes;

		public int getIdNiveau() {
			return idNiveau;
		}
		
		public void setIdNiveau(int idNiveau) {
			this.idNiveau = idNiveau;
		}

		public String getDescriptionNiveau() {
			return descriptionNiveau;
		}
		

		public Centre getCentre() {
			return centre;
		}

		public void setCentre(Centre centre) {
			this.centre = centre;
		}

		public Niveau() {
		}

		public void setDescriptionNiveau(String descriptionNiveau) {
			this.descriptionNiveau = descriptionNiveau;
		}
		public Date getDateDeCreation() {
			return dateDeCreation;
		}
		public void setDateDeCreation(Date dateDeCreation) {
			this.dateDeCreation = dateDeCreation;
		}

		public List<Classe> getClasses() {
			return classes;
		}

		public void setClasses(List<Classe> classes) {
			this.classes = classes;
		}
	
}
