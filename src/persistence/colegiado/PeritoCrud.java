package persistence.colegiado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import persistence.SolicitudServicios.SolicitudServiciosCrud;
import persistence.jdbc.Jdbc;
import persistence.util.Conf;

public class PeritoCrud {
	
	private static final String SQL_ListarPeritosOrdenados = Conf.getInstance().getProperty("LISTAR_PERITOS_ORDENADOS");
	private static final String SQL_RECUPERAR_POSICION = Conf.getInstance().getProperty("RECUPERAR_POSICION");
	
	
	

	
	
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
	
	
	


	public static void PosicionAnterior(String DNI) {
		Connection c = null;
		Statement pst = null;
		ResultSet rs = null;
		int c1 = 0;
		int c2 = 0;

		try {
			c = Jdbc.getConnection();

			pst = c.createStatement();
			rs = pst.executeQuery("SELECT PosicionAnterior, PosicionPerito from COLEGIADO where dni = \""+DNI+"\"");

			c1 = rs.getInt("PosicionPerito");
			c2 = rs.getInt("PosicionAnterior");
			
			
			

		} catch (SQLException e) {
			System.out.print("");
		} finally {
			Jdbc.close(rs, pst, c);
		}
		
		ColegiadoDto col = new ColegiadoDto();
		col.posicionPerito = c1;
		SolicitudServiciosCrud.actualizaPosicionesLista(col);
		col.posicionPerito = c2;
		SolicitudServiciosCrud.actualizaPosicionesListaAdd(col);

	}


	public static void RecuperaPosicion(String DNI) {
		// TODO Auto-generated method stub
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			PosicionAnterior(DNI);
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_RECUPERAR_POSICION);
			pst.setString(1, DNI);

			pst.execute();

		} catch (SQLException e) {
			System.out.print("");
		} finally {
			Jdbc.close(rs, pst, c);
		}
	}
}
