package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import persistence.Colegiado_Inscripcion.Colegiado_Inscripcion;
import persistence.InscripcionColegiado.InscripcionColegiadoDto;
import persistence.colegiado.ColegiadoDto;
import persistence.curso.CursoCRUD;
import persistence.curso.CursoDto;

public class DtoAssembler {

	public static List<ColegiadoDto> toColegiadoList(ResultSet rs) throws SQLException {
		List<ColegiadoDto> colegiados = new ArrayList<>();

		while (rs.next()) {
			colegiados.add(resultSetToColegiadoDto(rs));
		}

		return colegiados;
	}

	public static List<CursoDto> toInscripcionList(ResultSet rs) throws SQLException {
		List<CursoDto> inscripciones = new ArrayList<CursoDto>();
		while (rs.next()) {
			inscripciones.add(resultSetToInscripcionDto(rs));
		}
		return inscripciones;
	}

	public static ColegiadoDto resultSetToColegiadoDto(ResultSet rs) throws SQLException {
		ColegiadoDto c = new ColegiadoDto();

		c.DNI = rs.getString("DNI");
		c.nombre = rs.getString("nombre");
		c.apellidos = rs.getString("apellidos");
		c.poblacion = rs.getString("poblacion");
		c.titulacion = rs.getInt("titulacion");
		c.centro = rs.getString("centro");
		c.annio = rs.getInt("ano");
		c.numeroCuenta = rs.getString("numeroCuenta");
		c.fechaSolicitud = LocalDate.parse(rs.getString("fechaSolicitud"));
		c.numeroColegiado = rs.getString("numero");

		return c;

	}

	public static List<CursoDto> toCursoList(ResultSet rs) throws SQLException {
		List<CursoDto> cursos = new ArrayList<>();

		while (rs.next()) {
			cursos.add(resultSetToCursoDto(rs));
		}

		return cursos;
	}

	public static CursoDto resultSetToCursoDto(ResultSet rs) throws SQLException {
		CursoDto newCursoDto = new CursoDto();

		newCursoDto.codigoCurso = rs.getInt("IdCurso");
		newCursoDto.titulo = rs.getString("Titulo");
		
		
		if(rs.getString("FechaApertura") != null){
			newCursoDto.fechaApertura = LocalDate.parse(rs.getString("FechaApertura"));			
		}
		
		if(rs.getString("FechaCierre") != null){
			newCursoDto.fechaCierre = LocalDate.parse(rs.getString("FechaCierre"));			
		}
		
		if(rs.getString("FechaImpartir") != null) {
			newCursoDto.fechaInicio = LocalDate.parse(rs.getString("FechaImpartir"));			
		}
		
		
		
		newCursoDto.plazasDisponibles = rs.getInt("Plazas");
		newCursoDto.precio = rs.getDouble("Precio");

		boolean isCursoAbierto = CursoCRUD.isCursoAbierto(newCursoDto);

		newCursoDto.estado = isCursoAbierto ? CursoDto.CURSO_ABIERTO : CursoDto.CURSO_PLANIFICADO;

		return newCursoDto;

	}

	private static CursoDto resultSetToInscripcionDto(ResultSet rs) throws SQLException {
		CursoDto c = new CursoDto();

		c.codigoCurso = rs.getInt("IDCURSO");
		c.titulo = rs.getString("TITULO");
		c.fechaInicio = LocalDate.parse(rs.getString("FECHAIMPARTIR"));
		c.plazasDisponibles = rs.getInt("PLAZAS");
		c.precio = rs.getDouble("PRECIO");
		c.fechaApertura = LocalDate.parse(rs.getString("FECHAAPERTURA"));
		c.fechaCierre = LocalDate.parse(rs.getString("FECHACIERRE"));

		return c;
	}

	public static ArrayList<Colegiado_Inscripcion> toInscripcionColegiadosList(ResultSet rs) throws SQLException {
		ArrayList<Colegiado_Inscripcion> inscripciones = new ArrayList<Colegiado_Inscripcion>();
		while (rs.next()) {
			inscripciones.add(resultSetToInscripcionColegiadosDto(rs));
		}
		return inscripciones;
	}

	private static Colegiado_Inscripcion resultSetToInscripcionColegiadosDto(ResultSet rs) throws SQLException {
		InscripcionColegiadoDto I = new InscripcionColegiadoDto();
		ColegiadoDto c = new ColegiadoDto();

		c.nombre = rs.getString("nombre");
		c.apellidos = rs.getString("apellidos");
		I.cantidadPagar = rs.getDouble("CantidadPagar");
		I.estado = rs.getString("ESTADO");
		I.fechaSolicitud = LocalDate.parse(rs.getString("FechaPreInscripcion"));

		return new Colegiado_Inscripcion(c, I);

	}


}
