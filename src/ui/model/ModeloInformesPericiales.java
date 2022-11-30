package ui.model;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import persistence.SolicitudServicios.SolicitudServiciosDto;

public class ModeloInformesPericiales {

	public static final String HEADER_COLUMN1 = "DESCRIPCION";
	public static final String HEADER_COLUMN2 = "DNI PERITO";
	public static final String HEADER_COLUMN3 = "A�O SOLICITUD";
	public static final String HEADER_COLUMN4 = "ESTADO";
	public static final String HEADER_COLUMN5 = "FECHA CANCELACION";
	
	
	private List<SolicitudServiciosDto> solicitudServicios;

	public ModeloInformesPericiales(List<SolicitudServiciosDto> solicitudServicios) {
		this.solicitudServicios = solicitudServicios;
	}
	
	
	public TableModel getSolicitudModel(boolean cancelacion, boolean texto){

		// Listado de cursos actualmente planificados

		DefaultTableModel model = new DefaultTableModel();

		if (solicitudServicios.size() == 0) {
			model.addColumn("");
			if(texto) {
				model.addRow(new Object[] { "El perito indicado no tiene periciales asociadas" });
			}
			else {
				model.addRow(new Object[] { "NO HAY SOLICITUDES PARA LA CONFIGURACION DE FILTROS ACTUAL" });
			}
			

		} else {

			model.addColumn(HEADER_COLUMN1);
			model.addColumn(HEADER_COLUMN2);
			model.addColumn(HEADER_COLUMN3);
			model.addColumn(HEADER_COLUMN4);
			if(cancelacion) {
				model.addColumn(HEADER_COLUMN5);
				
	
				for (SolicitudServiciosDto s : solicitudServicios) {
					model.addRow(
							new Object[] {stringSaltoLinea(s.Descripcion), s.peritoDNI == null ? "" : s.peritoDNI, s.AgeSolicitud, s.estado,
									s.fechaCancelacion != null ? s.fechaCancelacion : ""});
				}
				
			}
			else {
				for (SolicitudServiciosDto s : solicitudServicios) {
					
					model.addRow(
							new Object[] {stringSaltoLinea(s.Descripcion), s.peritoDNI != null ? s.peritoDNI : "", s.AgeSolicitud, s.estado});
				}
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
