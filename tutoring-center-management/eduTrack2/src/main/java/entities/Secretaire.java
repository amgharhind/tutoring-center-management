package entities;

public class Secretaire  extends Utilisateur{
	 private int idSecretaire;
	    private Centre centre;
	    
	    
		public Secretaire() { super();}

		public int getIdSecretaire() {
			return idSecretaire;
		}
		
		
	
		public Centre getCentre() {
			return centre;
		}
		public void setCentre(Centre centre) {
			this.centre = centre;
		}
	    
}
