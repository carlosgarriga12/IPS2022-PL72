package persistence.curso.profesorado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import persistence.DtoAssembler;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;

public class ProfesorCRUD {
	public static final String SQL_ASIGNAR_PROFESOR_CURSO = Conf.getInstance()
			.getProperty("TPROFESOR_ASIGNAR_CURSO");
	
	public static final String SQL_FIND_PROFESORES_LIBRES = Conf.getInstance()
			.getProperty("TPROFESOR_FIND_PROFESORES_LIBRES");
	
	public static void asignarProfesorCurso(int idCurso, String nombre) {
		Connection c = null;
		PreparedStatement pst = null;
		
		try{
			c = Jdbc.getConnection();
			pst = c.prepareStatement(SQL_ASIGNAR_PROFESOR_CURSO);
			int i = 1;
			
			pst.setInt(i++ , idCurso);
			pst.setString(i++, nombre);
			
			pst.execute();
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(c);
		}
	}
	
	public static List<ProfesorDto> listProfesoresLibres() {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(SQL_FIND_PROFESORES_LIBRES);
			rs = pst.executeQuery();
			return DtoAssembler.toProfesorDtoList(rs);
			
			
		}catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}
	}
	
}