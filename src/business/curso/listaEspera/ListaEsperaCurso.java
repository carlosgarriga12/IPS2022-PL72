package business.curso.listaEspera;

import java.util.List;
import java.util.Optional;

import business.BusinessException;
import persistence.InscripcionColegiado.listaEsperaInscripcionCurso.ListaEsperaInscripcionCursoCrud;
import persistence.InscripcionColegiado.listaEsperaInscripcionCurso.ListaEsperaInscripcionCursoDto;

/**
 * Lista de espera de un curso.
 * <p>
 * Un curso tiene lista de espera si el número de plazas está completo. Los
 * usuario inscritos en la lista de espera no podrán salir ni se contemplarán
 * posibles actualizaciones automáticas con el curso al que pertenezca la lista.
 * 
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class ListaEsperaCurso {

	/**
	 * Apunta a la lista de espera del curso al colegiado indicado.
	 * 
	 * @since HU. 19733
	 * @param dniUsuario DNI del usuario a apuntar a la lista de espera.
	 * @throws BusinessException 
	 */
	public static void apuntarListaEspera(final String dniUsuario, final int codigoCurso) throws BusinessException {
		Optional<ListaEsperaInscripcionCursoDto> colegiadoListaEspera = ListaEsperaInscripcionCursoCrud
				.findByDni(dniUsuario, codigoCurso);

		if (colegiadoListaEspera.isPresent()) {
			throw new BusinessException("El usuario ya se encuentra apuntado en la lista de espera del curso.");
		}

		ListaEsperaInscripcionCursoCrud.addColegiadoListaEsperaCursoSeleccionado(dniUsuario, codigoCurso);
	}

	/**
	 * Listado de usuarios apuntados a la lista de espera del curso indicado.
	 * 
	 * @since HU. 19733
	 * @param idCurso Id del curso a mostrar su lista de espera, en caso de que la
	 *                tenga.
	 * @return Lista de espera del curso.
	 * @throws BusinessException
	 */
	public static List<ListaEsperaInscripcionCursoDto> findByCursoId(Integer idCurso) throws BusinessException {
		return ListaEsperaInscripcionCursoCrud.findByCursoId(idCurso);
	}
}
