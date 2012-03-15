package util;

import java.awt.Graphics;

import Mates.Matriz;
import Mates.Vector2D;

public class HerramientasGraficas {

		
	/**
	 * Dibuja una flecha desde el punto origen, hasta destino
	 * 
	 * @param origen Origen de la flecha
	 * @param destino Destino o fin de la flecha
	 * @param g Gráfico donde se dibujará
	 */
	
	public static void dibujarFlecha(Vector2D origen, Vector2D destino, Graphics g) {	
		Vector2D vector = destino.resta(origen).mult(0.8);
		Matriz giro1 = Matriz.getMatriz2x2Giro(-Math.PI/28);
		Matriz giro2 = Matriz.getMatriz2x2Giro(Math.PI/28);
		
		Vector2D vertice1 = new Vector2D(giro1.por(vector).suma(origen));
		Vector2D vertice2 = new Vector2D(giro2.por(vector).suma(origen));
		
		g.drawLine( 
				      (int) origen.getX(),
				      (int) origen.getY(),
				      (int) destino.getX(),
				      (int) destino.getY()
				  );
		
		g.drawLine( 
			          (int) vertice1.getX(),
			          (int) vertice1.getY(),
			          (int) destino.getX(),
			          (int) destino.getY()
			      ); 
		
		g.drawLine( 
			          (int) vertice2.getX(),
			          (int) vertice2.getY(),
			          (int) destino.getX(),
			          (int) destino.getY()
			      );
	}
}
