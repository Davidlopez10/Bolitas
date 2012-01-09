import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

import Entidades.Pelota;
import Mates.Vector;


public class Main {
	
	static final int ANCHO = 800;
	static final int ALTO  = 600;
	
	public static void main(String args[]) {
		
		int numPelotas = 20;
		double radioPelotas = 25;
		
		// Creamos la ventana.
		JFrame ventana = new JFrame("Escenario");
		ventana.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		ventana.setResizable(false);
		
		// Creamos un escenario.
		Escenario escenario = new Escenario(ANCHO, ALTO, numPelotas, Color.BLACK, 0.1);
		
		// Añadimos dicho escenario a la ventana y terminamos de crearla.
		ventana.getContentPane().add(escenario);
		ventana.pack();
		ventana.setVisible(true);
		
		// Introducimos las bolas en el escenario.
		for (int i=0;i<numPelotas;i++) 		
			escenario.insertarEntidad(new Pelota(radioPelotas, Vector.getRandomVector(ANCHO, ALTO), new Vector(20*Math.random(),5*Math.random()), new Vector(0,0), Color.PINK));
		
		// Comenzamos la animación.
		escenario.accion();
	}
}