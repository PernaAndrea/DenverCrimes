package it.polito.tdp.crimes.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		m.creaGrafo("aggravated-assault", 4);
		System.out.println(m.getArchi());
	}

}
