package it.polito.tdp.metrodeparis.model;

public class RichFermata {

	private Fermata fermata;
	private int idLinea;
	
	public RichFermata(Fermata fermata, int idLinea) {
		this.fermata = fermata;
		this.idLinea = idLinea;
	}

	public int getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}

	public Fermata getFermata() {
		return fermata;
	}

	public void setFermata(Fermata fermata) {
		this.fermata = fermata;
	}
	
	
	
}
