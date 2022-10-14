package persistence.colegiado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import business.BusinessException;
import business.colegiado.assembler.ColegiadoAssembler;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;

public class ColegiadoCrud {
	private static final String SQL_BUSCAR_COLEGIADO_DNI = Conf.getInstance().getProperty("TCOLEGIADO_FIND_BY_DNI");

	private static final String SQL_ANADIR_COLEGIADO = Conf.getInstance().getProperty("TCOLEGIADO_ADD");
	private static final String ESTADO_PENDIENTE = "PENDIENTE"; // se le asigna como PENDIENTE de momento

	private static final String SQL_FIND_ALL_COLEGIADOS = Conf.getInstance().getProperty("TCOLEGIADO_ALL");
	private static final String SQL_BUSCAR_COLEGIADO_NUM_COLEGIADO = Conf.getInstance()
			.getProperty("BUSCAR_COLEGIADO_NUM_COLEGIADO");

	private static final String SQL_LISTAR_SOLICITUDES_ALTA_COLEGIADOS = Conf.getInstance()
			.getProperty("TCOLEGIADO_FIND_ALL_CANDIDATES");

	private static final String SQL_OBTENER_TITULACION_COLEGIADO = Conf.getInstance()
			.getProperty("TCOLEGIADO_FIND_TITULACION_BY_DNI");

	private static final String SQL_ASIGNACION_NUMERO_COLEGIADO = Conf.getInstance()
			.getProperty("TCOLEGIADO_ASIGNACION_NUMERO_COLEGIADO");

	public static ColegiadoDto findColegiadoGeneral(String dni, String QuerySQL, String atributo)
			throws BusinessException {
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
			colegiado = ColegiadoAssembler.toColegiadoDto(rs);

		} catch (SQLException e) {
			throw new PersistenceException(e);

		} finally {
			Jdbc.close(rs, pst, c);
		}

		return colegiado;
	}

	public static ColegiadoDto findColegiadoDni(String Dni) throws BusinessException {
		return findColegiadoGeneral(Dni, SQL_BUSCAR_COLEGIADO_DNI, "DNI");
	}

	public static ColegiadoDto findColegiadoNumColegiado(String Num) throws BusinessException {
		return findColegiadoGeneral(Num, SQL_BUSCAR_COLEGIADO_NUM_COLEGIADO, "NUMERO");
	}

	public static ColegiadoDto addColegiado(ColegiadoDto colegiado) throws BusinessException {
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
			Jdbc.close(pst);
			Jdbc.close(con);
		}
		return colegiado;
	}

	public static List<ColegiadoDto> findAllColegiados() {
		Connection c = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();

			st = c.createStatement();
			rs = st.executeQuery(SQL_FIND_ALL_COLEGIADOS);

			return ColegiadoAssembler.toColegiadoList(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, st, c);
		}
	}

	/**
	 * Listado de todas las solicitudes de alta de colegiados.
	 * 
	 * @return
	 */
	public static List<ColegiadoDto> findAllSolicitudesAltaColegiados() {
		Connection c = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();

			st = c.createStatement();
			rs = st.executeQuery(SQL_LISTAR_SOLICITUDES_ALTA_COLEGIADOS);

			return ColegiadoAssembler.toColegiadoList(rs);

		} catch (SQLException e) {
			throw new PersistenceException(e);

		} finally {
			Jdbc.close(rs, st, c);
		}
	}

	/**
	 * Obtiene la titulación del colegiado
	 * 
	 * @param dniToSearch Dni del colegiado a buscar.
	 * @return
	 */
	public static int findTitulacionColegiadoByDni(final String dniToSearch) {
		int titulacion = -1;

		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_OBTENER_TITULACION_COLEGIADO);

			pst.setString(1, dniToSearch);

			rs = pst.executeQuery();
			rs.next();

			titulacion = rs.getInt("titulacion");

		} catch (SQLException e) {
			throw new PersistenceException(e);

		} finally {
			Jdbc.close(rs, pst, c);
		}

		return titulacion;
	}

	public static void updateNumColegiado(final ColegiadoDto colegiado) {
		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = Jdbc.getConnection();

			pst = con.prepareStatement(SQL_ASIGNACION_NUMERO_COLEGIADO);

			pst.setString(1, colegiado.numeroColegiado);
			pst.setString(2, colegiado.DNI);

			pst.executeUpdate();

		} catch (SQLException sqle) {
			throw new PersistenceException(sqle);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(con);
		}

	}
}
