package ui.model;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import business.BusinessException;
import persistence.InscripcionColegiado.InscripcionColegiadoDto;

public class InscripcionColegiadoModel {
		
	public static final int TRANSFERENCIAS_RECIBIDAS = 1;
	public static final int TRANSFERENCIAS_PROCESADAS = 2;
	public static final int INSCRIPCIONES_CANCELADAS = 3;
	public static final int INSCRIPCION_CANCELADA = 4;
	public static final String HEADER_COLUMN1 = "DNI";
	public static final String HEADER_COLUMN2 = "NOMBRE";
	public static final String HEADER_COLUMN3 = "APELLIDOS";
	public static final String HEADER_COLUMN4 = "CANTIDAD ABONADA";
	public static final String HEADER_COLUMN5 = "FECHA TRANSFERENCIA";
	public static final String HEADER_COLUMN6 = "CODIGO TRANSFERENCIA";
	public static final String HEADER_COLUMN7 = "ESTADO INSCRIPCION";
	public static final String HEADER_COLUMN8 = "CUOTA CURSO";
	public static final String HEADER_COLUMN9 = "INCIDENCIAS";
	private static final String HEADER_COLUMN10 = "DEVOLVER";
	private static final String HEADER_COLUMN11 = "TITULO CURSO";
	private static final String HEADER_COLUMN12 = "FECHA DE CANCELACION";
	private static final String HEADER_COLUMN13 = "POLITICA DE DEVOLUCION";


	private List<InscripcionColegiadoDto> inscripciones;

	public InscripcionColegiadoModel(List<InscripcionColegiadoDto> inscripciones) {
		this.inscripciones = inscripciones;
	}

	public TableModel getCursoModel(final int estado) throws BusinessException {
		// Listado de las incripciones
		DefaultTableModel model = new DefaultTableModel() { 
			private static final long serialVersionUID = 1L;

			@Override public boolean isCellEditable(int row, int column) { 
				return false; 
			} 
		};
		
		if (estado==TRANSFERENCIAS_RECIBIDAS) {
			if (inscripciones.size() == 0) {
				model.addColumn("");
				model.addRow(new Object[] { "NO HAY INSCRIPCIONES REALIZADAS POR TRANSFERENCIA PENDIENTES POR REVISAR" });

			} else {
				model.addColumn(HEADER_COLUMN1);
				model.addColumn(HEADER_COLUMN2);
				model.addColumn(HEADER_COLUMN3);
				model.addColumn(HEADER_COLUMN4);
				model.addColumn(HEADER_COLUMN5);
				model.addColumn(HEADER_COLUMN6);


				for (InscripcionColegiadoDto c : inscripciones) {
					if (c.fechaTransferencia == null) {
						model.addRow(new Object[] { c.colegiado.DNI, c.colegiado.nombre, c.colegiado.apellidos, c.cantidadPagada, "NO RELIZADA", "NO REALIZADA" });
					} else {
						model.addRow(new Object[] { c.colegiado.DNI, c.colegiado.nombre, c.colegiado.apellidos, c.cantidadPagada, c.fechaTransferencia.toString(), c.codigoTransferencia });
					}
				}	

			}
		} else if (estado==INSCRIPCIONES_CANCELADAS) {
			if (inscripciones.size() == 0) {
				model.addColumn("");
				model.addRow(new Object[] { "NO HAY INSCRIPCIONES CANCELADAS EN ESTE CURSO" });

			} else {
				model.addColumn(HEADER_COLUMN1);
				model.addColumn(HEADER_COLUMN2);
				model.addColumn(HEADER_COLUMN3);
				
				model.addColumn(HEADER_COLUMN11);
				model.addColumn(HEADER_COLUMN4);
				model.addColumn(HEADER_COLUMN8);
				model.addColumn(HEADER_COLUMN12);
				
				model.addColumn(HEADER_COLUMN7);
				model.addColumn(HEADER_COLUMN9);
				model.addColumn(HEADER_COLUMN10);


				for (InscripcionColegiadoDto c : inscripciones) {
					if (c.fechaTransferencia == null) {
						model.addRow(new Object[] { c.colegiado.DNI, c.colegiado.nombre, c.colegiado.apellidos, c.curso.titulo, 
								c.cantidadPagada, c.precio, c.fechaCancelacion, c.estado, "<html><p style = 'overflow-wrap: break-word;'>" + c.incidencias + "</p></html>", c.devolver });
					}
				}
			}	
		} else if (estado==INSCRIPCION_CANCELADA) {
			if (inscripciones.size() == 0) {
				model.addColumn("");
				model.addRow(new Object[] { "NO HAY NINGUNA INSCRIPCION CANCELADA EN ESTE CURSO" });

			} else {
				model.addColumn(HEADER_COLUMN1);
				model.addColumn(HEADER_COLUMN2);
				model.addColumn(HEADER_COLUMN3);
				
				model.addColumn(HEADER_COLUMN11);
				model.addColumn(HEADER_COLUMN4);
				model.addColumn(HEADER_COLUMN8);
				model.addColumn(HEADER_COLUMN12);
				
				model.addColumn(HEADER_COLUMN7);
				model.addColumn(HEADER_COLUMN9);
				model.addColumn(HEADER_COLUMN10);
				model.addColumn(HEADER_COLUMN13);


				for (InscripcionColegiadoDto c : inscripciones) {
					if (c.fechaTransferencia == null) {
						model.addRow(new Object[] { c.colegiado.DNI, c.colegiado.nombre, c.colegiado.apellidos, c.curso.titulo, 
								c.cantidadPagada, c.precio, c.fechaCancelacion, c.estado, "<html><p style = 'overflow-wrap: break-word;'>" + c.incidencias + "</p></html>", c.devolver,
								c.curso.porcentaje_devolucion});
					}
				}
			}	
			
		} else {
			if (inscripciones.size() == 0) {
				model.addColumn("");
				model.addRow(new Object[] { "NO HAY INSCRIPCIONES PROCESADAS EN ESTE CURSO" });

			} else {
				
				model.addColumn(HEADER_COLUMN1);
				model.addColumn(HEADER_COLUMN2);
				model.addColumn(HEADER_COLUMN3);
				model.addColumn(HEADER_COLUMN7);
				model.addColumn(HEADER_COLUMN4);
				model.addColumn(HEADER_COLUMN8);
				model.addColumn(HEADER_COLUMN9);
				model.addColumn(HEADER_COLUMN10);


				for (InscripcionColegiadoDto c : inscripciones) {
					model.addRow(new Object[] { c.colegiado.DNI, c.colegiado.nombre, c.colegiado.apellidos, c.estado , c.cantidadPagada, c.precio, c.incidencias, c.devolver });
				}		
				

			}
		}
		

		return model;
	}

}
