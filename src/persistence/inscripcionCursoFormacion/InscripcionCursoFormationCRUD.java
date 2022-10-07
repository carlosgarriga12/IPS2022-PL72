package persistence.inscripcionCursoFormacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;

/**
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class InscripcionCursoFormationCRUD {

	private static final String SQL_INSERT_INSCRIPCION_CURSO_FORMATIVO = Conf.getInstance()
			.getProperty("TINSCRIPCION_CURSO_ADD");

	/**
	 * Apertura de una nueva inscripción a un curso.
	 * 
	 * @param inscripcion
	 */
	public static void addNewInscripcion(final InscripcionCursoFormacionDto inscripcion) {
		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_INSERT_INSCRIPCION_CURSO_FORMATIVO);

			int i = 1;
			pst.setString(i++, String.valueOf(inscripcion.fechaApertura));
			pst.setString(i++, String.valueOf(inscripcion.fechaCierre));
			pst.setInt(i++, inscripcion.curso.codigoCurso);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);

		} finally {
			Jdbc.close(pst);
		}
	}
}
