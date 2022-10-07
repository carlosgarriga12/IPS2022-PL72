package business.inscripcion;

import java.sql.SQLException;
import java.time.LocalDate;

import business.BusinessException;
import business.curso.Curso;
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
			final LocalDate fechaCierre, final String plazasDisponibles) throws BusinessException, SQLException {

		int plazas = 0;

		try {
			plazas = Integer.parseInt(plazasDisponibles);

		} catch (NumberFormatException e) {
			throw new BusinessException("Por favor, introduzca un número válido para las plazas disponibles");
		}

		if (curso == null) {
			throw new BusinessException("Por favor, seleccione un curso de la lista.");

		} else if (DateUtils.checkDateIsAfter(fechaCierre, fechaApertura)
				|| DateUtils.checkDateIsBefore(curso.fechaInicio, fechaApertura)) {

			throw new BusinessException(
					"Corriga el periodo de inscripción. La fecha de impartición del curso seleccionado está programada para: "
							+ curso.fechaInicio);

		} else if (Curso.isCourseOpened(curso)) {
			throw new BusinessException(
					"Por favor, actualice el listado de cursos en el botón. El curso seleccionado ya tiene abiertas inscripciones.");

		} else if (plazas <= 0) {
			throw new BusinessException("Por favor, introduzca un número válido para las plazas disponibles");

		}

		InscripcionCursoFormacionDto inscripcionCurso = new InscripcionCursoFormacionDto();

		inscripcionCurso.curso = curso;
		inscripcionCurso.fechaApertura = fechaApertura;
		inscripcionCurso.fechaCierre = fechaCierre;

		InscripcionCursoFormationCRUD.addNewInscripcion(inscripcionCurso);

		curso.plazasDisponibles = plazas;
		curso.estado = CursoDto.CURSO_ABIERTO;

		CursoCRUD.abrirCurso(curso);
	}
}
