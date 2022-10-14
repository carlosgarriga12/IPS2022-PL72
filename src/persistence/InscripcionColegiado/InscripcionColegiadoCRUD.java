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
	private static final String SQL_INSCRIPCION_FIND_FECHA = Conf.getInstance().getProperty("TINSCRIPCION_FIND_BY_FECHA");
	private static final String SQL_INSCRIPCION_PAGAR = Conf.getInstance().getProperty("TINSCRIPCION_PAGAR");

	public static void InscribirColegiado(CursoDto curso, ColegiadoDto colegiado) throws PersistenceException {
		PreparedStatement stmt = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_COLEGIADO);
			
			int i = 1;
			stmt.setString(i++, colegiado.DNI);
			stmt.setInt(i++, curso.codigoCurso);
			stmt.setString(i++, LocalDate.now().toString());
			stmt.setDouble(i++, curso.precio);
			stmt.setString(i++, "pre-inscrito");
			stmt.execute();
			
			
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
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
	
	public static String findFechaPreinscripcion(String dni, int cursoSeleccionado) throws PersistenceException {
		PreparedStatement stmt = null;
		String fecha = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_FIND_FECHA);
			stmt.setString(1, dni);
			stmt.setInt(2, cursoSeleccionado);
			
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			
			fecha = rs.getString(1);
			
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
		return fecha;
	}
	
	public static void pagarCursoColegiado(String dni, int curso, String estado, String formaDePago) throws PersistenceException {
		PreparedStatement stmt = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_PAGAR);
			stmt.setString(1, estado);
			stmt.setString(2, formaDePago);
			stmt.setString(3, dni);
			stmt.setInt(4, curso);
			
			stmt.executeUpdate();
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
	}
}