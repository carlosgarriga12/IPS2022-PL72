package business.colegiado;

import business.BusinessException;
import persistence.colegiado.ColegiadoDto;

public interface ColegiadoService {

	/**
	 * Añade un nuevo colegiado al sistema con los datos del dto
	 * 
	 * @param colegiado que contiene la información
	 * @return colegiado con todos sus datos
	 * @throws IllegalArgumentException cuando es null or cualquiera de sus campos
	 *                                  es null o vacío (strings) o el telefono no
	 *                                  tiene 9 números o es negativo, o titulacion
	 *                                  si no es 0, 1 o 2, o numero de tarjeta si es
	 *                                  negativo o no tiene 16 números, año debe ser
	 *                                  > 0, y dni tenga 9 caracteres
	 *                                  BusinessException cuando ya existe ese
	 *                                  colegiado (mismo dni)
	 */
	ColegiadoDto addColegiado(ColegiadoDto colegiado) throws BusinessException;

	/**
	 * Busca los colegiados de un año
	 * 
	 * @param año, debe ser mayor que 0
	 * @return lista de los colegiados del sistema por año
	 * 
	 *         no @throws BusinessException y lanza una IllegalArgumentException si
	 *         el DNI es vacío, null o no tiene 9 caracteres
	 */
	ColegiadoDto findColegiadoPorDni(String DNI) throws BusinessException;

	/**
	 * Obtiene la titulacion de un colegiado dado su dni.
	 * 
	 * @param dni
	 * @return 0 si no tiene titulación, 1 si es titulado en Ingeniería Informática
	 *         y 2 para otras titulaciones.
	 * 
	 * @throws BusinessException
	 * @throws IllegalArgumentException Si el dni no es válido.
	 * <a>business.util.Argument</a>
	 * 
	 */
	int findTitulacionByDni(final String dni) throws BusinessException;
}
