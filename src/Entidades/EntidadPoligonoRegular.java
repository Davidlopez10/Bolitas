package Entidades;

import java.awt.Color;

import Exception.DimensionNoValidaException;
import Mates.Matriz;
import Mates.Vector2D;


public class EntidadPoligonoRegular extends EntidadPoligono {
	
	private double radio;
	
	/**
	 * Constructor por defecto (omite las caracter�ticas de giro de la entidad)
	 * 
	 * @param n
	 * @param radio
	 * @param posicion
	 * @param velocidad
	 * @param aceleracion
	 * @param masa
	 * @param color
	 * @throws DimensionNoValidaException
	 */
	public EntidadPoligonoRegular(int n, int radio, Vector2D posicion, Vector2D velocidad, Vector2D aceleracion, double masa, Color color) throws DimensionNoValidaException {
		super(getMatrizPuntos(n,radio),posicion,velocidad,aceleracion,masa,color);
		this.radio = radio;
	}
	
	/**
	 * Constructor b�sico.
	 * 
	 * @param n
	 * @param radio
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
	public EntidadPoligonoRegular(int n, int radio, Vector2D posicion, Vector2D velocidad, Vector2D aceleracion, double posicionAngular, double velocidadAngular, double aceleracionAngular, double masa, Color color) throws DimensionNoValidaException {
		super(getMatrizPuntos(n,radio),posicion,velocidad,aceleracion,posicionAngular,velocidadAngular,aceleracionAngular,masa,color);
		this.radio = radio;
	}
	
	/**
	 * Devuelve el radio del pol�gono
	 * 
	 * @return Radio del pol�gono
	 */
	public double getRadio() {
		return this.radio;
	}
	
	//TODO
	/**
	 * Establece un nuevo radio para el pol�gono
	 * 
	 * @param radio Nuevo radio para el pol�gono.
	 */
	@Deprecated
	public void setRadio(double radio) {
		//TODO
	}
	
	/**
	 * Dado el n�mero de vertices y el radio de los mismos, devuelve la matriz de puntos del pol�gono regular correspondiente
	 * 
	 * @param numVertices N�mero de v�rtices y lados del pol�gono
	 * @param radio Radio del pol�gono
	 * @return Matriz de puntos del pol�gono regular
	 */
	public static Matriz getMatrizPuntos(int numVertices, double radio) {
		
		Matriz matrizPuntos = new Matriz(2,numVertices);
		
		double angulo = 2*Math.PI / numVertices;
		Matriz matrizGiro = Matriz.getMatriz2x2Giro(angulo);
		
		// Iniciamos el primer vertice en el semieje OX positivo
		Vector2D vertice = new Vector2D(radio,0); 
		
		for (int i=0; i<numVertices; i++) {
			matrizPuntos.setColumna(i, vertice);
			vertice = new Vector2D(matrizGiro.por(vertice));
		}
		
		return matrizPuntos;
	}
	
	/* (non-Javadoc)
	 * @see Entidades.EntidadPoligono#toString()
	 */
	@Override
	public String toString() {
		return (super.toString() + "\n Poligono regular.");
	}
}