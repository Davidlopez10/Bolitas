import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;


public class Main {
	public static void main(String args[]) {
		int numPelotas = 20;
		double radioPelotas = 25;
		JFrame ventana = new JFrame("Escenario");
		ventana.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		ventana.setResizable(false);
		Escenario escenario = new Escenario(600,800, numPelotas, Color.BLACK);
		ventana.getContentPane().add(escenario);
		ventana.pack();
		ventana.setVisible(true);
		
		
		Vector posiciones[] = new Vector[numPelotas];
		Vector posicion = null;
		int numPelotasAgregadas = 0;
		boolean colisiona;
		
		// calculamos posciiones
		for (int i=0;i<numPelotas;i++) {
			/*
			colisiona = false;
			
			do {*/
			posicion = Vector.getRandomVector(800,600);
			
			/*for (int j=0;j<i;j++)
				if (posiciones[j].distanciaA(posicion) < radioPelotas ||
					posicion.x - radioPelotas < 0 ||
					posicion.x + radioPelotas > 600 ||
					posicion.y - radioPelotas < 0 ||
					posicion.y + radioPelotas > 800) {
					
						colisiona = true;
				
				}
			} while (colisiona);*/
				
			posiciones[i]=posicion;
			
			
		}
		
		// Spawneamos las boals
		for (int i=0;i<numPelotas;i++) {		
			escenario.insertarEntidad(new Pelota(radioPelotas, posiciones[i], new Vector(20*Math.random(),5*Math.random()), new Vector(5*Math.random(),10), Color.PINK));
		}
		escenario.accion();
	}
}