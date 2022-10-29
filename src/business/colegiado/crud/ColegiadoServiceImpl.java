
package business.colegiado.crud;

import business.BusinessException;
import business.colegiado.ColegiadoService;
import business.colegiado.crud.commands.AddColegiado;
import business.colegiado.crud.commands.FindColegiadoPorDni;
import persistence.colegiado.ColegiadoDto;

public class ColegiadoServiceImpl implements ColegiadoService {

	@Override
	public ColegiadoDto addColegiado(ColegiadoDto colegiado) throws BusinessException {
		return new AddColegiado(colegiado).execute();
	}

	@Override
	public ColegiadoDto findColegiadoPorDni(String dni) throws BusinessException {
		return new FindColegiadoPorDni(dni).execute();
	}

}
