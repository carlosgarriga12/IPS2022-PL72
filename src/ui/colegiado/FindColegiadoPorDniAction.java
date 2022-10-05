package ui.colegiado;

import business.BusinessException;
import business.BusinessFactory;
import business.util.Console;
import business.util.Printer;

public class FindColegiadoPorDniAction {
	
	public void execute() throws BusinessException {

		Console.println("\nColegiado \n");
		
		String dni = Console.readString("Inserte el dni");
		
		Printer.printColegiado(BusinessFactory.forColegiadoService().findColegiadoPorDni(dni));

	}

}
