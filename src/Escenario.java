import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Entidades.Entidad;
import Exception.ColisionException;

// TODO: Interfaz gráfica para insertar y eliminar objetos

/**
 * Esta clase implementa un escenario al que se pueden añadir entidades.
 * Debe estar insertado en un Jframe donde se mostrará
 * 
 * @author Jose Diaz
 */
@SuppressWarnings("serial")
public class Escenario extends JComponent {

	static final int TICKS_Y_FPS_POR_SEGUNDO = 60;
	
	private int alto;
	private int ancho;

	private java.util.Vector<Entidad> listaEntidades = new java.util.Vector<Entidad>();

	private Color colorFondo;
	
	private boolean escenarioActivo = false;
	 
	/**
	 * Constructor de Escenario
	 * 
	 * @param ventana Ventana donde se mostrará el escenario
	 * @param ancho ancho del escenario
	 * @param alto alto del escenario
	 * @param colorFondo Color de fondo para el escenario)
	 */
	public Escenario(JFrame ventana, int ancho, int alto, Color colorFondo) {
		this.alto = alto;
		this.ancho = ancho;

		this.colorFondo = colorFondo;

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
		return listaEntidades.size();
	}

	/**
	 * Inserta una nueva entidad en el escenario
	 * 
	 * @param ent Entidad a insertar
	 * @return TRUE si se insertó correctamente la entidad
	 * @throws ColisionException Si se intentó insertar una entidad donde ya existía otra
	 */
	public void insertarEntidad(Entidad ent) throws ColisionException {

		if (ent.hayColision(listaEntidades) != null
				|| ent.hayColisionX(0, ancho) || ent.hayColisionY( 0, alto))
			throw new ColisionException(ent);


		listaEntidades.add(ent);
	}
	
	/**
	 * Elimina una entidad del escenario, por su índice.
	 * 
	 * @param i Índice de la entidad a eliminar
	 * @return La entidad que se ha eliminado.
	 * @throws ArrayIndexOutOfBoundsException Si el índice proporcionado está más alla de el número actual de entidades
	 */
	public Entidad eliminarEntidad(int i) throws ArrayIndexOutOfBoundsException{
		return listaEntidades.remove(i);
	}
	
	/**
	 * Elimna una entidad del escenario, dada dicha entidad.
	 * 
	 * @param ent Entidad a eliminar
	 * @return TRUE si se eliminó la entidad. FALSE si la entidad no estaba en el escenario.
	 */
	public boolean eliminarEntidad(Entidad ent) {
		return listaEntidades.remove(ent);
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
		for (Entidad ent : listaEntidades) {
			ent.pintar(g);
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
		for (Entidad ent : listaEntidades) {
			ent.calcularNuevasPosiciones(dt);
		}


		// Cálculo colisiones
		for (int i = 0; i < listaEntidades.size(); i++) {

			// Colisiones con los bordes
			if (listaEntidades.get(i).hayColisionX(0, ancho))
				listaEntidades.get(i).tratarColisionEscenarioX();
			if (listaEntidades.get(i).hayColisionY(0, alto))
				listaEntidades.get(i).tratarColisionEscenarioY();
			// Colisiones con el resto de entidades
			for (int j = i + 1; j < listaEntidades.size(); j++) {
				if (listaEntidades.get(i).hayColision(listaEntidades.get(j))) {
					Entidad.trataColision(listaEntidades.get(i), listaEntidades.get(j));
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
	
		escenarioActivo = true;
	
		long ultimaVez = System.nanoTime();
		double sinProcesar = 0;
		double nanosegundosPorTick = 1000000000.0 / TICKS_Y_FPS_POR_SEGUNDO;
		int fotogramas = 0;
		int ticks = 0;
		long tempMostrarInfo = System.currentTimeMillis();
		
		while (escenarioActivo) {
			
			long estaVez = System.nanoTime();
			sinProcesar += (estaVez - ultimaVez) / nanosegundosPorTick;
			ultimaVez = estaVez;
			boolean hayQueRenderizar = false;
			
			while (sinProcesar > 0) {
				ticks++;
				calculaFisica(1.0/TICKS_Y_FPS_POR_SEGUNDO);
				sinProcesar--;
				hayQueRenderizar = true;
			}
	
			/*try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
	
			if (hayQueRenderizar) {
				fotogramas++;
				dibuja();
			}
			
			if (!hayQueRenderizar && sinProcesar <= 0) {
				try {
					Thread.sleep(1000/TICKS_Y_FPS_POR_SEGUNDO);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	
			if (System.currentTimeMillis() - tempMostrarInfo > 1000) {
				tempMostrarInfo += 1000;
				System.out.println(ticks + " ticks, " + fotogramas + " fps");
				fotogramas = 0;
				ticks = 0;
			}
		}
	}
	
	public void detener() {
		escenarioActivo = false;
	}

}