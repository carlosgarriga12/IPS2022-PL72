package business.curso;

import java.util.List;

import persistence.curso.CursoCRUD;
import persistence.curso.CursoDto;

/**
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class Curso {

	/**
	 * Listado de los curso actualmente planificados
	 * 
	 * @return
	 */
	public static List<CursoDto> listarCursosPlanificados() {
		return CursoCRUD.listarCursosActualmentePlanificados();
	}
}
