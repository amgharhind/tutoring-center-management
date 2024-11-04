package entities;

public class Classe {
	private int idClasse;
    private String descriptionClasse;
    private Niveau niveau;
    private boolean mainClasse;
    
	public int getIdClasse() {
		return idClasse;
	}
	
	public String getDescriptionClasse() {
		return descriptionClasse;
	}
	public void setDescriptionClasse(String descriptionClasse) {
		this.descriptionClasse = descriptionClasse;
	}
	public void setIdClasse(int idClasse) {
		this.idClasse = idClasse;
	}

	public Niveau getNiveau() {
		return niveau;
	}
	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public Classe() {
	}



	public boolean isMainClasse() {
		return mainClasse;
	}

	public void setMainClasse(boolean mainClasse) {
		this.mainClasse = mainClasse;
	} 
    
}
