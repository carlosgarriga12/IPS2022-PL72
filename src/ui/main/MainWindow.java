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
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.toedter.calendar.JCalendar;

import business.BusinessException;
import business.InscripcionColegiado.InscripcionColegiado;
import business.SolicitudServicios.SolicitudServicios;
import business.colegiado.Colegiado;
import business.colegiado.Perito;
import business.curso.Curso;
import business.curso.listaEspera.ListaEsperaCurso;
import business.inscripcion.InscripcionCursoFormativo;
import business.util.CSVLoteSolicitudesColegiacion;
import business.util.DateUtils;
import persistence.DtoAssembler;
import persistence.Colegiado_Inscripcion.Colegiado_Inscripcion;
import persistence.InscripcionColegiado.InscripcionColegiadoDto;
import persistence.SolicitudServicios.SolicitudServiciosDto;
import persistence.colegiado.ColegiadoCrud;
import persistence.colegiado.ColegiadoDto;
import persistence.curso.CursoCRUD;
import persistence.curso.CursoDto;
import persistence.curso.Precio_Colectivos;
import persistence.curso.profesorado.ProfesorCRUD;
import persistence.curso.profesorado.ProfesorDto;
import persistence.curso.sesion.SesionCRUD;
import persistence.curso.sesion.SesionDto;
import persistence.jdbc.PersistenceException;
import persistence.perito.PeritoCRUD;
import persistence.recibo.ReciboCRUD;
import persistence.solicitudVisados.SolicitudVisadoDto;
import persistence.solicitudVisados.SolicitudVisadosCRUD;
import ui.components.LookAndFeel;
import ui.components.buttons.ButtonColor;
import ui.components.buttons.DefaultButton;
import ui.components.messages.DefaultMessage;
import ui.components.messages.MessageType;
import ui.components.placeholder.TextPlaceHolderCustom;
import ui.model.ColegiadoModel;
import ui.model.CursoModel;
import ui.model.InscripcionColegiadoModel;
import ui.model.ListaEsperaCursoModel;
import ui.model.ModeloCurso;
import ui.model.ModeloInscripcion;
import ui.model.ModeloPeritos;
import ui.model.ModeloPeritosDisponiblesParaVisado;
import ui.model.ModeloSolicitudServicios;
import ui.model.ModeloSolicitudesVisados;
import ui.model.combo.ColectivoComboModel;
import ui.util.TimeFormatter;

import javax.swing.JTextArea;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.BoxLayout;

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
	private static final String LISTADO_INSCRIPCIONES_PANEL_NAME = "listadoInscripcionesPanel";
	private static final String CONSULTAR_TITULACION_SOLICITANTE_PANEL_NAME = "consultarTitulacionSolicitantePanel";
	private static final String INSCRIPCION_CURSO_TRANSFERENCIAS = "incripcionColegiadoTransferencias";
	private static final String INSCRIPCION_CURSO_TRANSFERENCIAS_PROCESADAS = "inscripcionColegiadoTransferenciasProcesadas";
	protected static final String RECEPCION_LOTES_COLEGIACION_PANEL = "recepcionLotesColegiacion";
	private static final String SOLICITUD_SERVICIOS = "SolicitudServicios";
	private static final String ASIGNACION_SOLICITUD_SERVICIOS = "AsignacionSolicitudServicios";
	private static final String LISTAS_PROFESIONALES = "ListasProfesionales";
	private static final String CANCELAR_CURSO = "cancelaCursoCOIIPA";
	protected static final String CANCELAR_INSCRIPCION = "cancelaInscripcionCurso";
	protected static final String CREAR_SOLICITUD_VISADOS = "crearSolicitudVisados";
	protected static final String ASIGNACION_VISADOS = "asignacionVisados";

	private static final int ALL_MINUS_ID = 1;

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

	private JPanel pnInscripcion_old;
	private JScrollPane spCursosInscripcionCurso;
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
	private JTextField textFieldTitulaciones;
	private JLabel lblTitulacinSegunSus;
	private JTextField txCursoDNI;

	private JPanel pnHomeNorth;
	private JPanel pnHomeCenter;
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
	private DefaultButton btHomeSecretariaAddCurso;
	private DefaultButton btHomeSecretariaListadoInscripciones;
	private JPanel pnListadoInscripciones;
	private JPanel pnConsultarTitulacionSolicitante;
	private JPanel pnCrearCurso;
	private JPanel pnCrearCursoTitulo;
	private JLabel lblCrearCurso;
	private JPanel pnHorarios;
	private JPanel pnCrearCursoButtons;
	private DefaultButton btnCrearCursoCancelar;
	private JButton btnCrearCursoCrear;
	private JPanel pnCrearCursoCenterContainer;
	private JScrollPane spnCursos;
	private JLabel lbAlertaListadoInscripciones;

	private JLabel lbTotalIngresos;
	private JLabel lbTotalIngresosText;

	private JPanel pnConsultarTitulacionNorth;
	private JPanel pnConsultarTitulacionCenter;
	private JPanel pnConsultarTitulacionSouth;
	private JLabel lbConsultarTitulacionTitle;
	private JScrollPane spListadoAltaSolicitudesColegiado;
	private JPanel pnConsultarTitulacionSouthButtons;
	private JButton btConsultarSolicitudColegiadoVolver;
	private JPanel pnConsultarColegiadoDatosColegiadoSeleccionado;
	private JLabel lbColegiadoSeleccionadoSolicitudRespuesta;
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

	private JPanel pnNumeroTarjetaDatosColegiadoText;
	private JTextField textFieldNumeroTarjetaColegiado;
	private JPanel pnNumeroTarjetaValidarTarjeta;
	private JPanel pnPagoTranferencia;
	private JPanel pnDatosColPoblacionText;
	private JPanel pnDatosColTelefonoText;
	private JPanel pnDatosColTitulacionText;
	private JPanel pnDatosColCentoText;
	private JPanel pnDatosColAnnioText;
	private JLabel lbNumeroSolicitudesColegiado;
	private JPanel pnNumeroSolicitudesColegiado;
	private JPanel pnListadoAltaSolicitanteRefrescarListaBotonContainer;
	private DefaultButton btRecepcionarLoteSolicitudesPendientesColegiado;
	private JPanel pnColectivos;
	private JTable tbCursosAbiertosInscripcionCurso;
	List<CursoDto> cursosAbiertosPnInscripcion;
	private JTable tbCursosInscripciones;
	private JTable tbInscripciones;
	private JPanel pnColectivosCenter;
	private JPanel pnColectivosEliminar;
	private JComboBox<String> cbColectivosEliminar;
	private JButton btnEliminarColectivo;
	private JPanel pnColectivosAnadir;
	private JComboBox<String> cbColectivosAnadir;
	private JButton btnAnadirColectivo;
	private Precio_Colectivos colectivos_Precios;
	private JPanel pnPrecioAnadirColectivo;
	private JTextField txPrecioAnadirColectivo;
	private JLabel lbPrecioAnadirColectivo;
	private JComboBox<String> cbSeleccionarColectivo;
	private JPanel pnTituloCurso;
	private JLabel lblTituloCurso;
	private JTextField txtTituloCurso;
	private JPanel pnProfesores;
	private JPanel pnSpinnerProfesores;
	private JLabel lblProfesores;
	private JComboBox<String> cbProfesores;
	private JPanel pnSesionesCurso;
	private JLabel lblFechaSesion;
	private JTextField txtFechaSesion;
	private JLabel lblHoraInicio;
	private JTextField txtHoraInicio;
	private JLabel lblHoraFin;
	private JTextField txtHoraFin;
	private JButton btnAnadirSesion;

	List<SesionDto> fechasCurso = new ArrayList<>();
	private JScrollPane spListaSesiones;
	private JList<SesionDto> listSesiones;

	private DefaultListModel<SesionDto> modeloSesiones = null;
	private JButton btnBorrarSesion;
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

	private JLabel lbTitulacionAltaInfo;
	private JPanel pnRecepcionLoteResultado;
	private JPanel pnRecepcionLoteNorth;
	private JLabel lbTituloRecepcionLote;
	private JPanel pnRecepcionLoteCenter;
	private JPanel pnRecepcionLoteSouth;
	private JButton btVolverHomeRecepcionLote;
	private JScrollPane spRecepcionLoteTablaDatos;
	private JTable tbListadoNuevosColegiadosRecepcionLote;
	private JPanel pnColectivosCuotasSeleccionadas;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;

	private JPanel pnSolicitudServicios;

	private JPanel pnTituloSolicitud;

	private JLabel lbTituloSolicitud;

	private JPanel pnCenterSolicitud;

	private JPanel pnSolicitudDni;

	private JLabel lbSolicitudDni;

	private JTextField txSolicitudDni;

	private JPanel pnSolicitudCorreo;

	private JLabel lbSolicitudCorreo;

	private JTextField txSolicitudCorreo;

	private JPanel pnSolicitudDescripcion;

	private JLabel lbSolicitudDescripcion;

	private JScrollPane scrollPane;

	private JTextField txSolicitudDescripcion;

	private JPanel pnSolicitudUrgente;

	private JLabel lbSolicitudUrgente;

	private JPanel pnSolicitudRadioButton;

	private JRadioButton rbSolicitudNormal;

	private final ButtonGroup buttonGroup = new ButtonGroup();

	private JRadioButton rbSolicitudUrgente;

	private JPanel pnSouthSolicitud;

	private DefaultButton btnRegistrarSolicitud;

	private DefaultButton btnInscripcionToInicio_1_1;

	private JPanel pnAsignacionSolicitudesServicios;

	private JPanel pnAsignacionSolicitudesTitulo;

	private JLabel lbAsignacionSolicitudTitulo;

	private JPanel pnAsignacionSolicitudesCenter;

	private JPanel pnAsignacionSolicitudes;

	private JLabel lbAsignacionSolicitudes;

	private JScrollPane spAsignacionSolicitudes;

	private JTable tbAsignacionSolicitudes;

	private JPanel pnAsignacionSolicitudesPeritos;

	private JLabel lbAsignacionSolicitudesPeritos;

	private JScrollPane spAsignacionSolicitudesPeritos;

	private JTable tbAsignacionSolicitudesPeritos;

	private JPanel pnAsignacionSolicitudesButton;

	private DefaultButton btAsignarSolicitud;

	private DefaultButton btnInscripcionToInicio_1;

	private JPanel pnListaProfesionalesPeritos;
	private JPanel pnBotones;
	private JButton btnAddPerito;
	private JButton btnRenovarPerito;
	private JPanel pnPedirDNI;
	private JLabel lblPedirDNI;
	private JTextField txtDNIPerito;
	private JPanel pnListaPeritos;
	private JLabel lblListaPeritos;
	private JPanel pnListaPeritosProfesionales;
	private JScrollPane spListaPeritos;
	private JTable tbListadoPeritosProfesionales;

	private DefaultButton btHomeSecretariaCancelarCursos;

	private DefaultButton btCancelarInscripcionCurso;

	private TableModel tableModelE;

	private RegisterWindow frame;

	// dni del colegiado
	private String dniColegiado;

	private ArrayList<SolicitudServiciosDto> listaSolicitudesServicios;
	private ArrayList<ColegiadoDto> listaPeritosOrdenada;
	private DefaultButton btHomeSolicitudServicios;
	private DefaultButton btHomeAsignacionSolicitudServicios;
	private DefaultButton btListasProfesionales;

	private JPanel pnCancelarCursoCOIIPA;
	private JPanel pnCancelarCursoCOIIPACentro;
	private JLabel lblCancelarCursoCOIIPATitulo;
	private JPanel pnCancelarCursoCOIIPACursos;
	private JPanel pnCancelarCursoCOIIPAInscripciones;
	private JPanel panelCancelarCursoCOIIPATabla;
	private JLabel lblNewLabelCursosDisponibles;
	private JScrollPane spListadoCursos;
	private JTable tbListadoCursos;

	private TableModel tableModelL;
	private JLabel lblNewLabelCancelarCursosCOIIPAInscripciones;
	private JScrollPane spInscripcionesCanceladas;

	private JTable tbListadoTransferenciasCanceladas;

	private JPanel pnCancelarCursosCOIIPACancelaciones;
	private JPanel panelCancelarCursoCOIIPACancelar;
	private JPanel pnCancelarCursosCOIIPAVolver;
	private DefaultButton btnCancelarCursoCOIIPAVolver;
	private JPanel pnCancelarInscripcionCurso;
	private JPanel pnCancelarInscripcionCursoCentro;
	private JPanel pnCancelarInscripcionCursoVolver;
	private JLabel lblCancelarInscipcionCursoTitulo;
	private JButton btnCancelarInscripcionCursoVolver;
	private JPanel pnCancelarInscripcionCursoCursosInscritos;
	private JPanel pnCancelarInscripcionCursoInscripciones;
	private JPanel pnCancelarInscripcionCursoCursosInscritosTabla;
	private JPanel pnCancelarInscripcionCursoCursosInscritosCancelar;
	private JLabel lblNewLabelCursosInscritos;
	private JLabel lblNewLabelCancelarInscripciones;
	private JPanel pnCancelarInscripcionCursoInscripcionesCanceladas;
	private JScrollPane scrollPaneInscripcion;

	private DefaultButton dfltbtnCancelarCurso;
	private DefaultButton btnNewButtonInscripcionCancelada;
	private JScrollPane scrollPaneCursosInscritos;

	private JTable tbListadoCursosInscrito;

	private TableModel tableModelF;
	private JTable tbInscripcionCanceladaCurso;

	private JPanel pnInscripcionCurso;
	private JPanel pnInscripcionCursoNorth;
	private JPanel pnInscripcionCursoCenter;
	private JPanel pnInscripcionCursoSouth;
	private JPanel pnInscripcionCursoTitulo;
	private JPanel pnInscripcionCursoDatosColegiado;
	private JLabel lbInscripcionCursoTitulo;
	private JPanel pnInscripcionCursoSouthButtons;
	private JPanel pnInscripcionCursoSouthMessage;
	private JButton btVolverInicioInscripcionCurso;
	private JButton btInscrirseInscripcionCurso;
	private JLabel lbInscripcionCursoMensaje;
	private JPanel pnInscripcionCursoCursosAbiertos;
	private JPanel pnInscripcionCursoListaEspera;
	private JLabel lbTablaCursosAbiertosInscripcionCursoTitulo;
	private JScrollPane spCursosAbiertosInscripcionCurso;
	private JLabel lbTablaListaEsperaInscripcionCursoTitulo;
	private JScrollPane spListaEsperaCursoInscripcionCurso;
	private JTable tbListaEsperaCursoSeleccionadoInscripcionCurso;
	private JPanel pnInscripcionCursoDatosColegiadoColectivo;
	private JPanel pnInscripcionCursoDatosColegiadoDni;
	private JPanel pnInscripcionCursoDatosColegiadoButtons;
	private JLabel lbInscripcionCursoSeleccionarColectivoTitulo;
	private JComboBox<String> cbSeleccionarColectivoInscripcionCurso;
	private JButton btMostrarCursosAbiertosInscripcionCurso;
	private JLabel lbInscripcionCursoDniColegiadoTitulo;
	private JTextField txDniColegiadoInscripcionCurso;
	private DefaultButton btSolicitudVisados;
	private JPanel pnSolicitudVisados;
	private JPanel pnNorthSolicitudVisados;
	private JPanel pnTituloCrearSolicitudVisado;
	private JPanel pnIntroducirDNIdePeritoParaVisado;
	private JLabel lblTituloCrearSolicitudVisado;
	private JLabel lblIntroduzcaSuDniParaVisado;
	private JTextField txtIntroduzcaSuDniParaVisado;
	private JPanel pnCenterSolicitudVisados;
	private JPanel pnSouthSolicitudVisados;
	private JButton btnEnviarSolicitudVisado;
	private JTextArea txtDescripcionVisado;
	private JLabel lblDescripcion;
	private JButton btnComprobarDNI;
	private JLabel lblTextoInfo;
	private JButton btnVisadosVolverAlInicio;
	private DefaultButton btHomeAsignarVisados;
	private JPanel pnAsignarVisados;
	private JPanel pnNorthAsignarVisados;
	private JLabel lblAsignacionDeVisados;
	private JPanel pnSouthAsignarVisados;
	private JButton btnVolverAlInicio;
	private JPanel pnCenterAsignarVisados;
	private JPanel pnVisadosARevisar;
	private JPanel pnPeritosDisponibles;
	private JLabel lblSolicitudesDeVisado;
	private JLabel lblPeritosDisponibles;
	private JScrollPane spVisados;
	private JTable tbVisados;
	private JButton btnAsignarVisado;
	private JScrollPane spPeritosDisponibles;
	private JTable tbPeritosDisponibles;
	private JButton btnActualizarVisados;

	private JRadioButton rbSeleccionCursoCancelableNo;
	private JRadioButton rbSeleccionCursoCancelableSi;
	private JLabel lbSeleccionCursoCancelable;
	
	private JPanel pnSeleccionCursoCancelableRadioButtons;
	private JPanel pnSeleccionCursoCancelable;
	private JPanel pnSeleccionCursoPorcentajeDevolucion;
	private JPanel pnColectivosSouth;

	private ButtonGroup buttonGroupCursoCancelable;
	private JLabel lbSeleccionCursoCancelablePorcentajeDevolucion;
	private JPanel pnSeleccionCursoCancelableRadios;
	private JPanel pnTxPorcentajeDevolucionCursoCancelableWrapper;
	private JLabel lbSimboloPorcentaje;
	private JSpinner spPorcentajeDevolucionCursoCancelable;

	private JPanel pnRecepcionLoteSolicitudesTablaWrapper;

	private JLabel lbTTituloRecepcionLoteSolicitudesTabla;

	private JScrollPane spRecepcionLoteSolicitudes;

	private JTable tbListadoRecepcionSolicitudes;
	private JPanel pnListadoAltaSolicitanteRecepcionLoteBotonesWrapper;

	public MainWindow() {
		initLookAndFeel();

		cursoSeleccionado = new CursoDto();

		// Configuraciones globales del programa
		buttonGroupCursoCancelable = new ButtonGroup();

		mainPanel = new JPanel();
		mainPanel.setName("");
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		mainCardLayout = new CardLayout(0, 0);
		mainPanel.setLayout(mainCardLayout);

		/*
		 * Card Panel - Paneles con las funcionalidades de la aplicacion. Evitar abrir
		 * JFrames nuevos si no es estrictamente necesario.
		 */

		mainPanel.add(getPnHome(), HOME_PANEL_NAME);
		mainPanel.add(getPnSolicitudColegiado(), SOLICITUD_COLEGIADO_PANEL_NAME);
		mainPanel.add(getPnAbrirInscripcionesCurso(), APERTURA_INSCRIPCIONES_PANEL_NAME);
		mainPanel.add(getPnInscripcion_old(), "inscripcion_cursos_OLD");
		mainPanel.add(getPnPagarInscripcionColegiado(), PAGAR_INSCRIPCION_CURSO_PANEL_NAME);
		mainPanel.add(getPnListadoInscripciones(), LISTADO_INSCRIPCIONES_PANEL_NAME);
		mainPanel.add(getPnConsultarTitulacionSolicitante(), CONSULTAR_TITULACION_SOLICITANTE_PANEL_NAME);
		mainPanel.add(getPnCrearCurso(), ADD_CURSO_PANEL_NAME);
		mainPanel.add(getPnTransferencias(), INSCRIPCION_CURSO_TRANSFERENCIAS);
		mainPanel.add(getPnTransferenciasProcesadas(), INSCRIPCION_CURSO_TRANSFERENCIAS_PROCESADAS);
		mainPanel.add(getPnRecepcionLoteResultado(), RECEPCION_LOTES_COLEGIACION_PANEL);
		mainPanel.add(getPnSolicitudServicios(), SOLICITUD_SERVICIOS);
		mainPanel.add(getPnAsignacionSolicitudesServicios(), ASIGNACION_SOLICITUD_SERVICIOS);
		mainPanel.add(getPnListaProfesionalesPeritos(), LISTAS_PROFESIONALES);
		mainPanel.add(getPnCancelarCursoCOIIPA(), CANCELAR_CURSO);
		mainPanel.add(getPnCancelarInscripcionCurso(), CANCELAR_INSCRIPCION);
		mainPanel.add(getPnInscripcionCurso(), INSCRIPCION_CURSO_PANEL_NAME);
		mainPanel.add(getPnSolicitudVisados(), CREAR_SOLICITUD_VISADOS);
		mainPanel.add(getPnAsignarVisados(), ASIGNACION_VISADOS);
		

		frame = new RegisterWindow(this);
		frame.setVisible(false);
		frame.setLocationRelativeTo(null);
		// Centrar la ventana
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		inicializarCampos();
	}

	/**
	 * Inicializa todos los aspectos relacionados con el Look & Feel de la
	 * aplicación.
	 * 
	 * @see https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html#available
	 */
	private void initLookAndFeel() {
		setTitle("COIIPA : Gestión de servicios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setFont(LookAndFeel.PRIMARY_FONT);
		setBounds(100, 100, 1126, 804);

		String lookAndFeel = null;

		if (LookAndFeel.DEFAULT_LOOK_AND_FEEL != null) {
			if (LookAndFeel.DEFAULT_LOOK_AND_FEEL.equals(LookAndFeel.AVAILABLE_LOOK_AND_FEELS[0])) {
				lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			}

			// System
			else if (LookAndFeel.DEFAULT_LOOK_AND_FEEL.equals(LookAndFeel.AVAILABLE_LOOK_AND_FEELS[1])) {
				lookAndFeel = UIManager.getSystemLookAndFeelClassName();
			}

			// Motif
			else if (LookAndFeel.DEFAULT_LOOK_AND_FEEL.equals(LookAndFeel.AVAILABLE_LOOK_AND_FEELS[2])) {
				lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
			}

			// Gtk
			else if (LookAndFeel.DEFAULT_LOOK_AND_FEEL.equals(LookAndFeel.AVAILABLE_LOOK_AND_FEELS[3])) {
				lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
			}

			// Otro
			else {
				System.err.println("Unexpected value of LOOKANDFEEL specified: " + LookAndFeel.DEFAULT_LOOK_AND_FEEL);
				lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			}

			// Metal
			try {

				UIManager.setLookAndFeel(lookAndFeel);

				// Metal
				if (LookAndFeel.DEFAULT_LOOK_AND_FEEL.equals(LookAndFeel.AVAILABLE_LOOK_AND_FEELS[0])) {

					if (LookAndFeel.METAL_THEME.equals(LookAndFeel.AVAILABLE_METAL_THEMES[0])) {
						MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());

					} else if (LookAndFeel.METAL_THEME.equals(LookAndFeel.AVAILABLE_METAL_THEMES[1])) {
						MetalLookAndFeel.setCurrentTheme(new OceanTheme());

					} else {
						// Theme personalizado.
					}

					UIManager.setLookAndFeel(new MetalLookAndFeel());
				}

			}

			catch (ClassNotFoundException e) {
				System.err.println("Couldn't find class for specified look and feel:" + lookAndFeel);
				System.err.println("Did you include the L&F library in the class path?");
				System.err.println("Using the default look and feel.");
			}

			catch (UnsupportedLookAndFeelException e) {
				System.err.println("Can't use the specified look and feel (" + lookAndFeel + ") on this platform.");
				System.err.println("Using the default look and feel.");
			}

			catch (Exception e) {
				System.err.println("Couldn't get specified look and feel (" + lookAndFeel + "), for some reason.");
				System.err.println("Using the default look and feel.");
				e.printStackTrace();
			}
		}

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

			try {
				TableModel tableModel = new CursoModel(Curso.listarCursosPlanificados()).getCursosPlanificadosModel();

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

//						CursoDto selectedCourse = Curso.getSelectedCourse();

						if (cursoSeleccionado == null) {
							throw new BusinessException("Por favor, seleccione un curso.");
						}
						int input = JOptionPane.showConfirmDialog(null,
								"<html><p>Â¿Confirma que desea abrir las inscripciones para el curso <b>"
										+ cursoSeleccionado.titulo + "</b> ?</p></html>");

						if (input == JOptionPane.OK_OPTION) {
							// Si el curso estÃ¡ abierto, mostrar mensaje de error

							CursoDto newCurso = new CursoDto();
							newCurso.codigoCurso = cursoSeleccionado.codigoCurso;
							newCurso.titulo = cursoSeleccionado.titulo;
							newCurso.fechaInicio = cursoSeleccionado.fechaInicio;
							newCurso.fechaApertura = fechaApertura;
							newCurso.fechaCierre = fechaCierre;
							newCurso.precio = cursoSeleccionado.precio;
							newCurso.plazasDisponibles = Integer.parseInt(plazas);

							InscripcionCursoFormativo.abrirCursoFormacion(newCurso, cursoSeleccionado);

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
		TableModel tableModel = new CursoModel(Curso.listarCursosPlanificados()).getCursosPlanificadosModel();
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
			JOptionPane.showMessageDialog(this, e.getMessage());
//			JOptionPane.showMessageDialog(this,
//					"Por favor, revise que no haya introducido un DNI que no es suyo, este DNI ya ha sido registrado",
//					"DNI invÃ¡lido", JOptionPane.INFORMATION_MESSAGE);
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
		dto.titulacion = DtoAssembler.parseTitulacionesColegiado(getTextFieldTitulacion().getText());

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

	private boolean confirmarVolverPrincipio() {
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
					if (confirmarVolverPrincipio()) {
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
			TextPlaceHolderCustom.setPlaceholder("ES66123443215567", textFieldNumeroCuenta);
			textFieldNumeroCuenta.setToolTipText(
					"Registre su numero de cuenta bancaria. El número de cuenta bancaria está formado por 16 caracteres alfanuméricos");
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
			textFieldCentro.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			textFieldCentro.setEnabled(false);
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
			GridBagLayout gbl_pnDatosTituloColegiadoLabel = new GridBagLayout();
			gbl_pnDatosTituloColegiadoLabel.columnWidths = new int[] { 269, 0 };
			gbl_pnDatosTituloColegiadoLabel.rowHeights = new int[] { 130, 100, 0 };
			gbl_pnDatosTituloColegiadoLabel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
			gbl_pnDatosTituloColegiadoLabel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
			pnDatosTituloColegiadoLabel.setLayout(gbl_pnDatosTituloColegiadoLabel);
			GridBagConstraints gbc_lblTitulacinSegunSus = new GridBagConstraints();
			gbc_lblTitulacinSegunSus.fill = GridBagConstraints.BOTH;
			gbc_lblTitulacinSegunSus.insets = new Insets(0, 0, 5, 0);
			gbc_lblTitulacinSegunSus.gridx = 0;
			gbc_lblTitulacinSegunSus.gridy = 0;
			pnDatosTituloColegiadoLabel.add(getLblTitulacinSegunSus(), gbc_lblTitulacinSegunSus);
			GridBagConstraints gbc_lbTitulacionAltaInfo = new GridBagConstraints();
			gbc_lbTitulacionAltaInfo.fill = GridBagConstraints.BOTH;
			gbc_lbTitulacionAltaInfo.gridx = 0;
			gbc_lbTitulacionAltaInfo.gridy = 1;
			pnDatosTituloColegiadoLabel.add(getLbTitulacionAltaInfo(), gbc_lbTitulacionAltaInfo);
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
		if (textFieldTitulaciones == null) {
			textFieldTitulaciones = new JTextField();
			textFieldTitulaciones.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && textFieldTitulaciones.getText().length() == 1
							|| textFieldTitulaciones.getText() == "") {
						textFieldCentro.setEnabled(false);
					}

					if (String.valueOf(e.getKeyChar()).matches(DtoAssembler.SPECIAL_CHARACTERS_REGEX)) {
						e.consume();
						JOptionPane.showMessageDialog(pnDatosColTitulacionText,
								"Las titulaciones tienen que ir separadas por una coma",
								"Alta Colegiado: Error en datos introducidos", JOptionPane.ERROR_MESSAGE);
					}

					if (textFieldTitulaciones.getText().length() > 0) {
						textFieldCentro.setEnabled(true);
					} else {
						textFieldCentro.setEnabled(false);
					}
				}
			});
			textFieldTitulaciones.setToolTipText("Teclee la titulación");
			TextPlaceHolderCustom.setPlaceholder("Licenciado en Informática,Medicina,Ingeniería Electrónica",
					textFieldTitulaciones);
			textFieldTitulaciones.setHorizontalAlignment(SwingConstants.LEFT);
			textFieldTitulaciones.setColumns(10);
		}
		return textFieldTitulaciones;
	}

	private JLabel getLblTitulacinSegunSus() {
		if (lblTitulacinSegunSus == null) {
			lblTitulacinSegunSus = new JLabel("Titulación segun sus estudios:");
			lblTitulacinSegunSus.setVerticalAlignment(SwingConstants.BOTTOM);
			lblTitulacinSegunSus.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitulacinSegunSus.setFont(LookAndFeel.PRIMARY_FONT);
			lblTitulacinSegunSus.setDisplayedMnemonic('I');
			lblTitulacinSegunSus.setLabelFor(getTextFieldTitulacion());
		}
		return lblTitulacinSegunSus;
	}

	private JPanel getPnInscripcion_old() {
		if (pnInscripcion_old == null) {
			pnInscripcion_old = new JPanel();
			pnInscripcion_old.setVisible(false);
			pnInscripcion_old.setFont(LookAndFeel.PRIMARY_FONT);
			pnInscripcion_old.setLayout(null);
			pnInscripcion_old.add(getSpCursosInscripcionCurso());
			pnInscripcion_old.add(getLbInscripcionSeleccionarCursos());
			pnInscripcion_old.add(getBtnInscribete());
			pnInscripcion_old.add(getLbAlerta());
			pnInscripcion_old.add(getLbConfirmacionInscripcion());
			pnInscripcion_old.add(getBtMostrarCursos());
			pnInscripcion_old.add(getBtnInscripcionToInicio());

			JLabel lbLoginUsername_1 = new JLabel("Introduzca su DNI:");
			lbLoginUsername_1.setHorizontalAlignment(SwingConstants.LEFT);
			lbLoginUsername_1.setFont(new Font("Arial", Font.BOLD, 12));
			lbLoginUsername_1.setBounds(356, 10, 324, 35);
			pnInscripcion_old.add(lbLoginUsername_1);

			txCursoDNI = new JTextField();
			txCursoDNI.setToolTipText("Intro");
			txCursoDNI.setColumns(10);
			txCursoDNI.setBounds(356, 44, 324, 35);
			pnInscripcion_old.add(txCursoDNI);

			getCbSeleccionarColectivo();

			JLabel lblNewLabel = new JLabel("Seleccione el colectivo al que pertenece:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel.setBounds(18, 15, 260, 24);
			pnInscripcion_old.add(lblNewLabel);

			JPanel pnInscripcionCursosCenter = new JPanel();
			pnInscripcionCursosCenter.setBounds(0, 0, 10, 10);
			pnInscripcion_old.add(pnInscripcionCursosCenter);
		}
		return pnInscripcion_old;
	}

	private JComboBox<String> getCbSeleccionarColectivo() {
		if (cbSeleccionarColectivo == null) {
			cbSeleccionarColectivo = new JComboBox<String>();
			cbSeleccionarColectivo.setModel(new DefaultComboBoxModel<String>(
					new String[] { "Colegiado", "Precolegiado", "Estudiante", "Desempleado", "Otros" }));
			cbSeleccionarColectivo.setFont(new Font("Tahoma", Font.PLAIN, 16));
			cbSeleccionarColectivo.setAlignmentX(0.0f);
			cbSeleccionarColectivo.setBounds(18, 46, 137, 26);
			pnInscripcion_old.add(cbSeleccionarColectivo);
		}
		return cbSeleccionarColectivo;
	}

	private JScrollPane getSpCursosInscripcionCurso() {
		if (spCursosInscripcionCurso == null) {
			spCursosInscripcionCurso = new JScrollPane();
			spCursosInscripcionCurso.setBounds(18, 139, 1057, 384);
			// spCursosInscripcionCurso.setViewportView();
		}
		return spCursosInscripcionCurso;
	}

	private JTable getTbCursosAbiertosInscripcionCurso() {
		if (tbCursosAbiertosInscripcionCurso == null) {
			tbCursosAbiertosInscripcionCurso = new JTable();
			tbCursosAbiertosInscripcionCurso.setIntercellSpacing(new Dimension(0, 0));
			tbCursosAbiertosInscripcionCurso.setShowGrid(false);
			tbCursosAbiertosInscripcionCurso.setRowMargin(0);
			tbCursosAbiertosInscripcionCurso.setRequestFocusEnabled(false);
			tbCursosAbiertosInscripcionCurso.setFocusable(false);
			tbCursosAbiertosInscripcionCurso.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbCursosAbiertosInscripcionCurso.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbCursosAbiertosInscripcionCurso.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbCursosAbiertosInscripcionCurso.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
			tbCursosAbiertosInscripcionCurso.setShowVerticalLines(false);
			tbCursosAbiertosInscripcionCurso.setOpaque(false);

			tbCursosAbiertosInscripcionCurso.setRowHeight(LookAndFeel.ROW_HEIGHT);
			tbCursosAbiertosInscripcionCurso.setGridColor(new Color(255, 255, 255));

			tbCursosAbiertosInscripcionCurso.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			TableModel tableModelCursosAbiertosInsCurso = new CursoModel(List.of()).getCursosAbiertosInscripcionCurso();
			tbCursosAbiertosInscripcionCurso.setModel(tableModelCursosAbiertosInsCurso);

			tbCursosAbiertosInscripcionCurso.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent event) {

					if (event.getValueIsAdjusting())
						return;

					try {

						int selectedRow = tbCursosAbiertosInscripcionCurso.getSelectedRow();

						if (selectedRow == -1) {
							selectedRow = 0;
						}

						/* Al seleccionar un curso, se habita el botón de inscripción */
						btInscrirseInscripcionCurso.setEnabled(true);

						cursoSeleccionado.titulo = tbCursosAbiertosInscripcionCurso.getValueAt(selectedRow, 0)
								.toString();

						cursoSeleccionado.plazasDisponibles = Integer
								.parseInt(tbCursosAbiertosInscripcionCurso.getValueAt(selectedRow, 2).toString());

						cursoSeleccionado.codigoCurso = Integer
								.parseInt(tbCursosAbiertosInscripcionCurso.getValueAt(selectedRow, 5).toString());

						try {
							refrescarListaEspera(cursoSeleccionado);

						} catch (BusinessException e) {

							e.printStackTrace();
						}

					} catch (NumberFormatException | ArrayIndexOutOfBoundsException nfe) {
					}

				}

			});

		}
		return tbCursosAbiertosInscripcionCurso;
	}

	private JTable getTbCursosInscripciones() {
		if (tbCursosInscripciones == null) {
			tbCursosInscripciones = new JTable();
			tbCursosInscripciones.setIntercellSpacing(new Dimension(0, 0));
			tbCursosInscripciones.setShowGrid(false);
			tbCursosInscripciones.setRowMargin(0);
			tbCursosInscripciones.setRequestFocusEnabled(false);
			tbCursosInscripciones.setFocusable(false);
			tbCursosInscripciones.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbCursosInscripciones.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbCursosInscripciones.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbCursosInscripciones.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbCursosInscripciones.setShowVerticalLines(false);
			tbCursosInscripciones.setOpaque(false);

			tbCursosInscripciones.setRowHeight(LookAndFeel.ROW_HEIGHT);
			tbCursosInscripciones.setGridColor(new Color(255, 255, 255));

			tbCursosInscripciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		}
		return tbCursosInscripciones;
	}

	private JLabel getLbInscripcionSeleccionarCursos() {
		if (lbInscripcionSeleccionarCursos == null) {
			lbInscripcionSeleccionarCursos = new JLabel("Selecciona un Curso:");
			lbInscripcionSeleccionarCursos.setDisplayedMnemonic('s');
			lbInscripcionSeleccionarCursos.setFont(new Font("Arial", Font.BOLD, 16));
			lbInscripcionSeleccionarCursos.setBounds(18, 70, 361, 35);
		}
		return lbInscripcionSeleccionarCursos;
	}

	private JButton getBtnInscribete() {
		if (btnInscribete == null) {
			btnInscribete = new DefaultButton("Inscribirme", "ventana", "Inscribirme", 'n', ButtonColor.NORMAL);
			btnInscribete.setEnabled(false);
			btnInscribete.setLocation(794, 533);
			btnInscribete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lbConfirmacionInscripcion.setVisible(false);
					lbAlerta.setVisible(false);
					int IndexCursoSeleccionado = getTbCursosAbiertosInscripcionCurso().getSelectedRow();
					CursoDto cursoSeleccionado = IndexCursoSeleccionado == -1 ? null
							: cursosAbiertosPnInscripcion.get(IndexCursoSeleccionado);
					try {
						if (cursoSeleccionado == null) {
							lbAlerta.setText("Para Realizar la Inscripcion es Necesario Seleccionar un Curso");
							lbAlerta.setVisible(true);
						} else {
							if (!InscripcionColegiado.isInscrito(colegiado, cursoSeleccionado)) {
								if (InscripcionCursoFormativo.hayPlazasLibres(cursoSeleccionado)) {

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
						e1.printStackTrace();
					} catch (BusinessException e1) {
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

			lbConfirmacionInscripcion.setBounds(18, 548, 719, 61);

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
							lbAlerta.setText("Para mostrar los Cursos es Necesario un DNI correcto");
							lbAlerta.setVisible(true);

						} else {

							if (!c.TipoColectivo
									.equalsIgnoreCase(String.valueOf(cbSeleccionarColectivo.getSelectedItem()))) {
								lbAlerta.setText(
										"El colectivo seleccionado no se corresponde con el colectivo asociado al DNI");
								lbAlerta.setVisible(true);
								return;
							}
							colegiado = c;

							cursosAbiertosPnInscripcion = InscripcionCursoFormativo.getCursosAbiertos();
							cursosAbiertosPnInscripcion = cursosAbiertosPnInscripcion.stream()
									.filter(curso -> Precio_Colectivos
											.StringToPrecio_Colectivos(curso.CantidadPagarColectivo)
											.containsAlgunColectivo(c.TipoColectivo, "Todos"))
									.collect(Collectors.toList());

							if (cursosAbiertosPnInscripcion.isEmpty()) {
								lbAlerta.setVisible(true);
								lbAlerta.setText(
										"Lo sentimos, No hay cursos Disponibles para el colectivo seleccionado");
								return;
							} else {

								TableModel tableModel = new ModeloCurso(cursosAbiertosPnInscripcion).getCursoModel(true,
										c.TipoColectivo);

								getTbCursosAbiertosInscripcionCurso().setModel(tableModel);

								btnInscribete.setEnabled(true);
								lbAlerta.setVisible(false);
							}
						}
					} catch (BusinessException e1) {
						e1.printStackTrace();
					}
				}
			});
			btMostrarCursos.setBounds(757, 21, 318, 66);

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

					btnInscribete.setEnabled(false);
				}
			});
			btnInscripcionToInicio.setFont(new Font("Tahoma", Font.BOLD, 16));

			btnInscripcionToInicio.setBounds(795, 619, 280, 64);

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
			pnCrearCurso.add(getPnHorarios(), BorderLayout.WEST);
			pnCrearCurso.add(getPnCrearCursoButtons(), BorderLayout.SOUTH);
			pnCrearCurso.add(getPnColectivos(), BorderLayout.CENTER);
			colectivos_Precios = new Precio_Colectivos();
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
			lblCrearCurso.setFont(LookAndFeel.HEADING_1_FONT);
		}
		return lblCrearCurso;
	}

	private JPanel getPnHorarios() {
		if (pnHorarios == null) {
			pnHorarios = new JPanel();
			pnHorarios.setOpaque(false);
			pnHorarios.setBorder(new EmptyBorder(0, 100, 0, 0));
			pnHorarios.setLayout(new BorderLayout(0, 0));
			pnHorarios.add(getPnCrearCursoCenterContainer(), BorderLayout.CENTER);
		}
		return pnHorarios;
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
			btnCrearCursoCancelar.setToolTipText("Haz click aquí para volver a inicio");
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
			btnCrearCursoCrear.setToolTipText("Haz click aquí para crear el curso con los datos indicados");
			btnCrearCursoCrear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					double precio = 0.0;
					if (txtTituloCurso.getText().isEmpty()) {
						JOptionPane.showMessageDialog(pnCrearCurso, "El titulo del curso esta vacio");
						return;
					}

					if (colectivos_Precios.size() == 0) {
						JOptionPane.showMessageDialog(pnCrearCurso,
								"Es necesario anadir precios de curso para algun colectivo");
						return;
					}

					if (fechasCurso.size() == 0) {
						JOptionPane.showMessageDialog(pnCrearCurso, "Es necesario anadir alguna sesinn para el curso");

					}
					if (colectivos_Precios.size() == 0) {
						JOptionPane.showMessageDialog(pnCrearCurso,
								"Es necesario anadir precios de curso para algun colectivo");
						return;
					}

					/*
					 * Si el curso es cancelable, comprobar que el porcentaje a devolver es válido
					 */
					boolean cursoCancelable = rbSeleccionCursoCancelableSi.isSelected();
					double porcentajeDevolucion = 0.0;

					try {

						porcentajeDevolucion = ((Double) spPorcentajeDevolucionCursoCancelable.getValue())
								.doubleValue();

					} catch (NumberFormatException nfe) {

						spPorcentajeDevolucionCursoCancelable.setValue(0);

						JOptionPane.showMessageDialog(pnCrearCurso,
								"Por favor, seleccione un valor entero entre 0 y 100 para el porcentaje a devolver del curso cancelable.",
								"Crear un nuevo curso: Error", JOptionPane.ERROR_MESSAGE);

					}

					if (cursoCancelable && porcentajeDevolucion < 0 || porcentajeDevolucion > 100) {
						JOptionPane.showMessageDialog(pnCrearCurso,
								"Por favor, seleccione un valor entero entre 0 y 100 para el porcentaje a devolver del curso cancelable.");
					}

					CursoDto curso = new CursoDto();
					curso.titulo = txtTituloCurso.getText();
					curso.precio = precio;
					curso.fechaInicio = fechasCurso.get(0).horaInicio.toLocalDate();
					curso.estado = CursoDto.CURSO_PLANIFICADO;
					curso.codigoCurso = CursoCRUD.generarCodigoCurso();
					curso.CantidadPagarColectivo = colectivos_Precios.toString();

					/* Curso cancelable */
					curso.isCancelable = cursoCancelable;

					if (cursoCancelable) {
						curso.porcentaje_devolucion = porcentajeDevolucion;
					}

//					System.out.println("POrcentaje dev: " + spPorcentajeDevolucionCursoCancelable.getValue());

					Curso.add(curso);

					for (int i = 0; i < fechasCurso.size(); i++) {
						SesionDto fecha = fechasCurso.get(i);
						SesionCRUD.addSesion(curso.codigoCurso, fecha.horaInicio.toString(), fecha.horaFin.toString());
					}

					ProfesorCRUD.asignarProfesorCurso(curso.codigoCurso, (String) cbProfesores.getSelectedItem());

					JOptionPane.showMessageDialog(pnCrearCurso, "Curso creado correctamente");

					colectivos_Precios = new Precio_Colectivos();

					txtTituloCurso.setText("");
					List<ProfesorDto> profesores = ProfesorCRUD.listProfesoresLibres();
					List<String> nombreProfesores = new ArrayList<>();
					for (ProfesorDto p : profesores) {
						nombreProfesores.add(p.nombre);
					}
					cbProfesores.setModel(new DefaultComboBoxModel(nombreProfesores.toArray()));
					txtFechaSesion.setText("");
					txtHoraInicio.setText("");
					txtHoraFin.setText("");
					txPrecioAnadirColectivo.setText("");
					modeloSesiones.removeAllElements();

					spPorcentajeDevolucionCursoCancelable.setValue(0);
					rbSeleccionCursoCancelableSi.setSelected(false);
					rbSeleccionCursoCancelableNo.setSelected(true);

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
			pnHomeAccionesColegiado.setLayout(new GridLayout(8, 1, 0, 10));

			pnHomeAccionesColegiado.add(getBtHomeAltaColegiado());
			pnHomeAccionesColegiado.add(getBtHomeInscripcionCurso());
			pnHomeAccionesColegiado.add(getBtHomePagarInscripcion());
			pnHomeAccionesColegiado.add(getBtHomeSolicitudServicios());
			pnHomeAccionesColegiado.add(getBtListasProfesionales());
			pnHomeAccionesColegiado.add(getBtCancelarInscripcionCurso());
			pnHomeAccionesColegiado.add(getBtSolicitudVisados());
		}
		return pnHomeAccionesColegiado;
	}

	private DefaultButton getBtCancelarInscripcionCurso() {
		if (btCancelarInscripcionCurso == null) {
			btCancelarInscripcionCurso = new DefaultButton("Cancelar inscripción", "ventana", "CancelarInscripción",
					'l', ButtonColor.NORMAL);
			btCancelarInscripcionCurso.setMnemonic('N');
			btCancelarInscripcionCurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dniColegiado = null;
					frame.setVisible(true);
				}
			});
			btCancelarInscripcionCurso.setText("Cancelar inscripción curso");
		}
		return btCancelarInscripcionCurso;
	}

	public void checkearDni(String dni) {
		dniColegiado = dni;
		if (check(dni)) {
			try {
				if (Curso.listarCursosIsInscrito(getColegiadoDni()).isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"No puede CANCELAR ninguna inscripción debido a que no tiene ninguna inscripción no cancelada en los cursos",
							"No puede cancelar ninguna inscripción", JOptionPane.WARNING_MESSAGE);

				} else {
					dniColegiado = dni;
					mainCardLayout.show(mainPanel, CANCELAR_INSCRIPCION);
					pnCancelarInscripcionCursoInscripciones.setVisible(false);

					try {
						tableModelF = new CursoModel(Curso.listarCursosIsInscrito(getColegiadoDni()))
								.getCursoModel(CursoModel.LISTA_CURSOS);

					} catch (BusinessException e11) {
						showMessage(e11, MessageType.ERROR);
						e11.printStackTrace();
					}

					tbListadoCursosInscrito.setModel(tableModelF);
					tbListadoCursosInscrito.repaint();
					scrollPaneCursosInscritos.setVisible(true);

					btnNewButtonInscripcionCancelada.setEnabled(true);
				}
			} catch (HeadlessException | BusinessException e) {
				e.printStackTrace();
			}

		}
	}

	private boolean check(String dni) {
		try {
			if (Colegiado.findColegiadoPorDni(dni) == null) {
				JOptionPane.showMessageDialog(null,
						"No puede CANCELAR ninguna inscripción debido a que no se ha registrado como usuario en el sistema",
						"No puede cancelar ninguna inscripción", JOptionPane.WARNING_MESSAGE);
				return false;
			} else if (InscripcionColegiado.findInscripcion(dni).isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"No puede CANCELAR ninguna inscripción debido a que no tiene ninguna inscripción no cancelada en los cursos",
						"No puede cancelar ninguna inscripción", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} catch (HeadlessException | BusinessException e) {
			e.printStackTrace();
		}
		return true;
	}

	private JPanel getPnHomeAccionesSecretaria() {
		if (pnHomeAccionesSecretaria == null) {
			pnHomeAccionesSecretaria = new JPanel();
			pnHomeAccionesSecretaria.setBorder(new EmptyBorder(50, 50, 50, 50));
			pnHomeAccionesSecretaria.setOpaque(false);
			pnHomeAccionesSecretaria.setLayout(new GridLayout(9, 1, 0, 10));
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaAbrirInscripciones());
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaConsultarTitulacionSolicitante());
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaEmitirCuotas());
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaAddCurso());
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaListadoInscripciones());
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaTransferencias());
			pnHomeAccionesSecretaria.add(getBtHomeAsignacionSolicitudServicios());
			pnHomeAccionesSecretaria.add(getBtHomeSecretariaCancelarCursos());
			pnHomeAccionesSecretaria.add(getBtHomeAsignarVisados());
		}
		return pnHomeAccionesSecretaria;
	}

	private DefaultButton getBtHomeSecretariaCancelarCursos() {
		if (btHomeSecretariaCancelarCursos == null) {
			btHomeSecretariaCancelarCursos = new DefaultButton("Cancelar un curso", "ventana", "CancelaCurso", 'l',
					ButtonColor.NORMAL);
			btHomeSecretariaCancelarCursos.setMnemonic('U');
			btHomeSecretariaCancelarCursos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (Curso.listarCursosAbiertos().size() == 0) {
							JOptionPane.showMessageDialog(null,
									"No puede CANCELAR ningún curso debido a que no hay ninguno disponible para cancelarlo",
									"No puede cancelar ningún curso", JOptionPane.WARNING_MESSAGE);
						} else {
							mainCardLayout.show(mainPanel, CANCELAR_CURSO);
							spInscripcionesCanceladas.setVisible(false);
							if (tbListadoCursos != null) {
								try {
									tableModelL = new CursoModel(Curso.listarCursosAbiertosPlanificados())
											.getCursoModel(CursoModel.LISTA_CURSOS);
								} catch (BusinessException e1) {
									e1.printStackTrace();
								}
								tbListadoCursos.setModel(tableModelL);
								tbListadoCursos.repaint();
							}
							dfltbtnCancelarCurso.setEnabled(true);
							pnCancelarCursoCOIIPAInscripciones.setVisible(false);
						}
					} catch (BusinessException e1) {
						e1.printStackTrace();
					}
				}
			});
			btHomeSecretariaCancelarCursos.setText("Cancelar cursos");
		}
		return btHomeSecretariaCancelarCursos;
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

	private JTable getTbInscripciones() {
		if (tbInscripciones == null) {
			tbInscripciones = new JTable();
			tbInscripciones.setIntercellSpacing(new Dimension(0, 0));
			tbInscripciones.setShowGrid(false);
			tbInscripciones.setRowMargin(0);
			tbInscripciones.setRequestFocusEnabled(false);
			tbInscripciones.setFocusable(false);
			tbInscripciones.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbInscripciones.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbInscripciones.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbInscripciones.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbInscripciones.setShowVerticalLines(false);
			tbInscripciones.setOpaque(false);

			tbInscripciones.setRowHeight(LookAndFeel.ROW_HEIGHT);
			tbInscripciones.setGridColor(new Color(255, 255, 255));

			tbInscripciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		}
		return tbInscripciones;
	}

	private JPanel getPnListadoInscripciones() {
		if (pnListadoInscripciones == null) {
			pnListadoInscripciones = new JPanel();
			pnListadoInscripciones.setLayout(null);
			pnListadoInscripciones.add(getSpnCursos());

			JScrollPane spnInscripciones = new JScrollPane();
			spnInscripciones.setBounds(547, 99, 481, 417);
			pnListadoInscripciones.add(spnInscripciones);

			spnInscripciones.setViewportView(getTbInscripciones());

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
					tbCursosInscripciones = null;
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

			enableRecepcionarLoteButton();
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
			GridBagLayout gbl_pnConsultarTitulacionCenter = new GridBagLayout();
			gbl_pnConsultarTitulacionCenter.rowHeights = new int[] { 253, 90, 253 };
			gbl_pnConsultarTitulacionCenter.columnWeights = new double[] { 1.0 };
			gbl_pnConsultarTitulacionCenter.rowWeights = new double[] { 1.0, 0.0, 1.0, 1.0 };
			pnConsultarTitulacionCenter.setLayout(gbl_pnConsultarTitulacionCenter);
			GridBagConstraints gbc_spListadoAltaSolicitudesColegiado = new GridBagConstraints();
			gbc_spListadoAltaSolicitudesColegiado.fill = GridBagConstraints.BOTH;
			gbc_spListadoAltaSolicitudesColegiado.insets = new Insets(0, 0, 5, 0);
			gbc_spListadoAltaSolicitudesColegiado.gridx = 0;
			gbc_spListadoAltaSolicitudesColegiado.gridy = 0;
			pnConsultarTitulacionCenter.add(getSpListadoAltaSolicitudesColegiado(),
					gbc_spListadoAltaSolicitudesColegiado);
			GridBagConstraints gbc_pnListadoAltaSolicitudesColegiadoActualizarLista = new GridBagConstraints();
			gbc_pnListadoAltaSolicitudesColegiadoActualizarLista.fill = GridBagConstraints.BOTH;
			gbc_pnListadoAltaSolicitudesColegiadoActualizarLista.insets = new Insets(0, 0, 5, 0);
			gbc_pnListadoAltaSolicitudesColegiadoActualizarLista.gridx = 0;
			gbc_pnListadoAltaSolicitudesColegiadoActualizarLista.gridy = 1;
			pnConsultarTitulacionCenter.add(getPnListadoAltaSolicitudesColegiadoActualizarLista(),
					gbc_pnListadoAltaSolicitudesColegiadoActualizarLista);
			GridBagConstraints gbc_spRecepcionLoteSolicitudes = new GridBagConstraints();
			gbc_spRecepcionLoteSolicitudes.insets = new Insets(0, 0, 5, 0);
			gbc_spRecepcionLoteSolicitudes.fill = GridBagConstraints.BOTH;
			gbc_spRecepcionLoteSolicitudes.gridx = 0;
			gbc_spRecepcionLoteSolicitudes.gridy = 2;
			GridBagConstraints gbc_pnRecepcionLoteSolicitudesTablaWrapper = new GridBagConstraints();
			gbc_pnRecepcionLoteSolicitudesTablaWrapper.gridheight = 2;
			gbc_pnRecepcionLoteSolicitudesTablaWrapper.fill = GridBagConstraints.BOTH;
			gbc_pnRecepcionLoteSolicitudesTablaWrapper.gridx = 0;
			gbc_pnRecepcionLoteSolicitudesTablaWrapper.gridy = 2;
			pnConsultarTitulacionCenter.add(getPnRecepcionLoteSolicitudesTablaWrapper(),
					gbc_pnRecepcionLoteSolicitudesTablaWrapper);
			GridBagConstraints gbc_lbTTituloRecepcionLoteSolicitudesTabla = new GridBagConstraints();
			gbc_lbTTituloRecepcionLoteSolicitudesTabla.gridx = 0;
			gbc_lbTTituloRecepcionLoteSolicitudesTabla.gridy = 3;
		}
		return pnConsultarTitulacionCenter;
	}
	
	private JPanel getPnRecepcionLoteSolicitudesTablaWrapper() {
		if (pnRecepcionLoteSolicitudesTablaWrapper == null) {
			pnRecepcionLoteSolicitudesTablaWrapper = new JPanel();
			pnRecepcionLoteSolicitudesTablaWrapper.setLayout(new BorderLayout(0, 0));
			pnRecepcionLoteSolicitudesTablaWrapper.add(getLbTTituloRecepcionLoteSolicitudesTabla(), BorderLayout.NORTH);
			pnRecepcionLoteSolicitudesTablaWrapper.add(getSpRecepcionLoteSolicitudes(), BorderLayout.CENTER);
		}
		return pnRecepcionLoteSolicitudesTablaWrapper;
	}
	
	private JScrollPane getSpRecepcionLoteSolicitudes() {
		if (spRecepcionLoteSolicitudes == null) {
			spRecepcionLoteSolicitudes = new JScrollPane(getTbListadoRecepcionSolicitudes());
			spRecepcionLoteSolicitudes.setBorder(null);
		}
		return spRecepcionLoteSolicitudes;
	}
	
	private JTable getTbListadoRecepcionSolicitudes() {
		if (tbListadoRecepcionSolicitudes == null) {
			tbListadoRecepcionSolicitudes = new JTable();

			tbListadoRecepcionSolicitudes.setRowSelectionAllowed(false);

			tbListadoRecepcionSolicitudes.setIntercellSpacing(new Dimension(0, 0));
			tbListadoRecepcionSolicitudes.setShowGrid(false);
			tbListadoRecepcionSolicitudes.setRowMargin(0);
			tbListadoRecepcionSolicitudes.setRequestFocusEnabled(false);
			tbListadoRecepcionSolicitudes.setFocusable(false);
			tbListadoRecepcionSolicitudes.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbListadoRecepcionSolicitudes.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbListadoRecepcionSolicitudes.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbListadoRecepcionSolicitudes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbListadoRecepcionSolicitudes.setShowVerticalLines(false);
			tbListadoRecepcionSolicitudes.setOpaque(false);

			tbListadoRecepcionSolicitudes.setRowHeight(LookAndFeel.ROW_HEIGHT);
			tbListadoRecepcionSolicitudes.setGridColor(new Color(255, 255, 255));

			tbListadoRecepcionSolicitudes.setModel(new DefaultTableModel());
		}
		return tbListadoRecepcionSolicitudes;
	}
	
	private JLabel getLbTTituloRecepcionLoteSolicitudesTabla() {
		if (lbTTituloRecepcionLoteSolicitudesTabla == null) {
			lbTTituloRecepcionLoteSolicitudesTabla = new JLabel("");
			lbTTituloRecepcionLoteSolicitudesTabla.setFont(LookAndFeel.HEADING_2_FONT);
			lbTTituloRecepcionLoteSolicitudesTabla.setVisible(false);
		}
		return lbTTituloRecepcionLoteSolicitudesTabla;
	}
	
	

	private JPanel getPnListadoAltaSolicitudesColegiadoActualizarLista() {
		if (pnListadoAltaSolicitudesColegiadoActualizarLista == null) {
			pnListadoAltaSolicitudesColegiadoActualizarLista = new JPanel();
			GridBagLayout gbl_pnListadoAltaSolicitudesColegiadoActualizarLista = new GridBagLayout();
			gbl_pnListadoAltaSolicitudesColegiadoActualizarLista.columnWidths = new int[] {850, 250, 0};
			gbl_pnListadoAltaSolicitudesColegiadoActualizarLista.rowHeights = new int[]{85, 0};
			gbl_pnListadoAltaSolicitudesColegiadoActualizarLista.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
			gbl_pnListadoAltaSolicitudesColegiadoActualizarLista.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			pnListadoAltaSolicitudesColegiadoActualizarLista.setLayout(gbl_pnListadoAltaSolicitudesColegiadoActualizarLista);
			GridBagConstraints gbc_pnListadoAltaSolicitanteRefrescarListaBotonContainer = new GridBagConstraints();
			gbc_pnListadoAltaSolicitanteRefrescarListaBotonContainer.fill = GridBagConstraints.BOTH;
			gbc_pnListadoAltaSolicitanteRefrescarListaBotonContainer.gridx = 0;
			gbc_pnListadoAltaSolicitanteRefrescarListaBotonContainer.gridy = 0;
			pnListadoAltaSolicitudesColegiadoActualizarLista
					.add(getPnListadoAltaSolicitanteRefrescarListaBotonContainer(), gbc_pnListadoAltaSolicitanteRefrescarListaBotonContainer);
			GridBagConstraints gbc_pnNumeroSolicitudesColegiado = new GridBagConstraints();
			gbc_pnNumeroSolicitudesColegiado.fill = GridBagConstraints.BOTH;
			gbc_pnNumeroSolicitudesColegiado.gridx = 1;
			gbc_pnNumeroSolicitudesColegiado.gridy = 0;
			pnListadoAltaSolicitudesColegiadoActualizarLista.add(getPnNumeroSolicitudesColegiado(), gbc_pnNumeroSolicitudesColegiado);
		}
		return pnListadoAltaSolicitudesColegiadoActualizarLista;
	}

	private DefaultButton getBtActualizarListaSolicitudesColegiado() {
		if (btActualizarListaSolicitudesColegiado == null) {
			btActualizarListaSolicitudesColegiado = new DefaultButton("Enviar lote", "ventana",
					"EnviarLoteSolicitudesColegiado", 'e', ButtonColor.NORMAL);
			btActualizarListaSolicitudesColegiado.setToolTipText(
					"Haz click aquí para enviar el lote con todas las solicitudes de colegiación pendientes");
			btActualizarListaSolicitudesColegiado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						/*
						 * Al hacer click en el botón "Enviar Lote, se procederá al envío de todas las
						 * solicitudes de alta de colegiado hasta la fecha"
						 */
						String nombreLote = Colegiado.enviarLoteSolicitudesColegiacion();

						// Si se envió el lote de forma satisfactoria, mostrar mensaje informativo
						String mensajeInformativoLote = "Se ha enviado el lote " + nombreLote
								+ " \ndentro del directorio: lotes_colegiacion";

						pnConsultarColegiadoDatosColegiadoSeleccionado.setVisible(true);
						lbColegiadoSeleccionadoSolicitudRespuesta.setText(mensajeInformativoLote);

						JOptionPane.showMessageDialog(null, mensajeInformativoLote,
								"Información | Envío lote solicitudes colegiación", JOptionPane.INFORMATION_MESSAGE);

						tbListadoSolicitudesColegiado.setModel(new DefaultTableModel());
						List<ColegiadoDto> colegiados = Colegiado.findAllSolicitudesAltaColegiados();

						lbColegiadoSeleccionadoSolicitudRespuesta.setText(
								"Lote enviado correctamente. En la tabla se reflejan las solicitudes enviadas.");
						TableModel allSolicitudesColegiado = new ColegiadoModel(colegiados).getColegiadoModel(false);
						refreshPanelRecepcionSolicitudes(allSolicitudesColegiado,
								"Envío solicitudes · Listado solicitudes colegiación enviadas");

						// Habilitar el boton para permitir la recepcion del lote
						btRecepcionarLoteSolicitudesPendientesColegiado.setEnabled(true);

					} catch (BusinessException e1) {
						pnConsultarColegiadoDatosColegiadoSeleccionado.setVisible(true);
						lbColegiadoSeleccionadoSolicitudRespuesta.setText(e1.getMessage());
					}
				}
			});
		}
		return btActualizarListaSolicitudesColegiado;
	}
	
	/**
	 * Actualiza los datos de la tabla de recepcion de solicitudes de colegiado en
	 * funcion del modelo pasado y el título.
	 * 
	 * @param model
	 * @param titulo
	 */
	private void refreshPanelRecepcionSolicitudes(TableModel model, String titulo) {
		lbNumeroSolicitudesColegiado.setText("");
		spRecepcionLoteSolicitudes.setVisible(true);
		lbTTituloRecepcionLoteSolicitudesTabla.setText(titulo);
		lbTTituloRecepcionLoteSolicitudesTabla.setVisible(true);
		tbListadoRecepcionSolicitudes.setModel(model);
		tbListadoRecepcionSolicitudes.repaint();
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
			tbListadoSolicitudesColegiado.setRowSelectionAllowed(false);

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
					resetearAjustesVentanaSolicitudesColegidado();
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

			pnConsultarColegiadoDatosColegiadoSeleccionado.setVisible(false);
		}
		return pnConsultarColegiadoDatosColegiadoSeleccionado;
	}

	private JLabel getLbColegiadoSeleccionadoSolicitudRespuesta() {
		if (lbColegiadoSeleccionadoSolicitudRespuesta == null) {
			lbColegiadoSeleccionadoSolicitudRespuesta = new JLabel("Mensaje del lote");
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
			pnCrearCursoCenterContainer.setLayout(new BorderLayout(0, 0));
			pnCrearCursoCenterContainer.add(getPnTituloCurso(), BorderLayout.NORTH);
			pnCrearCursoCenterContainer.add(getPnProfesores(), BorderLayout.EAST);
		}
		return pnCrearCursoCenterContainer;
	}

	private JScrollPane getSpnCursos() {
		if (spnCursos == null) {
			spnCursos = new JScrollPane();
			spnCursos.setBounds(77, 99, 481, 417);
			spnCursos.add(getTbCursosInscripciones());
			getTbCursosInscripciones().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					getLbAlertaListadoInscripciones().setVisible(false);
					int selectedRow = tbCursosInscripciones.getSelectedRow();
					if (selectedRow == -1) {
						selectedRow = 0;
					}
					List<Colegiado_Inscripcion> listInscripciones = InscripcionColegiado
							.Lista_Inscritos_Curso(cursosAbiertosPnInscripcion.get(selectedRow));
					if (listInscripciones.isEmpty()) {
						getLbAlertaListadoInscripciones().setVisible(true);
						getLbAlertaListadoInscripciones()
								.setText("Actualmente no hay inscritos en el curso seleccionado");
					} else {

						TableModel tableModel = new ModeloInscripcion(listInscripciones).getCursoModel();

						getTbInscripciones().setModel(tableModel);

						lbTotalIngresosText.setVisible(true);
						lbTotalIngresos.setVisible(true);
						lbTotalIngresos
								.setText(listInscripciones.stream().mapToDouble(I -> I.cantidadPagada()).sum() + "");
					}

				}
			});

			spnCursos.setViewportView(getTbCursosInscripciones());

		}
		return spnCursos;
	}

	public void ActualizaModeloListaCursos() {
		cursosAbiertosPnInscripcion = Curso.listaCursosAbiertosYCerrados();
		if (cursosAbiertosPnInscripcion.isEmpty()) {
			getLbAlertaListadoInscripciones().setVisible(true);
			getLbAlertaListadoInscripciones().setText("Lo sentimos, no hay cursos disponibles actualmente");
		} else {

			TableModel tableModel = new ModeloCurso(cursosAbiertosPnInscripcion).getCursoModel(false, "");

			getTbCursosInscripciones().setModel(tableModel);
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
							int idCurso = Integer.parseInt(
									((String) comboBoxIdentificadorCursosAbiertos.getSelectedItem()).split(" ")[0]);
							InscripcionColegiado.comprobarFecha(InscripcionColegiado
									.findFechaPreinscripcion(textFieldDNIColegiado.getText(), idCurso));
							InscripcionColegiado.pagarCursoColegiado(textFieldDNIColegiado.getText(), idCurso,
									"PENDIENTE", "TRANSFERENCIA");
							JOptionPane.showMessageDialog(null,
									"Ha seleccionado usted la opción de pagar por transferencia bancaria\n"
											+ "La inscripción al curso con identificador " + idCurso
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
		int idCurso = Integer.parseInt(((String) comboBoxIdentificadorCursosAbiertos.getSelectedItem()).split(" ")[0]);
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
			InscripcionColegiado.findFechaPreinscripcion(textFieldDNIColegiado.getText(), idCurso);
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
		if (textFieldDNIColegiado.getText().isEmpty() || textFieldDNIColegiado.getText().length() != 9) {
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
							int idCurso = Integer.parseInt(
									((String) comboBoxIdentificadorCursosAbiertos.getSelectedItem()).split(" ")[0]);
							InscripcionColegiado.comprobarFecha(InscripcionColegiado
									.findFechaPreinscripcion(textFieldDNIColegiado.getText(), idCurso));

							InscripcionColegiado.pagarCursoColegiado(textFieldDNIColegiado.getText(), idCurso,
									"INSCRITO", "TARJETA");

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

		if (textFieldDNIColegiado.getText().isEmpty() || textFieldDNIColegiado.getText().length() != 9
				|| textFieldNumeroTarjetaColegiado.getText().isEmpty()
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
			textFieldDNIColegiado.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if (!textFieldDNIColegiado.getText().isEmpty()) {

						ColegiadoDto c = null;

						try {
							c = Colegiado.findColegiadoPorDni(textFieldDNIColegiado.getText().toString());
							if (c != null && c.estado != null && c.estado.equalsIgnoreCase("EN_ESPERA")) {
								textFieldDNIColegiado.setText("");

								JOptionPane.showMessageDialog(null,
										"El solicitante no puede pagar la inscripción ya que se encuentra en la lista de espera de un curso",
										"Pagar inscripcion: Solicitante en lista de espera", JOptionPane.ERROR_MESSAGE);

							}
						} catch (BusinessException e1) {

							e1.printStackTrace();
						}

					}
				}
			});
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
			lblNewLabelIdentificadorCurso = new JLabel("Identificador del curso:");
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
			btnInicioInscripcion = new DefaultButton("Volver a inicio", "ventana", "VolverAInicio", 'v',
					ButtonColor.CANCEL);
			btnInicioInscripcion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (confirmarVolverPrincipio()) {
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
		List<ColegiadoDto> colegiados = Colegiado.findAllSolicitudesAltaColegiados();
		TableModel allSolicitudesColegiado = new ColegiadoModel(colegiados).getColegiadoModel(false);

		pnConsultarColegiadoDatosColegiadoSeleccionado.setVisible(false);
		lbNumeroSolicitudesColegiado.setText("Mostrando " + colegiados.size() + " Solicitudes");
		tbListadoSolicitudesColegiado.setModel(allSolicitudesColegiado);

//		lbColegiadoSeleccionadoSolicitudRespuesta.setText(
//				"Seleccione un colegiado de la lista y, si tiene titulación, se procederá a darle de alta en el COIIPA");
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
			GridBagLayout gbl_pnNumeroTarjetaValidarTarjeta = new GridBagLayout();
			gbl_pnNumeroTarjetaValidarTarjeta.columnWidths = new int[]{84, 347, 0};
			gbl_pnNumeroTarjetaValidarTarjeta.rowHeights = new int[]{45, 80, 0};
			gbl_pnNumeroTarjetaValidarTarjeta.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			gbl_pnNumeroTarjetaValidarTarjeta.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			pnNumeroTarjetaValidarTarjeta.setLayout(gbl_pnNumeroTarjetaValidarTarjeta);
			GridBagConstraints gbc_btnTarjetaCreditoColegiado = new GridBagConstraints();
			gbc_btnTarjetaCreditoColegiado.anchor = GridBagConstraints.NORTH;
			gbc_btnTarjetaCreditoColegiado.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnTarjetaCreditoColegiado.gridx = 1;
			gbc_btnTarjetaCreditoColegiado.gridy = 1;
			pnNumeroTarjetaValidarTarjeta.add(getBtnTarjetaCreditoColegiado(), gbc_btnTarjetaCreditoColegiado);
		}
		return pnNumeroTarjetaValidarTarjeta;
	}

	private JPanel getPnPagoTranferencia() {
		if (pnPagoTranferencia == null) {
			pnPagoTranferencia = new JPanel();
			pnPagoTranferencia.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			pnPagoTranferencia.setBorder(new EmptyBorder(0, 100, 0, 100));
			GridBagLayout gbl_pnPagoTranferencia = new GridBagLayout();
			gbl_pnPagoTranferencia.columnWidths = new int[]{129, 314, 0};
			gbl_pnPagoTranferencia.rowHeights = new int[]{212, 71, 0};
			gbl_pnPagoTranferencia.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			gbl_pnPagoTranferencia.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			pnPagoTranferencia.setLayout(gbl_pnPagoTranferencia);
			GridBagConstraints gbc_btnTransferenciaColegiado = new GridBagConstraints();
			gbc_btnTransferenciaColegiado.fill = GridBagConstraints.BOTH;
			gbc_btnTransferenciaColegiado.gridx = 1;
			gbc_btnTransferenciaColegiado.gridy = 1;
			pnPagoTranferencia.add(getBtnTransferenciaColegiado(), gbc_btnTransferenciaColegiado);
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
			GridBagConstraints gbc_textFieldTitulaciones = new GridBagConstraints();
			gbc_textFieldTitulaciones.gridheight = 2;
			gbc_textFieldTitulaciones.fill = GridBagConstraints.BOTH;
			gbc_textFieldTitulaciones.gridx = 0;
			gbc_textFieldTitulaciones.gridy = 1;
			pnDatosColTitulacionText.add(getTextFieldTitulacion(), gbc_textFieldTitulaciones);
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

	private JLabel getLbNumeroSolicitudesColegiado() {
		if (lbNumeroSolicitudesColegiado == null) {
			lbNumeroSolicitudesColegiado = new JLabel("No hay ninguna solicitud");
			lbNumeroSolicitudesColegiado.setHorizontalAlignment(SwingConstants.CENTER);
			lbNumeroSolicitudesColegiado.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lbNumeroSolicitudesColegiado;
	}

	private JPanel getPnNumeroSolicitudesColegiado() {
		if (pnNumeroSolicitudesColegiado == null) {
			pnNumeroSolicitudesColegiado = new JPanel();
			pnNumeroSolicitudesColegiado.setBorder(new EmptyBorder(0, 0, 0, 50));
			pnNumeroSolicitudesColegiado.setAlignmentX(Component.RIGHT_ALIGNMENT);
			pnNumeroSolicitudesColegiado.setOpaque(false);
			pnNumeroSolicitudesColegiado.setLayout(new BorderLayout(0, 0));
			pnNumeroSolicitudesColegiado.add(getLbNumeroSolicitudesColegiado());
		}
		return pnNumeroSolicitudesColegiado;
	}

	private JPanel getPnListadoAltaSolicitanteRefrescarListaBotonContainer() {
		if (pnListadoAltaSolicitanteRefrescarListaBotonContainer == null) {
			pnListadoAltaSolicitanteRefrescarListaBotonContainer = new JPanel();
			pnListadoAltaSolicitanteRefrescarListaBotonContainer.setLayout(new BoxLayout(pnListadoAltaSolicitanteRefrescarListaBotonContainer, BoxLayout.X_AXIS));
			pnListadoAltaSolicitanteRefrescarListaBotonContainer.add(getPnListadoAltaSolicitanteRecepcionLoteBotonesWrapper());
		}
		return pnListadoAltaSolicitanteRefrescarListaBotonContainer;
	}

	private DefaultButton getBtRecepcionarLoteSolicitudesPendientesColegiado() {
		if (btRecepcionarLoteSolicitudesPendientesColegiado == null) {
			btRecepcionarLoteSolicitudesPendientesColegiado = new DefaultButton("Recepcionar lote", "ventana",
					"VerFicheroLoteSolicitudesColegiacionEnviado", 'v', ButtonColor.INFO);
			btRecepcionarLoteSolicitudesPendientesColegiado.setHorizontalTextPosition(SwingConstants.CENTER);
			btRecepcionarLoteSolicitudesPendientesColegiado.setDoubleBuffered(true);
			btRecepcionarLoteSolicitudesPendientesColegiado.setBorder(new LineBorder(new Color(219, 151, 10), 2, true));
			btRecepcionarLoteSolicitudesPendientesColegiado
					.setToolTipText("Haz click aquí para recepcionar el lote de solicitudes de colegiación");
			btRecepcionarLoteSolicitudesPendientesColegiado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					try {
						List<ColegiadoDto> colegiadosAdmitidos = Colegiado.recepcionarLoteSolicitudesColegiacion()
								.stream().sorted((c1, c2) -> c1.numeroColegiado.compareTo(c2.numeroColegiado))
								.collect(Collectors.toList());

						TableModel solicitudesColegiadoAdmitidasModel = new ColegiadoModel(colegiadosAdmitidos)
								.getNuevoColegiadoModel();

						String msgAux = colegiadosAdmitidos.size() == 0
								? "No hay ninguna solicitud admitida para colegiación"
								: "Hay " + colegiadosAdmitidos.size()
										+ " solicitudes aptas. La tabla muestra los nuevos colegiados del COIIPA.";
						lbColegiadoSeleccionadoSolicitudRespuesta.setText("Lote recepcionado correctamente. " + msgAux);
						refreshPanelRecepcionSolicitudes(solicitudesColegiadoAdmitidasModel,
								"Recepción solicitudes · Listado de nuevos colegiados del COIIPA");

					} catch (BusinessException be) {

						lbColegiadoSeleccionadoSolicitudRespuesta.setText(be.getMessage());
					}
				}

			});
		}
		return btRecepcionarLoteSolicitudesPendientesColegiado;
	}

	private void enableRecepcionarLoteButton() {
		try {
			boolean enableRecepcionButton = CSVLoteSolicitudesColegiacion.existeLoteSolicitudesParaRecepcion();

		} catch (IOException e) {
			lbColegiadoSeleccionadoSolicitudRespuesta.setText("No hay lotes de solicitudes para recepcionar.");
			e.printStackTrace();
		}
	}

	private void resetearAjustesVentanaSolicitudesColegidado() {
		lbColegiadoSeleccionadoSolicitudRespuesta.setText("");
		lbColegiadoSeleccionadoSolicitudRespuesta.setVisible(false);

		btActualizarListaSolicitudesColegiado.setEnabled(true);
		// btRecepcionarLoteSolicitudesPendientesColegiado.setEnabled(true);

		lbConsultarTitulacionTitle.setText("Consultar titulación de un solicitante de Ingreso");

		lbNumeroSolicitudesColegiado.setText("No hay ninguna solicitud");
	}

	private JPanel getPnColectivos() {
		if (pnColectivos == null) {
			pnColectivos = new JPanel();
			pnColectivos.setBorder(null);
			pnColectivos.setLayout(new BorderLayout(50, 70));
			pnColectivos.add(getPnColectivosCenter(), BorderLayout.CENTER);

		}
		return pnColectivos;
	}

	private JPanel getPnColectivosCenter() {
		if (pnColectivosCenter == null) {
			pnColectivosCenter = new JPanel();
			pnColectivosCenter.setBounds(new Rectangle(0, 0, 500, 0));
			pnColectivosCenter.setLayout(new GridLayout(5, 1, 0, 0));
			pnColectivosCenter.add(getPnColectivosAnadir());
			
			pnColectivosCenter.add(getPnColectivosCuotasSeleccionadas());
			pnColectivosCenter.add(getPnColectivosEliminar());
			
			pnColectivosCenter.add(getPnColectivosSouth());
		}
		return pnColectivosCenter;
	}

	private JPanel getPnColectivosEliminar() {
		if (pnColectivosEliminar == null) {
			pnColectivosEliminar = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnColectivosEliminar.getLayout();
			flowLayout.setAlignment(FlowLayout.LEADING);
			pnColectivosEliminar.add(getCbColectivosEliminar());
			pnColectivosEliminar.add(getBtnEliminarColectivo());
		}
		return pnColectivosEliminar;
	}

	private JComboBox<String> getCbColectivosEliminar() {
		if (cbColectivosEliminar == null) {
			cbColectivosEliminar = new JComboBox<String>();
		}
		return cbColectivosEliminar;
	}

	private JButton getBtnEliminarColectivo() {
		if (btnEliminarColectivo == null) {
			btnEliminarColectivo = new JButton("Eliminar");
			btnEliminarColectivo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (cbColectivosEliminar.getModel() == null
							|| cbColectivosEliminar.getModel().getSelectedItem() == null) {
						JOptionPane.showMessageDialog(pnCrearCurso,
								"Para Eliminar un curso, debe haber un curso seleccionado");
						return;
					}
					colectivos_Precios.remove((String) cbColectivosEliminar.getModel().getSelectedItem());
					cbColectivosEliminar
							.setModel(new DefaultComboBoxModel(colectivos_Precios.getColectivos_precios().toArray()));

				}
			});
			btnEliminarColectivo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return btnEliminarColectivo;
	}

	private JPanel getPnColectivosAnadir() {
		if (pnColectivosAnadir == null) {
			pnColectivosAnadir = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnColectivosAnadir.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnColectivosAnadir.add(getCbColectivosAnadir());
			pnColectivosAnadir.add(getPnPrecioAnadirColectivo());
			pnColectivosAnadir.add(getBtnAnadirColectivo());
		}
		return pnColectivosAnadir;
	}

	private JComboBox<String> getCbColectivosAnadir() {
		if (cbColectivosAnadir == null) {
			cbColectivosAnadir = new JComboBox<String>();
			cbColectivosAnadir.setFont(new Font("Tahoma", Font.PLAIN, 16));
			cbColectivosAnadir.setModel(new DefaultComboBoxModel<String>(
					new String[] { "Colegiado", "Precolegiado", "Estudiante", "Desempleado", "Otros", "Todos" }));
			cbColectivosAnadir.setAlignmentX(0.0f);
		}
		return cbColectivosAnadir;
	}

	private JButton getBtnAnadirColectivo() {
		if (btnAnadirColectivo == null) {
			btnAnadirColectivo = new JButton("A\u00F1adir");
			btnAnadirColectivo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (colectivos_Precios == null) {
						colectivos_Precios = new Precio_Colectivos();
					}
					if (txPrecioAnadirColectivo.getText().isEmpty()) {
						JOptionPane.showMessageDialog(pnCrearCurso, "Es necesario introducir un precio");
						return;
					}
					try {
						double precio = Double.valueOf(txPrecioAnadirColectivo.getText());
						String colectivo = (String) cbColectivosAnadir.getSelectedItem();
						if (precio < 0) {
							JOptionPane.showMessageDialog(pnCrearCurso, "No se puede anadir un precio negativo");
							return;
						}
						if (colectivos_Precios.containsColectivo(colectivo)) {
							JOptionPane.showMessageDialog(pnCrearCurso,
									"No se puede anadir porque el colectivo ya ha sido anadido");
							return;
						}
						if (colectivo.equals("Todos")) {
							if (colectivos_Precios.size() != 0) {
								JOptionPane.showMessageDialog(pnCrearCurso,
										"No se puede anadir un precio para todos porque hay precios para otros colectivos");
								return;
							}
							colectivos_Precios.add(colectivo, precio);
							cbColectivosEliminar.setModel(
									new DefaultComboBoxModel(colectivos_Precios.getColectivos_precios().toArray()));
							return;
						} else {
							if (colectivos_Precios.containsColectivo("Todos")) {
								JOptionPane.showMessageDialog(pnCrearCurso,
										"No se puede anadir un precio para el colectivo seleccionado porque hay un precio para todos");
								return;
							} else {
								colectivos_Precios.add(colectivo, precio);
								cbColectivosEliminar.setModel(
										new DefaultComboBoxModel(colectivos_Precios.getColectivos_precios().toArray()));
								return;
							}
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(pnCrearCurso, "Introduzca un precio correcto");
					}

				}
			});
			btnAnadirColectivo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return btnAnadirColectivo;
	}

	private JPanel getPnPrecioAnadirColectivo() {
		if (pnPrecioAnadirColectivo == null) {
			pnPrecioAnadirColectivo = new JPanel();
			pnPrecioAnadirColectivo.setLayout(new BorderLayout(0, 0));
			pnPrecioAnadirColectivo.add(getTxPrecioAnadirColectivo());
			pnPrecioAnadirColectivo.add(getLbPrecioAnadirColectivo(), BorderLayout.NORTH);
		}
		return pnPrecioAnadirColectivo;
	}

	private JTextField getTxPrecioAnadirColectivo() {
		if (txPrecioAnadirColectivo == null) {
			txPrecioAnadirColectivo = new JTextField();
			txPrecioAnadirColectivo.setFont(new Font("Tahoma", Font.PLAIN, 16));
			txPrecioAnadirColectivo.setColumns(10);
		}
		return txPrecioAnadirColectivo;
	}

	private JLabel getLbPrecioAnadirColectivo() {
		if (lbPrecioAnadirColectivo == null) {
			lbPrecioAnadirColectivo = new JLabel("Precio:");
			lbPrecioAnadirColectivo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lbPrecioAnadirColectivo;
	}

//	TRANSFERENCIAS

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
			btHomeSecretariaTransferencias = new DefaultButton("Abrir inscripciones de un curso", "ventana",
					"AbrirInscrionesCurso", 'b', ButtonColor.NORMAL);
			btHomeSecretariaTransferencias.setMnemonic('T');
			btHomeSecretariaTransferencias.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (Curso.listarTodosLosCursos().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Lo sentimos, no hay ningún curso disponible",
									"No existen cursos", JOptionPane.WARNING_MESSAGE);
						} else {
							panelMuestraTransferencias.setVisible(false);
							btnMovimientosBancarios.setEnabled(true);
							btnProcesarPagos.setEnabled(false);
							mainCardLayout.show(mainPanel, INSCRIPCION_CURSO_TRANSFERENCIAS);
							tbCourses.setEnabled(true);
							tbCourses.clearSelection();
							tbCourses.removeAll();
							try {
								tableModelC = new CursoModel(Curso.listarCursosAbiertos()).getCursoModel(ALL_MINUS_ID);
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
			panelListaMovimientos.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
					"Movimientos bancarios del COIIPA", TitledBorder.CENTER, TitledBorder.TOP, null, null));
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
			lblSeleccionaCursoTransf = new JLabel(
					"Seleccione el curso sobre el que desea registrar su actividad bancaria");
			lblSeleccionaCursoTransf.setFont(LookAndFeel.HEADING_2_FONT);
		}
		return lblSeleccionaCursoTransf;
	}

	private JButton getBtnMovimientosBancarios() {
		if (btnMovimientosBancarios == null) {
			btnMovimientosBancarios = new DefaultButton("Solicitar movimientos bancarios del curso", "ventana",
					"ValidarTransferencia", 'S', ButtonColor.NORMAL);
			btnMovimientosBancarios.setToolTipText("Pulsa para registrar la actividad bancaria del curso seleccionado");
			btnMovimientosBancarios.setText("Registrar");
			btnMovimientosBancarios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (cursoSeleccionado == null) {
						JOptionPane.showMessageDialog(null,
								"Por favor, revise que haya seleccionado un curso para ver el estado de la cuenta bancaria",
								"Seleccione el curso", JOptionPane.WARNING_MESSAGE);
					} else {
						try {
							InscripcionColegiado.pagarBancoTransferencia(cursoSeleccionado.codigoCurso);
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

						if (tbTransferencias != null) {
							try {
								tableModel = new InscripcionColegiadoModel(
										InscripcionColegiado.obtenerTransferencias(cursoSeleccionado.codigoCurso))
												.getCursoModel(InscripcionColegiadoModel.TRANSFERENCIAS_RECIBIDAS);
							} catch (BusinessException e1) {
								e1.printStackTrace();
							}
							tbTransferencias.setModel(tableModel);
							tbTransferencias.repaint();
						}

						JOptionPane.showMessageDialog(null,
								"Se acaba de generar un fichero con los datos bancarios de cada inscripción del curso seleccionado\n"
										+ "Se mostrarán en la siguiente tabla, aunque también puede visualizarlo en la carpeta transferencias, cuyo nombre es "
										+ cursoSeleccionado.codigoCurso + "_banco.csv\n"
										+ "Contiene los datos más recientes sobre las transferencias de los clientes en la cuenta bancaria del COIIPA",
								"Consulta los datos bancarios", JOptionPane.INFORMATION_MESSAGE);

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
			panelMuestraTransferenciasCentro.setToolTipText(
					"Contiene la información de los registros bancarios del COIIPA del curso seleccionado");
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
			btnProcesarPagos = new DefaultButton("Procesar", "ventana", "ValidarTransferencia", 'P',
					ButtonColor.NORMAL);
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
						tableModelP = new InscripcionColegiadoModel(
								InscripcionColegiado.obtenerTransferenciasProcesadas(cursoSeleccionado.codigoCurso))
										.getCursoModel(InscripcionColegiadoModel.TRANSFERENCIAS_PROCESADAS);
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
				tableModelC = new CursoModel(Curso.listarCursosAbiertos()).getCursoModel(ALL_MINUS_ID);

				tbCourses.setModel(tableModelC);
			} catch (BusinessException e) {
				showMessage(e, MessageType.ERROR);
				e.printStackTrace();
			}

			tbCourses.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent event) {

					if (event.getValueIsAdjusting())
						return;

					try {

						int selectedRow = tbCourses.getSelectedRow();

						if (selectedRow == -1) {
							selectedRow = 0;
							cursoSeleccionado = null;
						}

						if (cursoSeleccionado != null) {
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

							cursoSeleccionado.codigoCurso = Integer
									.parseInt(tbCourses.getValueAt(selectedRow, 6).toString());
						}

					} catch (NumberFormatException | ArrayIndexOutOfBoundsException nfe) {
					}
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
				tableModel = new InscripcionColegiadoModel(
						InscripcionColegiado.obtenerTransferencias(cursoSeleccionado.codigoCurso))
								.getCursoModel(InscripcionColegiadoModel.TRANSFERENCIAS_RECIBIDAS);

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
			pnTransferenciasProcesadasCentro.setToolTipText(
					"Contiene los resultados de procesar las transferencias del COIIPA del curso seleccionado");
			pnTransferenciasProcesadasCentro
					.setBorder(new TitledBorder(null, "Lista de inscritos en el curso por transferencia",
							TitledBorder.CENTER, TitledBorder.TOP, null, null));
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
			lblNewLabelProcesarTransferencias = new JLabel(
					"Lista de movimientos en la cuenta bancaria de un curso del COIIPA");
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
					if (confirmarVolverPrincipio()) {
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
				tableModelP = new InscripcionColegiadoModel(
						InscripcionColegiado.obtenerTransferenciasProcesadas(cursoSeleccionado.codigoCurso))
								.getCursoModel(InscripcionColegiadoModel.TRANSFERENCIAS_PROCESADAS);

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

						transferencia.colegiado.apellidos = tbProcesarTransferencias.getValueAt(selectedRow, 2)
								.toString();

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

	private JLabel getLbTitulacionAltaInfo() {
		if (lbTitulacionAltaInfo == null) {
			lbTitulacionAltaInfo = new JLabel("Separe las titulaciones por una coma ','");
			lbTitulacionAltaInfo.setVerticalAlignment(SwingConstants.TOP);
			lbTitulacionAltaInfo.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbTitulacionAltaInfo;
	}

	private JPanel getPnRecepcionLoteResultado() {
		if (pnRecepcionLoteResultado == null) {
			pnRecepcionLoteResultado = new JPanel();
			pnRecepcionLoteResultado.setOpaque(false);
			pnRecepcionLoteResultado.setLayout(new BorderLayout(0, 10));
			pnRecepcionLoteResultado.add(getPnRecepcionLoteNorth(), BorderLayout.NORTH);
			pnRecepcionLoteResultado.add(getPnRecepcionLoteCenter(), BorderLayout.CENTER);
			pnRecepcionLoteResultado.add(getPnRecepcionLoteSouth(), BorderLayout.SOUTH);
		}
		return pnRecepcionLoteResultado;
	}

	private JPanel getPnRecepcionLoteNorth() {
		if (pnRecepcionLoteNorth == null) {
			pnRecepcionLoteNorth = new JPanel();
			pnRecepcionLoteNorth.add(getLbTituloRecepcionLote());
		}
		return pnRecepcionLoteNorth;
	}

	private JLabel getLbTituloRecepcionLote() {
		if (lbTituloRecepcionLote == null) {
			lbTituloRecepcionLote = new JLabel("Listado de nuevos colegiados");
			lbTituloRecepcionLote.setFont(LookAndFeel.HEADING_1_FONT);
		}
		return lbTituloRecepcionLote;
	}

	private JPanel getPnRecepcionLoteCenter() {
		if (pnRecepcionLoteCenter == null) {
			pnRecepcionLoteCenter = new JPanel();
			pnRecepcionLoteCenter.setLayout(new BorderLayout(0, 0));
			pnRecepcionLoteCenter.add(getSpRecepcionLoteTablaDatos());
		}
		return pnRecepcionLoteCenter;
	}

	private JPanel getPnRecepcionLoteSouth() {
		if (pnRecepcionLoteSouth == null) {
			pnRecepcionLoteSouth = new JPanel();
			pnRecepcionLoteSouth.add(getBtVolverHomeRecepcionLote());
		}
		return pnRecepcionLoteSouth;
	}

	private JButton getBtVolverHomeRecepcionLote() {
		if (btVolverHomeRecepcionLote == null) {
			btVolverHomeRecepcionLote = new DefaultButton("Volver a Inicio", "ventana", "VolverAInicioRecepcion", 'v',
					ButtonColor.NORMAL);
			btVolverHomeRecepcionLote.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int opt = JOptionPane.showConfirmDialog(null,
							"¿Está seguro que quiere volver a la página de inicio?");

					if (opt == JOptionPane.OK_OPTION) {
						mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
					}
				}
			});
		}
		return btVolverHomeRecepcionLote;
	}

	private JScrollPane getSpRecepcionLoteTablaDatos() {
		if (spRecepcionLoteTablaDatos == null) {
			spRecepcionLoteTablaDatos = new JScrollPane(getTbListadoNuevosColegiadosRecepcionLote());
		}
		return spRecepcionLoteTablaDatos;
	}

	private JTable getTbListadoNuevosColegiadosRecepcionLote() {
		if (tbListadoNuevosColegiadosRecepcionLote == null) {
			tbListadoNuevosColegiadosRecepcionLote = new JTable();
			tbListadoNuevosColegiadosRecepcionLote.setRowSelectionAllowed(false);

			tbListadoNuevosColegiadosRecepcionLote.setIntercellSpacing(new Dimension(0, 0));
			tbListadoNuevosColegiadosRecepcionLote.setShowGrid(false);
			tbListadoNuevosColegiadosRecepcionLote.setRowMargin(0);
			tbListadoNuevosColegiadosRecepcionLote.setRequestFocusEnabled(false);
			tbListadoNuevosColegiadosRecepcionLote.setFocusable(false);
			tbListadoNuevosColegiadosRecepcionLote.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbListadoNuevosColegiadosRecepcionLote.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbListadoNuevosColegiadosRecepcionLote.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbListadoNuevosColegiadosRecepcionLote.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbListadoNuevosColegiadosRecepcionLote.setShowVerticalLines(false);
			tbListadoNuevosColegiadosRecepcionLote.setOpaque(false);

			tbListadoNuevosColegiadosRecepcionLote.setRowHeight(LookAndFeel.ROW_HEIGHT);
			tbListadoNuevosColegiadosRecepcionLote.setGridColor(new Color(255, 255, 255));

		}
		return tbListadoNuevosColegiadosRecepcionLote;
	}

	private JPanel getPnColectivosCuotasSeleccionadas() {
		if (pnColectivosCuotasSeleccionadas == null) {
			pnColectivosCuotasSeleccionadas = new JPanel();
			GridBagLayout gbl_pnColectivosCuotasSeleccionadas = new GridBagLayout();
			gbl_pnColectivosCuotasSeleccionadas.columnWidths = new int[] { 0, 0 };
			gbl_pnColectivosCuotasSeleccionadas.rowHeights = new int[] { 0, 0, 0 };
			gbl_pnColectivosCuotasSeleccionadas.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
			gbl_pnColectivosCuotasSeleccionadas.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
			pnColectivosCuotasSeleccionadas.setLayout(gbl_pnColectivosCuotasSeleccionadas);
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel_1.gridx = 0;
			gbc_lblNewLabel_1.gridy = 0;
			pnColectivosCuotasSeleccionadas.add(getLblNewLabel_1(), gbc_lblNewLabel_1);
			GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
			gbc_lblNewLabel_2.gridx = 0;
			gbc_lblNewLabel_2.gridy = 1;
			pnColectivosCuotasSeleccionadas.add(getLblNewLabel_2(), gbc_lblNewLabel_2);
		}
		return pnColectivosCuotasSeleccionadas;
	}

	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Colectivos A\u00F1adidos:");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblNewLabel_1;
	}

	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("Se mostraran los precios para cada colectivo en el desplegable");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return lblNewLabel_2;
	}

	private JPanel getPnSolicitudServicios() {
		if (pnSolicitudServicios == null) {
			pnSolicitudServicios = new JPanel();
			pnSolicitudServicios.setLayout(new BorderLayout(0, 0));
			pnSolicitudServicios.add(getPnTituloSolicitud(), BorderLayout.NORTH);
			pnSolicitudServicios.add(getPnCenterSolicitud(), BorderLayout.WEST);
			pnSolicitudServicios.add(getPnSouthSolicitud(), BorderLayout.SOUTH);
		}
		return pnSolicitudServicios;
	}

	private JPanel getPnTituloSolicitud() {
		if (pnTituloSolicitud == null) {
			pnTituloSolicitud = new JPanel();
			pnTituloSolicitud.add(getLbTituloSolicitud());
		}
		return pnTituloSolicitud;
	}

	private JLabel getLbTituloSolicitud() {
		if (lbTituloSolicitud == null) {
			lbTituloSolicitud = new JLabel("Registro de solicitudes de Servicios");
			lbTituloSolicitud.setFont(new Font("Tahoma", Font.BOLD, 18));
		}
		return lbTituloSolicitud;
	}

	private JPanel getPnCenterSolicitud() {
		if (pnCenterSolicitud == null) {
			pnCenterSolicitud = new JPanel();
			pnCenterSolicitud.setLayout(new GridLayout(6, 1, 0, 50));
			pnCenterSolicitud.add(getPnSolicitudDni());
			pnCenterSolicitud.add(getPnSolicitudCorreo());
			pnCenterSolicitud.add(getPnSolicitudDescripcion());
			pnCenterSolicitud.add(getPnSolicitudUrgente());
		}
		return pnCenterSolicitud;
	}

	private JPanel getPnSolicitudDni() {
		if (pnSolicitudDni == null) {
			pnSolicitudDni = new JPanel();
			pnSolicitudDni.setLayout(new GridLayout(2, 1, 0, 0));
			pnSolicitudDni.add(getLbSolicitudDni());
			pnSolicitudDni.add(getTextField_3());
		}
		return pnSolicitudDni;
	}

	private JLabel getLbSolicitudDni() {
		if (lbSolicitudDni == null) {
			lbSolicitudDni = new JLabel("Introduzca su DNI:");
			lbSolicitudDni.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lbSolicitudDni;
	}

	private JTextField getTextField_3() {
		if (txSolicitudDni == null) {
			txSolicitudDni = new JTextField();
			txSolicitudDni.setFont(new Font("Tahoma", Font.PLAIN, 16));
			txSolicitudDni.setColumns(10);
		}
		return txSolicitudDni;
	}

	private JPanel getPnSolicitudCorreo() {
		if (pnSolicitudCorreo == null) {
			pnSolicitudCorreo = new JPanel();
			pnSolicitudCorreo.setLayout(new GridLayout(2, 1, 0, 0));
			pnSolicitudCorreo.add(getLbSolicitudCorreo());
			pnSolicitudCorreo.add(getTextField_1_1());
		}
		return pnSolicitudCorreo;
	}

	private JLabel getLbSolicitudCorreo() {
		if (lbSolicitudCorreo == null) {
			lbSolicitudCorreo = new JLabel("Introduzca su correo electronico:");
			lbSolicitudCorreo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lbSolicitudCorreo;
	}

	private JTextField getTextField_1_1() {
		if (txSolicitudCorreo == null) {
			txSolicitudCorreo = new JTextField();
			txSolicitudCorreo.setFont(new Font("Tahoma", Font.PLAIN, 16));
			txSolicitudCorreo.setColumns(10);
		}
		return txSolicitudCorreo;
	}

	private JPanel getPnSolicitudDescripcion() {
		if (pnSolicitudDescripcion == null) {
			pnSolicitudDescripcion = new JPanel();
			pnSolicitudDescripcion.setLayout(new GridLayout(2, 1, 0, 0));
			pnSolicitudDescripcion.add(getLbSolicitudDescripcion());
			pnSolicitudDescripcion.add(getScrollPane());
		}
		return pnSolicitudDescripcion;
	}

	private JLabel getLbSolicitudDescripcion() {
		if (lbSolicitudDescripcion == null) {
			lbSolicitudDescripcion = new JLabel("Introduzca una descripci\u00F3n de lo que desea:");
			lbSolicitudDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lbSolicitudDescripcion;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTextField_2_1());
		}
		return scrollPane;
	}

	private JTextField getTextField_2_1() {
		if (txSolicitudDescripcion == null) {
			txSolicitudDescripcion = new JTextField();
			txSolicitudDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 16));
			txSolicitudDescripcion.setColumns(10);
		}
		return txSolicitudDescripcion;
	}

	private JPanel getPnSolicitudUrgente() {
		if (pnSolicitudUrgente == null) {
			pnSolicitudUrgente = new JPanel();
			pnSolicitudUrgente.setLayout(new GridLayout(2, 1, 0, 0));
			pnSolicitudUrgente.add(getLbSolicitudUrgente());
			pnSolicitudUrgente.add(getPnSolicitudRadioButton());
		}
		return pnSolicitudUrgente;
	}

	private JLabel getLbSolicitudUrgente() {
		if (lbSolicitudUrgente == null) {
			lbSolicitudUrgente = new JLabel("Seleccione la urgencia de la solicitud:");
			lbSolicitudUrgente.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lbSolicitudUrgente;
	}

	private JPanel getPnSolicitudRadioButton() {
		if (pnSolicitudRadioButton == null) {
			pnSolicitudRadioButton = new JPanel();
			pnSolicitudRadioButton.add(getRbSolicitudNormal());
			pnSolicitudRadioButton.add(getRbSolicitudUrgente());
		}
		return pnSolicitudRadioButton;
	}

	private JRadioButton getRbSolicitudNormal() {
		if (rbSolicitudNormal == null) {
			rbSolicitudNormal = new JRadioButton("Normal");
			rbSolicitudNormal.setSelected(true);
			rbSolicitudNormal.setFont(new Font("Tahoma", Font.PLAIN, 16));
			buttonGroup.add(rbSolicitudNormal);
		}
		return rbSolicitudNormal;
	}

	private JRadioButton getRbSolicitudUrgente() {
		if (rbSolicitudUrgente == null) {
			rbSolicitudUrgente = new JRadioButton("Urgente");
			rbSolicitudUrgente.setFont(new Font("Tahoma", Font.PLAIN, 16));
			buttonGroup.add(rbSolicitudUrgente);
		}
		return rbSolicitudUrgente;
	}

	private JPanel getPnSouthSolicitud() {
		if (pnSouthSolicitud == null) {
			pnSouthSolicitud = new JPanel();
			pnSouthSolicitud.setLayout(new GridLayout(0, 4, 5, 0));
			pnSouthSolicitud.add(getBtnRegistrarSolicitud());
			pnSouthSolicitud.add(getBtnInscripcionToInicio_1_1());
		}
		return pnSouthSolicitud;
	}

	private DefaultButton getBtnRegistrarSolicitud() {
		if (btnRegistrarSolicitud == null) {
			btnRegistrarSolicitud = new DefaultButton("Registrar Solicitud");
			btnRegistrarSolicitud.setPreferredSize(new Dimension(250, 59));
			btnRegistrarSolicitud.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String dni = txSolicitudDni.getText().trim();
					String correo = txSolicitudCorreo.getText().trim();
					String descripcion = txSolicitudDescripcion.getText().trim();
					int urgencia = rbSolicitudUrgente.isSelected() ? 1 : 0;

					if (dni.length() != 9) {
						JOptionPane.showMessageDialog(pnSolicitudServicios, "Introduzca un formato de dni valido");
						return;
					}
					if (correo.isEmpty()) {
						JOptionPane.showMessageDialog(pnSolicitudServicios,
								"Es necesario introducir un correo electronico");
						return;
					}
					if (descripcion.isEmpty()) {
						JOptionPane.showMessageDialog(pnSolicitudServicios, "Es necesario incluir una descripcion");
						return;
					}

					SolicitudServiciosDto s = new SolicitudServiciosDto();
					s.CorreoElectronico = correo;
					s.DNI = dni;
					s.Descripcion = descripcion;
					s.Urgente = urgencia;

					SolicitudServicios.insertSolicitudServicios(s);

					JOptionPane.showMessageDialog(pnSolicitudServicios, "Se ha registrado la solicitud correctamente");

				}
			});
			btnRegistrarSolicitud.setHorizontalAlignment(SwingConstants.LEFT);
			btnRegistrarSolicitud.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return btnRegistrarSolicitud;
	}

	private DefaultButton getBtnInscripcionToInicio_1_1() {
		if (btnInscripcionToInicio_1_1 == null) {
			btnInscripcionToInicio_1_1 = new DefaultButton("Volver a inicio", "ventana", "VolverAInicio", 'v',
					ButtonColor.NORMAL);
			btnInscripcionToInicio_1_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
				}
			});
			btnInscripcionToInicio_1_1.setPreferredSize(new Dimension(250, 59));
			btnInscripcionToInicio_1_1.setMinimumSize(new Dimension(245, 59));
			btnInscripcionToInicio_1_1.setMaximumSize(new Dimension(245, 59));
			btnInscripcionToInicio_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return btnInscripcionToInicio_1_1;
	}

	private JPanel getPnAsignacionSolicitudesServicios() {
		if (pnAsignacionSolicitudesServicios == null) {
			pnAsignacionSolicitudesServicios = new JPanel();
			pnAsignacionSolicitudesServicios.setLayout(new BorderLayout(0, 20));
			pnAsignacionSolicitudesServicios.add(getPnAsignacionSolicitudesTitulo(), BorderLayout.NORTH);
			pnAsignacionSolicitudesServicios.add(getPnAsignacionSolicitudesCenter(), BorderLayout.CENTER);
			pnAsignacionSolicitudesServicios.add(getPnAsignacionSolicitudesButton(), BorderLayout.SOUTH);
		}
		return pnAsignacionSolicitudesServicios;
	}

	private JPanel getPnAsignacionSolicitudesTitulo() {
		if (pnAsignacionSolicitudesTitulo == null) {
			pnAsignacionSolicitudesTitulo = new JPanel();
			pnAsignacionSolicitudesTitulo.add(getLbAsignacionSolicitudTitulo());
		}
		return pnAsignacionSolicitudesTitulo;
	}

	private JLabel getLbAsignacionSolicitudTitulo() {
		if (lbAsignacionSolicitudTitulo == null) {
			lbAsignacionSolicitudTitulo = new JLabel("Asignacion de Solicitudes de Servicios");
			lbAsignacionSolicitudTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return lbAsignacionSolicitudTitulo;
	}

	private JPanel getPnAsignacionSolicitudesCenter() {
		if (pnAsignacionSolicitudesCenter == null) {
			pnAsignacionSolicitudesCenter = new JPanel();
			pnAsignacionSolicitudesCenter.setLayout(new GridLayout(0, 2, 0, 0));
			pnAsignacionSolicitudesCenter.add(getPnAsignacionSolicitudes());
			pnAsignacionSolicitudesCenter.add(getPnAsignacionSolicitudesPeritos());
		}
		return pnAsignacionSolicitudesCenter;
	}

	private JPanel getPnAsignacionSolicitudes() {
		if (pnAsignacionSolicitudes == null) {
			pnAsignacionSolicitudes = new JPanel();
			pnAsignacionSolicitudes.setLayout(new BorderLayout(0, 0));
			pnAsignacionSolicitudes.add(getLbAsignacionSolicitudes(), BorderLayout.NORTH);
			pnAsignacionSolicitudes.add(getSpAsignacionSolicitudes());
		}
		return pnAsignacionSolicitudes;
	}

	private JLabel getLbAsignacionSolicitudes() {
		if (lbAsignacionSolicitudes == null) {
			lbAsignacionSolicitudes = new JLabel("Solicitudes de Servicios:");
			lbAsignacionSolicitudes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lbAsignacionSolicitudes;
	}

	private JScrollPane getSpAsignacionSolicitudes() {
		if (spAsignacionSolicitudes == null) {
			spAsignacionSolicitudes = new JScrollPane();
			spAsignacionSolicitudes.setViewportView(getTbAsignacionSolicitudes());
		}
		return spAsignacionSolicitudes;
	}

	private JTable getTbAsignacionSolicitudes() {
		if (tbAsignacionSolicitudes == null) {
			tbAsignacionSolicitudes = new JTable();
			tbAsignacionSolicitudes.setIntercellSpacing(new Dimension(0, 0));
			tbAsignacionSolicitudes.setShowGrid(false);
			tbAsignacionSolicitudes.setRowMargin(0);
			tbAsignacionSolicitudes.setRequestFocusEnabled(false);
			tbAsignacionSolicitudes.setFocusable(false);
			tbAsignacionSolicitudes.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbAsignacionSolicitudes.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbAsignacionSolicitudes.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbAsignacionSolicitudes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbAsignacionSolicitudes.setShowVerticalLines(false);
			tbAsignacionSolicitudes.setOpaque(false);

			tbAsignacionSolicitudes.setRowHeight(80);
			tbAsignacionSolicitudes.setGridColor(new Color(255, 255, 255));

			tbAsignacionSolicitudes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return tbAsignacionSolicitudes;
	}

	private JPanel getPnAsignacionSolicitudesPeritos() {
		if (pnAsignacionSolicitudesPeritos == null) {
			pnAsignacionSolicitudesPeritos = new JPanel();
			pnAsignacionSolicitudesPeritos.setLayout(new BorderLayout(0, 0));
			pnAsignacionSolicitudesPeritos.add(getLbAsignacionSolicitudesPeritos(), BorderLayout.NORTH);
			pnAsignacionSolicitudesPeritos.add(getSpAsignacionSolicitudesPeritos());
		}
		return pnAsignacionSolicitudesPeritos;
	}

	private JLabel getLbAsignacionSolicitudesPeritos() {
		if (lbAsignacionSolicitudesPeritos == null) {
			lbAsignacionSolicitudesPeritos = new JLabel("Lista de Peritos:");
			lbAsignacionSolicitudesPeritos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lbAsignacionSolicitudesPeritos;
	}

	private JScrollPane getSpAsignacionSolicitudesPeritos() {
		if (spAsignacionSolicitudesPeritos == null) {
			spAsignacionSolicitudesPeritos = new JScrollPane();
			spAsignacionSolicitudesPeritos.setViewportView(getTbAsignacionSolicitudesPeritos());
		}
		return spAsignacionSolicitudesPeritos;
	}

	private JTable getTbAsignacionSolicitudesPeritos() {
		if (tbAsignacionSolicitudesPeritos == null) {
			tbAsignacionSolicitudesPeritos = new JTable();
			tbAsignacionSolicitudesPeritos.setIntercellSpacing(new Dimension(0, 0));
			tbAsignacionSolicitudesPeritos.setShowGrid(false);
			tbAsignacionSolicitudesPeritos.setRowMargin(0);
			tbAsignacionSolicitudesPeritos.setRequestFocusEnabled(false);
			tbAsignacionSolicitudesPeritos.setFocusable(false);
			tbAsignacionSolicitudesPeritos.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbAsignacionSolicitudesPeritos.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbAsignacionSolicitudesPeritos.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbAsignacionSolicitudesPeritos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbAsignacionSolicitudesPeritos.setShowVerticalLines(false);
			tbAsignacionSolicitudesPeritos.setOpaque(false);

			tbAsignacionSolicitudesPeritos.setRowHeight(LookAndFeel.ROW_HEIGHT);
			tbAsignacionSolicitudesPeritos.setGridColor(new Color(255, 255, 255));

			tbAsignacionSolicitudesPeritos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		}
		return tbAsignacionSolicitudesPeritos;
	}

	private JPanel getPnAsignacionSolicitudesButton() {
		if (pnAsignacionSolicitudesButton == null) {
			pnAsignacionSolicitudesButton = new JPanel();
			pnAsignacionSolicitudesButton.add(getBtAsignarSolicitud());
			pnAsignacionSolicitudesButton.add(getBtnInscripcionToInicio_1());
		}
		return pnAsignacionSolicitudesButton;
	}

	private DefaultButton getBtAsignarSolicitud() {
		if (btAsignarSolicitud == null) {
			btAsignarSolicitud = new DefaultButton("Asignar Solicitud");
			btAsignarSolicitud.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int indexSolicitudes = getTbAsignacionSolicitudes().getSelectedRow();
					int indexPeritos = getTbAsignacionSolicitudesPeritos().getSelectedRow();

					SolicitudServiciosDto solicitud = listaSolicitudesServicios.get(indexSolicitudes);
					ColegiadoDto perito = listaPeritosOrdenada.get(indexPeritos);

					SolicitudServicios.AsociaSolicitudServicio(solicitud, perito);

					ActualizaTablasSolicitudesServicios();
				}
			});
			btAsignarSolicitud.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return btAsignarSolicitud;
	}

	private DefaultButton getBtnInscripcionToInicio_1() {
		if (btnInscripcionToInicio_1 == null) {
			btnInscripcionToInicio_1 = new DefaultButton("Volver a inicio", "ventana", "VolverAInicio", 'v',
					ButtonColor.NORMAL);
			btnInscripcionToInicio_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
				}
			});
			btnInscripcionToInicio_1.setPreferredSize(new Dimension(250, 59));
			btnInscripcionToInicio_1.setMinimumSize(new Dimension(245, 59));
			btnInscripcionToInicio_1.setMaximumSize(new Dimension(245, 59));
			btnInscripcionToInicio_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return btnInscripcionToInicio_1;
	}

	private void ActualizaTablasSolicitudesServicios() {
		listaSolicitudesServicios = SolicitudServicios.listarSolicitudesServicios();
		listaPeritosOrdenada = Perito.listarPeritosOrdenados();
		getBtAsignarSolicitud().setEnabled(true);

		TableModel tableModelSolicitudes = new ModeloSolicitudServicios(listaSolicitudesServicios).getSolicitudModel();
		TableModel tableModelPeritos = new ModeloPeritos(listaPeritosOrdenada).getPeritoModel();
		getTbAsignacionSolicitudes().setModel(tableModelSolicitudes);
		getTbAsignacionSolicitudesPeritos().setModel(tableModelPeritos);

		if (listaSolicitudesServicios.isEmpty() || listaPeritosOrdenada.isEmpty()) {
			getBtAsignarSolicitud().setEnabled(false);
		} else {
			getTbAsignacionSolicitudes().setRowSelectionInterval(0, 0);
			getTbAsignacionSolicitudesPeritos().setRowSelectionInterval(0, 0);
		}

	}

	private DefaultButton getBtHomeSolicitudServicios() {
		if (btHomeSolicitudServicios == null) {
			btHomeSolicitudServicios = new DefaultButton("Darse de alta", "ventana", "AltaColegiado", 'l',
					ButtonColor.NORMAL);
			btHomeSolicitudServicios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, SOLICITUD_SERVICIOS);
				}
			});
			btHomeSolicitudServicios.setText("Solicitud de Servicios");
		}
		return btHomeSolicitudServicios;
	}

	private DefaultButton getBtHomeAsignacionSolicitudServicios() {
		if (btHomeAsignacionSolicitudServicios == null) {
			btHomeAsignacionSolicitudServicios = new DefaultButton("Darse de alta", "ventana", "AltaColegiado", 'l',
					ButtonColor.NORMAL);
			btHomeAsignacionSolicitudServicios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, ASIGNACION_SOLICITUD_SERVICIOS);
					ActualizaTablasSolicitudesServicios();
				}
			});
			btHomeAsignacionSolicitudServicios.setText("Asignacion Solicitud de Servicios");
		}
		return btHomeAsignacionSolicitudServicios;
	}

	private JPanel getPnTituloCurso() {
		if (pnTituloCurso == null) {
			pnTituloCurso = new JPanel();
			pnTituloCurso.setLayout(new GridLayout(0, 2, 0, 0));
			pnTituloCurso.add(getLblTituloCurso());
			pnTituloCurso.add(getTxtTituloCurso());
		}
		return pnTituloCurso;
	}

	private JLabel getLblTituloCurso() {
		if (lblTituloCurso == null) {
			lblTituloCurso = new JLabel("Titulo");
		}
		return lblTituloCurso;
	}

	private JTextField getTxtTituloCurso() {
		if (txtTituloCurso == null) {
			txtTituloCurso = new JTextField();
			txtTituloCurso.setColumns(10);
		}
		return txtTituloCurso;
	}

	private JPanel getPnProfesores() {
		if (pnProfesores == null) {
			pnProfesores = new JPanel();
			pnProfesores.setLayout(new GridLayout(2, 2, 0, 0));
			pnProfesores.add(getPnSpinnerProfesores());
			pnProfesores.add(getPnSesionesCurso());
		}
		return pnProfesores;
	}

	private JPanel getPnSpinnerProfesores() {
		if (pnSpinnerProfesores == null) {
			pnSpinnerProfesores = new JPanel();
			pnSpinnerProfesores.setLayout(new GridLayout(1, 2, 0, 0));
			pnSpinnerProfesores.add(getLblProfesores());
			pnSpinnerProfesores.add(getCbProfesores());
		}
		return pnSpinnerProfesores;
	}

	private JLabel getLblProfesores() {
		if (lblProfesores == null) {
			lblProfesores = new JLabel("Selecciona profesor para impartir curso:");
		}
		return lblProfesores;
	}

	private JComboBox<String> getCbProfesores() {
		if (cbProfesores == null) {
			cbProfesores = new JComboBox<String>();
			List<ProfesorDto> profesores = ProfesorCRUD.listProfesoresLibres();
			List<String> nombreProfesores = new ArrayList<>();
			for (ProfesorDto p : profesores) {
				nombreProfesores.add(p.nombre);
			}

			cbProfesores.setModel(new DefaultComboBoxModel(nombreProfesores.toArray()));
		}
		return cbProfesores;
	}

	private JPanel getPnSesionesCurso() {
		if (pnSesionesCurso == null) {
			pnSesionesCurso = new JPanel();
			pnSesionesCurso.setLayout(null);
			pnSesionesCurso.add(getLblFechaSesion_1());
			pnSesionesCurso.add(getTxtFechaSesion());
			pnSesionesCurso.add(getLblHoraInicio());
			pnSesionesCurso.add(getTxtHoraInicio());
			pnSesionesCurso.add(getLblHoraFin());
			pnSesionesCurso.add(getTxtHoraFin());
			pnSesionesCurso.add(getBtnAnadirSesion());
			pnSesionesCurso.add(getSpListaSesiones());
			pnSesionesCurso.add(getBtnBorrarSesion());
		}
		return pnSesionesCurso;
	}

	private JLabel getLblFechaSesion_1() {
		if (lblFechaSesion == null) {
			lblFechaSesion = new JLabel("Fecha sesion: ");
			lblFechaSesion.setBounds(0, 13, 99, 16);
		}
		return lblFechaSesion;
	}

	private JTextField getTxtFechaSesion() {
		if (txtFechaSesion == null) {
			txtFechaSesion = new JTextField();
			txtFechaSesion.setColumns(10);
			txtFechaSesion.setBounds(68, 11, 116, 22);
		}
		return txtFechaSesion;
	}

	private JLabel getLblHoraInicio() {
		if (lblHoraInicio == null) {
			lblHoraInicio = new JLabel("Hora inicio: ");
			lblHoraInicio.setBounds(0, 45, 148, 16);
		}
		return lblHoraInicio;
	}

	private JTextField getTxtHoraInicio() {
		if (txtHoraInicio == null) {
			txtHoraInicio = new JTextField();
			txtHoraInicio.setColumns(10);
			txtHoraInicio.setBounds(68, 43, 116, 22);
		}
		return txtHoraInicio;
	}

	private JLabel getLblHoraFin() {
		if (lblHoraFin == null) {
			lblHoraFin = new JLabel("Hora fin: ");
			lblHoraFin.setBounds(0, 80, 148, 16);
		}
		return lblHoraFin;
	}

	private JTextField getTxtHoraFin() {
		if (txtHoraFin == null) {
			txtHoraFin = new JTextField();
			txtHoraFin.setColumns(10);
			txtHoraFin.setBounds(68, 78, 116, 22);
		}
		return txtHoraFin;
	}

	private JButton getBtnAnadirSesion() {
		if (btnAnadirSesion == null) {
			btnAnadirSesion = new JButton("A\u00F1adir sesion");
			btnAnadirSesion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Fecha vacna
					if (txtFechaSesion.getText().isEmpty()) {
						JOptionPane.showMessageDialog(pnSesionesCurso, "La fecha de sesinn estn vacna");
						txtFechaSesion.grabFocus();
						return;
					}
					// Formato errnneo fecha
					LocalDate fechaSesion = null;
					try {
						fechaSesion = LocalDate.parse(txtFechaSesion.getText());
					} catch (DateTimeParseException d) {
						JOptionPane.showMessageDialog(pnSesionesCurso, "El formato de fecha es errnneo");
						txtFechaSesion.setText("");
						txtFechaSesion.grabFocus();
						return;
					}

					// Fecha despuns a la actual
					if (fechaSesion.isBefore(LocalDate.now())) {
						JOptionPane.showMessageDialog(pnSesionesCurso,
								"La fecha de inicio no puede ser anterior a la actual");
						txtFechaSesion.setText("");
						txtFechaSesion.grabFocus();
					}

					// Hora inicio vacna
					if (txtHoraInicio.getText().isEmpty()) {
						JOptionPane.showMessageDialog(pnSesionesCurso, "La hora de inicio estn vacna");
						txtHoraInicio.grabFocus();
						return;
					}

					LocalTime horaInicio = null;

					// Formato errnneo hora inicio
					try {
						horaInicio = LocalTime.parse(txtHoraInicio.getText());
					} catch (DateTimeParseException d) {
						JOptionPane.showMessageDialog(pnSesionesCurso, "El formato de hora de inicio es errnneo");
						txtHoraInicio.setText("");
						txtHoraInicio.grabFocus();
						return;
					}

					// Hora inicio vacna
					if (txtHoraFin.getText().isEmpty()) {
						JOptionPane.showMessageDialog(pnSesionesCurso, "La hora de fin estn vacna");
						txtHoraFin.grabFocus();
						return;
					}

					LocalTime horaFin = null;

					// Formato errnneo hora inicio
					try {
						horaFin = LocalTime.parse(txtHoraFin.getText());
					} catch (DateTimeParseException d) {
						JOptionPane.showMessageDialog(pnSesionesCurso, "El formato de hora de fin es errnneo");
						txtHoraFin.setText("");
						txtHoraFin.grabFocus();
						return;
					}
					// Comprobar que la fecha de inicio y fin no son la misma
					if (horaInicio.compareTo(horaFin) == 0) {
						JOptionPane.showMessageDialog(pnSesionesCurso,
								"La fecha de inicio no puede ser la misa que la fecha de fin");
						return;
					}

					// Comprobar que la fecha de fin estn despuns de la de inicio
					if (horaInicio.isAfter(horaFin)) {
						JOptionPane.showMessageDialog(pnSesionesCurso,
								"La fecha de inicio no puede estar despuns que la fecha de fin");
						return;
					}

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

					LocalDateTime fechaInicio = LocalDateTime
							.parse(txtFechaSesion.getText() + " " + txtHoraInicio.getText(), formatter);
					LocalDateTime fechaFin = LocalDateTime.parse(txtFechaSesion.getText() + " " + txtHoraFin.getText(),
							formatter);

					SesionDto sesion = new SesionDto();

					sesion.horaInicio = fechaInicio;
					sesion.horaFin = fechaFin;

					for (int i = 0; i < fechasCurso.size(); i++) {
						LocalDateTime first = fechasCurso.get(i).horaInicio;
						LocalDateTime second = fechasCurso.get(i).horaFin;
						if (fechaInicio.isAfter(first) && fechaInicio.isBefore(second)
								|| fechaFin.isAfter(first) && fechaFin.isBefore(second)
								|| fechaInicio.compareTo(first) == 0 || fechaFin.compareTo(second) == 0) {
							txtHoraInicio.setText("");
							txtHoraFin.setText("");
							JOptionPane.showMessageDialog(pnSesionesCurso, "La sesinn se solapa con las anteriores");
							return;
						}
					}

					fechasCurso.add(sesion);

					JOptionPane.showMessageDialog(pnSesionesCurso,
							"Anadida la siguiente sesinn al curso: \nFecha: " + txtFechaSesion.getText()
									+ "\nHora inicio: " + txtHoraInicio.getText() + "\nHora fin: "
									+ txtHoraFin.getText());

					modeloSesiones.addElement(sesion);
					txtFechaSesion.setText("");
					txtHoraInicio.setText("");
					txtHoraFin.setText("");

				}
			});
			btnAnadirSesion.setBounds(49, 113, 128, 25);
		}
		return btnAnadirSesion;
	}

	private JScrollPane getSpListaSesiones() {
		if (spListaSesiones == null) {
			spListaSesiones = new JScrollPane();
			spListaSesiones.setBounds(194, 13, 156, 81);
			spListaSesiones.setViewportView(getListSesiones());
		}
		return spListaSesiones;
	}

	private JList<SesionDto> getListSesiones() {
		if (listSesiones == null) {
			listSesiones = new JList<SesionDto>();
			modeloSesiones = new DefaultListModel<SesionDto>();
			listSesiones.setModel(modeloSesiones);

		}
		return listSesiones;
	}

	private JButton getBtnBorrarSesion() {
		if (btnBorrarSesion == null) {
			btnBorrarSesion = new JButton("Borrar sesion");
			btnBorrarSesion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (Object o : listSesiones.getSelectedValuesList()) {
						modeloSesiones.removeElement(o);
						fechasCurso.remove(o);
					}
				}
			});
			btnBorrarSesion.setBounds(211, 113, 116, 25);
		}
		return btnBorrarSesion;
	}

	private JPanel getPnListaProfesionalesPeritos() {
		if (pnListaProfesionalesPeritos == null) {
			pnListaProfesionalesPeritos = new JPanel();
			pnListaProfesionalesPeritos.setLayout(new BorderLayout(0, 0));
			pnListaProfesionalesPeritos.add(getPnBotones(), BorderLayout.SOUTH);
			pnListaProfesionalesPeritos.add(getPnPedirDNI(), BorderLayout.NORTH);
			pnListaProfesionalesPeritos.add(getPnListaPeritos(), BorderLayout.CENTER);
		}
		return pnListaProfesionalesPeritos;
	}

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnBotones.add(getBtnAddPerito());
			pnBotones.add(getBtnRenovarPerito());
		}
		return pnBotones;
	}

	private JButton getBtnAddPerito() {
		if (btnAddPerito == null) {
			btnAddPerito = new JButton("A\u00F1adir perito");
			btnAddPerito.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String dni = txtDNIPerito.getText();
					if (dni.isEmpty()) {
						JOptionPane.showMessageDialog(pnListaProfesionalesPeritos,
								"Por favor introduzca su " + "DNI para realizar cualquier operaci�n");
						return;
					}
					ColegiadoDto colegiado = ColegiadoCrud.findColegiadoDni(dni);
					if (colegiado != null) {
						if (colegiado.perito == null) {
							if (JOptionPane.showConfirmDialog(pnListaProfesionalesPeritos,
									"�Quieres inscribirte como perito en las listas?\n" + "Nombre: " + colegiado.nombre
											+ "\n" + "Apellidos: " + colegiado.apellidos) == JOptionPane.YES_OPTION) {
								PeritoCRUD.addPerito(dni);
								TableModel peritosModel = new ColegiadoModel(PeritoCRUD.findAllPeritosPosicion())
										.getPeritoModel();
								tbListadoPeritosProfesionales.setModel(peritosModel);
								JOptionPane.showMessageDialog(pnListaPeritosProfesionales,
										"Has sido a�adido a la lista con �xito");
							}
						} else {
							JOptionPane.showMessageDialog(pnListaPeritosProfesionales,
									"No es posible a�adir el perito");
						}
					} else {
						JOptionPane.showMessageDialog(pnListaPeritosProfesionales,
								"No existe colegiado con DNI: " + txtDNIPerito.getText());
					}
				}
			});
		}
		return btnAddPerito;
	}

	private JButton getBtnRenovarPerito() {
		if (btnRenovarPerito == null) {
			btnRenovarPerito = new JButton("Renovar perito");
			btnRenovarPerito.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String dni = txtDNIPerito.getText();
					if (dni.isEmpty()) {
						JOptionPane.showMessageDialog(pnListaProfesionalesPeritos,
								"Por favor introduzca su " + "DNI para realizar cualquier operaci�n");
						return;
					}
					ColegiadoDto colegiado = ColegiadoCrud.findColegiadoDni(dni);
					if (colegiado != null) {
						if (colegiado.perito.equals(ColegiadoDto.Perito_estado.SIN_RENOVAR
								.toString())/* Si se puede renovar se renueva */) {
							if (JOptionPane.showConfirmDialog(pnListaProfesionalesPeritos,
									"�Quieres inscribirte como perito en las listas?\n" + "Nombre: " + colegiado.nombre
											+ "\n" + "Apellidos: " + colegiado.apellidos) == JOptionPane.YES_OPTION) {
								PeritoCRUD.renovarPerito(dni);
								TableModel peritosModel = new ColegiadoModel(PeritoCRUD.findAllPeritosPosicion())
										.getPeritoModel();
								tbListadoPeritosProfesionales.setModel(peritosModel);
								JOptionPane.showMessageDialog(pnListaPeritosProfesionales,
										"Has sido renovado a la lista con �xito");
							}
						} else {
							JOptionPane.showMessageDialog(pnListaPeritosProfesionales,
									"No es posible renovar el perito");
						}
					} else {
						JOptionPane.showMessageDialog(pnListaPeritosProfesionales,
								"No existe colegiado con DNI: " + txtDNIPerito.getText());
					}
				}
			});
		}
		return btnRenovarPerito;
	}

	private JPanel getPnPedirDNI() {
		if (pnPedirDNI == null) {
			pnPedirDNI = new JPanel();
			pnPedirDNI.add(getLblPedirDNI());
			pnPedirDNI.add(getTxtDNIPerito());
		}
		return pnPedirDNI;
	}

	private JLabel getLblPedirDNI() {
		if (lblPedirDNI == null) {
			lblPedirDNI = new JLabel("Introduzca su DNI por favor: ");
			lblPedirDNI.setFont(new Font("Tahoma", Font.PLAIN, 20));
		}
		return lblPedirDNI;
	}

	private JTextField getTxtDNIPerito() {
		if (txtDNIPerito == null) {
			txtDNIPerito = new JTextField();
			txtDNIPerito.setFont(new Font("Tahoma", Font.PLAIN, 20));
			txtDNIPerito.setColumns(10);
		}
		return txtDNIPerito;
	}

	private JPanel getPnListaPeritos() {
		if (pnListaPeritos == null) {
			pnListaPeritos = new JPanel();
			pnListaPeritos.setLayout(new BorderLayout(0, 0));
			pnListaPeritos.add(getLblListaPeritos(), BorderLayout.NORTH);
			pnListaPeritos.add(getPnListaPeritosProfesionales(), BorderLayout.CENTER);
		}
		return pnListaPeritos;
	}

	private JLabel getLblListaPeritos() {
		if (lblListaPeritos == null) {
			lblListaPeritos = new JLabel("Lista de peritos profesionales");
			lblListaPeritos.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblListaPeritos.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblListaPeritos;
	}

	private JPanel getPnListaPeritosProfesionales() {
		if (pnListaPeritosProfesionales == null) {
			pnListaPeritosProfesionales = new JPanel();
			pnListaPeritosProfesionales.setLayout(new BorderLayout(0, 0));
			pnListaPeritosProfesionales.add(getSpListaPeritos());
		}
		return pnListaPeritosProfesionales;
	}

	private JScrollPane getSpListaPeritos() {
		if (spListaPeritos == null) {
			spListaPeritos = new JScrollPane(getTbListadoPeritos());
		}
		return spListaPeritos;
	}

	private JTable getTbListadoPeritos() {
		if (tbListadoPeritosProfesionales == null) {
			tbListadoPeritosProfesionales = new JTable();
			tbListadoPeritosProfesionales.setIntercellSpacing(new Dimension(0, 0));
			tbListadoPeritosProfesionales.setShowGrid(false);
			tbListadoPeritosProfesionales.setRowMargin(0);
			tbListadoPeritosProfesionales.setRequestFocusEnabled(false);
			tbListadoPeritosProfesionales.setFocusable(false);
			tbListadoPeritosProfesionales.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbListadoPeritosProfesionales.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbListadoPeritosProfesionales.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbListadoPeritosProfesionales.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbListadoPeritosProfesionales.setShowVerticalLines(false);
			tbListadoPeritosProfesionales.setOpaque(false);

			tbListadoPeritosProfesionales.setRowHeight(LookAndFeel.ROW_HEIGHT);
			tbListadoPeritosProfesionales.setGridColor(new Color(255, 255, 255));

			TableModel peritosModel = new ColegiadoModel(PeritoCRUD.findAllPeritosPosicion()).getPeritoModel();
			tbListadoPeritosProfesionales.setModel(peritosModel);

		}
		return tbListadoPeritosProfesionales;
	}

	private DefaultButton getBtListasProfesionales() {
		if (btListasProfesionales == null) {
			btListasProfesionales = new DefaultButton("Darse de alta", "ventana", "AltaColegiado", 'l',
					ButtonColor.NORMAL);
			btListasProfesionales.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, LISTAS_PROFESIONALES);
				}
			});
			btListasProfesionales.setText("Listas Profesionales");
		}
		return btListasProfesionales;
	}

	private JPanel getPnCancelarCursoCOIIPA() {
		if (pnCancelarCursoCOIIPA == null) {
			pnCancelarCursoCOIIPA = new JPanel();
			pnCancelarCursoCOIIPA.setLayout(new BorderLayout(0, 0));
			pnCancelarCursoCOIIPA.add(getPnCancelarCursoCOIIPACentro());
			pnCancelarCursoCOIIPA.add(getLblCancelarCursoCOIIPATitulo(), BorderLayout.NORTH);
			pnCancelarCursoCOIIPA.add(getPnCancelarCursosCOIIPAVolver(), BorderLayout.SOUTH);
		}
		return pnCancelarCursoCOIIPA;
	}

	private JPanel getPnCancelarCursoCOIIPACentro() {
		if (pnCancelarCursoCOIIPACentro == null) {
			pnCancelarCursoCOIIPACentro = new JPanel();
			pnCancelarCursoCOIIPACentro.setBorder(new TitledBorder(null, "Cursos abiertos del COIIPA",
					TitledBorder.CENTER, TitledBorder.TOP, null, null));
			pnCancelarCursoCOIIPACentro.setLayout(new GridLayout(2, 0, 0, 0));
			pnCancelarCursoCOIIPACentro.add(getPnCancelarCursoCOIIPACursos());
			pnCancelarCursoCOIIPACentro.add(getPnCancelarCursoCOIIPAInscripciones());
		}
		return pnCancelarCursoCOIIPACentro;
	}

	private JLabel getLblCancelarCursoCOIIPATitulo() {
		if (lblCancelarCursoCOIIPATitulo == null) {
			lblCancelarCursoCOIIPATitulo = new JLabel("Cancelación de cursos como COIIPA");
			lblCancelarCursoCOIIPATitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lblCancelarCursoCOIIPATitulo.setFont(LookAndFeel.HEADING_1_FONT);
		}
		return lblCancelarCursoCOIIPATitulo;
	}

	private JPanel getPnCancelarCursoCOIIPACursos() {
		if (pnCancelarCursoCOIIPACursos == null) {
			pnCancelarCursoCOIIPACursos = new JPanel();
			pnCancelarCursoCOIIPACursos.setLayout(new BorderLayout(0, 0));
			pnCancelarCursoCOIIPACursos.add(getPanelCancelarCursoCOIIPATabla(), BorderLayout.CENTER);
			pnCancelarCursoCOIIPACursos.add(getLblNewLabelCursosDisponibles(), BorderLayout.NORTH);
			pnCancelarCursoCOIIPACursos.add(getPanelCancelarCursoCOIIPACancelar(), BorderLayout.SOUTH);
		}
		return pnCancelarCursoCOIIPACursos;
	}

	private JPanel getPnCancelarCursoCOIIPAInscripciones() {
		if (pnCancelarCursoCOIIPAInscripciones == null) {
			pnCancelarCursoCOIIPAInscripciones = new JPanel();
			pnCancelarCursoCOIIPAInscripciones.setLayout(new BorderLayout(0, 0));
			pnCancelarCursoCOIIPAInscripciones.add(getLblNewLabelCancelarCursosCOIIPAInscripciones(),
					BorderLayout.NORTH);
			pnCancelarCursoCOIIPAInscripciones.add(getPnCancelarCursosCOIIPACancelaciones(), BorderLayout.CENTER);
		}
		return pnCancelarCursoCOIIPAInscripciones;
	}

	private JPanel getPanelCancelarCursoCOIIPATabla() {
		if (panelCancelarCursoCOIIPATabla == null) {
			panelCancelarCursoCOIIPATabla = new JPanel();
			panelCancelarCursoCOIIPATabla.setToolTipText("Selecciona un curso para cancelarlo");
			panelCancelarCursoCOIIPATabla.setLayout(new GridLayout(0, 1, 0, 0));
			panelCancelarCursoCOIIPATabla.add(getSpListadoCursos());
			paneMuestraCursosCentro.setLayout(new GridLayout(0, 1, 0, 0));
			paneMuestraCursosCentro.add(getScrollPaneCursos());
		}
		return panelCancelarCursoCOIIPATabla;
	}

	private JLabel getLblNewLabelCursosDisponibles() {
		if (lblNewLabelCursosDisponibles == null) {
			lblNewLabelCursosDisponibles = new JLabel("Lista de cursos disponibles");
			lblNewLabelCursosDisponibles.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelCursosDisponibles.setFont(LookAndFeel.HEADING_2_FONT);
		}
		return lblNewLabelCursosDisponibles;
	}

	private boolean fechaInvalida(LocalDate fechaInicio) {
		if (LocalDate.now().isBefore(fechaInicio)) {
			return false;
		}
		return true;
	}

	private JScrollPane getSpListadoCursos() {
		if (spListadoCursos == null) {
			spListadoCursos = new JScrollPane(getJTableListadoCursos());
		}
		return spListadoCursos;
	}

	private JTable getJTableListadoCursos() {
		if (tbListadoCursos == null) {
			tbListadoCursos = new JTable();
			tbListadoCursos.setIntercellSpacing(new Dimension(0, 0));
			tbListadoCursos.setShowGrid(false);
			tbListadoCursos.setRowMargin(0);
			tbListadoCursos.setRequestFocusEnabled(false);
			tbListadoCursos.setFocusable(false);
			tbListadoCursos.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbListadoCursos.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbListadoCursos.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbListadoCursos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbListadoCursos.setShowVerticalLines(false);
			tbListadoCursos.setOpaque(false);

			tbListadoCursos.setRowHeight(LookAndFeel.ROW_HEIGHT);
			tbListadoCursos.setGridColor(new Color(255, 255, 255));

			tbListadoCursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			try {
				tableModelL = new CursoModel(Curso.listarCursosAbiertosPlanificados())
						.getCursoModel(CursoModel.LISTA_CURSOS);

				tbListadoCursos.setModel(tableModelL);
			} catch (BusinessException e) {
				showMessage(e, MessageType.ERROR);
				e.printStackTrace();
			}

			tbListadoCursos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent event) {

					if (event.getValueIsAdjusting())
						return;

					cursoSeleccionado = new CursoDto();

					try {

						int selectedRow = tbListadoCursos.getSelectedRow();

						if (selectedRow == -1) {
							selectedRow = 0;
							cursoSeleccionado = null;
						}

						if (cursoSeleccionado != null) {
							cursoSeleccionado.titulo = tbListadoCursos.getValueAt(selectedRow, 0).toString();

							cursoSeleccionado.plazasDisponibles = Integer
									.parseInt(tbListadoCursos.getValueAt(selectedRow, 1).toString());

							cursoSeleccionado.numeroInscritos = Integer
									.parseInt(tbListadoCursos.getValueAt(selectedRow, 2).toString());

							cursoSeleccionado.precio = Double
									.parseDouble(tbListadoCursos.getValueAt(selectedRow, 3).toString());

							cursoSeleccionado.estado = tbListadoCursos.getValueAt(selectedRow, 4).toString();

							cursoSeleccionado.isCancelable = tbListadoCursos.getValueAt(selectedRow, 5)
									.toString() == "CANCELABLE" ? true : false;

							cursoSeleccionado.fechaInicio = LocalDate
									.parse(tbListadoCursos.getValueAt(selectedRow, 6).toString());

							cursoSeleccionado.codigoCurso = Integer
									.parseInt(tbListadoCursos.getValueAt(selectedRow, 7).toString());
						}

					} catch (NumberFormatException | ArrayIndexOutOfBoundsException nfe) {
					}

				}

			});
		}
		return tbListadoCursos;
	}

	private JLabel getLblNewLabelCancelarCursosCOIIPAInscripciones() {
		if (lblNewLabelCancelarCursosCOIIPAInscripciones == null) {
			lblNewLabelCancelarCursosCOIIPAInscripciones = new JLabel(
					"Inscripciones canceladas del curso seleccionado");
			lblNewLabelCancelarCursosCOIIPAInscripciones.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelCancelarCursosCOIIPAInscripciones.setFont(LookAndFeel.HEADING_2_FONT);
		}
		return lblNewLabelCancelarCursosCOIIPAInscripciones;
	}

	private JScrollPane getSpInscripcionesCanceladas() {
		if (spInscripcionesCanceladas == null) {
			spInscripcionesCanceladas = new JScrollPane(getJTableListadoTransferenciasCanceladas());
		}
		return spInscripcionesCanceladas;
	}

	private JTable getJTableListadoTransferenciasCanceladas() {
		if (tbListadoTransferenciasCanceladas == null) {
			tbListadoTransferenciasCanceladas = new JTable();
			tbListadoTransferenciasCanceladas.setIntercellSpacing(new Dimension(0, 0));
			tbListadoTransferenciasCanceladas.setShowGrid(false);
			tbListadoTransferenciasCanceladas.setRowMargin(0);
			tbListadoTransferenciasCanceladas.setRequestFocusEnabled(false);
			tbListadoTransferenciasCanceladas.setFocusable(false);
			tbListadoTransferenciasCanceladas.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbListadoTransferenciasCanceladas.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbListadoTransferenciasCanceladas.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbListadoTransferenciasCanceladas.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
			tbListadoTransferenciasCanceladas.setShowVerticalLines(false);
			tbListadoTransferenciasCanceladas.setOpaque(false);
			tbListadoTransferenciasCanceladas.setEnabled(false);

			tbListadoTransferenciasCanceladas.setRowHeight(115);
			tbListadoTransferenciasCanceladas.setGridColor(new Color(255, 255, 255));

			tbListadoTransferenciasCanceladas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		}
		return tbListadoTransferenciasCanceladas;
	}

	private JPanel getPnCancelarCursosCOIIPACancelaciones() {
		if (pnCancelarCursosCOIIPACancelaciones == null) {
			pnCancelarCursosCOIIPACancelaciones = new JPanel();
			pnCancelarCursosCOIIPACancelaciones.setLayout(new GridLayout(0, 1, 0, 0));
			pnCancelarCursosCOIIPACancelaciones.add(getSpInscripcionesCanceladas());
		}
		return pnCancelarCursosCOIIPACancelaciones;
	}

	private JPanel getPanelCancelarCursoCOIIPACancelar() {
		if (panelCancelarCursoCOIIPACancelar == null) {
			panelCancelarCursoCOIIPACancelar = new JPanel();
			panelCancelarCursoCOIIPACancelar.add(getBtnCancelarCursosCOIIPACancelarCursoBoton());
		}
		return panelCancelarCursoCOIIPACancelar;
	}

	private DefaultButton getBtnCancelarCursosCOIIPACancelarCursoBoton() {
		if (dfltbtnCancelarCurso == null) {
			dfltbtnCancelarCurso = new DefaultButton("Cancelar curso seleccionado", "ventana", "CancelaCurso", 'C',
					ButtonColor.NORMAL);
			dfltbtnCancelarCurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (cursoSeleccionado != null) {
						if (cursoSeleccionado.isCancelable == false) {
							JOptionPane.showMessageDialog(null, "El curso no es cancelable, luego no puede cancelarlo",
									"Curso no cancelable", JOptionPane.WARNING_MESSAGE);
						} else if (fechaInvalida(cursoSeleccionado.fechaInicio)) {
							JOptionPane.showMessageDialog(null,
									"No puede cancelar un curso que se está celebrando o ya se ha celebrado",
									"Fecha inválida", JOptionPane.WARNING_MESSAGE);
						} else {
							if (cursoSeleccionado.estado.equals("PLANIFICADO")) {
								JOptionPane.showMessageDialog(null,
										"Acaba de cancelar un curso, por lo que pasará a estado CANCELADO y no admitirá mas inscripciones\n"
												+ "Como el curso estaba PLANIFICADO y no tenía por defecto inscripciones asociadas, no hay ninguna inscripción cancelada registrada en el sistema",
										"Curso cancelado", JOptionPane.INFORMATION_MESSAGE);
								Curso.cancelarCursoCOIIPA(cursoSeleccionado);
							} else {
								JOptionPane.showMessageDialog(null,
										"Acaba de cancelar un curso, por lo que pasará a estado CANCELADO y no admitirá mas inscripciones\n"
												+ "Todas las inscripciones a este curso pasarán también a CANCELADO y se le reembolsará el 100% de la cuota pagada siempre que haya pagado\n"
												+ "Se mostrarán a continuación todos los detalles de las inscripciones al curso que han sido canceladas",
										"Curso cancelado", JOptionPane.INFORMATION_MESSAGE);
								Curso.cancelarCursoCOIIPA(cursoSeleccionado);
								InscripcionColegiado.cancelarInscripciones(cursoSeleccionado);
							}

							dfltbtnCancelarCurso.setEnabled(false);

							try {
								tableModelC = new InscripcionColegiadoModel(
										InscripcionColegiado.findInscripciones(cursoSeleccionado))
												.getCursoModel(InscripcionColegiadoModel.INSCRIPCIONES_CANCELADAS);
							} catch (BusinessException e1) {
								e1.printStackTrace();
							}
							tbListadoTransferenciasCanceladas.setModel(tableModelC);
							tbListadoTransferenciasCanceladas.repaint();
							spInscripcionesCanceladas.setVisible(true);
							pnCancelarCursoCOIIPAInscripciones.setVisible(true);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Selecciona un curso para poder cancelarlo, por favor",
								"Selecciona un curso", JOptionPane.WARNING_MESSAGE);
					}
				}
			});
			dfltbtnCancelarCurso.setText("Cancelar curso");
			dfltbtnCancelarCurso.setToolTipText("Pulse para cancelar el curso");
			dfltbtnCancelarCurso.setMnemonic('C');
		}
		return dfltbtnCancelarCurso;
	}

	private JPanel getPnCancelarCursosCOIIPAVolver() {
		if (pnCancelarCursosCOIIPAVolver == null) {
			pnCancelarCursosCOIIPAVolver = new JPanel();
			pnCancelarCursosCOIIPAVolver.add(getBtnCancelarCursoCOIIPAVolverAtras());
		}
		return pnCancelarCursosCOIIPAVolver;
	}

	private DefaultButton getBtnCancelarCursoCOIIPAVolverAtras() {
		if (btnCancelarCursoCOIIPAVolver == null) {
			btnCancelarCursoCOIIPAVolver = new DefaultButton("Volver a Inicio", "ventana", "VolverAInicio", 'v',
					ButtonColor.CANCEL);
			btnCancelarCursoCOIIPAVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (confirmarVolverPrincipio()) {
						mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
					}
				}
			});
			btnCancelarCursoCOIIPAVolver.setToolTipText("Pulsa para volver atrás");
			btnCancelarCursoCOIIPAVolver.setText("Volver");
			btnCancelarCursoCOIIPAVolver.setMnemonic('V');
		}
		return btnCancelarCursoCOIIPAVolver;
	}

	private JPanel getPnCancelarInscripcionCurso() {
		if (pnCancelarInscripcionCurso == null) {
			pnCancelarInscripcionCurso = new JPanel();
			pnCancelarInscripcionCurso.setLayout(new BorderLayout(0, 0));
			pnCancelarInscripcionCurso.add(getPnCancelarInscripcionCursoCentro(), BorderLayout.CENTER);
			pnCancelarInscripcionCurso.add(getPnCancelarInscripcionCursoVolver(), BorderLayout.SOUTH);
			pnCancelarInscripcionCurso.add(getLblCancelarInscipcionCursoTitulo(), BorderLayout.NORTH);
		}
		return pnCancelarInscripcionCurso;
	}

	private JPanel getPnCancelarInscripcionCursoCentro() {
		if (pnCancelarInscripcionCursoCentro == null) {
			pnCancelarInscripcionCursoCentro = new JPanel();
			pnCancelarInscripcionCursoCentro.setLayout(new GridLayout(2, 0, 0, 0));
			pnCancelarInscripcionCursoCentro.add(getPnCancelarInscripcionCursoCursosInscritos());
			pnCancelarInscripcionCursoCentro.add(getPnCancelarInscripcionCursoInscripciones());
		}
		return pnCancelarInscripcionCursoCentro;
	}

	private JPanel getPnCancelarInscripcionCursoVolver() {
		if (pnCancelarInscripcionCursoVolver == null) {
			pnCancelarInscripcionCursoVolver = new JPanel();
			pnCancelarInscripcionCursoVolver.add(getBtnCancelarInscripcionCursoVolver());
		}
		return pnCancelarInscripcionCursoVolver;
	}

	private JLabel getLblCancelarInscipcionCursoTitulo() {
		if (lblCancelarInscipcionCursoTitulo == null) {
			lblCancelarInscipcionCursoTitulo = new JLabel("Cancelar inscripción a cursos");
			lblCancelarInscipcionCursoTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lblCancelarInscipcionCursoTitulo.setFont(LookAndFeel.HEADING_1_FONT);
		}
		return lblCancelarInscipcionCursoTitulo;
	}

	private JButton getBtnCancelarInscripcionCursoVolver() {
		if (btnCancelarInscripcionCursoVolver == null) {
			btnCancelarInscripcionCursoVolver = new DefaultButton("Volver a Inicio", "ventana", "VolverAInicio", 'v',
					ButtonColor.CANCEL);
			btnCancelarInscripcionCursoVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (confirmarVolverPrincipio()) {
						mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
					}
				}
			});
			btnCancelarInscripcionCursoVolver.setToolTipText("Pulsa para volver atrás");
			btnCancelarInscripcionCursoVolver.setText("Volver");
			btnCancelarInscripcionCursoVolver.setMnemonic('V');
		}
		return btnCancelarInscripcionCursoVolver;
	}

	private JPanel getPnCancelarInscripcionCursoCursosInscritos() {
		if (pnCancelarInscripcionCursoCursosInscritos == null) {
			pnCancelarInscripcionCursoCursosInscritos = new JPanel();
			pnCancelarInscripcionCursoCursosInscritos.setLayout(new BorderLayout(0, 0));
			pnCancelarInscripcionCursoCursosInscritos.add(getPnCancelarInscripcionCursoCursosInscritosTabla(),
					BorderLayout.CENTER);
			pnCancelarInscripcionCursoCursosInscritos.add(getPnCancelarInscripcionCursoCursosInscritosCancelar(),
					BorderLayout.SOUTH);
			pnCancelarInscripcionCursoCursosInscritos.add(getLblNewLabelCursosInscritos(), BorderLayout.NORTH);
		}
		return pnCancelarInscripcionCursoCursosInscritos;
	}

	private JPanel getPnCancelarInscripcionCursoInscripciones() {
		if (pnCancelarInscripcionCursoInscripciones == null) {
			pnCancelarInscripcionCursoInscripciones = new JPanel();
			pnCancelarInscripcionCursoInscripciones.setLayout(new BorderLayout(0, 0));
			pnCancelarInscripcionCursoInscripciones.add(getLblNewLabelCancelarInscripciones(), BorderLayout.NORTH);
			pnCancelarInscripcionCursoInscripciones.add(getPnCancelarInscripcionCursoInscripcionesCanceladas());
		}
		return pnCancelarInscripcionCursoInscripciones;
	}

	private JPanel getPnCancelarInscripcionCursoCursosInscritosTabla() {
		if (pnCancelarInscripcionCursoCursosInscritosTabla == null) {
			pnCancelarInscripcionCursoCursosInscritosTabla = new JPanel();
			pnCancelarInscripcionCursoCursosInscritosTabla.setLayout(new GridLayout(1, 0, 0, 0));
			pnCancelarInscripcionCursoCursosInscritosTabla.add(getScrollPaneCursosInscritos());
		}
		return pnCancelarInscripcionCursoCursosInscritosTabla;
	}

	private JPanel getPnCancelarInscripcionCursoCursosInscritosCancelar() {
		if (pnCancelarInscripcionCursoCursosInscritosCancelar == null) {
			pnCancelarInscripcionCursoCursosInscritosCancelar = new JPanel();
			pnCancelarInscripcionCursoCursosInscritosCancelar.add(getBtnNewButtonInscripcionCancelada());
		}
		return pnCancelarInscripcionCursoCursosInscritosCancelar;
	}

	private JLabel getLblNewLabelCursosInscritos() {
		if (lblNewLabelCursosInscritos == null) {
			lblNewLabelCursosInscritos = new JLabel("Listado de cursos inscritos");
			lblNewLabelCursosInscritos.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelCursosInscritos.setFont(LookAndFeel.HEADING_2_FONT);
		}
		return lblNewLabelCursosInscritos;
	}

	private JLabel getLblNewLabelCancelarInscripciones() {
		if (lblNewLabelCancelarInscripciones == null) {
			lblNewLabelCancelarInscripciones = new JLabel("Inscripción cancelada del curso");
			lblNewLabelCancelarInscripciones.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabelCancelarInscripciones.setFont(LookAndFeel.HEADING_2_FONT);
		}
		return lblNewLabelCancelarInscripciones;
	}

	private JPanel getPnCancelarInscripcionCursoInscripcionesCanceladas() {
		if (pnCancelarInscripcionCursoInscripcionesCanceladas == null) {
			pnCancelarInscripcionCursoInscripcionesCanceladas = new JPanel();
			pnCancelarInscripcionCursoInscripcionesCanceladas.setLayout(new GridLayout(1, 0, 0, 0));
			pnCancelarInscripcionCursoInscripcionesCanceladas.add(getScrollPaneInscripcion());
		}
		return pnCancelarInscripcionCursoInscripcionesCanceladas;
	}

	private JScrollPane getScrollPaneInscripcion() {
		if (scrollPaneInscripcion == null) {
			scrollPaneInscripcion = new JScrollPane(getTbInscripcionCanceladaCurso());
		}
		return scrollPaneInscripcion;
	}

	private DefaultButton getBtnNewButtonInscripcionCancelada() {
		if (btnNewButtonInscripcionCancelada == null) {
			btnNewButtonInscripcionCancelada = new DefaultButton("Cancelar", "ventana", "CancelarInscripcion", 'C',
					ButtonColor.NORMAL);
			btnNewButtonInscripcionCancelada.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (cursoSeleccionado != null) {
						if (cursoSeleccionado.isCancelable == false) {
							JOptionPane.showMessageDialog(null, "El curso no es cancelable, luego no puede cancelarlo",
									"Curso no cancelable", JOptionPane.WARNING_MESSAGE);
						} else if (fechaInvalida(cursoSeleccionado.fechaInicio)) {
							JOptionPane.showMessageDialog(null,
									"No puede cancelar un curso cuando ya se ha celebrado o se está celebrando",

									"Fecha inválida", JOptionPane.WARNING_MESSAGE);
						} else if (fechaAntesSemana(cursoSeleccionado.fechaInicio)) {
							JOptionPane.showMessageDialog(null,
									"No puede cancelar un curso cuando queda menos de una semana para su celebración",
									"Fecha inválida", JOptionPane.WARNING_MESSAGE);
						} else {

							JOptionPane.showMessageDialog(null,
									"Acaba de cancelar su inscripción al curso, por lo que su inscripción pasará a estado CANCELADO\n"
											+ "Se le reembolsará el porcentaje de la cuota que ha pagado dependiendo de la política de devolución del curso (siempre que haya pagado)\n"
											+ "Se mostrarán a continuación todos los detalles de la inscripcion al curso que ha sido cancelada",
									"Curso cancelado", JOptionPane.INFORMATION_MESSAGE);
							InscripcionColegiado.cancelarInscripcion(cursoSeleccionado, getColegiadoDni());

							btnNewButtonInscripcionCancelada.setEnabled(false);

							try {
								tableModelE = new InscripcionColegiadoModel(
										InscripcionColegiado.findInscripcion(cursoSeleccionado, getColegiadoDni()))
												.getCursoModel(InscripcionColegiadoModel.INSCRIPCION_CANCELADA);
							} catch (BusinessException e1) {
								e1.printStackTrace();
							}
							tbInscripcionCanceladaCurso.setModel(tableModelE);
							tbInscripcionCanceladaCurso.repaint();
							scrollPaneInscripcion.setVisible(true);
							pnCancelarInscripcionCursoInscripciones.setVisible(true);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Selecciona un curso para poder cancelarlo, por favor",
								"Selecciona un curso", JOptionPane.WARNING_MESSAGE);
					}
				}
			});
			btnNewButtonInscripcionCancelada.setToolTipText("Pulse para cancelar el curso");
			btnNewButtonInscripcionCancelada.setMnemonic('C');
		}
		return btnNewButtonInscripcionCancelada;
	}

	private String getColegiadoDni() {
		return dniColegiado;
	}

	private boolean fechaAntesSemana(LocalDate fechaInicio) {
		return ChronoUnit.DAYS.between(LocalDate.now(), fechaInicio) < 7; // menos de una semana
	}

	private JScrollPane getScrollPaneCursosInscritos() {
		if (scrollPaneCursosInscritos == null) {
			scrollPaneCursosInscritos = new JScrollPane(getJTableListadoCursosInscritos());
		}
		return scrollPaneCursosInscritos;
	}

	private JTable getJTableListadoCursosInscritos() {
		if (tbListadoCursosInscrito == null) {
			tbListadoCursosInscrito = new JTable();
			tbListadoCursosInscrito.setIntercellSpacing(new Dimension(0, 0));
			tbListadoCursosInscrito.setShowGrid(false);
			tbListadoCursosInscrito.setRowMargin(0);
			tbListadoCursosInscrito.setRequestFocusEnabled(false);
			tbListadoCursosInscrito.setFocusable(false);
			tbListadoCursosInscrito.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbListadoCursosInscrito.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbListadoCursosInscrito.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbListadoCursosInscrito.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbListadoCursosInscrito.setShowVerticalLines(false);
			tbListadoCursosInscrito.setOpaque(false);

			tbListadoCursosInscrito.setRowHeight(LookAndFeel.ROW_HEIGHT);
			tbListadoCursosInscrito.setGridColor(new Color(255, 255, 255));

			tbListadoCursosInscrito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			try {
				tableModelF = new CursoModel(Curso.listarCursosIsInscrito(getColegiadoDni()))
						.getCursoModel(CursoModel.LISTA_CURSOS);

				tbListadoCursosInscrito.setModel(tableModelF);
			} catch (BusinessException e) {
				showMessage(e, MessageType.ERROR);
				e.printStackTrace();
			}

			tbListadoCursosInscrito.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent event) {

					if (event.getValueIsAdjusting())
						return;

					cursoSeleccionado = new CursoDto();

					try {

						int selectedRow = tbListadoCursosInscrito.getSelectedRow();

						if (selectedRow == -1) {
							selectedRow = 0;
							cursoSeleccionado = null;
						}

						if (cursoSeleccionado != null) {
							cursoSeleccionado.titulo = tbListadoCursosInscrito.getValueAt(selectedRow, 0).toString();

							cursoSeleccionado.plazasDisponibles = Integer
									.parseInt(tbListadoCursosInscrito.getValueAt(selectedRow, 1).toString());

							cursoSeleccionado.numeroInscritos = Integer
									.parseInt(tbListadoCursosInscrito.getValueAt(selectedRow, 2).toString());

							cursoSeleccionado.precio = Double
									.parseDouble(tbListadoCursosInscrito.getValueAt(selectedRow, 3).toString());

							cursoSeleccionado.estado = tbListadoCursosInscrito.getValueAt(selectedRow, 4).toString();

							cursoSeleccionado.isCancelable = tbListadoCursosInscrito.getValueAt(selectedRow, 5)
									.toString() == "CANCELABLE" ? true : false;

							cursoSeleccionado.fechaInicio = LocalDate
									.parse(tbListadoCursosInscrito.getValueAt(selectedRow, 6).toString());

							cursoSeleccionado.codigoCurso = Integer
									.parseInt(tbListadoCursosInscrito.getValueAt(selectedRow, 7).toString());

							cursoSeleccionado.porcentaje_devolucion = Double
									.parseDouble(tbListadoCursosInscrito.getValueAt(selectedRow, 8).toString());

						}

					} catch (NumberFormatException | ArrayIndexOutOfBoundsException nfe) {
					}

				}

			});
		}
		return tbListadoCursosInscrito;
	}

	private JTable getTbInscripcionCanceladaCurso() {
		if (tbInscripcionCanceladaCurso == null) {
			tbInscripcionCanceladaCurso = new JTable();
			tbInscripcionCanceladaCurso.setIntercellSpacing(new Dimension(0, 0));
			tbInscripcionCanceladaCurso.setShowGrid(false);
			tbInscripcionCanceladaCurso.setRowMargin(0);
			tbInscripcionCanceladaCurso.setRequestFocusEnabled(false);
			tbInscripcionCanceladaCurso.setFocusable(false);
			tbInscripcionCanceladaCurso.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbInscripcionCanceladaCurso.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbInscripcionCanceladaCurso.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbInscripcionCanceladaCurso.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
			tbInscripcionCanceladaCurso.setShowVerticalLines(false);
			tbInscripcionCanceladaCurso.setOpaque(false);
			tbInscripcionCanceladaCurso.setEnabled(false);

			tbInscripcionCanceladaCurso.setRowHeight(115);
			tbInscripcionCanceladaCurso.setGridColor(new Color(255, 255, 255));

			tbInscripcionCanceladaCurso.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return tbInscripcionCanceladaCurso;
	}

	private JPanel getPnInscripcionCurso() {
		if (pnInscripcionCurso == null) {
			pnInscripcionCurso = new JPanel();
			pnInscripcionCurso.setOpaque(false);
			pnInscripcionCurso.setLayout(new BorderLayout(0, 10));
			pnInscripcionCurso.add(getPnInscripcionCursoNorth(), BorderLayout.NORTH);
			pnInscripcionCurso.add(getPnInscripcionCursoCenter(), BorderLayout.CENTER);
			pnInscripcionCurso.add(getPnInscripcionCursoSouth(), BorderLayout.SOUTH);
		}
		return pnInscripcionCurso;
	}

	private JPanel getPnInscripcionCursoNorth() {
		if (pnInscripcionCursoNorth == null) {
			pnInscripcionCursoNorth = new JPanel();
			pnInscripcionCursoNorth.setOpaque(false);
			GridBagLayout gbl_pnInscripcionCursoNorth = new GridBagLayout();
			gbl_pnInscripcionCursoNorth.columnWidths = new int[] { 1100, 0 };
			gbl_pnInscripcionCursoNorth.rowHeights = new int[] { 60, 130, 0 };
			gbl_pnInscripcionCursoNorth.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
			gbl_pnInscripcionCursoNorth.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
			pnInscripcionCursoNorth.setLayout(gbl_pnInscripcionCursoNorth);
			GridBagConstraints gbc_pnInscripcionCursoTitulo = new GridBagConstraints();
			gbc_pnInscripcionCursoTitulo.fill = GridBagConstraints.BOTH;
			gbc_pnInscripcionCursoTitulo.insets = new Insets(0, 0, 5, 0);
			gbc_pnInscripcionCursoTitulo.gridx = 0;
			gbc_pnInscripcionCursoTitulo.gridy = 0;
			pnInscripcionCursoNorth.add(getPnInscripcionCursoTitulo(), gbc_pnInscripcionCursoTitulo);
			GridBagConstraints gbc_pnInscripcionCursoDatosColegiado = new GridBagConstraints();
			gbc_pnInscripcionCursoDatosColegiado.fill = GridBagConstraints.BOTH;
			gbc_pnInscripcionCursoDatosColegiado.gridx = 0;
			gbc_pnInscripcionCursoDatosColegiado.gridy = 1;
			pnInscripcionCursoNorth.add(getPnInscripcionCursoDatosColegiado(), gbc_pnInscripcionCursoDatosColegiado);
		}
		return pnInscripcionCursoNorth;
	}

	private JPanel getPnInscripcionCursoCenter() {
		if (pnInscripcionCursoCenter == null) {
			pnInscripcionCursoCenter = new JPanel();
			pnInscripcionCursoCenter.setOpaque(false);
			pnInscripcionCursoCenter.setLayout(new GridLayout(0, 1, 10, 0));
			pnInscripcionCursoCenter.add(getPnInscripcionCursoCursosAbiertos());
//			pnInscripcionCursoCenter.add(getPnInscripcionCursoListaEspera());
		}
		return pnInscripcionCursoCenter;
	}

	private JPanel getPnInscripcionCursoSouth() {
		if (pnInscripcionCursoSouth == null) {
			pnInscripcionCursoSouth = new JPanel();
			pnInscripcionCursoSouth.setOpaque(false);
			pnInscripcionCursoSouth.setLayout(new GridLayout(0, 2, 0, 0));
			pnInscripcionCursoSouth.add(getPnInscripcionCursoSouthMessage());
			pnInscripcionCursoSouth.add(getPnInscripcionCursoSouthButtons());
		}
		return pnInscripcionCursoSouth;
	}

	private JPanel getPnInscripcionCursoTitulo() {
		if (pnInscripcionCursoTitulo == null) {
			pnInscripcionCursoTitulo = new JPanel();
			pnInscripcionCursoTitulo.add(getLbInscripcionCursoTitulo());
		}
		return pnInscripcionCursoTitulo;
	}

	private JPanel getPnInscripcionCursoDatosColegiado() {
		if (pnInscripcionCursoDatosColegiado == null) {
			pnInscripcionCursoDatosColegiado = new JPanel();
			pnInscripcionCursoDatosColegiado.setLayout(new GridLayout(0, 3, 10, 0));
			pnInscripcionCursoDatosColegiado.add(getPnInscripcionCursoDatosColegiadoColectivo());
			pnInscripcionCursoDatosColegiado.add(getPnInscripcionCursoDatosColegiadoDni());
			pnInscripcionCursoDatosColegiado.add(getPnInscripcionCursoDatosColegiadoButtons());
		}
		return pnInscripcionCursoDatosColegiado;
	}

	private JLabel getLbInscripcionCursoTitulo() {
		if (lbInscripcionCursoTitulo == null) {
			lbInscripcionCursoTitulo = new JLabel("Inscripción a un curso");
			lbInscripcionCursoTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lbInscripcionCursoTitulo.setFont(LookAndFeel.HEADING_1_FONT);
		}
		return lbInscripcionCursoTitulo;
	}

	private JPanel getPnInscripcionCursoSouthButtons() {
		if (pnInscripcionCursoSouthButtons == null) {
			pnInscripcionCursoSouthButtons = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnInscripcionCursoSouthButtons.getLayout();
			flowLayout.setHgap(10);
			pnInscripcionCursoSouthButtons.add(getBtVolverInicioInscripcionCurso());
			pnInscripcionCursoSouthButtons.add(getBtInscrirseInscripcionCurso());
		}
		return pnInscripcionCursoSouthButtons;
	}

	private JPanel getPnInscripcionCursoSouthMessage() {
		if (pnInscripcionCursoSouthMessage == null) {
			pnInscripcionCursoSouthMessage = new JPanel();
			pnInscripcionCursoSouthMessage.setLayout(new BorderLayout(0, 0));
			pnInscripcionCursoSouthMessage.setBackground(LookAndFeel.TERTIARY_COLOR);
			pnInscripcionCursoSouthMessage.add(getLbInscripcionCursoMensaje(), BorderLayout.CENTER);

			pnInscripcionCursoSouthMessage.setVisible(false);
		}
		return pnInscripcionCursoSouthMessage;
	}

	private JButton getBtVolverInicioInscripcionCurso() {
		if (btVolverInicioInscripcionCurso == null) {
			btVolverInicioInscripcionCurso = new DefaultButton("Volver a inicio", "ventana", "VolverAInicio", 'v',
					ButtonColor.CANCEL);
			btVolverInicioInscripcionCurso.setToolTipText("Haz click aquí para volver a inicio");
			btVolverInicioInscripcionCurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
					toogleListaEsperaCursoSeleccionadoInscripcionCurso(false);
				}
			});
		}
		return btVolverInicioInscripcionCurso;
	}

	private JButton getBtInscrirseInscripcionCurso() {
		if (btInscrirseInscripcionCurso == null) {
			btInscrirseInscripcionCurso = new DefaultButton("Inscribirme", "ventana", "InscribirseCurso", 'v',
					ButtonColor.NORMAL);
			btInscrirseInscripcionCurso.setEnabled(false);
			btInscrirseInscripcionCurso.setToolTipText("Haz click aquí para confirmar la inscripción");
			btInscrirseInscripcionCurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					lbConfirmacionInscripcion.setVisible(false);
					lbAlerta.setVisible(false);
					int IndexCursoSeleccionado = getTbCursosAbiertosInscripcionCurso().getSelectedRow();
					CursoDto cursoSeleccionado = IndexCursoSeleccionado == -1 ? null
							: cursosAbiertosPnInscripcion.get(IndexCursoSeleccionado);
					try {
						if (cursoSeleccionado == null) {
							lbInscripcionCursoMensaje
									.setText("Para Realizar la Inscripcion es Necesario Seleccionar un Curso");
							pnInscripcionCursoSouthMessage.setVisible(true);
						} else {
							if (!InscripcionColegiado.isInscrito(colegiado, cursoSeleccionado)) {
								try {
									if (!InscripcionCursoFormativo.hayPlazasLibres(cursoSeleccionado)) {

										toogleListaEsperaCursoSeleccionadoInscripcionCurso(true);

										TableModel model = new ListaEsperaCursoModel(
												ListaEsperaCurso.findByCursoId(cursoSeleccionado.codigoCurso))
														.getListaEsperaSummaryModel();

										tbListaEsperaCursoSeleccionadoInscripcionCurso.setModel(model);

										mainPanel.repaint();

										int opt = JOptionPane.showConfirmDialog(null,
												"El curso está tiene todas las plazas cubiertas ¿Quiere apuntarse a la lista de espera?",
												"Inscripción curso: Apuntarse a la lista de espera.",
												JOptionPane.YES_NO_OPTION);

										if (opt == JOptionPane.YES_OPTION) {
											/* Actualizar lista de espera con el nuevo usuario */
											try {
												ListaEsperaCurso.apuntarListaEspera(
														txDniColegiadoInscripcionCurso.getText(),
														cursoSeleccionado.codigoCurso);

												/* Mostrar mensaje de confirmación de unión a la lista. */
												JOptionPane.showMessageDialog(null, "Inscripcion curso seleccionado",
														"Información acerca de su inscripción",
														JOptionPane.INFORMATION_MESSAGE);

												/* Actualizar tabla de la lista de espera */
												TableModel modelUpdated = new ListaEsperaCursoModel(
														ListaEsperaCurso.findByCursoId(cursoSeleccionado.codigoCurso))
																.getListaEsperaSummaryModel();

												tbListaEsperaCursoSeleccionadoInscripcionCurso.setModel(modelUpdated);
												tbListaEsperaCursoSeleccionadoInscripcionCurso.repaint();

												pnInscripcionCursoSouthMessage.setVisible(false);

											} catch (BusinessException be) {
												lbInscripcionCursoMensaje.setText(be.getMessage());
												pnInscripcionCursoSouthMessage.setVisible(true);
											}
										}
									} else {
										InscripcionColegiado.InscribirColegiado(cursoSeleccionado, colegiado);
										InscripcionColegiado.EmitirJustificante(colegiado, cursoSeleccionado);

										lbInscripcionCursoMensaje.setText(
												"La inscripcion en el curso seleccionado se ha realizado correctamente");

										pnInscripcionCursoSouthMessage.setVisible(true);
									}

								} catch (BusinessException e1) {
									lbInscripcionCursoMensaje.setText(e1.getMessage());
									e1.printStackTrace();
								}
							} else {
								lbInscripcionCursoMensaje.setText(
										"La inscripción no se ha realizado porque ya está inscrito en este curso");

								pnInscripcionCursoSouthMessage.setVisible(true);
							}
						}
					} catch (PersistenceException e1) {
						e1.printStackTrace();

					} catch (BusinessException e1) {
						e1.printStackTrace();
					}

					// mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
					btInscrirseInscripcionCurso.setEnabled(false);
				}
			});
		}
		return btInscrirseInscripcionCurso;
	}

	private JLabel getLbInscripcionCursoMensaje() {
		if (lbInscripcionCursoMensaje == null) {
			lbInscripcionCursoMensaje = new JLabel("MENSAJE DE INFORMACIÓN / ERROR");
			lbInscripcionCursoMensaje.setToolTipText("Aquí se muestra el mensaje informativo");
			lbInscripcionCursoMensaje.setHorizontalAlignment(SwingConstants.CENTER);
			lbInscripcionCursoMensaje.setHorizontalTextPosition(SwingConstants.CENTER);
			lbInscripcionCursoMensaje.setFont(LookAndFeel.LABEL_FONT);
		}
		return lbInscripcionCursoMensaje;
	}

	private JPanel getPnInscripcionCursoCursosAbiertos() {
		if (pnInscripcionCursoCursosAbiertos == null) {
			pnInscripcionCursoCursosAbiertos = new JPanel();
			pnInscripcionCursoCursosAbiertos.setLayout(new BorderLayout(0, 0));
			pnInscripcionCursoCursosAbiertos.add(getLbTablaCursosAbiertosInscripcionCursoTitulo(), BorderLayout.NORTH);
			pnInscripcionCursoCursosAbiertos.add(getSpCursosAbiertosInscripcionCurso(), BorderLayout.CENTER);
		}
		return pnInscripcionCursoCursosAbiertos;
	}

	private JPanel getPnInscripcionCursoListaEspera() {
		if (pnInscripcionCursoListaEspera == null) {
			pnInscripcionCursoListaEspera = new JPanel();
			pnInscripcionCursoListaEspera.setLayout(new BorderLayout(0, 0));
			pnInscripcionCursoListaEspera.add(getLbTablaListaEsperaInscripcionCursoTitulo(), BorderLayout.NORTH);
			pnInscripcionCursoListaEspera.add(getSpListaEsperaCursoInscripcionCurso(), BorderLayout.CENTER);
		}
		return pnInscripcionCursoListaEspera;
	}

	private JLabel getLbTablaCursosAbiertosInscripcionCursoTitulo() {
		if (lbTablaCursosAbiertosInscripcionCursoTitulo == null) {
			lbTablaCursosAbiertosInscripcionCursoTitulo = new JLabel("Cursos Abiertos");
			lbTablaCursosAbiertosInscripcionCursoTitulo
					.setToolTipText("Por favor, seleccione el curso al que desea inscribirse");
			lbTablaCursosAbiertosInscripcionCursoTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
			lbTablaCursosAbiertosInscripcionCursoTitulo.setHorizontalAlignment(SwingConstants.CENTER);

			lbTablaCursosAbiertosInscripcionCursoTitulo.setFont(LookAndFeel.HEADING_2_FONT);

		}
		return lbTablaCursosAbiertosInscripcionCursoTitulo;
	}

	private JScrollPane getSpCursosAbiertosInscripcionCurso() {
		if (spCursosAbiertosInscripcionCurso == null) {
			spCursosAbiertosInscripcionCurso = new JScrollPane(getTbCursosAbiertosInscripcionCurso());
			spCursosAbiertosInscripcionCurso.setToolTipText("Por favor, seleccione el curso al que desea inscribirse");
		}
		return spCursosAbiertosInscripcionCurso;
	}

	private JLabel getLbTablaListaEsperaInscripcionCursoTitulo() {
		if (lbTablaListaEsperaInscripcionCursoTitulo == null) {
			lbTablaListaEsperaInscripcionCursoTitulo = new JLabel("Lista de Espera");
			lbTablaListaEsperaInscripcionCursoTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
			lbTablaListaEsperaInscripcionCursoTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lbTablaListaEsperaInscripcionCursoTitulo.setFont(new Font("Poppins", Font.PLAIN, 18));
		}
		return lbTablaListaEsperaInscripcionCursoTitulo;
	}

	private JScrollPane getSpListaEsperaCursoInscripcionCurso() {
		if (spListaEsperaCursoInscripcionCurso == null) {
			spListaEsperaCursoInscripcionCurso = new JScrollPane(getTbListaEsperaCursoSeleccionadoInscripcionCurso());
			spListaEsperaCursoInscripcionCurso.setToolTipText("Lista de espera para el curso seleccionado (Si la hay)");
		}
		return spListaEsperaCursoInscripcionCurso;
	}

	private JTable getTbListaEsperaCursoSeleccionadoInscripcionCurso() {
		if (tbListaEsperaCursoSeleccionadoInscripcionCurso == null) {
			tbListaEsperaCursoSeleccionadoInscripcionCurso = new JTable();

			tbListaEsperaCursoSeleccionadoInscripcionCurso.setIntercellSpacing(new Dimension(0, 0));
			tbListaEsperaCursoSeleccionadoInscripcionCurso.setShowGrid(false);
			tbListaEsperaCursoSeleccionadoInscripcionCurso.setRowMargin(0);
			tbListaEsperaCursoSeleccionadoInscripcionCurso.setRequestFocusEnabled(false);
			tbListaEsperaCursoSeleccionadoInscripcionCurso.setFocusable(false);
			tbListaEsperaCursoSeleccionadoInscripcionCurso.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbListaEsperaCursoSeleccionadoInscripcionCurso.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbListaEsperaCursoSeleccionadoInscripcionCurso.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbListaEsperaCursoSeleccionadoInscripcionCurso.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
			tbListaEsperaCursoSeleccionadoInscripcionCurso.setShowVerticalLines(false);
			tbListaEsperaCursoSeleccionadoInscripcionCurso.setOpaque(false);

			tbListaEsperaCursoSeleccionadoInscripcionCurso.setRowHeight(LookAndFeel.ROW_HEIGHT);
			tbListaEsperaCursoSeleccionadoInscripcionCurso.setGridColor(new Color(255, 255, 255));

			tbListaEsperaCursoSeleccionadoInscripcionCurso.setRowSelectionAllowed(false);

			TableModel model = new ListaEsperaCursoModel(List.of()).getListaEsperaSummaryModel();
			tbListaEsperaCursoSeleccionadoInscripcionCurso.setModel(model);

		}
		return tbListaEsperaCursoSeleccionadoInscripcionCurso;
	}

	/**
	 * Muestra u oculta el panel de la lista de espera del curso seleccionado.
	 * 
	 * @since HU. 19733
	 * @param sinPlazas true si el curso no tiene plazas y false en caso contrario.
	 */
	private void toogleListaEsperaCursoSeleccionadoInscripcionCurso(boolean sinPlazas) {
		if (sinPlazas) {
			pnInscripcionCursoCenter.setLayout(new GridLayout(0, 2, 10, 0));
			pnInscripcionCursoCenter.add(getPnInscripcionCursoListaEspera());

		} else {
			pnInscripcionCursoCenter.setLayout(new GridLayout(0, 1, 10, 0));
			pnInscripcionCursoCenter.remove(getPnInscripcionCursoListaEspera());
		}

		this.repaint();
	}

	private JPanel getPnInscripcionCursoDatosColegiadoColectivo() {
		if (pnInscripcionCursoDatosColegiadoColectivo == null) {
			pnInscripcionCursoDatosColegiadoColectivo = new JPanel();
			pnInscripcionCursoDatosColegiadoColectivo.setLayout(new GridLayout(0, 1, 0, 0));
			pnInscripcionCursoDatosColegiadoColectivo.add(getLbInscripcionCursoSeleccionarColectivoTitulo());
			pnInscripcionCursoDatosColegiadoColectivo.add(getCbSeleccionarColectivoInscripcionCurso());
		}
		return pnInscripcionCursoDatosColegiadoColectivo;
	}

	private JPanel getPnInscripcionCursoDatosColegiadoDni() {
		if (pnInscripcionCursoDatosColegiadoDni == null) {
			pnInscripcionCursoDatosColegiadoDni = new JPanel();
			pnInscripcionCursoDatosColegiadoDni.setLayout(new GridLayout(2, 1, 0, 5));
			pnInscripcionCursoDatosColegiadoDni.add(getLbInscripcionCursoDniColegiadoTitulo());
			pnInscripcionCursoDatosColegiadoDni.add(getTxDniColegiadoInscripcionCurso());
		}
		return pnInscripcionCursoDatosColegiadoDni;
	}

	private JPanel getPnInscripcionCursoDatosColegiadoButtons() {
		if (pnInscripcionCursoDatosColegiadoButtons == null) {
			pnInscripcionCursoDatosColegiadoButtons = new JPanel();
			GridBagLayout gbl_pnInscripcionCursoDatosColegiadoButtons = new GridBagLayout();
			gbl_pnInscripcionCursoDatosColegiadoButtons.columnWidths = new int[] { 53, 250, 0 };
			gbl_pnInscripcionCursoDatosColegiadoButtons.rowHeights = new int[] { 50, 80, 0 };
			gbl_pnInscripcionCursoDatosColegiadoButtons.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
			gbl_pnInscripcionCursoDatosColegiadoButtons.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
			pnInscripcionCursoDatosColegiadoButtons.setLayout(gbl_pnInscripcionCursoDatosColegiadoButtons);
			GridBagConstraints gbc_btMostrarCursosAbiertosInscripcionCurso = new GridBagConstraints();
			gbc_btMostrarCursosAbiertosInscripcionCurso.anchor = GridBagConstraints.NORTHWEST;
			gbc_btMostrarCursosAbiertosInscripcionCurso.gridx = 1;
			gbc_btMostrarCursosAbiertosInscripcionCurso.gridy = 1;
			pnInscripcionCursoDatosColegiadoButtons.add(getBtMostrarCursosAbiertosInscripcionCurso(),
					gbc_btMostrarCursosAbiertosInscripcionCurso);
		}
		return pnInscripcionCursoDatosColegiadoButtons;
	}

	private JLabel getLbInscripcionCursoSeleccionarColectivoTitulo() {
		if (lbInscripcionCursoSeleccionarColectivoTitulo == null) {
			lbInscripcionCursoSeleccionarColectivoTitulo = new JLabel("Seleccionar el colectivo:");
			lbInscripcionCursoSeleccionarColectivoTitulo.setLabelFor(getCbSeleccionarColectivoInscripcionCurso());
			lbInscripcionCursoSeleccionarColectivoTitulo.setHorizontalAlignment(SwingConstants.LEFT);
			lbInscripcionCursoSeleccionarColectivoTitulo.setHorizontalTextPosition(SwingConstants.CENTER);

			lbInscripcionCursoSeleccionarColectivoTitulo.setFont(LookAndFeel.HEADING_3_FONT);
		}
		return lbInscripcionCursoSeleccionarColectivoTitulo;
	}

	private JComboBox<String> getCbSeleccionarColectivoInscripcionCurso() {
		if (cbSeleccionarColectivoInscripcionCurso == null) {

			ColectivoComboModel model = new ColectivoComboModel();

			cbSeleccionarColectivoInscripcionCurso = new JComboBox<String>();
			cbSeleccionarColectivoInscripcionCurso.setPreferredSize(new Dimension(30, 40));
			cbSeleccionarColectivoInscripcionCurso.setFont(LookAndFeel.LABEL_FONT);

			DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>(model.getColectivos());
			cbSeleccionarColectivoInscripcionCurso.setModel(comboModel);

		}
		return cbSeleccionarColectivoInscripcionCurso;
	}

	private JButton getBtMostrarCursosAbiertosInscripcionCurso() {
		if (btMostrarCursosAbiertosInscripcionCurso == null) {
			btMostrarCursosAbiertosInscripcionCurso = new DefaultButton("Ver cursos abiertos", "ventana",
					"VerCursosAbiertos", 's', ButtonColor.NORMAL);
			btMostrarCursosAbiertosInscripcionCurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						ColegiadoDto c = InscripcionColegiado.InicioSesion(txDniColegiadoInscripcionCurso.getText());

						if (c == null || txDniColegiadoInscripcionCurso.getText().isEmpty()) {
							lbInscripcionCursoMensaje.setText(
									"El DNI introducido no es válido. Por favor, revíselo e introdúzcalo de nuevo.");
							pnInscripcionCursoSouthMessage.setVisible(true);

						} else {

							if (checkCoincideDniConColectivo()) {
								colegiado = c;

								cursosAbiertosPnInscripcion = InscripcionCursoFormativo.getCursosAbiertos();

								cursosAbiertosPnInscripcion = cursosAbiertosPnInscripcion.stream()
										.filter(curso -> Precio_Colectivos
												.StringToPrecio_Colectivos(curso.CantidadPagarColectivo)
												.containsAlgunColectivo(c.TipoColectivo, "Todos"))
										.collect(Collectors.toList());

								if (cursosAbiertosPnInscripcion.isEmpty()) {
									lbInscripcionCursoMensaje.setText(
											"Lo sentimos, No hay cursos disponibles para el colectivo seleccionado");
									pnInscripcionCursoSouthMessage.setVisible(true);
								}

								TableModel tableModelCursosAbiertosInsCurso = new CursoModel(
										cursosAbiertosPnInscripcion).getCursosAbiertosInscripcionCurso();
								tbCursosAbiertosInscripcionCurso.setModel(tableModelCursosAbiertosInsCurso);
							}

						}
					} catch (BusinessException e1) {
						e1.printStackTrace();
					}
				}
			});
			btMostrarCursosAbiertosInscripcionCurso.setText("Ver cursos");
		}
		return btMostrarCursosAbiertosInscripcionCurso;
	}

	/**
	 * Método auxiliar al método
	 * {@link #getBtMostrarCursosAbiertosInscripcionCurso()}
	 * 
	 * Comprueba que el dni del usuario coincide con el colectivo seleccionado.
	 */
	private boolean checkCoincideDniConColectivo() {
		String dni = txDniColegiadoInscripcionCurso.getText();
		String colectivoSeleccionado = cbSeleccionarColectivoInscripcionCurso.getSelectedItem().toString();

		try {
			Colegiado.coincideDniConColectivo(dni, colectivoSeleccionado);
		} catch (BusinessException e) {
			lbInscripcionCursoMensaje.setText(e.getMessage());
			pnInscripcionCursoSouthMessage.setVisible(true);

			return false;
		}

		return true;
	}

	private JLabel getLbInscripcionCursoDniColegiadoTitulo() {
		if (lbInscripcionCursoDniColegiadoTitulo == null) {
			lbInscripcionCursoDniColegiadoTitulo = new JLabel("Introduzca su DNI:");
			lbInscripcionCursoDniColegiadoTitulo.setHorizontalAlignment(SwingConstants.LEFT);
			lbInscripcionCursoDniColegiadoTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
			lbInscripcionCursoDniColegiadoTitulo.setLabelFor(getTxDniColegiadoInscripcionCurso());
			lbInscripcionCursoDniColegiadoTitulo.setFont(LookAndFeel.HEADING_3_FONT);
		}
		return lbInscripcionCursoDniColegiadoTitulo;
	}

	private JTextField getTxDniColegiadoInscripcionCurso() {
		if (txDniColegiadoInscripcionCurso == null) {
			txDniColegiadoInscripcionCurso = new JTextField();
			txDniColegiadoInscripcionCurso.setLocale(new Locale("es"));
			txDniColegiadoInscripcionCurso.setToolTipText("Introduzca su DNI aquí");
			txDniColegiadoInscripcionCurso.setColumns(9);
			txDniColegiadoInscripcionCurso.setFont(LookAndFeel.LABEL_FONT);

			TextPlaceHolderCustom.setPlaceholder("33542356H", txDniColegiadoInscripcionCurso);

		}
		return txDniColegiadoInscripcionCurso;
	}

	/**
	 * 
	 * @throws BusinessException
	 * @see #toogleListaEsperaCursoSeleccionadoInscripcionCurso
	 * @since HU. 19733
	 */
	private void refrescarListaEspera(CursoDto seleccionado) throws BusinessException {

		toogleListaEsperaCursoSeleccionadoInscripcionCurso(seleccionado.plazasDisponibles > 0);

		TableModel model = new ListaEsperaCursoModel(ListaEsperaCurso.findByCursoId(seleccionado.codigoCurso))
				.getListaEsperaSummaryModel();
		tbListaEsperaCursoSeleccionadoInscripcionCurso.setModel(model);

		this.repaint();
	}

	private DefaultButton getBtSolicitudVisados() {
		if (btSolicitudVisados == null) {
			btSolicitudVisados = new DefaultButton("Cancelar inscripción", "ventana", "CancelarInscripción", 'l',
					ButtonColor.NORMAL);
			btSolicitudVisados.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, CREAR_SOLICITUD_VISADOS);
				}
			});
			btSolicitudVisados.setText("Solicitud Visados");
			btSolicitudVisados.setMnemonic('N');
		}
		return btSolicitudVisados;
	}

	private JPanel getPnSolicitudVisados() {
		if (pnSolicitudVisados == null) {
			pnSolicitudVisados = new JPanel();
			pnSolicitudVisados.setLayout(new BorderLayout(0, 0));
			pnSolicitudVisados.add(getPnNorthSolicitudVisados(), BorderLayout.NORTH);
			pnSolicitudVisados.add(getPnCenterSolicitudVisados(), BorderLayout.CENTER);
			pnSolicitudVisados.add(getPnSouthSolicitudVisados(), BorderLayout.SOUTH);
		}
		return pnSolicitudVisados;
	}

	private JPanel getPnNorthSolicitudVisados() {
		if (pnNorthSolicitudVisados == null) {
			pnNorthSolicitudVisados = new JPanel();
			pnNorthSolicitudVisados.setLayout(new BorderLayout(0, 0));
			pnNorthSolicitudVisados.add(getPnTituloCrearSolicitudVisado(), BorderLayout.NORTH);
			pnNorthSolicitudVisados.add(getPnIntroducirDNIdePeritoParaVisado(), BorderLayout.SOUTH);
		}
		return pnNorthSolicitudVisados;
	}
	
	private JPanel getPnColectivosSouth() {
		if (pnColectivosSouth == null) {
			pnColectivosSouth = new JPanel();
			pnColectivosSouth.setLayout(new GridLayout(1, 0, 0, 0));
			pnColectivosSouth.add(getPnSeleccionCursoCancelable());
		}
		return pnColectivosSouth;
	}
	
	
	private JPanel getPnSeleccionCursoCancelable() {
		if (pnSeleccionCursoCancelable == null) {
			pnSeleccionCursoCancelable = new JPanel();
			pnSeleccionCursoCancelable.setLayout(new GridLayout(2, 1, 0, 10));
			pnSeleccionCursoCancelable.add(getPnSeleccionCursoCancelableRadioButtons());
			pnSeleccionCursoCancelable.add(getPnSeleccionCursoPorcentajeDevolucion());

			pnSeleccionCursoCancelable.setBorder(new TitledBorder(new LineBorder(LookAndFeel.SECONDARY_COLOR, 1, true),
					"Curso cancelable", TitledBorder.LEADING, TitledBorder.TOP, null, LookAndFeel.SECONDARY_COLOR));
			
			pnSeleccionCursoCancelableRadioButtons.setLayout(new GridLayout(0, 2, 0, 0));

		}
		return pnSeleccionCursoCancelable;
	}

	private JPanel getPnSeleccionCursoCancelableRadioButtons() {
		if (pnSeleccionCursoCancelableRadioButtons == null) {
			pnSeleccionCursoCancelableRadioButtons = new JPanel();

			pnSeleccionCursoCancelableRadioButtons.add(getLbSeleccionCursoCancelable());
			pnSeleccionCursoCancelableRadioButtons.add(getPnSeleccionCursoCancelableRadios());
		}
		return pnSeleccionCursoCancelableRadioButtons;
	}
	
	private JLabel getLbSeleccionCursoCancelable() {
		if (lbSeleccionCursoCancelable == null) {
			lbSeleccionCursoCancelable = new JLabel("Curso cancelable:");
			lbSeleccionCursoCancelable.setFont(LookAndFeel.LABEL_FONT);
		}
		return lbSeleccionCursoCancelable;
	}
	

	private JRadioButton getRbSeleccionCursoCancelableSi() {
		if (rbSeleccionCursoCancelableSi == null) {
			rbSeleccionCursoCancelableSi = new JRadioButton("Sí");

			rbSeleccionCursoCancelableSi.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if (spPorcentajeDevolucionCursoCancelable != null && rbSeleccionCursoCancelableSi.isSelected()) {
						spPorcentajeDevolucionCursoCancelable.setEnabled(true);
					}
				}
			});

			rbSeleccionCursoCancelableSi.setFont(LookAndFeel.LABEL_FONT);
			buttonGroupCursoCancelable.add(rbSeleccionCursoCancelableSi);

		}
		return rbSeleccionCursoCancelableSi;
	}

	private JPanel getPnTituloCrearSolicitudVisado() {
		if (pnTituloCrearSolicitudVisado == null) {
			pnTituloCrearSolicitudVisado = new JPanel();
			pnTituloCrearSolicitudVisado.add(getLblTituloCrearSolicitudVisado());
		}

		return pnTituloCrearSolicitudVisado;
	}

	private JRadioButton getRbSeleccionCursoCancelableNo() {
		if (rbSeleccionCursoCancelableNo == null) {
			rbSeleccionCursoCancelableNo = new JRadioButton("No");
			rbSeleccionCursoCancelableNo.setFont(LookAndFeel.LABEL_FONT);

			rbSeleccionCursoCancelableNo.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if (spPorcentajeDevolucionCursoCancelable != null && rbSeleccionCursoCancelableNo.isSelected()) {
						spPorcentajeDevolucionCursoCancelable.setEnabled(false);
					}
				}
			});

			buttonGroupCursoCancelable.add(rbSeleccionCursoCancelableNo);

			/* Si el usuario no selecciona una opción, el curso será no cancelable */
			rbSeleccionCursoCancelableNo.setSelected(true);
		}

		return rbSeleccionCursoCancelableNo;
	}

	private JPanel getPnIntroducirDNIdePeritoParaVisado() {
		if (pnIntroducirDNIdePeritoParaVisado == null) {
			pnIntroducirDNIdePeritoParaVisado = new JPanel();
			pnIntroducirDNIdePeritoParaVisado.add(getLblIntroduzcaSuDniParaVisado());
			pnIntroducirDNIdePeritoParaVisado.add(getTxtIntroduzcaSuDniParaVisado());
			pnIntroducirDNIdePeritoParaVisado.add(getBtnComprobarDNI());

		}
		return pnIntroducirDNIdePeritoParaVisado;
	}

	private JPanel getPnSeleccionCursoPorcentajeDevolucion() {
		if (pnSeleccionCursoPorcentajeDevolucion == null) {
			pnSeleccionCursoPorcentajeDevolucion = new JPanel();
			pnSeleccionCursoPorcentajeDevolucion.setOpaque(false);
			pnSeleccionCursoPorcentajeDevolucion.setLayout(new GridLayout(0, 2, 0, 0));
			pnSeleccionCursoPorcentajeDevolucion.add(getLbSeleccionCursoCancelablePorcentajeDevolucion());
			pnSeleccionCursoPorcentajeDevolucion.add(getPnTxPorcentajeDevolucionCursoCancelableWrapper());
		}
		return pnSeleccionCursoPorcentajeDevolucion;
	}

	private JLabel getLblTituloCrearSolicitudVisado() {
		if (lblTituloCrearSolicitudVisado == null) {
			lblTituloCrearSolicitudVisado = new JLabel("Crear solicitud para visado");
			lblTituloCrearSolicitudVisado.setFont(new Font("Tahoma", Font.PLAIN, 22));
		}
		return lblTituloCrearSolicitudVisado;
	}

	private JLabel getLblIntroduzcaSuDniParaVisado() {
		if (lblIntroduzcaSuDniParaVisado == null) {
			lblIntroduzcaSuDniParaVisado = new JLabel("Introduzca su DNI: ");
			lblIntroduzcaSuDniParaVisado.setFont(new Font("Tahoma", Font.PLAIN, 17));
		}
		return lblIntroduzcaSuDniParaVisado;
	}

	private JTextField getTxtIntroduzcaSuDniParaVisado() {
		if (txtIntroduzcaSuDniParaVisado == null) {
			txtIntroduzcaSuDniParaVisado = new JTextField();
			txtIntroduzcaSuDniParaVisado.setFont(new Font("Tahoma", Font.PLAIN, 17));
			txtIntroduzcaSuDniParaVisado.setColumns(10);
		}

		return txtIntroduzcaSuDniParaVisado;
	}

	private JLabel getLbSeleccionCursoCancelablePorcentajeDevolucion() {
		if (lbSeleccionCursoCancelablePorcentajeDevolucion == null) {
			lbSeleccionCursoCancelablePorcentajeDevolucion = new JLabel("Porcentaje devolución:");
			lbSeleccionCursoCancelablePorcentajeDevolucion.setFont(LookAndFeel.LABEL_FONT);
		}
		
		return lbSeleccionCursoCancelablePorcentajeDevolucion;
	}

	private JPanel getPnCenterSolicitudVisados() {
		if (pnCenterSolicitudVisados == null) {
			pnCenterSolicitudVisados = new JPanel();
			pnCenterSolicitudVisados.setLayout(null);
			pnCenterSolicitudVisados.add(getTxtDescripcionVisado());
			pnCenterSolicitudVisados.add(getLblDescripcion());
			pnCenterSolicitudVisados.add(getLblTextoInfo());
		}
		return pnCenterSolicitudVisados;
	}

	private JPanel getPnSouthSolicitudVisados() {
		if (pnSouthSolicitudVisados == null) {
			pnSouthSolicitudVisados = new JPanel();
			pnSouthSolicitudVisados.add(getBtnVisadosVolverAlInicio());
			pnSouthSolicitudVisados.add(getBtnEnviarSolicitudVisado());
		}
		return pnSouthSolicitudVisados;
	}

	private JPanel getPnSeleccionCursoCancelableRadios() {
		if (pnSeleccionCursoCancelableRadios == null) {
			pnSeleccionCursoCancelableRadios = new JPanel();
			pnSeleccionCursoCancelableRadios.add(getRbSeleccionCursoCancelableSi());
			pnSeleccionCursoCancelableRadios.add(getRbSeleccionCursoCancelableNo());
		}
		return pnSeleccionCursoCancelableRadios;
	}

	private JButton getBtnEnviarSolicitudVisado() {
		if (btnEnviarSolicitudVisado == null) {
			btnEnviarSolicitudVisado = new JButton("Enviar solicitud");
			btnEnviarSolicitudVisado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String dniPerito = txtIntroduzcaSuDniParaVisado.getText();
					if (dniPerito.isEmpty()) {
						JOptionPane.showMessageDialog(pnSolicitudVisados, "Su DNI está vacío por favor introduzca uno");
						return;
					}
					List<ColegiadoDto> peritos = PeritoCRUD.findPeritoByDNI(dniPerito);
					if (peritos.isEmpty()) {
						JOptionPane.showMessageDialog(pnSolicitudVisados, "No existe perito con tal DNI");
						return;
					}
					String descripcion = txtDescripcionVisado.getText();
					if (descripcion.isEmpty()) {
						JOptionPane.showMessageDialog(pnSolicitudVisados,
								"Porfavor introduzca una descripción para su visado");
						return;
					}
					SolicitudVisadoDto s = new SolicitudVisadoDto();

					s.dniPerito = dniPerito;
					s.descripcion = descripcion;
					s.estado = "NO_ASIGNADA";

					SolicitudVisadosCRUD.addSolicitudVisado(s);
					JOptionPane.showMessageDialog(pnSolicitudVisados,
							"Perito con nombre: " + peritos.get(0).nombre + " y apellidos: " + peritos.get(0).apellidos
									+ "\nha presentado una solicitud para visado correctamente");

					txtIntroduzcaSuDniParaVisado.setText("");
					txtDescripcionVisado.setText("");
				}
			});
			btnEnviarSolicitudVisado.setBackground(Color.GREEN);
			btnEnviarSolicitudVisado.setFont(new Font("Tahoma", Font.PLAIN, 17));
		}
		return btnEnviarSolicitudVisado;
	}

	private JTextArea getTxtDescripcionVisado() {
		if (txtDescripcionVisado == null) {
			txtDescripcionVisado = new JTextArea();
			txtDescripcionVisado.setFont(new Font("Monospaced", Font.PLAIN, 15));
			txtDescripcionVisado.setBounds(24, 125, 1055, 192);
		}
		return txtDescripcionVisado;
	}

	private JLabel getLblDescripcion() {
		if (lblDescripcion == null) {
			lblDescripcion = new JLabel("Descripción del visado: ");
			lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 17));
			lblDescripcion.setBounds(24, 85, 291, 30);
		}
		return lblDescripcion;
	}

	private JButton getBtnComprobarDNI() {
		if (btnComprobarDNI == null) {
			btnComprobarDNI = new JButton("Comprobar");
			btnComprobarDNI.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (txtIntroduzcaSuDniParaVisado.getText().isEmpty()) {
						JOptionPane.showMessageDialog(pnSolicitudVisados, "Su DNI está vacío por favor introduzca uno");
						return;
					}
					List<ColegiadoDto> peritos = PeritoCRUD.findPeritoByDNI(txtIntroduzcaSuDniParaVisado.getText());
					if (peritos.isEmpty()) {
						JOptionPane.showMessageDialog(pnSolicitudVisados, "No existe perito con tal DNI");
					} else {
						JOptionPane.showMessageDialog(pnSolicitudVisados, "Perito encontrado\nNombre: "
								+ peritos.get(0).nombre + "\nApellidos: " + peritos.get(0).apellidos);
					}
					return;
				}
			});
			btnComprobarDNI.setFont(new Font("Tahoma", Font.PLAIN, 17));
		}
		return btnComprobarDNI;
	}

	private JLabel getLblTextoInfo() {
		if (lblTextoInfo == null) {
			lblTextoInfo = new JLabel(
					"Una vez que se envíe la solicitud de visado, el COIIPA la tendrá prensente para poder asignarle una persona que pueda visar la pericial");
			lblTextoInfo.setFont(new Font("Tahoma", Font.PLAIN, 17));
			lblTextoInfo.setBounds(24, 327, 1026, 30);
		}
		return lblTextoInfo;
	}

	private JButton getBtnVisadosVolverAlInicio() {
		if (btnVisadosVolverAlInicio == null) {
			btnVisadosVolverAlInicio = new JButton("Volver al inicio");
			btnVisadosVolverAlInicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
				}
			});
			btnVisadosVolverAlInicio.setBackground(Color.RED);
			btnVisadosVolverAlInicio.setFont(new Font("Tahoma", Font.PLAIN, 17));
		}
		return btnVisadosVolverAlInicio;
	}
	
	private DefaultButton getBtHomeAsignarVisados() {
		if (btHomeAsignarVisados == null) {
			btHomeAsignarVisados = new DefaultButton("Cancelar un curso", "ventana", "CancelaCurso", 'l', ButtonColor.NORMAL);
			btHomeAsignarVisados.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, ASIGNACION_VISADOS);
				}
			});
			btHomeAsignarVisados.setText("Asignar Visados");
			btHomeAsignarVisados.setMnemonic('U');
		}
		return btHomeAsignarVisados;
	}
	private JPanel getPnAsignarVisados() {
		if (pnAsignarVisados == null) {
			pnAsignarVisados = new JPanel();
			pnAsignarVisados.setLayout(new BorderLayout(0, 0));
			pnAsignarVisados.add(getPnNorthAsignarVisados(), BorderLayout.NORTH);
			pnAsignarVisados.add(getPnSouthAsignarVisados(), BorderLayout.SOUTH);
			pnAsignarVisados.add(getPnCenterAsignarVisados(), BorderLayout.CENTER);
		}
		return pnAsignarVisados;
	}
	private JPanel getPnNorthAsignarVisados() {
		if (pnNorthAsignarVisados == null) {
			pnNorthAsignarVisados = new JPanel();
			pnNorthAsignarVisados.add(getLblAsignacionDeVisados());
		}
		return pnNorthAsignarVisados;
	}
	private JLabel getLblAsignacionDeVisados() {
		if (lblAsignacionDeVisados == null) {
			lblAsignacionDeVisados = new JLabel("Asignacion de visados");
			lblAsignacionDeVisados.setFont(new Font("Tahoma", Font.BOLD, 19));
		}
		return lblAsignacionDeVisados;
	}
	private JPanel getPnSouthAsignarVisados() {
		if (pnSouthAsignarVisados == null) {
			pnSouthAsignarVisados = new JPanel();
			pnSouthAsignarVisados.add(getBtnAsignarVisado());
			pnSouthAsignarVisados.add(getBtnVolverAlInicio());
		}
		return pnSouthAsignarVisados;
	}
	private JButton getBtnVolverAlInicio() {
		if (btnVolverAlInicio == null) {
			btnVolverAlInicio = new JButton("Volver al inicio");
			btnVolverAlInicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCardLayout.show(mainPanel, HOME_PANEL_NAME);
				}
			});
			btnVolverAlInicio.setBackground(Color.RED);
			btnVolverAlInicio.setFont(new Font("Tahoma", Font.PLAIN, 17));
		}
		return btnVolverAlInicio;
	}
	private JPanel getPnCenterAsignarVisados() {
		if (pnCenterAsignarVisados == null) {
			pnCenterAsignarVisados = new JPanel();
			pnCenterAsignarVisados.setLayout(new BorderLayout(0, 0));
			pnCenterAsignarVisados.add(getPnVisadosARevisar(), BorderLayout.WEST);
			pnCenterAsignarVisados.add(getPnPeritosDisponibles(), BorderLayout.EAST);
		}
		return pnCenterAsignarVisados;
	}
	private JPanel getPnVisadosARevisar() {
		if (pnVisadosARevisar == null) {
			pnVisadosARevisar = new JPanel();
			pnVisadosARevisar.setLayout(new BorderLayout(0, 0));
			pnVisadosARevisar.add(getLblSolicitudesDeVisado(), BorderLayout.NORTH);
			pnVisadosARevisar.add(getSpVisados());
			pnVisadosARevisar.add(getBtnActualizarVisados(), BorderLayout.SOUTH);
		}
		return pnVisadosARevisar;
	}
	private JPanel getPnPeritosDisponibles() {
		if (pnPeritosDisponibles == null) {
			pnPeritosDisponibles = new JPanel();
			pnPeritosDisponibles.setLayout(new BorderLayout(0, 0));
			pnPeritosDisponibles.add(getLblPeritosDisponibles(), BorderLayout.NORTH);
			pnPeritosDisponibles.add(getSpPeritosDisponibles(), BorderLayout.CENTER);
		}
		return pnPeritosDisponibles;
	}
	private JLabel getLblSolicitudesDeVisado() {
		if (lblSolicitudesDeVisado == null) {
			lblSolicitudesDeVisado = new JLabel("Solicitudes de visado: ");
			lblSolicitudesDeVisado.setFont(new Font("Tahoma", Font.PLAIN, 17));
		}
		return lblSolicitudesDeVisado;
	}
	private JLabel getLblPeritosDisponibles() {
		if (lblPeritosDisponibles == null) {
			lblPeritosDisponibles = new JLabel("Peritos disponibles:");
			lblPeritosDisponibles.setFont(new Font("Tahoma", Font.PLAIN, 17));
		}
		return lblPeritosDisponibles;
	}
	private JScrollPane getSpVisados() {
		if (spVisados == null) {
			spVisados = new JScrollPane();
			spVisados.setViewportView(getTbVisados());
		}
		return spVisados;
	}
	private JTable getTbVisados() {
		if (tbVisados == null) {
			tbVisados = new JTable();
			tbVisados.setFont(new Font("Tahoma", Font.PLAIN, 17));
			TableModel visadosModel = new ModeloSolicitudesVisados(SolicitudVisadosCRUD.findAllSolicitudesVisado()).getSolicitudModel();
			tbVisados.setModel(visadosModel);
			tbVisados.setIntercellSpacing(new Dimension(0, 0));
			tbVisados.setShowGrid(false);
			tbVisados.setRowMargin(0);
			tbVisados.setRequestFocusEnabled(false);
			tbVisados.setFocusable(false);
			tbVisados.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbVisados.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbVisados.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbVisados.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbVisados.setShowVerticalLines(false);
			tbVisados.setOpaque(false);

			tbVisados.setRowHeight(80);
			tbVisados.setGridColor(new Color(255, 255, 255));
		}
		return tbVisados;
	}
	private JButton getBtnAsignarVisado() {
		if (btnAsignarVisado == null) {
			btnAsignarVisado = new JButton("Asignar visado");
			btnAsignarVisado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<SolicitudVisadoDto> visados = SolicitudVisadosCRUD.findAllSolicitudesVisado();
					List<ColegiadoDto> peritosDisponibles = PeritoCRUD.findPeritosDisponiblesParaVisado();
					
					int visadoIndex = getTbVisados().getSelectedRow();
					int peritoIndex = getTbPeritosDisponibles().getSelectedRow();
					
					if(visadoIndex == -1) {
						JOptionPane.showMessageDialog(pnAsignarVisados, "No se ha seleccionado un visado para revisar");
						return;
					}
					
					if(peritoIndex == -1) {
						JOptionPane.showMessageDialog(pnAsignarVisados, "No se ha seleccionado un perito para la pericial");
						return;
					}
					
					SolicitudVisadoDto s = visados.get(visadoIndex);
					ColegiadoDto c = peritosDisponibles.get(peritoIndex);
					
					if (s.dniPerito.equals(c.DNI)) {
						JOptionPane.showMessageDialog(pnAsignarVisados, "No puede asignar su visado a sí mismo");
						return;
					}
					
					if (s.estado.equals("ASIGNADA")) {
						JOptionPane.showMessageDialog(pnAsignarVisados, "Este visado ya ha sido asignado previamente");
						return;
					}
					
					SolicitudVisadosCRUD.asignarVisadoAPerito(s, c);
					JOptionPane.showMessageDialog(pnAsignarVisados, "Visado asignado correctamente");
					
					TableModel peritosModel = new ModeloPeritosDisponiblesParaVisado(PeritoCRUD.findPeritosDisponiblesParaVisado()).getSolicitudModel();
					tbPeritosDisponibles.setModel(peritosModel);
					tbPeritosDisponibles.repaint();
					
					TableModel visadosModel = new ModeloSolicitudesVisados(SolicitudVisadosCRUD.findAllSolicitudesVisado()).getSolicitudModel();
					tbVisados.setModel(visadosModel);
					tbVisados.repaint();
					
				}
			});
			btnAsignarVisado.setBackground(Color.GREEN);
			btnAsignarVisado.setFont(new Font("Tahoma", Font.PLAIN, 17));
		}
		return btnAsignarVisado;
	}
	private JScrollPane getSpPeritosDisponibles() {
		if (spPeritosDisponibles == null) {
			spPeritosDisponibles = new JScrollPane();
			spPeritosDisponibles.setViewportView(getTbPeritosDisponibles());
		}
		return spPeritosDisponibles;
	}
	private JTable getTbPeritosDisponibles() {
		if (tbPeritosDisponibles == null) {
			tbPeritosDisponibles = new JTable();
			tbPeritosDisponibles.setFont(new Font("Tahoma", Font.PLAIN, 17));
			TableModel peritosModel = new ModeloPeritosDisponiblesParaVisado(PeritoCRUD.findPeritosDisponiblesParaVisado()).getSolicitudModel();
			tbPeritosDisponibles.setModel(peritosModel);
			tbPeritosDisponibles.setIntercellSpacing(new Dimension(0, 0));
			tbPeritosDisponibles.setShowGrid(false);
			tbPeritosDisponibles.setRowMargin(0);
			tbPeritosDisponibles.setRequestFocusEnabled(false);
			tbPeritosDisponibles.setFocusable(false);
			tbPeritosDisponibles.setSelectionForeground(LookAndFeel.TERTIARY_COLOR);
			tbPeritosDisponibles.setSelectionBackground(LookAndFeel.SECONDARY_COLOR);
			tbPeritosDisponibles.setBorder(new EmptyBorder(10, 10, 10, 10));
			tbPeritosDisponibles.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tbPeritosDisponibles.setShowVerticalLines(false);
			tbPeritosDisponibles.setOpaque(false);

			tbPeritosDisponibles.setRowHeight(80);
			tbPeritosDisponibles.setGridColor(new Color(255, 255, 255));
			
		}
		return tbPeritosDisponibles;
	}
	private JButton getBtnActualizarVisados() {
		if (btnActualizarVisados == null) {
			btnActualizarVisados = new JButton("Actualizar lista");
			btnActualizarVisados.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TableModel visadosModel = new ModeloSolicitudesVisados(SolicitudVisadosCRUD.findAllSolicitudesVisado()).getSolicitudModel();
					tbVisados.setModel(visadosModel);
					tbVisados.repaint();
				}
			});
			btnActualizarVisados.setFont(new Font("Tahoma", Font.PLAIN, 17));
		}
		return btnActualizarVisados;
	}
	
	private JPanel getPnTxPorcentajeDevolucionCursoCancelableWrapper() {
		if (pnTxPorcentajeDevolucionCursoCancelableWrapper == null) {
			pnTxPorcentajeDevolucionCursoCancelableWrapper = new JPanel();
			pnTxPorcentajeDevolucionCursoCancelableWrapper.setLayout(new BorderLayout(10, 0));
			pnTxPorcentajeDevolucionCursoCancelableWrapper.add(getLbSimboloPorcentaje(), BorderLayout.EAST);
			pnTxPorcentajeDevolucionCursoCancelableWrapper.add(getSpPorcentajeDevolucionCursoCancelable());
		}
		return pnTxPorcentajeDevolucionCursoCancelableWrapper;
	}

	private JLabel getLbSimboloPorcentaje() {
		if (lbSimboloPorcentaje == null) {
			lbSimboloPorcentaje = new JLabel("%");
			lbSimboloPorcentaje.setHorizontalAlignment(SwingConstants.CENTER);
			lbSimboloPorcentaje.setFont(LookAndFeel.HEADING_2_FONT);
		}
		return lbSimboloPorcentaje;
	}

	private JSpinner getSpPorcentajeDevolucionCursoCancelable() {
		if (spPorcentajeDevolucionCursoCancelable == null) {
			spPorcentajeDevolucionCursoCancelable = new JSpinner();
			spPorcentajeDevolucionCursoCancelable.setFont(LookAndFeel.LABEL_FONT);
			spPorcentajeDevolucionCursoCancelable
					.setToolTipText("Introduzca el porcentaje de devolución del curso (Entre 0 y 100)");
			spPorcentajeDevolucionCursoCancelable.setModel(new SpinnerNumberModel(0.0, 0.0, 100.0, 0.1));

			spPorcentajeDevolucionCursoCancelable.setLocale(DateUtils.DEFAULT_LOCALE);

			/* El campo se habilitará si se selecciona la opcion de cancelable */
			spPorcentajeDevolucionCursoCancelable.setEnabled(false);
		}
		return spPorcentajeDevolucionCursoCancelable;
	}
	private JPanel getPnListadoAltaSolicitanteRecepcionLoteBotonesWrapper() {
		if (pnListadoAltaSolicitanteRecepcionLoteBotonesWrapper == null) {
			pnListadoAltaSolicitanteRecepcionLoteBotonesWrapper = new JPanel();
			GridBagLayout gbl_pnListadoAltaSolicitanteRecepcionLoteBotonesWrapper = new GridBagLayout();
			gbl_pnListadoAltaSolicitanteRecepcionLoteBotonesWrapper.columnWidths = new int[]{227, 275, 0};
			gbl_pnListadoAltaSolicitanteRecepcionLoteBotonesWrapper.rowHeights = new int[]{85, 0};
			gbl_pnListadoAltaSolicitanteRecepcionLoteBotonesWrapper.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			gbl_pnListadoAltaSolicitanteRecepcionLoteBotonesWrapper.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			pnListadoAltaSolicitanteRecepcionLoteBotonesWrapper.setLayout(gbl_pnListadoAltaSolicitanteRecepcionLoteBotonesWrapper);
			GridBagConstraints gbc_btActualizarListaSolicitudesColegiado = new GridBagConstraints();
			gbc_btActualizarListaSolicitudesColegiado.fill = GridBagConstraints.BOTH;
			gbc_btActualizarListaSolicitudesColegiado.insets = new Insets(0, 0, 0, 5);
			gbc_btActualizarListaSolicitudesColegiado.gridx = 0;
			gbc_btActualizarListaSolicitudesColegiado.gridy = 0;
			pnListadoAltaSolicitanteRecepcionLoteBotonesWrapper.add(getBtActualizarListaSolicitudesColegiado(), gbc_btActualizarListaSolicitudesColegiado);
			GridBagConstraints gbc_btRecepcionarLoteSolicitudesPendientesColegiado = new GridBagConstraints();
			gbc_btRecepcionarLoteSolicitudesPendientesColegiado.fill = GridBagConstraints.BOTH;
			gbc_btRecepcionarLoteSolicitudesPendientesColegiado.gridx = 1;
			gbc_btRecepcionarLoteSolicitudesPendientesColegiado.gridy = 0;
			pnListadoAltaSolicitanteRecepcionLoteBotonesWrapper.add(getBtRecepcionarLoteSolicitudesPendientesColegiado(), gbc_btRecepcionarLoteSolicitudesPendientesColegiado);
		}
		return pnListadoAltaSolicitanteRecepcionLoteBotonesWrapper;
	}
}
