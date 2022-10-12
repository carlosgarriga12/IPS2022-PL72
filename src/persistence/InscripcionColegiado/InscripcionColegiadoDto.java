package persistence.InscripcionColegiado;

import java.time.LocalDate;

import persistence.colegiado.ColegiadoDto;
import persistence.inscripcionCursoFormacion.InscripcionCursoFormacionDto;

public class InscripcionColegiadoDto {
	public ColegiadoDto colegiado;
	public InscripcionCursoFormacionDto inscripcion;
	public LocalDate fechaSolicitud;
	public double cantidadPagar;
	public String estado; //Preinscripcion = 0, Inscripcion = 1
	

}
