package business;

import business.colegiado.ColegiadoService;
import business.colegiado.crud.ColegiadoServiceImpl;

public class BusinessFactory {
	public static ColegiadoService forColegiadoService() {
		return new ColegiadoServiceImpl();
	}

}
