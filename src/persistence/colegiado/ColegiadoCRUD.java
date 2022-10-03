package persistence.colegiado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.DtoAssembler;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;


public class ColegiadoCRUD {
	private static final String SQL_INSERT_CURSO = 
			Conf.getInstance().getProperty("TCOLEGIADO_FIND_BY_YEAR");
	
	public List<ColegiadoDto> getColegiadosByYear(int año) throws SQLException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_INSERT_CURSO);
			
			pst.setInt(1, año);
			
			rs = pst.executeQuery();
			
			return DtoAssembler.toColegiadoList(rs);
			
		} catch(SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
	}
}
