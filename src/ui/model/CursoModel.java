package ui.model;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import business.BusinessException;
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

	public static final String HEADER_COLUMN7 = "FECHAAPERTURA";
	public static final String HEADER_COLUMN8 = "FECHACIERRE";
	public static final String HEADER_COLUMN9 = "ESTADO";

	private List<CursoDto> cursos;

	public CursoModel(List<CursoDto> cursos) {
		this.cursos = cursos;
	}

	public TableModel getCursoModel(final boolean showAllFields) throws BusinessException {

		// Listado de cursos actualmente planificados

		DefaultTableModel model = new DefaultTableModel();

		if (cursos.size() == 0) {
			model.addColumn("");
			model.addRow(new Object[] { "NO HAY CURSOS PLANIFICADOS ACTUALMENTE" });

		} else {

			model.addColumn(HEADER_COLUMN6);
			model.addColumn(HEADER_COLUMN1);
			model.addColumn(HEADER_COLUMN2);
			model.addColumn(HEADER_COLUMN3);
			model.addColumn(HEADER_COLUMN4);
	

			if (showAllFields) {
				model.addColumn(HEADER_COLUMN7);
				model.addColumn(HEADER_COLUMN8);
				model.addColumn(HEADER_COLUMN9);

				for (CursoDto c : cursos) {
					if(c.estado == CursoDto.CURSO_PLANIFICADO){
						model.addRow(new Object[] { c.codigoCurso, c.titulo, c.fechaInicio, c.plazasDisponibles, c.precio,
								" - ", " - ", c.estado });
					}else {
						model.addRow(new Object[] { c.codigoCurso, c.titulo, c.fechaInicio, c.plazasDisponibles, c.precio,
								c.fechaApertura, c.fechaCierre, c.estado });
					}
					
				}

			} else {
				for (CursoDto c : cursos) {
					model.addRow(
							new Object[] { c.codigoCurso, c.titulo, c.fechaInicio, c.plazasDisponibles, c.precio });
				}
			}

		}

		return model;
	}
}