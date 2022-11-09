package persistence.curso.sesion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;

public class SesionCRUD {
	
	public static final String SQL_ADD_SESION = Conf.getInstance()
			.getProperty("TSESION_ADD");
	
	public static void addSesion(int idCurso, String horaInicio, String horaFin) {
		Connection c = null;
		PreparedStatement pst = null;
		
		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(SQL_ADD_SESION);
			
			pst.setInt(1, idCurso);
			pst.setString(2, horaInicio);
			pst.setString(3, horaFin);
			
			pst.execute();
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(c);
		}
	}
}