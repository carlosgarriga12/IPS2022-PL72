package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import persistence.colegiado.ColegiadoDto;
import persistence.curso.CursoCRUD;
import persistence.curso.CursoDto;
import persistence.inscripcionCursoFormacion.InscripcionCursoFormacionDto;

public class DtoAssembler {

	public static List<ColegiadoDto> toColegiadoList(ResultSet rs) throws SQLException {
		List<ColegiadoDto> colegiados = new ArrayList<>();

		while (rs.next()) {
			colegiados.add(resultSetToColegiadoDto(rs));
		}

		return colegiados;
	}
	
	public static List<InscripcionCursoFormacionDto> toInscripcionList(ResultSet rs) throws SQLException{
		List<InscripcionCursoFormacionDto> inscripciones = new ArrayList<InscripcionCursoFormacionDto>();
		while(rs.next()) {
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
		c.numeroTarjeta = rs.getInt("numeroTarjeta");
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
		newCursoDto.fechaInicio = LocalDate.parse(rs.getString("FechaApertura"));
		newCursoDto.plazasDisponibles = rs.getInt("Plazas");
		newCursoDto.precio = rs.getDouble("Precio");
		
		boolean isCursoAbierto = CursoCRUD.isCursoAbierto(newCursoDto);
		
		newCursoDto.estado = isCursoAbierto ? CursoDto.CURSO_ABIERTO : CursoDto.CURSO_PLANIFICADO;

		return newCursoDto;

	}
	
	private static InscripcionCursoFormacionDto resultSetToInscripcionDto(ResultSet rs) throws SQLException {
		InscripcionCursoFormacionDto i = new InscripcionCursoFormacionDto();
		CursoDto c = new CursoDto();
		
		c.codigoCurso = rs.getInt("ID_CURSO");
		c.titulo = rs.getString("TITULO");
		c.fechaInicio = LocalDate.parse(rs.getString("FECHAIMPARTIR"));
		c.plazasDisponibles = rs.getInt("PLAZAS");
		c.precio = rs.getDouble("PRECIO");
		
		i.curso = c;
		i.fechaApertura = LocalDate.parse(rs.getString("FECHAAPERTURA"));
		i.fechaCierre = LocalDate.parse(rs.getString("FECHACIERRE"));
		
		return i;
		
	}

}
