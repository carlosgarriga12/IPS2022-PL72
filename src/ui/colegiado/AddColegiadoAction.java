package ui.colegiado;

import business.BusinessException;
import business.BusinessFactory;
import business.util.Console;
import persistence.colegiado.ColegiadoDto;

public class AddColegiadoAction {
	
	public void execute() throws BusinessException {
		
		// Get info
		String dni = Console.readString("Dni"); 
		String name = Console.readString("Nombre"); 
		String surname = Console.readString("Apellidos");
		String poblacion = Console.readString("Población");
		int telefono = Console.readInt("Teléfono");
		int titulacion = Console.readInt("Titulacion");
		String centro = Console.readString("Centro");
		int annio = Console.readInt("Annio");
		int tarjeta = Console.readInt("Numero de tarjeta");

		// creamos un objeto para pasarselo a la lógica
		ColegiadoDto colegiado = new ColegiadoDto();
		// le pasamos los atributos
		colegiado.DNI=dni;
		colegiado.nombre=name;
		colegiado.apellidos=surname;
		colegiado.poblacion=poblacion;
		colegiado.telefono=telefono;
		colegiado.titulacion=titulacion;
		colegiado.centro=centro;
		colegiado.annio=annio;
		colegiado.numeroTarjeta=tarjeta;

		BusinessFactory.forColegiadoService().addColegiado(colegiado);
		
		// Print result
		Console.println("Colegiado added");
	}

}


