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

	Graph<Airport, DefaultEdge> graph;
	Map<Integer, Airport> airports;

	public void creaGrafo(int distanzaMin) {
		graph = new SimpleWeightedGraph<Airport, DefaultEdge>(DefaultEdge.class);
		airports = new HashMap<Integer, Airport>();

		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
		Graphs.addAllVertices(graph, dao.loadAllAirports());

		for (Airport a : dao.loadAllAirports())
			airports.put(a.getId(), a);

		for (CoppieAereoporti ca : dao.trovaVoli(airports)) {
			DefaultEdge edge = graph.getEdge(ca.getAir1(), ca.getAir2());

			if (edge == null)
				Graphs.addEdge(graph, ca.getAir1(), ca.getAir2(), ca.getDist());

			else {
				double pesoV = graph.getEdgeWeight(edge);
				double pesoN = ca.getDist();
				graph.setEdgeWeight(edge, (pesoV + pesoN) / 2);
			}

		}

		System.out.println(String.format("Creato grafico con %d vertici e %d archi", graph.vertexSet().size(),
				graph.edgeSet().size()));

	}

}
