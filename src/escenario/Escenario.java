package escenario;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import entidades.Entidad;
import entidades.EntidadCirculo;
import exception.ColisionException;

import mates.Matriz;
import mates.Vector2D;

	
// TODO: Interfaz gráfica para insertar y eliminar objetos

/**
 * Esta clase implementa un escenario al que se pueden añadir entidades.
 * Debe estar insertado en un Jframe donde se mostrará
 * 
 * @author Jose Diaz
 */
@SuppressWarnings("serial")
public class Escenario extends JComponent {

	public static final int TICKS_Y_FPS_POR_SEGUNDO = 60;
	
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
	 * Devuelve un vector con las entidades que estan en el escenario.
	 * 
	 * @return Vector de entidades del escenario.
	 */
	public java.util.Vector<Entidad> getEntidades() {
		return listaEntidades;
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

		ent.setEscenarioContenedor(this);
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
		Entidad entidadEliminada = listaEntidades.remove(i);
		entidadEliminada.setEscenarioContenedor(null);
		return entidadEliminada;
	}
	
	/**
	 * Elimna una entidad del escenario, dada dicha entidad.
	 * 
	 * @param ent Entidad a eliminar
	 * @return TRUE si se eliminó la entidad. FALSE si la entidad no estaba en el escenario.
	 */
	public boolean eliminarEntidad(Entidad ent) {
		ent.setEscenarioContenedor(null);
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
					listaEntidades.get(i).tratarColision(listaEntidades.get(j));
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
	
	public static void main(String[] args) {
			
		final int ANCHO = 800;
		final int ALTO = 600;
		final int NumExcepcionesHastaParar = 1000;
		
		int numPelotas = 0;
		double radioPelotas = 10;
		int numExcepcionesProducidas = 0;
		int entidadesNoSpawneadas = 0;
		
		// Creamos la ventana.
		JFrame ventana = new JFrame("Escenario");
		ventana.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		ventana.setResizable(false);
	
		// Creamos un escenario.
		Escenario escenario = new Escenario(ventana, ANCHO, ALTO, Color.BLACK);
		
		ventana.pack();
		ventana.setVisible(true);
		
		Matriz MatrizPuntos1 = new Matriz(2,5,
				2,-1,-4,-1,-5,
				1, 5, 4, 1,-2);
		
		Matriz MatrizPuntos2 = new Matriz(2,7,
				3,10,5,-3, 2, 6, 8,
				2,3 ,7,-4,-1,-5,-1);
		
		MatrizPuntos1 = MatrizPuntos1.por(10);
		MatrizPuntos2 = MatrizPuntos2.por(10);
	
		/*try {
			escenario.insertarEntidad(new EntidadRectangulo(50, 70, new Vector2D(100,100), new Vector2D(0,0), new Vector2D(50, 50), 0,
		    		0, 2, Color.GREEN));
			escenario.insertarEntidad(new EntidadPoligonoRegular(4, 30, new Vector2D(
					300, 300), new Vector2D(100,700), new Vector2D(0, 0), 0,
					2 * Math.PI, 0, 0, Color.GREEN));
			//escenario.insertarEntidad(new EntidadPoligono(MatrizPuntos1, new Vector2D(600,100), new Vector2D(700,320), new Vector2D(0,0),40.0,55.0,0.0, 0,Color.CYAN));
			//escenario.insertarEntidad(new EntidadPoligono(MatrizPuntos2, new Vector2D(200,500), new Vector2D(650,400), new Vector2D(0,0),40.0,55.0,0.0, 0,Color.MAGENTA));
		} catch (ColisionException e) {
			System.out.println("La entidad" + e.getEntidad() + " no se pudo spawnear por colision.");
		}*/
	
		Random generadorAleatorio = new Random();
	
		Entidad ent = new EntidadCirculo(
				100, 
				new Vector2D(300,100), 
				new Vector2D(0,100), 
				new Vector2D(0,0),
				Color.GREEN);
		Entidad ent2 = new EntidadCirculo(
				90, 
				new Vector2D(300,400), 
				new Vector2D(0,0), 
				new Vector2D(0,0),
				Color.WHITE);
	
		try {
			escenario.insertarEntidad(ent);
			escenario.insertarEntidad(ent2);
		} catch (ColisionException e1) {
			System.out.println("¡Colision!");
			e1.printStackTrace();
		}
		
		
		
		for (int i = 0; i < numPelotas; i++) {
			try {
				escenario.insertarEntidad(new EntidadCirculo(radioPelotas*5, new Vector2D(
						Math.random() * ANCHO, Math.random() * ALTO),
						new Vector2D(50 * Math.random(), 50 * Math.random()),
						new Vector2D(0, 30), new Color(generadorAleatorio
								.nextInt(255), generadorAleatorio.nextInt(255),
								generadorAleatorio.nextInt(255))));
			} catch (ColisionException e1) {
				entidadesNoSpawneadas++;
			}
		}
		while (entidadesNoSpawneadas > 0) {
			try {
				escenario.insertarEntidad(new EntidadCirculo(radioPelotas, new Vector2D(
						Math.random() * ANCHO, Math.random() * ALTO),
						new Vector2D(500 * Math.random(), 500 * Math.random()),
						new Vector2D(0, 0), Color.RED));
				entidadesNoSpawneadas--;
			} catch (Exception e) {
				numExcepcionesProducidas++;
				if (numExcepcionesProducidas > NumExcepcionesHastaParar) {
					System.out.println("Se llego al límite de excepciones");
					System.exit(-1);
				}
			}
		}
	
	
		// Comenzamos la animación.
		escenario.accion();
	}
}