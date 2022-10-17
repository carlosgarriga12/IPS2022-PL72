package persistence.InscripcionColegiado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import persistence.colegiado.ColegiadoDto;
import persistence.curso.CursoDto;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;



public class InscripcionColegiadoCRUD {
	private static final String SQL_INSCRIPCION_COLEGIADO = Conf.getInstance().getProperty("INSCRIPCION_COLEGIADO");
	private static final String SQL_IS_INSCRITO = Conf.getInstance().getProperty("IS_INSCRITO");
	private static final String SQL_ID_INSCRIPCION = Conf.getInstance().getProperty("ID_INSCRIPCION");

	
	public static void InscribirColegiado(CursoDto curso, ColegiadoDto colegiado) throws PersistenceException {
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_COLEGIADO);
			int idInscripcion;
			stmt2 = cn.prepareStatement(SQL_ID_INSCRIPCION);
			stmt2.setInt(1, curso.codigoCurso);
			ResultSet rs = stmt2.executeQuery();
			idInscripcion = rs.getInt("ID");
			
			int i = 1;
			stmt.setString(i++, colegiado.DNI);
			stmt.setInt(i++, idInscripcion);
			stmt.setString(i++, LocalDate.now().toString());
			stmt.setDouble(i++, curso.precio);
			stmt.setInt(i++, 0);
			stmt.execute();
			
			
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
			Jdbc.close(stmt2);
		}
		
	}
	

	public static boolean isInscrito(ColegiadoDto colegiado, CursoDto cursoSeleccionado) throws PersistenceException {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		boolean inscrito = false;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_IS_INSCRITO);
			stmt.setString(1, colegiado.DNI);
			stmt.setInt(2, cursoSeleccionado.codigoCurso);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.getInt("TOTAL")>0) {
				inscrito = true;
			}
			
			
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
		return inscrito;
	}
}