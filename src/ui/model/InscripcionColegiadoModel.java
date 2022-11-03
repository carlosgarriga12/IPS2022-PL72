package ui.model;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import business.BusinessException;
import persistence.InscripcionColegiado.InscripcionColegiadoDto;

public class InscripcionColegiadoModel {
		
	public static final boolean TRANSFERENCIAS_RECIBIDAS = true;
	public static final boolean TRANSFERENCIAS_PROCESADAS = false;
	public static final String HEADER_COLUMN1 = "DNI";
	public static final String HEADER_COLUMN2 = "NOMBRE";
	public static final String HEADER_COLUMN3 = "APELLIDOS";
	public static final String HEADER_COLUMN4 = "CANTIDAD ABONADA";
	public static final String HEADER_COLUMN5 = "FECHA TRANSFERENCIA";
	public static final String HEADER_COLUMN6 = "CODIGO TRANSFERENCIA";
	public static final String HEADER_COLUMN7 = "ESTADO INSCRIPCION";
	public static final String HEADER_COLUMN8 = "CUOTA CURSO";
	public static final String HEADER_COLUMN9 = "INCIDENCIAS";
	private static final Object HEADER_COLUMN10 = "DEVOLVER";

	private List<InscripcionColegiadoDto> inscripciones;

	public InscripcionColegiadoModel(List<InscripcionColegiadoDto> inscripciones) {
		this.inscripciones = inscripciones;
	}

	public TableModel getCursoModel(final boolean estado) throws BusinessException {
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
