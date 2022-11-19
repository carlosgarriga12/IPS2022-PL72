package ui.model.combo;

import persistence.colegiado.TipoColectivo;

/**
 * Modelo de ComboBox para los colectivos del COIIPA.
 * 
 * @see {@link #persistence.colegiado.TipoColectivo}
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class ColectivoComboModel {

	private String[] items;

	public ColectivoComboModel() {
		TipoColectivo[] tipos = TipoColectivo.values();

		items = new String[tipos.length];

		for (int i = 0; i < tipos.length; i++) {
			items[i] = business.util.StringUtils.capitalize(tipos[i].name());
		}
	}

	public String[] getColectivos() {
		return items;
	}

}
