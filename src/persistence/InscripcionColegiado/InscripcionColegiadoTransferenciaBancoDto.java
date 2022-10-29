package persistence.InscripcionColegiado;

public class InscripcionColegiadoTransferenciaBancoDto {
	public String dni;
	public String nombre;
	public String apellidos;
	public double cantidadPagada;
	public String fechaTransferencia;
	public String codigoTransferencia;
	
	@Override
	public String toString() {
		return dni + ";"+ nombre + ";" +  apellidos + ";" + cantidadPagada + ";" + fechaTransferencia + ";" + codigoTransferencia + "\n";
	}
	
	
}


