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
	
	protected Color color;
	
	public Entidad(Vector posicion,Vector velocidad, Vector aceleracion,Color color) throws DimensionNoValidaException {
		if (posicion.dimension() != 2)
			throw new DimensionNoValidaException("Vector posicion debe ser de dos coordenadas " + posicion);
		if (velocidad.dimension() != 2)
			throw new DimensionNoValidaException("Vector velocidad debe ser de dos coordenadas " + velocidad);
		if (aceleracion.dimension() != 2)
			throw new DimensionNoValidaException("Vector aceleracion debe ser de dos coordenadas " + aceleracion);
		
		this.posicion = posicion;
		this.velocidad = velocidad;
		this.aceleracion = aceleracion;
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
	
	public void calcularNuevasPosiciones(double dt) {
		try {
			posicion.setX(posicion.getX() + velocidad.getX() *dt + 1/2 * aceleracion.getX() * dt * dt);
			posicion.setY(posicion.getY() + velocidad.getY() *dt + 1/2 * aceleracion.getY() * dt * dt);
			velocidad.setX(velocidad.getX() + aceleracion.getX() * dt);
			velocidad.setY(velocidad.getY() + aceleracion.getY() * dt);
		} catch(DimensionNoValidaException e) {
			// No debería pasar nunca ya que comprobamos que los vectores sean adecuados al contruir la entidad.
			e.printStackTrace();
		}
	}
	
	public abstract void pintar(Graphics g);
	
	public abstract boolean hayColisionX(int limite1, int limite2);
	
	public abstract boolean hayColisionY(int limite1, int limite2);
	
	public boolean hayColision(Entidad ent) {
		return hayColision(this,ent);
	}
	
	public Entidad hayColision(Entidad listaEntidades[],int n) {
		for (int i=0;i<n;i++)
			if (this.hayColision(listaEntidades[i]))
				return listaEntidades[i];
		
		return null;
	}
	
	public String toString() {
		return ("Entidad: \nPosicion: "+posicion+" \nVelocidad: "+velocidad+" \nAceleracion "+aceleracion+" \nColor "+color);
	}
	
	public static void trataColision(Entidad ent1, Entidad ent2) {
		if (ent1 instanceof Pelota) {
			Pelota ent1P = (Pelota) ent1;
			if (ent2 instanceof Pelota) {
				Pelota ent2P = (Pelota) ent2;				
				
				try {
					Vector choque1a2 = ent1P.getPosicion().resta(ent2P.getPosicion());
					Vector choque2a1 = ent2P.getPosicion().resta(ent1P.getPosicion());
	
					Vector velde1a2 = ent1P.getVelocidad().proyectarSobre(choque1a2);
					Vector velde2a1 = ent2P.getVelocidad().proyectarSobre(choque2a1); 
					
					Vector Vel1Final = ent1.getVelocidad().suma(velde2a1).resta(velde1a2);
					Vector Vel2Final = ent2.getVelocidad().suma(velde1a2).resta(velde2a1);
					
					ent1P.setVelocidad(Vel1Final);
					ent2P.setVelocidad(Vel2Final);	
				} catch (DimensionNoValidaException e) {
					// No debería ser lanzada nunca ya que comprobamos al contruir las dimensiones
					e.printStackTrace();
				}
			}
		}
		else {
			trataColision(ent2,ent1);
		}
	}
	
	public static boolean hayColision(Entidad ent1, Entidad ent2) {
		if (ent1 instanceof Pelota) {
			Pelota ent1P = (Pelota) ent1;
			if (ent2 instanceof Pelota) {
				Pelota ent2P = (Pelota) ent2;
				if ( ent1P.getRadio() + ent2P.getRadio() > ent1P.getPosicion().distanciaA(ent2P.getPosicion())) 
					return true;
				else 
					return false;
			}
			else if (ent2 instanceof Rectangulo) {
				return false;
			}
			else {
				return false;
			}
		}
		else if (ent1 instanceof Rectangulo) {
			return false;
		}
		else {
			return hayColision(ent2,ent1);
		}
	}
}