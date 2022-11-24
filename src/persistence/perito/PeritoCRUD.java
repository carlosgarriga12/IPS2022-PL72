package persistence.perito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import persistence.DtoAssembler;
import persistence.colegiado.ColegiadoDto;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;

public class PeritoCRUD {
	private static final String SQL_ADD_PERITO = Conf.getInstance()
			.getProperty("TCOLEGIADO_ADD_PERITO");
	
	private static final String SQL_RENOVAR_PERITO = Conf.getInstance()
			.getProperty("TCOLEGIADO_RENOVAR_PERITO");
	
	private static final String SQL_ULTIMO_NUMERO_PERITO = Conf.getInstance()
			.getProperty("TCOLEGIADO_ULTIMA_POSICION_PERITO");
	
	private static final String SQL_LIST_ALL_PERITOS = Conf.getInstance()
			.getProperty("TCOLEGIADO_FIND_ALL_PERITOS");
	
	public static void addPerito(String dni) {
		Connection c = null;
		PreparedStatement pst = null;
		
		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(SQL_ADD_PERITO);
			int i = 1;
			
			pst.setInt(i++, ultimaPosicionPerito());
			pst.setString(i++, dni);
			
			pst.execute();
			
		} catch(SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(c);
		}
	}
	
	public static void renovarPerito(String dni) {
		Connection c = null;
		PreparedStatement pst = null;
		
		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(SQL_RENOVAR_PERITO);
			
			pst.setString(1, dni);
			
			pst.execute();
			
		} catch(SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(c);
		}
	}

	private static int ultimaPosicionPerito() {
		Connection c = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getConnection();
			st = c.createStatement();
			rs = st.executeQuery(SQL_ULTIMO_NUMERO_PERITO);

			if (rs.next()) {
				return rs.getInt(1) + 1;
			} else {
				return 1;
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, st, c);
		}
	}
	
	public static List<ColegiadoDto> findAllPeritosPosicion() {
		Connection c = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getConnection();
			st = c.createStatement();
			rs = st.executeQuery(SQL_LIST_ALL_PERITOS);
			
			return DtoAssembler.toColegiadoList(rs);
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, st, c);
		}
	}
	
}
