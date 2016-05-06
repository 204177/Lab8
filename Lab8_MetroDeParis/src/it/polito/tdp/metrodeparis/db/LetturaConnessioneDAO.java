package it.polito.tdp.metrodeparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.metrodeparis.model.Connessione;
import it.polito.tdp.metrodeparis.model.Fermata;

public class LetturaConnessioneDAO {
	
	
	public List<Connessione> getConnessioni() {
		List<Connessione> lista = new ArrayList<Connessione>();
		
		Connection conn = DBConnect.getConnection();
		String sql = "select * from connessione";
		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) 
			{
				Connessione c = new Connessione(res.getInt("id_connessione"), res.getInt("id_linea"), res.getInt("id_stazP"), res.getInt("id_stazA"));
				lista.add(c);
			}
			
			res.close();
			conn.close();
			
			return lista;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
		
	}
}
