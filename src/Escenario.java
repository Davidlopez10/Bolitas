import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Entidades.Entidad;
import Exception.ColisionException;

// TODO: Implementar FPS constantes (o tiempo interno constante idnependiente dela velocida de cpu)
// TODO: Interfaz gráfica para insertar y eliminar obhetos
// TODO: Métodos para eliminar objetos

/**
 * Esta clase implementa un escenario al que se pueden añadir entidades.
 * Debe estar insertado en un Jframe donde se mostrará
 * 
 * @author Jose Diaz
 *
 */
@SuppressWarnings("serial")
public class Escenario extends JComponent {

	private int alto;
	private int ancho;

	private int maxEntidades;
	private int numEntidades;
	private Entidad[] listaEntidades;

	private Color colorFondo;

	/*
	 *  Determina la velocidad de la animacion (un valor más alto equivale a una
	 *  animación más rápida)
	 */
	private double dt;
	 

	/**
	 * Constructor de Escenario
	 * 
	 * @param ventana Ventana donde se mostrará el escenario
	 * @param ancho ancho del escenario
	 * @param alto alto del escenario
	 * @param maxEntidades número máximo de entidades que caben en el escenario
	 * @param colorFondo Color de fondo para el escenario
	 * @param dt Velocidad del escenario (tiempo relativo)
	 */
	public Escenario(JFrame ventana, int ancho, int alto, int maxEntidades, Color colorFondo, double dt) {
		this.alto = alto;
		this.ancho = ancho;
		this.numEntidades = 0;
		this.maxEntidades = maxEntidades;
		this.listaEntidades = new Entidad[maxEntidades];

		this.colorFondo = colorFondo;

		this.dt = dt;

		setPreferredSize(new Dimension(ancho, alto));
		ventana.getContentPane().add(this);
	}

	/**
	 * Devuelve el alto del escenario
	 * 
	 * @return Altura del escenario
	 */
	public double getAlto() {
		return this.alto;
	}

	/**
	 * Devuelve el ancho del escenario
	 * 
	 * @return Ancho del escenario
	 */
	public double getAncho() {
		return this.ancho;
	}

	/**
	 * Devuelve el numero actual de entidades en el escenario
	 * 
	 * @return número actual de entidades en el escenario
	 */
	public int getNumEntidades() {
		return numEntidades;
	}

	/**
	 * Devuelve el número máximo de entidades en el escenario
	 * 
	 * @return número máximo de entidades en el escenario
	 */
	public int getMaxEntidades() {
		return maxEntidades;
	}

	/**
	 * Devuelve la velocidad o tiempo relativo del escenario
	 * 
	 * @return Velocidad del escenario
	 */
	public double getVelocidad() {
		return dt;
	}

	/**
	 * Establece un nuevo tiempo relativo o velocidad para el escenario
	 * 
	 * @param dt nueva velocidad
	 */
	public void setVelocidad(double dt) {
		this.dt = dt;
	}

	/**
	 * Inserta una nueva entidad en el escenario
	 * 
	 * @param ent Entidad a insertar
	 * @return TRUE sii se insertó correctamente la entidad
	 * @throws ColisionException Si se intentó insertar una entidad donde ya existía otra
	 */
	public boolean insertarEntidad(Entidad ent) throws ColisionException {
		int i = 0;

		if (ent.hayColision(listaEntidades, numEntidades) != null
				|| ent.hayColisionX(0, ancho) || ent.hayColisionY( 0, alto))
			throw new ColisionException(ent);

		while (i < maxEntidades && listaEntidades[i] != null)
			i++;

		System.out.println("Insertado " + i + " / " + maxEntidades);

		if (i == maxEntidades) {
			return false;
		} else {
			listaEntidades[i] = ent;
			numEntidades++;
			return true;
		}

	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		// Rectángulo del fondo
		g.setColor(colorFondo);
		g.fillRect(0, 0, ancho, alto);

		// Entidades
		for (int i = 0; i < numEntidades; i++) {
			listaEntidades[i].pintar(g);
		}
	}

	/**
	 * Efectua un tick en el escenario, de forma que el tiempo avanza y se procesan todos los movimientos
	 * colisiones etc entre entidades, y se aplican (no se dibujan hasta que no se llame a {@link #paint(Graphics)}
	 * 
	 * @param dt valor de incremento en el tiempo con el que se hacen los cálculos.
	 */
	public void calculaFisica(double dt) {
		// Movimiento normal
		for (int i = 0; i < numEntidades; i++)
			listaEntidades[i].calcularNuevasPosiciones(dt);

		// Cálculo colisiones
		for (int i = 0; i < numEntidades; i++) {

			// Colisiones con los bordes
			if (listaEntidades[i].hayColisionX(0, ancho))
				listaEntidades[i].tratarColisionEscenarioX();
			if (listaEntidades[i].hayColisionY(0, alto))
				listaEntidades[i].tratarColisionEscenarioY();
			// Colisiones con el resto de entidades
			for (int j = i + 1; j < numEntidades; j++) {
				if (listaEntidades[i].hayColision(listaEntidades[j])) {
					Entidad.trataColision(listaEntidades[i], listaEntidades[j]);
				}
			}
		}
	}

	 /**
	 * Actualiza el escenario mostrando las entidades actualizadas por pantalla
	 */
	void dibuja() {
		try {
			SwingUtilities.invokeAndWait(
			new Runnable() {
				public void run() {
					paintImmediately(0, 0, ancho, alto);
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Entra en el bucle principal que se encarga de procesar las entidades y mostrarlas por pantalla
	 * indefinidamente.
	 */
	public void accion() {
		while (true) {
			calculaFisica(dt);
			dibuja();
		}
	}
}