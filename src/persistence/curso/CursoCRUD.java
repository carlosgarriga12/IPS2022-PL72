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
	private static final String SQL_LIST_SCHEDULED_COURSES_CURRENTLY = Conf.getInstance()
			.getProperty("TCURSO_LIST_SCHEDULED_COURSES_CURRENTLY");

	public static void add(CursoDto curso) throws SQLException {
		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_INSERT_CURSO);

			int i = 1;
			pst.setString(i++, curso.titulo);
			pst.setString(i++, curso.fechaInicio.toString());
			pst.setBigDecimal(i++, curso.precio);
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
			pst = con.prepareStatement(SQL_LIST_SCHEDULED_COURSES_CURRENTLY);

			res = DtoAssembler.toCursoList(pst.executeQuery());

		} catch (SQLException e) {
			throw new PersistenceException(e);

		} finally {
			Jdbc.close(pst);
		}

		return res;
	}

}
