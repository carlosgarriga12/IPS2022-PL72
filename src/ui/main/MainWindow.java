package ui.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
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
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import business.BusinessException;
import business.curso.Curso;
import business.inscripcion.InscripcionCursoFormativo;
import business.util.DateUtils;
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

	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1126, 710);
		mainPanel = new JPanel();
		mainPanel.setName("");
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
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
								"Al hacer click en confirmar, se abrirá la inscripción para el curso seleccionado ¿Confirmar apertura?");

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
		((DefaultMessage) pnListCoursesSouthMessages).setMessageColor(type);
		((DefaultMessage) pnListCoursesSouthMessages).setMessage("<html>" + e1.getMessage() + "</html>");
	}
}
