package persistence.curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.BusinessException;
import persistence.DtoAssembler;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;

public class CursoCRUD {

	private static final String SQL_INSERT_CURSO = Conf.getInstance().getProperty("TCURSO_INSERT");
	private static final String SQL_ABRIR_CURSO = Conf.getInstance().getProperty("TCURSO_ABRIR_CURSO");
	private static final String SQL_LIST_ALL_SCHEDULED_COURSES = Conf.getInstance()
			.getProperty("TCURSO_LIST_SCHEDULED_COURSES");
	private static final String SQL_LIST_ALL_COURSES = Conf.getInstance().getProperty("TCURSO_LIST_ALL_COURSES");
	private static final String SQL_CHECK_COURSE_OPEN = Conf.getInstance().getProperty("T_CURSO_IS_ABIERTO");
	private static final String SQL_FIND_MAX_CURSO_ID = Conf.getInstance().getProperty("TCURSO_MAX_NUMBER");
	private static final String SQL_LISTA_INSCRIPCIONES = Conf.getInstance().getProperty("LISTA_INSCRIPCIONES");
	private static final String SQL_LIST_ALL_OPENED_COURSES = Conf.getInstance()
			.getProperty("TCURSO_LIST_OPENED_COURSES");

	public static int generarCodigoCurso() {
		Connection c = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();

			st = c.createStatement();

			rs = st.executeQuery(SQL_FIND_MAX_CURSO_ID);

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

	public static void add(CursoDto curso) {
		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_INSERT_CURSO);

			int i = 1;
			pst.setString(i++, curso.titulo);
			pst.setString(i++, curso.fechaInicio.toString());
			pst.setDouble(i++, curso.precio);
			pst.setInt(i++, curso.codigoCurso);

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
	public static void abrirCurso(final CursoDto curso) {
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
	 * @throws BusinessException
	 */
	public static List<CursoDto> listarCursosActualmentePlanificados() {
		Connection con = null;
		PreparedStatement pst = null;
		List<CursoDto> res = new ArrayList<CursoDto>();

		try {

			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_LIST_ALL_SCHEDULED_COURSES);

			List<CursoDto> allCourses = DtoAssembler.toCursoList(pst.executeQuery());

			// Filtrado de cursos planificados
			for (CursoDto c : allCourses) {
				if (c.estado.equals(CursoDto.CURSO_PLANIFICADO)) {
					res.add(c);
				}
			}

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

	public static List<CursoDto> listaCursos() {
		Connection con = null;
		PreparedStatement pst = null;
		List<CursoDto> allCourses;

		try {

			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_LISTA_INSCRIPCIONES);

			allCourses = DtoAssembler.toInscripcionList(pst.executeQuery());

		} catch (SQLException e) {
			throw new PersistenceException(e);

		} finally {
			Jdbc.close(pst);
		}

		return allCourses;
	}

	/**
	 * Listado de todos los cursos PLANIFICADOS y ABIERTOS en el COIIPA.
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public static List<CursoDto> listTodosLosCursos() {
		Connection con = null;
		PreparedStatement pst = null;
		List<CursoDto> res = new ArrayList<CursoDto>();

		try {

			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_LIST_ALL_COURSES);

			res = DtoAssembler.toCursoList(pst.executeQuery());

		} catch (SQLException e) {
			throw new PersistenceException(e);

		} finally {
			Jdbc.close(pst);
		}

		return res;
	}

	public static List<CursoDto> listarCursosActualmenteAbiertos() {
		Connection con = null;
		PreparedStatement pst = null;
		List<CursoDto> res = null;

		try {

			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_LIST_ALL_OPENED_COURSES);

			res = DtoAssembler.toCursoList(pst.executeQuery());

		} catch (SQLException e) {
			throw new PersistenceException(e);

		} finally {
			Jdbc.close(pst);
		}

		return res;
	}

}
