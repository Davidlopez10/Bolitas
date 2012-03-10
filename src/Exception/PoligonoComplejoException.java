package Exception;

import Mates.Matriz;

/**
 * Excepcion generada cuando se da una matriz para definir un polígono de forma que
 * el polígono producido es complejo (tiene lados que se cruzan) 
 * @author Jose Diaz
 */

@SuppressWarnings("serial")
public class PoligonoComplejoException extends IllegalArgumentException {
	
	private double puntos[][];
	
	/**
	 * Constructor por defecto
	 */
	public PoligonoComplejoException() {
		super();
		puntos=null;
	}
	
	/**
	 * Constructor con la causa
	 * 
	 * @param s causa de la excepcion
	 */
	public PoligonoComplejoException(String s) {
		super(s);
	}
	
	/**
	 * Constructor con la entidad
	 * 
	 * @param m Matriz de puntos del polígono complejo que causó la excepción
	 */
	public PoligonoComplejoException(Matriz m) {
		super();
		this.puntos=m.getDatos();
	}
	
	/**
	 * Constructor con la entidad
	 * 
	 * @param puntos Array de arrays, puntos del polígono complejo que causó la excepción
	 */
	public PoligonoComplejoException(double[][] puntos) {
		super();
		this.puntos=puntos;
	}
	
	/**
	 * Constructor con la entidad y el motivo
	 * 
	 * @param s causa de la excepción
	 * @param m Matriz de puntos del polígono complejo que causó la excepción
	 */
	public PoligonoComplejoException(String s, Matriz m) {
		super(s);
		this.puntos=m.getDatos();
		
	}
	
	/**
	 * Constructor con la entidad y el motivo
	 * 
	 * @param s causa de la excepción
	 * @param puntos Array de arrays, puntos del polígono complejo que causó la excepción
	 */
	public PoligonoComplejoException(String s, double[][] puntos) {
		super(s);
		this.puntos=puntos;
		
	}
	
	/**
	 * Devuelve los puntos que causaron la excepcion
	 * 
	 * @return Array de arrays, puntos que causaron la excepcion
	 */
	public double[][] getPuntos() {
		return this.puntos;
	}
	
}