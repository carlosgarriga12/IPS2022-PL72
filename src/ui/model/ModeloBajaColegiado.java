package ui.model;


import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import persistence.colegiado.ColegiadoCrud;
import persistence.colegiado.ColegiadoDto;

public class ModeloBajaColegiado {
	public static final String HEADER_COLUMN1 = "NOMBRE";
	public static final String HEADER_COLUMN2 = "APELLIDOS";
	public static final String HEADER_COLUMN3 = "CANTIDAD DEUDADA";
	
	private ColegiadoDto colegiado;

	public ModeloBajaColegiado(ColegiadoDto colegiado) {
		this.colegiado = colegiado;
	}
	
	
	public TableModel getSolicitudModel(){

		// Listado de cursos actualmente planificados

		DefaultTableModel model = new DefaultTableModel();

		if (colegiado == null) {
			model.addColumn("");
			model.addRow(new Object[] { "NO HAY COLEGIADOS" });

		} else {
			model.addColumn(HEADER_COLUMN1);
			model.addColumn(HEADER_COLUMN2);
			model.addColumn(HEADER_COLUMN3);
			
			model.addRow(
					new Object[] {colegiado.nombre,
							colegiado.apellidos,
							ColegiadoCrud.getCantidadDeudada(colegiado.DNI)});
		}

		return model;
	}
}
