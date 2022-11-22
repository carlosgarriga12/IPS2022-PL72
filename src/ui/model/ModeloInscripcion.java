package ui.model;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import persistence.Colegiado_Inscripcion.Colegiado_Inscripcion;

public class ModeloInscripcion {
	public static final String HEADER_COLUMN1 = "APELLIDOS";
	public static final String HEADER_COLUMN2 = "NOMBRE";
	public static final String HEADER_COLUMN3 = "Fecha Inscripcion";
	public static final String HEADER_COLUMN4 = "Estado Inscripcion";
	public static final String HEADER_COLUMN5 = "Cantidad Pagada";
	
	
	private List<Colegiado_Inscripcion> inscripciones;

	public ModeloInscripcion(List<Colegiado_Inscripcion> inscripciones) {
		this.inscripciones = inscripciones;
	}
	
	
	public TableModel getCursoModel(){

		// Listado de cursos actualmente planificados

		DefaultTableModel model = new DefaultTableModel();

		if (inscripciones.size() == 0) {
			model.addColumn("");
			model.addRow(new Object[] { "No hay Inscripciones para el curso seleccionado" });

		} else {

			model.addColumn(HEADER_COLUMN1);
			model.addColumn(HEADER_COLUMN2);
			model.addColumn(HEADER_COLUMN3);
			model.addColumn(HEADER_COLUMN4);
			model.addColumn(HEADER_COLUMN5);
			
			for (Colegiado_Inscripcion i : inscripciones) {
				model.addRow(
						new Object[] {i.getC().apellidos, i.getC().nombre, i.getI().fechaSolicitud, i.getI().estado, i.cantidadPagada() });
			}

		}

		return model;
	}
}
