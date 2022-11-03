package ui.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import com.toedter.calendar.JCalendar;

import business.BusinessException;
import business.InscripcionColegiado.InscripcionColegiado;
import business.colegiado.Colegiado;
import business.curso.Curso;
import business.inscripcion.InscripcionCursoFormativo;
import business.util.DateUtils;
import business.util.GeneradorNumeroColegiado;
import persistence.Colegiado_Inscripcion.Colegiado_Inscripcion;
import persistence.InscripcionColegiado.InscripcionColegiadoDto;
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
import ui.components.placeholder.TextPlaceHolderCustom;
import ui.model.ColegiadoModel;
import ui.model.CursoModel;
import ui.model.InscripcionColegiadoModel;
import ui.util.TimeFormatter;
import java.awt.Insets;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import net.miginfocom.swing.MigLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -8203812316779660921L;

	// Nombres de los paneles contenidos en mainPanel
	private static final String HOME_PANEL_NAME = "homePanel";
	private static final String SOLICITUD_COLEGIADO_PANEL_NAME = "solicitudColegiadoPanel";
	// private static final String LOGIN_COLEGIADO_PANEL = "loginColegiadoPanel";
	private static final String APERTURA_INSCRIPCIONES_PANEL_NAME = "aperturaCursoPanel";
	private static final String INSCRIPCION_CURSO_PANEL_NAME = "inscripicionCursoPanel";
	private static final String ADD_CURSO_PANEL_NAME = "addCursoPanel";
	private static final String PAGAR_INSCRIPCION_CURSO_PANEL_NAME = "pagarInscripcionColegiado";
	private static final String LISTADO_CURSOS_PANEL_NAME = "listadoCursosPanel";
	private static final String LISTADO_INSCRIPCIONES_PANEL_NAME = "listadoInscripcionesPanel";
	private static final String CONSULTAR_TITULACION_SOLICITANTE_PANEL_NAME = "consultarTitulacionSolicitantePanel";
	private static final String INSCRIPCION_CURSO_TRANSFERENCIAS = "incripcionColegiadoTransferencias";
	private static final String INSCRIPCION_CURSO_TRANSFERENCIAS_PROCESADAS = "inscripcionColegiadoTransferenciasProcesadas";

	private static final int ALL_MINUS_ID = 1;

	private static final int ALL_CURSO = 0;


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
	private JLabel lblNewLabelNumeroCuenta;
	private JTextField textFieldNumeroCuenta;
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
	private JButton btMostrarCursos;
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
	private JPanel pnPagarInscripcion;
	private JPanel pnListadoInscripciones;
	private JPanel pnConsultarTitulacionSolicitante;
	private JPanel pnCrearCurso;
	private JPanel pnCrearCursoTitulo;
	private JLabel lblCrearCurso;
	private JPanel pnCrearCursoCampos;
	private JPanel pnCrearCursoButtons;
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
	private JScrollPane spnCursos;
	private JLabel lbAlertaListadoInscripciones;

	private JLabel lbTotalIngresos;
	private JLabel lbTotalIngresosText;

	private JList<Colegiado_Inscripcion> lInscripciones;
	private JList<CursoDto> lCursos;

	private JPanel pnConsultarTitulacionNorth;
	private JPanel pnConsultarTitulacionCenter;
	private JPanel pnConsultarTitulacionSouth;
	private JLabel lbConsultarTitulacionTitle;
	private JScrollPane spListadoAltaSolicitudesColegiado;
	private JPanel pnConsultarTitulacionSouthButtons;
	private JButton btConsultarSolicitudColegiadoVolver;
	private JPanel pnConsultarColegiadoDatosColegiadoSeleccionado;
	private JLabel lbColegiadoSeleccionadoSolicitudRespuesta;
	private JPanel pnListadoCursosSouth;
	private JPanel pnListadoCursosSouthButtons;
	private DefaultButton btListadoCursosVolver;
	private JScrollPane spListadoCursosCenter;
	private JTable tbListadoTodosCursos;
	private JTable tbListadoSolicitudesColegiado;
	private JPanel pnListadoAltaSolicitudesColegiadoActualizarLista;
	private DefaultButton btActualizarListaSolicitudesColegiado;

	private JLabel lbInscripcionPagoColegiado;
	private JPanel pnModosPagoInscripcionColegiado;
	private JButton btnTransferenciaColegiado;
	private JPanel pnTarjetaDatosColegiado;
	private JButton btnTarjetaCreditoColegiado;
	private JPanel pnNumeroTarjetaDatosColegiado;
	private JPanel pnNumeroFechaCaducidadDatosColegiado;
	private JLabel lblNumeroTarjetaDatosColegiado;
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
	private JComboBox<String> comboBoxIdentificadorCursosAbiertos;
	private JPanel pnPagarInscripcionColegiadoSur;
	private DefaultButton btnInicioInscripcion;
	private JPanel pnPagarInscripcionColegiadoNorte;

	private JPanel pnPagarInscripcionColegiado;

	private JCalendar calendarioFechaAperturaInscripcionCurso;
	private JPanel pnNumeroTarjetaDatosColegiadoText;
	private JTextField textFieldNumeroTarjetaColegiado;
	private JPanel pnNumeroTarjetaValidarTarjeta;
	private JPanel pnPagoTranferencia;
	private JPanel pnDatosColPoblacionText;
	private JPanel pnDatosColTelefonoText;
	private JPanel pnDatosColTitulacionText;
	private JPanel pnDatosColCentoText;
	private JPanel pnDatosColAnnioText;
	private JPanel pnTransferencias;
	private JPanel pnTransferenciasCentro;
	private JPanel pnTransferenciasNorte;
	private JLabel lblObtenerListaDeMov;
	private DefaultButton btHomeSecretariaTransferencias;
	private JPanel panelListaMovimientos;
	private JPanel panelMuestraCursos;
	private JPanel panelMuestraTransferencias;
	private JPanel panelMuestraCursosNorte;
	private JPanel paneMuestraCursosCentro;
	private JPanel panelMuestraCursosSur;
	private JLabel lblSeleccionaCursoTransf;
	private JButton btnMovimientosBancarios;
	private JPanel panelMuestraTransferenciasNorte;
	private JPanel panelMuestraTransferenciasCentro;
	private JPanel panelMuestraTransferenciasSur;
	private JLabel lblRegistrosBancarios;
	private JButton btnProcesarPagos;
	private JScrollPane scrollPaneCursos;
	private JTable tbCourses;
	private JScrollPane scrollPaneTransferencias;
	private JTable tbTransferencias;
	private JPanel pnTransferenciasProcesadas;
	private JPanel pnTransferenciasProcesadasNorte;
	private JPanel pnTransferenciasProcesadasCentro;
	private JPanel pnTransferenciasProcesadasSur;
	private JLabel lblNewLabelProcesarTransferencias;
	private JButton btnProcesarTransferencias;
	private JScrollPane scrollPaneProcesar;
	private JTable tbProcesarTransferencias;
	private TableModel tableModelC;
	private TableModel tableModel;
	private TableModel tableModelP;

	// CURSO SOBRE EL QUE QUEREMOS MIRAR LAS CUENTAS DEL BANCO
	private CursoDto cursoSeleccionado;


	public MainWindow() {
		setTitle("COIIPA : Gestión de servicios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setFont(LookAndFeel.PRIMARY_FONT);

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
		mainPanel.add(getPnCrearCurso(), ADD_CURSO_PANEL_NAME);
		mainPanel.add(getPnTransferencias(), INSCRIPCION_CURSO_TRANSFERENCIAS);
		mainPanel.add(getPnTransferenciasProcesadas(), INSCRIPCION_CURSO_TRANSFERENCIAS_PROCESADAS);

		// Centrar la ventana
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		inicializarCampos();
	}

	private void inicializarCampos() {
		textFieldNombre.grabFocus();
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

//			try {
//				TableModel tableModel = new CursoModel(Curso.listarCursosPlanificados()).getCursoModel(ALL_CURSO);
//
//				tbCoursesList.setModel(tableModel);
//			} catch (BusinessException e) {
//				showMessage(e, MessageType.ERROR);
//				e.printStackTrace();
//			}

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

			// Calendario para seleccionar la fecha de apertura del curso seleccionado
			// TODO:
			// pnCursoSeleccionadoFechaApertura.add(getCalAbrirInscripcionFechaApertura());
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

	private JCalendar getCalAbrirInscripcionFechaApertura() {
		if (calendarioFechaAperturaInscripcionCurso == null) {
			calendarioFechaAperturaInscripcionCurso = new JCalendar();
			calendarioFechaAperturaInscripcionCurso.getDayChooser().getDayPanel()
					.setToolTipText("Seleccione la fecha de apertura para las inscripciones del curso seleccionado.");

			calendarioFechaAperturaInscripcionCurso.setBorder(new LineBorder(Color.GRAY));
		}
		return calendarioFechaAperturaInscripcionCurso;
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

			getBtCancelarAperturaCurso().setText("cancelar");

			btAbrirCurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					String plazas = spNumeroPlazasCursoSeleccionado.getValue().toString();
					LocalDate fechaApertura = DateUtils
							.convertStringIntoLocalDate(fTxFechaInicioInscripcionesCursoSeleccionado.getText());

					LocalDate fechaCierre = DateUtils
							.convertStringIntoLocalDate(fTxFechaCierreInscripcionesCursoSeleccionado.getText());

					try {

						CursoDto selectedCourse = Curso.getSelectedCourse();

						if (selectedCourse == null) {
							throw new BusinessException("Por favor, seleccione un curso.");
						}
						int input = JOptionPane.showConfirmDialog(null,
								"<html><p>Â¿Confirma que desea abrir las inscripciones para el curso <b>"
										+ selectedCourse.titulo + "</b> ?</p></html>");

						if (input == JOptionPane.OK_OPTION) {
							// Si el curso estÃ¡ abierto, mostrar mensaje de error

							CursoDto newCurso = new CursoDto();
							newCurso.codigoCurso = selectedCourse.codigoCurso;
							newCurso.titulo = selectedCourse.titulo;
							newCurso.fechaInicio = selectedCourse.fechaInicio;
							newCurso.fechaApertura = fechaApertura;
							newCurso.fechaCierre = fechaCierre;
							newCurso.precio = selectedCourse.precio;
							newCurso.plazasDisponibles = Integer.parseInt(plazas);

							InscripcionCursoFormativo.abrirCursoFormacion(newCurso);

							((DefaultMessage) pnListCoursesSouthMessages).setMessageColor(MessageType.SUCCESS);
							((DefaultMessage) pnListCoursesSouthMessages)
									.setMessage("Se ha abierto la inscripciÃ³n para el curso seleccionado");

							refreshScheduledCoursesList();

							btAbrirCurso.setEnabled(false);
							getBtCancelarAperturaCurso().setText("volver");
						}

					} catch (BusinessException | PersistenceException | SQLException e1) {
						((DefaultMessage) pnListCoursesSouthMessages).setMessageColor(MessageType.ERROR);
						((DefaultMessage) pnListCoursesSouthMessages)
								.setMessage("<html>" + e1.getMessage() + "</html>");
						btAbrirCurso.setEnabled(false);
						getBtCancelarAperturaCurso().setText("cancelar");

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
			btRefreshOpenCoursesList = new DefaultButton("Actualizar lista", "ventana", "Actualizar lista", 'r',
					ButtonColor.NORMAL);
			btRefreshOpenCoursesList.setPreferredSize(new Dimension(250, 80));
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
		TableModel tableModel = new CursoModel(Curso.listarCursosPlanificados()).getCursoModel(ALL_CURSO);
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
					"Por favor, revise que no haya introducido un DNI que no es suyo, este DNI ya ha sido registrado",
					"DNI invÃ¡lido", JOptionPane.INFORMATION_MESSAGE);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this,
					"Por favor, revise que no deje ningÃºn campo vacÃ­o y se ha introducido correctamente cada dato (longitudes correspondientes, formato de datos, ...)\n"
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
		dto.numeroCuenta = getTextFieldNumeroCuenta().getText();
		dto.titulacion = getTextFieldTitulacion().getText();
		try {
			dto.telefono = Integer.parseInt(getTextFieldTelefono().getText());
			dto.annio = Integer.parseInt(getTextFieldAno().getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,
					"Por favor, revise que no se haya introducido ninguna cadena en alguno de los campos numÃ©ricos o haya dejado en blanco alguno de los campos numéricos",
					"Formato numÃ©rico incorrecto", JOptionPane.INFORMATION_MESSAGE);
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
		this.getTextFieldNumeroCuenta().setText(null);
		this.getTextFieldCentroColegiado().setText(null);
		this.getTextFieldPoblacion().setText(null);
		this.getTextFieldTelefono().setText(null);
		this.getTextFieldTitulacion().setText(null);

		this.getTextFieldNombre().grabFocus();
	}

	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new DefaultButton("Vovler a Inicio", "ventana", "VolverAInicio", 'v', ButtonColor.CANCEL);
			btnAtras.setMnemonic('V');
			btnAtras.setText("Volver a Inicio");
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (confirmarVolverPrinicipio()) {
						reiniciarSolicitudRegistro();
						mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
					}
				}
			});

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
			pnTarjetaTextField.add(getLblNewLabelNumeroCuenta());
			pnTarjetaTextField.add(getTextFieldNumeroCuenta());
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

	private JLabel getLblNewLabelNumeroCuenta() {
		if (lblNewLabelNumeroCuenta == null) {
			lblNewLabelNumeroCuenta = new JLabel("Número de cuenta bancaria:");
			lblNewLabelNumeroCuenta.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelNumeroCuenta.setFont(LookAndFeel.PRIMARY_FONT);
			lblNewLabelNumeroCuenta.setDisplayedMnemonic('N');
			lblNewLabelNumeroCuenta.setLabelFor(getTextFieldNumeroCuenta());
		}
		return lblNewLabelNumeroCuenta;
	}

	private JTextField getTextFieldNumeroCuenta() {
		if (textFieldNumeroCuenta == null) {
			textFieldNumeroCuenta = new JTextField();
			TextPlaceHolderCustom.setPlaceholder("ES6612344321", textFieldNumeroCuenta);
			textFieldNumeroCuenta.setToolTipText("Registre su numero de cuenta bancaria");
			textFieldNumeroCuenta.setHorizontalAlignment(SwingConstants.LEFT);
			textFieldNumeroCuenta.setColumns(10);
		}
		return textFieldNumeroCuenta;
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
			lblNewLabelN.setFont(LookAndFeel.PRIMARY_FONT);
			lblNewLabelN.setDisplayedMnemonic('M');
			lblNewLabelN.setLabelFor(getTextFieldNombre());
		}
		return lblNewLabelN;
	}

	private JTextField getTextFieldNombre() {
		if (textFieldNombre == null) {
			textFieldNombre = new JTextField();
			textFieldNombre.setToolTipText("Inserte el nombre");
			textFieldNombre.setHorizontalAlignment(SwingConstants.LEFT);
			textFieldNombre.setColumns(10);
			TextPlaceHolderCustom.setPlaceholder("Miguel", textFieldNombre);
		}
		return textFieldNombre;
	}

	private JLabel getLblNewLabelA() {
		if (lblNewLabelA == null) {
			lblNewLabelA = new JLabel("Apellidos:");
			lblNewLabelA.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelA.setFont(LookAndFeel.PRIMARY_FONT);
			lblNewLabelA.setDisplayedMnemonic('A');
			lblNewLabelA.setLabelFor(getTextFieldApellidos());
		}
		return lblNewLabelA;
	}

	private JTextField getTextFieldApellidos() {
		if (textFieldApellidos == null) {
			textFieldApellidos = new JTextField();
			textFieldApellidos.setToolTipText("Inserte los apellidos");
			TextPlaceHolderCustom.setPlaceholder("Gonzalez Navarro", textFieldApellidos);
			textFieldApellidos.setHorizontalAlignment(SwingConstants.LEFT);
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
			lblNewLabelD.setFont(LookAndFeel.PRIMARY_FONT);
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
			TextPlaceHolderCustom.setPlaceholder("71778880C", textFieldDni);
			textFieldDni.setHorizontalAlignment(SwingConstants.LEFT);
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
			pnDatosPoblacionColegiado.add(getPnDatosColPoblacionText());
		}
		return pnDatosPoblacionColegiado;
	}

	private JLabel getLblNewLabelPob() {
		if (lblNewLabelPob == null) {
			lblNewLabelPob = new JLabel("Población:");
			lblNewLabelPob.setToolTipText("Introduzca su poblaciÃ³n");
			lblNewLabelPob.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelPob.setFont(LookAndFeel.PRIMARY_FONT);
			lblNewLabelPob.setDisplayedMnemonic('P');
			lblNewLabelPob.setLabelFor(getTextFieldPoblacion());
		}
		return lblNewLabelPob;
	}

	private JTextField getTextFieldPoblacion() {
		if (textFieldPoblacion == null) {
			textFieldPoblacion = new JTextField();
			textFieldPoblacion.setMaximumSize(new Dimension(2147483647, 50));
			textFieldPoblacion.setBounds(new Rectangle(0, 0, 0, 50));
			textFieldPoblacion.setPreferredSize(new Dimension(7, 50));
			TextPlaceHolderCustom.setPlaceholder("Moreda", textFieldPoblacion);
			textFieldPoblacion.setToolTipText("Introduzca su población");
			textFieldPoblacion.setHorizontalAlignment(SwingConstants.LEFT);
			textFieldPoblacion.setColumns(10);
		}
		return textFieldPoblacion;
	}

	private JPanel getPanelDatosTelefonoColegiado() {
		if (pnDatosTelefonoColegiado == null) {
			pnDatosTelefonoColegiado = new JPanel();
			pnDatosTelefonoColegiado.setLayout(new GridLayout(0, 2, 0, 0));
			pnDatosTelefonoColegiado.add(getLblNewLabelTelefonoColegiado());
			pnDatosTelefonoColegiado.add(getPnDatosColTelefonoText());
		}
		return pnDatosTelefonoColegiado;
	}

	private JLabel getLblNewLabelTelefonoColegiado() {
		if (lblNewLabelTelefono == null) {
			lblNewLabelTelefono = new JLabel("Teléfono de contacto:");
			lblNewLabelTelefono.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelTelefono.setFont(LookAndFeel.PRIMARY_FONT);
			lblNewLabelTelefono.setDisplayedMnemonic('T');
			lblNewLabelTelefono.setLabelFor(getTextFieldTelefono());
		}
		return lblNewLabelTelefono;
	}

	private JTextField getTextFieldTelefono() {
		if (textFieldTelefono == null) {
			textFieldTelefono = new JTextField();
			textFieldTelefono.setToolTipText("Introduzca su teléfono");
			TextPlaceHolderCustom.setPlaceholder("681676654", textFieldTelefono);
			textFieldTelefono.setHorizontalAlignment(SwingConstants.LEFT);
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
			pnDatosCentroColegiado.add(getPnDatosColCentoText());
		}
		return pnDatosCentroColegiado;
	}

	private JPanel getPnDatosAnoColegiado() {
		if (pnDatosAnoColegiado == null) {
			pnDatosAnoColegiado = new JPanel();
			pnDatosAnoColegiado.setLayout(new GridLayout(0, 2, 0, 0));
			pnDatosAnoColegiado.add(getLblNewLabelDatAno());
			pnDatosAnoColegiado.add(getPnDatosColAnnioText());
		}
		return pnDatosAnoColegiado;
	}

	private JTextField getTextFieldAno() {
		if (textFieldAno == null) {
			textFieldAno = new JTextField();
			textFieldAno.setToolTipText("Escriba el ano");
			TextPlaceHolderCustom.setPlaceholder("2022", textFieldAno);
			textFieldAno.setHorizontalAlignment(SwingConstants.LEFT);
			textFieldAno.setColumns(10);
		}
		return textFieldAno;
	}

	private JLabel getLblNewLabelDatAno() {
		if (lblNewLabelDatAno == null) {
			lblNewLabelDatAno = new JLabel("Ano:");
			lblNewLabelDatAno.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelDatAno.setFont(LookAndFeel.PRIMARY_FONT);
			lblNewLabelDatAno.setDisplayedMnemonic('O');
			lblNewLabelDatAno.setLabelFor(getTextFieldAno());
		}
		return lblNewLabelDatAno;
	}

	private JTextField getTextFieldCentroColegiado() {
		if (textFieldCentro == null) {
			textFieldCentro = new JTextField();
			textFieldCentro.setToolTipText("Escriba su centro educativo");
			TextPlaceHolderCustom.setPlaceholder("Escuela de Ingenieria Informatica", textFieldCentro);
			textFieldCentro.setHorizontalAlignment(SwingConstants.LEFT);
			textFieldCentro.setColumns(10);
		}
		return textFieldCentro;
	}

	private JLabel getLblNewLabelDatCentro() {
		if (lblNewLabelDatCentro == null) {
			lblNewLabelDatCentro = new JLabel("Centro:");
			lblNewLabelDatCentro.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelDatCentro.setFont(LookAndFeel.PRIMARY_FONT);
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
		}
		return pnDatosTituloColegiadoLabel;
	}

	private JPanel getPnDatosTituloColegiadoCheck() {
		if (pnDatosTituloColegiadoCheck == null) {
			pnDatosTituloColegiadoCheck = new JPanel();
			pnDatosTituloColegiadoCheck.setLayout(new GridLayout(0, 1, 0, 0));
			pnDatosTituloColegiadoCheck.add(getPnDatosColTitulacionText());
		}
		return pnDatosTituloColegiadoCheck;
	}

	private JTextField getTextFieldTitulacion() {
		if (textFieldTitulo == null) {
			textFieldTitulo = new JTextField();
			textFieldTitulo.setToolTipText("Teclee la titulación");
			TextPlaceHolderCustom.setPlaceholder("Licenciado en Informática", textFieldTitulo);
			textFieldTitulo.setHorizontalAlignment(SwingConstants.LEFT);
			textFieldTitulo.setColumns(10);
		}
		return textFieldTitulo;
	}

	private JLabel getLblTitulacinSegunSus() {
		if (lblTitulacinSegunSus == null) {
			lblTitulacinSegunSus = new JLabel("Titulación segun sus estudios:");
			lblTitulacinSegunSus.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitulacinSegunSus.setFont(LookAndFeel.PRIMARY_FONT);
			lblTitulacinSegunSus.setDisplayedMnemonic('I');
			lblTitulacinSegunSus.setLabelFor(getTextFieldTitulacion());
		}
		return lblTitulacinSegunSus;
	}

	private JPanel getPnInscripcion() {
		if (pnInscripcion == null) {
			pnInscripcion = new JPanel();
			pnInscripcion.setVisible(false);
			pnInscripcion.setFont(LookAndFeel.PRIMARY_FONT);
			pnInscripcion.setLayout(null);
			pnInscripcion.add(getSpCursos());
			pnInscripcion.add(getLbInscripcionSeleccionarCursos());
			pnInscripcion.add(getBtnInscribete());
			pnInscripcion.add(getLbAlerta());
			pnInscripcion.add(getLbConfirmacionInscripcion());
			pnInscripcion.add(getBtMostrarCursos());
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
					lbConfirmacionInscripcion.setVisible(false);
					lbAlerta.setVisible(false);
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
			lbAlerta.setFont(LookAndFeel.PRIMARY_FONT);

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

			lbConfirmacionInscripcion.setBounds(18, 558, 719, 61);

		}
		return lbConfirmacionInscripcion;
	}

	private JButton getBtMostrarCursos() {
		if (btMostrarCursos == null) {
			btMostrarCursos = new DefaultButton("Mostrar Cursos Abiertos", "ventana", "MostrarCursosAbiertos", 'm',
					ButtonColor.NORMAL);
			btMostrarCursos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						ColegiadoDto c = InscripcionColegiado.InicioSesion(txCursoDNI.getText());

						if (c == null || txCursoDNI.getText().isEmpty()) {
							lbAlerta.setText(
									"Para mostrar los Cursos es Necesario un Numero de Colegiado-Precolegiado correcto");
							lbAlerta.setVisible(true);

						} else {
							colegiado = c;

							List<CursoDto> cursosAbiertos = InscripcionCursoFormativo.getCursosAbiertos();

							if (cursosAbiertos.isEmpty()) {
								lbAlerta.setVisible(true);
								lbAlerta.setText("Lo sentimos, No hay cursos Disponibles");
							} else {
								lsCursos.setModel(new AbstractListModel<CursoDto>() {
									private static final long serialVersionUID = 1L;
									CursoDto[] values = cursosAbiertos.toArray(new CursoDto[cursosAbiertos.size()]);

									public int getSize() {
										return values.length;
									}

									public CursoDto getElementAt(int index) {
										return values[index];
									}
								});
							}
						}
					} catch (BusinessException e1) {
						e1.printStackTrace();
					}
				}
			});
			btMostrarCursos.setBounds(782, 21, 293, 66);

		}
		return btMostrarCursos;
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

			btnInscripcionToInicio.setBounds(795, 604, 280, 64);

		}
		return btnInscripcionToInicio;
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
			GridBagLayout gbl_pnCrearCursoButtons = new GridBagLayout();
			gbl_pnCrearCursoButtons.columnWidths = new int[] { 262, 262 };
			gbl_pnCrearCursoButtons.rowHeights = new int[] { 80, 0 };
			gbl_pnCrearCursoButtons.columnWeights = new double[] { 0.0, 0.0 };
			gbl_pnCrearCursoButtons.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
			pnCrearCursoButtons.setLayout(gbl_pnCrearCursoButtons);
			GridBagConstraints gbc_btnCrearCursoCancelar = new GridBagConstraints();
			gbc_btnCrearCursoCancelar.fill = GridBagConstraints.BOTH;
			gbc_btnCrearCursoCancelar.insets = new Insets(0, 0, 0, 5);
			gbc_btnCrearCursoCancelar.gridx = 0;
			gbc_btnCrearCursoCancelar.gridy = 0;
			pnCrearCursoButtons.add(getBtnCrearCursoCancelar(), gbc_btnCrearCursoCancelar);
			GridBagConstraints gbc_btnCrearCursoCrear = new GridBagConstraints();
			gbc_btnCrearCursoCrear.fill = GridBagConstraints.BOTH;
			gbc_btnCrearCursoCrear.gridx = 1;
			gbc_btnCrearCursoCrear.gridy = 0;
			pnCrearCursoButtons.add(getBtnCrearCursoCrear(), gbc_btnCrearCursoCrear);
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

					Curso.add(curso);

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
			pnHomeAccionesSecretaria.setLayout(new GridLayout(6, 1, 0, 10));
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaAbrirInscripciones());
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaConsultarTitulacionSolicitante());
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaEmitirCuotas());
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaAddCurso());
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaListadoInscripciones());
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaTransferencias());
		}
		return pnHomeAccionesSecretaria;
	}

	private DefaultButton getBtHomeAltaColegiado() {
		if (btHomeAltaColegiado == null) {
			btHomeAltaColegiado = new DefaultButton("Darse de alta", "ventana", "AltaColegiado", 'l',
					ButtonColor.NORMAL);
			btHomeAltaColegiado.setMnemonic('A');
			btHomeAltaColegiado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, SOLICITUD_COLEGIADO_PANEL_NAME);
				}
			});
		}
		return btHomeAltaColegiado;
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
							JOptionPane.showMessageDialog(null,
									"No puede pagar la inscripción de un curso debido a que no hay ninguno abierto o no hay suficientes plazas",
									"No puede hacerse cargo de ninguna inscripción", JOptionPane.WARNING_MESSAGE);
						} else {
							mainCardLayout.show(mainPanel, PAGAR_INSCRIPCION_CURSO_PANEL_NAME);
							textFieldDNIColegiado.grabFocus();
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
					try {
						refreshScheduledCoursesList();
					} catch (BusinessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
			btHomeSecretariaConsultarTitulacionSolicitante.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						refrescarListaSolicitudesColegiado();
					} catch (BusinessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
					if (ReciboCRUD.emitirCuotas()) {
						JOptionPane.showMessageDialog(null, "Cuotas emitidads correctamente");
						;
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
			btHomeSecretariaListadoInscripciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, LISTADO_INSCRIPCIONES_PANEL_NAME);
					ActualizaModeloListaCursos();
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

	private JPanel getPnPagarInscripcion() {
		if (pnPagarInscripcion == null) {
			pnPagarInscripcion = new JPanel();
			pnPagarInscripcion.setLayout(new BorderLayout(0, 0));
			pnListadoCursos.add(getPnListadoCursosSouth(), BorderLayout.SOUTH);
			pnListadoCursos.add(getSpListadoCursosCenter(), BorderLayout.CENTER);
		}
		return pnPagarInscripcion;
	}

	private JPanel getPnListadoCursosSouth() {
		if (pnListadoCursosSouth == null) {
			pnListadoCursosSouth = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnListadoCursosSouth.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnListadoCursosSouth.setOpaque(false);
			pnListadoCursosSouth.add(getPnListadoCursosSouthButtons());
		}
		return pnListadoCursosSouth;
	}

	private JPanel getPnListadoCursosSouthButtons() {
		if (pnListadoCursosSouthButtons == null) {
			pnListadoCursosSouthButtons = new JPanel();
			pnListadoCursosSouthButtons.setOpaque(false);
			pnListadoCursosSouthButtons.setLayout(new BorderLayout(0, 0));
			pnListadoCursosSouthButtons.add(getBtListadoCursosVolver());
		}
		return pnListadoCursosSouthButtons;
	}

	private DefaultButton getBtListadoCursosVolver() {
		if (btListadoCursosVolver == null) {
			btListadoCursosVolver = new DefaultButton("Volver a Inicio", "ventana", "VolverAInicio", 'v',
					ButtonColor.NORMAL);
			btListadoCursosVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
				}
			});

		}
		return btListadoCursosVolver;
	}

	private JScrollPane getSpListadoCursosCenter() {
		if (spListadoCursosCenter == null) {
			spListadoCursosCenter = new JScrollPane(getTbListadoTodosCursos());
			spListadoCursosCenter.setBorder(null);
		}
		return spListadoCursosCenter;
	}

	private JTable getTbListadoTodosCursos() {
		if (tbListadoTodosCursos == null) {
			tbListadoTodosCursos = new JTable();
			tbListadoTodosCursos.setIntercellSpacing(new Dimension(0, 0));
			tbListadoTodosCursos.setShowGrid(false);
			tbListadoTodosCursos.setRowMargin(0);
			tbListadoTodosCursos.setRequestFocusEnabled(false);
			tbListadoTodosCursos.setFocusable(false);
			tbListadoTodosCursos.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbListadoTodosCursos.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbListadoTodosCursos.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbListadoTodosCursos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbListadoTodosCursos.setShowVerticalLines(false);
			tbListadoTodosCursos.setOpaque(false);

			tbListadoTodosCursos.setRowHeight(LookAndFeel.ROW_HEIGHT);
			tbListadoTodosCursos.setGridColor(new Color(255, 255, 255));

			try {
				TableModel allCoursesModel = new CursoModel(Curso.listarTodosLosCursos()).getCursoModel(ALL_CURSO);
				tbListadoTodosCursos.setModel(allCoursesModel);

			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}
		return tbListadoTodosCursos;
	}

	private JPanel getPnListadoInscripciones() {
		if (pnListadoInscripciones == null) {
			pnListadoInscripciones = new JPanel();
			pnListadoInscripciones.setLayout(null);
			pnListadoInscripciones.add(getSpnCursos());

			JScrollPane spnInscripciones = new JScrollPane();
			spnInscripciones.setBounds(547, 99, 481, 417);
			pnListadoInscripciones.add(spnInscripciones);

			lInscripciones = new JList<Colegiado_Inscripcion>();

			spnInscripciones.setViewportView(lInscripciones);

			lbTotalIngresosText = new JLabel("Total Ingresos:");
			lbTotalIngresosText.setVisible(false);
			lbTotalIngresosText.setBorder(new LineBorder(Color.BLACK, 2));
			lbTotalIngresosText.setHorizontalAlignment(SwingConstants.CENTER);
			lbTotalIngresosText.setFont(LookAndFeel.PRIMARY_FONT);
			lbTotalIngresosText.setBounds(885, 517, 142, 42);
			pnListadoInscripciones.add(lbTotalIngresosText);

			lbTotalIngresos = new JLabel("");
			lbTotalIngresos.setVisible(false);
			lbTotalIngresos.setFont(new Font("Arial", Font.PLAIN, 14));
			lbTotalIngresos.setHorizontalAlignment(SwingConstants.CENTER);
			lbTotalIngresos.setBorder(new LineBorder(new Color(0, 0, 0)));
			lbTotalIngresos.setBounds(885, 559, 142, 42);
			pnListadoInscripciones.add(lbTotalIngresos);

			JLabel lbListadoCursos = new JLabel("Listado Cursos de formación:");
			lbListadoCursos.setFont(LookAndFeel.PRIMARY_FONT);
			lbListadoCursos.setBounds(67, 67, 481, 32);
			pnListadoInscripciones.add(lbListadoCursos);

			JLabel lbListadoInscripciones = new JLabel("Listado Inscripciones:");
			lbListadoInscripciones.setFont(LookAndFeel.PRIMARY_FONT);
			lbListadoInscripciones.setBounds(547, 67, 481, 32);
			pnListadoInscripciones.add(lbListadoInscripciones);
			pnListadoInscripciones.add(getLbAlertaListadoInscripciones());

			DefaultButton btnListadoInscripcionesToInicio = new DefaultButton("Volver a inicio", "ventana",
					"VolverAInicio", 'v', ButtonColor.NORMAL);
			btnListadoInscripcionesToInicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
				}
			});
			btnListadoInscripcionesToInicio.setFont(new Font("Tahoma", Font.BOLD, 16));
			btnListadoInscripcionesToInicio.setBounds(812, 611, 280, 64);
			pnListadoInscripciones.add(btnListadoInscripcionesToInicio);
		}
		return pnListadoInscripciones;
	}

	private JPanel getPnConsultarTitulacionSolicitante() {
		if (pnConsultarTitulacionSolicitante == null) {
			pnConsultarTitulacionSolicitante = new JPanel();
			pnConsultarTitulacionSolicitante.setOpaque(false);
			pnConsultarTitulacionSolicitante.setLayout(new BorderLayout(0, 10));
			pnConsultarTitulacionSolicitante.add(getPnConsultarTitulacionNorth(), BorderLayout.NORTH);
			pnConsultarTitulacionSolicitante.add(getPnConsultarTitulacionCenter(), BorderLayout.CENTER);
			pnConsultarTitulacionSolicitante.add(getPnConsultarTitulacionSouth(), BorderLayout.SOUTH);
		}
		return pnConsultarTitulacionSolicitante;
	}

	private JPanel getPnConsultarTitulacionNorth() {
		if (pnConsultarTitulacionNorth == null) {
			pnConsultarTitulacionNorth = new JPanel();
			pnConsultarTitulacionNorth.setOpaque(false);
			pnConsultarTitulacionNorth.add(getLbConsultarTitulacionTitle());
		}
		return pnConsultarTitulacionNorth;
	}

	private JPanel getPnConsultarTitulacionCenter() {
		if (pnConsultarTitulacionCenter == null) {
			pnConsultarTitulacionCenter = new JPanel();
			pnConsultarTitulacionCenter.setOpaque(false);
			pnConsultarTitulacionCenter.setLayout(new BorderLayout(0, 0));
			pnConsultarTitulacionCenter.add(getSpListadoAltaSolicitudesColegiado());
			pnConsultarTitulacionCenter.add(getPnListadoAltaSolicitudesColegiadoActualizarLista(), BorderLayout.SOUTH);
		}
		return pnConsultarTitulacionCenter;
	}

	private JPanel getPnListadoAltaSolicitudesColegiadoActualizarLista() {
		if (pnListadoAltaSolicitudesColegiadoActualizarLista == null) {
			pnListadoAltaSolicitudesColegiadoActualizarLista = new JPanel();
			pnListadoAltaSolicitudesColegiadoActualizarLista
					.setLayout(new BoxLayout(pnListadoAltaSolicitudesColegiadoActualizarLista, BoxLayout.X_AXIS));
			pnListadoAltaSolicitudesColegiadoActualizarLista.add(getBtActualizarListaSolicitudesColegiado());
		}
		return pnListadoAltaSolicitudesColegiadoActualizarLista;
	}

	private DefaultButton getBtActualizarListaSolicitudesColegiado() {
		if (btActualizarListaSolicitudesColegiado == null) {
			btActualizarListaSolicitudesColegiado = new DefaultButton("Refrescar lista", "ventana",
					"RefrescarListaSolicitudesColegiado", 'r', ButtonColor.NORMAL);
			btActualizarListaSolicitudesColegiado.setBounds(new Rectangle(0, 0, 250, 80));
			btActualizarListaSolicitudesColegiado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						refrescarListaSolicitudesColegiado();
					} catch (BusinessException e1) {
						// TODO: MOSTRAR MENSAJE DE ERROR
						e1.printStackTrace();
					}
				}
			});
		}
		return btActualizarListaSolicitudesColegiado;
	}

	private JPanel getPnConsultarTitulacionSouth() {
		if (pnConsultarTitulacionSouth == null) {
			pnConsultarTitulacionSouth = new JPanel();
			pnConsultarTitulacionSouth.setOpaque(false);
			pnConsultarTitulacionSouth.setLayout(new BorderLayout(0, 10));
			pnConsultarTitulacionSouth.add(getPnConsultarTitulacionSouthButtons());
			pnConsultarTitulacionSouth.add(getPnConsultarColegiadoDatosColegiadoSeleccionado(), BorderLayout.NORTH);
		}
		return pnConsultarTitulacionSouth;
	}

	private JLabel getLbConsultarTitulacionTitle() {
		if (lbConsultarTitulacionTitle == null) {
			lbConsultarTitulacionTitle = new JLabel("Consultar titulación de un solicitante de Ingreso");
			lbConsultarTitulacionTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lbConsultarTitulacionTitle.setFont(LookAndFeel.HEADING_1_FONT);
		}
		return lbConsultarTitulacionTitle;
	}

	private JScrollPane getSpListadoAltaSolicitudesColegiado() {
		if (spListadoAltaSolicitudesColegiado == null) {
			spListadoAltaSolicitudesColegiado = new JScrollPane(getTbListadoSolicitudesColegiado());
			spListadoAltaSolicitudesColegiado.setBorder(null);
		}
		return spListadoAltaSolicitudesColegiado;
	}

	private JTable getTbListadoSolicitudesColegiado() {
		if (tbListadoSolicitudesColegiado == null) {
			tbListadoSolicitudesColegiado = new JTable();

			tbListadoSolicitudesColegiado.setIntercellSpacing(new Dimension(0, 0));
			tbListadoSolicitudesColegiado.setShowGrid(false);
			tbListadoSolicitudesColegiado.setRowMargin(0);
			tbListadoSolicitudesColegiado.setRequestFocusEnabled(false);
			tbListadoSolicitudesColegiado.setFocusable(false);
			tbListadoSolicitudesColegiado.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbListadoSolicitudesColegiado.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbListadoSolicitudesColegiado.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbListadoSolicitudesColegiado.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbListadoSolicitudesColegiado.setShowVerticalLines(false);
			tbListadoSolicitudesColegiado.setOpaque(false);

			tbListadoSolicitudesColegiado.setRowHeight(LookAndFeel.ROW_HEIGHT);
			tbListadoSolicitudesColegiado.setGridColor(new Color(255, 255, 255));

			try {
				TableModel allSolicitudesColegiado = new ColegiadoModel(Colegiado.findAllSolicitudesAltaColegiados())
						.getColegiadoModel(false);

				tbListadoSolicitudesColegiado.setModel(allSolicitudesColegiado);

			} catch (BusinessException e) {
				e.printStackTrace();
			}

			tbListadoSolicitudesColegiado.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {

					if (event.getValueIsAdjusting())
						return;

					try {

						int selectedRow = tbListadoSolicitudesColegiado.getSelectedRow();

						if (selectedRow == -1) {
							selectedRow = 0;
						}
						
						// TODO: arreglar Francisco
						// Titulacion del colegiado seleccionado
						String colegiadoSeleccionadoDni = tbListadoSolicitudesColegiado.getValueAt(selectedRow, 0)
								.toString();

//						int titulacionColegiadoSeleccionado = Colegiado
//								.findTitulacionColegiadoByDni(colegiadoSeleccionadoDni);

						String simulacionNumeroColegiado = GeneradorNumeroColegiado.generateNumber();

						// Si el solicitante tiene la titulacion = 1, se procede a darle de alta en el
						// COIIPA con el numero {@link #simulacionNumeroColegiado}
//						String isColegiadoMsg = titulacionColegiadoSeleccionado == 1
//								? " posee titulación y se dará de alta en el COIIPA con el número: "
//										+ simulacionNumeroColegiado
//								: " no tiene titulación";

//						lbColegiadoSeleccionadoSolicitudRespuesta
//								.setText("El solicitante de ingreso seleccionado " + isColegiadoMsg);

						// Si el solicitante está graduado, se procederá a su alta automática en el
						// COIIPA.
//						if (titulacionColegiadoSeleccionado == 1) {
//
//							String numeroColegiado = Colegiado.updateNumColegiado(colegiadoSeleccionadoDni);
//
//							// Actualizar la lista de solicitudes con los nuevos cambios
//							refrescarListaSolicitudesColegiado();
//
//							lbColegiadoSeleccionadoSolicitudRespuesta.setText(
//									"Se ha dado de alta en el COIIPA al solicitante con DNI " + colegiadoSeleccionadoDni
//											+ " con el número de colegiado " + numeroColegiado);
//						}

					} catch (NumberFormatException | ArrayIndexOutOfBoundsException | BusinessException nfe) {
						// TODO: MOSTRAR MENSAJE DE ERROR
					}
				}

			});

		}
		return tbListadoSolicitudesColegiado;
	}

	private JPanel getPnConsultarTitulacionSouthButtons() {
		if (pnConsultarTitulacionSouthButtons == null) {
			pnConsultarTitulacionSouthButtons = new JPanel();
			pnConsultarTitulacionSouthButtons.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			pnConsultarTitulacionSouthButtons.setAlignmentX(Component.RIGHT_ALIGNMENT);
			pnConsultarTitulacionSouthButtons.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			pnConsultarTitulacionSouthButtons.add(getBtConsultarSolicitudColegiadoVolver());
		}
		return pnConsultarTitulacionSouthButtons;
	}

	private JButton getBtConsultarSolicitudColegiadoVolver() {
		if (btConsultarSolicitudColegiadoVolver == null) {
			btConsultarSolicitudColegiadoVolver = new DefaultButton("Volver a Inicio", "ventana", "VolverAInicio", 'v',
					ButtonColor.NORMAL);

			btConsultarSolicitudColegiadoVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
				}
			});
		}
		return btConsultarSolicitudColegiadoVolver;
	}

	private JPanel getPnConsultarColegiadoDatosColegiadoSeleccionado() {
		if (pnConsultarColegiadoDatosColegiadoSeleccionado == null) {
			pnConsultarColegiadoDatosColegiadoSeleccionado = new JPanel();
			pnConsultarColegiadoDatosColegiadoSeleccionado.setBorder(new EmptyBorder(20, 0, 20, 0));
			pnConsultarColegiadoDatosColegiadoSeleccionado.setBackground(LookAndFeel.TERTIARY_COLOR);
			pnConsultarColegiadoDatosColegiadoSeleccionado.setLayout(new BorderLayout(0, 0));
			pnConsultarColegiadoDatosColegiadoSeleccionado.add(getLbColegiadoSeleccionadoSolicitudRespuesta());
		}
		return pnConsultarColegiadoDatosColegiadoSeleccionado;
	}

	private JLabel getLbColegiadoSeleccionadoSolicitudRespuesta() {
		if (lbColegiadoSeleccionadoSolicitudRespuesta == null) {
			lbColegiadoSeleccionadoSolicitudRespuesta = new JLabel(
					"Seleccione un colegiado de la lista y, si tiene titulación, se procederá a darle de alta en el COIIPA");
			lbColegiadoSeleccionadoSolicitudRespuesta.setHorizontalAlignment(SwingConstants.CENTER);
			lbColegiadoSeleccionadoSolicitudRespuesta.setAlignmentX(Component.CENTER_ALIGNMENT);
			lbColegiadoSeleccionadoSolicitudRespuesta.setFont(LookAndFeel.HEADING_3_FONT);
			lbColegiadoSeleccionadoSolicitudRespuesta.setForeground(LookAndFeel.SECONDARY_COLOR);
		}
		return lbColegiadoSeleccionadoSolicitudRespuesta;
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
			TextPlaceHolderCustom.setPlaceholder("Introduce el título", txCrearCursoTituloCursoInput);
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
			TextPlaceHolderCustom.setPlaceholder("Introduce la fecha de impartición",
					txCrearCursoFechaImparticionInput);
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
			TextPlaceHolderCustom.setPlaceholder("Introduce el precio", txCrearCursoPrecioInscripcionInput);
		}
		return txCrearCursoPrecioInscripcionInput;
	}

	private JScrollPane getSpnCursos() {
		if (spnCursos == null) {
			spnCursos = new JScrollPane();
			spnCursos.setBounds(67, 99, 481, 417);

			lCursos = new JList<CursoDto>();
			spnCursos.add(lCursos);
			lCursos.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					getLbAlertaListadoInscripciones().setVisible(false);
					List<Colegiado_Inscripcion> listInscripciones = InscripcionColegiado
							.Lista_Inscritos_Curso(lCursos.getSelectedValue());
					if (listInscripciones.isEmpty()) {
						getLbAlertaListadoInscripciones().setVisible(true);
						getLbAlertaListadoInscripciones()
								.setText("Actualmente no hay inscritos en el curso seleccionado");
					} else {

						lInscripciones.setModel(new AbstractListModel<Colegiado_Inscripcion>() {
							private static final long serialVersionUID = 1L;
							Colegiado_Inscripcion[] values = listInscripciones
									.toArray(new Colegiado_Inscripcion[listInscripciones.size()]);

							public int getSize() {
								return values.length;
							}

							public Colegiado_Inscripcion getElementAt(int index) {
								return values[index];
							}
						});

						lbTotalIngresosText.setVisible(true);
						lbTotalIngresos.setVisible(true);
						lbTotalIngresos
								.setText(listInscripciones.stream().mapToDouble(I -> I.cantidadPagada()).sum() + "");
					}

				}
			});

			spnCursos.setViewportView(lCursos);

		}
		return spnCursos;
	}

	public void ActualizaModeloListaCursos() {
		List<CursoDto> listCursos = Curso.listaCursosAbiertosYCerrados();
		if (listCursos.isEmpty()) {
			getLbAlertaListadoInscripciones().setVisible(true);
			getLbAlertaListadoInscripciones().setText("Lo sentimos, no hay cursos disponibles actualmente");
		} else {
			lCursos.setModel(new AbstractListModel<CursoDto>() {
				private static final long serialVersionUID = 1L;
				CursoDto[] values = listCursos.toArray(new CursoDto[listCursos.size()]);

				public int getSize() {
					return values.length;
				}

				public CursoDto getElementAt(int index) {
					return values[index];
				}
			});
		}
	}

	private JLabel getLbAlertaListadoInscripciones() {
		if (lbAlertaListadoInscripciones == null) {
			lbAlertaListadoInscripciones = new JLabel("Actualmente no hay inscritos en el curso seleccionado");
			lbAlertaListadoInscripciones.setForeground(new Color(255, 255, 255));
			lbAlertaListadoInscripciones.setHorizontalAlignment(SwingConstants.CENTER);
			lbAlertaListadoInscripciones.setBorder(null);
			lbAlertaListadoInscripciones.setBackground(LookAndFeel.ERROR_COLOR);
			lbAlertaListadoInscripciones.setOpaque(true);
			lbAlertaListadoInscripciones.setVisible(false);
			lbAlertaListadoInscripciones.setFont(LookAndFeel.PRIMARY_FONT);
			lbAlertaListadoInscripciones.setBounds(67, 533, 748, 68);
		}
		return lbAlertaListadoInscripciones;
	}

	private JButton getBtnTransferenciaColegiado() {
		if (btnTransferenciaColegiado == null) {
			btnTransferenciaColegiado = new DefaultButton("Transferencia bancaria", "ventana", "RealizarTransferencia",
					't', ButtonColor.NORMAL);
			btnTransferenciaColegiado.setMnemonic('T');
			btnTransferenciaColegiado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (comprobarCamposT() && comprobarColegiadoInscripcion()) {
						try {
							int idCurso = Integer.parseInt(((String) comboBoxIdentificadorCursosAbiertos.getSelectedItem()).substring(0, 1));
							InscripcionColegiado.comprobarFecha(
									InscripcionColegiado.findFechaPreinscripcion(textFieldDNIColegiado.getText(),
											idCurso));
							InscripcionColegiado.pagarCursoColegiado(textFieldDNIColegiado.getText(),
									idCurso, "PENDIENTE",
									"TRANSFERENCIA");
							JOptionPane.showMessageDialog(null,
									"Ha seleccionado usted la opción de pagar por transferencia bancaria\n"
											+ "La inscripción al curso con identificador "
											+ idCurso
											+ " se ha tramitado correctamente, queda en estado pendiente\n"
											+ "Tendrá que realizar la transferencia a través del banco en la fecha establecida\n"
											+ "En otro caso, su solicitud quedará cancelada (tiene 48 horas desde este momento para pagar)",
									"Pago pendiente", JOptionPane.INFORMATION_MESSAGE);
							reiniciarInscripcionColegiadoPagar();
							mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
						} catch (BusinessException e1) {
							JOptionPane.showMessageDialog(null,
									"Lo sentimos, no puede hacerse cargo de pagar un curso en el que se ha preinscrito hace más de dos días, "
									+ "tampoco puede pagarlo antes de dicha fecha\n"
											+ "Inténtelo de nuevo la próxima vez",
									"Inscripción no válida", JOptionPane.WARNING_MESSAGE);
							reiniciarInscripcionColegiadoPagar();
							mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
						}
					}
				}
			});
			btnTransferenciaColegiado.setToolTipText("Pulsa para pagar por transferencia bancaria");
		}
		return btnTransferenciaColegiado;
	}

	private void reiniciarInscripcionColegiadoPagar() {
		textFieldDNIColegiado.setText(null);
		textFieldNumeroTarjetaColegiado.setText(null);
		this.calendarioFechaCaducidad.setDate(new Date());
	}

	private boolean comprobarColegiadoInscripcion() {
		int idCurso = Integer.parseInt(((String) comboBoxIdentificadorCursosAbiertos.getSelectedItem()).substring(0, 1));
		// comprobamos que el dni asociado sea un colegiado
		try {
			if (Colegiado.findColegiadoPorDni(textFieldDNIColegiado.getText()) == null) {
				JOptionPane.showMessageDialog(null,
						"Lo sentimos, no puede hacerse cargo de pagar un curso, no está registrado en el COIIPA, formalice su solicitud primero\n"
								+ "Inténtelo de nuevo la próxima vez",
						"Dni inválido", JOptionPane.WARNING_MESSAGE);
				reiniciarInscripcionColegiadoPagar();
				mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
				return false;
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		// comprobamos que se ha inscrito en el curso
		try {
			InscripcionColegiado.findFechaPreinscripcion(textFieldDNIColegiado.getText(),
					idCurso);
			return true;
		} catch (BusinessException e) {
			JOptionPane.showMessageDialog(null,
					"Lo sentimos, no puede hacerse cargo de pagar un curso, en el que no se ha preinscrito o ya lo ha pagado\n"
							+ "Consulte sus pagos a las inscripciones del curso",
					"Preinscripción no realizada", JOptionPane.WARNING_MESSAGE);
			reiniciarInscripcionColegiadoPagar();
			mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
			return false;
		}
	}

	private boolean comprobarCamposT() {
		if (textFieldDNIColegiado.getText().isBlank() || textFieldDNIColegiado.getText().length() != 9) {
			JOptionPane.showMessageDialog(null,
					"Revise que no haya dejado ningún campo vacío y el formato del DNI es el correcto\n"
							+ "Sigue el ejemplo del campo correspondiente",
					"Datos no válidos", JOptionPane.WARNING_MESSAGE);
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
			pnTarjetaDatosColegiado.add(getPnNumeroTarjetaValidarTarjeta());
			pnTarjetaDatosColegiado.setBorder(new LineBorder(Color.BLACK));
		}
		return pnTarjetaDatosColegiado;
	}

	private JButton getBtnTarjetaCreditoColegiado() {
		if (btnTarjetaCreditoColegiado == null) {
			btnTarjetaCreditoColegiado = new DefaultButton("Validar tarjeta de crédito", "ventana", "ValidarTarjeta",
					'v', ButtonColor.NORMAL);
			btnTarjetaCreditoColegiado.setMnemonic('V');
			btnTarjetaCreditoColegiado.setDefaultCapable(false);
			btnTarjetaCreditoColegiado.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			btnTarjetaCreditoColegiado.setHorizontalTextPosition(SwingConstants.CENTER);

			btnTarjetaCreditoColegiado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (comprobarCampos() && comprobarColegiadoInscripcion() && comprobarFechaCaducidad()) {
						try {
							int idCurso = Integer.parseInt(((String) comboBoxIdentificadorCursosAbiertos.getSelectedItem()).substring(0, 1));
							InscripcionColegiado.comprobarFecha(
									InscripcionColegiado.findFechaPreinscripcion(textFieldDNIColegiado.getText(),
											idCurso));

							InscripcionColegiado.pagarCursoColegiado(textFieldDNIColegiado.getText(),
									idCurso, "PAGADO", "TARJETA");

							JOptionPane.showMessageDialog(null,
									"Ha seleccionado usted la opción de pagar por tarjeta de crédito\n"
											+ "El pago se ha inscrito con éxito",
									"Pago verificado", JOptionPane.INFORMATION_MESSAGE);
							reiniciarInscripcionColegiadoPagar();
							mainCardLayout.show(mainPanel, HOME_PANEL_NAME);

						} catch (BusinessException e1) {
							JOptionPane.showMessageDialog(null,
									"Lo sentimos, no puede hacerse cargo de pagar un curso en el que se ha preinscrito hace más de dos días.\n"
											+ "Inténtelo de nuevo la próxima vez",
									"Inscripción no válida", JOptionPane.WARNING_MESSAGE);
							reiniciarInscripcionColegiadoPagar();
							mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
						}
					}
				}
			});
			btnTarjetaCreditoColegiado.setToolTipText("Pulsa para pagar con tarjeta");

		}
		return btnTarjetaCreditoColegiado;
	}

	private boolean comprobarCampos() {

		try {
			@SuppressWarnings("unused")
			int numeroTarjeta = Integer.parseInt(textFieldNumeroTarjetaColegiado.getText());

		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "El número de tarjeta no es válido. Por favor, revíselo.",
					"Datos no válidos", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (textFieldDNIColegiado.getText().isBlank() || textFieldDNIColegiado.getText().length() != 9
				|| textFieldNumeroTarjetaColegiado.getText().isBlank()
				|| textFieldNumeroTarjetaColegiado.getText().length() != 5) {
			JOptionPane.showMessageDialog(null,
					"Revise que no haya dejado ningún campo vacío y el formato del DNI y de la tarjeta de crédito son correctos\n"
							+ "Sigue el ejemplo del campo correspondiente",
					"Datos no válidos", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private boolean comprobarFechaCaducidad() {
		Date fechaCaducidad = calendarioFechaCaducidad.getDate();
		Date ahora = new Date();

		if (fechaCaducidad.after(ahora)) {
			return true;
		}

		JOptionPane.showMessageDialog(null, "La fecha de caducidad debe ser posterior a la actual",
				"Fecha de caducidad incorrecta", JOptionPane.ERROR_MESSAGE);
		this.calendarioFechaCaducidad.setDate(new Date());
		return false;
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

	private JCalendar getCalendarioFechaCaducidad() {
		if (calendarioFechaCaducidad == null) {
			calendarioFechaCaducidad = new JCalendar();
			calendarioFechaCaducidad.getDayChooser().getDayPanel()
					.setToolTipText("Seleccione la fecha de caducidad de su tarjeta");
			calendarioFechaCaducidad.setBorder(new LineBorder(Color.GRAY));
		}
		return calendarioFechaCaducidad;
	}

	private JLabel getLblFechaCaducidadDatosColegiado() {
		if (lblFechaCaducidadDatosColegiado == null) {
			lblFechaCaducidadDatosColegiado = new JLabel("Fecha de caducidad:");
			lblFechaCaducidadDatosColegiado.setDisplayedMnemonic('F');
			lblFechaCaducidadDatosColegiado.setLabelFor(getCalendarioFechaCaducidad());
			lblFechaCaducidadDatosColegiado.setFont(LookAndFeel.PRIMARY_FONT);
			lblFechaCaducidadDatosColegiado.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblFechaCaducidadDatosColegiado;
	}

	private JLabel getLblNumeroTarjetaDatosColegiado() {
		if (lblNumeroTarjetaDatosColegiado == null) {
			lblNumeroTarjetaDatosColegiado = new JLabel("Número de tarjeta:");
			lblNumeroTarjetaDatosColegiado.setLabelFor(getTextFieldNumeroTarjetaColegiado());
			lblNumeroTarjetaDatosColegiado.setFont(LookAndFeel.PRIMARY_FONT);
			lblNumeroTarjetaDatosColegiado.setDisplayedMnemonic('N');
			lblNumeroTarjetaDatosColegiado.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNumeroTarjetaDatosColegiado;
	}

	private JPanel getPnNumeroTarjetaDatosColegiado() {
		if (pnNumeroTarjetaDatosColegiado == null) {
			pnNumeroTarjetaDatosColegiado = new JPanel();
			pnNumeroTarjetaDatosColegiado.setLayout(new GridLayout(0, 2, 0, 0));
			pnNumeroTarjetaDatosColegiado.add(getLblNumeroTarjetaDatosColegiado());
			pnNumeroTarjetaDatosColegiado.setBorder(new LineBorder(Color.BLACK));
			pnNumeroTarjetaDatosColegiado.add(getPnNumeroTarjetaDatosColegiadoText());
		}
		return pnNumeroTarjetaDatosColegiado;
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

	private JPanel getPnModosPagoInscripcionColegiado() {
		if (pnModosPagoInscripcionColegiado == null) {
			pnModosPagoInscripcionColegiado = new JPanel();
			pnModosPagoInscripcionColegiado.setLayout(new GridLayout(0, 2, 0, 0));
			pnModosPagoInscripcionColegiado.add(getPnTarjetaDatosColegiado());
			pnModosPagoInscripcionColegiado.add(getPnPagoTranferencia());
			pnModosPagoInscripcionColegiado.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
					"ELIGE UNA DE LAS DOS OPCIONES", TitledBorder.CENTER, TitledBorder.TOP, null, Color.GRAY));
			pnModosPagoInscripcionColegiado.add(getPnPagoTranferencia());
		}
		return pnModosPagoInscripcionColegiado;
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

	private JPanel getPnDatosInicialesIdCurso() {
		if (pnDatosInicialesIdCurso == null) {
			pnDatosInicialesIdCurso = new JPanel();
			pnDatosInicialesIdCurso.setLayout(new GridLayout(1, 0, 0, 0));
			pnDatosInicialesIdCurso.add(getLblNewLabelIdentificadorCurso());
			pnDatosInicialesIdCurso.add(getComboBoxIdentificadorCursosAbiertos());
		}
		return pnDatosInicialesIdCurso;
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

	private JLabel getLblNewLabelDNIColegiado() {
		if (lblNewLabelDNIColegiado == null) {
			lblNewLabelDNIColegiado = new JLabel("DNI:");
			lblNewLabelDNIColegiado.setLabelFor(getTextFieldDNIColegiado());
			lblNewLabelDNIColegiado.setDisplayedMnemonic('D');
			lblNewLabelDNIColegiado.setFont(LookAndFeel.PRIMARY_FONT);
			lblNewLabelDNIColegiado.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNewLabelDNIColegiado;
	}

	private JTextField getTextFieldDNIColegiado() {
		if (textFieldDNIColegiado == null) {
			textFieldDNIColegiado = new JTextField();
			TextPlaceHolderCustom.setPlaceholder("71778880C", textFieldDNIColegiado);
			textFieldDNIColegiado.setToolTipText("Introduce su DNI");
			textFieldDNIColegiado.setHorizontalAlignment(SwingConstants.LEFT);
			textFieldDNIColegiado.setColumns(10);
		}
		return textFieldDNIColegiado;
	}

	private JLabel getLblRellenarDatosInscripcionCurso() {
		if (lblRellenarDatosInscripcionCurso == null) {
			lblRellenarDatosInscripcionCurso = new JLabel(
					"Debe de cumplimentar su número de colegiado y el identificador del curso independientemente del método de pago que elija");
			lblRellenarDatosInscripcionCurso.setHorizontalAlignment(SwingConstants.CENTER);
			lblRellenarDatosInscripcionCurso.setFont(new Font("Arial Black", Font.PLAIN, 14));
		}

		return lblRellenarDatosInscripcionCurso;
	}

	private JLabel getLbInscripcionPagoAviso_1() {
		if (lbInscripcionPagoAviso == null) {
			lbInscripcionPagoAviso = new JLabel(
					"No puede pagar un curso el cual no se ha preinscrito, y en el caso de que se haya preinscrito contará con dos días a partir de la fecha de preinscripcion");
			lbInscripcionPagoAviso.setHorizontalAlignment(SwingConstants.CENTER);
			lbInscripcionPagoAviso.setForeground(Color.RED);
			lbInscripcionPagoAviso.setFont(LookAndFeel.PRIMARY_FONT);
		}
		return lbInscripcionPagoAviso;
	}

	private JLabel getLbInscripcionPagoColegiado() {
		if (lbInscripcionPagoColegiado == null) {
			lbInscripcionPagoColegiado = new JLabel(
					"Puede pagar la inscripción al curso por tarjeta o por transferencia bancaria");
			lbInscripcionPagoColegiado.setHorizontalAlignment(SwingConstants.CENTER);
			lbInscripcionPagoColegiado.setFont(new Font("Arial Black", Font.BOLD, 11));
			lbInscripcionPagoColegiado.setFont(LookAndFeel.HEADING_1_FONT);
		}
		return lbInscripcionPagoColegiado;
	}

	private JLabel getLblNewLabelIdentificadorCurso() {
		if (lblNewLabelIdentificadorCurso == null) {
			lblNewLabelIdentificadorCurso = new JLabel("Curso a seleccionar:");
			lblNewLabelIdentificadorCurso.setLabelFor(getComboBoxIdentificadorCursosAbiertos());
			lblNewLabelIdentificadorCurso.setDisplayedMnemonic('I');
			lblNewLabelIdentificadorCurso.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelIdentificadorCurso.setFont(LookAndFeel.PRIMARY_FONT);
		}
		return lblNewLabelIdentificadorCurso;
	}

	private JComboBox<String> getComboBoxIdentificadorCursosAbiertos() {
		if (comboBoxIdentificadorCursosAbiertos == null) {
			comboBoxIdentificadorCursosAbiertos = new JComboBox<String>();
			comboBoxIdentificadorCursosAbiertos.setToolTipText("Selecciona el curso que desea pagar");
			String[] elementsComboBox;
			List<CursoDto> lista;
			try {
				lista = Curso.listarCursosAbiertos();
				elementsComboBox = new String[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					elementsComboBox[i] = lista.get(i).codigoCurso + " (titulo = " + lista.get(i).titulo + ")";
				}
				comboBoxIdentificadorCursosAbiertos = new JComboBox<String>(
						new DefaultComboBoxModel<String>(elementsComboBox));
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}
		return comboBoxIdentificadorCursosAbiertos;
	}

	private JPanel getPnPagarInscripcionColegiadoSur() {
		if (pnPagarInscripcionColegiadoSur == null) {
			pnPagarInscripcionColegiadoSur = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnPagarInscripcionColegiadoSur.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnPagarInscripcionColegiadoSur.add(getBtnInicioInscripcion());
		}
		return pnPagarInscripcionColegiadoSur;
	}

	private DefaultButton getBtnInicioInscripcion() {
		if (btnInicioInscripcion == null) {
			btnInicioInscripcion = new DefaultButton("Vovler a Inicio", "ventana", "VolverAInicio", 'v',
					ButtonColor.CANCEL);
			btnInicioInscripcion.setText("Atras");
			btnInicioInscripcion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (confirmarVolverPrinicipio()) {
						reiniciarInscripcionColegiadoPagar();
						mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
					}
				}
			});
			btnInicioInscripcion.setToolTipText("Pulse para volver a la pantalla principal");
			btnInicioInscripcion.setMnemonic('A');
		}
		return btnInicioInscripcion;
	}

	/**
	 * Refresca el modelo de la tabla de la lista de solicitudes de alta para
	 * Colegiados.
	 * 
	 * @throws BusinessException
	 */
	private void refrescarListaSolicitudesColegiado() throws BusinessException {
		TableModel allSolicitudesColegiado = new ColegiadoModel(Colegiado.findAllSolicitudesAltaColegiados())
				.getColegiadoModel(false);

		tbListadoSolicitudesColegiado.setModel(allSolicitudesColegiado);

		lbColegiadoSeleccionadoSolicitudRespuesta.setText(
				"Seleccione un colegiado de la lista y, si tiene titulación, se procederá a darle de alta en el COIIPA");
	}

	private JPanel getPnNumeroTarjetaDatosColegiadoText() {
		if (pnNumeroTarjetaDatosColegiadoText == null) {
			pnNumeroTarjetaDatosColegiadoText = new JPanel();
			GridBagLayout gbl_pnNumeroTarjetaDatosColegiadoText = new GridBagLayout();
			gbl_pnNumeroTarjetaDatosColegiadoText.columnWidths = new int[] { 256 };
			gbl_pnNumeroTarjetaDatosColegiadoText.rowHeights = new int[] { 50, 0, 100, 50 };
			gbl_pnNumeroTarjetaDatosColegiadoText.columnWeights = new double[] { 1.0 };
			gbl_pnNumeroTarjetaDatosColegiadoText.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
			pnNumeroTarjetaDatosColegiadoText.setLayout(gbl_pnNumeroTarjetaDatosColegiadoText);
			GridBagConstraints gbc_textFieldNumeroTarjetaColegiado = new GridBagConstraints();
			gbc_textFieldNumeroTarjetaColegiado.gridheight = 2;
			gbc_textFieldNumeroTarjetaColegiado.fill = GridBagConstraints.BOTH;
			gbc_textFieldNumeroTarjetaColegiado.gridx = 0;
			gbc_textFieldNumeroTarjetaColegiado.gridy = 1;
			pnNumeroTarjetaDatosColegiadoText.add(getTextFieldNumeroTarjetaColegiado(),
					gbc_textFieldNumeroTarjetaColegiado);
		}
		return pnNumeroTarjetaDatosColegiadoText;
	}

	private JTextField getTextFieldNumeroTarjetaColegiado() {
		if (textFieldNumeroTarjetaColegiado == null) {
			textFieldNumeroTarjetaColegiado = new JTextField();
			textFieldNumeroTarjetaColegiado.setAlignmentY(Component.BOTTOM_ALIGNMENT);
			textFieldNumeroTarjetaColegiado.setAlignmentX(Component.LEFT_ALIGNMENT);
			textFieldNumeroTarjetaColegiado.setToolTipText("Introduzca el número de su tarjeta bancaria");
			TextPlaceHolderCustom.setPlaceholder("23456", textFieldNumeroTarjetaColegiado);
			textFieldNumeroTarjetaColegiado.setHorizontalAlignment(SwingConstants.LEFT);
			textFieldNumeroTarjetaColegiado.setColumns(10);
		}
		return textFieldNumeroTarjetaColegiado;
	}

	private JPanel getPnNumeroTarjetaValidarTarjeta() {
		if (pnNumeroTarjetaValidarTarjeta == null) {
			pnNumeroTarjetaValidarTarjeta = new JPanel();
			pnNumeroTarjetaValidarTarjeta.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			pnNumeroTarjetaValidarTarjeta.setMaximumSize(new Dimension(500, 500));
			pnNumeroTarjetaValidarTarjeta.setLayout(new GridLayout(0, 1, 0, 0));
			pnNumeroTarjetaValidarTarjeta.add(getBtnTarjetaCreditoColegiado());
		}
		return pnNumeroTarjetaValidarTarjeta;
	}

	private JPanel getPnPagoTranferencia() {
		if (pnPagoTranferencia == null) {
			pnPagoTranferencia = new JPanel();
			pnPagoTranferencia.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			pnPagoTranferencia.setBorder(new EmptyBorder(0, 100, 0, 100));
			pnPagoTranferencia.setLayout(new GridLayout(0, 1, 0, 0));
			pnPagoTranferencia.add(getBtnTransferenciaColegiado());
		}
		return pnPagoTranferencia;
	}

	private JPanel getPnDatosColPoblacionText() {
		if (pnDatosColPoblacionText == null) {
			pnDatosColPoblacionText = new JPanel();
			GridBagLayout gbl_pnDatosColPoblacionText = new GridBagLayout();
			gbl_pnDatosColPoblacionText.columnWidths = new int[] { 269, 0 };
			gbl_pnDatosColPoblacionText.rowHeights = new int[] { 74, 0, 74, 74 };
			gbl_pnDatosColPoblacionText.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
			gbl_pnDatosColPoblacionText.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
			pnDatosColPoblacionText.setLayout(gbl_pnDatosColPoblacionText);
			GridBagConstraints gbc_textFieldPoblacion = new GridBagConstraints();
			gbc_textFieldPoblacion.gridheight = 2;
			gbc_textFieldPoblacion.fill = GridBagConstraints.BOTH;
			gbc_textFieldPoblacion.gridx = 0;
			gbc_textFieldPoblacion.gridy = 1;
			pnDatosColPoblacionText.add(getTextFieldPoblacion(), gbc_textFieldPoblacion);
		}
		return pnDatosColPoblacionText;
	}

	private JPanel getPnDatosColTelefonoText() {
		if (pnDatosColTelefonoText == null) {
			pnDatosColTelefonoText = new JPanel();
			GridBagLayout gbl_pnDatosColTelefonoText = new GridBagLayout();
			gbl_pnDatosColTelefonoText.columnWidths = new int[] { 269, 0 };
			gbl_pnDatosColTelefonoText.rowHeights = new int[] { 74, 0, 74, 74 };
			gbl_pnDatosColTelefonoText.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
			gbl_pnDatosColTelefonoText.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
			pnDatosColTelefonoText.setLayout(gbl_pnDatosColTelefonoText);
			GridBagConstraints gbc_textFieldTelefono = new GridBagConstraints();
			gbc_textFieldTelefono.gridheight = 2;
			gbc_textFieldTelefono.fill = GridBagConstraints.BOTH;
			gbc_textFieldTelefono.gridx = 0;
			gbc_textFieldTelefono.gridy = 1;
			pnDatosColTelefonoText.add(getTextFieldTelefono(), gbc_textFieldTelefono);
		}
		return pnDatosColTelefonoText;
	}

	private JPanel getPnDatosColTitulacionText() {
		if (pnDatosColTitulacionText == null) {
			pnDatosColTitulacionText = new JPanel();
			GridBagLayout gbl_pnDatosColTitulacionText = new GridBagLayout();
			gbl_pnDatosColTitulacionText.columnWidths = new int[] { 269, 0 };
			gbl_pnDatosColTitulacionText.rowHeights = new int[] { 49, 0, 49, 49 };
			gbl_pnDatosColTitulacionText.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
			gbl_pnDatosColTitulacionText.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
			pnDatosColTitulacionText.setLayout(gbl_pnDatosColTitulacionText);
			GridBagConstraints gbc_textFieldTitulo = new GridBagConstraints();
			gbc_textFieldTitulo.gridheight = 2;
			gbc_textFieldTitulo.fill = GridBagConstraints.BOTH;
			gbc_textFieldTitulo.gridx = 0;
			gbc_textFieldTitulo.gridy = 1;
			pnDatosColTitulacionText.add(getTextFieldTitulacion(), gbc_textFieldTitulo);
		}
		return pnDatosColTitulacionText;
	}

	private JPanel getPnDatosColCentoText() {
		if (pnDatosColCentoText == null) {
			pnDatosColCentoText = new JPanel();
			GridBagLayout gbl_pnDatosColCentoText = new GridBagLayout();
			gbl_pnDatosColCentoText.columnWidths = new int[] { 269, 0 };
			gbl_pnDatosColCentoText.rowHeights = new int[] { 49, 0, 49, 49 };
			gbl_pnDatosColCentoText.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
			gbl_pnDatosColCentoText.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
			pnDatosColCentoText.setLayout(gbl_pnDatosColCentoText);
			GridBagConstraints gbc_textFieldCentro = new GridBagConstraints();
			gbc_textFieldCentro.gridheight = 2;
			gbc_textFieldCentro.fill = GridBagConstraints.BOTH;
			gbc_textFieldCentro.gridx = 0;
			gbc_textFieldCentro.gridy = 1;
			pnDatosColCentoText.add(getTextFieldCentroColegiado(), gbc_textFieldCentro);
		}
		return pnDatosColCentoText;
	}

	private JPanel getPnDatosColAnnioText() {
		if (pnDatosColAnnioText == null) {
			pnDatosColAnnioText = new JPanel();
			GridBagLayout gbl_pnDatosColAnnioText = new GridBagLayout();
			gbl_pnDatosColAnnioText.columnWidths = new int[] { 269, 0 };
			gbl_pnDatosColAnnioText.rowHeights = new int[] { 49, 0, 49, 49 };
			gbl_pnDatosColAnnioText.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
			gbl_pnDatosColAnnioText.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
			pnDatosColAnnioText.setLayout(gbl_pnDatosColAnnioText);
			GridBagConstraints gbc_textFieldAno = new GridBagConstraints();
			gbc_textFieldAno.gridheight = 2;
			gbc_textFieldAno.fill = GridBagConstraints.BOTH;
			gbc_textFieldAno.gridx = 0;
			gbc_textFieldAno.gridy = 1;
			pnDatosColAnnioText.add(getTextFieldAno(), gbc_textFieldAno);
		}
		return pnDatosColAnnioText;
	}
	
	private JPanel getPnTransferencias() {
		if (pnTransferencias == null) {
			pnTransferencias = new JPanel();
			pnTransferencias.setLayout(new BorderLayout(0, 0));
			pnTransferencias.add(getPnTransferenciasCentro());
			pnTransferencias.add(getPnTransferenciasNorte(), BorderLayout.NORTH);
			panelMuestraTransferencias.setVisible(false);
		}
		return pnTransferencias;
	}
	private JPanel getPnTransferenciasCentro() {
		if (pnTransferenciasCentro == null) {
			pnTransferenciasCentro = new JPanel();
			pnTransferenciasCentro.setLayout(new GridLayout(1, 2, 0, 0));
			pnTransferenciasCentro.add(getPanelListaMovimientos());
		}
		return pnTransferenciasCentro;
	}
	private JPanel getPnTransferenciasNorte() {
		if (pnTransferenciasNorte == null) {
			pnTransferenciasNorte = new JPanel();
			pnTransferenciasNorte.add(getLblObtenerListaDeMov());
		}
		return pnTransferenciasNorte;
	}
	private JLabel getLblObtenerListaDeMov() {
		if (lblObtenerListaDeMov == null) {
			lblObtenerListaDeMov = new JLabel("Lista de movimientos en la cuenta bancaria de un curso del COIIPA");
			lblObtenerListaDeMov.setHorizontalAlignment(SwingConstants.CENTER);
			lblObtenerListaDeMov.setFont(LookAndFeel.HEADING_1_FONT);
		}
		return lblObtenerListaDeMov;
	}
	private DefaultButton getBtHomeSecretariaTransferencias() {
		if (btHomeSecretariaTransferencias == null) {
			btHomeSecretariaTransferencias = new DefaultButton("Abrir inscripciones de un curso", "ventana", "AbrirInscrionesCurso", 'b', ButtonColor.NORMAL);
			btHomeSecretariaTransferencias.setMnemonic('T');
			btHomeSecretariaTransferencias.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try { 
						if (Curso.listarTodosLosCursos().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Lo sentimos, no hay ningún curso disponible","No existen cursos", JOptionPane.WARNING_MESSAGE);
						} else {
							panelMuestraTransferencias.setVisible(false);
							btnMovimientosBancarios.setEnabled(true);
							btnProcesarPagos.setEnabled(false);
							mainCardLayout.show(mainPanel, INSCRIPCION_CURSO_TRANSFERENCIAS);
							tbCourses.setEnabled(true);
							tbCourses.clearSelection();
							tbCourses.removeAll();
							try {
								tableModelC = new CursoModel(Curso.listarTodosLosCursos()).getCursoModel(ALL_MINUS_ID);
							} catch (BusinessException e1) {
								e1.printStackTrace();
							}
							tbCourses.setModel(tableModelC);
							tbCourses.repaint();
						}
					} catch (BusinessException e1) {
						e1.printStackTrace();
					}
				}
			});
			btHomeSecretariaTransferencias.setText("Registrar transferencias");
		}
		return btHomeSecretariaTransferencias;
	}
	private JPanel getPanelListaMovimientos() {
		if (panelListaMovimientos == null) {
			panelListaMovimientos = new JPanel();
			panelListaMovimientos.setLayout(new GridLayout(2, 1, 0, 0));
	        panelListaMovimientos.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Movimientos bancarios del COIIPA", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			panelListaMovimientos.add(getPanelMuestraCursos());
			panelListaMovimientos.add(getPanelMuestraTransferencias());
		}
		return panelListaMovimientos;
	}
	private JPanel getPanelMuestraCursos() {
		if (panelMuestraCursos == null) {
			panelMuestraCursos = new JPanel();
			panelMuestraCursos.setLayout(new BorderLayout(0, 0));
			panelMuestraCursos.add(getPanelMuestraCursosNorte(), BorderLayout.NORTH);
			panelMuestraCursos.add(getPaneMuestraCursosCentro(), BorderLayout.CENTER);
			panelMuestraCursos.add(getPanelMuestraCursosSur(), BorderLayout.SOUTH);
			panelMuestraCursos.setBorder(new LineBorder(Color.BLACK));
		}
		return panelMuestraCursos;
	}
	private JPanel getPanelMuestraTransferencias() {
		if (panelMuestraTransferencias == null) {
			panelMuestraTransferencias = new JPanel();
			panelMuestraTransferencias.setLayout(new BorderLayout(0, 0));
			panelMuestraTransferencias.add(getPanelMuestraTransferenciasNorte(), BorderLayout.NORTH);
			panelMuestraTransferencias.add(getPanelMuestraTransferenciasCentro(), BorderLayout.CENTER);
			panelMuestraTransferencias.add(getPanelMuestraTransferenciasSur(), BorderLayout.SOUTH);
			panelMuestraTransferencias.setBorder(new LineBorder(Color.BLACK));
		}
		return panelMuestraTransferencias;
	}
	private JPanel getPanelMuestraCursosNorte() {
		if (panelMuestraCursosNorte == null) {
			panelMuestraCursosNorte = new JPanel();
			panelMuestraCursosNorte.add(getLblSeleccionaCursoTransf());
		}
		return panelMuestraCursosNorte;
	}
	private JPanel getPaneMuestraCursosCentro() {
		if (paneMuestraCursosCentro == null) {
			paneMuestraCursosCentro = new JPanel();
			paneMuestraCursosCentro.setLayout(new GridLayout(0, 1, 0, 0));
			paneMuestraCursosCentro.add(getScrollPaneCursos());
		}
		return paneMuestraCursosCentro;
	}
	private JPanel getPanelMuestraCursosSur() {
		if (panelMuestraCursosSur == null) {
			panelMuestraCursosSur = new JPanel();
			panelMuestraCursosSur.add(getBtnMovimientosBancarios());
		}
		return panelMuestraCursosSur;
	}
	private JLabel getLblSeleccionaCursoTransf() {
		if (lblSeleccionaCursoTransf == null) {
			lblSeleccionaCursoTransf = new JLabel("Seleccione el curso sobre el que desea registrar su actividad bancaria");
			lblSeleccionaCursoTransf.setFont(LookAndFeel.HEADING_2_FONT);
		}
		return lblSeleccionaCursoTransf;
	}
	private JButton getBtnMovimientosBancarios() {
		if (btnMovimientosBancarios == null) {
			btnMovimientosBancarios = new DefaultButton("Solicitar movimientos bancarios del curso", "ventana", "ValidarTransferencia",
					'S', ButtonColor.NORMAL);
			btnMovimientosBancarios.setToolTipText("Pulsa para registrar la actividad bancaria del curso seleccionado");
			btnMovimientosBancarios.setText("Registrar");
			btnMovimientosBancarios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (cursoSeleccionado==null) {
						JOptionPane.showMessageDialog(null,
								"Por favor, revise que haya seleccionado un curso para ver el estado de la cuenta bancaria",
								"Seleccione el curso", JOptionPane.WARNING_MESSAGE);
					} else {
						try {
							// InscripcionColegiado.pagarBancoTransferencia(cursoSeleccionado.codigoCurso);
							InscripcionColegiado.emitirFicheroTransferenciaPorCurso(cursoSeleccionado.codigoCurso);
						} catch (BusinessException e1) {
							e1.printStackTrace();
						}
						btnMovimientosBancarios.setEnabled(false);
						panelMuestraTransferencias.setVisible(true);
						panelMuestraTransferencias.setVisible(true);
						btnProcesarPagos.setEnabled(true);
						tbCourses.setEnabled(false);
						panelMuestraTransferenciasCentro.add(getScrollPaneTransferencias());
						JOptionPane.showMessageDialog(null,
								"Se acaba de generar un fichero con los datos bancarios de cada inscripción del curso seleccionado\n"
								+ "Se mostrarán en la siguiente tabla, aunque también puede visualizarlo en la carpeta transferencias, cuyo nombre es " + cursoSeleccionado.codigoCurso + "_banco.csv\n"
								+ "Contiene los datos más recientes sobre las transferencias de los clientes en la cuenta bancaria del COIIPA",
								"Consulta los datos bancarios", JOptionPane.INFORMATION_MESSAGE);
						if (tbTransferencias!=null) {
							try {
								tableModel = new InscripcionColegiadoModel(InscripcionColegiado.obtenerTransferencias(cursoSeleccionado.codigoCurso)).getCursoModel(InscripcionColegiadoModel.TRANSFERENCIAS_RECIBIDAS);
							} catch (BusinessException e1) {
								e1.printStackTrace();
							}
							tbTransferencias.setModel(tableModel);
							tbTransferencias.repaint();
						}
						
						
					
					}
				}
			});
			btnMovimientosBancarios.setMnemonic('R');
		}
		return btnMovimientosBancarios;
	}
	private JPanel getPanelMuestraTransferenciasNorte() {
		if (panelMuestraTransferenciasNorte == null) {
			panelMuestraTransferenciasNorte = new JPanel();
			panelMuestraTransferenciasNorte.add(getLblRegistrosBancarios());
		}
		return panelMuestraTransferenciasNorte;
	}
	private JPanel getPanelMuestraTransferenciasCentro() {
		if (panelMuestraTransferenciasCentro == null) {
			panelMuestraTransferenciasCentro = new JPanel();
			panelMuestraTransferenciasCentro.setToolTipText("Contiene la información de los registros bancarios del COIIPA del curso seleccionado");
			panelMuestraTransferenciasCentro.setLayout(new GridLayout(0, 1, 0, 0));
		}
		return panelMuestraTransferenciasCentro;
	}
	private JPanel getPanelMuestraTransferenciasSur() {
		if (panelMuestraTransferenciasSur == null) {
			panelMuestraTransferenciasSur = new JPanel();
			panelMuestraTransferenciasSur.add(getBtnProcesarPagos());
		}
		return panelMuestraTransferenciasSur;
	}
	private JLabel getLblRegistrosBancarios() {
		if (lblRegistrosBancarios == null) {
			lblRegistrosBancarios = new JLabel("Registros bancarios de los pagos por transferencia del curso");
			lblRegistrosBancarios.setFont(LookAndFeel.HEADING_2_FONT);
		}
		return lblRegistrosBancarios;
	}
	private JButton getBtnProcesarPagos() {
		if (btnProcesarPagos == null) {
			btnProcesarPagos = new DefaultButton("Procesar", "ventana", "ValidarTransferencia",
					'P', ButtonColor.NORMAL);
			btnProcesarPagos.setToolTipText("Pulsa para procesar los pagos del curso por transferencia");
			btnProcesarPagos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tbTransferencias.setEnabled(false);
					btnProcesarPagos.setEnabled(false);
					InscripcionColegiado.procesarTransferencias(cursoSeleccionado.codigoCurso);
					pnTransferenciasProcesadasCentro.add(getScrollPaneProcesar());
					pnTransferencias.setVisible(false);
					pnTransferenciasProcesadas.setVisible(true);
					try {
						tableModelP = new InscripcionColegiadoModel(InscripcionColegiado.obtenerTransferenciasProcesadas(cursoSeleccionado.codigoCurso)).getCursoModel(InscripcionColegiadoModel.TRANSFERENCIAS_PROCESADAS);
					} catch (BusinessException e1) {
						e1.printStackTrace();
					}
					tbProcesarTransferencias.setModel(tableModelP);
					tbProcesarTransferencias.repaint();

				}
			});
			btnProcesarPagos.setMnemonic('P');
		}
		return btnProcesarPagos;
	}
	private JScrollPane getScrollPaneCursos() {
		if (scrollPaneCursos == null) {
			scrollPaneCursos = new JScrollPane(getTableCursos());
			scrollPaneCursos.setToolTipText("Seleccione el curso sobre el que quiere registrar la actividad bancaria");
		}
		return scrollPaneCursos;
	}
	

	private JTable getTableCursos() {
		if (tbCourses == null) {
			tbCourses = new JTable();
			tbCourses.setIntercellSpacing(new Dimension(0, 0));
			tbCourses.setRowMargin(0);
			tbCourses.setRequestFocusEnabled(false);
			tbCourses.setFocusable(false);
			tbCourses.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbCourses.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbCourses.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbCourses.setShowVerticalLines(true);
			tbCourses.setShowHorizontalLines(true);
			tbCourses.setOpaque(false);

			tbCourses.setRowHeight(LookAndFeel.ROW_HEIGHT);

			tbCourses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			try {
				tableModelC = new CursoModel(Curso.listarTodosLosCursos()).getCursoModel(ALL_MINUS_ID);
				
				tbCourses.setModel(tableModelC);
			} catch (BusinessException e) {
				showMessage(e, MessageType.ERROR);
				e.printStackTrace();
			}

			tbCourses.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent event) {

					if (event.getValueIsAdjusting())
						return;

					cursoSeleccionado = new CursoDto();

					try {

						int selectedRow = tbCourses.getSelectedRow();

						if (selectedRow == -1) {
							selectedRow = 0;
						}

						cursoSeleccionado.titulo = tbCourses.getValueAt(selectedRow, 0).toString();

						cursoSeleccionado.fechaInicio = LocalDate
								.parse(tbCourses.getValueAt(selectedRow, 1).toString());
						
						cursoSeleccionado.plazasDisponibles = Integer
								.parseInt(tbCourses.getValueAt(selectedRow, 2).toString());
												
						cursoSeleccionado.fechaApertura = LocalDate
								.parse(tbCourses.getValueAt(selectedRow, 3).toString());
						
						cursoSeleccionado.fechaCierre = LocalDate
								.parse(tbCourses.getValueAt(selectedRow, 4).toString());

						cursoSeleccionado.estado = tbCourses.getValueAt(selectedRow, 5).toString();
						
						cursoSeleccionado.codigoCurso = Integer.parseInt(tbCourses.getValueAt(selectedRow, 6).toString());
						
					} catch (NumberFormatException | ArrayIndexOutOfBoundsException nfe) {
					}

					Curso.setSelectedCourse(cursoSeleccionado);
				}

			});	
		}
		return tbCourses;
	}
	private JScrollPane getScrollPaneTransferencias() {
		if (scrollPaneTransferencias == null) {
			scrollPaneTransferencias = new JScrollPane(getTableTransferencias());
		}
		return scrollPaneTransferencias;
	}

	private JTable getTableTransferencias() {
		if (tbTransferencias == null) {
			tbTransferencias = new JTable();
			tbTransferencias.setIntercellSpacing(new Dimension(0, 0));
			tbTransferencias.setShowGrid(false);
			tbTransferencias.setRowMargin(0);
			tbTransferencias.setRequestFocusEnabled(false);
			tbTransferencias.setFocusable(false);
			tbTransferencias.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbTransferencias.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbTransferencias.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbTransferencias.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbTransferencias.setShowVerticalLines(false);
			tbTransferencias.setOpaque(false);
			tbTransferencias.setEnabled(false);
			
			tbTransferencias.setRowHeight(LookAndFeel.ROW_HEIGHT);
			tbTransferencias.setGridColor(new Color(255, 255, 255));

			tbTransferencias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			try {
				tableModel = new InscripcionColegiadoModel(InscripcionColegiado.obtenerTransferencias(cursoSeleccionado.codigoCurso)).getCursoModel(InscripcionColegiadoModel.TRANSFERENCIAS_RECIBIDAS);

				tbTransferencias.setModel(tableModel);
			} catch (BusinessException e) {
				showMessage(e, MessageType.ERROR);
				e.printStackTrace();
			}

			tbTransferencias.getSelectionModel().addListSelectionListener(new ListSelectionListener() {


				public void valueChanged(ListSelectionEvent event) {

					if (event.getValueIsAdjusting())
						return;

					InscripcionColegiadoDto transferencia = new InscripcionColegiadoDto();

					try {

						int selectedRow = tbTransferencias.getSelectedRow();

						if (selectedRow == -1) {
							selectedRow = 0;
						}

						transferencia.colegiado.DNI = tbTransferencias.getValueAt(selectedRow, 0).toString();

						transferencia.colegiado.nombre = tbTransferencias.getValueAt(selectedRow, 1).toString();
						
						transferencia.colegiado.apellidos = tbTransferencias.getValueAt(selectedRow, 2).toString();
						
						transferencia.cantidadPagada = Double
								.parseDouble(tbTransferencias.getValueAt(selectedRow, 3).toString());
												
						transferencia.fechaTransferencia = LocalDate
												.parse(tbTransferencias.getValueAt(selectedRow, 4).toString());
						
						transferencia.codigoTransferencia = tbTransferencias.getValueAt(selectedRow, 5).toString();
						
					} catch (NumberFormatException | ArrayIndexOutOfBoundsException nfe) {
					}

				}

			});	
		}
		return tbTransferencias;
	}
	private JPanel getPnTransferenciasProcesadas() {
		if (pnTransferenciasProcesadas == null) {
			pnTransferenciasProcesadas = new JPanel();
			pnTransferenciasProcesadas.setLayout(new BorderLayout(0, 0));
			pnTransferenciasProcesadas.add(getPnTransferenciasProcesadasNorte(), BorderLayout.NORTH);
			pnTransferenciasProcesadas.add(getPnTransferenciasProcesadasCentro(), BorderLayout.CENTER);
			pnTransferenciasProcesadas.add(getPnTransferenciasProcesadasSur(), BorderLayout.SOUTH);
		}
		return pnTransferenciasProcesadas;
	}
	private JPanel getPnTransferenciasProcesadasNorte() {
		if (pnTransferenciasProcesadasNorte == null) {
			pnTransferenciasProcesadasNorte = new JPanel();
			pnTransferenciasProcesadasNorte.add(getLblNewLabelProcesarTransferencias());
		}
		return pnTransferenciasProcesadasNorte;
	}
	private JPanel getPnTransferenciasProcesadasCentro() {
		if (pnTransferenciasProcesadasCentro == null) {
			pnTransferenciasProcesadasCentro = new JPanel();
			pnTransferenciasProcesadasCentro.setToolTipText("Contiene los resultados de procesar las transferencias del COIIPA del curso seleccionado");
			pnTransferenciasProcesadasCentro.setBorder(new TitledBorder(null, "Lista de inscritos en el curso por transferencia", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			pnTransferenciasProcesadasCentro.setLayout(new GridLayout(1, 0, 0, 0));
		}
		return pnTransferenciasProcesadasCentro;
	}
	private JPanel getPnTransferenciasProcesadasSur() {
		if (pnTransferenciasProcesadasSur == null) {
			pnTransferenciasProcesadasSur = new JPanel();
			pnTransferenciasProcesadasSur.add(getBtnNewButtonVolverAtrasProcesarTransferencias());
		}
		return pnTransferenciasProcesadasSur;
	}
	private JLabel getLblNewLabelProcesarTransferencias() {
		if (lblNewLabelProcesarTransferencias == null) {
			lblNewLabelProcesarTransferencias = new JLabel("Lista de movimientos en la cuenta bancaria de un curso del COIIPA");
			lblNewLabelProcesarTransferencias.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelProcesarTransferencias.setFont(LookAndFeel.HEADING_1_FONT);
		}
		return lblNewLabelProcesarTransferencias;
	}
	private JButton getBtnNewButtonVolverAtrasProcesarTransferencias() {
		if (btnProcesarTransferencias == null) {
			btnProcesarTransferencias = new DefaultButton("Volver a Inicio", "ventana", "VolverAInicio", 'v',
					ButtonColor.CANCEL);
			btnProcesarTransferencias.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (confirmarVolverPrinicipio()) {
						mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
					}
				}
			});
			btnProcesarTransferencias.setText("Salir");
			btnProcesarTransferencias.setToolTipText("Pulse para volver a la pantalla principal");
			btnProcesarTransferencias.setMnemonic('S');
		}
		return btnProcesarTransferencias;
	}
	private JScrollPane getScrollPaneProcesar() {
		if (scrollPaneProcesar == null) {
			scrollPaneProcesar = new JScrollPane(getTableProcesarTransferencias());
		}
		return scrollPaneProcesar;
	}

	private JTable getTableProcesarTransferencias() {
		if (tbProcesarTransferencias == null) {
			tbProcesarTransferencias = new JTable();
			tbProcesarTransferencias.setIntercellSpacing(new Dimension(0, 0));
			tbProcesarTransferencias.setShowGrid(false);
			tbProcesarTransferencias.setRowMargin(0);
			tbProcesarTransferencias.setRequestFocusEnabled(false);
			tbProcesarTransferencias.setFocusable(false);
			tbProcesarTransferencias.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbProcesarTransferencias.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbProcesarTransferencias.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbProcesarTransferencias.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbProcesarTransferencias.setShowVerticalLines(false);
			tbProcesarTransferencias.setOpaque(false);
			tbProcesarTransferencias.setEnabled(false);
			
			tbProcesarTransferencias.setRowHeight(LookAndFeel.ROW_HEIGHT);
			tbProcesarTransferencias.setGridColor(new Color(255, 255, 255));

			tbProcesarTransferencias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			try {
				tableModelP = new InscripcionColegiadoModel(InscripcionColegiado.obtenerTransferenciasProcesadas(cursoSeleccionado.codigoCurso)).getCursoModel(InscripcionColegiadoModel.TRANSFERENCIAS_PROCESADAS);

				tbProcesarTransferencias.setModel(tableModelP);
			} catch (BusinessException e) {
				showMessage(e, MessageType.ERROR);
				e.printStackTrace();
			}

			tbProcesarTransferencias.getSelectionModel().addListSelectionListener(new ListSelectionListener() {


				public void valueChanged(ListSelectionEvent event) {

					if (event.getValueIsAdjusting())
						return;

					InscripcionColegiadoDto transferencia = new InscripcionColegiadoDto();

					try {

						int selectedRow = tbProcesarTransferencias.getSelectedRow();

						if (selectedRow == -1) {
							selectedRow = 0;
						}

						transferencia.colegiado.DNI = tbProcesarTransferencias.getValueAt(selectedRow, 0).toString();

						transferencia.colegiado.nombre = tbProcesarTransferencias.getValueAt(selectedRow, 1).toString();
						
						transferencia.colegiado.apellidos = tbProcesarTransferencias.getValueAt(selectedRow, 2).toString();
						
						transferencia.estado = tbProcesarTransferencias.getValueAt(selectedRow, 3).toString();
						
						transferencia.cantidadPagada = Double
								.parseDouble(tbProcesarTransferencias.getValueAt(selectedRow, 4).toString());
												
						transferencia.precio = Double
								.parseDouble(tbProcesarTransferencias.getValueAt(selectedRow, 5).toString());
						
						transferencia.incidencias = tbProcesarTransferencias.getValueAt(selectedRow, 6).toString();
						
						transferencia.devolver = tbProcesarTransferencias.getValueAt(selectedRow, 7).toString(); 
						
					} catch (NumberFormatException | ArrayIndexOutOfBoundsException nfe) {
					}

				}

			});	
		}
		return tbProcesarTransferencias;
	}
}
