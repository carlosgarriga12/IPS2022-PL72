package persistence.solicitudVisados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import persistence.DtoAssembler;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;
import persistence.colegiado.ColegiadoDto;

public class SolicitudVisadosCRUD {
	private static final String SQL_ADD_SOLICITUD_VISADO = Conf.getInstance()
			.getProperty("TSOLICITUDVISADO_ADD");
	
	private static final String SQL_FIND_ALL_SOLICITUDES_VISADO = Conf.getInstance()
			.getProperty("TSOLICITUDVISADO_FIND_ALL");
	
	private static final String SQL_SET_DNI_VISADO = Conf.getInstance()
			.getProperty("TSOLICITUDVISADO_SET_DNI_VISADO");
	
	private static final String SQL_SET_VISADO = Conf.getInstance()
			.getProperty("TPERITO_SET_VISADO_TRUE");
	
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
	
	public static List<SolicitudVisadoDto> findAllSolicitudesVisado() {
		Connection c = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getConnection();
			
			st = c.createStatement();
			
			rs = st.executeQuery(SQL_FIND_ALL_SOLICITUDES_VISADO);
			
			return DtoAssembler.toSolicitudVisadosList( rs );
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, st, c);
		}
	}
	
	public static void asignarVisadoAPerito(SolicitudVisadoDto s, ColegiadoDto c) {
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			con = Jdbc.getConnection();
			
			pst = con.prepareStatement(SQL_SET_DNI_VISADO);
			
			pst.setString(1, c.DNI);
			pst.setString(2, s.dniPerito);
			
			pst.execute();
			
		} catch(SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(con);
		}
		
		try {
			con = Jdbc.getConnection();
			
			pst = con.prepareStatement(SQL_SET_VISADO);
			pst.setString(1, c.DNI);
			
			pst.execute();
			
		} catch(SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(con);
		}
	}
}
