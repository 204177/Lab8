package it.polito.tdp.metrodeparis.model;

import it.polito.tdp.metrodeparis.db.LetturaConnessioneDAO;
import it.polito.tdp.metrodeparis.db.LetturaFermataDAO;
import it.polito.tdp.metrodeparis.db.LetturaLineaDAO;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;


public class MetroModel {
	
	LetturaFermataDAO fDao = new LetturaFermataDAO();
	LetturaConnessioneDAO cDao = new LetturaConnessioneDAO();
	LetturaLineaDAO lDao = new LetturaLineaDAO();
	
	public WeightedGraph<Fermata, DefaultWeightedEdge> grafo = new SimpleWeightedGraph<Fermata, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	public List<Fermata> fermate = new LinkedList<Fermata>(fDao.getFermate());
	public List<Connessione> connessioni = new LinkedList<Connessione>(cDao.getConnessioni());
	public List<Linea> linee = new LinkedList<Linea>(lDao.getLinee());
	
	public WeightedGraph<Fermata, DefaultWeightedEdge> graph = new DefaultDirectedWeightedGraph<Fermata, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	
	public double tempo=0.0;
	
	public void creaGrafo(){
		
		for(Fermata f : fermate)
			grafo.addVertex(f);
		
		for(Connessione c : connessioni){
			Fermata fA = trovaFermata(c.getIdStazA());
			Fermata fP = trovaFermata(c.getIdStazP());
			double distanza = LatLngTool.distance(new LatLng(fP.getCoordX(), fP.getCoordY()), new LatLng(fA.getCoordX(), fA.getCoordY()), LengthUnit.KILOMETER);
			double weight = distanza/(trovaLinea(c.getIdLinea()));
			Graphs.addEdgeWithVertices(grafo, fP, fA, weight);
		}
		
	}
	
	public List<Fermata> camminoMinimo(String partenza, String arrivo){
		
		Fermata fP = this.trovaFermataPerNome(partenza);
		Fermata fA = this.trovaFermataPerNome(arrivo);
		
		if( fA==null || fP==null)
			return null;
		
		DijkstraShortestPath<Fermata, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(grafo, fP, fA);
		GraphPath<Fermata, DefaultWeightedEdge> path = dijkstra.getPath();
		if(path==null)
			return null;
		tempo = path.getWeight(); //restituisce somma di tutti i pesi per quel grafo
		tempo = tempo*60;
		return Graphs.getPathVertexList(dijkstra.getPath());
	}
	
	public Fermata trovaFermata(int id){
		for(Fermata f : fermate)
			if(f.getId()==id)
				return f;
		return null;
	}
	
	public Fermata trovaFermataPerNome(String nome){
		for(Fermata f : fermate)
			if(f.getNome().compareTo(nome)==0)
				return f;
		return null;
	}
	
	public double trovaLinea(int id){
		for(Linea l : linee)
			if(l.getId()==id)
				return l.getVelocita();
		return 0.0;
		
	}
	
	
	
}
