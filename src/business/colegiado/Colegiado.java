package business.colegiado;

import java.util.List;

import business.BusinessException;
import business.util.Argument;
import business.util.CSVProcessor;
import business.util.GeneradorNumeroColegiado;
import business.util.MathUtils;
import persistence.colegiado.ColegiadoCrud;
import persistence.colegiado.ColegiadoDto;

public class Colegiado {
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
		return ColegiadoCrud.findAllSolicitudesAltaColegiados();
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
	 * 
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
	 * 
	 * En primer lugar, se obtiene el listado de solicitudes de colegiación hasta la
	 * fecha. El envío se realiza únicamente de TODAS las solicitudes, no se permite
	 * envío parcial de solicitudes.
	 * 
	 * Al ejecutar el envío, se genera un fichero en formato .CSV con los datos de
	 * cada solicitud. El fichero se persiste de forma local en un directorio
	 * denominado lotesColegiacion.
	 * 
	 * @see business.util.CSVProcessor#generarLoteSolicitudesColegiacion(List)
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

		return CSVProcessor.generarLoteSolicitudesColegiacion(solicitudesColegiacion);

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

		Argument.isNotNull(colegiado.DNI);
		Argument.isNotEmpty(colegiado.DNI);
		Argument.longitudNueve(colegiado.DNI);

		Argument.isNotNull(colegiado.nombre);
		Argument.isNotEmpty(colegiado.nombre);

		Argument.isNotNull(colegiado.apellidos);
		Argument.isNotEmpty(colegiado.apellidos);

		Argument.isNotNull(colegiado.poblacion);
		Argument.isNotEmpty(colegiado.poblacion);

		Argument.isNotNull(colegiado.centro);
		Argument.isNotEmpty(colegiado.centro);

		Argument.isNotNull(colegiado.titulacion);
		Argument.isNotEmpty(colegiado.titulacion);

		Argument.isPositive(colegiado.annio);

		Argument.isTrue(colegiado.numeroCuenta.length() == 12);

		Argument.isPositive(colegiado.telefono);

		Argument.longitudNueve(colegiado.telefono);

		Argument.menorQueMax(colegiado.annio);
	}

	public static String getMaxNumberColegiado() {
		return ColegiadoCrud.getMaxNumber();
	}

	private static void comprobarArgumentos(String dni) {
		Argument.isNotEmpty(dni);
		Argument.longitudNueve(dni);
	}

}