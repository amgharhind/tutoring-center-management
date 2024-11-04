package entities;

public class Absence {
	private int idAbsence;
    private Seance seance; 
    private Eleve eleve; 
    private String justification;
	public int getIdAbsence() {
		return idAbsence;
	}
	
	public Seance getSeance() {
		return seance;
	}
	public void setSeance(Seance seance) {
		this.seance = seance;
	}
	public Eleve getEleve() {
		return eleve;
	}
	public void setIdAbsence(int idAbsence) {
		this.idAbsence = idAbsence;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	

	public Absence() {
	}
	
    
}
