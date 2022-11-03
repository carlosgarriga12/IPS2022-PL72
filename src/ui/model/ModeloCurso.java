package ui.model;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import persistence.curso.CursoDto;
import persistence.curso.Precio_Colectivos;

public class ModeloCurso {
	public static final String HEADER_COLUMN1 = "TITULO";
	public static final String HEADER_COLUMN2 = "FECHA INICIO";
	public static final String HEADER_COLUMN3 = "PLAZAS";
	public static final String HEADER_COLUMN4 = "PRECIO";
	
	
	private List<CursoDto> cursos;

	public ModeloCurso(List<CursoDto> cursos) {
		this.cursos = cursos;
	}
	
	
	public TableModel getCursoModel(boolean b, String TipoColectivo){

		// Listado de cursos actualmente planificados

		DefaultTableModel model = new DefaultTableModel();

		if (cursos.size() == 0) {
			model.addColumn("");
			model.addRow(new Object[] { "NO HAY CURSOS Abiertos ACTUALMENTE" });

		} else {

			model.addColumn(HEADER_COLUMN1);
			model.addColumn(HEADER_COLUMN2);
			model.addColumn(HEADER_COLUMN3);
			
			if(b) {
				model.addColumn(HEADER_COLUMN4);
				for (CursoDto c : cursos) {
					model.addRow(
							new Object[] {c.titulo, c.fechaInicio, c.plazasDisponibles, Precio_Colectivos.StringToPrecio_Colectivos(c.CantidadPagarColectivo).getPrecio(TipoColectivo) });
				}
			}
			else {
				for (CursoDto c : cursos) {
					model.addRow(
							new Object[] {c.titulo, c.fechaInicio, c.plazasDisponibles});
				}
			}
			


		}

		return model;
	}
}
