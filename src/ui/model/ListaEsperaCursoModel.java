package ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import persistence.InscripcionColegiado.listaEsperaInscripcionCurso.ListaEsperaInscripcionCursoDto;

/**
 * Modelo para las tablas que muestren la lista de espera de un curso.
 * 
 * @since HU. 19733
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class ListaEsperaCursoModel {

	public static final String HEADER_COLUMN1 = "DNI";
	public static final String HEADER_COLUMN2 = "NOMBRE";
	public static final String HEADER_COLUMN3 = "POSICION";

	private List<ListaEsperaInscripcionCursoDto> listaEspera = new ArrayList<>();

	public ListaEsperaCursoModel(List<ListaEsperaInscripcionCursoDto> listaEspera) {
		this.listaEspera = listaEspera;
	}

	public TableModel getListaEsperaSummaryModel() {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		if (listaEspera.isEmpty()) {
			model.addColumn("");
			model.addRow(new Object[] { "EL CURSO SELECCIONADO TIENE PLAZAS DISPONIBLES" });
		
		}else {
			
			model.addColumn(HEADER_COLUMN1);
			model.addColumn(HEADER_COLUMN2);
			model.addColumn(HEADER_COLUMN3);
			
			for(ListaEsperaInscripcionCursoDto item : listaEspera) {
				model.addRow(new Object[] { item.dniUsuario, item.nombreUsuario, item.posicionUsuarioLista });
			}
		}

		return model;
	}

}
