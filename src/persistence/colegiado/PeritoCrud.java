package persistence.colegiado;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import persistence.jdbc.Jdbc;
import persistence.util.Conf;

public class PeritoCrud {
	
	private static final String SQL_ListarPeritosOrdenados = Conf.getInstance().getProperty("LISTAR_PERITOS_ORDENADOS");
	
	

	
	
	public static ArrayList<ColegiadoDto> listarPeritosOrdenados(){
		Connection c = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList<ColegiadoDto> solList = new ArrayList<>();

		try {
			c = Jdbc.getConnection();

			st = c.createStatement();
			rs = st.executeQuery(SQL_ListarPeritosOrdenados);
			
			while (rs.next()) {
				try {
					ColegiadoDto sol = new ColegiadoDto();
				sol.nombre = rs.getString("Nombre");
				sol.DNI = rs.getString("DNI");
				sol.apellidos = rs.getString("Apellidos");
				sol.telefono = rs.getInt("Telefono");
				sol.posicionPerito = rs.getInt("PosicionPerito");
				solList.add(sol);}
				catch(Exception e) {}
			}
			return solList;
			
		} catch (SQLException e) {
			System.out.print("");
		} 
		finally {
			Jdbc.close(rs, st, c);
		}
		return solList;
	}
}
