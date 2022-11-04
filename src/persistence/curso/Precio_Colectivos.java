package persistence.curso;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Precio_Colectivos {
	ArrayList<String> colectivos_precios;
	public Precio_Colectivos(){
		colectivos_precios = new ArrayList<String>();
	}
	
	public Precio_Colectivos(String[] colectivos_precios) {
		this.colectivos_precios = new ArrayList<>();
		for(int i=0;i<colectivos_precios.length;i++) {
			this.colectivos_precios.add(colectivos_precios[i]);
		}
	}
	
	public void add(String colectivo_precio) {
		colectivos_precios.add(colectivo_precio);
	}
	
	public void add(String colectivo, double precio) {
		add(colectivo+":"+precio);
	}
	
	public void removeColectivo(String colectivo) {
		int index = indiceColectivo(colectivo);
		if(index != -1) {
			colectivos_precios.remove(index);
		}
	}
	
	public boolean remove(String colectivo_precio) {
		return colectivos_precios.remove(colectivo_precio);
	}
	
	public int size() {
		return colectivos_precios.size();
	}
	
	public int indiceColectivo(String colectivo) {
		return colectivos_precios.stream().map(s -> s.split(":")[0]).collect(Collectors.toList()).indexOf(colectivo);
	}
	
	public boolean containsAlgunColectivo(String ... colectivo) {
		for(int i=0;i<colectivo.length;i++) {
			if(containsColectivo(colectivo[i])) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsColectivo(String colectivo) {
		if(indiceColectivo(colectivo)==-1) {
			return false;
		}
		return true;
	}
	
	public Double getPrecio(String colectivo) {
		int index = indiceColectivo(colectivo);
		if(index == -1) {
			return null;
		}
		String colectivo_precio = colectivos_precios.get(index);
		double precio = Double.valueOf(colectivo_precio.split(":")[1]);
		return precio;
	}
	
	public ArrayList<String> getColectivos_precios() {
		return colectivos_precios;
	}

	public String toString() {
		String s = "";
		for(String colectivo_precio:colectivos_precios) {
			s += colectivo_precio+";";
		}
		s = s.substring(0, s.length()-1);
		return s;
	}
	
	public static Precio_Colectivos StringToPrecio_Colectivos(String s) {
		Precio_Colectivos c = new Precio_Colectivos(s.split(";"));
		return c;
	}

}
