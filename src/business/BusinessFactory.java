package business;

import business.colegiado.ColegiadoService;
import business.colegiado.crud.ColegiadoServiceImpl;


/**
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */

public class BusinessFactory {
	public static ColegiadoService forColegiadoService() {
		return new ColegiadoServiceImpl();
	}

}
