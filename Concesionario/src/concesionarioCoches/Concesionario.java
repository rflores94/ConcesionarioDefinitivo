/**
 * Paquete que contiene los archivos del programa
 */
package concesionarioCoches;

import java.io.Serializable;
/**
 * Importaci�n de la clase ArrayList
 */
import java.util.ArrayList;

/**
 * Concesionario que almacena coches, los coches no pueden tener igual matricula
 * y deben de contener todos los campos especificados en la clase coche
 * 
 * @author Roberto Carlos Flores Gomez
 * @version 1.0
 * 
 */
public class Concesionario implements Serializable {
	
	/**
	 * Colecci�n de coches. No puede tener null.
	 */
	private ArrayList<Coche> almacen = new ArrayList<Coche>();
	
	/**
	 * Nombre del concesionario
	 */
	private final String nombre = "IES Gran Capit�n";

	/**
	 * A�ade un coche al almacen
	 * 
	 * @param matricula
	 *            Representa la matr�cula del coche a a�adir
	 * @param color
	 *            Representa el color del coche a a�adir
	 * @param modelo
	 *            Representa el modelo del coche a a�adir
	 * @return true si el coche se a�ade, false en otro caso (el coche es null o
	 *         el coche ya est� contenido en el almacen)
	 */
	public boolean annadir(String matricula, Color color, Modelo modelo) {
		Coche coche = Coche.instanciarCoche(matricula, color, modelo);
		if (coche == null || almacen.contains(coche))
			return false;
		return almacen.add(coche);
	}

	/**
	 * Elimina un coche del almacen
	 * 
	 * @param matricula
	 *            Representa la matr�cula del coche a eliminar
	 * @return true si el coche se elimina, false en otro caso (el coche no est�
	 *         en el almacen)
	 */
	public boolean eliminar(String matricula) {
		return almacen.remove(Coche.instanciarCoche(matricula));
	}

	/**
	 * Devuelve el n�mero de coches del almacen
	 * 
	 * @return N�mero de coches del almacen
	 */
	public int size() {
		return almacen.size();
	}

	/**
	 * Devuelve el coche indicado por la matr�cula
	 * 
	 * @param matricula
	 *            Representa la matr�cula a buscar
	 * @return Coche contenido en el almacen. null si no existe
	 */
	public Coche get(String matricula) {
		Coche coche = Coche.instanciarCoche(matricula);
		int index = almacen.indexOf(coche);
		if (index != -1) {
			return almacen.get(index);
		}
		return null;
	}
	
	public Coche get(int index) {
		if(almacen.isEmpty())
			return null;
		if(index < 0 | index > almacen.size()-1)
			return null;
		return almacen.get(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Concesionario " + nombre + "[almacen=" + almacen + "]";
	}

	/**
	 * Genera una lista de coches de un determinado color
	 * 
	 * @param color
	 *            Representa el color a buscar
	 * @return Lista de coches de un determinado color
	 */
	public ArrayList<Coche> getCochesColor(Color color) {
		ArrayList<Coche> arrCochesColor = new ArrayList<Coche>();
		for (Coche coche : almacen) {
			if(coche.getColor()== color)
				arrCochesColor.add(coche);
		}
		return arrCochesColor;
	}
}