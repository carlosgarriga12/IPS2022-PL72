package business.recibo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import persistence.colegiado.ColegiadoDto;

public class EmisionCuotas {

	public static void emitirCuotas(List<ColegiadoDto> colegiados, List<Integer> numerosRecibo) {
		LocalDate fechaEmision = LocalDate.now();
		double cantidad = 50.0; // Cantidad fija a pagar tanto para colegiados como pre-colegiados
		
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
			
			for(int i = 0; i < colegiados.size(); i++) {
				bw.write(numerosRecibo.get(i) +"\t\t\t\t\t"+
						fechaEmision + "\t\t\t" + colegiados.get(i).DNI+ 
						"\t\t" + colegiados.get(i).numeroTarjeta + "\t\t" + cantidad + "\n");
			}
			
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
}
