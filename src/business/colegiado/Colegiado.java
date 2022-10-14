package business.colegiado;

import business.BusinessException;
import persistence.colegiado.ColegiadoCrud;
import persistence.colegiado.ColegiadoDto;

public class Colegiado {

	public static ColegiadoDto InicioSesion(String DNI) throws BusinessException {
		try {

			return ColegiadoCrud.findColegiadoDni(DNI);
		}

		catch (BusinessException e) {
			if (e.getMessage().equals("java.sql.SQLException: ResultSet closed")) {
				return null;
			} else {
				throw e;
			}
		}
	}

}
