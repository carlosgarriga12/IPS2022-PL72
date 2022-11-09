package ui.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import persistence.InscripcionColegiado.InscripcionColegiadoDto;
import persistence.colegiado.ColegiadoDto;

public class Ficheros {
	
	public static void escribirFichero(List<InscripcionColegiadoDto> datosTransferencia, int cursoSeleccionado) throws IOException {
		FileWriter file = new FileWriter(new File("files_transferencias/" + cursoSeleccionado + "_banco.csv"));
		String sb = "";
        sb = "DNI;Nombre;Apellidos;Cantidad abonada;Fecha de transferencia;Código de transferencia\n";
        for (int i=0; i < datosTransferencia.size(); i++) {
        	sb += datosTransferencia.get(i).toString();
        }
        file.write(sb);
        file.close();		
	}
	
	public static List<InscripcionColegiadoDto> leerFichero(int id){
        List<InscripcionColegiadoDto> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("files_transferencias/" + id + "_banco.csv"));) {
    		scanner.nextLine();
        	while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return records;
    }
	
    private static InscripcionColegiadoDto getRecordFromLine(String line) {
    	line = line.substring(0, line.length()-1);
    	InscripcionColegiadoDto values = new InscripcionColegiadoDto();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(";");
            while (rowScanner.hasNext()) {
            	values.colegiado = new ColegiadoDto();
                values.colegiado.DNI = rowScanner.next();
                values.colegiado.nombre = rowScanner.next();
                values.colegiado.apellidos = rowScanner.next();
                values.cantidadPagada = Double.parseDouble(rowScanner.next());
                String fecha = rowScanner.next();
                String codigo = rowScanner.next();
                if (fecha.equals("NO REALIZADA")) {
                	values.fechaTransferencia = null;
                	values.codigoTransferencia = null;
                } else {
                	values.fechaTransferencia = LocalDate.parse(fecha);
                	values.codigoTransferencia = codigo;
                }
            }
        }
        return values;
    }
    
    public static String generarCodigoTransferencia(int longitud) {
        // El banco de caracteres
        String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        // La cadena en donde iremos agregando un carácter aleatorio
        String cadena = "";
        for (int x = 0; x < longitud; x++) {
            int indiceAleatorio = new Random().nextInt(banco.length() - 1);
            char caracterAleatorio = banco.charAt(indiceAleatorio);
            cadena += caracterAleatorio;
        }
        
        return cadena;
    }
    


}
