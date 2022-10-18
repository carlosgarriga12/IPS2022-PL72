package persistence.recibo;

public class MainCuotas {
	public static void main(String []args) {
		if(ReciboCRUD.emitirCuotas()) {
			System.out.println("Cuotas emitidas correctamente");
		} else {
			System.out.println("No se han encontrado cuotas para emitir");
		}
	}
}