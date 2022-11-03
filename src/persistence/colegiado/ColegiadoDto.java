package persistence.colegiado;

import java.time.LocalDate;

public class ColegiadoDto {
	public String DNI; // 55555555H
	public String nombre; // Enrique
	public String apellidos; // Fern�ndez
	public String poblacion; // Oviedo
	public int telefono; // 666666666
	public String titulacion; // Licenciado en Informática
	public String centro; // Campus de los catalanes
	public int annio; // 2022
	public String numeroCuenta; // ES6612344321
	public LocalDate fechaSolicitud; // 03/10/2022
	public String estado; // "PENDIENTE"
	public String numeroColegiado; // 2022-0001
	
	public enum Perito_estado {RENOVADO, SIN_RENOVAR};
	public String perito;
	public Integer posicionPerito;
	
}
