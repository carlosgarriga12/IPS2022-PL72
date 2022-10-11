package ui.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import business.BusinessException;
import business.BusinessFactory;
import business.curso.Curso;
import business.inscripcion.InscripcionCursoFormativo;
import business.util.DateUtils;
import persistence.colegiado.ColegiadoDto;
import persistence.curso.CursoDto;
import persistence.jdbc.PersistenceException;
import ui.components.LookAndFeel;
import ui.components.buttons.ButtonColor;
import ui.components.buttons.DefaultButton;
import ui.components.messages.DefaultMessage;
import ui.components.messages.MessageType;
import ui.model.CursoModel;
import ui.util.TimeFormatter;

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
	private JScrollPane spCoursesListCenter;
	private JTable tbCoursesList;

	private JPanel pnCoursesListSouth;
	private JPanel pnCoursesListSouthCourseAbrir;
	private JPanel pnCoursesListSouthButtonsAndMessages;
	private JPanel pnCursoSeleccionadoTitulo;
	private JLabel lbAbrirInscripcionesCurso;
	private JPanel pnCursoSeleccionadoTituloCurso;
	private JPanel pnCursoSeleccionadoFechas;
	private JPanel pnCursoSeleccionadoFechaApertura;
	private JPanel pnCursoSeleccionadoFechaCierre;
	private JPanel pnCursoSeleccionadoPlazas;
	private JLabel lbNumeroPlazasAperturaCurso;
	private JLabel lbFechaAperturaAperturaCurso;
	private JLabel lbFechaCierreAperturaCurso;
	private JFormattedTextField fTxFechaInicioInscripcionesCursoSeleccionado;
	private JLabel lbTituloCursoSeleccionado;
	private JFormattedTextField fTxFechaCierreInscripcionesCursoSeleccionado;
	private JSpinner spNumeroPlazasCursoSeleccionado;
	private JLabel lbTituloCursoSeleccionadoTabla;
	private JPanel pnCoursesListNorth;
	private JLabel lbTituloVentanaAperturaInscripcionCurso;
	private JPanel pnCoursesListSouthButtons;
	private DefaultButton btCancelarAperturaCurso;
	private DefaultButton btAbrirCurso;

	private JPanel pnListCoursesSouthMessages;
	private JPanel pnCoursesListNorthRefreshOpenCoursesList;
	private DefaultButton btRefreshOpenCoursesList;

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
	private JTextField textFieldAno;
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
	private JLabel lblNewLabelDatAno;
	private JLabel lblNewLabelDatCentro;
	private JLabel lblNewLabelA;
	private JLabel lblNewLabelD;

	public MainWindow() {
		setTitle("COIIPA : Gestión de servicios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 915, 477);
		mainPanel = new JPanel();
		mainPanel.setName("");
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		mainPanel.setLayout(new CardLayout(0, 0));

		// TODO: Cambiar orden para ver al arrancar el programa
		mainPanel.add(getPnCoursesList(), "coursesTablePanel");
		mainPanel.add(getPnSolicitudColegiado(), "solicitudColegiadoPanel");
		textFieldNombre.grabFocus();
		mainPanel.add(getPnHome(), "homePanel");
		mainPanel.add(getPnLogin(), "loginPanel");
		mainPanel.add(getPnProgramAccess(), "programAccessPanel");

		// Centrar la ventana
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		inicializarCampos();
	}

	private void inicializarCampos() {
		textFieldNombre.grabFocus();
		textFieldApellidos.setText("Ej: Gonzalez Navarro");
		textFieldDni.setText("Ej: 71778880C [9 caracteres]");
		textFieldPoblacion.setText("Ej: Moreda");
		textFieldTelefono.setText("Ej: 681676654 [9 números]");
		textFieldTitulo.setText("Ej: 0 (sin titulación), 1 (ingenieria) o 2 (otros)");
		textFieldCentro.setText("Ej: Escuela de Ingeniería");
		textFieldAno.setText("Ej : 2022 [4 números]");
		textFieldNTarjeta.setText("Ej: 76567 [5 números]");
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
			pnCoursesList.setBorder(null);
			pnCoursesList.setLayout(new BorderLayout(0, 20));
			pnCoursesList.add(getSpCoursesListCenter(), BorderLayout.CENTER);
			pnCoursesList.add(getPnCoursesListSouth(), BorderLayout.SOUTH);
			pnCoursesList.add(getPnCoursesListNorth(), BorderLayout.NORTH);
		}
		return pnCoursesList;
	}

	private JScrollPane getSpCoursesListCenter() {
		if (spCoursesListCenter == null) {
			spCoursesListCenter = new JScrollPane(getTbCoursesList());
			spCoursesListCenter.setOpaque(false);
			spCoursesListCenter.setBorder(null);
		}
		return spCoursesListCenter;
	}

	private JTable getTbCoursesList() {
		if (tbCoursesList == null) {
			tbCoursesList = new JTable();
			tbCoursesList.setIntercellSpacing(new Dimension(0, 0));
			tbCoursesList.setShowGrid(false);
			tbCoursesList.setRowMargin(0);
			tbCoursesList.setRequestFocusEnabled(false);
			tbCoursesList.setFocusable(false);
			tbCoursesList.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbCoursesList.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbCoursesList.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbCoursesList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbCoursesList.setShowVerticalLines(false);
			tbCoursesList.setOpaque(false);

			tbCoursesList.setRowHeight(LookAndFeel.ROW_HEIGHT);
			tbCoursesList.setGridColor(new Color(255, 255, 255));

			tbCoursesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			TableModel tableModel;
			try {
				tableModel = CursoModel.getCursoModel();
				tbCoursesList.setModel(tableModel);
			} catch (BusinessException e) {
				showMessage(e, MessageType.ERROR);
				e.printStackTrace();
			}

			// Evento de selección de curso para abrir inscripciones
			tbCoursesList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {

					if (event.getValueIsAdjusting())
						return;

					CursoDto cursoSeleccionado = new CursoDto();

					try {

						int selectedRow = tbCoursesList.getSelectedRow();

						if (selectedRow == -1) {
							selectedRow = 0;
						}

						cursoSeleccionado.codigoCurso = Integer
								.parseInt(tbCoursesList.getValueAt(selectedRow, 0).toString());

						cursoSeleccionado.titulo = tbCoursesList.getValueAt(selectedRow, 1).toString();

						cursoSeleccionado.fechaInicio = LocalDate
								.parse(tbCoursesList.getValueAt(selectedRow, 2).toString());

						cursoSeleccionado.plazasDisponibles = Integer
								.parseInt(tbCoursesList.getValueAt(selectedRow, 3).toString());

						cursoSeleccionado.precio = Double
								.parseDouble(tbCoursesList.getValueAt(selectedRow, 4).toString());

						cursoSeleccionado.estado = tbCoursesList.getValueAt(selectedRow, 5).toString();

					} catch (NumberFormatException | ArrayIndexOutOfBoundsException nfe) {
					}

					Curso.setSelectedCourse(cursoSeleccionado);

					lbTituloCursoSeleccionadoTabla.setText(cursoSeleccionado.titulo);
					spNumeroPlazasCursoSeleccionado.setValue(cursoSeleccionado.plazasDisponibles);

					fTxFechaCierreInscripcionesCursoSeleccionado.setText(String.valueOf(cursoSeleccionado.fechaInicio));
				}

			});
		}
		return tbCoursesList;
	}

	private JPanel getPnCoursesListSouth() {
		if (pnCoursesListSouth == null) {
			pnCoursesListSouth = new JPanel();
			pnCoursesListSouth.setLayout(new BorderLayout(0, 10));
			pnCoursesListSouth.add(getPnCoursesListSouthCourseAbrir(), BorderLayout.CENTER);
			pnCoursesListSouth.add(getPnCoursesListSouthButtonsAndMessages(), BorderLayout.SOUTH);
			pnCoursesListSouth.add(getPnCoursesListNorthRefreshOpenCoursesList_1(), BorderLayout.NORTH);
		}
		return pnCoursesListSouth;
	}

	private JPanel getPnCoursesListSouthCourseAbrir() {
		if (pnCoursesListSouthCourseAbrir == null) {
			pnCoursesListSouthCourseAbrir = new JPanel();
			pnCoursesListSouthCourseAbrir.setLayout(new GridLayout(0, 1, 10, 10));
			pnCoursesListSouthCourseAbrir.add(getPnCursoSeleccionadoTitulo());
			pnCoursesListSouthCourseAbrir.add(getPnCursoSeleccionadoTituloCurso());
			pnCoursesListSouthCourseAbrir.add(getPnCursoSeleccionadoFechas());
			pnCoursesListSouthCourseAbrir.add(getPnCursoSeleccionadoPlazas());
		}
		return pnCoursesListSouthCourseAbrir;
	}

	private JPanel getPnCoursesListSouthButtonsAndMessages() {
		if (pnCoursesListSouthButtonsAndMessages == null) {
			pnCoursesListSouthButtonsAndMessages = new JPanel();
			pnCoursesListSouthButtonsAndMessages.setOpaque(false);
			pnCoursesListSouthButtonsAndMessages.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			pnCoursesListSouthButtonsAndMessages.setBackground(Color.WHITE);
			pnCoursesListSouthButtonsAndMessages.setLayout(new GridLayout(0, 2, 10, 0));
			pnCoursesListSouthButtonsAndMessages.add(getPnCoursesListSouthMessages());
			pnCoursesListSouthButtonsAndMessages.add(getPnCoursesListSouthButtons());
		}
		return pnCoursesListSouthButtonsAndMessages;
	}

	/**
	 * Panel que muestra los mensajes de la ventana de abrir una inscripción.
	 * 
	 * @return
	 */
	private JPanel getPnCoursesListSouthMessages() {
		if (pnListCoursesSouthMessages == null) {
			pnListCoursesSouthMessages = new DefaultMessage("");
		}

		return pnListCoursesSouthMessages;
	}

	private JPanel getPnCursoSeleccionadoTitulo() {
		if (pnCursoSeleccionadoTitulo == null) {
			pnCursoSeleccionadoTitulo = new JPanel();
			pnCursoSeleccionadoTitulo.setOpaque(false);
			pnCursoSeleccionadoTitulo.setLayout(new BorderLayout(0, 0));
			pnCursoSeleccionadoTitulo.add(getLbAbrirInscripcionesCurso());
		}
		return pnCursoSeleccionadoTitulo;
	}

	private JLabel getLbAbrirInscripcionesCurso() {
		if (lbAbrirInscripcionesCurso == null) {
			lbAbrirInscripcionesCurso = new JLabel("APERTURA INSCRIPCIÓN CURSO SELECCIONADO");
			lbAbrirInscripcionesCurso.setHorizontalAlignment(SwingConstants.CENTER);
			lbAbrirInscripcionesCurso.setFont(LookAndFeel.HEADING_2_FONT);
			lbAbrirInscripcionesCurso.setToolTipText(
					"Se establecerán los plazos de inicio y cierre de inscripciones, así como el número de plazas del curso seleccionado en la tabla.");
		}
		return lbAbrirInscripcionesCurso;
	}

	private JPanel getPnCursoSeleccionadoTituloCurso() {
		if (pnCursoSeleccionadoTituloCurso == null) {
			pnCursoSeleccionadoTituloCurso = new JPanel();
			pnCursoSeleccionadoTituloCurso.setLayout(new GridLayout(0, 2, 10, 0));
			pnCursoSeleccionadoTituloCurso.add(getLbTituloCursoSeleccionado());
			pnCursoSeleccionadoTituloCurso.add(getLbTituloCursoSeleccionadoTabla());
		}
		return pnCursoSeleccionadoTituloCurso;
	}

	private JPanel getPnCursoSeleccionadoFechas() {
		if (pnCursoSeleccionadoFechas == null) {
			pnCursoSeleccionadoFechas = new JPanel();
			pnCursoSeleccionadoFechas.setBorder(new TitledBorder(null, "Periodo inscripci\u00F3n", TitledBorder.LEADING,
					TitledBorder.TOP, null, null));
			pnCursoSeleccionadoFechas.setLayout(new GridLayout(1, 2, 10, 0));
			pnCursoSeleccionadoFechas.add(getPnCursoSeleccionadoFechaApertura());
			pnCursoSeleccionadoFechas.add(getPnCursoSeleccionadoFechaCierre());
		}
		return pnCursoSeleccionadoFechas;
	}

	private JPanel getPnCursoSeleccionadoFechaApertura() {
		if (pnCursoSeleccionadoFechaApertura == null) {
			pnCursoSeleccionadoFechaApertura = new JPanel();
			pnCursoSeleccionadoFechaApertura.setOpaque(false);
			pnCursoSeleccionadoFechaApertura.setLayout(new GridLayout(0, 2, 0, 0));
			pnCursoSeleccionadoFechaApertura.add(getLbFechaAperturaAperturaCurso());
			pnCursoSeleccionadoFechaApertura.add(getFTxFechaInicioInscripcionesCursoSeleccionado());
		}
		return pnCursoSeleccionadoFechaApertura;
	}

	private JPanel getPnCursoSeleccionadoFechaCierre() {
		if (pnCursoSeleccionadoFechaCierre == null) {
			pnCursoSeleccionadoFechaCierre = new JPanel();
			pnCursoSeleccionadoFechaCierre.setOpaque(false);
			pnCursoSeleccionadoFechaCierre.setLayout(new GridLayout(0, 2, 0, 0));
			pnCursoSeleccionadoFechaCierre.add(getLbFechaCierreAperturaCurso());
			pnCursoSeleccionadoFechaCierre.add(getFTxFechaCierreInscripcionesCursoSeleccionado());
		}
		return pnCursoSeleccionadoFechaCierre;
	}

	private JPanel getPnCursoSeleccionadoPlazas() {
		if (pnCursoSeleccionadoPlazas == null) {
			pnCursoSeleccionadoPlazas = new JPanel();
			pnCursoSeleccionadoPlazas.setMinimumSize(new Dimension(10, 30));
			pnCursoSeleccionadoPlazas.setBounds(new Rectangle(0, 0, 0, 33));
			pnCursoSeleccionadoPlazas.setOpaque(false);
			pnCursoSeleccionadoPlazas.setLayout(new GridLayout(0, 2, 10, 0));
			pnCursoSeleccionadoPlazas.add(getLbNumeroPlazasAperturaCurso());
			pnCursoSeleccionadoPlazas.add(getSpNumeroPlazasCursoSeleccionado());
		}
		return pnCursoSeleccionadoPlazas;
	}

	private JLabel getLbNumeroPlazasAperturaCurso() {
		if (lbNumeroPlazasAperturaCurso == null) {
			lbNumeroPlazasAperturaCurso = new JLabel("Número de plazas:");
			lbNumeroPlazasAperturaCurso.setDisplayedMnemonic('p');
			lbNumeroPlazasAperturaCurso.setHorizontalAlignment(SwingConstants.CENTER);
			lbNumeroPlazasAperturaCurso.setLabelFor(getSpNumeroPlazasCursoSeleccionado());
		}
		return lbNumeroPlazasAperturaCurso;
	}

	private JLabel getLbFechaAperturaAperturaCurso() {
		if (lbFechaAperturaAperturaCurso == null) {
			lbFechaAperturaAperturaCurso = new JLabel("Fecha de inicio:");
			lbFechaAperturaAperturaCurso.setDisplayedMnemonic('n');
			lbFechaAperturaAperturaCurso.setHorizontalAlignment(SwingConstants.CENTER);
			lbFechaAperturaAperturaCurso.setLabelFor(getFTxFechaInicioInscripcionesCursoSeleccionado());
		}
		return lbFechaAperturaAperturaCurso;
	}

	private JLabel getLbFechaCierreAperturaCurso() {
		if (lbFechaCierreAperturaCurso == null) {
			lbFechaCierreAperturaCurso = new JLabel("Fecha finalización");
			lbFechaCierreAperturaCurso.setDisplayedMnemonic('z');
			lbFechaCierreAperturaCurso.setHorizontalAlignment(SwingConstants.CENTER);
			lbFechaCierreAperturaCurso.setLabelFor(getFTxFechaCierreInscripcionesCursoSeleccionado());
		}
		return lbFechaCierreAperturaCurso;
	}

	private JFormattedTextField getFTxFechaInicioInscripcionesCursoSeleccionado() {
		if (fTxFechaInicioInscripcionesCursoSeleccionado == null) {
			fTxFechaInicioInscripcionesCursoSeleccionado = new JFormattedTextField(new TimeFormatter());
			fTxFechaInicioInscripcionesCursoSeleccionado
					.setToolTipText("Seleccione la fecha de apertura de las inscripciones al curso seleccioando");
			fTxFechaInicioInscripcionesCursoSeleccionado.setHorizontalAlignment(SwingConstants.CENTER);
			fTxFechaInicioInscripcionesCursoSeleccionado.setOpaque(false);
		}
		return fTxFechaInicioInscripcionesCursoSeleccionado;
	}

	private JLabel getLbTituloCursoSeleccionado() {
		if (lbTituloCursoSeleccionado == null) {
			lbTituloCursoSeleccionado = new JLabel("Título:");
			lbTituloCursoSeleccionado.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbTituloCursoSeleccionado;
	}

	private JFormattedTextField getFTxFechaCierreInscripcionesCursoSeleccionado() {
		if (fTxFechaCierreInscripcionesCursoSeleccionado == null) {
			fTxFechaCierreInscripcionesCursoSeleccionado = new JFormattedTextField(new TimeFormatter());
			fTxFechaCierreInscripcionesCursoSeleccionado
					.setToolTipText("Seleccione la fecha de cierre de las inscripciones al curso seleccioando");
			fTxFechaCierreInscripcionesCursoSeleccionado.setHorizontalAlignment(SwingConstants.CENTER);
			fTxFechaCierreInscripcionesCursoSeleccionado.setOpaque(false);
		}
		return fTxFechaCierreInscripcionesCursoSeleccionado;
	}

	private JSpinner getSpNumeroPlazasCursoSeleccionado() {
		if (spNumeroPlazasCursoSeleccionado == null) {
			spNumeroPlazasCursoSeleccionado = new JSpinner();
			spNumeroPlazasCursoSeleccionado.setFocusable(false);
			spNumeroPlazasCursoSeleccionado.setOpaque(false);
			spNumeroPlazasCursoSeleccionado.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					enableOpenCourseButton();
				}
			});
			spNumeroPlazasCursoSeleccionado.setToolTipText("Introduzca el número de plazas del curso seleccionado");
			spNumeroPlazasCursoSeleccionado.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
		}
		return spNumeroPlazasCursoSeleccionado;
	}

	private JLabel getLbTituloCursoSeleccionadoTabla() {
		if (lbTituloCursoSeleccionadoTabla == null) {
			lbTituloCursoSeleccionadoTabla = new JLabel("");
			lbTituloCursoSeleccionadoTabla.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return lbTituloCursoSeleccionadoTabla;
	}

	private JPanel getPnCoursesListNorth() {
		if (pnCoursesListNorth == null) {
			pnCoursesListNorth = new JPanel();
			pnCoursesListNorth.setLayout(new BorderLayout(0, 0));
			pnCoursesListNorth.add(getLbTituloVentanaAperturaInscripcionCurso(), BorderLayout.CENTER);
		}
		return pnCoursesListNorth;
	}

	private JLabel getLbTituloVentanaAperturaInscripcionCurso() {
		if (lbTituloVentanaAperturaInscripcionCurso == null) {
			lbTituloVentanaAperturaInscripcionCurso = new JLabel("CURSOS ACTUALMENTE PLANIFICADOS");
			lbTituloVentanaAperturaInscripcionCurso.setFont(LookAndFeel.HEADING_1_FONT);
			lbTituloVentanaAperturaInscripcionCurso.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbTituloVentanaAperturaInscripcionCurso;
	}

	private void enableOpenCourseButton() {
		btAbrirCurso.setEnabled(true);
		// Comprobar fechas
//		if (Integer.parseInt(spNumeroPlazasCursoSeleccionado.getValue().toString()) > 0
//				&& spNumeroPlazasCursoSeleccionado.getValue() != "") {
//			
//		} else {
//			btAbrirCurso.setEnabled(false);
//		}
	}

	private JPanel getPnCoursesListSouthButtons() {
		if (pnCoursesListSouthButtons == null) {
			pnCoursesListSouthButtons = new JPanel();
			pnCoursesListSouthButtons.setLayout(new GridLayout(0, 2, 10, 0));
			pnCoursesListSouthButtons.add(getBtCancelarAperturaCurso());
			pnCoursesListSouthButtons.add(getBtAbrirCurso());
		}
		return pnCoursesListSouthButtons;
	}

	private DefaultButton getBtCancelarAperturaCurso() {
		if (btCancelarAperturaCurso == null) {
			btCancelarAperturaCurso = new DefaultButton("Cancelar", "ventana", "Cancelar", 'c', ButtonColor.CANCEL);
			btCancelarAperturaCurso.setToolTipText("Cancelar apertura de curso");
			btCancelarAperturaCurso.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btCancelarAperturaCurso.setFocusPainted(false);
		}
		return btCancelarAperturaCurso;
	}

	private DefaultButton getBtAbrirCurso() {
		if (btAbrirCurso == null) {
			btAbrirCurso = new DefaultButton("Abrir curso", "ventana", "Abrir curso", 'a', ButtonColor.NORMAL);
			btAbrirCurso.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btAbrirCurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					String plazas = spNumeroPlazasCursoSeleccionado.getValue().toString();
					LocalDate fechaApertura = DateUtils
							.convertStringIntoLocalDate(fTxFechaInicioInscripcionesCursoSeleccionado.getText());
					LocalDate fechaCierre = DateUtils
							.convertStringIntoLocalDate(fTxFechaCierreInscripcionesCursoSeleccionado.getText());

					try {

						CursoDto selectedCourse = Curso.getSelectedCourse();

						int input = JOptionPane.showConfirmDialog(null,
								"<html><p>¿Confirma que desea abrir las inscripciones para el curso <b>"
										+ selectedCourse.titulo + "</b> ?</p></html>");

						if (input == JOptionPane.OK_OPTION) {
							// Si el curso está abierto, mostrar mensaje de error
							InscripcionCursoFormativo.abrirCursoFormacion(selectedCourse, fechaApertura, fechaCierre,
									plazas);

							((DefaultMessage) pnListCoursesSouthMessages).setMessageColor(MessageType.SUCCESS);
							((DefaultMessage) pnListCoursesSouthMessages)
									.setMessage("Se ha abierto la inscripción para el curso seleccionado");

							refreshScheduledCoursesList();

							btAbrirCurso.setEnabled(false);
						}

					} catch (BusinessException | PersistenceException | SQLException e1) {
						((DefaultMessage) pnListCoursesSouthMessages).setMessageColor(MessageType.ERROR);
						((DefaultMessage) pnListCoursesSouthMessages)
								.setMessage("<html>" + e1.getMessage() + "</html>");
						btAbrirCurso.setEnabled(false);

					}
				}
			});
			btAbrirCurso.setToolTipText("Confirmar cambios y abrir inscripciones al curso seleccionado en la tabla");
			btAbrirCurso.setEnabled(false);
		}
		return btAbrirCurso;
	}

	private JPanel getPnCoursesListNorthRefreshOpenCoursesList_1() {
		if (pnCoursesListNorthRefreshOpenCoursesList == null) {
			pnCoursesListNorthRefreshOpenCoursesList = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnCoursesListNorthRefreshOpenCoursesList.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnCoursesListNorthRefreshOpenCoursesList.setOpaque(false);
			pnCoursesListNorthRefreshOpenCoursesList.add(getBtRefreshOpenCoursesList());
		}
		return pnCoursesListNorthRefreshOpenCoursesList;
	}

	private DefaultButton getBtRefreshOpenCoursesList() {
		if (btRefreshOpenCoursesList == null) {
			btRefreshOpenCoursesList = new DefaultButton("Actualizar lista", "small", "Actualizar lista", 'r',
					ButtonColor.NORMAL);
			btRefreshOpenCoursesList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						refreshScheduledCoursesList();
					} catch (BusinessException e1) {
						showMessage(e1, MessageType.ERROR);
						e1.printStackTrace();
					}
				}
			});
		}
		return btRefreshOpenCoursesList;
	}

	private void refreshScheduledCoursesList() throws BusinessException {
		TableModel tableModel = CursoModel.getCursoModel();
		tbCoursesList.setModel(tableModel);
		tbCoursesList.repaint();
	}

	/**
	 * 
	 * @param e1
	 * @param type
	 */
	private void showMessage(BusinessException e1, MessageType type) {
		if(pnListCoursesSouthMessages != null) {
			((DefaultMessage) pnListCoursesSouthMessages).setMessageColor(type);
			((DefaultMessage) pnListCoursesSouthMessages).setMessage("<html>" + e1.getMessage() + "</html>");			
		}
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
			panelDatosPersonales.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
					"Datos personales", TitledBorder.CENTER, TitledBorder.TOP, null, Color.GRAY));
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
			panelDatosAcademicos.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
					"Datos acad\u00E9micos", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(128, 128, 128)));
			panelDatosAcademicos.setLayout(null);
			panelDatosAcademicos.setBounds(0, 116, 691, 99);
			panelDatosAcademicos.add(getLbDatAcademicos());
			panelDatosAcademicos.add(getTxFieldTitulo());
			panelDatosAcademicos.add(getTxFieldCentro());
			panelDatosAcademicos.add(getTxFieldAno());
			panelDatosAcademicos.add(getLblNewLabelDatAno());
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

	private JTextField getTxFieldAno() {
		if (textFieldAno == null) {
			textFieldAno = new JTextField();
			textFieldAno.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldAno.setText(null);
				}
			});
			textFieldAno.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldAno.setToolTipText("Escriba el ano");
			textFieldAno.setColumns(10);
			textFieldAno.setBounds(550, 52, 131, 20);
		}
		return textFieldAno;
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
					if (anadirColegiado()) {
						mostrarEstadoPendiente();
						getPnSolicitudColegiado().setVisible(false);
						getPnHome().setVisible(true);
						reiniciarSolicitudRegistro();
					}
				}
			});
			btnFinalizar.setBackground(Color.GREEN);
			btnFinalizar.setToolTipText("Pulse para formalizar su solicitud de ser colegiado");
		}
		return btnFinalizar;
	}

	private boolean anadirColegiado() {
		// coger datos
		ColegiadoDto dto = recogerDatosCampos();
		if (dto == null) {
			return false;
		}
		return anadirColegiadoBaseDatos(dto);
	}

	private boolean anadirColegiadoBaseDatos(ColegiadoDto dto) {
		try {
			BusinessFactory.forColegiadoService().addColegiado(dto);
			return true;
		} catch (BusinessException e) {
			JOptionPane.showMessageDialog(this,
					"Por favor, revise que no haya introducido un DNI que no es suyo, este DNI ya ha sido registrado",
					"DNI inválido", JOptionPane.INFORMATION_MESSAGE);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this,
					"Por favor, revise que no deje ningún campo vacío y se ha introducido correctamente cada dato (longitudes correspondientes, formato de datos, ...)\n"
							+ "Sigue los ejemplos que aparecen en los campos correspondientes",
					"Datos incorrectos", JOptionPane.INFORMATION_MESSAGE);
		}
		return false;
	}

	private ColegiadoDto recogerDatosCampos() {
		ColegiadoDto dto = new ColegiadoDto();
		dto.DNI = getTextFieldDni().getText();
		dto.nombre = getTextFieldNombre().getText();
		dto.apellidos = getTextFieldApellidos().getText();
		dto.poblacion = getTxFieldPoblacion().getText();
		dto.centro = getTxFieldCentro().getText();
		try {
			dto.telefono = Integer.parseInt(getTxFieldTelefono().getText());
			dto.titulacion = Integer.parseInt(getTxFieldTitulo().getText());
			dto.annio = Integer.parseInt(getTxFieldAno().getText());
			dto.numeroTarjeta = Integer.parseInt(getTextFieldNTarjeta().getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,
					"Por favor, revise que no se haya introducido ninguna cadena en alguno de los campos numéricos",
					"Formato numérico incorrecto", JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		return dto;
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
			lblParaFinalizarDebe = new JLabel(
					"Para finalizar, debe registrar sus datos bancarios para el pago de las cuotas:");
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
		int resp = JOptionPane.showConfirmDialog(this, "¿ Estás seguro de que quieres borrar los campos ?",
				"Borrar los campos", JOptionPane.INFORMATION_MESSAGE);
		if (resp == JOptionPane.YES_OPTION) {
			confir = true;
		}
		return confir;
	}

	private boolean confirmarVolverPrinicipio() {
		boolean confir = false;
		int resp = JOptionPane.showConfirmDialog(this, "¿ Estás seguro de que quieres volver a la pantalla de inicio ?",
				"Volver al inicio", JOptionPane.INFORMATION_MESSAGE);
		if (resp == JOptionPane.YES_OPTION) {
			confir = true;
		}
		return confir;
	}

	private void mostrarEstadoPendiente() {
		JOptionPane.showMessageDialog(this,
				"Se acaba de tramitar su solicitud, quedará en estado 'PENDIENTE' hasta nuevo aviso",
				"Solicitud de colegiado enviada", JOptionPane.INFORMATION_MESSAGE);
	}

	private void reiniciarSolicitudRegistro() {
		this.getTxFieldAno().setText(null);
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

	private JLabel getLblNewLabelDatAno() {
		if (lblNewLabelDatAno == null) {
			lblNewLabelDatAno = new JLabel("Ano:");
			lblNewLabelDatAno.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelDatAno.setBounds(612, 27, 41, 14);
			lblNewLabelDatAno.setLabelFor(this.getTxFieldAno());
			lblNewLabelDatAno.setDisplayedMnemonic('O');
		}
		return lblNewLabelDatAno;
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
