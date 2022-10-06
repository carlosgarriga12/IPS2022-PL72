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
	private static final String SQL_LIST_ALL_COURSES = Conf.getInstance().getProperty("TCURSO_LIST_ALL_COURSES");
	
	public static void add(CursoDto curso) throws SQLException {
		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_INSERT_CURSO);

			int i = 1;
			pst.setString(i++, curso.titulo);
			pst.setString(i++, curso.fechaInicio.toString());
			pst.setDouble(i++, curso.precio);
			pst.setString(i++, curso.estado);

			pst.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

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

}
