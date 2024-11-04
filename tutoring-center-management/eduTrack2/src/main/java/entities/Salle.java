package entities;

public class Salle {
	private int idSalle;
    private int capacite;
    private boolean disponible;
    private boolean occuper;
    private Centre centre;
    
	public Salle() {
	}

	public int getIdSalle() {
		return idSalle;
	}
	
	public int getCapacite() {
		return capacite;
	}
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	public boolean isDisponible() {
		return disponible;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	public boolean isOccuper() {
		return occuper;
	}
	public void setOccuper(boolean occuper) {
		this.occuper = occuper;
	}
	public Centre getCentre() {
		return centre;
	}
	public void setCentre(Centre centre) {
		this.centre = centre;
	}

	public void setIdSalle(int idSalle) {
		this.idSalle = idSalle;
	}

	
    
}
