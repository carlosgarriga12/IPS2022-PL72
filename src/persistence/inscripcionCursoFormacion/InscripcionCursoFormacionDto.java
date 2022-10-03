package persistence.inscripcionCursoFormacion;

import java.time.LocalDate;

import business.util.DateUtils;
import persistence.curso.CursoDto;

/**
 * Inscripcion a un curso de formación.
 * 
 * @author Francisco Coya Abajo
 * @version v1.0.0
 *
 */
public class InscripcionCursoFormacionDto {

	private CursoDto curso;
	private LocalDate fechaApertura;
	private LocalDate fechaCierre;

	public CursoDto getCurso() {
		return curso;
	}

	public LocalDate getFechaApertura() {
		return fechaApertura;
	}

	public LocalDate getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaApertura(final LocalDate fechaApertura) {
		if (DateUtils.checkDateIsBefore(fechaApertura, getFechaCierre())
				&& DateUtils.checkDateIsBefore(fechaApertura, getCurso().getFechaInicio())) {
			this.fechaApertura = fechaApertura;
		}
	}

	public void setFechaCierre(final LocalDate fechaCierre) {
		if (DateUtils.checkDateIsAfter(fechaCierre, getFechaCierre())
				&& DateUtils.checkDateIsAfter(fechaCierre, getCurso().getFechaInicio())) {
			this.fechaCierre = fechaCierre;
		}
	}

}
