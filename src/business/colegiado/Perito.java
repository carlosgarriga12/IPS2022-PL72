package business.colegiado;

import java.util.ArrayList;

import persistence.colegiado.ColegiadoDto;
import persistence.colegiado.PeritoCrud;

public class Perito {
	public static ArrayList<ColegiadoDto> listarPeritosOrdenados(){
		return PeritoCrud.listarPeritosOrdenados();
	}
}
