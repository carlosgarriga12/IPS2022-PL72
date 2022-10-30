package business.InscripcionColegiado;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.swing.JOptionPane;

import business.BusinessException;
import persistence.Colegiado_Inscripcion.Colegiado_Inscripcion;
import persistence.InscripcionColegiado.InscripcionColegiadoCRUD;
import persistence.InscripcionColegiado.InscripcionColegiadoDto;
import persistence.colegiado.ColegiadoCrud;
import persistence.colegiado.ColegiadoDto;
import persistence.curso.CursoDto;
import ui.util.Ficheros;

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

	public static void emitirFicheroTransferenciaPorCurso(int cursoSeleccionado) throws BusinessException {
		List<InscripcionColegiadoDto> lista = InscripcionColegiadoCRUD.findInscripcionesPorCursoId(cursoSeleccionado);
		try {
			Ficheros.escribirFichero(lista, cursoSeleccionado);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<InscripcionColegiadoDto> leerFicheroTransferenciasPorCurso(int cursoSeleccionado) {
		return Ficheros.leerFichero(cursoSeleccionado);
	}
	
	public static void pagarBancoTransferencia(int curso) {
		List<InscripcionColegiadoDto> lista = InscripcionColegiadoCRUD.findInscripcionesPorCursoId(curso);
		for (int i=0; i < lista.size(); i++) {
			InscripcionColegiadoCRUD.pagarBanco(lista.get(i).colegiado.DNI, curso, lista.get(i).precio);
		}		
	}
	
	public static List<InscripcionColegiadoDto> obtenerTransferenciasProcesadas(int curso) {
		return InscripcionColegiadoCRUD.findInscripcionesPorCursoIdProcesadas(curso);
	}
	
	public static List<InscripcionColegiadoDto> obtenerTransferencias(int curso) {
		return InscripcionColegiadoCRUD.findInscripcionesPorCursoId(curso);
	}

	public static void procesarTransferencias(int codigoCurso) {
		List<InscripcionColegiadoDto> lista = InscripcionColegiadoCRUD.findInscripcionesPorCursoId(codigoCurso);
		int i=0;
		while (i < lista.size()) {
			InscripcionColegiadoDto elemento = lista.get(i);
			if (elemento.fechaTransferencia==null) {
				InscripcionColegiadoCRUD.procesarTransferencia("CANCELADO", "CUOTA NO PAGADA", codigoCurso, elemento.colegiado.DNI, "NADA");
				i++;
				break;
			}
			LocalDate fechaPreinscripcion = elemento.fechaPreinscripcion;
			LocalDate fechaTransferencia = elemento.fechaTransferencia;
			if (Period.between(fechaPreinscripcion, fechaTransferencia).getDays() > 2 || fechaTransferencia.isBefore(fechaPreinscripcion)) {
				InscripcionColegiadoCRUD.procesarTransferencia("CANCELADO", "PLAZO INVÁLIDO",
						 codigoCurso, elemento.colegiado.DNI, elemento.cantidadPagada + "€");
				i++;
				break;
			} else {
				if (elemento.cantidadPagada < elemento.precio) {
					InscripcionColegiadoCRUD.procesarTransferencia("CANCELADO", "CUOTA INFERIOR", codigoCurso, elemento.colegiado.DNI, elemento.cantidadPagada + "€" );
					i++;
					break;
				} else if (elemento.cantidadPagada==elemento.precio) {
					InscripcionColegiadoCRUD.procesarTransferencia("INSCRITO", "CUOTA CORRECTA",
							codigoCurso, elemento.colegiado.DNI, "NADA");
					i++;
					break;
				} else {
					InscripcionColegiadoCRUD.procesarTransferencia("INSCRITO", "CUOTA CORRECTA", codigoCurso, elemento.colegiado.DNI, (elemento.cantidadPagada-elemento.precio) + "€");
					i++;
					break;
				}
			}
		}
	}
	
	
		
}




