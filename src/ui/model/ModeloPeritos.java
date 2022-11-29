package ui.model;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import persistence.colegiado.ColegiadoDto;

public class ModeloPeritos {
	public static final String HEADER_COLUMN1 = "NOMBRE";
	public static final String HEADER_COLUMN2 = "APELLIDOS";
	public static final String HEADER_COLUMN3 = "DNI";
	public static final String HEADER_COLUMN4 = "TELEFONO";
	public static final String HEADER_COLUMN5 = "POSICION";
	
	
	private List<ColegiadoDto> peritos;

	public ModeloPeritos(List<ColegiadoDto> peritos) {
		this.peritos = peritos;
	}
	
	
	public TableModel getPeritoModel(){

		// Listado de cursos actualmente planificados

		DefaultTableModel model = new DefaultTableModel();

		if (peritos.size() == 0) {
			model.addColumn("");
			model.addRow(new Object[] { "NO HAY PERITOS DISPONIBLES ACTUALMENTE" });

		} else {

			model.addColumn(HEADER_COLUMN1);
			model.addColumn(HEADER_COLUMN2);
			model.addColumn(HEADER_COLUMN3);
			model.addColumn(HEADER_COLUMN4);
			model.addColumn(HEADER_COLUMN5);

			
			for (ColegiadoDto p : peritos) {
				model.addRow(
						new Object[] {p.nombre, p.apellidos, p.DNI, p.telefono, p.posicionPerito});
			}
		}

		return model;
	}
}
