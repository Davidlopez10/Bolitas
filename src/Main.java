import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

import Entidades.Pelota;
import Entidades.Rectangulo;
import Exception.ColisionaException;
import Exception.DimensionNoValidaException;
import Mates.Matriz;
import Mates.Vector;

public class Main {
	static final int ANCHO = 800;
	static final int ALTO  = 600;
	
	public static void main(String args[]) {
		int numPelotas = 10;
		double radioPelotas = 15;
		int maxExcepcionesSpawn=100;
		int numExcepcionesSpawn=0;
		
		int entidadesNoSpawneadas=0;
		
		// Creamos la ventana.
		JFrame ventana = new JFrame("Escenario");
		ventana.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		ventana.setResizable(false);
		
		// Creamos un escenario.
		Escenario escenario = new Escenario(ANCHO, ALTO, numPelotas, Color.BLACK, 0.005);

		// Añadimos dicho escenario a la ventana y terminamos de crearla.
		ventana.getContentPane().add(escenario);
		ventana.pack();
		ventana.setVisible(true);
		
		try {
			escenario.insertarEntidad(new Pelota(20, new Vector(500,400), new Vector(0,-10), new Vector(0,2),Color.BLUE));
			escenario.insertarEntidad(new Pelota(20, new Vector(490,300), new Vector(0,0), new Vector(0,2),Color.RED));
			escenario.insertarEntidad(new Pelota(20, new Vector(500,500), new Vector(0,10), new Vector(0,2),Color.BLUE));
			escenario.insertarEntidad(new Pelota(20, new Vector(500,100), new Vector(0,-20), new Vector(0,2),Color.RED));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			escenario.insertarEntidad(new Rectangulo(10,30, new Vector(100,100), new Vector(10,-4), new Vector(0,0), Color.BLUE));
			escenario.insertarEntidad(new Rectangulo(10,30, new Vector(400,100), new Vector(-3,7), new Vector(0,0), Color.GREEN));
		} catch (Exception e1) {		
			e1.printStackTrace();
		}
	
		/*
		while ( entidadesNoSpawneadas > 0) {
			try {
				escenario.insertarEntidad(new Pelota(radioPelotas, Vector.getRandomVector(ANCHO, ALTO), new Vector(20*Math.random(),5*Math.random()), new Vector(0,0), Color.RED));
				entidadesNoSpawneadas--;
			} catch (Exception e) {
				numExcepcionesSpawn++;
				if (numExcepcionesSpawn > maxExcepcionesSpawn) {
					System.out.println("Se llego al límite de excepciones");
					System.exit(-1);
				}
				entidadesNoSpawneadas++;
				System.out.println(e + "\n");
			}
		}*/
		
		// Comenzamos la animación.
		escenario.accion();
	}
}