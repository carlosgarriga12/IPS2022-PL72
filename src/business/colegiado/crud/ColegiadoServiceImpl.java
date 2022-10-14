
package business.colegiado.crud;

import business.BusinessException;
import business.colegiado.ColegiadoService;
import business.colegiado.crud.commands.AddColegiado;
import business.colegiado.crud.commands.FindColegiadoPorDni;
import business.colegiado.crud.commands.FindTitulacionColegiadoByDni;
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

	@Override
	public int findTitulacionByDni(final String dni) throws BusinessException {
		return new FindTitulacionColegiadoByDni(dni).execute();
	}

}
