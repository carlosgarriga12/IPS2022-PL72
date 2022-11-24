package ui.model;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import persistence.colegiado.ColegiadoDto;

public class ModeloPeritosDisponiblesParaVisado {
	public static final String HEADER_COLUMN1 = "Nombre";
	public static final String HEADER_COLUMN2 = "Apellidos";
	public static final String HEADER_COLUMN3 = "DNI";
	
	private List<ColegiadoDto> peritos;
	
	public ModeloPeritosDisponiblesParaVisado(List<ColegiadoDto> peritos) {
		this.peritos = peritos;
	}
	
	public TableModel getSolicitudModel() {
		DefaultTableModel model = new DefaultTableModel();
		
		if (peritos.isEmpty()) {
			model.addColumn("");
			model.addRow(new Object[] { "NO HAY PERITOS DISPONIBLES PARA VISADOS" });
		} else {
			model.addColumn(HEADER_COLUMN1);
			model.addColumn(HEADER_COLUMN2);
			model.addColumn(HEADER_COLUMN3);
			
			for(ColegiadoDto c : peritos) {
				model.addRow(new Object[] {c.nombre, c.apellidos, c.DNI});
			}
		}
		
		return model;
	}

}
