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
	
	public static final String HEADER_COLUMN10 = "NÚMERO DE PLAZAS";
	public static final String HEADER_COLUMN11 = "NÚMERO DE INSCRIPCIONES";
	public static final String HEADER_COLUMN12 = "¿ ES CANCELABLE ?";
	public static final String HEADER_COLUMN13 = "FECHA DE INICIO";
	
	public static final int LISTA_CURSOS = 3;



	private List<CursoDto> cursos;

	public CursoModel(List<CursoDto> cursos) {
		this.cursos = cursos;
	}

	/**
	 * Modelo para el listado de cursos planificados.
	 * 
	 * @since HU. 19062
	 * @return
	 */
	public TableModel getCursosPlanificadosModel() {
		DefaultTableModel model = new DefaultTableModel();

		if (cursos.size() == 0) {
			model.addColumn("");
			model.addRow(new Object[] { "NO HAY CURSOS PLANIFICADOS ACTUALMENTE" });

		} else {

			model.addColumn(HEADER_COLUMN6);
			model.addColumn(HEADER_COLUMN1);
			model.addColumn(HEADER_COLUMN2);
			model.addColumn(HEADER_COLUMN3);

			for (CursoDto c : cursos) {
				model.addRow(new Object[] { c.codigoCurso, c.titulo, c.fechaInicio, c.plazasDisponibles });
			}

		}

		return model;
	}

	/**
	 * @since Segundo Sprint Miguel.
	 * @param showAllFields
	 * 
	 *                      <code>
	 * 			<ul>
	 * 				<li>0 Todos los cursos</li>
	 * 				<li>1 Todos los cursos menos id</li>
	 * 			</ul>
	 * 		</code>
	 * @return
	 * @throws BusinessException
	 */
	public TableModel getCursoModel(final int showAllFields) throws BusinessException {

		// 0 -> todos
		// 1 -> todos menos id
		// Listado de cursos actualmente planificados
		DefaultTableModel model = new DefaultTableModel() {

			private static final long serialVersionUID = 4438277548587975019L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		if (cursos.size() == 0) {
			model.addColumn("");
			model.addRow(new Object[] { "NO HAY CURSOS PLANIFICADOS ACTUALMENTE" });

		} else {
			if (showAllFields == 1) {
				model.addColumn(HEADER_COLUMN1);
				model.addColumn(HEADER_COLUMN2);
				model.addColumn(HEADER_COLUMN3);

				model.addColumn(HEADER_COLUMN7);
				model.addColumn(HEADER_COLUMN8);
				model.addColumn(HEADER_COLUMN9);

				model.addColumn(HEADER_COLUMN6);

				for (CursoDto c : cursos) {
					model.addRow(new Object[] { c.titulo, c.fechaInicio, c.plazasDisponibles, c.fechaApertura,
							c.fechaCierre, c.estado, c.codigoCurso });
				}
			} else if (showAllFields == LISTA_CURSOS) {
				model.addColumn(HEADER_COLUMN1);
				model.addColumn(HEADER_COLUMN10);
				model.addColumn(HEADER_COLUMN11);

				model.addColumn(HEADER_COLUMN4);
				model.addColumn(HEADER_COLUMN9);
				model.addColumn(HEADER_COLUMN12);
				model.addColumn(HEADER_COLUMN13);
				
				model.addColumn(HEADER_COLUMN6);

				for (CursoDto c : cursos) {
					model.addRow(new Object[] { c.titulo, c.plazasDisponibles, c.numeroInscritos, c.precio,
							c.estado, c.isCancelable == true ? "CANCELABLE" : "NO CANCELABLE", c.fechaInicio, c.codigoCurso });

				}

			} else {
				model.addColumn(HEADER_COLUMN6);
				model.addColumn(HEADER_COLUMN1);
				model.addColumn(HEADER_COLUMN2);
				model.addColumn(HEADER_COLUMN3);
				model.addColumn(HEADER_COLUMN4);

				model.addColumn(HEADER_COLUMN7);
				model.addColumn(HEADER_COLUMN8);
				model.addColumn(HEADER_COLUMN9);

				for (CursoDto c : cursos) {
					if (c.estado == CursoDto.CURSO_PLANIFICADO) {
						model.addRow(new Object[] { c.codigoCurso, c.titulo, c.fechaInicio, c.plazasDisponibles,
								c.precio, " - ", " - ", c.estado });
					} else {
						model.addRow(new Object[] { c.codigoCurso, c.titulo, c.fechaInicio, c.plazasDisponibles,
								c.precio, c.fechaApertura, c.fechaCierre, c.estado });
					}

				}

				for (CursoDto c : cursos) {
					model.addRow(
							new Object[] { c.codigoCurso, c.titulo, c.fechaInicio, c.plazasDisponibles, c.precio });
				}
			}
		}

		return model;

	}

	public TableModel getCursosAbiertosInscripcionCurso() {
		DefaultTableModel model = new DefaultTableModel() {

			private static final long serialVersionUID = -3053373842241847716L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		if (cursos.size() == 0) {
			model.addColumn("");
			model.addRow(new Object[] { "NO HAY CURSOS ABIERTOS" });

		} else {
			model.addColumn(HEADER_COLUMN1);
			model.addColumn(HEADER_COLUMN2);
			model.addColumn(HEADER_COLUMN3);

			model.addColumn(HEADER_COLUMN7);
			model.addColumn(HEADER_COLUMN8);

			model.addColumn(HEADER_COLUMN6);

			for (CursoDto c : cursos) {
				model.addRow(new Object[] { c.titulo, c.fechaInicio, c.plazasDisponibles, c.fechaApertura,
						c.fechaCierre, c.codigoCurso });
			}

		}

		return model;
	}

	public boolean isCellEditable(int i, int i1) {
		return false; // To change body of generated methods, choose Tools | Templates.
	}
}