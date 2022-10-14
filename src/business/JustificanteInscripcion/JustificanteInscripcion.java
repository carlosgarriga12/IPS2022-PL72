package business.JustificanteInscripcion;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import business.BusinessException;
import persistence.colegiado.ColegiadoDto;
import persistence.curso.CursoDto;

public class JustificanteInscripcion {
	
	public static void EmitirJustificante(ColegiadoDto colegiado, CursoDto curso) throws BusinessException {
		String ruta = curso.codigoCurso+"_"+colegiado.DNI+".txt";
		String contenido = "Nombre: "+colegiado.nombre+" "+colegiado.apellidos+"  Número Colegiado: " + colegiado.numeroColegiado + "  Fecha Solicitud: "
				+ LocalDate.now().toString() + "  Cantidad a Abonar: " + curso.precio;
		File file = new File(ruta);
           try {
        	   	if(!file.exists()) {
        	   		file.createNewFile();
        	   	}
	            FileWriter fw = new FileWriter(file);
	            BufferedWriter bw = new BufferedWriter(fw);
	            bw.write(contenido);
	            bw.close();
            } catch (IOException e) {
				// TODO Auto-generated catch block
				throw new BusinessException(e);
			}
		
		
	}
}
