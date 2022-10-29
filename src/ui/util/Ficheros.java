package ui.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import persistence.InscripcionColegiado.InscripcionColegiadoTransferenciaBancoDto;

public class Ficheros {
	
	public static void escribirFichero(List<InscripcionColegiadoTransferenciaBancoDto> datosTransferencia, int cursoSeleccionado) throws IOException {
		FileWriter file = new FileWriter(new File("files_transferencias/" + cursoSeleccionado + "_banco.csv"));
		String sb = "";
        sb = "DNI;Nombre;Apellidos;Cantidad abonada;Fecha de transferencia;Código de transferencia\n";
        for (int i=0; i < datosTransferencia.size(); i++) {
        	sb += datosTransferencia.get(i).toString();
        }
        file.write(sb);
        file.close();		
	}
	
	public static List<InscripcionColegiadoTransferenciaBancoDto> leerFichero(int id){
        List<InscripcionColegiadoTransferenciaBancoDto> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(id + "_banco.csv"));) {
        	while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return records;
    }
	
    private static InscripcionColegiadoTransferenciaBancoDto getRecordFromLine(String line) {
    	line = line.substring(0, line.length()-1);
    	InscripcionColegiadoTransferenciaBancoDto values = new InscripcionColegiadoTransferenciaBancoDto();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(";");
            while (rowScanner.hasNext()) {
                values.dni = rowScanner.next();
                values.nombre = rowScanner.next();
                values.apellidos = rowScanner.next();
                values.cantidad = Double.parseDouble(rowScanner.next());
                values.fecha = rowScanner.next();
                values.codigo = rowScanner.next();
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
            int indiceAleatorio = new Random().nextInt(0, banco.length() - 1);
            char caracterAleatorio = banco.charAt(indiceAleatorio);
            cadena += caracterAleatorio;
        }
        return cadena;
    }
    


}
