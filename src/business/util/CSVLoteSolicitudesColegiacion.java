package business.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import business.BusinessException;
import persistence.colegiado.ColegiadoDto;

/**
 * Procesador de ficheros CSV.
 * 
 * @author Francisco Coya
 * @version 1.0.0
 *
 */
public class CSVLoteSolicitudesColegiacion {

	public static final String DEFAULT_SOLICITUDES_COLEGIACION_FOLDER = "lotes_colegiacion/";
	public static final String DEFAULT_SOLICITUDES_COLEGIACION_FILENAME = "lote";

	public static final String CSV_SEPARATOR = ";";
	public static final String CSV_LINE_END = "\n";
	public static final String TITULACION_COLEGIADO_SEPARATOR = ":";
	public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_16;

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
	 * @since HU. 19061
	 * @param solicitudesColegiacion Lista con todas las solicitudes de colegiacion.
	 */
	public static String generarLoteSolicitudesColegiacion(List<ColegiadoDto> solicitudesColegiacion) {

		String outputFilename = null;
		PrintWriter pW = null;

		// Lista de solicitudes ordenada por fecha de solicitud
		List<ColegiadoDto> solicitudesOrdenadas = solicitudesColegiacion.stream()
				.sorted((s1, s2) -> s1.fechaSolicitud.compareTo(s2.fechaSolicitud)).collect(Collectors.toList());

		try {
			eliminarLotesGeneradosAnteriormente();

			LocalDateTime currentDate = LocalDateTime.now();

			// Nombre de salida del fichero de lote generado
			outputFilename = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")).concat("_")
					.concat(DEFAULT_SOLICITUDES_COLEGIACION_FILENAME).concat(".csv");

			Path path = Paths.get(DEFAULT_SOLICITUDES_COLEGIACION_FOLDER + "/" + outputFilename);

			FileOutputStream file = new FileOutputStream(path.toString());

			pW = new PrintWriter(new OutputStreamWriter(file, DEFAULT_CHARSET));

			/*
			 * Cada línea tiene los siguientes campos: DNI, nombre apellidos, fecha
			 * solicitud, titulacion/es
			 */
			String cabecera = String.join(CSV_SEPARATOR, "DNI", "Nombre", "Apellidos", "Fecha de solicitud",
					"Titulación/es");
			pW.write(cabecera + CSV_LINE_END);

			for (ColegiadoDto col : solicitudesOrdenadas) {

				// Listado de titulaciones de un colegiado.
				String titulacionesColegiado = col.titulacion.stream().map(t -> String.valueOf(t))
						.collect(Collectors.joining(TITULACION_COLEGIADO_SEPARATOR));

				String fila = String.join(CSV_SEPARATOR, col.DNI, col.nombre, col.apellidos,
						col.fechaSolicitud.toString(), titulacionesColegiado);

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

	/**
	 * Vacía el directorio de los lotes de solicitudes de colegiacion.
	 * 
	 * @param folder
	 */
	private static void eliminarLotesGeneradosAnteriormente() {
		File destinationFolder = new File(DEFAULT_SOLICITUDES_COLEGIACION_FOLDER);

		if (destinationFolder.exists()) {
			Arrays.stream(Objects.requireNonNull(destinationFolder.listFiles())).filter(f -> !f.isDirectory())
					.forEach(File::delete);
		} else {
			destinationFolder.mkdir();
		}

	}

	/**
	 * 
	 * 
	 * @since HU. 19062
	 * @return
	 * @throws BusinessException
	 */
	public static List<ColegiadoDto> leerLoteSolicitudesColegiacion() throws BusinessException {
		BufferedReader br = null;

		List<ColegiadoDto> colegiadosLote = new ArrayList<>();

		File destinationFolder = new File(DEFAULT_SOLICITUDES_COLEGIACION_FOLDER);

		if (!destinationFolder.exists()) {
			throw new BusinessException("No hay lotes de solicitudes de colegiación.");
		}

		Optional<File> ficheroLote = Arrays.stream(Objects.requireNonNull(destinationFolder.listFiles())).findFirst();

		if (ficheroLote.isPresent()) {
			String line;

			try {
				br = new BufferedReader(new FileReader(ficheroLote.get(), DEFAULT_CHARSET));

				br.readLine();

				while ((line = br.readLine()) != null) {
					// Formato entrada: DNI ; Nombre apellidos
					List<String> content = Arrays.asList(line.split(CSV_SEPARATOR));

					ColegiadoDto colegiadoLineaLote = new ColegiadoDto();

					colegiadoLineaLote.DNI = content.get(0);
					colegiadoLineaLote.nombre = content.get(1);
					colegiadoLineaLote.apellidos = content.get(2);
					colegiadoLineaLote.fechaSolicitud = LocalDate.parse(content.get(3),
							DateTimeFormatter.ofPattern("yyyy-MM-dd"));

					if (content.size() == 5 && !content.get(4).trim().isEmpty()) {
						colegiadoLineaLote.titulacion = Arrays
								.asList(content.get(4).split(TITULACION_COLEGIADO_SEPARATOR));
					}

					colegiadosLote.add(colegiadoLineaLote);

				}

			} catch (IOException ioe) {
				System.err
						.println("Se produjo un error al leer el fichero de lote de solicitudes. " + ioe.getMessage());
				ioe.printStackTrace();
			}

		}

		return colegiadosLote;
	}
}
