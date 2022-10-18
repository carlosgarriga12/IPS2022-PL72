package persistence.InscripcionColegiado;

import java.time.LocalDate;

import persistence.colegiado.ColegiadoDto;
import persistence.curso.CursoDto;

public class InscripcionColegiadoDto {
	public ColegiadoDto colegiado;
	public CursoDto curso;
	public LocalDate fechaSolicitud;
	public double cantidadPagar;
	public String estado;

}
