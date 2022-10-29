package business.InscripcionColegiado;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import business.BusinessException;
import persistence.Colegiado_Inscripcion.Colegiado_Inscripcion;
import persistence.InscripcionColegiado.InscripcionColegiadoCRUD;
import persistence.colegiado.ColegiadoCrud;
import persistence.colegiado.ColegiadoDto;
import persistence.curso.CursoDto;

public class InscripcionColegiado {
	
	
	public static void InscribirColegiado(CursoDto curso, ColegiadoDto colegiado) throws BusinessException {
		//inscribir a un socio en un curso
		InscripcionColegiadoCRUD.InscribirColegiado(curso, colegiado);
	}

	public static boolean isInscrito(ColegiadoDto colegiado, CursoDto cursoSeleccionado) throws BusinessException {
		// TODO Auto-generated method stub
		return InscripcionColegiadoCRUD.isInscrito(colegiado, cursoSeleccionado);
	}
	
	public static void EmitirJustificante(ColegiadoDto colegiado, CursoDto curso) throws BusinessException {
		String contenido = "Nombre: "+colegiado.nombre+" "+colegiado.apellidos+"  Número Colegiado: " + colegiado.numeroColegiado + "  Fecha Solicitud: "
				+ LocalDate.now().toString() + "  Cantidad a Abonar: " + curso.precio;
		
		JOptionPane.showMessageDialog(null, "<html><p align='center'>Justificante de Inscripcion:</p><p>"+contenido+"</p></html>");
	}
	
	public static ColegiadoDto InicioSesion(String Num) throws BusinessException {
		
		return ColegiadoCrud.findColegiadoNumColegiado(Num);
		}
	
	public static List<Colegiado_Inscripcion> Lista_Inscritos_Curso(CursoDto c){
		return InscripcionColegiadoCRUD.Lista_Inscritos_Curso(c);
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
		LocalDate ahora = LocalDate.now();
	    if (Duration.between(ahora.atStartOfDay(), fecha.atStartOfDay()).toDays() < -2 ||
	    		Duration.between(ahora.atStartOfDay(), fecha.atStartOfDay()).toDays() > 0) {
	    	throw new BusinessException("No puede inscribirse a un curso en el que han pasado más de dos días desde la fecha de pre-inscripcion (ni antes)");
	    }
	}
		
}




