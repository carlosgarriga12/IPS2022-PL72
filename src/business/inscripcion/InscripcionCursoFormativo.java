package business.inscripcion;

import java.sql.SQLException;
import java.time.LocalDate;

import business.BusinessException;
import business.util.DateUtils;
import persistence.curso.CursoCRUD;
import persistence.curso.CursoDto;
import persistence.inscripcionCursoFormacion.InscripcionCursoFormacionDto;
import persistence.inscripcionCursoFormacion.InscripcionCursoFormationCRUD;

/**
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class InscripcionCursoFormativo {

	/**
	 * Realización de la apertura de inscripciones a un curso formativo.
	 * 
	 * @param inscripcionCurso
	 * @param fechaApertura
	 * @param fechaCierre
	 * @param plazasDisponibles
	 * 
	 * @throws BusinessException Si la fecha de inscripcion no es válida.
	 * @throws SQLException
	 */
	public static void abrirCursoFormacion(final CursoDto curso, final LocalDate fechaApertura,
			final LocalDate fechaCierre, final int plazasDisponibles) throws BusinessException, SQLException {

		if (DateUtils.checkDateIsBefore(fechaCierre, fechaApertura)
				|| DateUtils.checkDateIsBefore(curso.fechaInicio, fechaApertura)) {

			throw new BusinessException();
		}

		InscripcionCursoFormacionDto inscripcionCurso = new InscripcionCursoFormacionDto();

		inscripcionCurso.curso = curso;
		inscripcionCurso.fechaApertura = fechaApertura;
		inscripcionCurso.fechaCierre = fechaCierre;
		
		InscripcionCursoFormationCRUD.addNewInscripcion(inscripcionCurso);
		
		curso.plazasDisponibles = plazasDisponibles;
		curso.estado = CursoDto.CURSO_ABIERTO;
		
		CursoCRUD.abrirCurso(curso);
	}
}
