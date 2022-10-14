package business.curso;

import java.util.List;

import business.BusinessException;
import business.util.Argument;
import persistence.curso.CursoCRUD;
import persistence.curso.CursoDto;

/**
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class Curso {

	private static CursoDto selectedCourse;

	/**
	 * Listado de los curso actualmente planificados
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public static List<CursoDto> listarCursosPlanificados() {
		return CursoCRUD.listarCursosActualmentePlanificados();
	}

	public static CursoDto getSelectedCourse() {
		return selectedCourse;
	}

	public static void setSelectedCourse(final CursoDto selectedCourse) throws IllegalArgumentException {
		Argument.isNotNull(selectedCourse);
		Curso.selectedCourse = selectedCourse;
	}

	public static boolean isCourseOpened(final CursoDto courseToCheck) {
		Argument.isNotNull(courseToCheck);
		return CursoCRUD.isCursoAbierto(courseToCheck);
	}
}
