package entities;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Seance {
	private int idSeance;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private boolean annule;
    private boolean miseAJour;
    private LocalDateTime dateSeance;
    private Salle salle; 
    private Groupe groupe;
    private String reason;
    public Seance() {}
	public int getIdSeance() {
		return idSeance;
	}
	
	public void setIdSeance(int idSeance) {
		this.idSeance = idSeance;
	}
	public LocalTime getHeureDebut() {
		return heureDebut;
	}
	public void setHeureDebut(LocalTime heureDebut) {
		this.heureDebut = heureDebut;
	}
	public LocalTime getHeureFin() {
		return heureFin;
	}
	public void setHeureFin(LocalTime heureFin) {
		this.heureFin = heureFin;
	}
	public boolean isAnnule() {
		return annule;
	}
	public void setAnnule(boolean annule) {
		this.annule = annule;
	}
	public boolean isMiseAJour() {
		return miseAJour;
	}
	public void setMiseAJour(boolean miseAJour) {
		this.miseAJour = miseAJour;
	}
	public LocalDateTime getDateSeance() {
		return dateSeance;
	}
	public void setDateSeance(LocalDateTime dateSeance) {
		this.dateSeance = dateSeance;
	}
	public Salle getSalle() {
		return salle;
	}
	public void setSalle(Salle salle) {
		this.salle = salle;
	}
	public Groupe getGroupe() {
		return groupe;
	}
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	} 
    
}
