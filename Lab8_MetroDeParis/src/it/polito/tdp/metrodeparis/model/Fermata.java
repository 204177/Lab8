package it.polito.tdp.metrodeparis.model;

public class Fermata {

	private int id;
	private String nome;
	private double coordX;
	private double coordY;
	
	public Fermata(int id, String nome, double coordX, double coordY) {
		this.id = id;
		this.nome = nome;
		this.coordX = coordX;
		this.coordY = coordY;
	}

	public String getNome() {
		return nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCoordX() {
		return coordX;
	}

	public void setCoordX(double coordX) {
		this.coordX = coordX;
	}

	public double getCoordY() {
		return coordY;
	}

	public void setCoordY(double coordY) {
		this.coordY = coordY;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	/*
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fermata other = (Fermata) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public boolean equals(Fermata arg0) {
		if(this.id==arg0.getId())
			return true;
		return false;
	}*/
	
	
	
}
