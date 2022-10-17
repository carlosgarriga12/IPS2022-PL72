package business.recibo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import persistence.colegiado.ColegiadoCrud;
import persistence.colegiado.ColegiadoDto;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.recibo.ReciboDto;
import persistence.util.Conf;

public class ReciboCRUD {
	
	private static final String SQL_INSERT_RECIBO = 
			Conf.getInstance().getProperty("TRECIBO_INSERT");
	
	private static final String SQL_FIND_RECIBO_COLEGIADO_YEAR =
			Conf.getInstance().getProperty("TRECIBO_FIND_BY_YEAR_COLEGIADO");
	
	private static final String SQL_FIND_MAX_NUMERO_RECIBO = 
			Conf.getInstance().getProperty("TRECIBO_MAX_NUMBER");
	
	private static boolean findReciboColegiadoByYear(String dni, int year) {		
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(SQL_FIND_RECIBO_COLEGIADO_YEAR);
			int i = 1;
			pst.setString(i++, dni);
			pst.setInt(i++, year);
			
			rs = pst.executeQuery();
			
			return rs.next();
			
		} catch(SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}
	}
	
	public static boolean emitirCuotas() {
		Connection c = null;
		PreparedStatement pst = null;
		List<ColegiadoDto> colegiados = ColegiadoCrud.findAllColegiados();
		List<ColegiadoDto> emitidos = new ArrayList<>();
		List<Integer> numerosRecibo = new ArrayList<>();
		try {
			c = Jdbc.getConnection();
			
			if (colegiados.size() == 0) {
				return false;
			}
			for(ColegiadoDto col : colegiados) {
				if(!findReciboColegiadoByYear(col.DNI, LocalDate.now().getYear())) {
					ReciboDto recibo = new ReciboDto();
					
					recibo.dniColegiado = col.DNI;
					recibo.year = LocalDate.now().getYear();
					recibo.numeroRecibo = generateNumeroRecibo();
					emitidos.add(col);
					numerosRecibo.add(recibo.numeroRecibo);
					addRecibo(recibo);
				}
			}
			if (emitidos.size() == 0) {
				return false;
			}
			EmisionCuotas.emitirCuotas(emitidos, numerosRecibo);
			return true;
			
		} catch(SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(c);
		}
	}
	
	private static int generateNumeroRecibo() {
		Connection c = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getConnection();
			st = c.createStatement();
			rs = st.executeQuery(SQL_FIND_MAX_NUMERO_RECIBO);
			
			if(rs.next()) {
				return rs.getInt(1) + 1;
			} else {
				return 1;
			}
		} catch(SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, st, c);
		}
	}

	public static void addRecibo(ReciboDto recibo) {
		Connection c = null;
		PreparedStatement pst = null;
		
		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(SQL_INSERT_RECIBO);
			int i = 1;
			pst.setInt(i++, recibo.numeroRecibo);
			pst.setString(i++, recibo.dniColegiado);
			pst.setInt(i++, recibo.year);
			
			pst.executeUpdate();
			
		}catch(SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(c);
			Jdbc.close(pst);
		}
	}
}
