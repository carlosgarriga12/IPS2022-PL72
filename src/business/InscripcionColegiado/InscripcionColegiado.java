package business.InscripcionColegiado;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;


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
	
	public static String findFechaPreinscripcion(String dni, int cursoSeleccionado) throws BusinessException {
		// devuelve la fecha de la preinscripcion
		String fecha = InscripcionColegiadoCRUD.findFechaPreinscripcion(dni, cursoSeleccionado);
		if (fecha == null) {
			throw new BusinessException("No puede inscribirse a un curso en el que no se ha preinscrito");
		} 
		return fecha;
	}
	
	public static void pagarCursoColegiado(String dni, int curso, String estado, String formaDePago) throws BusinessException {
		// paga un curso, establece el m�todo y el estado del pago
		InscripcionColegiadoCRUD.pagarCursoColegiado(dni, curso, estado, formaDePago);
	}
	
	public static void comprobarFecha(String fechaPreInscripcion) throws BusinessException {
		// comprueba que no han pasado dos dias desde la fecha actual
		LocalDate fecha = LocalDate.parse(fechaPreInscripcion);
	    if (ChronoUnit.DAYS.between(LocalDate.now(), fecha) > 2) {
	    	throw new BusinessException("No puede inscribirse a un curso en el que han pasado más de dos días desde la fecha de pre-inscripcion");
	    }
	}
	
	
	public static void EmitirJustificante(ColegiadoDto colegiado, CursoDto curso) throws BusinessException {
		String contenido = "Nombre: "+colegiado.nombre+" "+colegiado.apellidos+"  Número Colegiado: " + colegiado.numeroColegiado + "  Fecha Solicitud: "
				+ LocalDate.now().toString() + "  Cantidad a Abonar: " + curso.precio;
		
		JOptionPane.showMessageDialog(null, "<html><p align='center'>Justificante de Inscripcion:</p><p>"+contenido+"</p></html>");
	}
}

