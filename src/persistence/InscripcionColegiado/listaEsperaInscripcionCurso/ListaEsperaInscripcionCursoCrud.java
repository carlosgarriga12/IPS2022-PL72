package persistence.InscripcionColegiado.listaEsperaInscripcionCurso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import persistence.DtoAssembler;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;

public class ListaEsperaInscripcionCursoCrud {

	public static final String SQL_FIND_LISTA_ESPERA_BY_IDCURSO = Conf.getInstance()
			.getProperty("LISTA_ESPERA_CURSO_FINDBYCURSOID");

	public static final String SQL_COLEGIADO_ADD_LISTA_ESPERA_CURSO = Conf.getInstance()
			.getProperty("LISTA_ESPERA_CURSO_ADD");

	public static final String SQL_MAX_POSICION_LISTA_ESPERA_CURSO = Conf.getInstance()
			.getProperty("LISTA_ESPERA_CURSO_MAX_POSICION");

	public static final String SQL_LISTA_ESPERA_FINDBYDNI = Conf.getInstance()
			.getProperty("LISTA_ESPERA_CURSO_FINDBYDNI");

	/**
	 * Listado de usuario de la lista de espera del curso indicado.
	 * 
	 * @since HU. 19733
	 * @param idCurso Id del curso.
	 * @return
	 */
	public static List<ListaEsperaInscripcionCursoDto> findByCursoId(int idCurso) {
		List<ListaEsperaInscripcionCursoDto> res = new ArrayList<>();

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = Jdbc.getConnection();

			pst = con.prepareStatement(SQL_FIND_LISTA_ESPERA_BY_IDCURSO);
			pst.setInt(1, idCurso);

			rs = pst.executeQuery();

			res = DtoAssembler.toListaEsperaInscripcionCursoDtoList(rs);

		} catch (SQLException sqle) {
			throw new PersistenceException(sqle.getMessage());

		} finally {
			Jdbc.close(rs, pst);
			Jdbc.close(con);
		}

		return res;

	}

	/**
	 * Añade un usuario a la lista de espera de un curso, si dicho curso tiene las
	 * plazas agotadas. No se contempla la posibilidad de sacar un usuario de la
	 * lista de espera.
	 * 
	 * @since HU. 19733
	 * @param dniColegiado Dni del usuario.
	 * @param codigoCurso  Id del curso.
	 */
	public static void addColegiadoListaEsperaCursoSeleccionado(final String dniColegiado, final int codigoCurso) {
		PreparedStatement pst = null;
		Connection con = null;

		PreparedStatement pst2 = null;
		ResultSet rs2 = null;

		try {
			con = Jdbc.getConnection();
			con.setAutoCommit(false);

			// La posicion en la lista será consecutiva al último usuario añadido a dicha
			// lista de espera.
			pst2 = con.prepareStatement(SQL_MAX_POSICION_LISTA_ESPERA_CURSO);
			rs2 = pst2.executeQuery();

			rs2.next();
			int maxPosicion = rs2.getInt("MAX_POSITION") + 1;

			System.out.println(maxPosicion);

			pst = con.prepareStatement(SQL_COLEGIADO_ADD_LISTA_ESPERA_CURSO);

			pst.setInt(1, codigoCurso);
			pst.setString(2, dniColegiado);
			pst.setInt(3, maxPosicion);

			pst.executeUpdate();

			con.commit();

		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new PersistenceException(sqle.getMessage());

		} finally {
			Jdbc.close(rs2, pst2);
			Jdbc.close(pst);
			Jdbc.close(con);
		}

	}

	/**
	 * Obtiene el usuario de la lista de espera de un curso.
	 * <p>
	 * Dados el id del curso y el DNI del usuario.
	 * 
	 * @see {@link persistence.DtoAssembler#toListaEsperaInscripcionCursoDto}
	 * 
	 * @since HU. 19733
	 * @param dniUsuario DNI del usuario a buscar en la lista de espera del curso.
	 * @param idCurso    Id del curso.
	 * @return Registro de la lista de espera si lo hay, empty en caso contrario.
	 */
	public static Optional<ListaEsperaInscripcionCursoDto> findByDni(final String dniUsuario, final int idCurso) {
		Optional<ListaEsperaInscripcionCursoDto> res = Optional.empty();

		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Jdbc.getConnection();

			pst = con.prepareStatement(SQL_LISTA_ESPERA_FINDBYDNI);
			pst.setString(1, dniUsuario);
			pst.setInt(2, idCurso);

			rs = pst.executeQuery();
			rs.next();

			res = Optional.ofNullable(DtoAssembler.toListaEsperaInscripcionCursoDto(rs));

		} catch (SQLException sqle) {
			throw new PersistenceException(sqle.getMessage());

		} finally {
			Jdbc.close(rs, pst);
			Jdbc.close(con);
		}

		return res;
	}
}
