package ui.main;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import ui.components.LookAndFeel;
import ui.components.placeholder.TextPlaceHolderCustom;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;

public class RegisterWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private MainWindow mainWindow;
	private String dni;
	private JLabel lblNewLabelDni;
	private JTextField textFieldDni;
	private JButton btnNewButtonOk;
	
	public RegisterWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle("COIIPA : Iniciar sesión");
		setBounds(300, 300, 391, 189);
		getContentPane().setLayout(null);
		getContentPane().add(getLblNewLabelDni());
		getContentPane().add(getTextFieldDni());
		getContentPane().add(getBtnNewButtonOk());
	}
	
	
	public String getDni() {
		while (dni == null) {
			if (dni != null) break;
		}
		return dni;
	}
	
	private JLabel getLblNewLabelDni() {
		if (lblNewLabelDni == null) {
			lblNewLabelDni = new JLabel("Introduzca el DNI:");
			lblNewLabelDni.setDisplayedMnemonic('I');
			lblNewLabelDni.setLabelFor(getTextFieldDni());
			lblNewLabelDni.setBounds(43, 29, 291, 38);
			lblNewLabelDni.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelDni.setFont(LookAndFeel.HEADING_2_FONT);
		}
		return lblNewLabelDni;
	}
	private JTextField getTextFieldDni() {
		if (textFieldDni == null) {
			textFieldDni = new JTextField();
			textFieldDni.setHorizontalAlignment(SwingConstants.LEFT);
			textFieldDni.setToolTipText("Introduzca su DNI");
			textFieldDni.setBounds(28, 93, 209, 23);
			textFieldDni.setColumns(10);
			TextPlaceHolderCustom.setPlaceholder("71778880C", textFieldDni);
		}
		return textFieldDni;
	}
	private JButton getBtnNewButtonOk() {
		if (btnNewButtonOk == null) {
			btnNewButtonOk = new JButton("OK");

			btnNewButtonOk.setForeground(Color.BLACK);
			btnNewButtonOk.setBackground(Color.WHITE);
			
			btnNewButtonOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (textFieldDni.getText()==null || textFieldDni.getText().isBlank() || textFieldDni.getText().length() != 9) {
						JOptionPane.showMessageDialog(null, "Introduce de nuevo el dni", "Error, formato incorrecto", JOptionPane.ERROR_MESSAGE);
						textFieldDni.setText(null);
					} else {
						dni = textFieldDni.getText();
						mainWindow.checkearDni(dni);
						dispose();
					}
				}
			});
			btnNewButtonOk.setToolTipText("Pulse para validar su DNI");
			btnNewButtonOk.setMnemonic('O');
			btnNewButtonOk.setBounds(282, 93, 52, 23);
		}
		return btnNewButtonOk;
	}
	
}
