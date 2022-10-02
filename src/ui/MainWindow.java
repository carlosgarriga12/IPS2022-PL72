package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -8203812316779660921L;
	
	private JPanel mainPanel;
	private JLabel lblTitle;
	private JButton btnSecretaria;
	private JButton btnSolicitud;
	private JTextField txtDNI;
	private JLabel lblDNI;
	private JPanel pnHome;
	private JPanel pnLogin;
	private JPanel pnLoginNorth;
	private JLabel lbLoginTitle;
	private JPanel pnLoginCenter;
	private JPanel pnLoginFields;
	private JLabel lbLoginUsername;
	private JTextField txLoginUsername;
	private JPanel pnProgramAccess;
	private JPanel pnCoursesList;
	private JScrollPane spCoursesList;
	private JTable tbCoursesList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1126, 710);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		mainPanel.setLayout(new CardLayout(0, 0));

		// TODO: Cambiar orden para ver al arrancar el programa
		mainPanel.add(getPnCoursesList(), "coursesTablePanel");
		mainPanel.add(getPnLogin(), "loginPanel");
		mainPanel.add(getPnHome(), "homePanel");
		mainPanel.add(getPnProgramAccess(), "programAccessPanel");
		
		// Centrar la ventana
		this.setLocationRelativeTo(null);
	}

	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Bienvenidos a la aplicaci\u00F3n del COIIPA");
			lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
		}
		return lblTitle;
	}

	private JButton getBtnSecretaria() {
		if (btnSecretaria == null) {
			btnSecretaria = new JButton("Secretaria");
			btnSecretaria.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
		}
		return btnSecretaria;
	}

	private JButton getBtnSolicitud() {
		if (btnSolicitud == null) {
			btnSolicitud = new JButton("Solicitud de Alta");
		}
		return btnSolicitud;
	}

	private JTextField getTxtDNI() {
		if (txtDNI == null) {
			txtDNI = new JTextField();
			txtDNI.setColumns(10);
		}
		return txtDNI;
	}

	private JLabel getLblDNI() {
		if (lblDNI == null) {
			lblDNI = new JLabel("DNI");
		}
		return lblDNI;
	}

	private JPanel getPnHome() {
		if (pnHome == null) {
			pnHome = new JPanel();
			pnHome.setLayout(new BorderLayout(0, 0));

			pnHome.add(getLblTitle(), BorderLayout.NORTH);
			pnHome.add(getBtnSecretaria());
			pnHome.add(getBtnSolicitud());
			pnHome.add(getTxtDNI());
			pnHome.add(getLblDNI());
		}
		return pnHome;
	}

	private JPanel getPnLogin() {
		if (pnLogin == null) {
			pnLogin = new JPanel();
			pnLogin.setName("panelLogin");
			pnLogin.setLayout(new BorderLayout(0, 0));
			pnLogin.add(getPnLoginNorth(), BorderLayout.NORTH);
			pnLogin.add(getPnLoginCenter(), BorderLayout.CENTER);
		}
		return pnLogin;
	}

	private JPanel getPnLoginNorth() {
		if (pnLoginNorth == null) {
			pnLoginNorth = new JPanel();
			pnLoginNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnLoginNorth.add(getLbLoginTitle());
		}
		return pnLoginNorth;
	}

	private JLabel getLbLoginTitle() {
		if (lbLoginTitle == null) {
			lbLoginTitle = new JLabel("Iniciar sesión");
		}
		return lbLoginTitle;
	}

	private JPanel getPnLoginCenter() {
		if (pnLoginCenter == null) {
			pnLoginCenter = new JPanel();
			pnLoginCenter.add(getPnLoginFields());
		}
		return pnLoginCenter;
	}

	private JPanel getPnLoginFields() {
		if (pnLoginFields == null) {
			pnLoginFields = new JPanel();
			pnLoginFields.setLayout(new GridLayout(0, 1, 0, 0));
			pnLoginFields.add(getLbLoginUsername());
			pnLoginFields.add(getTxLoginUsername());
		}
		return pnLoginFields;
	}

	private JLabel getLbLoginUsername() {
		if (lbLoginUsername == null) {
			lbLoginUsername = new JLabel("Usuario");
			lbLoginUsername.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return lbLoginUsername;
	}

	private JTextField getTxLoginUsername() {
		if (txLoginUsername == null) {
			txLoginUsername = new JTextField();
			txLoginUsername.setToolTipText("Intro");
			txLoginUsername.setColumns(10);
		}
		return txLoginUsername;
	}
	private JPanel getPnProgramAccess() {
		if (pnProgramAccess == null) {
			pnProgramAccess = new JPanel();
		}
		return pnProgramAccess;
	}
	private JPanel getPnCoursesList() {
		if (pnCoursesList == null) {
			pnCoursesList = new JPanel();
			pnCoursesList.setLayout(new BorderLayout(0, 0));
			pnCoursesList.add(getSpCoursesList(), BorderLayout.NORTH);
			pnCoursesList.add(getTbCoursesList(), BorderLayout.CENTER);
		}
		return pnCoursesList;
	}
	private JScrollPane getSpCoursesList() {
		if (spCoursesList == null) {
			spCoursesList = new JScrollPane(tbCoursesList);
		}
		return spCoursesList;
	}
	private JTable getTbCoursesList() {
		if (tbCoursesList == null) {
			tbCoursesList = new JTable();
		}
		return tbCoursesList;
	}
}
