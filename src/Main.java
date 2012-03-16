import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JFrame;

import Entidades.Entidad;
import Entidades.EntidadCirculo;
import Entidades.EntidadPoligono;
import Entidades.EntidadPoligonoRegular;
import Entidades.EntidadRectangulo;
import Exception.ColisionException;
import Exception.DimensionNoValidaException;
import Mates.Matriz;
import Mates.Vector2D;

public class Main {

	final static int ANCHO = 800;
	final static int ALTO = 600;
	final static int NumExcepcionesHastaParar = 1000;

	public static void main(String args[]) {
		
		int numPelotas = 1;
		double radioPelotas = 5;
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
			escenario.insertarEntidad(new EntidadRectangulo(50, 70, new Vector2D(100,
					100), new Vector2D(200,100), new Vector2D(0, 0), 0,
		    		6, 0, Color.BLUE));
			escenario.insertarEntidad(new EntidadPoligonoRegular(4, 30, new Vector2D(
					300, 300), new Vector2D(100,700), new Vector2D(0, 0), 0,
					2 * Math.PI, 0, 0, Color.GREEN));
			escenario.insertarEntidad(new EntidadPoligono(MatrizPuntos1, new Vector2D(600,100), new Vector2D(700,320), new Vector2D(0,0),40.0,55.0,0.0, 0,Color.CYAN));
			escenario.insertarEntidad(new EntidadPoligono(MatrizPuntos2, new Vector2D(200,500), new Vector2D(650,400), new Vector2D(0,0),40.0,55.0,0.0, 0,Color.MAGENTA));
		} catch (ColisionException e) {
			System.out.println("La entidad" + e.getEntidad() + " no se pudo spawnear por colision.");
		}*/

		Random generadorAleatorio = new Random();

		Entidad ent = new EntidadCirculo(
				radioPelotas*5, 
				new Vector2D(radioPelotas*5,radioPelotas*5), 
				new Vector2D(10,0), 
				new Vector2D(0,0),
				Color.RED);

		try {
			escenario.insertarEntidad(ent);
		} catch (ColisionException e1) {
			System.out.println("¡Colision!");
			e1.printStackTrace();
		}
		
		
		
		/*for (int i = 0; i < numPelotas; i++) {
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
		}*/
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
