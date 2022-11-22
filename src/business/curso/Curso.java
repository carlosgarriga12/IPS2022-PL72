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
	public static List<CursoDto> listarCursosPlanificados() throws BusinessException {
		return CursoCRUD.listarCursosActualmentePlanificados();
	}

	/**
	 * Listado de todos los cursos actualmente disponibles en el COIIPA
	 * (PLANIFICADOS y ABIERTOS).
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public static List<CursoDto> listarTodosLosCursos() throws BusinessException {
		return CursoCRUD.listTodosLosCursos();
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

	// TODO: Duplicado - eliminar
	public static List<CursoDto> listaCursosAbiertosYCerrados() {
		return CursoCRUD.listaCursos();
	}

	public static void add(CursoDto curso) {
		CursoCRUD.add(curso);
	}

	public static List<CursoDto> listarCursosAbiertos() throws BusinessException {
		return CursoCRUD.listarCursosActualmenteAbiertos();
	}
	
	public static void cancelarCursoCOIIPA(CursoDto cursoSeleccionado) {
		CursoCRUD.cancelarCursoCOIIPA(cursoSeleccionado.codigoCurso);
	}
	
	public static List<CursoDto> listarCursosAbiertosPlanificados() throws BusinessException {
		return CursoCRUD.listarCursosAbiertosPlanificados();
	}
	
	public static List<CursoDto> listarCursosIsInscrito(String dni) throws BusinessException {
		return CursoCRUD.listarCursosIsInscrito(dni);
	}

}
