package business.InscripcionColegiado;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import business.BusinessException;
import persistence.InscripcionColegiado.InscripcionColegiadoCRUD;
import persistence.colegiado.ColegiadoCrud;
import persistence.colegiado.ColegiadoDto;
import persistence.curso.CursoCRUD;
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
		
	}

