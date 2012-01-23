package Entidades;

import java.awt.Color;
import java.awt.Graphics;

import Exception.DimensionNoValidaException;
import Mates.Vector;

public class EntidadPelota extends Entidad {

	private double radio;

	public EntidadPelota(double radio, Vector posicion, Vector velocidad,
			Vector aceleracion, Color color) throws DimensionNoValidaException {
		super(posicion, velocidad, aceleracion, EntidadPelota.getMasa(radio), color);
		this.radio = radio;
	}

	public EntidadPelota(double radio, Vector posicion, Vector velocidad,
			Vector aceleracion, double masa, Color color)
			throws DimensionNoValidaException {
		super(posicion, velocidad, aceleracion, masa, color);
		this.radio = radio;
	}

	public EntidadPelota(double radio, Vector posicion, Vector velocidad,
			Vector aceleracion, double posicionAngular,
			double velocidadAngular, double aceleracionAngular, Color color)
			throws DimensionNoValidaException {
		super(posicion, velocidad, aceleracion, posicionAngular,
				velocidadAngular, aceleracionAngular, EntidadPelota.getMasa(radio),
				color);
		this.radio = radio;
	}

	public EntidadPelota(double radio, Vector posicion, Vector velocidad,
			Vector aceleracion, double posicionAngular,
			double velocidadAngular, double aceleracionAngular, double masa,
			Color color) throws DimensionNoValidaException {
		super(posicion, velocidad, aceleracion, posicionAngular,
				velocidadAngular, aceleracionAngular, masa, color);
		this.radio = radio;
	}

	public double getRadio() {
		return this.radio;
	}

	public void setRadio(double nuevoRadio) {
		this.radio = nuevoRadio;
	}

	public void pintar(Graphics g) {
		g.setColor(color);
		try {
			g.fillOval((int) Math.round(posicion.getX() - radio),
					(int) Math.round(posicion.getY() - radio),
					2 * (int) Math.round(radio), 2 * (int) Math.round(radio));
		} catch (DimensionNoValidaException e) {
			// No debería pasar nunca ya que comprobamos al contruir la Entidad
			e.printStackTrace();
		}
	}

	public boolean hayColisionX(int limite1, int limite2) {
		try {
			if (posicion.getX() - radio < limite1
					|| posicion.getX() + radio > limite2)
				return true;
			else
				return false;
		} catch (DimensionNoValidaException e) {
			// No debería pasar nunca ya que comprobamos al contruir la Entidad
			e.printStackTrace();
			return true;
		}
	}

	public boolean hayColisionY(int limite1, int limite2) {
		try {
			if (posicion.getY() - radio < limite1
					|| posicion.getY() + radio > limite2)
				return true;
			else
				return false;
		} catch (DimensionNoValidaException e) {
			// No debería pasar nunca ya que comprobamos al contruir la Entidad
			e.printStackTrace();
			return true;
		}
	}

	public void tratarColisionX() {
		try {
			this.getVelocidad().invertirX();
		} catch (DimensionNoValidaException e) {
			// No debería pasar nunca ya que controlamos la dimension de los
			// vectores en el constructor
			e.printStackTrace();
		}
	}

	public void tratarColisionY() {
		try {
			this.getVelocidad().invertirY();
		} catch (DimensionNoValidaException e) {
			// No debería pasar nunca ya que controlamos la dimension de los
			// vectores en el constructor
			e.printStackTrace();
		}
	}

	public String toString() {
		return (super.toString() + "\nPelota. Radio: " + this.radio);
	}

	public static double getMasa(double radio) {
		return (4 / 3) * Math.PI * Math.pow(radio, 3);
	}
}