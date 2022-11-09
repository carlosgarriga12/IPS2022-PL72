package business.SolicitudServicios;

import java.util.ArrayList;

import persistence.SolicitudServicios.SolicitudServiciosCrud;
import persistence.SolicitudServicios.SolicitudServiciosDto;
import persistence.colegiado.ColegiadoDto;

public class SolicitudServicios {
	public static ArrayList<SolicitudServiciosDto> listarSolicitudesServicios(){
		return SolicitudServiciosCrud.listarSolicitudesServicios();
	}
	
	public static void insertSolicitudServicios(SolicitudServiciosDto s) {
		SolicitudServiciosCrud.insertSolicitudServicios(s);
	}

	public static void AsociaSolicitudServicio(SolicitudServiciosDto s, ColegiadoDto c) {
		SolicitudServiciosCrud.AsociaSolicitudServicio(s, c);
	}

}
