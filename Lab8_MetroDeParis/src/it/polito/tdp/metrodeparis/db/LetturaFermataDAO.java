package it.polito.tdp.metrodeparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.metrodeparis.db.DBConnect;
import it.polito.tdp.metrodeparis.model.Fermata;

public class LetturaFermataDAO {
	
	public List<Fermata> getFermate() {
		List<Fermata> lista = new ArrayList<Fermata>();
		
		Connection conn = DBConnect.getConnection();
		String sql = "select * from fermata";
		PreparedStatement st;
		try {
			
			st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) 
			{
				Fermata f = new Fermata(res.getInt("id_fermata"), res.getString("nome"), res.getDouble("coordX"), res.getDouble("coordY"));
				lista.add(f);
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
