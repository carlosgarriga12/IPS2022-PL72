package ui.model;

import java.awt.Component;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import business.BusinessException;
import persistence.DtoAssembler;
import persistence.colegiado.ColegiadoDto;

public class ColegiadoModel extends DefaultTableModel {

	private static final long serialVersionUID = -8972242062999724063L;

	public static final String HEADER_COLUMN1 = "DNI";
	public static final String HEADER_COLUMN2 = "NOMBRE";
	public static final String HEADER_COLUMN3 = "APELLIDOS";
	public static final String HEADER_COLUMN4 = "TELÉFONO";
	public static final String HEADER_COLUMN5 = "FECHA DE SOLICITUD";
	public static final String HEADER_COLUMN6 = "TITULACIÓN";
	public static final String HEADER_COLUMN7 = "NUMERO COLEGIADO";

	private List<ColegiadoDto> colegiados;

	public ColegiadoModel(List<ColegiadoDto> colegiados) {
		this.colegiados = colegiados;
	}

	public TableModel getColegiadoModel(final boolean showAllFields) throws BusinessException {
		DefaultTableModel model = new DefaultTableModel() {

			private static final long serialVersionUID = 360537344960895264L;

			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}

		};

		if (colegiados.size() == 0) {
			model.addColumn("");
			model.addRow(new Object[] { "NO HAY SOLICITUDES DE ALTA DE COLEGIADOS" });

		} else {

			model.setColumnIdentifiers(new Object[] { HEADER_COLUMN1, HEADER_COLUMN2, HEADER_COLUMN3, HEADER_COLUMN4,
					HEADER_COLUMN5, HEADER_COLUMN6 });

			for (ColegiadoDto c : colegiados) {
				model.addRow(new Object[] { c.DNI, c.nombre, c.apellidos, c.telefono, c.fechaSolicitud,
						c.titulacion.isEmpty() ? "Sin titulación"
								: DtoAssembler.listaTitulacionesColegiadoToString(c.titulacion) });
			}

		}

		return model;

	}

	public TableModel getPeritoModel() {
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		if (colegiados.size() == 0) {
			model.addColumn("");
			model.addRow(new Object[] { "NO HAY PERITOS EN LA LISTA DE PROFESIONALES" });

		} else {

			model.setColumnIdentifiers(
					new Object[] { HEADER_COLUMN1, HEADER_COLUMN2, HEADER_COLUMN3, "Renovacion", "PosicionPerito" });

			for (ColegiadoDto c : colegiados) {
				model.addRow(new Object[] { createCustomCell(c.DNI), createCustomCell(c.nombre),
						createCustomCell(c.apellidos), createCustomCell(c.perito),
						createCustomCell(c.posicionPerito) });
			}

		}

		return model;
	}

	/**
	 * Modelo de tabla para Colegiado destina de mostrar los datos de las
	 * solicitudes aceptadas del lote recepcionado.
	 * 
	 * @since HU. 19062
	 * @return TableModel con el listado de solicitantes a colegiado admitidos.
	 * @throws BusinessException
	 */
	public TableModel getNuevoColegiadoModel() throws BusinessException {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 360537344960895264L;

			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};

		if (colegiados.size() == 0) {
			model.addColumn("");
			model.addRow(new Object[] { "YA SE HA RECEPCIONADO EL LOTE DE SOLICITUDES DE COLEGIACION" });

		} else {

			model.setColumnIdentifiers(new Object[] { HEADER_COLUMN7, HEADER_COLUMN1, HEADER_COLUMN2, HEADER_COLUMN3,
					HEADER_COLUMN4, HEADER_COLUMN5, HEADER_COLUMN6 });

			for (ColegiadoDto c : colegiados) {
				model.addRow(new Object[] { c.numeroColegiado, c.DNI, c.nombre, c.apellidos, c.telefono,
						c.fechaSolicitud, c.titulacion.isEmpty() ? "Sin titulación"
								: DtoAssembler.listaTitulacionesColegiadoToString(c.titulacion) });
			}

		}

		return model;

	}

	/**
	 * 
	 * 
	 * Su finalidad es centrar el texto de una celda. Permitiendo personalizarla aún
	 * 
	 * 
	 * más.
	 *
	 * 
	 * 
	 * 
	 * 
	 * @param content Heterogéneo String | Integer | Date
	 * 
	 * 
	 * @return Contenido centrado
	 * 
	 * 
	 */

	private String createCustomCell(Object content) {

		JLabel cell = new JLabel(String.valueOf(content));

		cell.setHorizontalAlignment(SwingConstants.CENTER);

		cell.setAlignmentX(Component.CENTER_ALIGNMENT);

		return cell.getText();

	}

	@Override

	public boolean isCellEditable(int i, int i1) {
		return false;
	}

}