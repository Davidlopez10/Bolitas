package Entidades;

import java.awt.Color;
import java.awt.Graphics;

import util.HerramientasGraficas;

import Exception.DimensionNoValidaException;
import Mates.Vector2D;

// TODO: Modificar colisiones para que usen masa
// TODO: A�adir nombre �nico a cada clase.

/**
 * Esta clase abstracta define entidades para la clase Escenario.
 * Una entidad es todo objeto que es representado en un escenario definida por (al menos):
 * Posicion, velocidad, aceleracion
 * Posicion velocidad y aceleracion angulares
 * Masa y color.
 * 
 * @author Jose Diaz
 *
 */
public abstract class Entidad {

	static final int FACTOR_REDUCCION_VECTORES = 5;
	
	protected Vector2D posicion;
	protected Vector2D velocidad;
	protected Vector2D aceleracion;

	protected double posicionAngular;
	protected double velocidadAngular;
	protected double aceleracionAngular;
	
	protected boolean mostrarVelocidad = true;
	protected boolean mostrarAceleracion = true;

	protected double masa;

	protected Color color;

	/**
	 *  Constructor b�sico (se omiten caracter�sticas de giro para la entidad)
	 * 
	 * @param posicion 
	 * @param velocidad 
	 * @param aceleracion 
	 * @param masa 
	 * @param color
	 * @throws DimensionNoValidaException Si alguno de los vectores posicion, velocidad o aceleracion no tienen 2 componentes
	 */
	public Entidad(Vector2D posicion, Vector2D velocidad, Vector2D aceleracion,
			double masa, Color color) throws DimensionNoValidaException {
		if (posicion.getDimension() != 2)
			throw new DimensionNoValidaException(
					"Vector2D posicion debe ser de dos coordenadas " + posicion);
		if (velocidad.getDimension() != 2)
			throw new DimensionNoValidaException(
					"Vector2D velocidad debe ser de dos coordenadas " + velocidad);
		if (aceleracion.getDimension() != 2)
			throw new DimensionNoValidaException(
					"Vector2D aceleracion debe ser de dos coordenadas "
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

	/**
	 * Constructor de entidades
	 * 
	 * @param posicion
	 * @param velocidad
	 * @param aceleracion
	 * @param posicionAngular
	 * @param velocidadAngular
	 * @param aceleracionAngular
	 * @parWam masa
	 * @param color
	 * @throws DimensionNoValidaException Si alguno de los vectores posicion, velocidad o aceleracion no tienen 2 componentes
	 */
	public Entidad(Vector2D posicion, Vector2D velocidad, Vector2D aceleracion,
			double posicionAngular, double velocidadAngular,
			double aceleracionAngular, double masa, Color color)
			throws DimensionNoValidaException {
		if (posicion.getDimension() != 2)
			throw new DimensionNoValidaException(
					"Vector2D posicion debe ser de dos coordenadas " + posicion);
		if (velocidad.getDimension() != 2)
			throw new DimensionNoValidaException(
					"Vector2D velocidad debe ser de dos coordenadas " + velocidad);
		if (aceleracion.getDimension() != 2)
			throw new DimensionNoValidaException(
					"Vector2D aceleracion debe ser de dos coordenadas "
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

	/**
	 * Devuelve la posicion de la entidad
	 * 
	 * @return posicion de la entidad
	 */
	public Vector2D getPosicion() {
		return posicion;
	}

	/**
	 * Cambia la posicion de la entidad
	 * 
	 * @param posicion Nueva posicion.
	 */
	public void setPosicion(Vector2D posicion) {
		this.posicion = posicion;
	}

	/**
	 * Devuelve el vector  velocidad de la entidad
	 * 
	 * @return Vector2D velocidad de la entidad
	 */
	public Vector2D getVelocidad() {
		return velocidad;
	}

	/**
	 * Cambia el vector velocidad de la entidad
	 * 
	 * @param velocidad Nuevo vector entidad
	 */
	public void setVelocidad(Vector2D velocidad) {
		this.velocidad = velocidad;
	}

	/**
	 * Devuelve el vector aceleracion de la entidad
	 * 
	 * @return Vector2D aceleracion de la entidad
	 */
	public Vector2D getAceleracion() {
		return aceleracion;
	}

	/**
	 * Cambia el vector aceleracion de la entidad
	 * 
	 * @param aceleracion Nuevo vector aceleracion de la entidad
	 */
	public void setAceleracion(Vector2D aceleracion) {
		this.aceleracion = aceleracion;
	}

	/**
	 * Devuelve la masa de la entidad
	 * 
	 * @return masa de la entidad
	 */
	public double getMasa() {
		return this.masa;
	}

	/**
	 * Cambia la masa de la entidad
	 * 
	 * @param nuevaMasa nueva masa para la entidad
	 */
	public void setMasa(double nuevaMasa) {
		this.masa = nuevaMasa;
	}

	/**
	 * Devuelve la orientacion del objeto
	 * 
	 * @return Devuelve el angulo en el que el objeto est� orientado en ese momento
	 */
	public double getPosicionAngular() {
		return this.posicionAngular;
	}

	/**
	 * Establece una nueva orientaci�n para el objeto.
	 * 
	 * @param posicionAngular Nueva posicion angular
	 */
	public void setPosicionAngular(double posicionAngular) {
		while (posicionAngular > 2 * Math.PI)
			posicionAngular -= 2 * Math.PI;
		this.posicionAngular = posicionAngular;
	}

	/**
	 * Devuelve la velocidad angular
	 * 
	 * @return velocidad angular de la entidad
	 */
	public double getVelocidadAngular() {
		return this.velocidadAngular;
	}

	/**
	 * Nueva velocidad angular
	 * 
	 * @param nuevaVelocidadAngular Nueva velocidad angular
	 */
	public void setVelocidadAngular(double nuevaVelocidadAngular) {
		this.velocidadAngular = nuevaVelocidadAngular;
	}

	/**
	 * Invierte la velocidad angular de la entidad 
	 */
	public void invertirVelocidadAngular() {
		this.velocidadAngular = -this.velocidadAngular;
	}

	/**
	 * Devuelve la aceleracion angular de la entidad
	 * 
	 * @return aceleracion angular
	 */
	public double getAceleracionAngular() {
		return this.aceleracionAngular;
	}

	/**
	 * Establece una nueva velocidad angular para la entidad
	 * 
	 * @param nuevaVelocidadAngular nueva velocidad angular
	 */
	public void setAceleracionAngular(double nuevaVelocidadAngular) {
		this.aceleracionAngular = nuevaVelocidadAngular;
	}

	/**
	 * Devuelve el color de la entidad
	 * 
	 * @return color de la entidad
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Establece un nuevo color para la entidad
	 * 
	 * @param nuevoColor nuevo color.
	 */
	public void setColor(Color nuevoColor) {
		this.color = nuevoColor;
	}
	
	/**
	 * Devuelve true si actualmente se esta mostrando el vector velocidad de esta entidad por pantalla. False en otro caso.
	 * 
	 * @return true si actualmente se esta mostrando el vector velocidad de esta entidad por pantalla. False en otro caso.
	 */
	public boolean getMostrandoVelocidad() {
		return mostrarVelocidad;
	}
	
	/**
	 * Devuelve true si actualmente se esta mostrando el vector aceleracion de esta entidad por pantalla. False en otro caso.
	 * 
	 * @return true si actualmente se esta mostrando el vector aceleracion de esta entidad por pantalla. False en otro caso.
	 */
	public boolean getMostrandoAceleracion() {
		return mostrarAceleracion;
	}
	
	/**
	 * Establece si mostrar o no el vector velocidad por pantalla
	 * 
	 * @param b True para mostrar el vector velocidad por pantalla, false para ocultarlo.
	 */
	public void setMostrarVelocidad(boolean b) {
		mostrarVelocidad = b;
	}
	
	/**
	 * Establece si mostrar o no el vector aceleracion por pantalla
	 * 
	 * @param b True para mostrar el vector aceleracion por pantalla, false para ocultarlo.
	 */
	public void setMostrarAceleracion(boolean b) {
		mostrarAceleracion = b;
	}
	

	/**
	 * Dado un lapso de tiempo, actualiza las posiciones y las velocidades de la entidad
	 * 
	 * @param dt Laps de tiempo para los c�lculos
	 */
	public void calcularNuevasPosiciones(double dt) {
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
	}

	/**
	 * Pinta la entidad en un gr�fico
	 * 
	 * @param g gr�fico dondep intar la funcion
	 */
	public void pintar(Graphics g) {
		if (this.mostrarVelocidad) {
			g.setColor(Color.BLUE);
			HerramientasGraficas.dibujarFlecha(posicion, posicion.suma(velocidad), g);
		}
		
		if (this.mostrarAceleracion) {
			g.setColor(Color.RED);
			HerramientasGraficas.dibujarFlecha(posicion, posicion.suma(aceleracion), g);
		}
	}


	/**
	 * Detecta si la entidad colisiona o no con los bordes X del escenario
	 * 
	 * @param Xinf X minima del escenario
	 * @param Xsup X m�xima del escenario
	 * @return TRUE si la entidad colisiona
	 */
	public abstract boolean hayColisionX(int Xinf, int Xsup);
	
	/**
	 * Detecta si la entidad colisiona o no con los bordes Y del escenario
	 * 
	 * @param Xinf Y minima del escenario
	 * @param Xsup Y m�xima del escenario
	 * @return TRUE si la entidad colisiona
	 */
	public abstract boolean hayColisionY(int Yinf, int Ysup);

	/**
	 * Procesa si hay una colision con otra entidad o no.
	 * 
	 * @param ent Entidad con la que comprobar si hay o no colision
	 * @return TRUE si hay colisi�n con la entidad.
	 */
	public abstract boolean hayColision(Entidad ent);

	/**
	 * Procesa si hay una colision con alguna entidad en una lista de entidades o no
	 * 
	 * @param listaEntidades Lista de entidades con las que comprobar si hay o no colsi�n
	 * @param n Numero de entidades ocupadas en el vector
	 * @return Primera entidad de la lista con la que colisiona, o null si no colisiona con ninguna.
	 */
	public Entidad hayColision(Entidad listaEntidades[], int n) {
		for (int i = 0; i < n; i++)
			if (this.hayColision(listaEntidades[i]))
				return listaEntidades[i];

		return null;
	}
	
	/**
	 * Procesa la colision de la entidad con el borde X del escenario
	 */
	public void tratarColisionEscenarioX() {
		this.getVelocidad().invertirX();
		this.invertirVelocidadAngular();
	}
	
	/**
	 * Procesa la colision de la entidad con el borde Y del escenario
	 */
	public void tratarColisionEscenarioY() {
		this.getVelocidad().invertirY();
		this.invertirVelocidadAngular();
	}

	public static void trataColision(Entidad ent1, Entidad ent2) {
		if (ent1 instanceof EntidadCirculo) {
			EntidadCirculo ent1P = (EntidadCirculo) ent1;
			if (ent2 instanceof EntidadCirculo) {
				EntidadCirculo ent2P = (EntidadCirculo) ent2;

				try {
					Vector2D choque1a2 = ent1P.getPosicion().resta(
							ent2P.getPosicion());
					Vector2D choque2a1 = ent2P.getPosicion().resta(
							ent1P.getPosicion());

					Vector2D velde1a2 = (ent1P.getVelocidad()
							.proyectarSobre(choque1a2));
					Vector2D velde2a1 = (ent2P.getVelocidad()
							.proyectarSobre(choque2a1));

					Vector2D Vel1Final = ent1.getVelocidad().suma(velde2a1)
							.resta(velde1a2);
					Vector2D Vel2Final = ent2.getVelocidad().suma(velde1a2)
							.resta(velde2a1);

					ent1P.setVelocidad(Vel1Final);
					ent2P.setVelocidad(Vel2Final);
				} catch (ArithmeticException e) {
					// Podr�a pasar si un objeto colisiona consigo mismo...
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ("Entidad: \nPosicion: " + posicion + " \nVelocidad: "
				+ velocidad + " \nAceleracion " + aceleracion + " \nColor "
				+ color + "\n Masa: " + this.masa);
	}
}


/*

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
			// No deber�a pasar ya que controlamos las dimensiones al
			// construir entidades
			e.printStackTrace();
			return false;
		}
		if (ent1P.getRadio() + ent2P.getRadio() > distancia)
			return true;
		else
			return false;
	} else if (ent2 instanceof EntidadPoligono) {
		EntidadPoligono ent2Pol = (EntidadPoligono) ent2;
		int n = ent2Pol.getNumeroVertices();
		// TODO: Comprobar
		// Comprobamos si contiene a los v�rtices
		for (int i=0; i < n; i++) {
			if (ent1P.contiene(ent2Pol.getVerticeAbsoluto(i)))
				return true;
		}			
		// Comprobamos si contiene una parte del segmento:
		try {
			for (int i=0; i<n; i++) {
				
				Vector2D primerVer = ent2Pol.getVerticeAbsoluto(i);
				Vector2D segunVer  = ent2Pol.getVerticeAbsoluto((i+1)%n);
				
				Vector2D vectorSegmento = new Vector2D(primerVer, segunVer);
				Vector2D verticeCentro = new Vector2D(primerVer,ent1P.getPosicion());
				
				Vector2D normal = verticeCentro.resta(verticeCentro.proyectarSobre(vectorSegmento));
				if (normal.norma() < ent1P.getRadio()) {
					// La normal a la recta que determina el segmento es menor que el radio del circulo
					// Pero aun nos falta comprobar que la proyeccion del circulo en la recta cae
					// dentro del segmento
					
					normal.invertirX();
					normal.invertirY();
					Vector2D puntoProyectado = ent1P.getPosicion().suma(normal);
					
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
		} catch (DimensionNoValidaException e) {
			// Imposible �? (se comprueba en los constructores)
			e.printStackTrace();
		} catch (ArithmeticException e) {
			// Un v�rtice del pol�gono coincide con el centro del circulo ?�?�
			return true;
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return false;
	} else {
		return hayColision(ent2, ent1);
	}
} else if (ent1 instanceof EntidadPoligono) {
	EntidadPoligono ent1Pol = (EntidadPoligono) ent1;
	if (ent2 instanceof EntidadPoligono) {
		EntidadPoligono ent2Pol = (EntidadPoligono) ent2;
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
*/