package business.colegiado.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import business.colegiado.ColegiadoService.ColegiadoDto;
import business.colegiado.assembler.ColegiadoAssembler;
import business.util.Argument;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;

public class FindColegiadoPorDni {
	private static final String SQL_BUSCAR_COLEGIADO_DNI = 
			Conf.getInstance().getProperty("TCOLEGIADO_FIND_BY_DNI");
	
	private String dni;

	public FindColegiadoPorDni(String dni) {
		Argument.isNotEmpty(dni);
		Argument.isNotNull(dni);
		Argument.longitudNueve(dni);
		
		this.dni=dni;
	}

	public ColegiadoDto execute() {
		ColegiadoDto colegiado;
		
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(SQL_BUSCAR_COLEGIADO_DNI);
			
			pst.setString(1, dni);
			
			rs = pst.executeQuery();
			colegiado = ColegiadoAssembler.toColegiadoDto(rs);
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
		
		return colegiado;
	}

}
