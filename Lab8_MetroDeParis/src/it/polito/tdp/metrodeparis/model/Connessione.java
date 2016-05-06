package it.polito.tdp.metrodeparis.model;

public class Connessione {
	
	private int id;
	private int idLinea;
	private int idStazP;
	private int idStazA;
	
	public Connessione(int id, int idLinea, int idStazP, int idStazA) {
		this.id = id;
		this.idLinea = idLinea;
		this.idStazP = idStazP;
		this.idStazA = idStazA;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}

	public int getIdStazP() {
		return idStazP;
	}

	public void setIdStazP(int idStazP) {
		this.idStazP = idStazP;
	}

	public int getIdStazA() {
		return idStazA;
	}

	public void setIdStazA(int idStazA) {
		this.idStazA = idStazA;
	}
	
	
}
