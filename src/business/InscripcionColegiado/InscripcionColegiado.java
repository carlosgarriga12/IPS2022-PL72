package business.InscripcionColegiado;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	
	public static void pagarBancoTransferencia(String dni, int curso, double precio) {
		InscripcionColegiadoCRUD.pagarBanco(dni, curso, precio);
	}
	
	public static List<InscripcionColegiadoDto> obtenerTransferenciasProcesadas(int curso) {
		return InscripcionColegiadoCRUD.findInscripcionesPorCursoIdProcesadas(curso);
	}

	public static void procesarTransferencias(int codigoCurso) {
		List<InscripcionColegiadoDto> lista = InscripcionColegiadoCRUD.findInscripcionesPorCursoId(codigoCurso);
		int i=0;
		while (i < lista.size()) {
			InscripcionColegiadoDto elemento = lista.get(i);
			if (elemento.fechaTransferencia==null) {
				InscripcionColegiadoCRUD.procesarTransferencia("CANCELADO", "Su inscripción se ha CANCELADO, debido a que no ha pagado la cuota", codigoCurso, elemento.colegiado.DNI);
				i++;
				break;
			}
			LocalDate fechaPreinscripcion = elemento.fechaPreinscripcion;
			LocalDate fechaTransferencia = elemento.fechaTransferencia;
			if (Period.between(fechaPreinscripcion, fechaTransferencia).getDays() > 2) {
				InscripcionColegiadoCRUD.procesarTransferencia("CANCELADO", "Su inscripción se ha CANCELADO, debido a que no ha pagado la cuota en el perido establecido de 48 horas desde la preinscripción"
						+ "\nSe procederá a devolverle el importe que ha pagado por cuenta bancaria", codigoCurso, elemento.colegiado.DNI);
				i++;
				break;
			} else {
				if (elemento.cantidadPagada < elemento.precio) {
					InscripcionColegiadoCRUD.procesarTransferencia("CANCELADO", "Su inscripción se ha CANCELADO, debido a que no ha pagado la cuota en el perido establecido de 48 horas desde la preinscripción"
							+ "\nSe procederá a devolverle el importe que ha pagado por cuenta bancaria", codigoCurso, elemento.colegiado.DNI);
					i++;
					break;
				} else if (elemento.cantidadPagada==elemento.precio) {
					InscripcionColegiadoCRUD.procesarTransferencia("INSCRITO", "Su inscripción se ha INSCRITO en plazo de manera correcta",
							codigoCurso, elemento.colegiado.DNI);
					i++;
					break;
				} else {
					InscripcionColegiadoCRUD.procesarTransferencia("INSCRITO", "Su inscripción se ha INSCRITO en plazo de manera correcta"
							+ "\nSe procederá a devolverle el importe que ha pagado de más por cuenta bancaria", codigoCurso, elemento.colegiado.DNI);
					i++;
					break;
				}
			}
		}
	}
	
	
		
}




