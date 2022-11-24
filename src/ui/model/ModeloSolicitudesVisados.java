package ui.model;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import persistence.solicitudVisados.SolicitudVisadoDto;

public class ModeloSolicitudesVisados {
	public static final String HEADER_COLUMN1 = "DniPerito";
	public static final String HEADER_COLUMN2 = "DESCRIPCION";
	public static final String HEADER_COLUMN3 = "Estado";
	
	private List<SolicitudVisadoDto> solicitudVisados;
	
	public ModeloSolicitudesVisados(List<SolicitudVisadoDto> solicitudVisados) {
		this.solicitudVisados = solicitudVisados;
	}
	
	public TableModel getSolicitudModel() {
		DefaultTableModel model = new DefaultTableModel();
		
		if (solicitudVisados.isEmpty()) {
			model.addColumn("");
			model.addRow(new Object[] { "NO HAY SOLICITUDES ACTUALMENTE" });
		} else {
			model.addColumn(HEADER_COLUMN1);
			model.addColumn(HEADER_COLUMN2);
			model.addColumn(HEADER_COLUMN3);
			
			for(SolicitudVisadoDto s : solicitudVisados) {
				model.addRow(new Object[] {s.dniPerito, stringSaltoLinea(s.descripcion), s.estado});
			}
		}
		
		return model;
	}
	
	private String stringSaltoLinea(String s) {
		String[] other = s.split(" ");
		String nuevoString = "";
		int sizeLinea = 0;
		for(String o : other) {
			if(sizeLinea + o.length() >= 90) {
				nuevoString += "\n" + o;
				sizeLinea = o.length();
			}
			else {
				nuevoString += " " + o;
				sizeLinea += o.length() +1;
			}
		}
		String sinSaltos = nuevoString.replaceAll("\n", "<br> ");
		return "<HTML> " + sinSaltos + " </HTML>";
	}
}
