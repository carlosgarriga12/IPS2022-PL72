package business.util;

import java.util.List;

import business.colegiado.ColegiadoService.ColegiadoDto;

public class Printer {

	public static void printColegiado(ColegiadoDto m) {
		Console.printf("\t%-36.36s %-9s %-10.10s %-25.25s \n", m.DNI, m.nombre, m.apellidos, m.poblacion);
	}

	public static void printColegiados(List<ColegiadoDto> list) {

		Console.printf("\t%-36s %-9s %-10s %-25s \n", "DNI", "Nombre", "Apellidos", "Poblacion");
		for (ColegiadoDto m : list)
			printColegiado(m);
	}
	
}