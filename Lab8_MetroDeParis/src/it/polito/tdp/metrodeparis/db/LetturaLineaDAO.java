package it.polito.tdp.metrodeparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.metrodeparis.model.Connessione;
import it.polito.tdp.metrodeparis.model.Linea;

public class LetturaLineaDAO {

	
	public List<Linea> getLinee() {
		List<Linea> lista = new ArrayList<Linea>();
		
		Connection conn = DBConnect.getConnection();
		String sql = "select * from linea";
		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) 
			{
				Linea l = new Linea(res.getInt("id_linea"), res.getString("nome"), res.getDouble("velocita"), res.getDouble("intervallo"), res.getString("colore"));
				lista.add(l);
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
