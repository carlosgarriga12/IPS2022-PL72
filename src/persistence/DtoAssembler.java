package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import persistence.colegiado.ColegiadoDto;
import persistence.curso.CursoDto;

public class DtoAssembler {

	public static List<ColegiadoDto> toColegiadoList(ResultSet rs) throws SQLException {
		List<ColegiadoDto> colegiados = new ArrayList<>();

		while (rs.next()) {
			colegiados.add(resultSetToColegiadoDto(rs));
		}

		return colegiados;
	}

	private static ColegiadoDto resultSetToColegiadoDto(ResultSet rs) throws SQLException {
		ColegiadoDto c = new ColegiadoDto();

		c.DNI = rs.getString("DNI");
		c.nombre = rs.getString("nombre");
		c.apellidos = rs.getString("apellidos");
		c.poblacion = rs.getString("poblacion");
		c.titulacion = rs.getInt("titulacion");
		c.centro = rs.getString("centro");
		c.annio = rs.getInt("annio");
		c.numeroTarjeta = rs.getInt("numeroTarjeta");
		c.fechaSolicitud = LocalDate.parse(rs.getString("fechaSolicitud"));
		c.numeroColegiado = rs.getString("numeroColegiado");

		return c;

	}

	public static List<CursoDto> toCursoList(ResultSet rs) throws SQLException {
		List<CursoDto> cursos = new ArrayList<>();

		while (rs.next()) {
			cursos.add(resultSetToCursoDto(rs));
		}

		return cursos;
	}

	private static CursoDto resultSetToCursoDto(ResultSet rs) throws SQLException {
		CursoDto newCursoDto = new CursoDto();

		newCursoDto.titulo = rs.getString("titulo");
		newCursoDto.fechaInicio = LocalDate.parse(rs.getString("FechaImpartir"));
		newCursoDto.plazasDisponibles = rs.getInt("Plazas");
		newCursoDto.precio = rs.getDouble("Precio");
		// newCursoDto.estado = rs.getString("ESTADO");

		return newCursoDto;

	}

}
