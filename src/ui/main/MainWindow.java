package ui.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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

import business.BusinessException;
import business.BusinessFactory;
import persistence.colegiado.ColegiadoDto;

import javax.swing.border.EtchedBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -8203812316779660921L;
	
	private JPanel mainPanel;
	private JLabel lblTitle;
	private JButton btnSecretaria;
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
	private JLabel lblNewLabelN;
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

	private JLabel lblNewLabelDatTitulacion;
	private JButton btnSolicitud;
	private JButton btnAtras;
	private JLabel lblNewLabelDatAño;
	private JLabel lblNewLabelDatCentro;
	private JLabel lblNewLabelA;
	private JLabel lblNewLabelD;

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("COIIPA : Gestión de servicios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 717, 415);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		mainPanel.setLayout(new CardLayout(0, 0));

		// TODO: Cambiar orden para ver al arrancar el programa
		mainPanel.add(getPnSolicitudColegiado(), "solicitudColegiadoPanel");
		textFieldNombre.grabFocus();
		mainPanel.add(getPnHome(), "homePanel");
		mainPanel.add(getPnCoursesList(), "coursesTablePanel");
		mainPanel.add(getPnLogin(), "loginPanel");
		mainPanel.add(getPnProgramAccess(), "programAccessPanel");
		inicializarCampos();
	}

	private void inicializarCampos() {
		textFieldNombre.grabFocus();
		textFieldApellidos.setText("Ej: Gonzalez Navarro");
		textFieldDni.setText("Ej: 71778880C [9 caracteres]");
		textFieldPoblacion.setText("Ej: Moreda");
		textFieldTelefono.setText("Ej: 681676654 [9 números]");
		textFieldTitulo.setText("Ej: 0 (sin titulación) [solo vale 0, 1(ingenieria) o 2(otros)]");
		textFieldCentro.setText("Ej: Escuela de Ingeniería");
		textFieldAño.setText("Ej : 2022 [4 números]");
		textFieldNTarjeta.setText("Ej: 7656766667776667 [16 números]");
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
			pnHome.add(getTxtDNI());
			pnHome.add(getLblDNI());
			pnHome.add(getBtnSolicitud(), BorderLayout.EAST);
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
			panelDatosPersonales.add(getLblNewLabelN());
			panelDatosPersonales.add(getTextFieldNombre());
			panelDatosPersonales.add(getTextFieldApellidos());
			panelDatosPersonales.add(getTextFieldDni());
			panelDatosPersonales.add(getLbPoblacion());
			panelDatosPersonales.add(getTxFieldPoblacion());
			panelDatosPersonales.add(getTxFieldTelefono());
			panelDatosPersonales.add(getLbTelefono());
			panelDatosPersonales.add(getLblNewLabelA());
			panelDatosPersonales.add(getLblNewLabelD());
		}
		return panelDatosPersonales;
	}
	private JLabel getLbPoblacion() {
		if (lblNewLabelPob == null) {
			lblNewLabelPob = new JLabel("Población:");
			lblNewLabelPob.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelPob.setToolTipText("Introduzca su población");
			lblNewLabelPob.setBounds(23, 65, 59, 14);
			lblNewLabelPob.setLabelFor(getTxFieldPoblacion());
			lblNewLabelPob.setDisplayedMnemonic('P');
		}
		return lblNewLabelPob;
	}
	private JTextField getTxFieldPoblacion() {
		if (textFieldPoblacion == null) {
			textFieldPoblacion = new JTextField();
			textFieldPoblacion.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldPoblacion.setText(null);
				}
			});
			textFieldPoblacion.setToolTipText("Introduzca su población");
			textFieldPoblacion.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldPoblacion.setColumns(10);
			textFieldPoblacion.setBounds(100, 62, 148, 20);
		}
		return textFieldPoblacion;
	}
	private JTextField getTxFieldTelefono() {
		if (textFieldTelefono == null) {
			textFieldTelefono = new JTextField();
			textFieldTelefono.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldTelefono.setText(null);
				}
			});
			textFieldTelefono.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTelefono.setToolTipText("Introduzca su teléfono");
			textFieldTelefono.setColumns(10);
			textFieldTelefono.setBounds(428, 62, 190, 20);
		}
		return textFieldTelefono;
	}
	private JLabel getLbTelefono() {
		if (lblNewLabelTelefono == null) {
			lblNewLabelTelefono = new JLabel("Teléfono de contacto:");
			lblNewLabelTelefono.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelTelefono.setBounds(279, 65, 139, 14);
			lblNewLabelTelefono.setLabelFor(getTxFieldTelefono());
			lblNewLabelTelefono.setDisplayedMnemonic('T');
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
			panelDatosAcademicos.add(getLblNewLabelDatAño());
			panelDatosAcademicos.add(getLblNewLabelDatCentro());
		}
		return panelDatosAcademicos;
	}
	private JLabel getLbDatAcademicos() {
		if (lblNewLabelDatTitulacion == null) {
			lblNewLabelDatTitulacion = new JLabel("Titulación [0, 1 o 2] segun sus estudios:");
			lblNewLabelDatTitulacion.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelDatTitulacion.setBounds(54, 27, 238, 14);
			lblNewLabelDatTitulacion.setLabelFor(this.getTxFieldTitulo());
			lblNewLabelDatTitulacion.setDisplayedMnemonic('I');
		}
		return lblNewLabelDatTitulacion;
	}
	private JTextField getTxFieldTitulo() {
		if (textFieldTitulo == null) {
			textFieldTitulo = new JTextField();
			textFieldTitulo.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldTitulo.setText(null);
				}
			});
			textFieldTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTitulo.setToolTipText("Teclee la titulación");
			textFieldTitulo.setColumns(10);
			textFieldTitulo.setBounds(10, 52, 309, 20);
		}
		return textFieldTitulo;
	}
	private JTextField getTxFieldCentro() {
		if (textFieldCentro == null) {
			textFieldCentro = new JTextField();
			textFieldCentro.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldCentro.setText(null);
				}
			});
			textFieldCentro.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldCentro.setToolTipText("Escriba su centro educativo");
			textFieldCentro.setColumns(10);
			textFieldCentro.setBounds(329, 52, 196, 20);
		}
		return textFieldCentro;
	}
	private JTextField getTxFieldAño() {
		if (textFieldAño == null) {
			textFieldAño = new JTextField();
			textFieldAño.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldAño.setText(null);
				}
			});
			textFieldAño.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldAño.setToolTipText("Escriba el año");
			textFieldAño.setColumns(10);
			textFieldAño.setBounds(550, 52, 131, 20);
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
			panelDatosSur.add(getBtnAtras());
			panelDatosSur.add(getBtnLimpiar());
			panelDatosSur.add(getBtnFinalizar());
		}
		return panelDatosSur;
	}
	private JButton getBtnLimpiar() {
		if (btnLimpiar == null) {
			btnLimpiar = new JButton("Limpiar");
			btnLimpiar.setMnemonic('L');
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
			btnFinalizar.setMnemonic('F');
			btnFinalizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					anadirColegiado();
					mostrarEstadoPendiente();
					getPnSolicitudColegiado().setVisible(false);
					getPnHome().setVisible(true);
					reiniciarSolicitudRegistro();
				}
			});
			btnFinalizar.setBackground(Color.GREEN);
			btnFinalizar.setToolTipText("Pulse para formalizar su solicitud de ser colegiado");
		}
		return btnFinalizar;
	}

	private void anadirColegiado() {
		// coger datos
		ColegiadoDto dto = new ColegiadoDto();
		dto.DNI=getTextFieldDni().getText();
		dto.nombre=getTextFieldNombre().getText();
		dto.apellidos=getTextFieldApellidos().getText();
		dto.poblacion=getTxFieldPoblacion().getText();
		dto.telefono=Integer.parseInt(getTxFieldTelefono().getText());
		dto.titulacion=Integer.parseInt(getTxFieldTitulo().getText());
		dto.centro=getTxFieldCentro().getText();
		dto.annio=Integer.parseInt(getTxFieldAño().getText());
		dto.numeroTarjeta=Integer.parseInt(getTextFieldNTarjeta().getText());
		try {
			if (BusinessFactory.forColegiadoService().findColegiadoPorDni(dto.DNI)!=null) {
				JOptionPane.showMessageDialog(this, "Se acaba de registrar con un DNI que ya se encuentra en tramitación. Inténtelo de nuevo con su DNI", "DNI no válido",
						JOptionPane.INFORMATION_MESSAGE);
				this.reiniciarSolicitudRegistro();
			} else {
				try {
					BusinessFactory.forColegiadoService().addColegiado(dto);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private JTextField getTextFieldNombre() {
		if (textFieldNombre == null) {
			textFieldNombre = new JTextField();
			textFieldNombre.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldNombre.setText(null);
				}
			});
			textFieldNombre.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldNombre.setToolTipText("Inserte el nombre");
			textFieldNombre.setBounds(79, 21, 127, 20);
			textFieldNombre.setColumns(10);
			textFieldNombre.grabFocus();
		}
		return textFieldNombre;
	}
	private JTextField getTextFieldApellidos() {
		if (textFieldApellidos == null) {
			textFieldApellidos = new JTextField();
			textFieldApellidos.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldApellidos.setText(null);
				}
			});
			textFieldApellidos.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldApellidos.setToolTipText("Inserte los apellidos");
			textFieldApellidos.setBounds(287, 21, 181, 20);
			textFieldApellidos.setColumns(10);
		}
		return textFieldApellidos;
	}
	private JTextField getTextFieldDni() {
		if (textFieldDni == null) {
			textFieldDni = new JTextField();
			textFieldDni.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldDni.setText(null);
				}
			});
			textFieldDni.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldDni.setToolTipText("Inserte el DNI");
			textFieldDni.setBounds(516, 21, 165, 20);
			textFieldDni.setColumns(10);
		}
		return textFieldDni;
	}
	private JLabel getLblNewLabelN() {
		if (lblNewLabelN == null) {
			lblNewLabelN = new JLabel("Nombre:");
			lblNewLabelN.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelN.setBounds(10, 24, 59, 14);
			lblNewLabelN.setLabelFor(this.getTextFieldNombre());
			lblNewLabelN.setDisplayedMnemonic('N');
		}
		return lblNewLabelN;
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
			lblNewLabelNumeroTarjeta.setLabelFor(getTextFieldNTarjeta());
			lblNewLabelNumeroTarjeta.setDisplayedMnemonic('N');
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
			textFieldNTarjeta.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldNTarjeta.setText(null);
				}
			});
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
	
	private boolean confirmarVolverPrinicipio() {
		boolean confir = false;
		int resp = JOptionPane.showConfirmDialog(this, "¿ Estás seguro de que quieres volver a la pantalla de inicio ?", "Volver al inicio",
				JOptionPane.INFORMATION_MESSAGE);
		if (resp == JOptionPane.YES_OPTION) {
			confir = true;
		}
		return confir;
	}
	private void mostrarEstadoPendiente() {
		JOptionPane.showMessageDialog(this, "Se acaba de tramitar su solicitud, quedará en estado 'PENDIENTE' hasta nuevo aviso", "Solicitud de colegiado enviada",
				JOptionPane.INFORMATION_MESSAGE);
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
	private JButton getBtnSolicitud() {
		if (btnSolicitud == null) {
			btnSolicitud = new JButton("Solicitud de colegiado");
			btnSolicitud.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getPnHome().setVisible(false);
					getPnSolicitudColegiado().setVisible(true);
					inicializarCampos();
				}
			});
			btnSolicitud.setMnemonic('S');
			btnSolicitud.setToolTipText("Pulsa para formalizar su solicitud");
		}
		return btnSolicitud;
	}
	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atrás");
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (confirmarVolverPrinicipio()) {
						reiniciarSolicitudRegistro();
						getPnSolicitudColegiado().setVisible(false);
						getPnHome().setVisible(true);
					}
				}
			});
			btnAtras.setBackground(Color.RED);
			btnAtras.setMnemonic('R');
			btnAtras.setToolTipText("Pulse para volver a la pantalla principal");
		}
		return btnAtras;
	}
	private JLabel getLblNewLabelDatAño() {
		if (lblNewLabelDatAño == null) {
			lblNewLabelDatAño = new JLabel("Año:");
			lblNewLabelDatAño.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelDatAño.setBounds(612, 27, 41, 14);
			lblNewLabelDatAño.setLabelFor(this.getTxFieldAño());
			lblNewLabelDatAño.setDisplayedMnemonic('O');
		}
		return lblNewLabelDatAño;
	}
	private JLabel getLblNewLabelDatCentro() {
		if (lblNewLabelDatCentro == null) {
			lblNewLabelDatCentro = new JLabel("Centro:");
			lblNewLabelDatCentro.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelDatCentro.setBounds(408, 27, 92, 14);
			lblNewLabelDatCentro.setLabelFor(this.getTxFieldCentro());
			lblNewLabelDatCentro.setDisplayedMnemonic('C');
		}
		return lblNewLabelDatCentro;
	}
	private JLabel getLblNewLabelA() {
		if (lblNewLabelA == null) {
			lblNewLabelA = new JLabel("Apellidos:");
			lblNewLabelA.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelA.setBounds(216, 24, 71, 14);
			lblNewLabelA.setLabelFor(this.getTextFieldApellidos());
			lblNewLabelA.setDisplayedMnemonic('A');
		}
		return lblNewLabelA;
	}
	private JLabel getLblNewLabelD() {
		if (lblNewLabelD == null) {
			lblNewLabelD = new JLabel("DNI:");
			lblNewLabelD.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelD.setBounds(478, 24, 46, 14);
			lblNewLabelD.setLabelFor(this.getTextFieldDni());
			lblNewLabelD.setDisplayedMnemonic('D');
		}
		return lblNewLabelD;
	}
}
