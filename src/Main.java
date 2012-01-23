import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JFrame;

import Entidades.EntidadPelota;
import Entidades.EntidadPoligonoRegular;
import Entidades.EntidadRectangulo;
import Exception.ColisionaException;
import Exception.DimensionNoValidaException;
import Mates.Matriz;
import Mates.Recta;
import Mates.Vector;

// http://enalpha.wordpress.com/2009/05/08/trabaja-mas-rapido-en-eclipse-con-atajos-y-trucos/

// TODO: Añadir @overrides
// TODO: Javadocs
// TODO: Excepcion y solucion a entidades colisionando dentro de otras...

public class Main {
	static final int ANCHO = 800;
	static final int ALTO = 600;

	public static void main(String args[]) {
		
		Vector p1 = new Vector(3,4);
		Vector p2 = new Vector(1,1);
		Vector p3 = new Vector(20,13);
		Recta recta = new Recta(p1,p2);
		System.out.println(recta.distanciaA(p3));
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		
		int FPS = 1;
		int numPelotas = 1;
		double radioPelotas = 7;
		int maxExcepcionesSpawn = 1000;
		int numExcepcionesSpawn = 0;

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
		Escenario escenario = new Escenario(ANCHO, ALTO, 1000, Color.BLACK,
				0.00005, FPS);

		// Añadimos dicho escenario a la ventana y terminamos de crearla.
		ventana.getContentPane().add(escenario);
		ventana.pack();
		ventana.setVisible(true);

		/*
		 * try { escenario.insertarEntidad(new Pelota(20, new Vector(500,400),
		 * new Vector(0,-10), new Vector(0,0),Color.BLUE));
		 * escenario.insertarEntidad(new Pelota(40, new Vector(500,300), new
		 * Vector(0,0), new Vector(0,0),Color.RED));
		 * //escenario.insertarEntidad(new Pelota(20, new Vector(500,500), new
		 * Vector(0,10), new Vector(0,0),Color.BLUE));
		 * //escenario.insertarEntidad(new Pelota(20, new Vector(500,100), new
		 * Vector(0,-20), new Vector(0,0),Color.RED)); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */

		try {
			escenario.insertarEntidad(new EntidadRectangulo(50, 70, new Vector(100,
					100), new Vector(1000, 777), new Vector(0, 0), 0,
		    		6, 0, Color.BLUE));
			escenario.insertarEntidad(new EntidadPoligonoRegular(4, 30, new Vector(
					300, 300), new Vector(450, 300), new Vector(0, 0), 0,
					2 * Math.PI, 0, 0, Color.GREEN));
			// escenario.insertarEntidad(new PoligonoRegular(5,30, new
			// Vector(300,300), new Vector(1000,700), new
			// Vector(0,0),0,2*Math.PI,0, 0 , Color.GREEN));
			// escenario.insertarEntidad(new Rectangulo(10,30, new
			// Vector(400,100), new Vector(-3,7), new Vector(0,0),10,0,0.01,
			// Color.GREEN));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		Random generadorAleatorio = new Random();

		for (int i = 0; i < numPelotas; i++) {
			try {
				escenario.insertarEntidad(new EntidadPelota(radioPelotas*5, new Vector(
						Math.random() * ANCHO, Math.random() * ALTO),
						new Vector(2000 * Math.random(), 2000 * Math.random()),
						new Vector(0, 0), new Color(generadorAleatorio
								.nextInt(255), generadorAleatorio.nextInt(255),
								generadorAleatorio.nextInt(255))));
			} catch (ColisionaException e1) {
				entidadesNoSpawneadas++;
			} catch (DimensionNoValidaException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		while (entidadesNoSpawneadas > 0) {
			try {
				escenario.insertarEntidad(new EntidadPelota(radioPelotas, new Vector(
						Math.random() * ANCHO, Math.random() * ALTO),
						new Vector(20 * Math.random(), 20 * Math.random()),
						new Vector(0, 0), Color.RED));
				entidadesNoSpawneadas--;
			} catch (Exception e) {
				numExcepcionesSpawn++;
				if (numExcepcionesSpawn > maxExcepcionesSpawn) {
					System.out.println("Se llego al límite de excepciones");
					System.exit(-1);
				}
				System.out.println(e + "\n");
			}
		}

		// Comenzamos la animación.
		escenario.accion();
	}
}