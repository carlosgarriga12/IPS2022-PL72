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
	private JPanel pnSolicitudDatosCentro;
	private JPanel pnSolicitudColegiadoSur;
	private JButton btnLimpiar;
	private JButton btnFinalizar;
	private JButton btnSolicitud;
	private JButton btnAtras;
	private JLabel lbRellenarDatos;
	private JPanel pnSolicitudDatosSur;
	private JPanel pnTarjetaLabel;
	private JPanel pnTarjetaTextField;
	private JLabel lblParaFinalizarDebe;
	private JLabel lblNewLabelNumeroTarjeta;
	private JTextField textFieldTarjeta;
	private JPanel pnDatosPersonales;
	private JPanel pnNombreColegiado;
	private JPanel pnApellidosColegiado;
	private JPanel pnDNIColegiado;
	private JLabel lblNewLabelN;
	private JTextField textFieldNombre;
	private JLabel lblNewLabelA;
	private JTextField textFieldApellidos;
	private JLabel lblNewLabelD;
	private JTextField textFieldDni;
	private JPanel pnDatosGeoAcademicos;
	private JPanel panelDatosGeo;
	private JPanel pnDatosPoblacionColegiado;
	private JLabel lblNewLabelPob;
	private JTextField textFieldPoblacion;
	private JPanel pnDatosTelefonoColegiado;
	private JLabel lblNewLabelTelefono;
	private JTextField textFieldTelefono;
	private JPanel panelDatosAcademicos;
	private JPanel pnDatosTituloColegiado;
	private JPanel pnDatosCentroColegiado;
	private JPanel pnDatosAñoColegiado;
	private JTextField textFieldAño;
	private JLabel lblNewLabelDatAno;
	private JTextField textFieldCentro;
	private JLabel lblNewLabelDatCentro;
	private JPanel pnDatosTituloColegiadoLabel;
	private JPanel pnDatosTituloColegiadoCheck;
	private JTextField textFieldTitulo;
	private JLabel lblTitulacinSegunSus;
	private JLabel lblNewLabelTitulacionColegiado;

	public MainWindow() {
		setTitle("COIIPA : Gestión de servicios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 932, 402);
		mainPanel = new JPanel();
		mainPanel.setName("");
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		mainPanel.setLayout(new CardLayout(0, 0));

		// TODO: Cambiar orden para ver al arrancar el programa
		mainPanel.add(getPnSolicitudColegiado(), "solicitudColegiadoPanel");
		mainPanel.add(getPnCoursesList(), "coursesTablePanel");
		mainPanel.add(getPnHome(), "homePanel");
		mainPanel.add(getPnLogin(), "loginPanel");
		mainPanel.add(getPnProgramAccess(), "programAccessPanel");

		// Centrar la ventana
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		inicializarCampos();
	}

	private void inicializarCampos() {
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
			pnSolicitudColegiado.setLayout(new BorderLayout(0, 0));
			pnSolicitudColegiado.add(getPnSolicitudColegiadoNorte(), BorderLayout.NORTH);
			pnSolicitudColegiado.add(getPnSolicitudColegiadoCentro());
			pnSolicitudColegiado.add(getPanelDatosSur(), BorderLayout.SOUTH);
		}
		return pnSolicitudColegiado;
	}

	private JPanel getPnSolicitudColegiadoNorte() {
		if (pnSolicitudColegiadoNorte == null) {
			pnSolicitudColegiadoNorte = new JPanel();
			pnSolicitudColegiadoNorte.setForeground(SystemColor.desktop);
			pnSolicitudColegiadoNorte.setLayout(new GridLayout(2, 2, 0, 0));
			pnSolicitudColegiadoNorte.add(getLbTitulo());
			pnSolicitudColegiadoNorte.add(getLbRellenarDatos_1());
		}
		return pnSolicitudColegiadoNorte;
	}

	private JPanel getPnSolicitudColegiadoCentro() {
		if (pnSolicitudColegiadoCentro == null) {
			pnSolicitudColegiadoCentro = new JPanel();
			pnSolicitudColegiadoCentro.setLayout(new BorderLayout(0, 0));
			pnSolicitudColegiadoCentro.add(getPnSolicitudDatosCentro(), BorderLayout.CENTER);
			pnSolicitudColegiadoCentro.add(getPnSolicitudDatosSur(), BorderLayout.SOUTH);
		}
		return pnSolicitudColegiadoCentro;
	}

	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel("Solicitud de alta para ser colegiado en el COIIPA");
			lbTitulo.setFont(new Font("Arial Black", Font.BOLD, 11));
			lbTitulo.setFont(LookAndFeel.HEADING_1_FONT);
			lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbTitulo;
	}

	private JPanel getPnSolicitudDatosCentro() {
		if (pnSolicitudDatosCentro == null) {
			pnSolicitudDatosCentro = new JPanel();
			pnSolicitudDatosCentro.setLayout(new BorderLayout(0, 0));
			pnSolicitudDatosCentro.add(getPanelDatosPersonalesColegiado(), BorderLayout.NORTH);
			pnSolicitudDatosCentro.add(getPnDatosGeoAcademicos(), BorderLayout.CENTER);
		}
		return pnSolicitudDatosCentro;
	}

	private JPanel getPanelDatosSur() {
		if (pnSolicitudColegiadoSur == null) {
			pnSolicitudColegiadoSur = new JPanel();
			pnSolicitudColegiadoSur.setLayout(new GridLayout(1, 3, 0, 0));
			pnSolicitudColegiadoSur.add(getBtnAtras());
			pnSolicitudColegiadoSur.add(getBtnLimpiar());
			pnSolicitudColegiadoSur.add(getBtnFinalizar());
		}
		return pnSolicitudColegiadoSur;
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
		dto.poblacion = getTextFieldPoblacion().getText();
		dto.centro = getTextFieldCentroColegiado().getText();
		try {
			dto.telefono = Integer.parseInt(getTextFieldTelefono().getText());
			dto.titulacion = Integer.parseInt(getTextFieldTitulacion().getText());
			dto.annio = Integer.parseInt(getTextFieldAño().getText());
			dto.numeroTarjeta = Integer.parseInt(getTextFieldTarjeta().getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,
					"Por favor, revise que no se haya introducido ninguna cadena en alguno de los campos numéricos",
					"Formato numérico incorrecto", JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		return dto;
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
		this.getTextFieldAño().setText(null);
		this.getTextFieldApellidos().setText(null);
		this.getTextFieldDni().setText(null);
		this.getTextFieldNombre().setText(null);
		this.getTextFieldTarjeta().setText(null);
		this.getTextFieldCentroColegiado().setText(null);
		this.getTextFieldPoblacion().setText(null);
		this.getTextFieldTelefono().setText(null);
		this.getTextFieldTitulacion().setText(null);
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
	private JLabel getLbRellenarDatos_1() {
		if (lbRellenarDatos == null) {
			lbRellenarDatos = new JLabel("Por favor, rellene los siguientes datos para formalizar su solicitud:");
			lbRellenarDatos.setHorizontalAlignment(SwingConstants.CENTER);
			lbRellenarDatos.setFont(LookAndFeel.HEADING_2_FONT);
		}
		return lbRellenarDatos;
	}
	private JPanel getPnSolicitudDatosSur() {
		if (pnSolicitudDatosSur == null) {
			pnSolicitudDatosSur = new JPanel();
			pnSolicitudDatosSur.setLayout(new GridLayout(1, 2, 0, 0));
			pnSolicitudDatosSur.add(getPnTarjetaLabel());
			pnSolicitudDatosSur.add(getPnTarjetaTextField());
		}
		return pnSolicitudDatosSur;
	}
	private JPanel getPnTarjetaLabel() {
		if (pnTarjetaLabel == null) {
			pnTarjetaLabel = new JPanel();
			pnTarjetaLabel.add(getLblParaFinalizarDebe());
		}
		return pnTarjetaLabel;
	}
	private JPanel getPnTarjetaTextField() {
		if (pnTarjetaTextField == null) {
			pnTarjetaTextField = new JPanel();
			pnTarjetaTextField.setLayout(new GridLayout(1, 2, 0, 0));
			pnTarjetaTextField.add(getLblNewLabelNumeroTarjeta());
			pnTarjetaTextField.add(getTextFieldTarjeta());
		}
		return pnTarjetaTextField;
	}
	private JLabel getLblParaFinalizarDebe() {
		if (lblParaFinalizarDebe == null) {
			lblParaFinalizarDebe = new JLabel("Para finalizar, debe registrar sus datos bancarios para el pago de las cuotas:");
			lblParaFinalizarDebe.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return lblParaFinalizarDebe;
	}
	private JLabel getLblNewLabelNumeroTarjeta() {
		if (lblNewLabelNumeroTarjeta == null) {
			lblNewLabelNumeroTarjeta = new JLabel("Número de tarjeta:");
			lblNewLabelNumeroTarjeta.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelNumeroTarjeta.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelNumeroTarjeta.setDisplayedMnemonic('N');
			lblNewLabelNumeroTarjeta.setLabelFor(getTextFieldTarjeta());
		}
		return lblNewLabelNumeroTarjeta;
	}
	private JTextField getTextFieldTarjeta() {
		if (textFieldTarjeta == null) {
			textFieldTarjeta = new JTextField();
			textFieldTarjeta.setToolTipText("Registre su número de tarjeta");
			textFieldTarjeta.setText("Ej: 76567 [5 números]");
			textFieldTarjeta.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTarjeta.setColumns(10);
		}
		return textFieldTarjeta;
	}
	private JPanel getPanelDatosPersonalesColegiado() {
		if (pnDatosPersonales == null) {
			pnDatosPersonales = new JPanel();
			pnDatosPersonales.setLayout(new GridLayout(0, 3, 0, 0));
			pnDatosPersonales.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
					"Datos personales", TitledBorder.CENTER, TitledBorder.TOP, null, Color.GRAY));
			pnDatosPersonales.add(getPnNombreColegiado());
			pnDatosPersonales.add(getPnApellidosColegiado());
			pnDatosPersonales.add(getPnDNIColegiado());
		}
		return pnDatosPersonales;
	}
	private JPanel getPnNombreColegiado() {
		if (pnNombreColegiado == null) {
			pnNombreColegiado = new JPanel();
			pnNombreColegiado.setLayout(new GridLayout(0, 2, 0, 0));
			pnNombreColegiado.add(getLblNewLabelN());
			pnNombreColegiado.add(getTextFieldNombre());
		}
		return pnNombreColegiado;
	}
	private JPanel getPnApellidosColegiado() {
		if (pnApellidosColegiado == null) {
			pnApellidosColegiado = new JPanel();
			pnApellidosColegiado.setLayout(new GridLayout(0, 2, 0, 0));
			pnApellidosColegiado.add(getLblNewLabelA());
			pnApellidosColegiado.add(getTextFieldApellidos());
		}
		return pnApellidosColegiado;
	}
	private JPanel getPnDNIColegiado() {
		if (pnDNIColegiado == null) {
			pnDNIColegiado = new JPanel();
			pnDNIColegiado.setLayout(null);
			pnDNIColegiado.add(getLblNewLabelD());
			pnDNIColegiado.add(getTextFieldDni());
		}
		return pnDNIColegiado;
	}
	private JLabel getLblNewLabelN() {
		if (lblNewLabelN == null) {
			lblNewLabelN = new JLabel("Nombre:");
			lblNewLabelN.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelN.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelN.setDisplayedMnemonic('N');
			lblNewLabelN.setLabelFor(getTextFieldNombre());
		}
		return lblNewLabelN;
	}
	private JTextField getTextFieldNombre() {
		if (textFieldNombre == null) {
			textFieldNombre = new JTextField();
			textFieldNombre.setToolTipText("Inserte el nombre");
			textFieldNombre.setText("Ej: Miguel");
			textFieldNombre.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldNombre.setColumns(10);
		}
		return textFieldNombre;
	}
	private JLabel getLblNewLabelA() {
		if (lblNewLabelA == null) {
			lblNewLabelA = new JLabel("Apellidos:");
			lblNewLabelA.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelA.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelA.setDisplayedMnemonic('A');
			lblNewLabelA.setLabelFor(getTextFieldApellidos());
		}
		return lblNewLabelA;
	}
	private JTextField getTextFieldApellidos() {
		if (textFieldApellidos == null) {
			textFieldApellidos = new JTextField();
			textFieldApellidos.setToolTipText("Inserte los apellidos");
			textFieldApellidos.setText("Ej: Gonzalez Navarro");
			textFieldApellidos.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldApellidos.setColumns(10);
		}
		return textFieldApellidos;
	}
	private JLabel getLblNewLabelD() {
		if (lblNewLabelD == null) {
			lblNewLabelD = new JLabel("DNI:");
			lblNewLabelD.setBounds(0, 0, 146, 20);
			lblNewLabelD.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelD.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelD.setDisplayedMnemonic('D');
			lblNewLabelD.setLabelFor(getTextFieldDni());
		}
		return lblNewLabelD;
	}
	private JTextField getTextFieldDni() {
		if (textFieldDni == null) {
			textFieldDni = new JTextField();
			textFieldDni.setBounds(146, 0, 146, 20);
			textFieldDni.setToolTipText("Inserte el DNI");
			textFieldDni.setText("Ej:71778880C");
			textFieldDni.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldDni.setColumns(10);
		}
		return textFieldDni;
	}
	private JPanel getPnDatosGeoAcademicos() {
		if (pnDatosGeoAcademicos == null) {
			pnDatosGeoAcademicos = new JPanel();
			pnDatosGeoAcademicos.setLayout(new GridLayout(0, 2, 0, 0));
			pnDatosGeoAcademicos.add(getPanelDatosGeo());
			pnDatosGeoAcademicos.add(getPanelDatosAcademicos());
		}
		return pnDatosGeoAcademicos;
	}
	private JPanel getPanelDatosGeo() {
		if (panelDatosGeo == null) {
			panelDatosGeo = new JPanel();
			panelDatosGeo.setBorder(new TitledBorder(
														new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
														"Datos geográficos", TitledBorder.CENTER, TitledBorder.TOP, null, Color.GRAY));
			panelDatosGeo.setLayout(new GridLayout(2, 2, 0, 0));
			panelDatosGeo.add(getPanelDatosGeoPoblacion());
			panelDatosGeo.add(getPanelDatosTelefonoColegiado());
		}
		return panelDatosGeo;
	}
	private JPanel getPanelDatosGeoPoblacion() {
		if (pnDatosPoblacionColegiado == null) {
			pnDatosPoblacionColegiado = new JPanel();
			pnDatosPoblacionColegiado.setLayout(new GridLayout(0, 2, 0, 0));
			pnDatosPoblacionColegiado.add(getLblNewLabelPob());
			pnDatosPoblacionColegiado.add(getTextFieldPoblacion());
		}
		return pnDatosPoblacionColegiado;
	}
	private JLabel getLblNewLabelPob() {
		if (lblNewLabelPob == null) {
			lblNewLabelPob = new JLabel("Población:");
			lblNewLabelPob.setToolTipText("Introduzca su población");
			lblNewLabelPob.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelPob.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelPob.setDisplayedMnemonic('P');
			lblNewLabelPob.setLabelFor(getTextFieldPoblacion());
		}
		return lblNewLabelPob;
	}
	private JTextField getTextFieldPoblacion() {
		if (textFieldPoblacion == null) {
			textFieldPoblacion = new JTextField();
			textFieldPoblacion.setToolTipText("Introduzca su población");
			textFieldPoblacion.setText("Ej: Moreda");
			textFieldPoblacion.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldPoblacion.setColumns(10);
		}
		return textFieldPoblacion;
	}
	private JPanel getPanelDatosTelefonoColegiado() {
		if (pnDatosTelefonoColegiado == null) {
			pnDatosTelefonoColegiado = new JPanel();
			pnDatosTelefonoColegiado.setLayout(new GridLayout(0, 2, 0, 0));
			pnDatosTelefonoColegiado.add(getLblNewLabelTelefonoColegiado());
			pnDatosTelefonoColegiado.add(getTextFieldTelefono());
		}
		return pnDatosTelefonoColegiado;
	}
	private JLabel getLblNewLabelTelefonoColegiado() {
		if (lblNewLabelTelefono == null) {
			lblNewLabelTelefono = new JLabel("Teléfono de contacto:");
			lblNewLabelTelefono.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelTelefono.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelTelefono.setDisplayedMnemonic('T');
			lblNewLabelTelefono.setLabelFor(getTextFieldTelefono());
		}
		return lblNewLabelTelefono;
	}
	private JTextField getTextFieldTelefono() {
		if (textFieldTelefono == null) {
			textFieldTelefono = new JTextField();
			textFieldTelefono.setToolTipText("Introduzca su teléfono");
			textFieldTelefono.setText("Ej: 681676654 [9 números]");
			textFieldTelefono.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTelefono.setColumns(10);
		}
		return textFieldTelefono;
	}
	private JPanel getPanelDatosAcademicos() {
		if (panelDatosAcademicos == null) {
			panelDatosAcademicos = new JPanel();
			panelDatosAcademicos.setBorder(new TitledBorder(
											new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
											"Datos acad\u00E9micos", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(128, 128, 128)));
			panelDatosAcademicos.setLayout(new GridLayout(0, 1, 0, 0));
			panelDatosAcademicos.add(getPnDatosTituloColegiado());
			panelDatosAcademicos.add(getPnDatosCentroColegiado());
			panelDatosAcademicos.add(getPnDatosAñoColegiado());
		}
		return panelDatosAcademicos;
	}
	private JPanel getPnDatosTituloColegiado() {
		if (pnDatosTituloColegiado == null) {
			pnDatosTituloColegiado = new JPanel();
			pnDatosTituloColegiado.setLayout(new GridLayout(0, 2, 0, 0));
			pnDatosTituloColegiado.add(getPnDatosTituloColegiadoLabel());
			pnDatosTituloColegiado.add(getPnDatosTituloColegiadoCheck());
		}
		return pnDatosTituloColegiado;
	}
	private JPanel getPnDatosCentroColegiado() {
		if (pnDatosCentroColegiado == null) {
			pnDatosCentroColegiado = new JPanel();
			pnDatosCentroColegiado.setLayout(new GridLayout(0, 2, 0, 0));
			pnDatosCentroColegiado.add(getLblNewLabelDatCentro());
			pnDatosCentroColegiado.add(getTextFieldCentroColegiado());
		}
		return pnDatosCentroColegiado;
	}
	private JPanel getPnDatosAñoColegiado() {
		if (pnDatosAñoColegiado == null) {
			pnDatosAñoColegiado = new JPanel();
			pnDatosAñoColegiado.setLayout(new GridLayout(0, 2, 0, 0));
			pnDatosAñoColegiado.add(getLblNewLabelDatAno());
			pnDatosAñoColegiado.add(getTextFieldAño());
		}
		return pnDatosAñoColegiado;
	}
	private JTextField getTextFieldAño() {
		if (textFieldAño == null) {
			textFieldAño = new JTextField();
			textFieldAño.setToolTipText("Escriba el ano");
			textFieldAño.setText("Ej : 2022");
			textFieldAño.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldAño.setColumns(10);
		}
		return textFieldAño;
	}
	private JLabel getLblNewLabelDatAno() {
		if (lblNewLabelDatAno == null) {
			lblNewLabelDatAno = new JLabel("Año:");
			lblNewLabelDatAno.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelDatAno.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelDatAno.setDisplayedMnemonic('O');
			lblNewLabelDatAno.setLabelFor(getTextFieldAño());
		}
		return lblNewLabelDatAno;
	}
	private JTextField getTextFieldCentroColegiado() {
		if (textFieldCentro == null) {
			textFieldCentro = new JTextField();
			textFieldCentro.setToolTipText("Escriba su centro educativo");
			textFieldCentro.setText("Ej: Escuela de Ingeniería");
			textFieldCentro.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldCentro.setColumns(10);
		}
		return textFieldCentro;
	}
	private JLabel getLblNewLabelDatCentro() {
		if (lblNewLabelDatCentro == null) {
			lblNewLabelDatCentro = new JLabel("Centro:");
			lblNewLabelDatCentro.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelDatCentro.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelDatCentro.setDisplayedMnemonic('C');
			lblNewLabelDatCentro.setLabelFor(getTextFieldCentroColegiado());
		}
		return lblNewLabelDatCentro;
	}
	private JPanel getPnDatosTituloColegiadoLabel() {
		if (pnDatosTituloColegiadoLabel == null) {
			pnDatosTituloColegiadoLabel = new JPanel();
			pnDatosTituloColegiadoLabel.setLayout(new GridLayout(0, 1, 0, 0));
			pnDatosTituloColegiadoLabel.add(getLblTitulacinSegunSus());
			pnDatosTituloColegiadoLabel.add(getLabelTitulacionColegiado());
		}
		return pnDatosTituloColegiadoLabel;
	}
	private JPanel getPnDatosTituloColegiadoCheck() {
		if (pnDatosTituloColegiadoCheck == null) {
			pnDatosTituloColegiadoCheck = new JPanel();
			pnDatosTituloColegiadoCheck.setLayout(new GridLayout(0, 1, 0, 0));
			pnDatosTituloColegiadoCheck.add(getTextFieldTitulacion());
		}
		return pnDatosTituloColegiadoCheck;
	}
	private JTextField getTextFieldTitulacion() {
		if (textFieldTitulo == null) {
			textFieldTitulo = new JTextField();
			textFieldTitulo.setToolTipText("Teclee la titulación");
			textFieldTitulo.setText("Ej: 0 (sin titulación)");
			textFieldTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTitulo.setColumns(10);
		}
		return textFieldTitulo;
	}
	private JLabel getLblTitulacinSegunSus() {
		if (lblTitulacinSegunSus == null) {
			lblTitulacinSegunSus = new JLabel("Titulación segun sus estudios:");
			lblTitulacinSegunSus.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitulacinSegunSus.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblTitulacinSegunSus.setDisplayedMnemonic('I');
			lblTitulacinSegunSus.setLabelFor(getTextFieldTitulacion());
		}
		return lblTitulacinSegunSus;
	}
	private JLabel getLabelTitulacionColegiado() {
		if (lblNewLabelTitulacionColegiado == null) {
			lblNewLabelTitulacionColegiado = new JLabel("[0, 1(ingeniero), 2(Otros)]");
			lblNewLabelTitulacionColegiado.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNewLabelTitulacionColegiado;
	}
}
