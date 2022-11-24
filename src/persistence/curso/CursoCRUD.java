package persistence.curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import business.BusinessException;
import business.util.DateUtils;
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

	private static final String SQL_CANCELAR_CURSO = Conf.getInstance()
			.getProperty("TCURSO_CANCELAR_ID");
	private static final String SQL_LIST_ALL_OPENED_PLANIF_COURSES = Conf.getInstance()
			.getProperty("TCURSO_SELECCIONAR_ABIERTOS_PLANIFICADOS");
	private static final String SQL_LIST_ALL_INSC_COURSES = Conf.getInstance()
			.getProperty("TCURSO_SELECCIONAR_CURSO_INSCRITO");

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
			pst.setInt(i++, curso.codigoCurso);
			pst.setString(i++, curso.estado);
			pst.setString(i++, curso.CantidadPagarColectivo);
			
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
			pst.setString(2, curso.fechaApertura.toString());
			pst.setString(3, curso.fechaCierre.toString());
			pst.setInt(4, curso.codigoCurso);

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
				 
				if (c.estado.equals(CursoDto.CURSO_PLANIFICADO) 
						&& DateUtils.checkDateIsAfter(c.fechaInicio, LocalDate.now())) {
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
	
	public static List<CursoDto> listarCursosAbiertosPlanificados() {
		Connection con = null;
		PreparedStatement pst = null;
		List<CursoDto> res = null;

		try {

			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_LIST_ALL_OPENED_PLANIF_COURSES);

			res = DtoAssembler.toCursoList(pst.executeQuery());

		} catch (SQLException e) {
			throw new PersistenceException(e);

		} finally {
			Jdbc.close(pst);
		}

		return res;
	}

	public static void cancelarCursoCOIIPA(int codigoCurso) {
		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_CANCELAR_CURSO);

			pst.setInt(1, codigoCurso);
			
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	public static List<CursoDto> listarCursosIsInscrito(String dni) {
		Connection con = null;
		PreparedStatement pst = null;
		try {

			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_LIST_ALL_INSC_COURSES);
			
			pst.setString(1, dni);
			
			return DtoAssembler.toCursoList(pst.executeQuery());


		} catch (SQLException e) {
			throw new PersistenceException(e);

		} finally {
			Jdbc.close(pst);
		}
	}

}