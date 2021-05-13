package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private SimpleWeightedGraph<String,DefaultWeightedEdge> grafo;
	private EventsDao dao;
	private List<String> percorsoMigliore;
	
	public Model() {
		dao = new EventsDao();
	}
	public List<String> getCategorie(){
		return dao.getCategorie();
	}
	public void creaGrafo(String categoria,int mese) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getVertici(categoria,mese));
		
		for(Adiacenza a : dao.getAdiacenze(categoria, mese)) {
			if(this.grafo.getEdge(a.getV1(), a.getV2()) == null) {
				Graphs.addEdgeWithVertices(grafo, a.getV1(), 
						a.getV2(), a.getPeso());
			}
		}
		System.out.println("# Vertici : "+grafo.vertexSet().size());
		System.out.println("# Archi : "+grafo.edgeSet().size());
		
	}
	
	public List<Adiacenza> getArchi() {
		//calcolo prima il peso medio degli archi e poi filtro con quelli che hanno peso maggiore a quello medio
		double pesoMedio = 0.0;
		
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			pesoMedio += this.grafo.getEdgeWeight(e);
		}
		pesoMedio = pesoMedio/this.grafo.edgeSet().size();
		
		List<Adiacenza>result = new ArrayList<>();
		
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e) > pesoMedio) {
				result.add(new Adiacenza(this.grafo.getEdgeSource(e),this.grafo.getEdgeTarget(e),this.grafo.getEdgeWeight(e)));
			}
			
		}
		return result;
	}
	//DEVO FARE UNA RICORSIONE PER TROVARE IL PERCORSO
	public List<String> trovaPercorso(String sorgente, String destinazione) {
		this.percorsoMigliore = new LinkedList<>();
		List<String> parziale = new LinkedList<>();
		parziale.add(sorgente);
		cerca(destinazione,parziale);
		return this.percorsoMigliore;
	}
	private void cerca(String destinazione , List<String> parziale ) {
		//CASO TERMINALE
		if(parziale.get(parziale.size()-1).equals(destinazione)) {
			if(parziale.size()>this.percorsoMigliore.size()) {
				this.percorsoMigliore=new LinkedList<>(parziale);
			}
			return;
		}
		//...ALTRIMENTI ,SCORRO I VICINI DELL ULTIMO INSERITO E PROVO AD AGGIUNGERLI UNO AD UNO
		for(String vicino : Graphs.neighborListOf(grafo,parziale.get(parziale.size()-1))) { // metodo per vedere quali sono i vicini di quel vertice
			if(!parziale.contains(vicino)) {
				parziale.add(vicino);
				cerca(destinazione,parziale);
				parziale.remove(parziale.size()-1);
			}
		}
	}
}
