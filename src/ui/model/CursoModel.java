package ui.model;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import business.BusinessException;
import business.curso.Curso;
import persistence.curso.CursoDto;

/**
 * Modelo de datos de cursos para poblar una tabla JTable de Swing.
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class CursoModel {

	public static final String HEADER_COLUMN6 = "CÓDIGO";
	public static final String HEADER_COLUMN1 = "TITULO";
	public static final String HEADER_COLUMN2 = "FECHA IMPARTICIÓN";
	public static final String HEADER_COLUMN3 = "PLAZAS";
	public static final String HEADER_COLUMN4 = "PRECIO";
	public static final String HEADER_COLUMN5 = "ESTADO";

	public static TableModel getCursoModel() throws BusinessException {

		// Listado de cursos actualmente planificados
		List<CursoDto> cursosPlanificados = Curso.listarCursosPlanificados();

		DefaultTableModel model = new DefaultTableModel();

		model.addColumn(HEADER_COLUMN6);
		model.addColumn(HEADER_COLUMN1);
		model.addColumn(HEADER_COLUMN2);
		model.addColumn(HEADER_COLUMN3);
		model.addColumn(HEADER_COLUMN4);
		model.addColumn(HEADER_COLUMN5);

		for (CursoDto c : cursosPlanificados) {
			model.addRow(
					new Object[] { c.codigoCurso, c.titulo, c.fechaInicio, c.plazasDisponibles, c.precio, c.estado });
		}

		return model;
	}
}
