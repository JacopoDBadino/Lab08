package it.polito.tdp.extflightdelays.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.extflightdelays.model.Airline;
import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.CoppieAereoporti;
import it.polito.tdp.extflightdelays.model.Flight;

public class ExtFlightDelaysDAO {

	public List<Airline> loadAllAirlines() {
		String sql = "SELECT * from airlines";
		List<Airline> result = new ArrayList<Airline>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Airline(rs.getInt("ID"), rs.getString("IATA_CODE"), rs.getString("AIRLINE")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Airport> loadAllAirports() {
		String sql = "SELECT * FROM airports";
		List<Airport> result = new ArrayList<Airport>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Airport airport = new Airport(rs.getInt("ID"), rs.getString("IATA_CODE"), rs.getString("AIRPORT"),
						rs.getString("CITY"), rs.getString("STATE"), rs.getString("COUNTRY"), rs.getDouble("LATITUDE"),
						rs.getDouble("LONGITUDE"), rs.getDouble("TIMEZONE_OFFSET"));
				result.add(airport);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Flight> loadAllFlights() {
		String sql = "SELECT * FROM flights";
		List<Flight> result = new LinkedList<Flight>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Flight flight = new Flight(rs.getInt("ID"), rs.getInt("AIRLINE_ID"), rs.getInt("FLIGHT_NUMBER"),
						rs.getString("TAIL_NUMBER"), rs.getInt("ORIGIN_AIRPORT_ID"),
						rs.getInt("DESTINATION_AIRPORT_ID"),
						rs.getTimestamp("SCHEDULED_DEPARTURE_DATE").toLocalDateTime(), rs.getDouble("DEPARTURE_DELAY"),
						rs.getDouble("ELAPSED_TIME"), rs.getInt("DISTANCE"),
						rs.getTimestamp("ARRIVAL_DATE").toLocalDateTime(), rs.getDouble("ARRIVAL_DELAY"));
				result.add(flight);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<CoppieAereoporti> trovaVoli(Map<Integer, Airport> airports) {
		String sql = "SELECT ORIGIN_AIRPORT_ID, DESTINATION_AIRPORT_ID, COUNT(*) as C, "
				+ "AVG(DISTANCE) AS D FROM flights " + "GROUP BY ORIGIN_AIRPORT_ID, DESTINATION_AIRPORT_ID";
		List<CoppieAereoporti> result = new LinkedList<CoppieAereoporti>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Airport a1 = airports.get((rs.getInt("ORIGIN_AIRPORT_ID")));
				Airport a2 = airports.get((rs.getInt("DESTINATION_AIRPORT_ID")));
				int cnt = rs.getInt("C");
				int dist = rs.getInt("D");

				result.add(new CoppieAereoporti(a1, a2, cnt, dist));
			}

			conn.close();
			return result;

		} catch (

		SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public CoppieAereoporti trovaVoli(int id1, int id2) {

		String sql = "SELECT ORIGIN_AIRPORT_ID, DESTINATION_AIRPORT_ID, COUNT(*) as C, AVG(DISTANCE) AS D "
				+ "FROM flights WHERE ORIGIN_AIRPORT_ID = ? AND DESTINATION_AIRPORT_ID = ? "
				+ "GROUP BY ORIGIN_AIRPORT_ID, DESTINATION_AIRPORT_ID";

		CoppieAereoporti c;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id2);
			st.setInt(2, id1);
			ResultSet rs = st.executeQuery();

			rs.first();

			int cnt = rs.getInt("C");
			int distance = rs.getInt("D");
			c = new CoppieAereoporti(null, null, cnt, distance);

			conn.close();
			return c;

		} catch (

		SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			return null;
		}

	}

}
