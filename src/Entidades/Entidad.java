package Entidades;

import java.awt.Color;
import java.awt.Graphics;

import Exception.DimensionNoValidaException;
import Mates.Vector;

// TODO: Agregar masa....
public abstract class Entidad {

	protected Vector posicion;
	protected Vector velocidad;
	protected Vector aceleracion;

	protected double posicionAngular;
	protected double velocidadAngular;
	protected double aceleracionAngular;

	protected double masa;

	protected Color color;

	public Entidad(Vector posicion, Vector velocidad, Vector aceleracion,
			double masa, Color color) throws DimensionNoValidaException {
		if (posicion.dimension() != 2)
			throw new DimensionNoValidaException(
					"Vector posicion debe ser de dos coordenadas " + posicion);
		if (velocidad.dimension() != 2)
			throw new DimensionNoValidaException(
					"Vector velocidad debe ser de dos coordenadas " + velocidad);
		if (aceleracion.dimension() != 2)
			throw new DimensionNoValidaException(
					"Vector aceleracion debe ser de dos coordenadas "
							+ aceleracion);

		this.posicion = posicion;
		this.velocidad = velocidad;
		this.aceleracion = aceleracion;

		this.masa = masa;

		this.posicionAngular = 0;
		this.velocidadAngular = 0;
		this.aceleracionAngular = 0;

		this.color = color;
	}

	public Entidad(Vector posicion, Vector velocidad, Vector aceleracion,
			double posicionAngular, double velocidadAngular,
			double aceleracionAngular, double masa, Color color)
			throws DimensionNoValidaException {
		if (posicion.dimension() != 2)
			throw new DimensionNoValidaException(
					"Vector posicion debe ser de dos coordenadas " + posicion);
		if (velocidad.dimension() != 2)
			throw new DimensionNoValidaException(
					"Vector velocidad debe ser de dos coordenadas " + velocidad);
		if (aceleracion.dimension() != 2)
			throw new DimensionNoValidaException(
					"Vector aceleracion debe ser de dos coordenadas "
							+ aceleracion);

		this.posicion = posicion;
		this.velocidad = velocidad;
		this.aceleracion = aceleracion;

		this.masa = masa;

		this.posicionAngular = posicionAngular;
		this.velocidadAngular = velocidadAngular;
		this.aceleracionAngular = aceleracionAngular;

		this.color = color;
	}

	public Vector getPosicion() {
		return posicion;
	}

	public void setPosicion(Vector posicion) {
		this.posicion = posicion;
	}

	public Vector getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(Vector velocidad) {
		this.velocidad = velocidad;
	}

	public Vector getAceleracion() {
		return aceleracion;
	}

	public void setAceleracion(Vector aceleracion) {
		this.aceleracion = aceleracion;
	}

	public double getMasa() {
		return this.masa;
	}

	public void setMasa(double nuevaMasa) {
		this.masa = nuevaMasa;
	}

	public double getPosicionAngular() {
		return this.posicionAngular;
	}

	public void setPosicionAngular(double pos) {
		while (pos > 2 * Math.PI)
			pos -= 2 * Math.PI;
		this.posicionAngular = pos;
	}

	public double getVelocidadAngular() {
		return this.velocidadAngular;
	}

	public void setVelocidadAngular(double vel) {
		this.velocidadAngular = vel;
	}

	public void invertirVelocidadAngular() {
		this.velocidadAngular = -this.velocidadAngular;
	}

	public double getAceleracionAngular() {
		return this.aceleracionAngular;
	}

	public void setAceleracionAngular(double aa) {
		this.aceleracionAngular = aa;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color nuevoColor) {
		this.color = nuevoColor;
	}

	public void calcularNuevasPosiciones(double dt) {
		try {
			posicion.setX(posicion.getX() + velocidad.getX() * dt + 1 / 2
					* aceleracion.getX() * dt * dt);
			posicion.setY(posicion.getY() + velocidad.getY() * dt + 1 / 2
					* aceleracion.getY() * dt * dt);
			velocidad.setX(velocidad.getX() + aceleracion.getX() * dt);
			velocidad.setY(velocidad.getY() + aceleracion.getY() * dt);

			this.setPosicionAngular(this.getPosicionAngular()
					+ this.getVelocidadAngular() * dt + (1 / 2)
					* this.getAceleracionAngular() * Math.pow(dt, 2));
			this.setVelocidadAngular(this.getVelocidadAngular()
					+ this.getAceleracionAngular() * dt);
		} catch (DimensionNoValidaException e) {
			// No debería pasar nunca ya que comprobamos que los vectores sean
			// adecuados al contruir la entidad.
			e.printStackTrace();
		}
	}

	public abstract void pintar(Graphics g);

	public abstract boolean hayColisionX(int limite1, int limite2);

	public abstract boolean hayColisionY(int limite1, int limite2);

	public abstract void tratarColisionX();

	public abstract void tratarColisionY();

	public boolean hayColision(Entidad ent) {
		return hayColision(this, ent);
	}

	public Entidad hayColision(Entidad listaEntidades[], int n) {
		for (int i = 0; i < n; i++)
			if (this.hayColision(listaEntidades[i]))
				return listaEntidades[i];

		return null;
	}

	public String toString() {
		return ("Entidad: \nPosicion: " + posicion + " \nVelocidad: "
				+ velocidad + " \nAceleracion " + aceleracion + " \nColor "
				+ color + "\n Masa: " + this.masa);
	}

	public static void trataColision(Entidad ent1, Entidad ent2) {
		if (ent1 instanceof EntidadPelota) {
			EntidadPelota ent1P = (EntidadPelota) ent1;
			if (ent2 instanceof EntidadPelota) {
				EntidadPelota ent2P = (EntidadPelota) ent2;

				try {
					Vector choque1a2 = ent1P.getPosicion().resta(
							ent2P.getPosicion());
					Vector choque2a1 = ent2P.getPosicion().resta(
							ent1P.getPosicion());

					Vector velde1a2 = (ent1P.getVelocidad()
							.proyectarSobre(choque1a2));
					Vector velde2a1 = (ent2P.getVelocidad()
							.proyectarSobre(choque2a1));

					Vector Vel1Final = ent1.getVelocidad().suma(velde2a1)
							.resta(velde1a2);
					Vector Vel2Final = ent2.getVelocidad().suma(velde1a2)
							.resta(velde2a1);

					ent1P.setVelocidad(Vel1Final);
					ent2P.setVelocidad(Vel2Final);
				} catch (DimensionNoValidaException e) {
					// No debería ser lanzada nunca ya que comprobamos al
					// construir las dimensiones
					e.printStackTrace();
				} catch (ArithmeticException e) {
					// Podría pasar si un objeto colisiona consigo mismo...
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if (ent2 instanceof EntidadPoligono) {
				EntidadPoligono ent2Pol = (EntidadPoligono) ent2;
				try {
					ent2Pol.invertirVelocidadAngular();
					ent2Pol.getVelocidad().invertirX();
					ent2Pol.getVelocidad().invertirY();
					ent1P.invertirVelocidadAngular();
					ent1P.getVelocidad().invertirX();
					ent1P.getVelocidad().invertirY();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				trataColision(ent2,ent1);
			}
		} else if (ent1 instanceof EntidadPoligono) {
			EntidadPoligono ent1Pol = (EntidadPoligono) ent1;
			if (ent2 instanceof EntidadPoligono) {
				EntidadPoligono ent2Pol = (EntidadPoligono) ent2;
				try { 
					ent2Pol.invertirVelocidadAngular();
					ent2Pol.getVelocidad().invertirX();
					ent2Pol.getVelocidad().invertirY();
					ent1Pol.invertirVelocidadAngular();
					ent1Pol.getVelocidad().invertirX();
					ent1Pol.getVelocidad().invertirY();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				trataColision(ent2,ent1);
			}
		} else {
			//Do Nothing?
		}
	}

	public static boolean hayColision(Entidad ent1, Entidad ent2) {
		
		if (ent1 instanceof EntidadPelota) {
			EntidadPelota ent1P = (EntidadPelota) ent1;
			if (ent2 instanceof EntidadPelota) {
				EntidadPelota ent2P = (EntidadPelota) ent2;
				double distancia;
				try {
					distancia = ent1P.getPosicion().distanciaA(
							ent2P.getPosicion());
				} catch (DimensionNoValidaException e) {
					// No debería pasar ya que controlamos las dimensiones al
					// construir entidades
					e.printStackTrace();
					return false;
				}
				if (ent1P.getRadio() + ent2P.getRadio() > distancia)
					return true;
				else
					return false;
			} else if (ent2 instanceof EntidadPoligono) {
				// TODO: Implementar
				return false;
			} else {
				return hayColision(ent2, ent1);
			}
		} else if (ent1 instanceof EntidadPoligono) {
			EntidadPoligono ent1Pol = (EntidadPoligono) ent1;
			if (ent2 instanceof EntidadPoligono) {
				EntidadPoligono ent2Pol = (EntidadPoligono) ent2;
				// TODO: Imlementar:
				for (int i=0; i<ent1Pol.getNumeroVertices();i++)
					if (EntidadPoligono.contiene(ent2Pol, ent1Pol.getVerticeAbsoluto(i)))
						return true;
				for (int i=0; i<ent2Pol.getNumeroVertices();i++)
					if (EntidadPoligono.contiene(ent1Pol, ent2Pol.getVerticeAbsoluto(i)))
						return true;
				return false;
			}
			else {
				return hayColision(ent2, ent1);
			}
		} else {
			return false;
		}
	}
}