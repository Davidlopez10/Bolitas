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

// TODO: Tras refactorizar los vectores para ser genéricos, no funciona.
// 		 repasar...

public class Main {
	static final int ANCHO = 800;
	static final int ALTO  = 600;
	
	public static void main(String args[]) {
		
		Matriz m1 = null;
		Matriz m2 = null;
		System.out.println("Antes");
		try {
			m1 = new Matriz(2,3,1,2,3,4,5,6);
			m2 = new Matriz(3,4,1,2,3,4,5,6,7,8,9,10,11,12);
			System.out.println(m1.getFilas() + "x" + m1.getColumnas() + "\n" + m1);
			System.out.println();
			System.out.println(m2.getFilas() + "x" + m2.getColumnas() + "\n" + m2);
			System.out.println();
			System.out.println(m1.por(m2));
		} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
		}
		
		
		Vector test1 = new Vector(1,2,3);
		Vector test2 = new Vector(4,5,6);
		Vector test3 = new Vector(7,8,9);

		
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
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
			escenario.insertarEntidad(new Pelota(10, new Vector(500,400), new Vector(0,-10), new Vector(0,2),Color.BLUE));
			escenario.insertarEntidad(new Pelota(10, new Vector(490,300), new Vector(0,0), new Vector(0,2),Color.RED));
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
		
		// Introducimos las bolas en el escenario.
		/*for (int i=0;i<numPelotas;i++) { 		
			try {
				escenario.insertarEntidad(new Pelota(Math.random()*radioPelotas, Vector.getRandomVector(ANCHO, ALTO), new Vector(10+Math.random(),Math.random()), new Vector(0,10), Color.RED));
			} catch (ColisionaException e) {
				numExcepcionesSpawn++;
				entidadesNoSpawneadas++;
				System.out.println(e + "\n");
			}
		}*/
		
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
		}
		
		// Comenzamos la animación.
		
		escenario.accion();
		
		// A partir de aquí códigos de pruebas...
	
		Vector v1 = new Vector(30.0,0.0);
		Vector v2 = new Vector(0.0,0.0);
		Vector v3 = new Vector(-18.0000000000000233,6.0000000000005343);
		
		try {
			System.out.println(v1.resta(v2));
		} catch (DimensionNoValidaException e1) {
			e1.printStackTrace();
		}
	
	}
}