package it.polito.tdp.crimes.model;

public class Adiacenza {

	private String v1;
	private String v2;
	private double peso;
	public Adiacenza(String v1, String v2, double peso) {
		super();
		this.v1 = v1;
		this.v2 = v2;
		this.peso = peso;
	}
	public String getV1() {
		return v1;
	}
	public void setV1(String v1) {
		this.v1 = v1;
	}
	public String getV2() {
		return v2;
	}
	public void setV2(String v2) {
		this.v2 = v2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return  v1 + " - " + v2 + "  " + peso ;
	}
	
	
}
