package Entidades;

import java.awt.Color;

import Exception.DimensionNoValidaException;
import Mates.Matriz;
import Mates.Vector2D;

public class EntidadRectangulo extends EntidadPoligono {
	
	private double altura;
	private double anchura;
	
	/**
	 * Constructor b�sico (omite las caracter�sticas de giro y masa)
	 * 
	 * @param anchura
	 * @param altura
	 * @param posicion
	 * @param velocidad
	 * @param aceleracion
	 * @param color
	 * @throws DimensionNoValidaException
	 */
	public EntidadRectangulo(double anchura, double altura, Vector2D posicion, Vector2D velocidad, Vector2D aceleracion, Color color) throws DimensionNoValidaException {
		super(getMatrizPuntos(anchura,altura), posicion,velocidad,aceleracion,EntidadRectangulo.getMasa(anchura, altura), color);
		this.altura=altura;
		this.anchura = anchura;
	}
	
	/**
	 * Constructor b�sico  (omite caracter�sticas de giro)
	 * 
	 * @param anchura
	 * @param altura
	 * @param posicion
	 * @param velocidad
	 * @param aceleracion
	 * @param masa
	 * @param color
	 * @throws DimensionNoValidaException
	 */
	public EntidadRectangulo(double anchura, double altura, Vector2D posicion, Vector2D velocidad, Vector2D aceleracion, double masa, Color color) throws DimensionNoValidaException {
		super(getMatrizPuntos(anchura,altura),posicion,velocidad,aceleracion, masa, color);
		this.altura=altura;
		this.anchura = anchura;
	}
	
	/**
	 *  Constructor b�sico (omite masa)
	 * 
	 * @param anchura
	 * @param altura
	 * @param posicion
	 * @param velocidad
	 * @param aceleracion
	 * @param posicionAngular
	 * @param velocidadAngular
	 * @param aceleracionAngular
	 * @param color
	 * @throws DimensionNoValidaException
	 */
	public EntidadRectangulo (double anchura, double altura, Vector2D posicion, Vector2D velocidad, Vector2D aceleracion, double posicionAngular, double velocidadAngular, double aceleracionAngular, Color color) throws DimensionNoValidaException {
		super(getMatrizPuntos(anchura,altura),posicion,velocidad,aceleracion,posicionAngular, velocidadAngular, aceleracionAngular, EntidadRectangulo.getMasa(anchura, altura), color);
		this.altura=altura;
		this.anchura = anchura;
	}
	
	/**
	 * Constructor b�sico
	 * 
	 * @param anchura
	 * @param altura
	 * @param posicion
	 * @param velocidad
	 * @param aceleracion
	 * @param posicionAngular
	 * @param velocidadAngular
	 * @param aceleracionAngular
	 * @param masa
	 * @param color
	 * @throws DimensionNoValidaException
	 */
	public EntidadRectangulo (double anchura, double altura, Vector2D posicion, Vector2D velocidad, Vector2D aceleracion, double posicionAngular, double velocidadAngular, double aceleracionAngular, double masa, Color color) throws DimensionNoValidaException {
		super(getMatrizPuntos(anchura,altura),posicion,velocidad,aceleracion,posicionAngular, velocidadAngular, aceleracionAngular, masa, color);
		this.altura=altura;
		this.anchura = anchura;
	}

	/**
	 * Devuelve la altura del rect�ngulo
	 * 
	 * @return altura del rect�ngulo
	 */
	public double getAltura() {
		return altura;
	}

	/**
	 * Devuelve la anchura del rect�ngulo
	 * 
	 * @return anchura del rect�ngulo
	 */
	public double getAnchura() {
		return anchura;
	}
	
	/* (non-Javadoc)
	 * @see Entidades.EntidadPoligono#toString()
	 */
	public String toString() {
		return (super.toString() + "\nRectangulo, dimensiones: Altura:" + altura + " Anchura: " + anchura);
	}
	
	/**
	 * Devuelve la masa dada la anchura y la altura
	 * 
	 * @param anchura
	 * @param altura
	 * @return masa
	 */
	public static double getMasa (double anchura,double altura) {
		return anchura*altura;
	}
	
	/**
	 * Devuelve la matriz de puntos de un rect�ngulo
	 * 
	 * @param anchura 
	 * @param altura
	 * @return Matriz de puntos del rect�ngulo
	 */
	public static Matriz getMatrizPuntos(double anchura, double altura) {
		return new Matriz(2,4,
				-anchura/2,	+anchura/2,	+anchura/2,	-anchura/2,
				+altura/2,	+altura/2,	-altura/2,	-altura/2
		);

	}
}