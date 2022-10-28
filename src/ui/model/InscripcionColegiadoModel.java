package ui.model;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import business.BusinessException;

public class InscripcionColegiadoModel {
	
	public static final String HEADER_COLUMN5 = "FECHA TRANSFERENCIA";
	public static final String HEADER_COLUMN1 = "DNI";
	public static final String HEADER_COLUMN2 = "NOMBRE";
	public static final String HEADER_COLUMN3 = "APELLIDOS";
	public static final String HEADER_COLUMN4 = "CANTIDAD ABONADA";
	public static final String HEADER_COLUMN6 = "CODIGO TRANSFERENCIA";

	private List<List<String>> inscripciones;

	public InscripcionColegiadoModel(List<List<String>> inscripciones) {
		this.inscripciones = inscripciones;
	}

	public TableModel getCursoModel() throws BusinessException {
		// Listado de las incripciones
		DefaultTableModel model = new DefaultTableModel();

		if (inscripciones.size() == 0) {
			model.addColumn("");
			model.addRow(new Object[] { "NO HAY INSCRIPCIONES REALIZADAS POR TRANSFERENCIA PENDIENTES" });

		} else {
			model.addColumn(HEADER_COLUMN1);
			model.addColumn(HEADER_COLUMN2);
			model.addColumn(HEADER_COLUMN3);
			model.addColumn(HEADER_COLUMN4);
			model.addColumn(HEADER_COLUMN5);
			model.addColumn(HEADER_COLUMN6);


			for (List<String> c : inscripciones) {
					model.addRow(new Object[] { c.get(0), c.get(1), c.get(2), c.get(3), c.get(4), c.get(5) });
			}		

		}

		return model;
	}

}
