package persistence.InscripcionColegiado;

public class InscripcionColegiadoTransferenciaBancoDto {
	public String dni;
	public String nombre;
	public String apellidos;
	public double cantidad;
	public String fecha;
	public String codigo;
	
	@Override
	public String toString() {
		return dni + ";"+ nombre + ";" +  apellidos + ";" + cantidad + ";" + fecha + ";" + codigo + "\n";
	}
	
	
}


