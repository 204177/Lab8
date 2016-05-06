package it.polito.tdp.metrodeparis.model;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.db.LetturaConnessioneDAO;
import it.polito.tdp.metrodeparis.db.LetturaFermataDAO;
import it.polito.tdp.metrodeparis.db.LetturaLineaDAO;

public class MetroModel2 {

	LetturaFermataDAO fDao = new LetturaFermataDAO();
	LetturaConnessioneDAO cDao = new LetturaConnessioneDAO();
	LetturaLineaDAO lDao = new LetturaLineaDAO();

	public List<Fermata> fermate = new LinkedList<Fermata>(fDao.getFermate());
	public List<RichFermata> rFermate = new LinkedList<RichFermata>();
	public List<Connessione> connessioni = new LinkedList<Connessione>(cDao.getConnessioni());
	public List<Linea> linee = new LinkedList<Linea>(lDao.getLinee());

	public WeightedGraph<RichFermata, DefaultWeightedEdge> grafo = new DefaultDirectedWeightedGraph<RichFermata, DefaultWeightedEdge>(
			DefaultWeightedEdge.class);

	public double tempo = 0.0;

	public void creaGrafo() {

		for (Connessione c : connessioni)
			{
				Fermata f = this.trovaFermata(c.getIdStazP());
				RichFermata rf = new RichFermata(f, c.getIdLinea());
				rFermate.add(rf);
				grafo.addVertex(rf);
			}

		for (Connessione c : connessioni) {
			RichFermata fA = trovaRFermata(c.getIdStazA());
			RichFermata fP = trovaRFermata(c.getIdStazP());
			double distanza = LatLngTool.distance(new LatLng(fP.getFermata().getCoordX(), fP.getFermata().getCoordY()), new LatLng(fA.getFermata().getCoordX(), fA.getFermata().getCoordY()), LengthUnit.KILOMETER);
			double weight = distanza / (trovaLinea(c.getIdLinea()));
			Graphs.addEdgeWithVertices(grafo, fP, fA, weight);
		}

		for (RichFermata rf1 : rFermate) {
			for (RichFermata rf2 : rFermate) {
				if (rf1.getFermata().getId() == rf2.getFermata().getId() && rf1.getIdLinea()!=rf2.getIdLinea()) {
					Graphs.addEdgeWithVertices(grafo, rf1, rf2, this.intervalloPerLinea(rf2.getIdLinea()));
					Graphs.addEdgeWithVertices(grafo, rf2, rf1, this.intervalloPerLinea(rf1.getIdLinea()));
				}
			}
		}
	}

	public List<RichFermata> camminoMinimo(String partenza, String arrivo) {

		RichFermata fP = this.trovaFermataPerNome(partenza);
		RichFermata fA = this.trovaFermataPerNome(arrivo);

		if (fA == null || fP == null)
			return null;

		DijkstraShortestPath<RichFermata, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<RichFermata, DefaultWeightedEdge>(
				grafo, fP, fA);
		GraphPath<RichFermata, DefaultWeightedEdge> path = dijkstra.getPath();
		if (path == null)
			return null;
		tempo = path.getWeight(); // restituisce somma di tutti i pesi per quel
									// grafo
		tempo = tempo * 60;
		return Graphs.getPathVertexList(dijkstra.getPath());
	}

	public RichFermata trovaRFermata(int id) {
		for (RichFermata f : rFermate)
			if (f.getFermata().getId() == id)
				return f;
		return null;
	}
	
	public Fermata trovaFermata(int id) {
		for (Fermata f : fermate)
			if (f.getId() == id)
				return f;
		return null;
	}

	public RichFermata trovaFermataPerNome(String nome) {
		for (RichFermata f : rFermate)
			if (f.getFermata().getNome().compareTo(nome) == 0)
				return f;
		return null;
	}

	public double trovaLinea(int id) {
		for (Linea l : linee)
			if (l.getId() == id)
				return l.getVelocita();
		return 0.0;
	}

	public double intervalloPerLinea(int id) {
		for (Linea l : linee)
			if (l.getId() == id)
				return l.getIntervallo();
		return 0.0;
	}

}
