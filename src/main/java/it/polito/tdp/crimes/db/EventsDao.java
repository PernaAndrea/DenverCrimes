package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	public List<String> getCategorie() {
		String sql = "SELECT DISTINCT offense_category_id FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<String> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getString("offense_category_id"));
				} catch (Throwable t) {
					t.printStackTrace();
				//	System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
				//	System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> getVertici(String categoria,int mese){
		String sql = "SELECT offense_type_id FROM events WHERE offense_category_id = ? AND Month(reported_date) = ?" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, categoria);
			st.setInt(2, mese);
			List<String> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getString("offense_type_id"));
					
				} catch (Throwable t) {
					t.printStackTrace();
					//System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	public List<Adiacenza> getAdiacenze(String categoria, int mese){
		String sql = "SELECT e1.offense_type_id as v1, e2.offense_type_id as v2, COUNT(DISTINCT(e1.neighborhood_id)) as peso "
				+ "FROM events e1, events e2 "
				+ "WHERE e1.offense_category_id = ? AND e1.offense_category_id = e2.offense_category_id "
				+ "AND Month(e1.reported_date) = ? AND Month(e1.reported_date) = Month(e2.reported_date) "
				+ "AND e1.offense_type_id > e2.offense_type_id "
				+ "AND e1.neighborhood_id = e2.neighborhood_id "
				+ "GROUP BY e1.offense_type_id, e2.offense_type_id";
		List<Adiacenza> result = new ArrayList<>();
		try  {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, categoria);
			st.setInt(2, mese);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(new Adiacenza(res.getString("v1"), res.getString("v2"), res.getInt("peso")));
			}
			st.close();
			res.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	/*
	public List<Adiacenza> getAdiacenze(String categoria,int mese){
		String sql = "SELECT e1.offense_type_id, e2.offense_type_id, COUNT( DISTINCT e1.neighborhood_id) AS peso "
				+ "FROM events e1, events e2 "
				+ "WHERE e1.offense_category_id = ? AND e1.offense_category_id = e2.offense_category_id AND "
					+ "Month(e1.reported_date) = ? AND Month(e1.reported_date) = Month(e2.reported_date) AND "
					+ "e1.offense_type_id > e2.offense_type_id AND "
					+ "e1.neighborhood_id = e2.neighborhood_id "
				+ "GROUP BY e1.offense_type_id, e2.offense_type_id" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, categoria);
			st.setInt(2, mese);
			List<Adiacenza> result = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					result.add(new Adiacenza(res.getString("e1.offense_type_id"),res.getString("e1.offense_type_id"),res.getInt("peso")));
					
				} catch (Throwable t) {
					t.printStackTrace();
					//System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return result ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}*/

}
