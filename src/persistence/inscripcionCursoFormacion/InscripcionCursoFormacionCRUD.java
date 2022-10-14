package persistence.inscripcionCursoFormacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import persistence.DtoAssembler;
import persistence.curso.CursoDto;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;

/**
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class InscripcionCursoFormacionCRUD {

	private static final String SQL_INSERT_INSCRIPCION_CURSO_FORMATIVO = Conf.getInstance()
			.getProperty("TINSCRIPCION_CURSO_ADD");
	private static final String SQL_Lista_Inscripciones = Conf.getInstance().getProperty("LISTA_INSCRIPCIONES");
	private static final String SQL_Plazas_Libres = Conf.getInstance().getProperty("PLAZAS_LIBRES");
	private static final String SQL_Is_Curso_Abierto = Conf.getInstance().getProperty("Is_Curso_Abierto");


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
	public static boolean isFechaDentro(LocalDate FechaInicio, LocalDate FechaFinal) {
		LocalDate fAhora = LocalDate.now();
		if(fAhora.isAfter(FechaInicio) && fAhora.isBefore(FechaFinal)) {
			return true;
		}
		if(fAhora.isEqual(FechaInicio) || fAhora.isEqual(FechaFinal)) {
			return true;
		}
		return false;
	}
	
	public static List<CursoDto> listaCursosAbiertos() throws PersistenceException {
		PreparedStatement stmt = null;
		List<CursoDto> cursos = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_Lista_Inscripciones);
			List<CursoDto> respuesta = DtoAssembler.toInscripcionList(stmt.executeQuery());
			LocalDate ahora = LocalDate.now();
			cursos = new ArrayList<>();
			for(CursoDto curso: respuesta) {
				if(isFechaDentro(curso.fechaApertura, curso.fechaCierre)) {
					cursos.add(curso);
				}
			}
			
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
		return cursos;
	}
	
	public static boolean PlazasLibres(CursoDto curso) throws PersistenceException {
		PreparedStatement stmt = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_Plazas_Libres);
			stmt.setInt(1, curso.codigoCurso);
			int respuesta = stmt.executeQuery().getInt("TOTAL");
			return curso.plazasDisponibles > respuesta;
			
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
		
	}
	
	


}
