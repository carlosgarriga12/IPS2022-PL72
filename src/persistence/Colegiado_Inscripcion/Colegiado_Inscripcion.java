package persistence.Colegiado_Inscripcion;


import persistence.InscripcionColegiado.InscripcionColegiadoDto;
import persistence.colegiado.ColegiadoDto;

public class Colegiado_Inscripcion {
	private ColegiadoDto c;
	private InscripcionColegiadoDto I;
	
	public Colegiado_Inscripcion(ColegiadoDto c, InscripcionColegiadoDto I) {
		this.c = c;
		this.I = I;
	}

	public ColegiadoDto getC() {
		return c;
	}

	public InscripcionColegiadoDto getI() {
		return I;
	}
	
	public double cantidadPagada() {
		if(I.estado.equals("PAGADO")) {
			return I.cantidadPagada;
		}
		else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return c.apellidos+", "+c.nombre+ ". fecha inscripcion= "+I.fechaSolicitud+", Estado Inscripcion= "+I.estado+", Cantidad Pagada= "+cantidadPagada();
	}
	
	
	
	

}
