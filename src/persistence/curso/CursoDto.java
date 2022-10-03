package persistence.curso;

import java.math.BigDecimal;
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
	public static final String CURSO_IMPARTIENDO = "IMPARTIENDO";

	public String codigoCurso;
	public String titulo;
	public LocalDate fechaInicio;
	public int plazasDisponibles;
	public BigDecimal precio;
	public String estado;

	public void setTitulo(final String titulo) {
		// TODO: Comprobar cadena valida
		this.titulo = titulo;
	}

	public void setFechaInicio(final LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setPlazasDisponibles(final int plazasDisponibles) {
		// TODO: Comprobar número válido
		this.plazasDisponibles = plazasDisponibles;
	}

	public void setPrecio(final BigDecimal precio) {
		// TODO: Comprobar precio válido
		this.precio = precio;
	}

	public void setEstado(final String estado) {
		if (isEstadoValido(estado)) {
			this.estado = estado;
		}
	}

	/**
	 * Comprueba que el estado del curso se encuentra entre los estados disponibles.
	 * 
	 * @param estadoCurso
	 * @return true si el curso pasado es válido y false en caso contrario.
	 */
	private boolean isEstadoValido(final String estadoCurso) {
		return estadoCurso.equals(CURSO_ABIERTO) || estadoCurso.equals(CURSO_CERRADO)
				|| estadoCurso.equals(CURSO_IMPARTIENDO);
	}

}
