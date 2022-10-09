package persistence.colegiado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import persistence.DtoAssembler;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;


public class ColegiadoCRUD {	
	private static final String SQL_FIND_ALL_COLEGIADO = 
			Conf.getInstance().getProperty("TCOLEGIADO_FIND_ALL");
	
	
	public List<ColegiadoDto> findAllColegiados() throws SQLException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			con = Jdbc.getConnection();
			pst = con.prepareStatement(SQL_FIND_ALL_COLEGIADO);
			
			rs = pst.executeQuery();
			
			return DtoAssembler.toColegiadoList(rs);
			
		} catch(SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
	}
}
