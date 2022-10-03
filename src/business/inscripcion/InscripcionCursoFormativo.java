package business.inscripcion;

import java.time.LocalDate;

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
	 */
	public void abrirCursoFormacion(final InscripcionCursoFormacionDto inscripcionCurso, final LocalDate fechaApertura,
			final LocalDate fechaCierre, final int plazasDisponibles) {

		// Establecer fecha inicio
		inscripcionCurso.setFechaApertura(fechaApertura);

		// Establecer fecha fin
		inscripcionCurso.setFechaCierre(fechaCierre);

		// Cambiar estado del curso a abierto
		inscripcionCurso.getCurso().setEstado(CursoDto.CURSO_ABIERTO);

		// Establecer el número de plazas del curso
		inscripcionCurso.getCurso().setPlazasDisponibles(plazasDisponibles);
	}
}
