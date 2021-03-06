package entidades;

import java.awt.Color;

import exception.DimensionNoValidaException;

import mates.Matriz;
import mates.Vector2D;


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
		this(anchura,altura,posicion,velocidad,aceleracion,0,0,0,color);
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
		this(anchura,altura,posicion,velocidad,aceleracion,0,0,masa,color);
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
		this(anchura,altura,posicion,velocidad,aceleracion,posicionAngular,velocidadAngular,aceleracionAngular,0,color);
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
	 * Devuelve la altura del rect�ngulo en p�xeles
	 * 
	 * @return altura del rect�ngulo
	 */
	public double getAltura() {
		return altura;
	}

	/**
	 * Devuelve la anchura del rect�ngulo en p�xeles
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
	 * @param anchura en p�xeles
	 * @param altura en p�xeles
	 * @return masa
	 */
	public static double getMasa (double anchura,double altura) {
		return anchura*altura;
	}
	
	/**
	 * Devuelve la matriz de puntos de un rect�ngulo
	 * 
	 * @param anchura en p�xeles
	 * @param altura en p�xeles
	 * @return Matriz de puntos del rect�ngulo con coordenadas en p�xeles
	 */
	public static Matriz getMatrizPuntos(double anchura, double altura) {
		return new Matriz(2,4,
				-anchura/2,	+anchura/2,	+anchura/2,	-anchura/2,
				+altura/2,	+altura/2,	-altura/2,	-altura/2
		);

	}
}