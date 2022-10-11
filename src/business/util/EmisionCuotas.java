package business.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EmisionCuotas {

	public static void main(String[] args) {
		BufferedWriter bw = null;
		try {
			File fichero = new File("recibos/fichero.txt");

			System.out.println(fichero.getCanonicalPath()); // Path completodonde se crear� el fichero.

			bw = new BufferedWriter(new FileWriter(fichero));

			bw.write("Mensaje a escribir en el fichero");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close(); // Cerramos el buffer
			} catch (Exception e) {
			}
		}
	}
}
