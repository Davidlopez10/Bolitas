package Entidades;
import java.awt.Color;
import java.awt.Graphics;

import Exception.DimensionNoValidaException;
import Mates.Vector;


public class Pelota extends Entidad {
	
	private double radio;
	
	public Pelota(double radio, Vector posicion, Vector velocidad, Vector aceleracion,Color color) throws DimensionNoValidaException {
		super(posicion,velocidad,aceleracion,color);
		this.radio=radio;
	}
	
	public double getRadio() {
		return this.radio;
	}
	
	public void setRadio(double nuevoRadio) {
		this.radio=nuevoRadio;
	}

	public void pintar(Graphics g) {
		g.setColor(color);
		try {
			g.fillOval((int) Math.round(posicion.getX() - radio),(int) Math.round(posicion.getY() - radio), 2 * (int) Math.round(radio), 2 * (int) Math.round(radio));
		} catch (DimensionNoValidaException e) {
			// No debería pasar nunca ya que comprobamos al contruir la Entidad
			e.printStackTrace();
		}
	}
	
	public boolean hayColisionX(int limite1, int limite2) {
		try {
			if (posicion.getX() - radio < limite1 || posicion.getX() + radio > limite2)
				return true;
			else
				return false;
		} catch (DimensionNoValidaException e) {
			// No debería pasar nunca ya que comprobamos al contruir la Entidad
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean hayColisionY(int limite1, int limite2) {
		try {
			if (posicion.getY() - radio < limite1 || posicion.getY() + radio > limite2)
				return true;
			else
				return false;
		} catch (DimensionNoValidaException e) {
			// No debería pasar nunca ya que comprobamos al contruir la Entidad
			e.printStackTrace();
			return false;
		}
	}
	
	public String toString() {
		return (super.toString() + "\nPelota. Radio: " + this.radio);
	}
}