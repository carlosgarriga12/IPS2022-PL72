package business.colegiado.crud.commands;

import java.util.List;

import persistence.colegiado.ColegiadoCrud;
import persistence.colegiado.ColegiadoDto;

public class FindAllSolicitudesColegiado {

	public List<ColegiadoDto> execute() {
		return ColegiadoCrud.findAllSolicitudesAltaColegiados();
	}
}
