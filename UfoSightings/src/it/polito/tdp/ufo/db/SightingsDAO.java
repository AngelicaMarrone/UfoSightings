package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.polito.tdp.ufo.model.Adiacenza;
import it.polito.tdp.ufo.model.Sighting;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Sighting(res.getInt("id"),
							res.getTimestamp("datetime").toLocalDateTime(),
							res.getString("city"), 
							res.getString("state"), 
							res.getString("country"),
							res.getString("shape"),
							res.getInt("duration"),
							res.getString("duration_hm"),
							res.getString("comments"),
							res.getDate("date_posted").toLocalDate(),
							res.getDouble("latitude"), 
							res.getDouble("longitude"))) ;
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
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

	public List<Integer> listAnni() {
		String sql = "SELECT DISTINCT YEAR(s.DATETIME) AS y " + 
				"FROM sighting s " +
				"WHERE s.country=\'us\' "+
				"ORDER BY y " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getInt("y"));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
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

	public int getAvvistamenti(Integer a) {
		String sql = "SELECT COUNT(*) as cnt " + 
				"FROM sighting s " + 
				"WHERE Year(s.DATETIME)=? AND s.country=\'us\' " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			int count = 0;
			st.setInt(1, a);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				count= res.getInt("cnt");
			}
			
			conn.close();
			return count ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0 ;
		}
	}

	public List<String> getVertex(Integer anno) {
		String sql = "SELECT s.state, COUNT(s.id) " + 
				"FROM sighting s " + 
				"WHERE Year(s.DATETIME)=? AND s.country=\'us\' " + 
				"GROUP BY s.state " + 
				"HAVING COUNT(s.id)>0 " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<String> stati= new ArrayList<String>();
			st.setInt(1, anno);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				stati.add(res.getString("s.state"));
			}
			
			conn.close();
			return stati ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Adiacenza> getEdges(Integer anno) {
		String sql = "SELECT DISTINCT s1.state, s2.state " + 
				"FROM sighting s1, sighting s2 " + 
				"WHERE YEAR(s1.DATETIME)=? AND YEAR(s2.DATETIME)=YEAR(s1.DATETIME) AND s1.country=\'us\' AND s2.country=s1.country " + 
				"AND s1.state<s2.state " + 
				"AND DATEDIFF(s2.DATETIME,s1.DATETIME)>0 " + 
				"GROUP BY s1.state,s2.state " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Adiacenza> ris= new ArrayList<Adiacenza>();
			st.setInt(1, anno);
			
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				ris.add(new Adiacenza(res.getString("s1.state"), res.getString("s2.state")));
			}
			
			conn.close();
			return ris ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
