package persistence.InscripcionColegiado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import persistence.DtoAssembler;
import persistence.Colegiado_Inscripcion.Colegiado_Inscripcion;
import persistence.colegiado.ColegiadoDto;
import persistence.curso.CursoDto;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;



public class InscripcionColegiadoCRUD {
	private static final String SQL_INSCRIPCION_COLEGIADO = Conf.getInstance().getProperty("INSCRIPCION_COLEGIADO");
	private static final String SQL_IS_INSCRITO = Conf.getInstance().getProperty("IS_INSCRITO");
	private static final String SQL_LISTA_INSCRIPCIONES_COLEGIADO = Conf.getInstance().getProperty("LISTA_INSCRIPCIONES_COLEGIADO");

	
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
			stmt.setString(i++, "PREINSCRITO");
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
	
	public static List<Colegiado_Inscripcion> Lista_Inscritos_Curso(CursoDto c){
		PreparedStatement stmt = null;
		ArrayList<Colegiado_Inscripcion> inscritos;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_LISTA_INSCRIPCIONES_COLEGIADO);
			stmt.setInt(1, c.codigoCurso);
			
			ResultSet rs = stmt.executeQuery();
			inscritos =  DtoAssembler.toInscripcionColegiadosList(rs);
			
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
		return inscritos;
	}
}