package entidades;

import java.awt.Color;
import java.awt.Graphics;

import escenario.Escenario;
import exception.EntidadDesconocidaException;

import mates.Vector2D;

import util.HerramientasGraficas;



// TODO: Modificar colisiones para que usen masa
// TODO: Añadir nombre único a cada clase.

/**
 * Esta clase abstracta define entidades para la clase Escenario.
 * 
 * 
 * Una entidad es todo objeto que es representado en un escenario definida por (al menos):
 * 
 * -Posicion, velocidad, aceleracion
 * 
 * -Posicion velocidad y aceleracion angulares
 * 
 * -Masa y color.
 * 
 * @author Jose Diaz
 */

public abstract class Entidad {

	static final int FACTOR_REDUCCION_VECTORES = 5;
	static final Color COLOR_VECTOR_VELOCIDAD = Color.BLUE;
	static final Color COLOR_VECTOR_ACELERACION = Color.RED;
	static final Color COLOR_VELOCIDAD_ANGULAR = Color.BLUE;
	static final Color COLOR_ACELERACION_ANGULAR = Color.RED;
	
	protected Escenario escenarioContenedor;
	
	protected Vector2D posicion;
	protected Vector2D velocidad;
	protected Vector2D aceleracion;

	protected double posicionAngular;
	protected double velocidadAngular;
	protected double aceleracionAngular;
	
	protected boolean mostrarVelocidad = true;
	protected boolean mostrarAceleracion = true;
	protected boolean mostrarVelocidadAngular = true;
	protected boolean mostrarAceleracionAngular = true;

	protected double masa;

	protected Color colorEntidad;

	/**
	 *  Constructor básico (se omiten características de giro para la entidad)
	 * 
	 * 
	 * @param posicion Posicion de la entidad, en píxeles
	 * @param velocidad Velocidad de la entidad, en píxeles / seg
	 * @param aceleracion Aceleración de la entidad, en píxeles / seg^2
	 * @param masa Masa de la entidad
	 * @param color Color de la entidad
	 */
	public Entidad(Vector2D posicion, Vector2D velocidad, Vector2D aceleracion,
			double masa, Color color) {
		this(posicion,velocidad,aceleracion,0,0,0,masa,color);
	}

	/**
	 * Constructor de entidades
	 * 
	 * @param posicion Posicion de la entidad, en píxeles
	 * @param velocidad Velocidad de la entidad, en píxeles / seg
	 * @param aceleracion Aceleración de la entidad, en píxeles / seg^2
	 * @param posicionAngular En radianes
	 * @param velocidadAngular En radianes / seg
	 * @param aceleracionAngular En radianes / seg ^ 2 
	 * @param masa Masa de la entidad
	 * @param color Color de la entidad
	 * */
	public Entidad(Vector2D posicion, Vector2D velocidad, Vector2D aceleracion,
			double posicionAngular, double velocidadAngular,
			double aceleracionAngular, double masa, Color color)  {

		this.posicion = posicion;
		this.velocidad = velocidad;
		this.aceleracion = aceleracion;

		this.masa = masa;

		this.posicionAngular = posicionAngular;
		this.velocidadAngular = velocidadAngular;
		this.aceleracionAngular = aceleracionAngular;

		this.colorEntidad = color;
	}

	/**
	 * Devuelve el escenario en el que está contenida esta entidad. Null si no está en ningun escenario.
	 * 
	 * @return {link @Escenario} en el que está la entidad, o null si no está en ninguno.
	 */
	public Escenario getEscenarioContenedor() {
		return escenarioContenedor;
	}
	
	/**
	 * Establece un nuevo escenario donde restá contenida la entidad.
	 * 
	 * @param esc {link @Escenario} donde está contenida la 
	 */
	public void setEscenarioContenedor(Escenario esc) {
		escenarioContenedor = esc;
	}
	
	/**
	 * Devuelve la posicion de la entidad en píxeles
	 * 
	 * @return Posicion de la entidad
	 */
	public Vector2D getPosicion() {
		return posicion;
	}

	/**
	 * Cambia la posicion de la entidad en píxeles
	 * 
	 * @param posicion Nueva posicion.
	 */
	public void setPosicion(Vector2D posicion) {
		this.posicion = posicion;
	}

	/**
	 * Devuelve el vector  velocidad de la entidad en píxeles/seg
	 * 
	 * @return Vector2D velocidad de la entidad
	 */
	public Vector2D getVelocidad() {
		return velocidad;
	}

	/**
	 * Cambia el vector velocidad de la entidad en píxeles/seg
	 * 
	 * @param velocidad Nuevo vector entidad
	 */
	public void setVelocidad(Vector2D velocidad) {
		this.velocidad = velocidad;
	}

	/**
	 * Devuelve el vector aceleracion de la entidad en píxeles/seg^2
	 * 
	 * @return Vector2D aceleracion de la entidad
	 */
	public Vector2D getAceleracion() {
		return aceleracion;
	}

	/**
	 * Cambia el vector aceleracion de la entidad en píxeles/seg^2
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
	 * Devuelve la orientacion del objeto en radianes.
	 * 
	 * @return Devuelve el angulo en el que el objeto está orientado en ese momento
	 */
	public double getPosicionAngular() {
		return this.posicionAngular;
	}

	/**
	 * Establece una nueva orientación para el objeto en radianes.
	 * 
	 * @param posicionAngular Nueva posicion angular
	 */
	public void setPosicionAngular(double posicionAngular) {
		while (posicionAngular > 2 * Math.PI)
			posicionAngular -= 2 * Math.PI;
		this.posicionAngular = posicionAngular;
	}

	/**
	 * Devuelve la velocidad angular en radianes / seg
	 * 
	 * @return velocidad angular de la entidad
	 */
	public double getVelocidadAngular() {
		return this.velocidadAngular;
	}

	/**
	 * Nueva velocidad angular en radianes/seg
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
	 * Devuelve la aceleracion angular de la entidad en radianes/seg^2 
	 * 
	 * @return aceleracion angular
	 */
	public double getAceleracionAngular() {
		return this.aceleracionAngular;
	}

	/**
	 * Establece una nueva velocidad angular para la entidad en radianes/seg^2 
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
		return this.colorEntidad;
	}

	/**
	 * Establece un nuevo color para la entidad
	 * 
	 * @param nuevoColor nuevo color.
	 */
	public void setColor(Color nuevoColor) {
		this.colorEntidad = nuevoColor;
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
	 * Devuelve true si actualmente se esta mostrando la velocidad angular de esta entidad por pantalla. False en otro caso.
	 * 
	 * @return true si actualmente se esta mostrando la velocidad angular de esta entidad por pantalla. False en otro caso.
	 */
	public boolean getMostrandoVelocidadAngular() {
		return mostrarVelocidadAngular;
	}
	
	/**
	 * Devuelve true si actualmente se esta mostrando la aceleracion angular de esta entidad por pantalla. False en otro caso.
	 * 
	 * @return true si actualmente se esta mostrando la aceleracion angular de esta entidad por pantalla. False en otro caso.
	 */
	public boolean getMostrandoAceleracionAngular() {
		return mostrarAceleracionAngular;
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
	 * Establece si mostrar o no la velocidad angular por pantalla
	 * 
	 * @param b True para mostrar la velocidad angular por pantalla, false para ocultarlo.
	 */
	public void setMostrarVelocidadAngular(boolean b) {
		mostrarVelocidadAngular = b;
	}
	
	/**
	 * Establece si mostrar o no la aceleracion angular por pantalla
	 * 
	 * @param b True para mostrar la aceleracion angular por pantalla, false para ocultarlo.
	 */
	public void setMostrarAceleracionAngular(boolean b) {
		mostrarAceleracionAngular = b;
	}

	/**
	 * Dado un lapso de tiempo, actualiza las posiciones y las velocidades de la entidad
	 * 
	 * @param dt Laps de tiempo para los cálculos
	 */
	public void calcularNuevasPosiciones(double dt) {
		posicion.setX(posicion.getX() + velocidad.getX() * dt + 0.5 * aceleracion.getX() * dt * dt);
		posicion.setY(posicion.getY() + velocidad.getY() * dt + 0.5 * aceleracion.getY() * dt * dt);
		
		velocidad.setX(velocidad.getX() + aceleracion.getX() * dt);
		velocidad.setY(velocidad.getY() + aceleracion.getY() * dt);

		this.setPosicionAngular( this.getPosicionAngular() + 
				                 this.getVelocidadAngular() * dt + 
				                 0.5 * this.getAceleracionAngular() * Math.pow(dt, 2));
		
		this.setVelocidadAngular(this.getVelocidadAngular() + 
				                 this.getAceleracionAngular() * dt);
	}

	/**
	 * Pinta parte de la entidad, en concreto los vectores velocidad y aceleración, en el gráfico.
	 * 
	 * @param g gráfico donde pintar la entidad
	 */
	public void pintar(Graphics g) {
		if (mostrarVelocidad) {
			g.setColor(COLOR_VECTOR_VELOCIDAD);
			HerramientasGraficas.dibujarFlecha(posicion, posicion.suma(velocidad), g);
		}
		
		if (mostrarAceleracion) {
			g.setColor(COLOR_VECTOR_ACELERACION);
			HerramientasGraficas.dibujarFlecha(posicion, posicion.suma(aceleracion), g);
		}
		
		if (mostrarVelocidadAngular && velocidadAngular != 0) {
			g.setColor(COLOR_VELOCIDAD_ANGULAR);
			HerramientasGraficas.dibujarEspiral(posicion, velocidadAngular/10, g);
		}
		
		if (mostrarAceleracionAngular && aceleracionAngular != 0) {
			g.setColor(COLOR_ACELERACION_ANGULAR);
			HerramientasGraficas.dibujarEspiral(posicion, aceleracionAngular/10, g);
		}
	}


	/**
	 * Detecta si la entidad colisiona o no con los bordes X del escenario
	 * 
	 * @param Xinf X minima del escenario
	 * @param Xsup X máxima del escenario
	 * @return TRUE si la entidad colisiona
	 */
	public abstract boolean hayColisionX(int Xinf, int Xsup);
	
	/**
	 * Detecta si la entidad colisiona o no con los bordes Y del escenario
	 * 
	 * @param Xinf Y minima del escenario
	 * @param Xsup Y máxima del escenario
	 * @return TRUE si la entidad colisiona
	 */
	public abstract boolean hayColisionY(int Yinf, int Ysup);

	/**
	 * Detecta si hay una colision con otra entidad o no.
	 * 
	 * @param ent Entidad con la que comprobar si hay o no colision
	 * @return TRUE si hay colisión con la entidad.
	 * @throws EntidadDesconocidaException Si no se ha implementado un método para detectar colisiones con tal tipo de entidad
	 */
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
	protected abstract boolean hayColision(EntidadCirculo circulo);
	
	/**
	 * Devuelve si hay colision o no con una {@link EntidadPoligono}	
	 * 
	 * @param polig {@link EntidadPoligono} con el que colisiona
	 * @return TRUE si colisina, false en otro caso.
	 */
	protected abstract boolean hayColision(EntidadPoligono polig);

	/**
	 * Detecta si hay una colision con alguna entidad en una lista de entidades o no
	 * 
	 * @param listaEntidades Lista de entidades con las que comprobar si hay o no colisión
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
	 * Trata la colision con otra entidad, cambiando los vectores velocidad (y posicion si es necesario, aunque no debería) adecaudamente.
	 * 
	 * @param ent Otra entidad con la que la entidad llamada está colisionando.
	 * @throws EntidadDesconocidaException Si no se ha implementado un método para tratar colisiones con tal tipo de entidad.
	 */
	public void tratarColision(Entidad e) throws EntidadDesconocidaException {
		System.out.println("Tratar Colision con Entidades ha sido llamado :)");
		if (hayColision(e)) {
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
		this.resolverColision();
		e.resolverColision();
	}
	
	/**
	 * Intenta resolver una colisión, una vez se han modificado los vectores velocidad, haciendo que las entidades se muevan
	 * hacia su vector velocidad. De esta forma se deshace la colisión entre ellas tarde o temprano (Los vectores deberían ser
	 * de tal forma que las entidades se alejen entre sí)
	 * 
	 * Debería ser llamada exclusívamente desde {@link #tratarColision(Entidad)}
	 */
	private void resolverColision() {
		double dt = 1.0/Escenario.TICKS_Y_FPS_POR_SEGUNDO;
		posicion = posicion.suma(velocidad.mult(dt));
	}
	
	
	/**
	 * Tras una colisión, corrige los modulos de los vectores velocidad de las entidades que colisionaron
	 * para que la influencia de la masa se vea reflejada
	 * 
	 * @param ent1 {@link Entidad} que colisiona
	 * @param ent2 {@link Entidad} que colisiona
	 */
	private static void corregirModulosSegunMasa(Entidad ent1,Entidad ent2) {
		Vector2D vect1 = (ent1.getVelocidad().mult(Math.abs(ent1.getMasa() - ent2.getMasa())).suma(ent2.getVelocidad().mult(2*ent2.getMasa()))).div(ent1.getMasa() + ent2.getMasa());
		Vector2D vect2 = (ent2.getVelocidad().mult(Math.abs(ent2.getMasa() - ent1.getMasa())).suma(ent1.getVelocidad().mult(2*ent1.getMasa()))).div(ent2.getMasa() + ent1.getMasa());

		ent1.setVelocidad(vect1);
		ent2.setVelocidad(vect2);
	}
	
	/**
	 * Trata colisiones de la entidad con una {@link EntidadCirculo}
	 * 
	 * @param circulo {@link EntidadCirculo} con el que tratar la colision
	 */
	protected abstract void tratarColision(EntidadCirculo circulo);
	
	/**
	 * Trata colisiones de la entidad con una {@link EntidadPoligono}
	 * 
	 * @param polig {@link EntidadPoligono} con el que tratar la colision.
	 */
	protected abstract void tratarColision(EntidadPoligono poligono);
	
	/**
	 * Detecta si hay una colision con alguna entidad en una lista de entidades o no
	 * 
	 * @param listaEntidades Vector de entidades con las que comprobar si hay o no colisión
	 * @return Primera entidad de la lista con la que colisiona, o null si no colisiona con ninguna.
	 */
	public Entidad hayColision(java.util.Vector<Entidad> listaEntidades) {
		for (int i=0; i<listaEntidades.size(); i++)
			if (this.hayColision(listaEntidades.get(i))) {
				return listaEntidades.get(i);
			}
		return null;
	}
	
	/**
	 * Procesa la colision de la entidad con el borde X del escenario
	 */
	public abstract void tratarColisionEscenarioX();
	
	/**
	 * Procesa la colision de la entidad con el borde Y del escenario
	 */
	public abstract void tratarColisionEscenarioY();
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ("Entidad: \nPosicion: " + posicion + " \nVelocidad: "
				+ velocidad + " \nAceleracion " + aceleracion + " \nColor "
				+ colorEntidad + "\n Masa: " + this.masa);
	}
}