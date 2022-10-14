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
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import business.BusinessException;
import business.InscripcionColegiado.InscripcionColegiado;
import business.colegiado.Colegiado;
import business.curso.Curso;
import business.inscripcion.InscripcionCursoFormativo;
import business.util.DateUtils;
import persistence.colegiado.ColegiadoDto;
import persistence.curso.CursoCRUD;
import persistence.curso.CursoDto;
import persistence.jdbc.PersistenceException;
import persistence.recibo.ReciboCRUD;
import ui.components.LookAndFeel;
import ui.components.buttons.ButtonColor;
import ui.components.buttons.DefaultButton;
import ui.components.messages.DefaultMessage;
import ui.components.messages.MessageType;
import ui.model.CursoModel;
import ui.util.TimeFormatter;
import com.toedter.calendar.JCalendar;
import javax.swing.JComboBox;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -8203812316779660921L;

	// Nombres de los paneles contenidos en mainPanel
	private static final String HOME_PANEL_NAME = "homePanel";
	private static final String SOLICITUD_COLEGIADO_PANEL_NAME = "solicitudAltaColegiado";
	private static final String LOGIN_COLEGIADO_PANEL = "loginColegiadoPanel";
	private static final String APERTURA_INSCRIPCIONES_PANEL_NAME = "aperturaCursoPanel";
	private static final String INSCRIPCION_CURSO_PANEL_NAME = "inscripicionCursoPanel";
	private static final String ADD_CURSO_PANEL_NAME = "addCursoPanel";
	private static final String PAGAR_INSCRIPCION_CURSO_PANEL_NAME = "pagarInscripcionColegiado";
	private static final String LISTADO_CURSOS_PANEL_NAME = "listadoCursosPanel";
	private static final String LISTADO_INSCRIPCIONES_PANEL_NAME = "listadoInscripcionesPanel";
	private static final String CONSULTAR_TITULACION_SOLICITANTE_PANEL_NAME = "consultarTitulacionSolicitantePanel";
	private static final String EMITIR_CUOTAS_COLEGIADOS_PANEL_NAME = "emitirCuotasColegiadosPanels";

	private JPanel mainPanel;

	private JPanel pnHome;
	private JPanel pnAbrirInscripcionesCurso;
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

	private JPanel pnInscripcion;
	private JScrollPane spCursos;
	private JList<CursoDto> lsCursos;
	private JLabel lbInscripcionSeleccionarCursos;
	private JButton btnInscribete;
	private JLabel lbAlerta;
	private JLabel lbConfirmacionInscripcion;
	private ColegiadoDto colegiado;
	private CardLayout mainCardLayout;
	private JButton btnInscripcionToInicio;

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
	private JPanel pnDatosAnoColegiado;
	private JTextField textFieldAno;
	private JLabel lblNewLabelDatAno;
	private JTextField textFieldCentro;
	private JLabel lblNewLabelDatCentro;
	private JPanel pnDatosTituloColegiadoLabel;
	private JPanel pnDatosTituloColegiadoCheck;
	private JTextField textFieldTitulo;
	private JLabel lblTitulacinSegunSus;
	private JLabel lblNewLabelTitulacionColegiado;
	private JPanel pnPagarInscripcionColegiado;
	private JPanel pnPagarInscripcionColegiadoNorte;

	private JTextField txCursoDNI;
	

	private JPanel pnHomeNorth;
	private JPanel pnHomeCenter;
	private JPanel pnHomeSouth;
	private JPanel pnHomeTituloColegiado;
	private JPanel pnHomeTituloSecretaria;
	private JLabel lbTituloHomeColegiado;
	private JLabel lbTituloHomeSecretaria;
	private JPanel pnHomeAccionesColegiado;
	private JPanel pnHomeAccionesSecretaria;
	private DefaultButton btHomeAltaColegiado;
	private DefaultButton btHomeVerCursos;
	private DefaultButton btHomeInscripcionCurso;
	private DefaultButton btHomePagarInscripcion;
	private DefaultButton btHomeSecretariaAbrirInscripciones;
	private DefaultButton btHomeSecretariaConsultarTitulacionSolicitante;
	private DefaultButton btHomeSecretariaEmitirCuotas;
	private JPanel pnHomeSouthDatabaseOptions;
	private JPanel pnHomeClearDatabase;
	private JPanel pnHomeLoadDatabase;
	private DefaultButton btHomeLoadDatabase;
	private JPanel pnHomeSouthTitle;
	private JLabel lbHomeSouthDatabaseTitle;
	private DefaultButton btHomeSecretariaAddCurso;
	private DefaultButton btHomeSecretariaListadoInscripciones;
	private DefaultButton btHomeClearDatabase;
	private JPanel pnListadoCursos;
	private JPanel pnListadoInscripciones;
	private JPanel pnConsultarTitulacionSolicitante;
	private JPanel pnEmitirCuotasColegiados;
	private JPanel pnCrearCurso;
	private JPanel pnCrearCursoTitulo;
	private JLabel lblCrearCurso;
	private JPanel pnCrearCursoCampos;
	private JPanel pnCrearCursoButtons;
	private JLabel lbInscripcionPagoColegiado;
	private JPanel pnModosPagoInscripcionColegiado;
	private JButton btnTransferenciaColegiado;
	private JPanel pnTarjetaDatosColegiado;
	private JButton btnTarjetaCreditoColegiado;
	private JPanel pnNumeroTarjetaDatosColegiado;
	private JPanel pnNumeroFechaCaducidadDatosColegiado;
	private JLabel lblNumeroTarjetaDatosColegiado;
	private JTextField textFieldNumeroTarjetaColegiado;
	private JLabel lblFechaCaducidadDatosColegiado;

	private JCalendar calendarioFechaCaducidad;
	private JPanel pnDatosInicialesPagarInscripcion;
	private JLabel lbInscripcionPagoAviso;
	private JPanel pnDatosInicialesIdColegiado;
	private JPanel pnDatosInicialesIdCurso;
	private JLabel lblNewLabelDNIColegiado;
	private JTextField textFieldDNIColegiado;
	private JLabel lblRellenarDatosInscripcionCurso;
	private JLabel lblNewLabelIdentificadorCurso;

	private JComboBox<Integer> comboBoxIdentificadorCursosAbiertos;
	private DefaultButton btnCrearCursoCancelar;
	private JButton btnCrearCursoCrear;
	private JPanel pnCrearCursoCenterContainer;
	private JPanel pnCrearCursoTituloCurso;
	private JPanel pnCrearCursoFechaImparticion;
	private JPanel pnCrearCursoPrecioInscripcion;
	private JLabel lblTituloCurso;
	private JTextField txCrearCursoTituloCursoInput;
	private JLabel lblFechaImparticion;
	private JTextField txCrearCursoFechaImparticionInput;
	private JLabel lblCrearCursoPrecio;
	private JTextField txCrearCursoPrecioInscripcionInput;
	private JPanel pnPagarInscripcionColegiadoSur;
	private DefaultButton btnInicioInscripcion;


	public MainWindow() {
		setTitle("COIIPA : Gestión de servicios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// TODO: Revisar dimensiones ventana
		setBounds(100, 100, 1126, 740);

		mainPanel = new JPanel();
		mainPanel.setName("");
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);
		
		mainCardLayout = new CardLayout(0, 0);
		mainPanel.setLayout(mainCardLayout);





		// TODO: Cambiar orden para ver al arrancar el programa
		mainPanel.add(getPnHome(), HOME_PANEL_NAME);
		mainPanel.add(getPnSolicitudColegiado(), SOLICITUD_COLEGIADO_PANEL_NAME);
		mainPanel.add(getPnAbrirInscripcionesCurso(), APERTURA_INSCRIPCIONES_PANEL_NAME);
		mainPanel.add(getPnInscripcion(), INSCRIPCION_CURSO_PANEL_NAME);
		mainPanel.add(getPnListadoCursos(), LISTADO_CURSOS_PANEL_NAME);
		mainPanel.add(getPnPagarInscripcionColegiado(), PAGAR_INSCRIPCION_CURSO_PANEL_NAME);
		mainPanel.add(getPnListadoInscripciones(), LISTADO_INSCRIPCIONES_PANEL_NAME);
		mainPanel.add(getPnConsultarTitulacionSolicitante(), CONSULTAR_TITULACION_SOLICITANTE_PANEL_NAME);
		mainPanel.add(getPnEmitirCuotasColegiados(), EMITIR_CUOTAS_COLEGIADOS_PANEL_NAME);
		mainPanel.add(getPnCrearCurso(), ADD_CURSO_PANEL_NAME);

		// Centrar la ventana
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

	}

	private void inicializarCampos() {
		textFieldNombre.grabFocus();
		textFieldApellidos.setText("Ej: Gonzalez Navarro");
		textFieldDni.setText("Ej: 71778880C");
		textFieldPoblacion.setText("Ej: Moreda");
		textFieldTelefono.setText("Ej: 681676654 [9 nÃºmeros]");
		textFieldTitulo.setText("Ej: 0 (sin titulaciÃ³n)");
		textFieldCentro.setText("Ej: Escuela de IngenierÃ­a");
		textFieldAno.setText("Ej : 2022");
		textFieldTarjeta.setText("Ej: 76567 [5 nÃºmeros]");
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	}

	private JPanel getPnHome() {
		if (pnHome == null) {
			pnHome = new JPanel();
			pnHome.setLayout(new BorderLayout(0, 0));
			pnHome.add(getPnHomeNorth(), BorderLayout.NORTH);
			pnHome.add(getPnHomeCenter(), BorderLayout.CENTER);
			pnHome.add(getPnHomeSouth(), BorderLayout.SOUTH);
		}
		return pnHome;
	}

	private JPanel getPnAbrirInscripcionesCurso() {
		if (pnAbrirInscripcionesCurso == null) {
			pnAbrirInscripcionesCurso = new JPanel();
			pnAbrirInscripcionesCurso.setBorder(null);
			pnAbrirInscripcionesCurso.setLayout(new BorderLayout(0, 20));
			pnAbrirInscripcionesCurso.add(getSpCoursesListCenter(), BorderLayout.CENTER);
			pnAbrirInscripcionesCurso.add(getPnCoursesListSouth(), BorderLayout.SOUTH);
			pnAbrirInscripcionesCurso.add(getPnCoursesListNorth(), BorderLayout.NORTH);
		}
		return pnAbrirInscripcionesCurso;
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

			// Evento de selecciÃ³n de curso para abrir inscripciones
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
	 * Panel que muestra los mensajes de la ventana de abrir una inscripciÃ³n.
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
			lbAbrirInscripcionesCurso = new JLabel("APERTURA INSCRIPCIÃ“N CURSO SELECCIONADO");
			lbAbrirInscripcionesCurso.setHorizontalAlignment(SwingConstants.CENTER);
			lbAbrirInscripcionesCurso.setFont(LookAndFeel.HEADING_2_FONT);
			lbAbrirInscripcionesCurso.setToolTipText(
					"Se establecerÃ¡n los plazos de inicio y cierre de inscripciones, asÃ­ como el nÃºmero de plazas del curso seleccionado en la tabla.");
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
			lbNumeroPlazasAperturaCurso = new JLabel("NÃºmero de plazas:");
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
			lbFechaCierreAperturaCurso = new JLabel("Fecha finalizaciÃ³n");
			lbFechaCierreAperturaCurso.setDisplayedMnemonic('z');
			lbFechaCierreAperturaCurso.setHorizontalAlignment(SwingConstants.CENTER);
			lbFechaCierreAperturaCurso.setLabelFor(getFTxFechaCierreInscripcionesCursoSeleccionado());
		}
		return lbFechaCierreAperturaCurso;
	}

	private JFormattedTextField getFTxFechaInicioInscripcionesCursoSeleccionado() {
		// TODO: Reemplazar por JCalendar
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
			lbTituloCursoSeleccionado = new JLabel("TÃ­tulo:");
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
			spNumeroPlazasCursoSeleccionado.setToolTipText("Introduzca el nÃºmero de plazas del curso seleccionado");
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
			btCancelarAperturaCurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
				}
			});
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
								"<html><p>Â¿Confirma que desea abrir las inscripciones para el curso <b>"
										+ selectedCourse.titulo + "</b> ?</p></html>");

						if (input == JOptionPane.OK_OPTION) {
							// Si el curso estÃ¡ abierto, mostrar mensaje de error
							InscripcionCursoFormativo.abrirCursoFormacion(selectedCourse, fechaApertura, fechaCierre,
									plazas);

							((DefaultMessage) pnListCoursesSouthMessages).setMessageColor(MessageType.SUCCESS);
							((DefaultMessage) pnListCoursesSouthMessages)
									.setMessage("Se ha abierto la inscripciÃ³n para el curso seleccionado");

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
		if (pnListCoursesSouthMessages != null) {
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
			pnSolicitudColegiadoSur.setLayout(new GridLayout(1, 3, 10, 0));
			pnSolicitudColegiadoSur.add(getBtnAtras());
			pnSolicitudColegiadoSur.add(getBtnLimpiar());
			pnSolicitudColegiadoSur.add(getBtnFinalizar());
		}
		return pnSolicitudColegiadoSur;
	}

	private JButton getBtnLimpiar() {
		if (btnLimpiar == null) {
			btnLimpiar = new DefaultButton("Limpiar campos", "ventana", "LimpiarCampos", 'l', ButtonColor.NORMAL);
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
			btnFinalizar = new DefaultButton("Finalizar solicitud y enviar", "ventana", "FinalizarSolicitudAlta", 'f',
					ButtonColor.NORMAL);
			btnFinalizar.setMnemonic('F');
			btnFinalizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (anadirColegiado()) {
						mostrarEstadoPendiente();
						reiniciarSolicitudRegistro();
						mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
					}
				}
			});
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
			Colegiado.addColegiado(dto);
			return true;
		} catch (BusinessException e) {
			JOptionPane.showMessageDialog(this,
					"Este DNI ya ha sido registrado para una solicitud.\n"
					+ "En el caso de que no se haya registrado anteriormente, revise que no haya introducido un DNI que no es suyo\n",
					"DNI invÃ¡lido", JOptionPane.WARNING_MESSAGE);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this,
					"Por favor, revise que no deje ningÃºn campo vacÃ­o y se ha introducido correctamente cada dato (longitudes correspondientes, formato de datos, ...)\n"
							+ "Sigue los ejemplos que aparecen en los campos correspondientes",
					"Datos incorrectos", JOptionPane.WARNING_MESSAGE);
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
			dto.annio = Integer.parseInt(getTextFieldAno().getText());
			dto.numeroTarjeta = Integer.parseInt(getTextFieldTarjeta().getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,
					"Por favor, revise que no se haya introducido ninguna cadena en alguno de los campos numÃ©ricos",
					"Formato numÃ©rico incorrecto", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return dto;
	}

	private boolean confirmarBorrar() {
		boolean confir = false;
		int resp = JOptionPane.showConfirmDialog(this, "Â¿ EstÃ¡s seguro de que quieres borrar los campos ?",
				"Borrar los campos", JOptionPane.INFORMATION_MESSAGE);
		if (resp == JOptionPane.YES_OPTION) {
			confir = true;
		}
		return confir;
	}

	private boolean confirmarVolverPrinicipio() {
		boolean confir = false;
		int resp = JOptionPane.showConfirmDialog(this,
				"Â¿ EstÃ¡s seguro de que quieres volver a la pantalla de inicio ?", "Volver al inicio",
				JOptionPane.INFORMATION_MESSAGE);
		if (resp == JOptionPane.YES_OPTION) {
			confir = true;
		}
		return confir;
	}

	private void mostrarEstadoPendiente() {
		JOptionPane.showMessageDialog(this,
				"Se acaba de tramitar su solicitud, quedarÃ¡ en estado 'PENDIENTE' hasta nuevo aviso",
				"Solicitud de colegiado enviada", JOptionPane.INFORMATION_MESSAGE);
	}

	private void reiniciarSolicitudRegistro() {
		this.getTextFieldAno().setText(null);
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

	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new DefaultButton("Vovler a Inicio", "ventana", "VolverAInicio", 'v', ButtonColor.CANCEL);
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (confirmarVolverPrinicipio()) {
						reiniciarSolicitudRegistro();
						mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
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
			lblParaFinalizarDebe = new JLabel(
					"Para finalizar, debe registrar sus datos bancarios para el pago de las cuotas:");
			lblParaFinalizarDebe.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return lblParaFinalizarDebe;
	}

	private JLabel getLblNewLabelNumeroTarjeta() {
		if (lblNewLabelNumeroTarjeta == null) {
			lblNewLabelNumeroTarjeta = new JLabel("NÃºmero de tarjeta:");
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
			textFieldTarjeta.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldTarjeta.setText(null);
				}
			});
			textFieldTarjeta.setToolTipText("Registre su nÃºmero de tarjeta");
			textFieldTarjeta.setText("Ej: 76567 [5 nÃºmeros]");
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
			lblNewLabelN.setDisplayedMnemonic('M');
			lblNewLabelN.setLabelFor(getTextFieldNombre());
		}
		return lblNewLabelN;
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
			textFieldApellidos.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldApellidos.setText(null);
				}
			});
			textFieldApellidos.setToolTipText("Inserte los apellidos");
			textFieldApellidos.setText("Ej: Gonzalez Navarro");
			textFieldApellidos.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldApellidos.grabFocus();
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
			textFieldDni.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldDni.setText(null);
				}
			});
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
					"Datos geogrÃ¡ficos", TitledBorder.CENTER, TitledBorder.TOP, null, Color.GRAY));
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
			lblNewLabelPob.setToolTipText("Introduzca su poblaciÃ³n");
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
			textFieldPoblacion.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldPoblacion.setText(null);
				}
			});
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
			textFieldTelefono.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldTelefono.setText(null);
				}
			});
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
			panelDatosAcademicos.add(getPnDatosAnoColegiado());
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

	private JPanel getPnDatosAnoColegiado() {
		if (pnDatosAnoColegiado == null) {
			pnDatosAnoColegiado = new JPanel();
			pnDatosAnoColegiado.setLayout(new GridLayout(0, 2, 0, 0));
			pnDatosAnoColegiado.add(getLblNewLabelDatAno());
			pnDatosAnoColegiado.add(getTextFieldAno());
		}
		return pnDatosAnoColegiado;
	}

	private JTextField getTextFieldAno() {
		if (textFieldAno == null) {
			textFieldAno = new JTextField();
			textFieldAno.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldAno.setText(null);
				}
			});
			textFieldAno.setToolTipText("Escriba el ano");
			textFieldAno.setText("Ej : 2022");
			textFieldAno.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldAno.setColumns(10);
		}
		return textFieldAno;
	}

	private JLabel getLblNewLabelDatAno() {
		if (lblNewLabelDatAno == null) {
			lblNewLabelDatAno = new JLabel("Ano:");
			lblNewLabelDatAno.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelDatAno.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelDatAno.setDisplayedMnemonic('O');
			lblNewLabelDatAno.setLabelFor(getTextFieldAno());
		}
		return lblNewLabelDatAno;
	}

	private JTextField getTextFieldCentroColegiado() {
		if (textFieldCentro == null) {
			textFieldCentro = new JTextField();
			textFieldCentro.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldCentro.setText(null);
				}
			});
			textFieldCentro.setToolTipText("Escriba su centro educativo");
			textFieldCentro.setText("Ej: Escuela de IngenierÃ­a");
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
			textFieldTitulo.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldTitulo.setText(null);
				}
			});
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

	private JPanel getPnInscripcion() {
		if (pnInscripcion == null) {
			pnInscripcion = new JPanel();
			pnInscripcion.setVisible(false);
			pnInscripcion.setFont(new Font("Arial", Font.BOLD, 14));
			pnInscripcion.setLayout(null);
			pnInscripcion.add(getSpCursos());
			pnInscripcion.add(getLbInscripcionSeleccionarCursos());
			pnInscripcion.add(getBtnInscribete());
			pnInscripcion.add(getLbAlerta());
			pnInscripcion.add(getLbConfirmacionInscripcion());
			pnInscripcion.add(getBtnInscripcionToInicio());
			
			JLabel lbLoginUsername_1 = new JLabel("Introduzca su número de Colegiado-Precolegiado:");
			lbLoginUsername_1.setHorizontalAlignment(SwingConstants.LEFT);
			lbLoginUsername_1.setFont(new Font("Arial", Font.BOLD, 12));
			lbLoginUsername_1.setBounds(356, 10, 324, 35);
			pnInscripcion.add(lbLoginUsername_1);
			
			txCursoDNI = new JTextField();
			txCursoDNI.setToolTipText("Intro");
			txCursoDNI.setColumns(10);
			txCursoDNI.setBounds(356, 44, 324, 35);
			pnInscripcion.add(txCursoDNI);
		}
		return pnInscripcion;
	}

	private JScrollPane getSpCursos() {
		if (spCursos == null) {
			spCursos = new JScrollPane();
			spCursos.setBounds(18, 139, 1057, 408);
			spCursos.setViewportView(getLsCursos());
		}
		return spCursos;
	}

	private JList<CursoDto> getLsCursos() {
		if (lsCursos == null) {
			lsCursos = new JList<CursoDto>();
			lsCursos.setFont(new Font("Arial", Font.BOLD, 15));
		}
		return lsCursos;
	}


	private JLabel getLbInscripcionSeleccionarCursos() {
		if (lbInscripcionSeleccionarCursos == null) {
			lbInscripcionSeleccionarCursos = new JLabel("Selecciona un Curso:");
			lbInscripcionSeleccionarCursos.setDisplayedMnemonic('s');
			lbInscripcionSeleccionarCursos.setLabelFor(getLsCursos());
			lbInscripcionSeleccionarCursos.setFont(new Font("Arial", Font.BOLD, 16));
			lbInscripcionSeleccionarCursos.setBounds(18, 70, 361, 35);
		}
		return lbInscripcionSeleccionarCursos;
	}

	private JButton getBtnInscribete() {
		if (btnInscribete == null) {
			btnInscribete = new DefaultButton("Inscribirme", "ventana", "Inscribirme", 'n', ButtonColor.NORMAL);
			btnInscribete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CursoDto cursoSeleccionado = lsCursos.getSelectedValue();
					try {
						if (cursoSeleccionado == null) {
							lbAlerta.setText("Para Realizar la Inscripcion es Necesario Seleccionar un Curso");
							lbAlerta.setVisible(true);
						} else {
								if (!InscripcionColegiado.isInscrito(colegiado, cursoSeleccionado)) {
									if (InscripcionCursoFormativo.PlazasLibres(cursoSeleccionado)) {
										InscripcionColegiado.InscribirColegiado(cursoSeleccionado, colegiado);
										InscripcionColegiado.EmitirJustificante(colegiado, cursoSeleccionado);
										lbConfirmacionInscripcion.setText(
												"La inscripcion en el curso seleccionado se ha realizado correctamente");
										lbConfirmacionInscripcion.setForeground(Color.green);
										lbConfirmacionInscripcion.setVisible(true);
									} else {
										lbConfirmacionInscripcion.setText(
												"Lo sentimos, no hay plazas disponibles para inscribirse en este curso");
										lbConfirmacionInscripcion.setForeground(Color.red);
										lbConfirmacionInscripcion.setVisible(true);
									}
								} else {
									lbConfirmacionInscripcion.setText(
											"La inscripciÃ³n no se ha realizado porque ya estÃ¡ inscrito en este curso");
									lbConfirmacionInscripcion.setForeground(Color.red);
									lbConfirmacionInscripcion.setVisible(true);
								}
						}
					} catch (PersistenceException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BusinessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			btnInscribete.setBorder(new LineBorder(Color.BLACK, 2));
			btnInscribete.setMnemonic('i');
			btnInscribete.setBackground(Color.GREEN);
			btnInscribete.setFont(new Font("Arial", Font.BOLD, 16));
			btnInscribete.setBounds(731, 581, 191, 61);

		}
		return btnInscribete;
	}

	private JLabel getLbAlerta() {
		if (lbAlerta == null) {

			lbAlerta = new JLabel("Para Realizar la Inscripción es Necesario Seleccionar un Curso");
			lbAlerta.setHorizontalTextPosition(SwingConstants.LEFT);
			lbAlerta.setVisible(false);
			lbAlerta.setBorder(new LineBorder(Color.BLACK));
			lbAlerta.setForeground(Color.RED);
			lbAlerta.setFont(new Font("Arial", Font.BOLD, 14));

			lbAlerta.setHorizontalAlignment(SwingConstants.CENTER);
			lbAlerta.setBounds(209, 98, 678, 29);

		}
		return lbAlerta;
	}

	private JLabel getLbConfirmacionInscripcion() {
		if (lbConfirmacionInscripcion == null) {
			lbConfirmacionInscripcion = new JLabel();
			lbConfirmacionInscripcion.setBackground(Color.WHITE);
			lbConfirmacionInscripcion.setVisible(false);
			lbConfirmacionInscripcion.setOpaque(true);
			lbConfirmacionInscripcion.setHorizontalAlignment(SwingConstants.CENTER);
			lbConfirmacionInscripcion.setFont(new Font("Arial", Font.BOLD, 18));
			lbConfirmacionInscripcion.setBorder(new LineBorder(Color.BLACK));

			lbConfirmacionInscripcion.setBounds(18, 584, 678, 61);

		}
		return lbConfirmacionInscripcion;
	}

	private JButton getBtnInscripcionToInicio() {
		if (btnInscripcionToInicio == null) {

			btnInscripcionToInicio = new DefaultButton("Volver a inicio", "ventana", "VolverAInicio", 'v',
					ButtonColor.NORMAL);
			btnInscripcionToInicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
				}
			});
			btnInscripcionToInicio.setFont(new Font("Tahoma", Font.BOLD, 16));

			btnInscripcionToInicio.setBounds(561, 602, 267, 64);

		}
		return btnInscripcionToInicio;
	}
	private JPanel getPnPagarInscripcionColegiado() {
		if (pnPagarInscripcionColegiado == null) {
			pnPagarInscripcionColegiado = new JPanel();
			pnPagarInscripcionColegiado.setLayout(new BorderLayout(0, 0));
			pnPagarInscripcionColegiado.add(getPnPagarInscripcionColegiadoNorte(), BorderLayout.NORTH);
			pnPagarInscripcionColegiado.add(getPnModosPagoInscripcionColegiado(), BorderLayout.CENTER);
			pnPagarInscripcionColegiado.add(getPnPagarInscripcionColegiadoSur(), BorderLayout.SOUTH);
		}
		return pnPagarInscripcionColegiado;
	}
	private JPanel getPnPagarInscripcionColegiadoNorte() {
		if (pnPagarInscripcionColegiadoNorte == null) {
			pnPagarInscripcionColegiadoNorte = new JPanel();
			pnPagarInscripcionColegiadoNorte.setForeground(Color.BLACK);
			pnPagarInscripcionColegiadoNorte.setLayout(new GridLayout(4, 2, 0, 0));
			pnPagarInscripcionColegiadoNorte.add(getLbInscripcionPagoColegiado());
			pnPagarInscripcionColegiadoNorte.add(getLbInscripcionPagoAviso_1());
			pnPagarInscripcionColegiadoNorte.add(getLblRellenarDatosInscripcionCurso());
			pnPagarInscripcionColegiadoNorte.add(getPnDatosInicialesPagarInscripcion());
		}
		return pnPagarInscripcionColegiadoNorte;
	}

	private JPanel getPnHomeNorth() {
		if (pnHomeNorth == null) {
			pnHomeNorth = new JPanel();
			pnHomeNorth.setOpaque(false);
			pnHomeNorth.setLayout(new GridLayout(0, 2, 10, 0));
			pnHomeNorth.add(getPnHomeTituloColegiado());
			pnHomeNorth.add(getPnHomeTituloSecretaria());
		}
		return pnHomeNorth;
	}

	private JPanel getPnHomeCenter() {
		if (pnHomeCenter == null) {
			pnHomeCenter = new JPanel();
			pnHomeCenter.setOpaque(false);
			pnHomeCenter.setLayout(new GridLayout(0, 2, 10, 0));
			pnHomeCenter.add(getPnHomeAccionesColegiado());
			pnHomeCenter.add(getPnHomeAccionesSecretaria());
		}
		return pnHomeCenter;
	}

	private JPanel getPnHomeSouth() {
		if (pnHomeSouth == null) {
			pnHomeSouth = new JPanel();
			pnHomeSouth.setBackground(LookAndFeel.SECONDARY_COLOR);
			pnHomeSouth.setLayout(new BorderLayout(0, 0));
			pnHomeSouth.add(getPnHomeSouthTitle_1(), BorderLayout.NORTH);
			pnHomeSouth.add(getPnHomeSouthDatabaseOptions());
		}
		return pnHomeSouth;
	}

	private JPanel getPnHomeTituloColegiado() {
		if (pnHomeTituloColegiado == null) {
			pnHomeTituloColegiado = new JPanel();
			pnHomeTituloColegiado.setOpaque(false);
			pnHomeTituloColegiado.add(getLbTituloHomeColegiado());
		}
		return pnHomeTituloColegiado;
	}

	private JPanel getPnHomeTituloSecretaria() {
		if (pnHomeTituloSecretaria == null) {
			pnHomeTituloSecretaria = new JPanel();
			pnHomeTituloSecretaria.add(getLbTituloHomeSecretaria());
		}
		return pnHomeTituloSecretaria;
	}

	private JLabel getLbTituloHomeColegiado() {
		if (lbTituloHomeColegiado == null) {
			lbTituloHomeColegiado = new JLabel("Colegiado");
			lbTituloHomeColegiado.setFont(LookAndFeel.HEADING_1_FONT);
			lbTituloHomeColegiado.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbTituloHomeColegiado;
	}

	private JLabel getLbTituloHomeSecretaria() {
		if (lbTituloHomeSecretaria == null) {
			lbTituloHomeSecretaria = new JLabel("Secretaría");
			lbTituloHomeSecretaria.setFont(LookAndFeel.HEADING_1_FONT);
			lbTituloHomeSecretaria.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbTituloHomeSecretaria;
	}

	private JPanel getPnCrearCurso() {
		if (pnCrearCurso == null) {
			pnCrearCurso = new JPanel();
			pnCrearCurso.setLayout(new BorderLayout(0, 0));
			pnCrearCurso.add(getPnCrearCursoTitulo(), BorderLayout.NORTH);
			pnCrearCurso.add(getPnCrearCursoCampos(), BorderLayout.CENTER);
			pnCrearCurso.add(getPnCrearCursoButtons(), BorderLayout.SOUTH);
		}
		return pnCrearCurso;
	}

	private JPanel getPnCrearCursoTitulo() {
		if (pnCrearCursoTitulo == null) {
			pnCrearCursoTitulo = new JPanel();
			pnCrearCursoTitulo.setBorder(new EmptyBorder(30, 30, 30, 30));
			pnCrearCursoTitulo.add(getLblCrearCurso());
		}
		return pnCrearCursoTitulo;
	}

	private JLabel getLblCrearCurso() {
		if (lblCrearCurso == null) {
			lblCrearCurso = new JLabel("Crear curso");
			lblCrearCurso.setFont(new Font("Tahoma", Font.BOLD, 24));
		}
		return lblCrearCurso;
	}

	private JPanel getPnCrearCursoCampos() {
		if (pnCrearCursoCampos == null) {
			pnCrearCursoCampos = new JPanel();
			pnCrearCursoCampos.setOpaque(false);
			pnCrearCursoCampos.setBorder(new EmptyBorder(0, 100, 0, 100));
			pnCrearCursoCampos.setLayout(new BorderLayout(0, 0));
			pnCrearCursoCampos.add(getPnCrearCursoCenterContainer());
		}
		return pnCrearCursoCampos;
	}

	private JPanel getPnCrearCursoButtons() {
		if (pnCrearCursoButtons == null) {
			pnCrearCursoButtons = new JPanel();
			pnCrearCursoButtons.setOpaque(false);
			pnCrearCursoButtons.setBorder(new EmptyBorder(20, 20, 20, 20));
			pnCrearCursoButtons.setLayout(new GridLayout(0, 2, 10, 0));
			pnCrearCursoButtons.add(getBtnCrearCursoCancelar());
			pnCrearCursoButtons.add(getBtnCrearCursoCrear());
		}
		return pnCrearCursoButtons;
	}

	private DefaultButton getBtnCrearCursoCancelar() {
		if (btnCrearCursoCancelar == null) {
			btnCrearCursoCancelar = new DefaultButton("Volver a Inicio", "ventana", "VolverAInicio", 'v',
					ButtonColor.CANCEL);
			btnCrearCursoCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
				}
			});
			btnCrearCursoCancelar.setBackground(Color.RED);
		}
		return btnCrearCursoCancelar;
	}

	private JButton getBtnCrearCursoCrear() {
		if (btnCrearCursoCrear == null) {
			btnCrearCursoCrear = new DefaultButton("Crear curso", "ventana", "CrearCurso", 'c', ButtonColor.NORMAL);
			btnCrearCursoCrear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// TODO: Pasar comprobaciones a business. Aqui solo imprimir y mostrar mensajes.
					LocalDate fecha;
					double precio = 0.0;
					if (txCrearCursoTituloCursoInput.getText().isEmpty()) {
						JOptionPane.showMessageDialog(pnCrearCurso, "El titulo del curso esta vacio");
						return;
					}
					if (txCrearCursoFechaImparticionInput.getText().isEmpty()) {
						JOptionPane.showMessageDialog(pnCrearCurso, "La fecha de imparticion del curso esta vacio");
						return;
					}
					try {
						fecha = LocalDate.parse(txCrearCursoFechaImparticionInput.getText());
					} catch (Exception e) {
						txCrearCursoFechaImparticionInput.setText("");
						txCrearCursoFechaImparticionInput.grabFocus();
						JOptionPane.showMessageDialog(pnCrearCurso, "Introduzca un formato de fecha valido");
						return;
					}
					if (fecha.compareTo(LocalDate.now()) < 0) {
						JOptionPane.showMessageDialog(pnCrearCurso,
								"No puede crear un curso con una fecha anterior a la actual");
						return;
					}

					if (txCrearCursoPrecioInscripcionInput.getText().isEmpty()) {
						JOptionPane.showMessageDialog(pnCrearCurso, "El precio del curso esta vacio");
						return;
					}
					try {
						precio = Double.valueOf(txCrearCursoPrecioInscripcionInput.getText());
					} catch (Exception e) {
						txCrearCursoPrecioInscripcionInput.setText("");
						txCrearCursoPrecioInscripcionInput.grabFocus();
						JOptionPane.showMessageDialog(pnCrearCurso,
								"Introduzca un valor decimal " + "para el precio del curso");
						return;
					}
					if (precio <= 0) {
						txCrearCursoPrecioInscripcionInput.setText("");
						txCrearCursoPrecioInscripcionInput.grabFocus();
						JOptionPane.showMessageDialog(pnCrearCurso, "El precio tiene que ser positivo");
						return;
					}
					CursoDto curso = new CursoDto();
					curso.titulo = txCrearCursoTituloCursoInput.getText();
					curso.precio = precio;
					curso.fechaInicio = fecha;
					curso.estado = CursoDto.CURSO_PLANIFICADO;
					curso.codigoCurso = CursoCRUD.generarCodigoCurso();

					try {
						CursoCRUD.add(curso);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(pnCrearCurso, "Algo fue mal mientras se creo el curso");
						return;
					}

					JOptionPane.showMessageDialog(pnCrearCurso, "Curso creado correctamente");
				}
			});
		}
		return btnCrearCursoCrear;
	}

	private JPanel getPnHomeAccionesColegiado() {
		if (pnHomeAccionesColegiado == null) {
			pnHomeAccionesColegiado = new JPanel();
			pnHomeAccionesColegiado.setBorder(new EmptyBorder(50, 50, 50, 50));
			pnHomeAccionesColegiado.setOpaque(false);
			pnHomeAccionesColegiado.setLayout(new GridLayout(4, 1, 0, 10));

			pnHomeAccionesColegiado.add(getBtHomeAltaColegiado());
			pnHomeAccionesColegiado.add(getBtHomeVerCursos());
			pnHomeAccionesColegiado.add(getBtHomeInscripcionCurso());
			pnHomeAccionesColegiado.add(getBtHomePagarInscripcion());
		}
		return pnHomeAccionesColegiado;
	}

	private JPanel getPnHomeAccionesSecretaria() {
		if (pnHomeAccionesSecretaria == null) {
			pnHomeAccionesSecretaria = new JPanel();
			pnHomeAccionesSecretaria.setBorder(new EmptyBorder(50, 50, 50, 50));
			pnHomeAccionesSecretaria.setOpaque(false);
			pnHomeAccionesSecretaria.setLayout(new GridLayout(5, 1, 0, 10));
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaAbrirInscripciones());
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaConsultarTitulacionSolicitante());
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaEmitirCuotas());
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaAddCurso());
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaListadoInscripciones());
		}
		return pnHomeAccionesSecretaria;
	}

	private DefaultButton getBtHomeAltaColegiado() {
		if (btHomeAltaColegiado == null) {
			btHomeAltaColegiado = new DefaultButton("Darse de alta", "ventana", "AltaColegiado", 'l',
					ButtonColor.NORMAL);
			btHomeAltaColegiado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, SOLICITUD_COLEGIADO_PANEL_NAME);
					inicializarCampos();
				}
			});
		}
		return btHomeAltaColegiado;
	}

	private DefaultButton getBtHomeVerCursos() {
		if (btHomeVerCursos == null) {
			btHomeVerCursos = new DefaultButton("Ver cursos", "ventana", "VerCursos", 'v', ButtonColor.NORMAL);
			btHomeVerCursos.setEnabled(false);
			btHomeVerCursos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, LISTADO_CURSOS_PANEL_NAME);
				}
			});
		}
		return btHomeVerCursos;
	}

	private DefaultButton getBtHomeInscripcionCurso() {
		if (btHomeInscripcionCurso == null) {
			btHomeInscripcionCurso = new DefaultButton("Inscribirse a un curso", "ventana", "InscribirseCursoColegiado",
					'n', ButtonColor.NORMAL);
			btHomeInscripcionCurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, INSCRIPCION_CURSO_PANEL_NAME);
				}
			});
		}
		return btHomeInscripcionCurso;
	}

	private DefaultButton getBtHomePagarInscripcion() {
		if (btHomePagarInscripcion == null) {
			btHomePagarInscripcion = new DefaultButton("Pagar una inscripción", "ventana", "PagarInscripcionColegiado",
					'p', ButtonColor.NORMAL);
			btHomePagarInscripcion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (Curso.listarCursosAbiertos().isEmpty()) {
							JOptionPane.showMessageDialog(null, "No puede pagar la inscripción de un curso debido a que no hay ninguno abierto"
									, "No puede hacerse cargo de la inscripción", JOptionPane.WARNING_MESSAGE);
						} else {
							textFieldDNIColegiado.grabFocus();
							mainCardLayout.show(mainPanel, PAGAR_INSCRIPCION_CURSO_PANEL_NAME);
						}
					} catch (BusinessException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btHomePagarInscripcion;
	}

	private DefaultButton getBtHomeSecretariaAbrirInscripciones() {
		if (btHomeSecretariaAbrirInscripciones == null) {
			btHomeSecretariaAbrirInscripciones = new DefaultButton("Abrir inscripciones de un curso", "ventana",
					"AbrirInscrionesCurso", 'b', ButtonColor.NORMAL);
			btHomeSecretariaAbrirInscripciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, APERTURA_INSCRIPCIONES_PANEL_NAME);
				}
			});
		}
		return btHomeSecretariaAbrirInscripciones;
	}

	private DefaultButton getBtHomeSecretariaConsultarTitulacionSolicitante() {
		if (btHomeSecretariaConsultarTitulacionSolicitante == null) {
			btHomeSecretariaConsultarTitulacionSolicitante = new DefaultButton("Consultar titulación de un solicitante",
					"ventana", "ConsultarTitulacionSolicitante", 'c', ButtonColor.NORMAL);
			btHomeSecretariaConsultarTitulacionSolicitante.setEnabled(false);
			btHomeSecretariaConsultarTitulacionSolicitante.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, CONSULTAR_TITULACION_SOLICITANTE_PANEL_NAME);
				}
			});
		}
		return btHomeSecretariaConsultarTitulacionSolicitante;
	}

	private DefaultButton getBtHomeSecretariaEmitirCuotas() {
		if (btHomeSecretariaEmitirCuotas == null) {
			btHomeSecretariaEmitirCuotas = new DefaultButton("Emitir cuotas colegiados", "ventana",
					"EmitirCuotasColegiados", 't', ButtonColor.NORMAL);
			btHomeSecretariaEmitirCuotas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, EMITIR_CUOTAS_COLEGIADOS_PANEL_NAME);
					if (ReciboCRUD.emitirCuotas()) {
						JOptionPane.showMessageDialog(null, "Cuotas emitidads correctamente");;
					} else {
						JOptionPane.showMessageDialog(null, "No hay cuotas pendientes");
					}
				}
			});
		}
		return btHomeSecretariaEmitirCuotas;
	}

	private JPanel getPnHomeSouthDatabaseOptions() {
		if (pnHomeSouthDatabaseOptions == null) {
			pnHomeSouthDatabaseOptions = new JPanel();
			pnHomeSouthDatabaseOptions.setOpaque(false);
			pnHomeSouthDatabaseOptions.setBorder(new EmptyBorder(30, 50, 30, 50));
			pnHomeSouthDatabaseOptions.setBackground(LookAndFeel.SECONDARY_COLOR);
			pnHomeSouthDatabaseOptions.setLayout(new GridLayout(0, 2, 30, 0));
			pnHomeSouthDatabaseOptions.add(getPnHomeLoadDatabase());
			pnHomeSouthDatabaseOptions.add(getPnHomeClearDatabase());
		}
		return pnHomeSouthDatabaseOptions;
	}

	private JPanel getPnHomeClearDatabase() {
		if (pnHomeClearDatabase == null) {
			pnHomeClearDatabase = new JPanel();
			pnHomeClearDatabase.setOpaque(false);
			pnHomeClearDatabase.setLayout(new BorderLayout(0, 0));
			pnHomeClearDatabase.add(getBtHomeLoadDatabase());
		}
		return pnHomeClearDatabase;
	}

	private JPanel getPnHomeLoadDatabase() {
		if (pnHomeLoadDatabase == null) {
			pnHomeLoadDatabase = new JPanel();
			pnHomeLoadDatabase.setOpaque(false);
			pnHomeLoadDatabase.setLayout(new BorderLayout(0, 0));
			pnHomeLoadDatabase.add(getBtHomeClearDatabase());
		}
		return pnHomeLoadDatabase;
	}

	private DefaultButton getBtHomeLoadDatabase() {
		if (btHomeLoadDatabase == null) {
			btHomeLoadDatabase = new DefaultButton("Cargar datos", "ventana", "CargarDatos", 'r', ButtonColor.INFO);
			btHomeLoadDatabase.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO: Cargar datos en la base de datos
				}
			});
		}
		return btHomeLoadDatabase;
	}

	private JPanel getPnHomeSouthTitle_1() {
		if (pnHomeSouthTitle == null) {
			pnHomeSouthTitle = new JPanel();
			pnHomeSouthTitle.setOpaque(false);
			FlowLayout fl_pnHomeSouthTitle = new FlowLayout(FlowLayout.CENTER, 5, 5);
			fl_pnHomeSouthTitle.setAlignOnBaseline(true);
			pnHomeSouthTitle.setLayout(fl_pnHomeSouthTitle);
			pnHomeSouthTitle.add(getLbHomeSouthDatabaseTitle_1());
		}
		return pnHomeSouthTitle;
	}

	private JLabel getLbHomeSouthDatabaseTitle_1() {
		if (lbHomeSouthDatabaseTitle == null) {
			lbHomeSouthDatabaseTitle = new JLabel("Base de datos");
			lbHomeSouthDatabaseTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lbHomeSouthDatabaseTitle.setFont(new Font("Arial", Font.PLAIN, 24));
			lbHomeSouthDatabaseTitle.setForeground(LookAndFeel.TERTIARY_COLOR);
		}
		return lbHomeSouthDatabaseTitle;
	}

	private DefaultButton getBtHomeSecretariaAddCurso() {
		if (btHomeSecretariaAddCurso == null) {
			btHomeSecretariaAddCurso = new DefaultButton("Añadir un curso", "ventana", "AddCurso", 'd',
					ButtonColor.NORMAL);
			btHomeSecretariaAddCurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, ADD_CURSO_PANEL_NAME);
				}
			});
		}
		return btHomeSecretariaAddCurso;
	}

	private DefaultButton getBtHomeSecretariaListadoInscripciones() {
		if (btHomeSecretariaListadoInscripciones == null) {
			btHomeSecretariaListadoInscripciones = new DefaultButton("Ver todas las inscripciones", "ventana",
					"ListadoInscripciones", 'l', ButtonColor.NORMAL);
			btHomeSecretariaListadoInscripciones.setEnabled(false);
			btHomeSecretariaListadoInscripciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, LISTADO_INSCRIPCIONES_PANEL_NAME);
				}
			});
		}
		return btHomeSecretariaListadoInscripciones;
	}

	private DefaultButton getBtHomeClearDatabase() {
		if (btHomeClearDatabase == null) {
			btHomeClearDatabase = new DefaultButton("Vaciar BBDD", "ventana", "VaciarDB", 'v', ButtonColor.INFO);
			btHomeClearDatabase.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO: Vaciar base de datos
				}
			});
		}
		return btHomeClearDatabase;
	}

	private JPanel getPnListadoCursos() {
		if (pnListadoCursos == null) {
			pnListadoCursos = new JPanel();
			pnListadoCursos.setLayout(new BorderLayout(0, 0));
		}
		return pnListadoCursos;
	}


	private JPanel getPnListadoInscripciones() {
		if (pnListadoInscripciones == null) {
			pnListadoInscripciones = new JPanel();
			pnListadoInscripciones.setLayout(new BorderLayout(0, 0));
		}
		return pnListadoInscripciones;
	}

	private JPanel getPnConsultarTitulacionSolicitante() {
		if (pnConsultarTitulacionSolicitante == null) {
			pnConsultarTitulacionSolicitante = new JPanel();
			pnConsultarTitulacionSolicitante.setLayout(new BorderLayout(0, 0));
		}
		return pnConsultarTitulacionSolicitante;
	}

	private JPanel getPnEmitirCuotasColegiados() {
		if (pnEmitirCuotasColegiados == null) {
			pnEmitirCuotasColegiados = new JPanel();
			pnEmitirCuotasColegiados.setLayout(new BorderLayout(0, 0));
		}
		return pnEmitirCuotasColegiados;
	}

	private JPanel getPnCrearCursoCenterContainer() {
		if (pnCrearCursoCenterContainer == null) {
			pnCrearCursoCenterContainer = new JPanel();
			pnCrearCursoCenterContainer.setOpaque(false);
			pnCrearCursoCenterContainer.setBounds(new Rectangle(0, 0, 500, 0));
			pnCrearCursoCenterContainer.setBorder(new EmptyBorder(70, 100, 70, 100));
			pnCrearCursoCenterContainer.setLayout(new GridLayout(3, 1, 0, 10));
			pnCrearCursoCenterContainer.add(getPnCrearCursoTituloCurso());
			pnCrearCursoCenterContainer.add(getPnCrearCursoFechaImparticion());
			pnCrearCursoCenterContainer.add(getPnCrearCursoPrecioInscripcion());
		}
		return pnCrearCursoCenterContainer;
	}

	private JPanel getPnCrearCursoTituloCurso() {
		if (pnCrearCursoTituloCurso == null) {
			pnCrearCursoTituloCurso = new JPanel();
			pnCrearCursoTituloCurso.setOpaque(false);
			pnCrearCursoTituloCurso.setLayout(new GridLayout(0, 2, 10, 0));
			pnCrearCursoTituloCurso.add(getLblTituloCurso());
			pnCrearCursoTituloCurso.add(getTxCrearCursoTituloCursoInput());
		}
		return pnCrearCursoTituloCurso;
	}

	private JPanel getPnCrearCursoFechaImparticion() {
		if (pnCrearCursoFechaImparticion == null) {
			pnCrearCursoFechaImparticion = new JPanel();
			pnCrearCursoFechaImparticion.setOpaque(false);
			pnCrearCursoFechaImparticion.setLayout(new GridLayout(0, 2, 10, 0));
			pnCrearCursoFechaImparticion.add(getLblFechaImparticion_1());
			pnCrearCursoFechaImparticion.add(getTxCrearCursoFechaImparticionInput());
		}
		return pnCrearCursoFechaImparticion;
	}

	private JPanel getPnCrearCursoPrecioInscripcion() {
		if (pnCrearCursoPrecioInscripcion == null) {
			pnCrearCursoPrecioInscripcion = new JPanel();
			pnCrearCursoPrecioInscripcion.setOpaque(false);
			pnCrearCursoPrecioInscripcion.setLayout(new GridLayout(0, 2, 10, 0));
			pnCrearCursoPrecioInscripcion.add(getLblCrearCursoPrecio());
			pnCrearCursoPrecioInscripcion.add(getTxCrearCursoPrecioInscripcionInput());
		}
		return pnCrearCursoPrecioInscripcion;
	}

	private JLabel getLblTituloCurso() {
		if (lblTituloCurso == null) {
			lblTituloCurso = new JLabel("Título curso:");
			lblTituloCurso.setLabelFor(getTxCrearCursoTituloCursoInput());
			lblTituloCurso.setHorizontalAlignment(SwingConstants.CENTER);
			lblTituloCurso.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblTituloCurso;
	}

	private JTextField getTxCrearCursoTituloCursoInput() {
		if (txCrearCursoTituloCursoInput == null) {
			txCrearCursoTituloCursoInput = new JTextField();
			txCrearCursoTituloCursoInput.setColumns(10);
		}
		return txCrearCursoTituloCursoInput;
	}

	private JLabel getLblFechaImparticion_1() {
		if (lblFechaImparticion == null) {
			lblFechaImparticion = new JLabel("Fecha imparticion: ");
			lblFechaImparticion.setLabelFor(getTxCrearCursoFechaImparticionInput());
			lblFechaImparticion.setHorizontalAlignment(SwingConstants.CENTER);
			lblFechaImparticion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblFechaImparticion;
	}

	private JTextField getTxCrearCursoFechaImparticionInput() {
		if (txCrearCursoFechaImparticionInput == null) {
			txCrearCursoFechaImparticionInput = new JTextField();
			txCrearCursoFechaImparticionInput.setColumns(10);
		}
		return txCrearCursoFechaImparticionInput;
	}

	private JLabel getLblCrearCursoPrecio() {
		if (lblCrearCursoPrecio == null) {
			lblCrearCursoPrecio = new JLabel("Precio inscripcion: ");
			lblCrearCursoPrecio.setLabelFor(getTxCrearCursoPrecioInscripcionInput());
			lblCrearCursoPrecio.setHorizontalAlignment(SwingConstants.CENTER);
			lblCrearCursoPrecio.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblCrearCursoPrecio;
	}

	private JTextField getTxCrearCursoPrecioInscripcionInput() {
		if (txCrearCursoPrecioInscripcionInput == null) {
			txCrearCursoPrecioInscripcionInput = new JTextField();
			txCrearCursoPrecioInscripcionInput.setColumns(10);
		}
		return txCrearCursoPrecioInscripcionInput;
	}
	private JLabel getLbInscripcionPagoColegiado() {
		if (lbInscripcionPagoColegiado == null) {
			lbInscripcionPagoColegiado = new JLabel("Puede pagar la inscripción al curso por tarjeta o por transferencia bancaria");
			lbInscripcionPagoColegiado.setHorizontalAlignment(SwingConstants.CENTER);
			lbInscripcionPagoColegiado.setFont(new Font("Arial Black", Font.BOLD, 11));
			lbInscripcionPagoColegiado.setFont(LookAndFeel.HEADING_1_FONT);
		}
		return lbInscripcionPagoColegiado;
	}
	private JPanel getPnModosPagoInscripcionColegiado() {
		if (pnModosPagoInscripcionColegiado == null) {
			pnModosPagoInscripcionColegiado = new JPanel();
			pnModosPagoInscripcionColegiado.setLayout(new GridLayout(0, 2, 0, 0));
			pnModosPagoInscripcionColegiado.add(getPnTarjetaDatosColegiado());
			pnModosPagoInscripcionColegiado.add(getBtnTransferenciaColegiado());
			pnModosPagoInscripcionColegiado.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
					"ELIGE UNA DE LAS DOS OPCIONES", TitledBorder.CENTER, TitledBorder.TOP, null, Color.GRAY));		
		}
		return pnModosPagoInscripcionColegiado;
	}
	private JButton getBtnTransferenciaColegiado() {
		if (btnTransferenciaColegiado == null) {
			btnTransferenciaColegiado = new JButton("Transferencia bancaria");
			btnTransferenciaColegiado.setBackground(new Color(152, 251, 152));
			btnTransferenciaColegiado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (comprobarCamposT() && comprobarColegiadoInscripcion()) {
						try {
							InscripcionColegiado.comprobarFecha(InscripcionColegiado.findFechaPreinscripcion
									(textFieldDNIColegiado.getText(),(int) comboBoxIdentificadorCursosAbiertos.getSelectedItem()));
							InscripcionColegiado.pagarCursoColegiado(textFieldDNIColegiado.getText(), (int) comboBoxIdentificadorCursosAbiertos.getSelectedItem() , "PENDIENTE", "TRANSFERENCIA");
							JOptionPane.showMessageDialog(null,
									"Ha seleccionado usted la opción de pagar por transferencia bancaria\n"
									+ "El pago se queda en estado pendiente",
									"Pago pendiente", JOptionPane.INFORMATION_MESSAGE);	
						} catch (BusinessException e1) {
							JOptionPane.showMessageDialog(null,
									"Lo sentimos, no puede hacerse cargo de pagar un curso en el que se ha preinscrito hace más de dos días\n"
									+ "Inténtelo de nuevo la próxima vez",
									"Inscripción no válida", JOptionPane.WARNING_MESSAGE);
							reiniciarInscripcionColegiadoPagar();
							mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
						}		
					}
				}
			});
			btnTransferenciaColegiado.setMnemonic('T');
			btnTransferenciaColegiado.setToolTipText("Pulsa para pagar por transferencia bancaria");
			btnTransferenciaColegiado.setBorder(new LineBorder(Color.BLACK));
		}
		return btnTransferenciaColegiado;
	}
	
	private boolean comprobarCamposT() {
		if (textFieldDNIColegiado.getText().isBlank() || textFieldDNIColegiado.getText().length() != 9 ) {
			JOptionPane.showMessageDialog(null, "Revise que no haya dejado ningún campo vacío y el formato del DNI es el correcto\n"
					+ "Sigue el ejemplo del campo correspondiente"
					, "Datos no válidos", JOptionPane.WARNING_MESSAGE);
			return false;
		} 
		return true;
	}
	
	private JPanel getPnTarjetaDatosColegiado() {
		if (pnTarjetaDatosColegiado == null) {
			pnTarjetaDatosColegiado = new JPanel();
			pnTarjetaDatosColegiado.setLayout(new GridLayout(0, 1, 0, 0));
			pnTarjetaDatosColegiado.add(getPnNumeroTarjetaDatosColegiado());
			pnTarjetaDatosColegiado.add(getPnNumeroFechaCaducidadDatosColegiado());
			pnTarjetaDatosColegiado.add(getBtnTarjetaCreditoColegiado());
			pnTarjetaDatosColegiado.setBorder(new LineBorder(Color.BLACK));
		}
		return pnTarjetaDatosColegiado;
	}
	private JButton getBtnTarjetaCreditoColegiado() {
		if (btnTarjetaCreditoColegiado == null) {
			btnTarjetaCreditoColegiado = new JButton("Validar tarjeta de crédito");
			btnTarjetaCreditoColegiado.setBackground(new Color(255, 69, 0));
			btnTarjetaCreditoColegiado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {					
					if (comprobarCampos() && comprobarColegiadoInscripcion() && comprobarFechaCaducidad()) {
							try {
								InscripcionColegiado.comprobarFecha(InscripcionColegiado.findFechaPreinscripcion
										(textFieldDNIColegiado.getText(), Integer.parseInt((String) comboBoxIdentificadorCursosAbiertos.getSelectedItem())));
								InscripcionColegiado.pagarCursoColegiado(textFieldDNIColegiado.getText(), 
										Integer.parseInt((String) comboBoxIdentificadorCursosAbiertos.getSelectedItem()) , "PAGADO", "TARJETA");
								JOptionPane.showMessageDialog(null,
										"Ha seleccionado usted la opción de pagar por tarjeta de crédito\n"
										+ "El pago se ha inscrito con éxito",
										"Pago verificado", JOptionPane.INFORMATION_MESSAGE);
							} catch (BusinessException e1) {
								JOptionPane.showMessageDialog(null,
										"Lo sentimos, no puede hacerse cargo de pagar un curso en el que se ha preinscrito hace más de dos días\n"
										+ "Inténtelo de nuevo la próxima vez",
										"Inscripción no válida", JOptionPane.WARNING_MESSAGE);
								reiniciarInscripcionColegiadoPagar();
								mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
							} 
					} 
				}
			});
			btnTarjetaCreditoColegiado.setToolTipText("Pulsa para pagar con tarjeta");
			btnTarjetaCreditoColegiado.setMnemonic('V');
			
		}
		return btnTarjetaCreditoColegiado;
	}
	
	private boolean comprobarColegiadoInscripcion() {
		// comprobamos que el dni asociado sea un colegiado
		try {
			Colegiado.findColegiadoPorDni(textFieldDNIColegiado.getText());
		} catch (BusinessException e) {
			JOptionPane.showMessageDialog(null,
					"Lo sentimos, no puede hacerse cargo de pagar un curso, no es usted ni colegiado ni precolegiado, formalice su solicitud primero\n"
					+ "Inténtelo de nuevo la próxima vez",
					"Dni inválido", JOptionPane.WARNING_MESSAGE);
			reiniciarInscripcionColegiadoPagar();
			mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
			return false;
		}
		// comprobamos que se ha inscrito en el curso
		try {
			InscripcionColegiado.findFechaPreinscripcion(textFieldDNIColegiado.getText(), (int) comboBoxIdentificadorCursosAbiertos.getSelectedItem());
			return true;
		} catch (BusinessException e) {
			JOptionPane.showMessageDialog(null,
					"Lo sentimos, no puede hacerse cargo de pagar un curso, en el que no se ha preinscrito\n"
					+ "Inténtelo de nuevo la próxima vez",
					"Preinscripción no realizada", JOptionPane.WARNING_MESSAGE);
			reiniciarInscripcionColegiadoPagar();
			mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
			return false;
		}
	}


	@SuppressWarnings("deprecation")
	private boolean comprobarFechaCaducidad() {
		Date date = new Date();
		if (this.getCalendarioFechaCaducidad().getCalendar().get(java.util.Calendar.YEAR) < date.getYear() + 1900 || 
			this.getCalendarioFechaCaducidad().getCalendar().get(java.util.Calendar.YEAR) == date.getYear() + 1900  && this.getCalendarioFechaCaducidad().getCalendar().get(java.util.Calendar.MONTH) + 1 < date.getMonth() + 1 ||
			this.getCalendarioFechaCaducidad().getCalendar().get(java.util.Calendar.YEAR) == date.getYear() + 1900  && this.getCalendarioFechaCaducidad().getCalendar().get(java.util.Calendar.MONTH) + 1 == date.getMonth() + 1
			&& this.getCalendarioFechaCaducidad().getCalendar().get(java.util.Calendar.DATE) <= date.getDay() + 19) {
				JOptionPane.showMessageDialog(null, "La fecha de caducidad debe ser posterior a la actual"
						, "Fecha de caducidad incorrecta", JOptionPane.ERROR_MESSAGE);
			this.calendarioFechaCaducidad.setDate(new Date());
			return false;
		}
		return true;
				
	}

	private boolean comprobarCampos() {
		try {
			@SuppressWarnings("unused")
			int tarjeta = Integer.parseInt(textFieldNumeroTarjetaColegiado.getText());
			if (textFieldNumeroTarjetaColegiado.getText().isBlank() || textFieldNumeroTarjetaColegiado.getText().length() != 5
				|| textFieldDNIColegiado.getText().isBlank() || textFieldDNIColegiado.getText().length() != 9 ) {
				JOptionPane.showMessageDialog(null, "Revise que no haya dejado ningún campo vacío y los formatos de los datos que tiene que introducir\n"
						+ "Sigue el ejemplo del campo correspondiente"
						, "Datos no válidos", JOptionPane.WARNING_MESSAGE);
				return false;
			} 
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Revise que no haya dejado ningún campo vacío y los formatos de los datos que tiene que introducir\n"
					+ "Sigue el ejemplo del campo correspondiente"
					, "Datos no válidos", JOptionPane.ERROR_MESSAGE);
			textFieldNumeroTarjetaColegiado.setText(null);
			return false;
		}
		return true;
	}

	private JPanel getPnNumeroTarjetaDatosColegiado() {
		if (pnNumeroTarjetaDatosColegiado == null) {
			pnNumeroTarjetaDatosColegiado = new JPanel();
			pnNumeroTarjetaDatosColegiado.setLayout(new GridLayout(0, 2, 0, 0));
			pnNumeroTarjetaDatosColegiado.add(getLblNumeroTarjetaDatosColegiado());
			pnNumeroTarjetaDatosColegiado.add(getTextFieldNumeroTarjetaColegiado());
			pnNumeroTarjetaDatosColegiado.setBorder(new LineBorder(Color.BLACK));
		}
		return pnNumeroTarjetaDatosColegiado;
	}
	private JPanel getPnNumeroFechaCaducidadDatosColegiado() {
		if (pnNumeroFechaCaducidadDatosColegiado == null) {
			pnNumeroFechaCaducidadDatosColegiado = new JPanel();
			pnNumeroFechaCaducidadDatosColegiado.setLayout(new GridLayout(0, 2, 0, 0));
			pnNumeroFechaCaducidadDatosColegiado.add(getLblFechaCaducidadDatosColegiado());
			pnNumeroFechaCaducidadDatosColegiado.add(getCalendarioFechaCaducidad());
			pnNumeroFechaCaducidadDatosColegiado.setBorder(new LineBorder(Color.BLACK));
		}
		return pnNumeroFechaCaducidadDatosColegiado;
	}
	private JLabel getLblNumeroTarjetaDatosColegiado() {
		if (lblNumeroTarjetaDatosColegiado == null) {
			lblNumeroTarjetaDatosColegiado = new JLabel("Número de tarjeta:");
			lblNumeroTarjetaDatosColegiado.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNumeroTarjetaDatosColegiado.setDisplayedMnemonic('N');
			lblNumeroTarjetaDatosColegiado.setLabelFor(getTextFieldNumeroTarjetaColegiado());
			lblNumeroTarjetaDatosColegiado.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNumeroTarjetaDatosColegiado;
	}
	private JTextField getTextFieldNumeroTarjetaColegiado() {
		if (textFieldNumeroTarjetaColegiado == null) {
			textFieldNumeroTarjetaColegiado = new JTextField();
			textFieldNumeroTarjetaColegiado.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldNumeroTarjetaColegiado.setText(null);
				}
			});
			textFieldNumeroTarjetaColegiado.setText("Ej: 76567 [5 numeros]");
			textFieldNumeroTarjetaColegiado.setToolTipText("Introduzca el número de su tarjeta bancaria");
			textFieldNumeroTarjetaColegiado.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldNumeroTarjetaColegiado.setColumns(10);
		}
		return textFieldNumeroTarjetaColegiado;
	}
	private JLabel getLblFechaCaducidadDatosColegiado() {
		if (lblFechaCaducidadDatosColegiado == null) {
			lblFechaCaducidadDatosColegiado = new JLabel("Fecha de caducidad:");
			lblFechaCaducidadDatosColegiado.setDisplayedMnemonic('F');
			lblFechaCaducidadDatosColegiado.setLabelFor(getCalendarioFechaCaducidad());
			lblFechaCaducidadDatosColegiado.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblFechaCaducidadDatosColegiado.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblFechaCaducidadDatosColegiado;
	}
	private JCalendar getCalendarioFechaCaducidad() {
		if (calendarioFechaCaducidad==null) {
			calendarioFechaCaducidad = new JCalendar();
			calendarioFechaCaducidad.getDayChooser().getDayPanel().setToolTipText("Seleccione la fecha de caducidad de su tarjeta");
			calendarioFechaCaducidad.setBorder(new LineBorder(Color.GRAY));
		}
		return calendarioFechaCaducidad;
	}
	private JPanel getPnDatosInicialesPagarInscripcion() {
		if (pnDatosInicialesPagarInscripcion == null) {
			pnDatosInicialesPagarInscripcion = new JPanel();
			pnDatosInicialesPagarInscripcion.setLayout(new GridLayout(0, 2, 0, 0));
			pnDatosInicialesPagarInscripcion.add(getPnDatosInicialesIdColegiado());
			pnDatosInicialesPagarInscripcion.add(getPnDatosInicialesIdCurso());
			pnDatosInicialesPagarInscripcion.setBorder(new LineBorder(Color.BLACK));
		}
		return pnDatosInicialesPagarInscripcion;
	}
	private JLabel getLbInscripcionPagoAviso_1() {
		if (lbInscripcionPagoAviso == null) {
			lbInscripcionPagoAviso = new JLabel("No puede pagar un curso el cual no se ha preinscrito, y en el caso de que se haya preinscrito contará con dos días a partir de la fecha de preinscripcion");
			lbInscripcionPagoAviso.setHorizontalAlignment(SwingConstants.CENTER);
			lbInscripcionPagoAviso.setForeground(Color.RED);
			lbInscripcionPagoAviso.setFont(new Font("Arial", Font.BOLD, 14));
		}
		return lbInscripcionPagoAviso;
	}
	private JPanel getPnDatosInicialesIdColegiado() {
		if (pnDatosInicialesIdColegiado == null) {
			pnDatosInicialesIdColegiado = new JPanel();
			pnDatosInicialesIdColegiado.setLayout(new GridLayout(1, 0, 0, 0));
			pnDatosInicialesIdColegiado.add(getLblNewLabelDNIColegiado());
			pnDatosInicialesIdColegiado.add(getTextFieldDNIColegiado());
		}
		return pnDatosInicialesIdColegiado;
	}
	private JPanel getPnDatosInicialesIdCurso() {
		if (pnDatosInicialesIdCurso == null) {
			pnDatosInicialesIdCurso = new JPanel();
			pnDatosInicialesIdCurso.setLayout(new GridLayout(1, 0, 0, 0));
			pnDatosInicialesIdCurso.add(getLblNewLabelIdentificadorCurso());
			pnDatosInicialesIdCurso.add(getComboBoxIdentificadorCursosAbiertos());
		}
		return pnDatosInicialesIdCurso;
	}
	private JLabel getLblNewLabelDNIColegiado() {
		if (lblNewLabelDNIColegiado == null) {
			lblNewLabelDNIColegiado = new JLabel("DNI:");
			lblNewLabelDNIColegiado.setLabelFor(getTextFieldDNIColegiado());
			lblNewLabelDNIColegiado.setDisplayedMnemonic('D');
			lblNewLabelDNIColegiado.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabelDNIColegiado.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNewLabelDNIColegiado;
	}
	private JTextField getTextFieldDNIColegiado() {
		if (textFieldDNIColegiado == null) {
			textFieldDNIColegiado = new JTextField();
			textFieldDNIColegiado.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					textFieldDNIColegiado.setText(null);
				}
			});
			textFieldDNIColegiado.setText("Ej: 71778880C");
			textFieldDNIColegiado.setToolTipText("Introduce su DNI");
			textFieldDNIColegiado.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldDNIColegiado.setColumns(10);
		}
		return textFieldDNIColegiado;
	}
	private JLabel getLblRellenarDatosInscripcionCurso() {
		if (lblRellenarDatosInscripcionCurso == null) {
			lblRellenarDatosInscripcionCurso = new JLabel("Debe de cumplimentar su número de colegiado y el identificador del curso independientemente del método de pago que elija");
			lblRellenarDatosInscripcionCurso.setHorizontalAlignment(SwingConstants.CENTER);
			lblRellenarDatosInscripcionCurso.setFont(new Font("Arial Black", Font.PLAIN, 14));
		}
		return lblRellenarDatosInscripcionCurso;
	}
	private JLabel getLblNewLabelIdentificadorCurso() {
		if (lblNewLabelIdentificadorCurso == null) {
			lblNewLabelIdentificadorCurso = new JLabel("Identificador del curso:");
			lblNewLabelIdentificadorCurso.setLabelFor(getComboBoxIdentificadorCursosAbiertos());
			lblNewLabelIdentificadorCurso.setDisplayedMnemonic('I');
			lblNewLabelIdentificadorCurso.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelIdentificadorCurso.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lblNewLabelIdentificadorCurso;
	}
	
	private JComboBox<Integer> getComboBoxIdentificadorCursosAbiertos() {
		if (comboBoxIdentificadorCursosAbiertos == null) {
			comboBoxIdentificadorCursosAbiertos = new JComboBox<Integer>();
			comboBoxIdentificadorCursosAbiertos.setToolTipText("Selecciona el curso que desea pagar");
				Integer[] elementsComboBox;
				List<CursoDto> lista;
				try {
					lista = Curso.listarCursosAbiertos();
					elementsComboBox = new Integer[lista.size()];
					for (int i=0; i < lista.size(); i++) {
						elementsComboBox[i] = lista.get(i).codigoCurso;
					}
					comboBoxIdentificadorCursosAbiertos = new JComboBox<Integer>(new DefaultComboBoxModel<Integer>(elementsComboBox));
				} catch (BusinessException e) {
					e.printStackTrace();
				}		
		}
		return comboBoxIdentificadorCursosAbiertos;
	}
	
	private JPanel getPnPagarInscripcionColegiadoSur() {
		if (pnPagarInscripcionColegiadoSur == null) {
			pnPagarInscripcionColegiadoSur = new JPanel();
			pnPagarInscripcionColegiadoSur.add(getBtnInicioInscripcion());
		}
		return pnPagarInscripcionColegiadoSur;
	}
	private DefaultButton getBtnInicioInscripcion() {
		if (btnInicioInscripcion == null) {
			btnInicioInscripcion = new DefaultButton("Vovler a Inicio", "ventana", "VolverAInicio", 'v', ButtonColor.CANCEL);
			btnInicioInscripcion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (confirmarVolverPrinicipio()) {
						reiniciarInscripcionColegiadoPagar();
						mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
					}
				}
			});
			btnInicioInscripcion.setToolTipText("Pulse para volver a la pantalla principal");
			btnInicioInscripcion.setMnemonic('R');
			btnInicioInscripcion.setBackground(Color.RED);
		}
		return btnInicioInscripcion;
	}

	private void reiniciarInscripcionColegiadoPagar() {
		textFieldDNIColegiado.setText("Ej: 71778880C");
		textFieldNumeroTarjetaColegiado.setText("Ej: 76567 [5 numeros]");
		this.calendarioFechaCaducidad.setDate(new Date());
	}
}
