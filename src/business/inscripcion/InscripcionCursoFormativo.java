package business.inscripcion;

import java.time.LocalDate;

import business.BusinessException;
import business.util.DateUtils;
import persistence.curso.CursoDto;
import persistence.inscripcionCursoFormacion.InscripcionCursoFormacionDto;

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
	 */
	public void abrirCursoFormacion(final InscripcionCursoFormacionDto inscripcionCurso, final LocalDate fechaApertura,
			final LocalDate fechaCierre, final int plazasDisponibles) throws BusinessException {

		if (DateUtils.checkDateIsBefore(fechaCierre, fechaApertura)
				|| DateUtils.checkDateIsBefore(inscripcionCurso.curso.fechaInicio, fechaApertura)) {

			throw new BusinessException();
		}

		inscripcionCurso.fechaApertura = fechaApertura;
		inscripcionCurso.fechaCierre = fechaCierre;
		inscripcionCurso.curso.estado = CursoDto.CURSO_ABIERTO;
		inscripcionCurso.curso.plazasDisponibles = plazasDisponibles;
	}
}
