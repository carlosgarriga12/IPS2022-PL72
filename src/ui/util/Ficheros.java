package ui.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ficheros {
	
	public static void escribirFichero(List<String[]> datosTransferencia, int cursoSeleccionado) throws IOException {
		FileWriter file = new FileWriter(new File("files_transferencias/" + cursoSeleccionado + "_banco.csv"));
		StringBuilder sb = new StringBuilder();
        sb.append("DNI;Nombre;Apellidos;Cantidad abonada;Fecha de transferencia;Código de transferencia\n");
        for (int i=0; i < datosTransferencia.size(); i++) {
        	for (int j=0; j < datosTransferencia.get(i).length; j++)
        		if (! (j==datosTransferencia.get(i).length-1))
        			sb.append(datosTransferencia.get(i) + ";");
        		else {
        			sb.append(datosTransferencia.get(i) + "\n");
        		}
        }
        file.write(sb.toString());
        file.close();		
	}
	
	public static List<List<String>> leerFichero(int id){
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(id + "_banco.csv"));) {
        	while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return records;
    }
	
    private static List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(";");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }
    


}
