package business.colegiado.crud.commands;

import java.time.LocalDate;

import business.util.Argument;
import persistence.colegiado.ColegiadoCrud;
import persistence.colegiado.ColegiadoDto;

/**
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class UpdateNumColegiado {

	private ColegiadoDto colegiado;

	public UpdateNumColegiado(final ColegiadoDto colegiado) {
		Argument.isNotNull(colegiado);
		Argument.isNotEmpty(colegiado.DNI);
		Argument.isNotEmpty(colegiado.nombre);

		this.colegiado = colegiado;
	}

	public void execute() {
		int n = ColegiadoCrud.findAllColegiados().size() + 1;

		int currentYear = LocalDate.now().getYear();

		String defNumeroColegiado = String.valueOf(currentYear).concat(String.valueOf(n));

		colegiado.numeroColegiado = defNumeroColegiado;

		ColegiadoCrud.updateNumColegiado(colegiado);
	}
}
