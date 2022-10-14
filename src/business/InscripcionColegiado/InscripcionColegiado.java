package business.InscripcionColegiado;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
	
	public static void pagarCursoColegiado(ColegiadoDto colegiado, CursoDto cursoSeleccionado, String estado, String formaDePago) throws BusinessException {
		// paga un curso, establece el m�todo y el estado del pago
		InscripcionColegiadoCRUD.pagarCursoColegiado(colegiado, cursoSeleccionado, estado, formaDePago);
	}
	
	public static void comprobarFecha(String fechaPreInscripcion) throws BusinessException {
		// comprueba que no han pasado dos dias desde la fecha actual
		LocalDate fecha = LocalDate.parse(fechaPreInscripcion);
	    if (ChronoUnit.DAYS.between(LocalDate.now(), fecha) > 2) {
	    	throw new BusinessException("No puede inscribirse a un curso en el que han pasado más de dos días desde la fecha de pre-inscripcion");
	    }
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
