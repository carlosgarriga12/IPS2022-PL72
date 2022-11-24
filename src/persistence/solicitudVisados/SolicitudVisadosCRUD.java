package persistence.solicitudVisados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import persistence.jdbc.Jdbc;
import persistence.util.Conf;
import persistence.jdbc.PersistenceException;

public class SolicitudVisadosCRUD {
	private static final String SQL_ADD_SOLICITUD_VISADO = Conf.getInstance()
			.getProperty("TSOLICITUDVISADO_ADD");
	
	public static void addSolicitudVisado(SolicitudVisadoDto s) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(SQL_ADD_SOLICITUD_VISADO);
			
			pst.setString(1, s.descripcion);
			pst.setString(2, s.estado);
			pst.setString(3, s.dniPerito);
			
			pst.execute();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}
	} 
}
