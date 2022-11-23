package persistence.InscripcionColegiado.listaEsperaInscripcionCurso;

public class ListaEsperaInscripcionCursoDto {

	public static final String DNI_USUARIO = "DNI";
	public static final String NOMBRE_USUARIO = "NOMBRE";
	public static final String ID_CURSO = "ID_CURSO";
	public static final String POS_USUARIO = "POSICION";

	public String dniUsuario;
	public String nombreUsuario;
	public int posicionUsuarioLista; // Posición que ostenta el usuario en la lista.

	public int idCurso;

}
