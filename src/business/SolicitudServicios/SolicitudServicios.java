package business.SolicitudServicios;

import java.util.ArrayList;

import persistence.SolicitudServicios.SolicitudServiciosCrud;
import persistence.SolicitudServicios.SolicitudServiciosDto;
import persistence.colegiado.ColegiadoDto;
import persistence.colegiado.PeritoCrud;
import business.colegiado.Perito;

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
	
	public static ArrayList<SolicitudServiciosDto> listarSolicitudesServiciosConFiltros(String estado, int año, String dniPerito){
		
		String Query = "Select * from SolicitudPeritos";
		

		String queryEstado = estado.equals("Todos") ? "" : " WHERE SolicitudPeritos.Estado = "+'"'+estado+'"';
		String queryAge = año == -1 ? "" : queryEstado.equals("") ? " WHERE SolicitudPeritos.AgeSolicitud = "+año : " AND SolicitudPeritos.AgeSolicitud = "+año;
		String queryDni = dniPerito.trim().equals("") ? "" : (!queryEstado.equals("")) || (!queryEstado.equals("")) ? " AND SolicitudPeritos.dniPerito = "+'"'+dniPerito.trim()+'"' : 
			" WHERE SolicitudPeritos.dniPerito = "+'"'+dniPerito.trim()+'"';
		return SolicitudServiciosCrud.listarSolicitudesServiciosConFiltros(Query+queryEstado+queryAge+queryDni);
	}
	
	public static void CancelaPericial(SolicitudServiciosDto s) {
		SolicitudServiciosCrud.CancelaPericial(s);
		PeritoCrud.RecuperaPosicion(s.peritoDNI);
	}

}
