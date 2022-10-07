package ui.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

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
	private JPanel pnSolicitudColegiado;
	private JPanel pnSolicitudColegiadoNorte;
	private JPanel pnSolicitudColegiadoCentro;
	private JLabel lbTitulo;
	private JPanel pnSolicitudDatosNorte;
	private JPanel pnSolicitudDatosCentro;
	private JLabel lbRellenarDatos;
	private JPanel panelDatosSur;
	private JButton btnLimpiar;
	private JButton btnFinalizar;
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JTextField textFieldDni;
	private JLabel lblNewLabelNAD;
	private JTextField textFieldPoblacion;
	private JTextField textFieldTelefono;
	private JTextField textFieldTitulo;
	private JTextField textFieldCentro;
	private JTextField textFieldAño;
	private JPanel panelBanco;
	private JLabel lblParaFinalizarDebe;
	private JTextField textFieldNTarjeta;

	private JLabel lblNewLabelNumeroTarjeta;

	private JPanel panelDatosPersonales;

	private JLabel lblNewLabelPob;

	private JLabel lblNewLabelTelefono;

	private JPanel panelDatosAcademicos;

	private JLabel lblNewLabelDatAcademicos;

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (confirmarCancelacion()) {
					System.exit(0);
				}
			}
			@Override
			public void windowOpened(WindowEvent e) {
				textFieldNombre.grabFocus();
			}
		});
		setTitle("COIIPA : Gestión de servicios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 717, 415);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		mainPanel.setLayout(new CardLayout(0, 0));

		// TODO: Cambiar orden para ver al arrancar el programa
		mainPanel.add(getPnSolicitudColegiado(), "solicitudColegiadoPanel");
		mainPanel.add(getPnHome(), "homePanel");
		mainPanel.add(getPnCoursesList(), "coursesTablePanel");
		mainPanel.add(getPnLogin(), "loginPanel");
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
	private JPanel getPnSolicitudColegiado() {
		if (pnSolicitudColegiado == null) {
			pnSolicitudColegiado = new JPanel();
			pnSolicitudColegiado.setLayout(null);
			pnSolicitudColegiado.add(getPnSolicitudColegiadoNorte());
			pnSolicitudColegiado.add(getPnSolicitudColegiadoCentro());
			pnSolicitudColegiado.add(getPanelDatosSur_1());
		}
		return pnSolicitudColegiado;
	}
	private JPanel getPnSolicitudColegiadoNorte() {
		if (pnSolicitudColegiadoNorte == null) {
			pnSolicitudColegiadoNorte = new JPanel();
			pnSolicitudColegiadoNorte.setBounds(0, 0, 691, 24);
			pnSolicitudColegiadoNorte.setForeground(SystemColor.desktop);
			pnSolicitudColegiadoNorte.add(getLbTitulo());
		}
		return pnSolicitudColegiadoNorte;
	}
	private JPanel getPnSolicitudColegiadoCentro() {
		if (pnSolicitudColegiadoCentro == null) {
			pnSolicitudColegiadoCentro = new JPanel();
			pnSolicitudColegiadoCentro.setBounds(0, 24, 691, 297);
			pnSolicitudColegiadoCentro.setLayout(new BorderLayout(0, 0));
			pnSolicitudColegiadoCentro.add(getPnSolicitudDatosNorte(), BorderLayout.NORTH);
			pnSolicitudColegiadoCentro.add(getPnSolicitudDatosCentro(), BorderLayout.CENTER);
		}
		return pnSolicitudColegiadoCentro;
	}
	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel("Solicitud de alta para ser colegiado en el COIIPA");
			lbTitulo.setFont(new Font("Arial Black", Font.BOLD, 11));
		}
		return lbTitulo;
	}
	private JPanel getPnSolicitudDatosNorte() {
		if (pnSolicitudDatosNorte == null) {
			pnSolicitudDatosNorte = new JPanel();
			pnSolicitudDatosNorte.add(getLbRellenarDatos());
		}
		return pnSolicitudDatosNorte;
	}
	private JPanel getPnDatosPersonales() {
		if (panelDatosPersonales == null) {
			panelDatosPersonales = new JPanel();
			panelDatosPersonales.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Datos personales", TitledBorder.CENTER, TitledBorder.TOP, null, Color.GRAY));
			panelDatosPersonales.setBounds(0, 0, 691, 118);
			panelDatosPersonales.setLayout(null);
			panelDatosPersonales.add(getLblNewLabelNAD());
			panelDatosPersonales.add(getTextFieldNombre());
			panelDatosPersonales.add(getTextFieldApellidos());
			panelDatosPersonales.add(getTextFieldDni());
			panelDatosPersonales.add(getLbPoblacion());
			panelDatosPersonales.add(getTxFieldPoblacion());
			panelDatosPersonales.add(getTxFieldTelefono());
			panelDatosPersonales.add(getLbTelefono());
		}
		return panelDatosPersonales;
	}
	private JLabel getLbPoblacion() {
		if (lblNewLabelPob == null) {
			lblNewLabelPob = new JLabel("Población:");
			lblNewLabelPob.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelPob.setToolTipText("Introduzca su población");
			lblNewLabelPob.setBounds(23, 65, 59, 14);
		}
		return lblNewLabelPob;
	}
	private JTextField getTxFieldPoblacion() {
		if (textFieldPoblacion == null) {
			textFieldPoblacion = new JTextField();
			textFieldPoblacion.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldPoblacion.setColumns(10);
			textFieldPoblacion.setBounds(100, 62, 148, 20);
		}
		return textFieldPoblacion;
	}
	private JTextField getTxFieldTelefono() {
		if (textFieldTelefono == null) {
			textFieldTelefono = new JTextField();
			textFieldTelefono.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTelefono.setToolTipText("Introduzca su teléfono");
			textFieldTelefono.setColumns(10);
			textFieldTelefono.setBounds(155, 85, 127, 20);
		}
		return textFieldTelefono;
	}
	private JLabel getLbTelefono() {
		if (lblNewLabelTelefono == null) {
			lblNewLabelTelefono = new JLabel("Teléfono de contacto:");
			lblNewLabelTelefono.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelTelefono.setBounds(23, 88, 139, 14);
		}
		return lblNewLabelTelefono;
	}
	private JPanel getPnDatosAcademicos() {
		if (panelDatosAcademicos == null) {
			panelDatosAcademicos = new JPanel();
			panelDatosAcademicos.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Datos acad\u00E9micos", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(128, 128, 128)));
			panelDatosAcademicos.setLayout(null);
			panelDatosAcademicos.setBounds(0, 116, 691, 99);
			panelDatosAcademicos.add(getLbDatAcademicos());
			panelDatosAcademicos.add(getTxFieldTitulo());
			panelDatosAcademicos.add(getTxFieldCentro());
			panelDatosAcademicos.add(getTxFieldAño());
		}
		return panelDatosAcademicos;
	}
	private JLabel getLbDatAcademicos() {
		if (lblNewLabelDatAcademicos == null) {
			lblNewLabelDatAcademicos = new JLabel("Introduzca sus datos académicos (titulación, centro, año):");
			lblNewLabelDatAcademicos.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelDatAcademicos.setBounds(192, 23, 335, 14);
		}
		return lblNewLabelDatAcademicos;
	}
	private JTextField getTxFieldTitulo() {
		if (textFieldTitulo == null) {
			textFieldTitulo = new JTextField();
			textFieldTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTitulo.setToolTipText("Teclee la titulación");
			textFieldTitulo.setColumns(10);
			textFieldTitulo.setBounds(32, 52, 227, 20);
		}
		return textFieldTitulo;
	}
	private JTextField getTxFieldCentro() {
		if (textFieldCentro == null) {
			textFieldCentro = new JTextField();
			textFieldCentro.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldCentro.setToolTipText("Escriba su centro educativo");
			textFieldCentro.setColumns(10);
			textFieldCentro.setBounds(283, 52, 288, 20);
		}
		return textFieldCentro;
	}
	private JTextField getTxFieldAño() {
		if (textFieldAño == null) {
			textFieldAño = new JTextField();
			textFieldAño.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldAño.setToolTipText("Escriba el año");
			textFieldAño.setColumns(10);
			textFieldAño.setBounds(598, 52, 67, 20);
		}
		return textFieldAño;
	}
	private JPanel getPnSolicitudDatosCentro() {
		if (pnSolicitudDatosCentro == null) {
			pnSolicitudDatosCentro = new JPanel();
			pnSolicitudDatosCentro.setLayout(null);
			pnSolicitudDatosCentro.add(getPnDatosPersonales());
			pnSolicitudDatosCentro.add(getPnDatosAcademicos());						
			pnSolicitudDatosCentro.add(getPanelBanco());
		}
		return pnSolicitudDatosCentro;
	}
	private JLabel getLbRellenarDatos() {
		if (lbRellenarDatos == null) {
			lbRellenarDatos = new JLabel("Por favor, rellene los siguientes datos para formalizar su solicitud:");
		}
		return lbRellenarDatos;
	}
	private JPanel getPanelDatosSur_1() {
		if (panelDatosSur == null) {
			panelDatosSur = new JPanel();
			panelDatosSur.setBounds(0, 325, 691, 41);
			panelDatosSur.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panelDatosSur.add(getBtnLimpiar());
			panelDatosSur.add(getBtnFinalizar());
		}
		return panelDatosSur;
	}
	private JButton getBtnLimpiar() {
		if (btnLimpiar == null) {
			btnLimpiar = new JButton("Limpiar");
			btnLimpiar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (confirmarBorrar()) {
						reiniciarSolicitudRegistro();
					}
				}
			});
			btnLimpiar.setBackground(Color.LIGHT_GRAY);
			btnLimpiar.setToolTipText("Pulse para borrar los datos introducidos en las casillas");
		}
		return btnLimpiar;
	}
	private JButton getBtnFinalizar() {
		if (btnFinalizar == null) {
			btnFinalizar = new JButton("Finalizar");
			btnFinalizar.setBackground(Color.GREEN);
			btnFinalizar.setToolTipText("Pulse para formalizar su solicitud de ser colegiado");
		}
		return btnFinalizar;
	}
	private JTextField getTextFieldNombre() {
		if (textFieldNombre == null) {
			textFieldNombre = new JTextField();
			textFieldNombre.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldNombre.setToolTipText("Inserte el nombre");
			textFieldNombre.setBounds(220, 36, 127, 20);
			textFieldNombre.setColumns(10);
			textFieldNombre.grabFocus();
		}
		return textFieldNombre;
	}
	private JTextField getTextFieldApellidos() {
		if (textFieldApellidos == null) {
			textFieldApellidos = new JTextField();
			textFieldApellidos.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldApellidos.setToolTipText("Inserte los apellidos");
			textFieldApellidos.setBounds(357, 36, 212, 20);
			textFieldApellidos.setColumns(10);
		}
		return textFieldApellidos;
	}
	private JTextField getTextFieldDni() {
		if (textFieldDni == null) {
			textFieldDni = new JTextField();
			textFieldDni.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldDni.setToolTipText("Inserte el DNI");
			textFieldDni.setBounds(579, 36, 102, 20);
			textFieldDni.setColumns(10);
		}
		return textFieldDni;
	}
	private JLabel getLblNewLabelNAD() {
		if (lblNewLabelNAD == null) {
			lblNewLabelNAD = new JLabel("Registre su nombre / apellidos / DNI:");
			lblNewLabelNAD.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelNAD.setBounds(10, 39, 238, 14);
		}
		return lblNewLabelNAD;
	}
	private JPanel getPanelBanco() {
		if (panelBanco == null) {
			panelBanco = new JPanel();
			panelBanco.setBounds(0, 215, 691, 58);
			panelBanco.setLayout(null);
			panelBanco.add(getLblParaFinalizarDebe());
			panelBanco.add(getTextFieldNTarjeta());
			panelBanco.add(getLblNumeroTarjeta());
		}
		return panelBanco;
	}
	private JLabel getLblNumeroTarjeta() {
		if (lblNewLabelNumeroTarjeta == null) {
			lblNewLabelNumeroTarjeta = new JLabel("Número de tarjeta:");
			lblNewLabelNumeroTarjeta.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelNumeroTarjeta.setBounds(158, 29, 121, 17);
		}
		return lblNewLabelNumeroTarjeta;
	}
	private JLabel getLblParaFinalizarDebe() {
		if (lblParaFinalizarDebe == null) {
			lblParaFinalizarDebe = new JLabel("Para finalizar, debe registrar sus datos bancarios para el pago de las cuotas:");
			lblParaFinalizarDebe.setBounds(164, 2, 446, 14);
		}
		return lblParaFinalizarDebe;
	}
	private JTextField getTextFieldNTarjeta() {
		if (textFieldNTarjeta == null) {
			textFieldNTarjeta = new JTextField();
			textFieldNTarjeta.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldNTarjeta.setToolTipText("Registre su número de tarjeta");
			textFieldNTarjeta.setBounds(282, 27, 299, 20);
			textFieldNTarjeta.setColumns(10);
		}
		return textFieldNTarjeta;
	}
	private boolean confirmarBorrar() {
		boolean confir = false;
		int resp = JOptionPane.showConfirmDialog(this, "¿ Estás seguro de que quieres borrar los campos ?", "Borrar los campos",
				JOptionPane.INFORMATION_MESSAGE);
		if (resp == JOptionPane.YES_OPTION) {
			confir = true;
		}
		return confir;
	}
	private boolean confirmarCancelacion() {
		boolean confir = false;
		int resp = JOptionPane.showConfirmDialog(this, "¿ Estás seguro de que quieres salir de la aplicación ?", "Salir de la aplicación",
				JOptionPane.INFORMATION_MESSAGE);
		if (resp == JOptionPane.YES_OPTION) {
			confir = true;
		}
		return confir;
	}
	private void reiniciarSolicitudRegistro() {
		this.getTxFieldAño().setText(null);
		this.getTextFieldApellidos().setText(null);
		this.getTextFieldDni().setText(null);
		this.getTextFieldNombre().setText(null);
		this.getTextFieldNTarjeta().setText(null);
		this.getTxFieldCentro().setText(null);
		this.getTxFieldPoblacion().setText(null);
		this.getTxFieldTitulo().setText(null);
		this.getTxFieldTelefono().setText(null);
		this.getTextFieldNombre().grabFocus();
	}
}
