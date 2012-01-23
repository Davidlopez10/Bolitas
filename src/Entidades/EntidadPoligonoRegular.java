package Entidades;

import java.awt.Color;

import Exception.DimensionNoValidaException;
import Mates.Matriz;
import Mates.Vector;


public class EntidadPoligonoRegular extends EntidadPoligono {
	public EntidadPoligonoRegular(int n, int radio, Vector posicion, Vector velocidad, Vector aceleracion, double masa, Color color) throws DimensionNoValidaException {
		super(getMatrizPuntos(n,radio),posicion,velocidad,aceleracion,masa,color);
	}
	
	public EntidadPoligonoRegular(int n, int radio, Vector posicion, Vector velocidad, Vector aceleracion, double posicionAngular, double velocidadAngular, double aceleracionAngular, double masa, Color color) throws DimensionNoValidaException {
		super(getMatrizPuntos(n,radio),posicion,velocidad,aceleracion,posicionAngular,velocidadAngular,aceleracionAngular,masa,color);
	}
	
	public static Matriz getMatrizPuntos(int numVertices, double radio) throws DimensionNoValidaException {
		
		Matriz matrizPuntos = new Matriz(2,numVertices);
		
		double angulo = 2*Math.PI / numVertices;
		Matriz matrizGiro = Matriz.getMatriz2x2Giro(angulo);
		Vector vertice = new Vector(radio,0); // Iniciamos el primer vertice...
		
		for (int i=0; i<numVertices; i++) {
			try {
				matrizPuntos.setColumna(i, vertice);
			} catch (Exception e) {
				e.printStackTrace();
			}
			vertice = matrizGiro.por(vertice);
		}
		
		return matrizPuntos;
	}
	
	@Override
	public String toString() {
		return (super.toString() + "\n Poligono regular.");
	}
}