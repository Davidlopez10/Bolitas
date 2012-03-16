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
	 * Constructor básico (ignora propiedades de giro de la entidad, y calcula la masa a partir del radio)
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
	 * Constructor básico (ignora propiedades de giro de la entidad)
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
	 * Constructor para el círculo (calcula la masa automáticamente)
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
	 * Constructor para el círculo (calcula la masa automáticamente)
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
	 * Cambia el radio del círculo. Nota: No actualiza la masa.
	 * 
	 * @param nuevoRadio nuevo radio del círculo
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
	public boolean hayColision(Entidad ent) throws EntidadDesconocidaException {
		if (ent instanceof EntidadCirculo) {
			return hayColision((EntidadCirculo) ent);
		}
		else if (ent instanceof EntidadPoligono) {
			return hayColision((EntidadPoligono) ent);
		}
		else {
			throw new EntidadDesconocidaException(ent);
		}
	}
	
	/**
	 * Devuelve si hay colision o no con una {@link EntidadCirculo}
	 * 
	 * @param circulo {@link EntidadCirculo} con el que comprobar si hay colision o no
	 * @return TRUE si colisina, false en otro caso.
	 */
	private boolean hayColision(EntidadCirculo circulo) {	
		
		double distancia = this.getPosicion().distanciaA(circulo.getPosicion());
		
		if (this.getRadio() + circulo.getRadio() > distancia)
			return true;
		else
			return false;	
	}
	
	/**
	 * Devuelve si hay colision o no con una {@link EntidadPoligono}	
	 * 
	 * @param polig {@link EntidadPoligono} con el que colisiona
	 * @return TRUE si colisina, false en otro caso.
	 */
	private boolean hayColision(EntidadPoligono polig) {
		int n = polig.getNumeroVertices();

		// Comprobamos si contiene a los vértices
		for (int i=0; i < n; i++) {
			if (this.contiene(polig.getVerticeAbsoluto(i)))
				return true;
		}	
		
		// Comprobamos si contiene al centro
		if (polig.contiene(this.getPosicion()))
			return true;
		
		// Comprobamos si contiene una parte del segmento:
		try {
			for (int i=0; i<n; i++) {
				
				Vector2D primerVer = polig.getVerticeAbsoluto(i);
				Vector2D segundoVer  = polig.getVerticeAbsoluto((i+1)%n);
				
				Vector2D vectorSegmento = new Vector2D(primerVer, segundoVer);
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
					if (puntoProyectado.distanciaA(primerVer) + puntoProyectado.distanciaA(segundoVer)
						==
						primerVer.distanciaA(segundoVer)
						) 
						{
							return true;
						}
						
				}

			}
		} catch (ArithmeticException e) {
			// Un vértice del polígono coincide con el centro del circulo ?¿?¿
			return true;
		} 			
		return false;
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
	 * Comprueba si un punto está contenido o no en una esfera (o en su perímetro)
	 * 
	 * @param punto Punto a comprobar
	 * @return TRUE: si el punto está contenido
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
	
	/* (non-Javadoc)
	 * @see Entidades.Entidad#tratarColision(Entidades.Entidad)
	 */
	public void tratarColision(Entidad e) throws EntidadDesconocidaException {
		if (e instanceof EntidadCirculo) {
			tratarColision((EntidadCirculo) e);
		}
		else if (e instanceof EntidadPoligono) {
			tratarColision((EntidadPoligono) e);
		}
		else {
			throw new EntidadDesconocidaException(e);
		}
	}

	
	/**
	 * Trata colisiones de la entidad con un círculo
	 * 
	 * @param circulo Círculo con el que tratar la colision
	 */
	private void tratarColision(EntidadCirculo circulo) {
		try {
			Vector2D choque1a2 = this.getPosicion().resta(circulo.getPosicion());
			Vector2D choque2a1 = circulo.getPosicion().resta(this.getPosicion());

			Vector2D velde1a2 = (this.getVelocidad().proyectarSobre(choque1a2));
			Vector2D velde2a1 = (circulo.getVelocidad().proyectarSobre(choque2a1));

			Vector2D Vel1Final = this.getVelocidad().suma(velde2a1).resta(velde1a2);
			Vector2D Vel2Final = circulo.getVelocidad().suma(velde1a2).resta(velde2a1);

			this.setVelocidad(Vel1Final);
			circulo.setVelocidad(Vel2Final);
		} catch (ArithmeticException e) {
			// Podría pasar si un objeto colisiona consigo mismo...
			System.out.println("Posíblemente un objeto colisionó consigo mismo (?)");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// TODO: Implementar convenientemente.
	/**
	 * Trata colisiones de la entidad con polígonos
	 * 
	 * @param polig Polígono con el que tratar la colision.
	 */
	private void tratarColision(EntidadPoligono polig) {
		this.invertirVelocidadAngular();
		this.getVelocidad().invertirX();
		this.getVelocidad().invertirY();
		polig.invertirVelocidadAngular();
		polig.getVelocidad().invertirX();
		polig.getVelocidad().invertirY();
	}
}