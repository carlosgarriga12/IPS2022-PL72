package business.InscripcionColegiado;

import business.BusinessException;
import persistence.InscripcionColegiado.InscripcionColegiadoCRUD;
import persistence.colegiado.ColegiadoCrud;
import persistence.colegiado.ColegiadoDto;
import persistence.curso.CursoDto;
import persistence.jdbc.PersistenceException;

public class InscripcionColegiado {
	
	
	public static void InscribirColegiado(CursoDto curso, ColegiadoDto colegiado) throws BusinessException {
		//inscribir a un socio en un curso
		InscripcionColegiadoCRUD.InscribirColegiado(curso, colegiado);
	}

	public static boolean isInscrito(ColegiadoDto colegiado, CursoDto cursoSeleccionado) throws BusinessException {
		// TODO Auto-generated method stub
		return InscripcionColegiadoCRUD.isInscrito(colegiado, cursoSeleccionado);
	}
	
	public static String findFechaPreinscripcion(ColegiadoDto colegiado, CursoDto cursoSeleccionado) throws BusinessException {
		// devuelve la fecha de la preinscripcion
		return InscripcionColegiadoCRUD.findFechaPreinscripcion(colegiado, cursoSeleccionado);
	}
	
	public static void pagarCursoColegiado(ColegiadoDto colegiado, CursoDto cursoSeleccionado, String estado, String formaDePago) throws PersistenceException {
		// paga un curso, establece el m�todo y el estado del pago
		InscripcionColegiadoCRUD.pagarCursoColegiado(colegiado, cursoSeleccionado, estado, formaDePago);
	}
	
	public static ColegiadoDto InicioSesion(String DNI) throws BusinessException {
		try {
			return ColegiadoCrud.findColegiadoDni(DNI);}
		catch(PersistenceException e){
			if(e.getMessage().equals("java.sql.SQLException: ResultSet closed")) {
				return null;
			}
			else {
				throw e;
			}
		} 
	}
	
	
}
