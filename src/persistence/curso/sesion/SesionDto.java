package persistence.curso.sesion;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SesionDto {
	
	public int idCurso;
	public LocalDateTime horaInicio;
	public LocalDateTime horaFin;
	
	@Override
	public String toString() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
		String aux = "";
		aux += horaInicio.toLocalDate().toString() + " ";
		
		aux += LocalTime.parse(horaInicio.toLocalTime().toString(), format);
		aux += "-";
		aux += LocalTime.parse(horaFin.toLocalTime().toString(), format);
		
		return aux;
	}
}
