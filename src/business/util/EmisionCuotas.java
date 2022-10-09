package business.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import persistence.colegiado.ColegiadoDto;

public class EmisionCuotas {

	public static void main(String[] args) {
		LocalDate fechaEmision = LocalDate.now();
		double cantidad = 50.0; // Cantidad fija a pagar tanto para colegiados como pre-colegiados
		int numeroDeRecibo = 1; //Numero que va incrementando para la generación de recibos
		
		int year = LocalDate.now().getYear();
				
//		File archivo = new File("recibos/fichero" + year + ".txt");
//		if (archivo.exists()) {
//		    return;
//		}
		
		BufferedWriter bw = null;
		try {
			File fichero = new File("recibos/fichero" + year + ".txt");

			bw = new BufferedWriter(new FileWriter(fichero));

			bw.write("Fichero de recibos \n");
			bw.write("Numero de recibo\tFecha de emision\tDNI\t\tNumero cuenta\t\tCantidad\n");
			
			bw.write(numeroDeRecibo++ +"\t\t\t\t\t"+ fechaEmision + "\t\t\t" + "555" + "\t\t" + "123456789090" + "\t\t" + cantidad + "\n");
			bw.write(numeroDeRecibo++ +"\t\t\t\t\t"+ fechaEmision + "\t\t\t" + "555" + "\t\t" + "123456789090" + "\t\t" + cantidad + "\n");
			bw.write(numeroDeRecibo++ +"\t\t\t\t\t"+ fechaEmision + "\t\t\t" + "555" + "\t\t" + "123456789090" + "\t\t" + cantidad + "\n");
			bw.write(numeroDeRecibo++ +"\t\t\t\t\t"+ fechaEmision + "\t\t\t" + "555" + "\t\t" + "123456789090" + "\t\t" + cantidad + "\n");
			
			bw.write("");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close(); // Cerramos el buffer
			} catch (Exception e) {
				
			}
		}
	}

	public static void emitirCuotas(List<ColegiadoDto> colegiados) {
		
	}
}
