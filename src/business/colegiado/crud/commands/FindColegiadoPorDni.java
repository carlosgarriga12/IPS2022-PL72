package business.colegiado.crud.commands;


import business.BusinessException;
import business.util.Argument;
import persistence.colegiado.ColegiadoCrud;
import persistence.colegiado.ColegiadoDto;

public class FindColegiadoPorDni {
	
	
	private String dni;

	public FindColegiadoPorDni(String dni) {
		Argument.isNotEmpty(dni);
		Argument.longitudNueve(dni);
		
		this.dni=dni;
	}

	public ColegiadoDto execute() throws BusinessException {
		return ColegiadoCrud.findColegiadoDni(dni);
	}

}
