package ui.components.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import ui.components.LookAndFeel;

/**
 * Botón personalizado por defecto en la aplicación.
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class DefaultButton extends JButton {

	private static final long serialVersionUID = 3999829096412848103L;

	public static final int DEFAULT_WIDTH = 300;
	public static final int DEFAULT_HEIGHT = 175;

	public static final Color DEFAULT_BACKGROUND = LookAndFeel.SECONDARY_COLOR;
	public static final Color DEFAULT_FOREGROUND = Color.WHITE;

	public static final Color DEFAULT_DISABLED_BACKGROUND = LookAndFeel.DISABLED_BUTTON_COLOR;
	public static final Color DEFAULT_DISABLED_FOREGORUND = Color.DARK_GRAY;

	// Boton small
	private static final int SMALL_TYPE_WIDTH = 20;
	private static final int SMALL_TYPE_HEIGHT = 35;

	// Boton small
	private static final int REGULAR_TYPE_WIDTH = 150;
	private static final int REGULAR_TYPE_HEIGHT = 50;

	// Boton small
	private static final int VENTANA_TYPE_WIDTH = 250;
	private static final int VENTANA_TYPE_HEIGHT = 80;

	private String[] sizes = new String[] { "small", "regular", "ventana" };

	/**
	 * Botón por defecto, sólo con texto y estilos personalizados
	 * 
	 * @param text
	 */
	public DefaultButton(final String text) {
		super(text);

		// Colores

		this.setBackground(DEFAULT_BACKGROUND);
		this.setForeground(DEFAULT_FOREGROUND);

		// Padding
		this.setBorder(new EmptyBorder(20, 70, 20, 70));

		// Tipografia
		this.setFont(LookAndFeel.PRIMARY_FONT);

		this.setBorderPainted(false);
//		this.setAlignmentX(CENTER);
		this.setAlignmentY(CENTER);
	}

	/**
	 * Botón accesible y configurable padding horizonal y vertical.
	 * 
	 * @param text
	 * @param width
	 * @param height
	 * @param actionCommand
	 * @param mnemonic
	 */
	public DefaultButton(final String text, final String actionCommand, final char mnemonic) {
		this(text);

		setButtonType("ventana");
		this.setActionCommand(actionCommand);
		this.setFocusPainted(false);
		this.setMnemonic(mnemonic);
	}

	/**
	 * Botón accesible y configurable padding horizonal y vertical, con tipo
	 * seleccionable.
	 * 
	 * @param text
	 * @param type          small | regular | ventana
	 * @param actionCommand
	 * @param mnemonic
	 */
	public DefaultButton(final String text, final String type, final String actionCommand, final char mnemonic,
			final ButtonColor buttonColorStyle) {
		this(text, actionCommand, mnemonic);
		setButtonType(type);
		setButtonColor(buttonColorStyle);
	}

	/**
	 * Establece el tamaño del botón, en función de los tipos establecidos en
	 * {@link #sizes}
	 * 
	 * @param type small | regular | ventana. Escribir una de las tres opciones en
	 *             función del tipo de botón deseado.
	 */
	private void setButtonType(final String type) {

		// Si el tipo de boton no se corresponde con ningun tipo establecido
		// se asigna el boton tipo ventana.

		if (!type.equalsIgnoreCase(sizes[0]) || type.equalsIgnoreCase(sizes[1]) || type.equalsIgnoreCase(sizes[2])) {
			this.setPreferredSize(new Dimension(VENTANA_TYPE_WIDTH, VENTANA_TYPE_HEIGHT));
			this.setBounds(new Rectangle(VENTANA_TYPE_WIDTH, VENTANA_TYPE_HEIGHT));
		}

		// Boton pequeño
		if (type.equalsIgnoreCase(sizes[0])) {
			this.setPreferredSize(new Dimension(SMALL_TYPE_WIDTH, SMALL_TYPE_HEIGHT));
			this.setBounds(new Rectangle(SMALL_TYPE_WIDTH, SMALL_TYPE_HEIGHT));
			this.setFont(LookAndFeel.REGULAR_BUTTON_FONT);

			// Boton regular
		} else if (type.equalsIgnoreCase(sizes[1])) {
			this.setPreferredSize(new Dimension(REGULAR_TYPE_WIDTH, REGULAR_TYPE_HEIGHT));
			this.setBounds(new Rectangle(REGULAR_TYPE_WIDTH, REGULAR_TYPE_HEIGHT));
			this.setFont(LookAndFeel.REGULAR_BUTTON_FONT);

			// Boton ventana
		} else if (type.equalsIgnoreCase(sizes[1])) {
			this.setPreferredSize(new Dimension(VENTANA_TYPE_WIDTH, VENTANA_TYPE_HEIGHT));
			this.setBounds(new Rectangle(VENTANA_TYPE_WIDTH, VENTANA_TYPE_HEIGHT));
		}
	}

	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			this.setBackground(DEFAULT_BACKGROUND);
			this.setForeground(DEFAULT_FOREGROUND);
		} else {
			this.setBackground(DEFAULT_DISABLED_BACKGROUND);
		}
	}

	private void setButtonColor(final ButtonColor seletedType) {
		if (seletedType == ButtonColor.NORMAL) {
			this.setBackground(DEFAULT_BACKGROUND);
			this.setForeground(DEFAULT_FOREGROUND);

		} else if (seletedType == ButtonColor.CANCEL) {
			this.setBackground(LookAndFeel.PRIMARY_COLOR);
			this.setForeground(Color.WHITE);

		} else if (seletedType == ButtonColor.INFO) {
			this.setBackground(LookAndFeel.TERTIARY_COLOR);
			this.setForeground(LookAndFeel.SECONDARY_COLOR);
			this.setBorder(new LineBorder(LookAndFeel.TERTIARY_COLOR_DARK, 2));
		}
	}

}