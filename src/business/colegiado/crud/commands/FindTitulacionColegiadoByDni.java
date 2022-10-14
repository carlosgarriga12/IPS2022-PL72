package business.colegiado.crud.commands;

import business.BusinessException;
import business.util.Argument;
import persistence.colegiado.ColegiadoCrud;

/**
 * 
 * @author Francisco Coya Abajo
 * @version v1.0.0
 *
 */
public class FindTitulacionColegiadoByDni {

	private String dni;

	public FindTitulacionColegiadoByDni(final String dni) {
		Argument.isNotNull(dni);
		Argument.isNotEmpty(dni);

		this.dni = dni;
	}

	public int execute() throws BusinessException {
		return ColegiadoCrud.findTitulacionColegiadoByDni(dni);
	}
}
