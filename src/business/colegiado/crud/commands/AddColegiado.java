package business.colegiado.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

import business.BusinessException;
import business.colegiado.ColegiadoService.ColegiadoDto;
import business.util.Argument;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;

public class AddColegiado {
	
	private static final String SQL_AÑADIR_COLEGIADO = 
			Conf.getInstance().getProperty("TCOLEGIADO_ADD");
	private static final String ESTADO_PENDIENTE = "PENDIENTE"; // se le asigna como PENDIENTE de momento
	
	
	private ColegiadoDto colegiado; // se lo pasamos
	
	public AddColegiado(ColegiadoDto colegiado) {
		Argument.isNotNull(colegiado);
		
		Argument.isNotNull(colegiado.DNI);
		Argument.isNotEmpty(colegiado.DNI);
		Argument.longitudNueve(colegiado.DNI);

		Argument.isNotNull(colegiado.nombre);
		Argument.isNotEmpty(colegiado.nombre);

		Argument.isNotNull(colegiado.apellidos);
		Argument.isNotEmpty(colegiado.apellidos);
		
		Argument.isNotNull(colegiado.poblacion);
		Argument.isNotEmpty(colegiado.poblacion);
		
		Argument.isNotNull(colegiado.centro);
		Argument.isNotEmpty(colegiado.centro);
		
		Argument.is012(colegiado.titulacion);
		
		Argument.isPositive(colegiado.annio);
		
		Argument.isPositive(colegiado.numeroTarjeta);

		Argument.isPositive(colegiado.telefono);

		Argument.longitudNueve(colegiado.telefono);
		
		Argument.longitudDieciseis(colegiado.numeroTarjeta);
		
		// comprobar si existe en la base de datos
		
		this.colegiado=colegiado;
	}

	public ColegiadoDto execute() throws BusinessException {
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_AÑADIR_COLEGIADO);
			
			// set cosas, completar
			pst.setString(1, colegiado.DNI);
			pst.setString(2, colegiado.nombre);
			pst.setString(3, colegiado.apellidos);
			pst.setString(4, colegiado.poblacion);
			pst.setInt(5, colegiado.telefono);
			pst.setInt(6, colegiado.titulacion);
			pst.setString(7, colegiado.centro);
			pst.setInt(8, colegiado.annio);
			pst.setInt(9, colegiado.numeroTarjeta);
			pst.setString(10, LocalDate.now().toString());
			pst.setString(11, ESTADO_PENDIENTE);
			pst.setString(12, UUID.randomUUID().toString());

			pst.executeUpdate();
						
		} catch(SQLException e) {
			throw new PersistenceException(e);
		} finally {
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
			if (con != null) try { con.close(); } catch(SQLException e) { /* ignore */ }
		}
		return colegiado;
	}


}
