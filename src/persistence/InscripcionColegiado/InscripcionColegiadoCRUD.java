package persistence.InscripcionColegiado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import persistence.DtoAssembler;
import persistence.Colegiado_Inscripcion.Colegiado_Inscripcion;
import persistence.colegiado.ColegiadoDto;
import persistence.curso.CursoDto;
import persistence.curso.Precio_Colectivos;
import persistence.jdbc.Jdbc;
import persistence.jdbc.PersistenceException;
import persistence.util.Conf;
import ui.util.Ficheros;



public class InscripcionColegiadoCRUD {
	private static final String SQL_INSCRIPCION_COLEGIADO = Conf.getInstance().getProperty("INSCRIPCION_COLEGIADO");
	private static final String SQL_IS_INSCRITO = Conf.getInstance().getProperty("IS_INSCRITO");
	private static final String SQL_LISTA_INSCRIPCIONES_COLEGIADO = Conf.getInstance().getProperty("LISTA_INSCRIPCIONES_COLEGIADO");
	private static final String SQL_INSCRIPCION_FIND_FECHA = Conf.getInstance().getProperty("TINSCRIPCION_FIND_FECHA");
	private static final String SQL_INSCRIPCION_PAGAR = Conf.getInstance().getProperty("TINSCRIPCION_PAGAR");
	
	private static final String SQL_INSCRIPCION_TRANSFERENCIA_BANCO = Conf.getInstance().getProperty("TINSCRIPCION_FIND_DATOS_TRANSFERENCIA");
	private static final String SQL_INSCRIPCION_TRANSFERENCIA_BANCO_PROCESADAS = Conf.getInstance().getProperty("TINSCRIPCION_FIND_DATOS_TRANSFERENCIA_PROCESADA");
	private static final String SQL_INSCRIPCION_BANCO_TRANSFERENCIA = Conf.getInstance().getProperty("TINSCRIPCION_BANCO_PAGAR");
	private static final String SQL_INSCRIPCION_BANCO_PROCESAR_TRANSFERENCIA = Conf.getInstance().getProperty("TINSCRIPCION_PROCESAR_TRANSFERENCIAS");

	private static final String SQL_TOTAL_INSCRITOS_CURSO = Conf.getInstance().getProperty("TINSCRIPCION_FIND_ALL_COURSE");
	private static final String SQL_INSCRIPCION_FIND_ID = Conf.getInstance().getProperty("TINSCRIPCION_FIND_ALL");
	private static final String SQL_INSCRIPCION_CANCELAR_PREINSCRITO_PENDIENTE = Conf.getInstance().getProperty("TINSCRIPCION_CANCELAR_PREINSCRITO_PENDIENTE");
	private static final String SQL_INSCRIPCION_CANCELAR_TARJETA = Conf.getInstance().getProperty("TINSCRIPCION_CANCELAR_TARJETA");
	private static final String SQL_INSCRIPCION_CANCELAR_TRANSF = Conf.getInstance().getProperty("TINSCRIPCION_CANCELAR_TRANSF");
	private static final String SQL_INSCRIPCION_FIND_CANCELADAS = Conf.getInstance().getProperty("TINSCRIPCION_FIND_CANCELADAS");
	private static final String SQL_INSCRIPCION_FIND_CANCELADA = Conf.getInstance().getProperty("TINSCRIPCION_FIND_CANCELADA");
	private static final String SQL_INSCRIPCION_FIND_CANCELAR = Conf.getInstance().getProperty("TINSCRIPCION_FIND_CANCELAR");
	private static final String SQL_INSCRIPCION_FIND_BY_DNI = Conf.getInstance().getProperty("TINSCRIPCION_FIND_BY_DNI");
	private static final String SQL_INSCRIPCION_DELETE_BY_DNI = Conf.getInstance().getProperty("TINSCRIPCION_DELETE_BY_DNI");
	
	public static void InscribirColegiado(CursoDto curso, ColegiadoDto colegiado) throws PersistenceException {
		PreparedStatement stmt = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_COLEGIADO);
			
			int i = 1;
			stmt.setString(i++, colegiado.DNI);
			stmt.setInt(i++, curso.codigoCurso);
			stmt.setString(i++, LocalDate.now().toString());
			double precio = Precio_Colectivos.StringToPrecio_Colectivos(curso.CantidadPagarColectivo).getPrecio(colegiado.TipoColectivo);
			stmt.setDouble(i++, precio);
			stmt.setString(i++, "PREINSCRITO");
			stmt.execute();
			
			
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
		
	}
	

	public static boolean isInscrito(ColegiadoDto colegiado, CursoDto cursoSeleccionado) throws PersistenceException {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		boolean inscrito = false;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_IS_INSCRITO);
			stmt.setString(1, colegiado.DNI);
			stmt.setInt(2, cursoSeleccionado.codigoCurso);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.getInt("TOTAL")>0) {
				inscrito = true;
			}
			
			
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
		return inscrito;
	}
	
	public static List<Colegiado_Inscripcion> Lista_Inscritos_Curso(CursoDto c){
		PreparedStatement stmt = null;
		ArrayList<Colegiado_Inscripcion> inscritos;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_LISTA_INSCRIPCIONES_COLEGIADO);
			stmt.setInt(1, c.codigoCurso);
			
			ResultSet rs = stmt.executeQuery();
			inscritos =  DtoAssembler.toInscripcionColegiadosList(rs);
			
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
		return inscritos;
	}
	
	
	
	public static String findFechaPreinscripcion(String dni, int cursoSeleccionado) throws PersistenceException {
		PreparedStatement stmt = null;
		String fecha = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_FIND_FECHA);
			stmt.setString(1, dni);
			stmt.setInt(2, cursoSeleccionado);
			
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			
			if (rs.getString(1) == null) {
				return null;
			}
			
			fecha = rs.getString(1);
			
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
		return fecha;
	}
	
	public static void pagarCursoColegiado(String dni, int curso, String estado, String formaDePago) throws PersistenceException {
		PreparedStatement stmt = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_PAGAR);
			stmt.setString(1, estado);
			stmt.setString(2, formaDePago);
			stmt.setString(3, dni);
			stmt.setInt(4, curso);
			
			stmt.executeUpdate();
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
	}
	
	public static List<InscripcionColegiadoDto> findInscripcionesPorCursoId(int cursoSeleccionado) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<InscripcionColegiadoDto> res = new ArrayList<>();
        try {
            Connection cn = Jdbc.getConnection();
            stmt = cn.prepareStatement(SQL_INSCRIPCION_TRANSFERENCIA_BANCO);
            stmt.setInt(1, cursoSeleccionado);
            
            rs = stmt.executeQuery();
            while (rs.next()) {
                res.add(DtoAssembler.resultsetToIncripcionTransferencia(rs));
            }
            
            return res;
        }
        catch(SQLException e){
            throw new PersistenceException(e);
        }
        finally {
            Jdbc.close(stmt);
        }
    }
	
	public static List<InscripcionColegiadoDto> findInscripcionesPorCursoIdProcesadas(int cursoSeleccionado) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<InscripcionColegiadoDto> res = new ArrayList<>();
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_TRANSFERENCIA_BANCO_PROCESADAS);
			stmt.setInt(1, cursoSeleccionado);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				res.add(DtoAssembler.resultsetToIncripcionTransferenciaProcesar(rs));
			}
			
			return res;
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
	}
	
	public static void pagarBanco(String dni, int curso, double precio) {
		PreparedStatement stmt = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_BANCO_TRANSFERENCIA);
			stmt.setString(1, LocalDate.now().toString());
			stmt.setString(2, Ficheros.generarCodigoTransferencia(12));
			stmt.setDouble(3, precio);
			stmt.setInt(4, curso);
			stmt.setString(5, dni);
			stmt.executeUpdate();
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
	}
	
	public static void pagarBancoFechaIncorrecta(String dni, int curso, double precioPagar) {
		PreparedStatement stmt = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_BANCO_TRANSFERENCIA);
			stmt.setString(1, LocalDate.now()
								.plusMonths(7)
								.toString());
			stmt.setString(2, Ficheros.generarCodigoTransferencia(12));
			stmt.setDouble(3, precioPagar);
			stmt.setInt(4, curso);
			stmt.setString(5, dni);
			stmt.executeUpdate();
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
	}


	public static void procesarTransferencia(String estado, String incidencias, int codigoCurso, String dni, String devolver) {
		PreparedStatement stmt = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_BANCO_PROCESAR_TRANSFERENCIA);
			stmt.setString(1, estado);
			stmt.setString(2, incidencias);
			stmt.setString(3, devolver);
			stmt.setInt(4, codigoCurso);
			stmt.setString(5, dni);
			stmt.executeUpdate();
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
	}


	public static int getTotalInscrito(CursoDto cursoSeleccionado) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_TOTAL_INSCRITOS_CURSO);
			stmt.setInt(1, cursoSeleccionado.codigoCurso);
			rs = stmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
	}

	public static List<InscripcionColegiadoDto> findInscripciones(int codigoCurso) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<InscripcionColegiadoDto> res = new ArrayList<>();
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_FIND_ID);
			stmt.setInt(1, codigoCurso);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				res.add(DtoAssembler.resultsetToIncripcion(rs));
			}
			
			return res;
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
	}


	public static void actualizarPreinscritoPendiente(int codigoCurso, String dNI, String devolver, LocalDate fechaCancelacion, String estado,
			String incidencias) {
		PreparedStatement stmt = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_CANCELAR_PREINSCRITO_PENDIENTE);
			stmt.setString(1, incidencias);
			stmt.setString(2, fechaCancelacion.toString());
			stmt.setString(3, estado);
			stmt.setString(4, devolver);
			stmt.setInt(5, codigoCurso);
			stmt.setString(6, dNI);

			stmt.executeUpdate();
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
	}


	public static void actualizarTarjeta(int codigoCurso, String dNI, double precio, String devolver,
			LocalDate fechaCancelacion, String estado, String incidencias) {
		PreparedStatement stmt = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_CANCELAR_TARJETA);
			stmt.setString(1, incidencias);
			stmt.setString(2, fechaCancelacion.toString());
			stmt.setString(3, estado);
			stmt.setDouble(4, precio);
			stmt.setString(5, devolver);
			stmt.setInt(6, codigoCurso);
			stmt.setString(7, dNI);

			stmt.executeUpdate();
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
		
	}


	public static void actualizarTransf(int codigoCurso, String dNI, String devolver, LocalDate fechaCancelacion,
			String estado, String incidencias) {
		PreparedStatement stmt = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_CANCELAR_TRANSF);
			stmt.setString(1, incidencias);
			stmt.setString(2, fechaCancelacion.toString());
			stmt.setString(3, estado);
			stmt.setString(4, devolver);
			stmt.setInt(5, codigoCurso);
			stmt.setString(6, dNI);

			stmt.executeUpdate();
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
	}


	public static List<InscripcionColegiadoDto> findInscripcionesCanceladas(int codigoCurso) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<InscripcionColegiadoDto> res = new ArrayList<>();
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_FIND_CANCELADAS);
			stmt.setInt(1, codigoCurso);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				res.add(DtoAssembler.resultsetToIncripcion(rs));
			}
			
			return res;
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
	}


	public static InscripcionColegiadoDto findInscripcion(int codigoCurso, String dni, boolean elem) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Connection cn = Jdbc.getConnection();
			if (elem) {
				stmt = cn.prepareStatement(SQL_INSCRIPCION_FIND_CANCELAR);
			} else {
				stmt = cn.prepareStatement(SQL_INSCRIPCION_FIND_CANCELADA);
			}
			stmt.setInt(1, codigoCurso);
			stmt.setString(2, dni);
			
			rs = stmt.executeQuery();
			if (rs.next()==true) {
				return DtoAssembler.resultsetToIncripcion(rs);
			}
			
			return null;
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
	}


	public static InscripcionColegiadoDto findInscripcion(String colegiadoDni) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Connection cn = Jdbc.getConnection();
			stmt = cn.prepareStatement(SQL_INSCRIPCION_FIND_BY_DNI);
			stmt.setString(1, colegiadoDni);
			
			rs = stmt.executeQuery();
			if (rs.next()==true) {
				return DtoAssembler.resultsetToIncripcionDni(rs);
			}
			
			return null;
		}
		catch(SQLException e){
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(stmt);
		}
	}
	
	public static void deleteInscripcionesByDni(String dni) {
		Connection c = null;
		PreparedStatement pst = null;
		
		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(SQL_INSCRIPCION_DELETE_BY_DNI);
			
			pst.setString(1, dni);
			pst.execute();
			
		} catch(SQLException e) {
			throw new PersistenceException(e);
			
		} finally {
			Jdbc.close(pst);
			Jdbc.close(c);
		}
	}
	
}