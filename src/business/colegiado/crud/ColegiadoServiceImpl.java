
package business.colegiado.crud;

import java.util.List;

import business.BusinessException;
import business.colegiado.ColegiadoService;
import business.colegiado.crud.commands.AddColegiado;
import business.colegiado.crud.commands.FindAllSolicitudesColegiado;
import business.colegiado.crud.commands.FindColegiadoPorDni;
import business.colegiado.crud.commands.FindTitulacionColegiadoByDni;
import business.colegiado.crud.commands.UpdateNumColegiado;
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

	@Override
	public void updateNumColegiado(ColegiadoDto colegiado) throws BusinessException {
		new UpdateNumColegiado(colegiado).execute();
	}

	@Override
	public List<ColegiadoDto> findAllSolicitudesColegiado() throws BusinessException {
		return new FindAllSolicitudesColegiado().execute();
	}

}
