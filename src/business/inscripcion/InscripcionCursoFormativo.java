package business.inscripcion;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import business.BusinessException;
import business.curso.Curso;
import business.util.DateUtils;
import persistence.curso.CursoCRUD;
import persistence.curso.CursoDto;
import persistence.inscripcionCursoFormacion.InscripcionCursoFormacionCRUD;

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
	public static void abrirCursoFormacion(final CursoDto curso, CursoDto cursoSeleccionado) throws BusinessException, SQLException {

		checkCamposAperturaCurso(curso, cursoSeleccionado);

		curso.plazasDisponibles = curso.plazasDisponibles;

		CursoCRUD.abrirCurso(curso);
	}

	/**
	 * Comprueba si los valores del curso pasado son válidos.
	 * 
	 * @param curso
	 * @throws BusinessException Si alguno de los atributos del curso indicado no es
	 *                           valido.
	 */
	public static void checkCamposAperturaCurso(final CursoDto curso, CursoDto cursoSeleccionado) throws BusinessException {
		if (curso == null || cursoSeleccionado == null) {
			throw new BusinessException("Por favor, seleccione un curso de la lista.");

			// Fecha inscripciones anterior a fecha actual
		} else if (DateUtils.checkDateIsBefore(curso.fechaApertura, LocalDate.now())
				|| DateUtils.checkDateIsBefore(curso.fechaCierre, LocalDate.now())) {

			throw new BusinessException(
					"Por favor, corriga el periodo de inscripción. La fecha de inscripción ha de ser a partir del día de hoy");

			
			
			// Si fecha cierre < fecha apertura
			// ó fecha inicio < fecha apertura
		} else if (DateUtils.checkDateIsAfter(curso.fechaApertura, curso.fechaInicio)) {
			throw new BusinessException(
					"Por favor, corriga el periodo de inscripción. la fecha de apertura de la inscripcion no puede ser posterior a la de inicio del curso");
		} else if (DateUtils.checkDateIsAfter(curso.fechaCierre, curso.fechaInicio)) {
			throw new BusinessException(
					"Por favor, corriga el periodo de inscripción. La fecha de de cierre de la inscripcion no puede ser posterior a la fecha de inicio del curso");
		}

		else if (DateUtils.checkDateIsBefore(curso.fechaCierre, curso.fechaApertura)
				|| DateUtils.checkDateIsBefore(curso.fechaInicio, curso.fechaApertura)) {

			throw new BusinessException(
					"Por favor, corriga el periodo de inscripción. La fecha de impartición del curso seleccionado está programada para: "
							+ curso.fechaInicio);

			// Si las fechas de apertura y cierre son el mismo dia
		} else if (curso.fechaApertura.isEqual(curso.fechaCierre)) {
			throw new BusinessException(
					"Por favor, corriga el periodo de inscripción. El periodo mínimo de inscripciones ha de ser de un día natural");
		}
		// Si el curso seleccionado ya está abierto
		else if (Curso.isCourseOpened(curso)) {
			throw new BusinessException(
					"Por favor, actualice el listado de cursos en el botón. El curso seleccionado ya tiene abiertas inscripciones.");

			// Si no se ha seleccionado un número de plazas
		} else if (curso.plazasDisponibles <= 0) {
			throw new BusinessException("Por favor, introduzca un número válido para las plazas disponibles");

		}
	}

	public static List<CursoDto> getCursosAbiertos() {
		return InscripcionCursoFormacionCRUD.listaCursosAbiertos();

	}

	public static boolean hayPlazasLibres(CursoDto curso) throws BusinessException {
		return InscripcionCursoFormacionCRUD.hayPlazasLibres(curso);
	}

}