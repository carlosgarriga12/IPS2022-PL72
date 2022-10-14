package persistence.colegiado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import persistence.DtoAssembler;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;

public class ColegiadoCrud {
	private static final String SQL_BUSCAR_COLEGIADO_DNI = Conf.getInstance().getProperty("TCOLEGIADO_FIND_BY_DNI");

	private static final String SQL_ANADIR_COLEGIADO = Conf.getInstance().getProperty("TCOLEGIADO_ADD");
	private static final String ESTADO_PENDIENTE = "PENDIENTE"; // se le asigna como PENDIENTE de momento
	
	private static final String SQL_FIND_ALL_COLEGIADOS = Conf.getInstance().getProperty("TCOLEGIADO_ALL");
	private static final String SQL_BUSCAR_COLEGIADO_NUM_COLEGIADO = Conf.getInstance().getProperty("BUSCAR_COLEGIADO_NUM_COLEGIADO");
	

	
	public static ColegiadoDto findColegiadoGeneral(String dni, String QuerySQL, String atributo) throws PersistenceException {
		ColegiadoDto colegiado;

		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(QuerySQL);

			pst.setString(1, dni);

			rs = pst.executeQuery();
			rs.next();
			if (rs.getString(atributo) == null) {
				return null;
			}
			colegiado = DtoAssembler.toColegiadoDto(rs);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					/* ignore */ }
			if (pst != null)
				try {
					pst.close();
				} catch (SQLException e) {
					/* ignore */ }
			if (c != null)
				try {
					c.close();
				} catch (SQLException e) {
					/* ignore */ }
		}

		return colegiado;
	}
	
	public static ColegiadoDto findColegiadoDni(String Dni) throws PersistenceException {
		return findColegiadoGeneral(Dni, SQL_BUSCAR_COLEGIADO_DNI, "DNI");
	}
	
	
	public static ColegiadoDto findColegiadoNumColegiado(String Num) throws PersistenceException {
		return findColegiadoGeneral(Num, SQL_BUSCAR_COLEGIADO_NUM_COLEGIADO, "NUMERO");
	}

	
	public static ColegiadoDto addColegiado(ColegiadoDto colegiado) throws PersistenceException {
		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_ANADIR_COLEGIADO);

			// set cosas, completar
			pst.setString(1, colegiado.DNI);
			pst.setString(2, colegiado.nombre);
			pst.setString(3, colegiado.apellidos);
			pst.setString(4, colegiado.poblacion);
			pst.setInt(5, colegiado.telefono);
			pst.setInt(6, colegiado.titulacion);
			pst.setString(7, colegiado.centro);
			pst.setInt(8, colegiado.annio);
			pst.setInt(9, colegiado.numeroTarjeta);
			pst.setString(10, LocalDate.now().toString());
			pst.setString(11, ESTADO_PENDIENTE);
			pst.setString(12, "");
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			if (pst != null)
				try {
					pst.close();
				} catch (SQLException e) {
					/* ignore */ }
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					/* ignore */ }
		}
		return colegiado;
	}
	
	public static List<ColegiadoDto> findAllColegiados() throws PersistenceException {
		Connection c = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getConnection();
		
			st = c.createStatement();
			rs = st.executeQuery(SQL_FIND_ALL_COLEGIADOS);
			
			return DtoAssembler.toColegiadoList(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, st, c);
		}
	}
	
	
}
