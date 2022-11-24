package ui.components;

import java.awt.Color;
import java.awt.Font;

/**
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class LookAndFeel {

	public static final String[] AVAILABLE_LOOK_AND_FEELS = new String[] { "Metal", "System", "Motif", "GTK" };

	/* Reemplazar por null para establecer el Look & Feel por defecto */
	public static final String DEFAULT_LOOK_AND_FEEL = AVAILABLE_LOOK_AND_FEELS[1];
	/* Si se selecciona el Look & Feel 'Metal', se puede configurar el tema */
	public static final String[] AVAILABLE_METAL_THEMES = new String[] { "DefaultMetal", "Ocean" };
	public static final String METAL_THEME = AVAILABLE_METAL_THEMES[1];


	// Typography styles
	public static final String TYPOGRAPHY = "Poppins";
	public static final Font PRIMARY_FONT = new Font(TYPOGRAPHY, Font.PLAIN, 14);
	public static final Font HEADING_1_FONT = new Font(TYPOGRAPHY, Font.PLAIN, 24);
	public static final Font HEADING_2_FONT = new Font(TYPOGRAPHY, Font.PLAIN, 18);
	public static final Font HEADING_3_FONT = new Font(TYPOGRAPHY, Font.PLAIN, 16);
	public static final Font LABEL_FONT = new Font(TYPOGRAPHY, Font.PLAIN, 14);
	public static final Font REGULAR_BUTTON_FONT = new Font(TYPOGRAPHY, Font.PLAIN, 12);

	// Color palette
	public static final Color PRIMARY_COLOR = new Color(193, 18, 21); // "#c1121f"
	public static final Color PRIMARY_COLOR_DARK = new Color(120, 0, 0); // "#780000"

	public static final Color SECONDARY_COLOR = new Color(0, 48, 73); // "#003049"
	public static final Color SECONDARY_COLOR_DARK = new Color(0, 117, 179); // "#003049"

	public static final Color TERTIARY_COLOR = new Color(253, 240, 213);// "#fdf0d5";
	public static final Color TERTIARY_COLOR_DARK = new Color(219, 151, 10);// "#fdf0d5";

	public static final Color SUCCESS_COLOR = new Color(199, 249, 204);// "#c7f9cc";
	public static final Color SUCCESS_COLOR_DARK = new Color(17, 161, 32);// "#11a120";

	public static final Color ERROR_COLOR = new Color(239, 35, 60);// "#ef233c";
	public static final Color ERROR_COLOR_DARK = new Color(217, 4, 41);// "#d90429";

	public static final Color DISABLED_BUTTON_COLOR = new Color(217, 217, 217);

	// Table styles
	public static final int ROW_HEIGHT = 50;

}