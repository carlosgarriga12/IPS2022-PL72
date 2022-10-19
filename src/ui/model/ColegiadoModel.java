package ui.model;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import business.BusinessException;
import persistence.colegiado.ColegiadoDto;

public class ColegiadoModel {

	public static final String HEADER_COLUMN1 = "DNI";
	public static final String HEADER_COLUMN2 = "NOMBRE";
	public static final String HEADER_COLUMN3 = "APELLIDOS";
//	public static final String HEADER_COLUMN4 = "TITULACION";
	public static final String HEADER_COLUMN5 = "FECHASOLICITUD";
	public static final String HEADER_COLUMN6 = "ESTADO";

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

			model.addColumn(HEADER_COLUMN1);
			model.addColumn(HEADER_COLUMN2);
			model.addColumn(HEADER_COLUMN3);
//			model.addColumn(HEADER_COLUMN4);
			model.addColumn(HEADER_COLUMN5);
			model.addColumn(HEADER_COLUMN6);

			for (ColegiadoDto c : colegiados) {
				model.addRow(new Object[] { c.DNI, c.nombre, c.apellidos, c.fechaSolicitud, c.estado });
			}
		}

		return model;

	}

}