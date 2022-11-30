package persistence.SolicitudServicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import persistence.colegiado.ColegiadoDto;
import persistence.jdbc.Jdbc;
import persistence.util.Conf;

public class SolicitudServiciosCrud {

	private static final String SQL_ListarSolicitudesServicios = Conf.getInstance()
			.getProperty("LISTAR_SOLICITUDES_SERVICIOS");
	private static final String SQL_InsertSolicitudesServicios = Conf.getInstance()
			.getProperty("INSERT_SOLICITUDES_SERVICIOS");
	private static final String SQL_AsociaSolicitudServicio = Conf.getInstance()
			.getProperty("ASOCIA_SOLICITUD_SERVICIO");
	private static final String SQL_ActualizaPosicionPerito = Conf.getInstance()
			.getProperty("ACTUALIZA_POSICION_PERITO");
	private static final String SQL_ActualizaPosicionesLista = Conf.getInstance()
			.getProperty("ACTUALIZA_POSICIONES_LISTA");
	private static final String SQL_ActualizaPosicionesListaAdd = Conf.getInstance()
			.getProperty("ACTUALIZA_POSICIONES_LISTA_ADD");
	private static final String SQL_CANCELA_PERICIAL = Conf.getInstance()
			.getProperty("CANCELA_PERICIAL");

	public static ArrayList<SolicitudServiciosDto> listarSolicitudesServicios() {
		Connection c = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList<SolicitudServiciosDto> solList = new ArrayList<>();

		try {
			c = Jdbc.getConnection();

			st = c.createStatement();
			rs = st.executeQuery(SQL_ListarSolicitudesServicios);

			while (rs.next()) {
				try {
					SolicitudServiciosDto sol = new SolicitudServiciosDto();
					sol.CorreoElectronico = rs.getString("CorreoElectronico");
					sol.DNI = rs.getString("DNI");
					sol.Descripcion = rs.getString("Descripcion");
					sol.id = rs.getInt("id");
					solList.add(sol);
				} catch (Exception e) {
				}
			}
			return solList;

		} catch (SQLException e) {
			System.out.print("");
		} finally {
			Jdbc.close(rs, st, c);
		}
		return solList;
	}

	public static void insertSolicitudServicios(SolicitudServiciosDto s) {

		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_InsertSolicitudesServicios);
			pst.setString(1, s.CorreoElectronico);
			pst.setString(2, s.DNI);
			pst.setString(3, s.Descripcion);
			pst.setInt(4, s.Urgente);
			pst.setInt(5, LocalDate.now().getYear());

			pst.execute();

		} catch (SQLException e) {
		} finally {
			Jdbc.close(rs, pst, c);
		}

	}

	public static void AsociaSolicitudServicio(SolicitudServiciosDto s, ColegiadoDto colegiado) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_AsociaSolicitudServicio);
			pst.setString(1, colegiado.DNI);
			pst.setInt(2, s.id);

			pst.execute();
			ActualizaPosicionPerito(colegiado);

		} catch (SQLException e) {
			System.out.print("");
		} finally {
			Jdbc.close(rs, pst, c);
		}

	}

	public static void ActualizaPosicionPerito(ColegiadoDto colegiado) {

		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		actualizaPosicionesLista(colegiado);

		c = null;
		pst = null;
		rs = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_ActualizaPosicionPerito);
			pst.setString(1, colegiado.DNI);

			pst.execute();

		} catch (SQLException e) {
			System.out.print("");
		} finally {
			Jdbc.close(rs, pst, c);
		}
	}
	
	public static void actualizaPosicionesLista(ColegiadoDto colegiado) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_ActualizaPosicionesLista);
			pst.setInt(1, colegiado.posicionPerito);

			pst.execute();

		} catch (SQLException e) {
			System.out.print("");
		} finally {
			Jdbc.close(rs, pst, c);
		}
	}
	
	public static void actualizaPosicionesListaAdd(ColegiadoDto colegiado) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_ActualizaPosicionesListaAdd);
			pst.setInt(1, colegiado.posicionPerito);

			pst.execute();

		} catch (SQLException e) {
			System.out.print("");
		} finally {
			Jdbc.close(rs, pst, c);
		}
	}
	
	
	
	
	public static ArrayList<SolicitudServiciosDto> listarSolicitudesServiciosConFiltros(String Query) {
		Connection c = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList<SolicitudServiciosDto> solList = new ArrayList<>();

		try {
			c = Jdbc.getConnection();

			st = c.createStatement();
			rs = st.executeQuery(Query);
			while (rs.next()) {
				try {
					SolicitudServiciosDto sol = new SolicitudServiciosDto();
					sol.Descripcion = rs.getString("Descripcion");
					sol.AgeSolicitud = rs.getInt("AgeSolicitud");
					sol.estado = rs.getString("Estado");
					sol.id = rs.getInt("id");
					String fCancelacion = rs.getString("FechaCancelacion");
					if(fCancelacion==null || fCancelacion == "") {
						sol.fechaCancelacion = null;
					}
					else {
						sol.fechaCancelacion = LocalDate.parse(rs.getString("FechaCancelacion"));
					}
					String dniPerito = rs.getString("dniPerito");
					if(dniPerito == null || dniPerito == "") {
						sol.peritoDNI = "";
					}
					else {
						sol.peritoDNI = dniPerito;
					}
					solList.add(sol);
				} catch (Exception e) {
				}
			}
			return solList;

		} catch (SQLException e) {
			System.out.print("");
		} finally {
			Jdbc.close(rs, st, c);
		}
		return solList;
	}

	public static void CancelaPericial(SolicitudServiciosDto s) {
		// TODO Auto-generated method stub
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_CANCELA_PERICIAL);
			pst.setString(1, LocalDate.now().toString());
			pst.setInt(2, s.id);

			pst.execute();

		} catch (SQLException e) {
			System.out.print("");
		} finally {
			Jdbc.close(rs, pst, c);
		}

	}
	
	
	
	
}
