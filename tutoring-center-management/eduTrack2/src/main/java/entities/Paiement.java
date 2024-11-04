package entities;

import java.time.LocalDateTime;

public class Paiement {
	private int idPaiement;
    private  Matiere matiere; 
    private Eleve eleve; 
    private LocalDateTime datePaiement;
	public int getIdPaiement() {
		return idPaiement;
	}
	
	public Matiere getMatiere() {
		return matiere;
	}
	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}
	public Eleve getEleve() {
		return eleve;
	}
	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}
	public LocalDateTime getDatePaiement() {
		return datePaiement;
	}
	public void setDatePaiement(LocalDateTime datePaiement) {
		this.datePaiement = datePaiement;
	}

	public Paiement() {}
    
    

}
