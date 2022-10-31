package business.util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import persistence.colegiado.ColegiadoDto;

/**
 * Procesador de ficheros CSV.
 * 
 * @author Francisco Coya
 * @version 1.0.0
 *
 */
public class CSVProcessor {

	/*
	 * Nombre por defecto del directorio que almacena los lotes de solicitudes de
	 * colegiacion
	 */
	public static final String DEFAULT_SOLICITUDES_COLEGIACION_FOLDER = "lotes_colegiacion/";
	public static final String DEFAULT_SOLICITUDES_COLEGIACION_FILENAME = "lote";

	public static final String CSV_SEPARATOR = ";";
	public static final String CSV_LINE_END = "\n";

	/**
	 * Genera un fichero en formato CSV para el lote de solicitudes de colegiación.
	 * 
	 * El fichero ha de contener los siguientes campos: DNI, nombre completo, fecha
	 * de solicitud, titulacion/es de cada solicitante.
	 * 
	 * Se guardará la fecha de generación de dicho fichero y se nombrará según a
	 * esta.
	 * 
	 * El fichero se sobreescribe cada vez que se envía el lote.
	 * 
	 * @param solicitudesColegiacion Lista con todas las solicitudes de colegiacion.
	 */
	public static String generarLoteSolicitudesColegiacion(List<ColegiadoDto> solicitudesColegiacion) {

		String outputFilename = null;

		// Lista de solicitudes ordenada por fecha de solicitud
		List<ColegiadoDto> solicitudesOrdenadas = solicitudesColegiacion.stream()
				.sorted((s1, s2) -> s1.fechaSolicitud.compareTo(s2.fechaSolicitud)).collect(Collectors.toList());

		PrintWriter pW = null;

		try {
			LocalDateTime currentDate = LocalDateTime.now();

			// Nombre de salida del fichero de lote generado
			outputFilename = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")).concat("_")
					.concat(DEFAULT_SOLICITUDES_COLEGIACION_FILENAME).concat(".csv");

			Path path = Paths.get(DEFAULT_SOLICITUDES_COLEGIACION_FOLDER + "/" + outputFilename);

			File file = new File(path.toString());

			pW = new PrintWriter(file);

			/*
			 * Cada línea tiene los siguientes campos: DNI, nombre apellidos, fecha
			 * solicitud, titulacion/es
			 */
			String cabecera = String.join(CSV_SEPARATOR, "DNI", "Nombre completo", "Fecha de solicitud",
					"Titulación/es");
			pW.write(cabecera + CSV_LINE_END);

			for (ColegiadoDto col : solicitudesOrdenadas) {
				String fila = String.join(CSV_SEPARATOR, col.DNI, col.nombre + " " + col.apellidos,
						col.fechaSolicitud.toString(), col.titulacion);

				pW.write(fila + CSV_LINE_END);
			}

		} catch (IOException e) {
			System.err.println("Se produjo un error al generar el fichero de lotes de solicitudes de colegiación. "
					+ e.getMessage());
			e.printStackTrace();

		} finally {
			pW.flush();
			pW.close();
		}

		return outputFilename;
	}
	
	public static void verLoteGenerado() {
		
	}
}
