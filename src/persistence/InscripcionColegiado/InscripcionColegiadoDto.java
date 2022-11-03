package persistence.InscripcionColegiado;

import java.time.LocalDate;

import persistence.colegiado.ColegiadoDto;
import persistence.curso.CursoDto;

public class InscripcionColegiadoDto {
	public ColegiadoDto colegiado;
	public CursoDto curso;
	public LocalDate fechaSolicitud;
	public double cantidadPagada;
	public String estado; // PAGADO (tarjeta) o PENDIENTE (transferencia)
	public String formaDePago; // TARJETA O TRANSFERENCIA 
	public String codigoTransferencia;
	public LocalDate fechaTransferencia;
	public LocalDate fechaPreinscripcion;
	public String incidencias;
	public double precio;
	public String devolver;
	
	@Override
	public String toString() {
		if (fechaTransferencia==null) {
			return colegiado.DNI + ";"+ colegiado.nombre + ";" +  colegiado.apellidos + ";" + cantidadPagada + ";" + "NO REALIZADA"
					+ ";" + "NO REALIZADA"  + "\n";
		} return colegiado.DNI + ";"+ colegiado.nombre + ";" +  colegiado.apellidos + ";" + cantidadPagada + ";" + fechaTransferencia.toString()
				+ ";" + codigoTransferencia  + "\n";
		
	}

}