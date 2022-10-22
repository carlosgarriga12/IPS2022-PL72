package persistence.inscripcionCursoFormacion;

import java.time.LocalDate;

import persistence.curso.CursoDto;

/**
 * Inscripcion a un curso de formación.
 * 
 * @author Francisco Coya Abajo
 * @version v1.0.0
 *
 */
public class InscripcionCursoFormacionDto {

	public CursoDto curso;
	public LocalDate fechaApertura;
	public LocalDate fechaCierre;

}
