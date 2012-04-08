package util;

import java.awt.Graphics;

import mates.Matriz;
import mates.Vector2D;


/**
 * Esta clase implementa una serie de m�todos est�ticos que ayudan a dibujar algunos elementos.
 * 
 * No deber�a instanciarse.
 * 
 * @author Jose D�az
 */
public class HerramientasGraficas {

	private static final int SEGMENTOS_POR_VUELTA = 20;
	private static final int MAXIMAS_VUELTAS_ESPIRAL = 10;
	
	private static final int TAMA�O_FLECHA_VECTOR = 15;
		
	/**
	 * Dibuja una flecha desde el punto origen, hasta destino
	 * 
	 * @param origen {@link Vector2D} Origen de la flecha
	 * @param destino {@link Vector2D} Destino o fin de la flecha
	 * @param g {@link Graphics} donde se dibujar�
	 */
	public static void dibujarFlecha(Vector2D origen, Vector2D destino, Graphics g) {	
		Vector2D vector = destino.resta(origen).mult(0.9);
		Matriz giro1 = Matriz.getMatriz2x2Giro(-Math.PI/100);
		Matriz giro2 = Matriz.getMatriz2x2Giro(Math.PI/100);
		
		Vector2D vertice1 = new Vector2D(giro1.por(vector).suma(origen));
		Vector2D vertice2 = new Vector2D(giro2.por(vector).suma(origen));
		

		Vector2D vertice1RelativoADestino = destino.resta(vertice1);
		Vector2D vertice2RelativoADestino = destino.resta(vertice2);
		
		vertice1RelativoADestino = vertice1RelativoADestino.normalizar().mult(-TAMA�O_FLECHA_VECTOR);
		vertice2RelativoADestino = vertice2RelativoADestino.normalizar().mult(-TAMA�O_FLECHA_VECTOR);
		
		vertice1 = vertice1RelativoADestino.suma(destino);
		vertice2 = vertice2RelativoADestino.suma(destino);

		
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
	
	/**
	 * Dibuja un espiral de centro dado proporcional a la magnitud dada.
	 * 
	 * @param centro {@link Vector2D} Centro de la espiral
	 * @param magnitud Determinar� cu�n larga se dibujar� la espiral.
	 * @param g {@link Graphics} fonde se dibujar� la espiral.
	 */
	public static void dibujarEspiral(Vector2D centro, double magnitud, Graphics g) {
		// Parametro de la curva en param�tricas. Empezamos en 2 Pi para no empezar pegados al centro de la entidad.
		double t;
		double incremento;
		
		
		// Esto es necesario debido a la forma en la que hemos decidido que las entidades giran (a derecha (?))
		magnitud = -magnitud;
		
		if (Math.abs(magnitud) > MAXIMAS_VUELTAS_ESPIRAL) {
			magnitud = Math.signum(magnitud) * MAXIMAS_VUELTAS_ESPIRAL;
		}
		
		if (magnitud >0) {
			t = 2*Math.PI; 
			incremento = 2*Math.PI / SEGMENTOS_POR_VUELTA;
		}
		else {
			t = -2*Math.PI; 
			incremento = -2*Math.PI / SEGMENTOS_POR_VUELTA;
		}
		
		Vector2D inicio = new Vector2D(t*Math.cos(t),t*Math.sin(t));
		inicio = inicio.suma(centro);
		t += incremento;
		Vector2D fin = new Vector2D(t*Math.cos(t),t*Math.sin(t));
		fin = fin.suma(centro);
		
		for (int i=0; i< Math.abs((magnitud / 2*Math.PI) * SEGMENTOS_POR_VUELTA); i++) {
			g.drawLine(
					   (int) inicio.getX(), 
					   (int) inicio.getY(), 
					   (int) fin.getX(), 
					   (int) fin.getY()
					   );			
			inicio.setXY(t*Math.cos(t), t*Math.sin(t));
			inicio = inicio.suma(centro);
			t += incremento;
			fin.setXY(t*Math.cos(t), t*Math.sin(t));
			fin = fin.suma(centro);
		}
		fin = inicio.suma(fin.resta(inicio).mult(2));
		dibujarFlecha(inicio,fin,g);	
	}
}
