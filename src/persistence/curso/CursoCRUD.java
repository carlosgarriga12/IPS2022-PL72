package persistence.curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;

public class CursoCRUD {
	
	private static final String SQL_INSERT_CURSO = 
			Conf.getInstance().getProperty("TCURSO_INSERT");
	
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
		} catch(SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
	}
}
