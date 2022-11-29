package persistence.SolicitudServicios;

import java.time.LocalDate;

public class SolicitudServiciosDto {
	
	public static final String CANCELADA = "Cancelada";
	public static final String ASIGNADA = "Asignada";
	public static final String FINALIZADA = "Finalizada";
	public static final String  SIN_ASIGNAR = "SinAsignar";
	
	public String DNI;
	public String CorreoElectronico;
	public String Descripcion;
	public int Urgente;
	public int id;
	public String peritoDNI;
	public String estado;
	public int AgeSolicitud;
	public LocalDate fechaCancelacion;
}
