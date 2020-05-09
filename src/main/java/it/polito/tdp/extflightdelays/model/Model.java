package it.polito.tdp.extflightdelays.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	Graph<Airport, DefaultWeightedEdge> graph;
	Map<Integer, Airport> airports;

	public Graph<Airport, DefaultWeightedEdge> creaGrafo(int distanzaMin) {
		graph = new SimpleWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		airports = new HashMap<Integer, Airport>();

		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
		Graphs.addAllVertices(graph, dao.loadAllAirports());

		for (Airport a : dao.loadAllAirports())
			airports.put(a.getId(), a);

		for (CoppieAereoporti ca : dao.trovaVoli(airports, distanzaMin)) {
			DefaultWeightedEdge edge = graph.getEdge(ca.getAir1(), ca.getAir2());

			if (edge == null)
				Graphs.addEdge(graph, ca.getAir1(), ca.getAir2(), ca.getDist());

			else {
				double pesoV = graph.getEdgeWeight(edge);
				double pesoN = ca.getDist();
				graph.setEdgeWeight(edge, (pesoV + pesoN) / 2);
			}

		}

		return graph;

	}

}
