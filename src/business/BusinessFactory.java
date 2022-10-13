package business;

import business.colegiado.Colegiado;

/**
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */

public class BusinessFactory {
	public static Colegiado forColegiadoService() {
		return new Colegiado();
	}

}
