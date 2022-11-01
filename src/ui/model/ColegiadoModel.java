package ui.model;

import java.util.List;

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

	private List<ColegiadoDto> colegiados;

	public ColegiadoModel(List<ColegiadoDto> colegiados) {
		this.colegiados = colegiados;
	}

	public TableModel getColegiadoModel(final boolean showAllFields) throws BusinessException {
		DefaultTableModel model = new DefaultTableModel();

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

	@Override
	public boolean isCellEditable(int i, int i1) {
		return false;
	}

}