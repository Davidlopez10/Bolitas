package Entidades;

import java.awt.Color;
import java.awt.Graphics;

import Exception.DimensionNoValidaException;
import Mates.Matriz;
import Mates.Vector;

// TODO: Giros en torno al centro...
// TODO: Implementar colisiones...


/* Vertices guadados como
 * 1       2
 * 
 * 
 * 3       4
 */
public class Rectangulo extends Entidad {
	
	private double altura;
	private double anchura;
	
	private Matriz puntos;
	
	private double angulo;
	public double velocidadAngular;
	private double aceleracionAngular;
	
	public Rectangulo(double altura, double anchura, Vector posicion, Vector velocidad, Vector aceleracion, Color color) throws DimensionNoValidaException {
		super(posicion,velocidad,aceleracion,color);
		this.setAltura(altura);
		this.setAnchura(anchura);
		
		puntos = new Matriz(2,4,0,0,0,0,0,0,0,0);
		
		angulo=Math.PI/3;
		velocidadAngular = -0.01;
		aceleracionAngular = -0.000001;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getAnchura() {
		return anchura;
	}

	public void setAnchura(double anchura) {
		this.anchura = anchura;
	}
	
	public String toString() {
		return (super.toString() + "\nRectangulo, dimensiones: Altura:" + altura + " Anchura: " + anchura);
	}
	
	public void refrescarVertices() {
		try {	
			puntos = new Matriz(2,4,
					-anchura/2,
					+anchura/2,
					+anchura/2,
					-anchura/2,
					+altura/2,
					+altura/2,
					-altura/2,
					-altura/2
			);
			Matriz matrizGiro = new Matriz(2,2,Math.cos(angulo),Math.sin(angulo),-Math.sin(angulo),Math.cos(angulo));
			
			puntos = matrizGiro.por(puntos);
			Matriz posicionMatriz = new Matriz(2,4,
										posicion.getX(),posicion.getX(),posicion.getX(),posicion.getX(),
										posicion.getY(),posicion.getY(),posicion.getY(),posicion.getY()
			);			
			puntos = puntos.suma(posicionMatriz);
			
		} catch (DimensionNoValidaException e) {
			// No debería pasar nunca ya que comprobamos al contruir
			e.printStackTrace();
		}
	}
	
	public boolean hayColisionX(int limite1, int limite2) {
		Vector coordenadasX = puntos.getFila(0);
		for (int i=0;i<coordenadasX.dimension();i++)
			if (coordenadasX.get(i) < limite1 ||
				coordenadasX.get(i) > limite2)
				return true;
		return false;
		/*
		try {
			if (this.posicion.getX()-anchura/2 < limite1 ||
				this.posicion.getX() +anchura/2 > limite2) {	
					return true;
			}
			else {
				return false;
			}
		} catch (DimensionNoValidaException e) {
			// No debería pasar nunca ya que comprobamos al construir la Entidad
			e.printStackTrace();
			return false;
		}*/
	}
	
	public boolean hayColisionY(int limite1, int limite2) {
		Vector coordenadasY= puntos.getFila(1);
		for (int i=0;i<coordenadasY.dimension();i++)
			if (coordenadasY.get(i) < limite1 ||
				coordenadasY.get(i) > limite2)
				return true;
		return false;
		/*
		try {
		if (this.posicion.getY()-altura/2 < limite1 ||
			this.posicion.getY()+altura/2 > limite2) {	
				return true;
			}
			else {
				return false;
			}	
		} catch (DimensionNoValidaException e) {
			// No debería pasar nunca ya que comprobamos al construir la entidad
			e.printStackTrace();
			return false;
		}*/
	}
	
	public void pintar(Graphics g) {
		g.setColor(color);
		refrescarVertices();
		g.fillPolygon(puntos.getFila(0).getDatosInt(), puntos.getFila(1).getDatosInt(),4);
	}

	public double getAngulo() {
		return angulo;
	}

	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}

	public double getVelocidadAngular() {
		return velocidadAngular;
	}

	public void setVelocidadAngular(double velocidadAngular) {
		this.velocidadAngular = velocidadAngular;
	}

	public double getAceleracionAngular() {
		return aceleracionAngular;
	}

	public void setAceleracionAngular(double aceleracionAngular) {
		this.aceleracionAngular = aceleracionAngular;
	}
	
	public void calcularNuevasPosiciones(double dt) {
		super.calcularNuevasPosiciones(dt);
		angulo += velocidadAngular*dt + (1/2) * Math.pow(aceleracionAngular,2);
		velocidadAngular += aceleracionAngular*dt;
	}
}