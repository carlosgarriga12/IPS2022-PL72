package persistence.curso;

import java.time.LocalDate;

/**
 * Curso de formación.
 * 
 * @version v1.0.0
 *
 */
public class CursoDto {

	public static final String CURSO_ABIERTO = "ABIERTO";
	public static final String CURSO_CERRADO = "CERRADO";
	public static final String CURSO_PLANIFICADO = "PLANIFICADO";

	public int codigoCurso;
	public String titulo;
	public LocalDate fechaInicio;
	public int plazasDisponibles;
	public double precio;
	public String estado = CURSO_PLANIFICADO;
	public LocalDate fechaCierre;
	public LocalDate fechaApertura;
	public String CantidadPagarColectivo;
	
	public String toString() {
		return "titulo= " +titulo+ ", fechaInicio= "+fechaInicio+", precio "+precio;
	}
}
