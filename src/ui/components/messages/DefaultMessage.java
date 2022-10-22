package ui.components.messages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import ui.components.LookAndFeel;

/**
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class DefaultMessage extends JPanel {

	private static final long serialVersionUID = 5170069250639897225L;

	private JLabel messageLabel;
	private MessageType type;
	private String msg;

	public DefaultMessage(final String msg) {
		this.msg = msg;
		this.setLayout(new BorderLayout(0, 0));
		this.add(getLbCustomFormMessage());
	}

	public DefaultMessage(final String msg, final MessageType type) {
		this(msg);
		this.type = type;
		setMessageColor(this.type);
	}

	private JLabel getLbCustomFormMessage() {
		if (messageLabel == null) {
			messageLabel = new JLabel(this.msg);
			messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
			messageLabel.setForeground(LookAndFeel.TERTIARY_COLOR_DARK);
			messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return messageLabel;
	}

	/**
	 * Establece el color del mensaje en función del tipo de este.
	 * 
	 * @param type
	 */
	public void setMessageColor(final MessageType type) {
		if (type == MessageType.SUCCESS) {
			changeAllColors(LookAndFeel.SUCCESS_COLOR, LookAndFeel.SUCCESS_COLOR_DARK);

		} else if (type == MessageType.ERROR) {
			changeAllColors(LookAndFeel.ERROR_COLOR, Color.WHITE);

		} else if (type == MessageType.INFO) {
			changeAllColors(LookAndFeel.TERTIARY_COLOR, LookAndFeel.TERTIARY_COLOR_DARK);
		}
	}

	private void changeAllColors(final Color colorLight, final Color colorDark) {
		this.setBackground(colorLight);
		this.setForeground(colorDark);
		this.setBorder(new LineBorder(colorDark, 2, true));

		messageLabel.setForeground(colorDark);
	}

	public void setMessage(final String msg) {
		messageLabel.setText(msg);
	}

}
