package persistence.SolicitudServicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
