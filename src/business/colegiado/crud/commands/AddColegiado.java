 package business.colegiado.crud.commands;


import business.BusinessException;
import business.util.Argument;
import persistence.colegiado.ColegiadoCrud;
import persistence.colegiado.ColegiadoDto;

public class AddColegiado {
	
	private persistence.colegiado.ColegiadoDto colegiado; // se lo pasamos
	
	public AddColegiado(persistence.colegiado.ColegiadoDto colegiado) {
		Argument.isNotNull(colegiado);
		
		Argument.isNotNull(colegiado.DNI);
		Argument.isNotEmpty(colegiado.DNI);
		Argument.longitudNueve(colegiado.DNI);

		Argument.isNotNull(colegiado.nombre);
		Argument.isNotEmpty(colegiado.nombre);

		Argument.isNotNull(colegiado.apellidos);
		Argument.isNotEmpty(colegiado.apellidos);
		
		Argument.isNotNull(colegiado.poblacion);
		Argument.isNotEmpty(colegiado.poblacion);
		
		Argument.isNotNull(colegiado.centro);
		Argument.isNotEmpty(colegiado.centro);
		
		Argument.is012(colegiado.titulacion);
		
		Argument.isPositive(colegiado.annio);
		
		Argument.isPositive(colegiado.numeroTarjeta);

		Argument.isPositive(colegiado.telefono);

		Argument.longitudNueve(colegiado.telefono);
		
		Argument.menorQueMax(colegiado.annio);
		
		Argument.longitudCinco(colegiado.numeroTarjeta);
		
		this.colegiado=colegiado;
	}

	public ColegiadoDto execute() throws BusinessException {
		checkDniSinRepetir(colegiado.DNI);
		return ColegiadoCrud.addColegiado(colegiado);
	}
	
	public void checkDniSinRepetir(String dni) throws BusinessException {
		if (ColegiadoCrud.findColegiadoDni(dni)!=null) {
			throw new BusinessException("No se puede añadir colegiados con el mismo dni");
		}
	}


}
