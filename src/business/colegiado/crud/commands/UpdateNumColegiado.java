package business.colegiado.crud.commands;

import business.BusinessException;
import business.util.GeneradorNumeroColegiado;
import persistence.colegiado.ColegiadoCrud;
import persistence.colegiado.ColegiadoDto;

/**
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class UpdateNumColegiado {

	private String dni;

	public UpdateNumColegiado(final String dni) {
		this.dni = dni;
	}

	public String execute() throws BusinessException {

		ColegiadoDto colegiado = new ColegiadoDto();
		colegiado.DNI = dni;
		colegiado.numeroColegiado = GeneradorNumeroColegiado.generateNumber();

		ColegiadoCrud.updateNumColegiado(colegiado);

		return colegiado.numeroColegiado;
	}
}