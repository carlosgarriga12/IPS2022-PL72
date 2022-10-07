package persistence.curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import persistence.DtoAssembler;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;

public class CursoCRUD {

	private static final String SQL_INSERT_CURSO = Conf.getInstance().getProperty("TCURSO_INSERT");
	private static final String SQL_ABRIR_CURSO = Conf.getInstance().getProperty("TCURSO_ABRIR_CURSO");
	private static final String SQL_LIST_ALL_COURSES = Conf.getInstance().getProperty("TCURSO_LIST_ALL_COURSES");
	private static final String SQL_CHECK_COURSE_OPEN = Conf.getInstance().getProperty("T_CURSO_IS_ABIERTO");

	public static void add(final CursoDto curso) throws SQLException {
		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_INSERT_CURSO);

			int i = 1;
			pst.setString(i++, curso.titulo);
			pst.setString(i++, curso.fechaInicio.toString());
			pst.setDouble(i++, curso.precio);

			pst.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	/**
	 * La apertura de un curso implica la actualización del número de plazas
	 * disponibles y el estado de este pasará a ser ABIERTO.
	 * 
	 * @param curso
	 * @throws SQLException
	 */
	public static void abrirCurso(final CursoDto curso) throws SQLException {
		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_ABRIR_CURSO);

			pst.setInt(1, curso.plazasDisponibles);
			pst.setInt(2, curso.codigoCurso);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	/**
	 * Listado de todos los curso actualmente planificados.
	 * 
	 * @return
	 */
	public static List<CursoDto> listarCursosActualmentePlanificados() {
		Connection con = null;
		PreparedStatement pst = null;
		List<CursoDto> res = null;

		try {

			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_LIST_ALL_COURSES);

			List<CursoDto> allCourses = DtoAssembler.toCursoList(pst.executeQuery());

			// Filtrar por curso planificado
			res = allCourses.stream().filter(c -> c.estado.equalsIgnoreCase(CursoDto.CURSO_PLANIFICADO)).toList();

		} catch (SQLException e) {
			throw new PersistenceException(e);

		} finally {
			Jdbc.close(pst);
		}

		return res;
	}

	/**
	 * Comprueba si el curso pasado como parametro está abierto, es decir, tiene una
	 * inscripción abierta.
	 * 
	 * @param curso
	 * @return
	 */
	public static boolean isCursoAbierto(final CursoDto curso) {
		Connection con = null;
		PreparedStatement pst = null;
		boolean isOpen = false;

		try {
			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_CHECK_COURSE_OPEN);

			pst.setInt(1, curso.codigoCurso);

			isOpen = pst.executeQuery().getInt("CURSO_NUM") == 1;

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}

		return isOpen;
	}

}
