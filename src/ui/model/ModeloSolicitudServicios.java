package ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import persistence.SolicitudServicios.SolicitudServiciosDto;

public class ModeloSolicitudServicios {
	public static final String HEADER_COLUMN1 = "DESCRIPCION";
	
	
	private List<SolicitudServiciosDto> solicitudServicios;

	public ModeloSolicitudServicios(List<SolicitudServiciosDto> solicitudServicios) {
		this.solicitudServicios = solicitudServicios;
	}
	
	
	public TableModel getSolicitudModel(){

		// Listado de cursos actualmente planificados

		DefaultTableModel model = new DefaultTableModel();

		if (solicitudServicios.size() == 0) {
			model.addColumn("");
			model.addRow(new Object[] { "NO HAY SOLICITUDES ACTUALMENTE" });

		} else {

			model.addColumn(HEADER_COLUMN1);

			
			for (SolicitudServiciosDto s : solicitudServicios) {
				
				model.addRow(
						new Object[] {stringSaltoLinea(s.Descripcion)});
			}
		}

		return model;
	}
	
	private String stringSaltoLinea(String s) {
		ArrayList<Integer> espacios = new ArrayList<Integer>();
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
