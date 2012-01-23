package Entidades;

import java.awt.Color;

import Exception.DimensionNoValidaException;
import Mates.Matriz;
import Mates.Vector;

// TODO: Giros en torno al centro...
// TODO: Implementar colisiones...

public class EntidadRectangulo extends EntidadPoligono {
	
	private double altura;
	private double anchura;
	
	public EntidadRectangulo(double anchura, double altura, Vector posicion, Vector velocidad, Vector aceleracion, Color color) throws DimensionNoValidaException {
		super(getMatrizPuntos(anchura,altura), posicion,velocidad,aceleracion,EntidadRectangulo.getMasa(anchura, altura), color);
		this.altura=altura;
		this.anchura = anchura;
	}
	
	public EntidadRectangulo(double anchura, double altura, Vector posicion, Vector velocidad, Vector aceleracion, double masa, Color color) throws DimensionNoValidaException {
		super(getMatrizPuntos(anchura,altura),posicion,velocidad,aceleracion, masa, color);
		this.altura=altura;
		this.anchura = anchura;
	}
	
	public EntidadRectangulo (double anchura, double altura, Vector posicion, Vector velocidad, Vector aceleracion, double posicionAngular, double velocidadAngular, double aceleracionAngular, Color color) throws DimensionNoValidaException {
		super(getMatrizPuntos(anchura,altura),posicion,velocidad,aceleracion,posicionAngular, velocidadAngular, aceleracionAngular, EntidadRectangulo.getMasa(anchura, altura), color);
		this.altura=altura;
		this.anchura = anchura;
	}
	
	public EntidadRectangulo (double anchura, double altura, Vector posicion, Vector velocidad, Vector aceleracion, double posicionAngular, double velocidadAngular, double aceleracionAngular, double masa, Color color) throws DimensionNoValidaException {
		super(getMatrizPuntos(anchura,altura),posicion,velocidad,aceleracion,posicionAngular, velocidadAngular, aceleracionAngular, masa, color);
		this.altura=altura;
		this.anchura = anchura;
	}

	public double getAltura() {
		return altura;
	}

	public double getAnchura() {
		return anchura;
	}
	
	public String toString() {
		return (super.toString() + "\nRectangulo, dimensiones: Altura:" + altura + " Anchura: " + anchura);
	}
	
	public static double getMasa (double anchura,double altura) {
		return anchura*altura;
	}
	
	public static Matriz getMatrizPuntos(double anchura, double altura) {
		try {
			return new Matriz(2,4,
					-anchura/2+40,	+anchura/2,	+anchura/2,	-anchura/2,
					+altura/2-50,	+altura/2,	-altura/2,	-altura/2
			);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}