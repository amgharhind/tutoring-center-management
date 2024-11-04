package entities;

import java.util.List;

public class Parent extends Utilisateur{
	    private int idParent;
	    private List<Eleve> enfants;

	public Parent() {
		super();

	}
	
	public int getIdParent() {
		return idParent;
	}
	public void setIdParent(int idParent) {
		this.idParent = idParent;
	}

	public List<Eleve> getEnfants() {
		return enfants;
	}

	public void setEnfants(List<Eleve> enfants) {
		this.enfants = enfants;
	}
	

	
	 

}
