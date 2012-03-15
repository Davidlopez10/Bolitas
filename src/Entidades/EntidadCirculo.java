package Entidades;

import java.awt.Color;
import java.awt.Graphics;

import Exception.DimensionNoValidaException;
import Exception.EntidadDesconocidaException;
import Mates.Vector2D;

/**
 * Esta clase representa un tipo de entidad: el circulo.
 * 
 * @author Jose Diaz
 *
 */
public class EntidadCirculo extends Entidad {

	private double radio;

	/**
	 * Constructor b�sico (ignora propiedades de giro de la entidad, y calcula la masa a partir del radio)
	 * 
	 * @param radio
	 * @param posicion
	 * @param velocidad
	 * @param aceleracion
	 * @param color
	 * @throws DimensionNoValidaException
	 */
	public EntidadCirculo(double radio, Vector2D posicion, Vector2D velocidad,
			Vector2D aceleracion, Color color) {
		this(radio,posicion,velocidad,aceleracion,0,0,0,0,color);
	}

	/**
	 * Constructor b�sico (ignora propiedades de giro de la entidad)
	 * 
	 * @param radio
	 * @param posicion
	 * @param velocidad
	 * @param aceleracion
	 * @param masa
	 * @param color
	 * @throws DimensionNoValidaException
	 */
	public EntidadCirculo(double radio, Vector2D posicion, Vector2D velocidad,
			Vector2D aceleracion, double masa, Color color) {
		this(radio,posicion,velocidad,aceleracion,0,0,0,0,color);
	}

	/**
	 * Constructor para el c�rculo (calcula la masa autom�ticamente)
	 * 
	 * @param radio
	 * @param posicion
	 * @param velocidad
	 * @param aceleracion
	 * @param posicionAngular
	 * @param velocidadAngular
	 * @param aceleracionAngular
	 * @param color
	 * @throws DimensionNoValidaException
	 */
	public EntidadCirculo(double radio, Vector2D posicion, Vector2D velocidad,
			Vector2D aceleracion, double posicionAngular,
			double velocidadAngular, double aceleracionAngular, Color color) {
		this(radio,posicion,velocidad,aceleracion,posicionAngular,velocidadAngular,aceleracionAngular,0,color);
	}
	
	/**
	 * Constructor para el c�rculo (calcula la masa autom�ticamente)
	 * 
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
	public EntidadCirculo(double radio, Vector2D posicion, Vector2D velocidad,
			Vector2D aceleracion, double posicionAngular,
			double velocidadAngular, double aceleracionAngular, double masa,
			Color color) {
		super(posicion, velocidad, aceleracion, posicionAngular,
				velocidadAngular, aceleracionAngular, masa, color);
		this.radio = radio;
	}

	/**
	 * Devuelve el radio del circulo
	 * 
	 * @return radio del circulo
	 */
	public double getRadio() {
		return this.radio;
	}

	/**
	 * Cambia el radio del c�rculo. Nota: No actualiza la masa.
	 * 
	 * @param nuevoRadio nuevo radio del c�rculo
	 */
	public void setRadio(double nuevoRadio) {
		this.radio = nuevoRadio;
	}

	/* (non-Javadoc)
	 * @see Entidades.Entidad#pintar(java.awt.Graphics)
	 */
	public void pintar(Graphics g) {
		g.setColor(colorEntidad);
		g.fillOval( (int) Math.round(posicion.getX() - radio),
				    (int) Math.round(posicion.getY() - radio),
				    2 * (int) Math.round(radio), 
				    2*(int) Math.round(radio)
				  );
		super.pintar(g);
	}
	
	/* (non-Javadoc)
	 * @see Entidades.Entidad#hayColision(Entidades.Entidad)
	 */
	@Override
	public boolean hayColision(Entidad entidad) throws EntidadDesconocidaException {
		if (entidad instanceof EntidadCirculo) {
			EntidadCirculo entPelota = (EntidadCirculo) entidad;
			
			double distancia = this.getPosicion().distanciaA(entPelota.getPosicion());
			
			if (this.getRadio() + entPelota.getRadio() > distancia)
				return true;
			else
				return false;
		} 
		
		else if (entidad instanceof EntidadPoligono) {
			EntidadPoligono entPolig = (EntidadPoligono) entidad;
			int n = entPolig.getNumeroVertices();

			// Comprobamos si contiene a los v�rtices
			for (int i=0; i < n; i++) {
				if (this.contiene(entPolig.getVerticeAbsoluto(i)))
					return true;
			}	
			
			// Comprobamos si contiene al centro
			if (entPolig.contiene(this.getPosicion()))
				return true;
			
			// Comprobamos si contiene una parte del segmento:
			try {
				for (int i=0; i<n; i++) {
					
					Vector2D primerVer = entPolig.getVerticeAbsoluto(i);
					Vector2D segunVer  = entPolig.getVerticeAbsoluto((i+1)%n);
					
					Vector2D vectorSegmento = new Vector2D(primerVer, segunVer);
					Vector2D verticeCentro = new Vector2D(primerVer,this.getPosicion());
					
					Vector2D normal = verticeCentro.resta(verticeCentro.proyectarSobre(vectorSegmento));
					if (normal.norma() < this.getRadio()) {
						// La normal a la recta que determina el segmento es menor que el radio del circulo
						// Pero aun nos falta comprobar que la proyeccion del circulo en la recta cae
						// dentro del segmento
						
						normal.invertirX();
						normal.invertirY();
						Vector2D puntoProyectado = this.getPosicion().suma(normal);
						
						// TODO: Optimizar
						if (puntoProyectado.distanciaA(primerVer) + puntoProyectado.distanciaA(segunVer)
							==
							primerVer.distanciaA(segunVer)
							) 
							{
								return true;
							}
							
					}

				}
			} catch (ArithmeticException e) {
				// Un v�rtice del pol�gono coincide con el centro del circulo ?�?�
				return true;
			} 			
			return false;
		} else {
			throw new EntidadDesconocidaException("Imposible procesar colision con entidad desconocida" , entidad);
		}
	}
	
	/* (non-Javadoc)
	 * @see Entidades.Entidad#hayColisionX(int, int)
	 */
	public boolean hayColisionX(int Xinf, int Xsup) {
		if (posicion.getX() - radio < Xinf
				|| posicion.getX() + radio > Xsup)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see Entidades.Entidad#hayColisionY(int, int)
	 */
	public boolean hayColisionY(int Yinf, int Ysup) {
		if (posicion.getY() - radio < Yinf
				|| posicion.getY() + radio > Ysup)
			return true;
		return false;
	}
	
	/**
	 * Comprueba si un punto est� contenido o no en una esfera (o en su per�metro)
	 * 
	 * @param punto Punto a comprobar
	 * @return TRUE: si el punto est� contenido
	 */
	public boolean contiene(Vector2D punto) {
		if (getPosicion().distanciaA(punto) <= getRadio())
			return true;
		return false;
	}
	
	/* (non-Javadoc)
	 * @see Entidades.Entidad#toString()
	 */
	@Override
	public String toString() {
		return (super.toString() + "\nPelota. Radio: " + this.radio);
	}

	/**
	 * Devuelve la masa por defecto de uan esfera, dado su radio
	 * 
	 * @param radio Radio de la esfera
	 * @return Masa de la esfera con radio dado
	 */
	public static double getMasa(double radio) {
		return (4 / 3) * Math.PI * Math.pow(radio, 3);
	}

	/* (non-Javadoc)
	 * @see Entidades.Entidad#tratarColisionEscenarioX()
	 */
	@Override
	public void tratarColisionEscenarioX() {
		this.getVelocidad().invertirX();
	}

	/* (non-Javadoc)
	 * @see Entidades.Entidad#tratarColisionEscenarioY()
	 */
	@Override
	public void tratarColisionEscenarioY() {
		this.getVelocidad().invertirY();
	}
}