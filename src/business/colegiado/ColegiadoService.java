package business.colegiado;

import java.time.LocalDate;

import business.BusinessException;

public interface ColegiadoService {
	
	/**
	 * Añade un nuevo colegiado al sistema con los datos del dto
	 * 
	 * @param colegiado que contiene la información
	 * @return colegiado con todos sus datos 
	 * @throws IllegalArgumentException cuando es null or cualquiera
	 * de sus campos es null o vacío (strings) o el telefono no tiene 9 números o es negativo, o titulacion si no es 0, 1 o 2,
	 * o numero de tarjeta si es negativo o no tiene 16 números, año debe ser > 0, y dni tenga 9 caracteres
	 * BusinessException cuando ya existe ese colegiado (mismo dni)
	 */
	ColegiadoDto addColegiado(ColegiadoDto colegiado) throws BusinessException;
	
	/**
	 * Busca los colegiados de un año
	 * @param año, debe ser mayor que 0
	 * @return lista de los colegiados del sistema por año
	 * 
	 * no @throws BusinessException y lanza una IllegalArgumentException si el DNI es vacío, null o no tiene 9 caracteres
	 */
	ColegiadoDto findColegiadoPorDni(String DNI) throws BusinessException;

	
	public class ColegiadoDto {
		public String DNI;					//55555555H
		public String nombre;				//Enrique
		public String apellidos;			//Fern�ndez	
		public String poblacion;			//Oviedo	
		public int telefono;				//666666666
		public int titulacion;				//0 (Sin titulaci�n),
											//1 (Titulaci�n en ingenier�a inform�tica) ,
											//2 (Otras titulaciones)
		public String centro;				//Campus de los catalanes
		public int annio;						//2022
		public int numeroTarjeta;			//5555555555555555
		public LocalDate fechaSolicitud;	//03/10/2022
		public String estado;				// PENDIENTE
		public String numeroColegiado;		//2022-0001
	}

}
