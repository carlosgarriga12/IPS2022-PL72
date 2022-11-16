package business.colegiado;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import business.BusinessException;
import business.util.Argument;
import business.util.CSVLoteSolicitudesColegiacion;
import business.util.GeneradorNumeroColegiado;
import business.util.MathUtils;
import business.util.StringUtils;
import persistence.colegiado.ColegiadoCrud;
import persistence.colegiado.ColegiadoDto;

public class Colegiado {

	public static final List<String> LISTADO_TITULACIONES_ADMITIDAS = new ArrayList<String>() {
		private static final long serialVersionUID = 3540528532189606581L;

		{
			add("ingenieria informatica");
			add("master en ingenieria");
			add("licenciado en informatica");
		}
	};

	public static final int MAX_LONGITUD_NUMERO_CUENTA = 16;

	/**
	 * Añade un nuevo colegiado al sistema con los datos del dto
	 * 
	 * @param colegiado que contiene la información
	 * @return colegiado con todos sus datos
	 * 
	 * @throws IllegalArgumentException cuando es null or cualquiera de sus campos
	 *                                  es null o vacío (strings) o el telefono no
	 *                                  tiene 9 números o es negativo, o titulacion
	 *                                  si no es 0, 1 o 2, o numero de tarjeta si es
	 *                                  negativo o no tiene 16 números, año debe ser
	 *                                  > 0, y dni tenga 9 caracteres
	 *                                  BusinessException cuando ya existe ese
	 *                                  colegiado (mismo dni)
	 */
	public static ColegiadoDto addColegiado(ColegiadoDto colegiado) throws BusinessException {
		comprobarArgumentos(colegiado);
		comprobarDni(colegiado);
		return ColegiadoCrud.addColegiado(colegiado);
	}

	/**
	 * Busca los colegiados de un año
	 * 
	 * @param año, debe ser mayor que 0
	 * @return lista de los colegiados del sistema por año
	 * 
	 *         no @throws BusinessException y lanza una IllegalArgumentException si
	 *         el DNI es vacío, null o no tiene 9 caracteres
	 */
	public static ColegiadoDto findColegiadoPorDni(String dni) throws BusinessException {
		comprobarArgumentos(dni);

		return ColegiadoCrud.findColegiadoDni(dni);
	}

	public static List<ColegiadoDto> findAllSolicitudesAltaColegiados() throws BusinessException {
		return ColegiadoCrud.findAllSolicitudesAltaColegiados().stream()
				.sorted((c1, c2) -> c2.fechaSolicitud.compareTo(c1.fechaSolicitud)).collect(Collectors.toList());
	}

	/**
	 * Obtiene la titulación del colegiado con el DNI indicado.
	 * 
	 * @param dni
	 * @return
	 * @throws BusinessException
	 */
	public static String findTitulacionColegiadoByDni(String dni) throws BusinessException {
		Argument.isNotNull(dni);
		Argument.isNotEmpty(dni);

		return ColegiadoCrud.findTitulacionColegiadoByDni(dni);
	}

	/**
	 * Consulta al ministerio para conocer la titulacion de un solicitante de
	 * ingreso para proceder al alta inmediata en el COIIPA.
	 * <p>
	 * Dado el solicitante con el dni indicado, se busca la titulación. Respuestas
	 * posibles: <code>
	 * 			0 ( Sin titulación ).
	 * 			1 ( Titulación en Ingeniería Informática ).
	 * 			2 ( Otras titulaciones ).
	 * 
	 * </code>
	 * 
	 * 
	 * @param dniSolicitante
	 * @return
	 * @since HU.18155
	 * @deprecated La funcion que reemplaza a esta funcion es
	 *             {@link #enviarLoteSolicitudesColegiacion}
	 * @throws BusinessException
	 */
	public static String simularConsultaMinisterio(String dniSolicitante) throws BusinessException {
		// String titulacion = findTitulacionColegiadoByDni(dniSolicitante);
		String res = "";

		int resp = MathUtils.generateRandomNumber(0, 2);
		res += getMensajeRespuestaMinisterioTitulacion(resp);

		if (resp == 1) {
			String num = updateNumColegiado(dniSolicitante);
			res += "\n El número de colegiado asignado es: " + num;
		}

		return res;
	}

	/**
	 * Realiza en el envío de un lote de solicitudes de colegiación.
	 * <p>
	 * En primer lugar, se obtiene el listado de solicitudes de colegiación hasta la
	 * fecha. El envío se realiza únicamente de TODAS las solicitudes, no se permite
	 * envío parcial de solicitudes.
	 * <p>
	 * Al ejecutar el envío, se genera un fichero en formato .CSV con los datos de
	 * cada solicitud. El fichero se persiste de forma local en un directorio
	 * denominado lotesColegiacion.
	 * 
	 * @see business.util.CSVLoteSolicitudesColegiacion#generarLoteSolicitudesColegiacion(List)
	 * @since HU. 19061
	 * @throws BusinessException Si la lista de solicitudes no es válida o se
	 *                           produce un error al listarla.
	 *
	 */
	public static String enviarLoteSolicitudesColegiacion() throws BusinessException {

		List<ColegiadoDto> solicitudesColegiacion = findAllSolicitudesAltaColegiados();

		if (solicitudesColegiacion.size() == 0) {
			throw new BusinessException("En este momento no hay solicitudes de colegiación para enviar.");
		}

		return CSVLoteSolicitudesColegiacion.generarLoteSolicitudesColegiacion(solicitudesColegiacion);

	}

	/**
	 * Recepciona un lote de solicitudes de colegiación.
	 * 
	 * Se lee el fichero .CSV del directorio "<code>lotes_colegiacion</code> y se
	 * obtiene una lista de DTOs de Colegiado.
	 * <p>
	 * Si el colegiado de la línea de lote leída está en posesión de las
	 * titulaciones comprendidas en {@link #LISTADO_TITULACIONES_ADMITIDAS}, el
	 * solicitante pasa a ser Colegiado.
	 * <p>
	 * En el proceso de Colegiación, al solicitante se le asignará un número de
	 * colegiado. Sin embargo, si el solicitante no cumple este criterio (No es
	 * apto), el estado de la solicitud pasa a estado <code>CANCELADO</code>
	 *
	 * 
	 * @see business.util.CSVLoteSolicitudesColegiacion#leerLoteSolicitudesColegiacion()
	 * @see business.util.GeneradorNumeroColegiado#generateNumber()
	 * @see business.util.StringUtils#normalizarCadenaTexto(String)
	 * 
	 * @since HU. 19062
	 * @throws BusinessException Si se produce algún error durante el proceso de
	 *                           lectura del fichero y carga de colegiados en la
	 *                           lista a procesar.
	 */
	public static List<ColegiadoDto> recepcionarLoteSolicitudesColegiacion() throws BusinessException {
		List<ColegiadoDto> colegiadosAdmitidos = new ArrayList<>();

		List<ColegiadoDto> loteColegiados = CSVLoteSolicitudesColegiacion.leerLoteSolicitudesColegiacion();

		if (loteColegiados.size() == 0) {
			throw new BusinessException("No hay lotes de colegiación para recepcionar");
		}

		for (ColegiadoDto col : loteColegiados) {

			boolean titulacionAdmitida = false;

			if (col.titulacion != null) {
				for (String t : col.titulacion) {

					if (LISTADO_TITULACIONES_ADMITIDAS.contains(StringUtils.normalizarCadenaTexto(t))
							&& titulacionAdmitida == false) {
						// Caso 1: El colegiado es apto: Posee al menos una de las titulaciones
						// admitidas

						ColegiadoDto colegiadoAllData = ColegiadoCrud.findColegiadoDni(col.DNI);

						String num = updateNumColegiado(col.DNI);
						colegiadoAllData.numeroColegiado = num;
						colegiadosAdmitidos.add(colegiadoAllData);
						titulacionAdmitida = true;

					}
				}

				// Caso 2: El solicitante no está es posesión de ninguna de las titulaciones
				// adminitidas.
				if (titulacionAdmitida == false) {
					ColegiadoCrud.updateEstadoColegiado(col, "CANCELADO");
				}

			} else {
				// Caso 3: El solicitante no está en posesión de ninguna titulacion
				ColegiadoCrud.updateEstadoColegiado(col, "CANCELADO");
			}
		}

		return colegiadosAdmitidos;
	}

	/**
	 * Devuelve el mensaje de la respuesta del ministerio en relación a la consulta
	 * de titulacion.
	 * 
	 * @param respuesta
	 * @return
	 * @throws BusinessException
	 */
	public static String getMensajeRespuestaMinisterioTitulacion(int respuesta) throws BusinessException {
		String msg = "";

		switch (respuesta) {
		case 0:
			msg = "Sin titulación";
			break;

		case 1:
			msg = "Titulación en Ingeniería Informática";
			break;

		case 2:
			msg = "Otras titulaciones.";
			break;
		}

		return msg;
	}

	/**
	 * Actualiza el número de colegiado con el DNI indicado
	 * 
	 * @param dni
	 * @return
	 * @throws BusinessException
	 */
	public static String updateNumColegiado(String dni) throws BusinessException {
		Argument.isNotEmpty(dni);

		ColegiadoDto colegiado = new ColegiadoDto();
		colegiado.DNI = dni;
		colegiado.numeroColegiado = GeneradorNumeroColegiado.generateNumber();

		ColegiadoCrud.updateNumColegiado(colegiado);

		return colegiado.numeroColegiado;
	}

	private static void comprobarDni(ColegiadoDto colegiado) throws BusinessException {
		checkDniSinRepetir(colegiado.DNI);
	}

	private static void checkDniSinRepetir(String dni) throws BusinessException {
		if (ColegiadoCrud.findColegiadoDni(dni) != null) {
			throw new BusinessException("No se puede añadir colegiados con el mismo dni");
		}
	}

	private static void comprobarArgumentos(ColegiadoDto colegiado) {
		Argument.isNotNull(colegiado);

		Argument.isNotEmpty(colegiado.DNI, "El DNI es obligatorio.");
		Argument.longitudNueve(colegiado.DNI);

		Argument.isNotEmpty(colegiado.nombre, "El nombre es obligatorio.");
		Argument.isNotEmpty(colegiado.apellidos, "Los apellidos son obligatorios.");
		Argument.isNotEmpty(colegiado.poblacion, "La población es obligatoria.");

		if (colegiado.titulacion.size() > 0) {
			Argument.isNotEmpty(colegiado.centro, "El centro es obligatorio.");
		}

		// Nota: La titulacion no se valida ya que puede no tener titulación.
//		validarTitulaciones(colegiado.titulacion);

		Argument.isPositive(colegiado.annio);
		Argument.isTrue(colegiado.numeroCuenta.length() == MAX_LONGITUD_NUMERO_CUENTA,
				"El número de cuenta ha de tener " + MAX_LONGITUD_NUMERO_CUENTA + " caracteres alfanuméricos.");
		Argument.isPositive(colegiado.telefono);
		Argument.longitudNueve(colegiado.telefono, "El número de teléfono ha de tener 9 dígitos.");
		Argument.menorQueMax(colegiado.annio, "El año ha de ser igual o superior al año en curso.");
	}

	/**
	 * Comprueba, en el caso de que el solicitante esté en posesión de una o más
	 * titulaciones, si el formato es el correcto.
	 * <p>
	 * Si el solicitante está en posesión de más de una titulación, ha de separar
	 * dichas titulaciones por
	 * {@link persistence.DtoAssembler#SEPARADOR_TITULACIONES}
	 *
	 * @param titulacion
	 */
	public static boolean validarTitulaciones(String titulacion) {
		boolean isValid = true;
		// Caso 1: Vacío --> Sin titulacion

		// Caso 2: Una titulacion
		if (titulacion.length() == 0) {
			Argument.isNotEmpty(titulacion);
			isValid = false;
		}

		// Caso 3: Dos o más titulaciones

		return isValid;

	}

	public static String getMaxNumberColegiado() {
		return ColegiadoCrud.getMaxNumber();
	}

	private static void comprobarArgumentos(String dni) {
		Argument.isNotEmpty(dni);
		Argument.longitudNueve(dni);
	}
}