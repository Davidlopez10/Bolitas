package util;

import java.awt.Graphics;

import Mates.Matriz;
import Mates.Vector2D;

public class HerramientasGraficas {

	static final int SEGMENTOS_POR_VUELTA = 20;
	static final int MAXIMAS_VUELTAS_ESPIRAL = 10;
	
	static final int TAMAÑO_FLECHA_VECTOR = 15;
		
	/**
	 * Dibuja una flecha desde el punto origen, hasta destino
	 * 
	 * @param origen Origen de la flecha
	 * @param destino Destino o fin de la flecha
	 * @param g Gráfico donde se dibujará
	 */
	public static void dibujarFlecha(Vector2D origen, Vector2D destino, Graphics g) {	
		Vector2D vector = destino.resta(origen).mult(0.9);
		Matriz giro1 = Matriz.getMatriz2x2Giro(-Math.PI/100);
		Matriz giro2 = Matriz.getMatriz2x2Giro(Math.PI/100);
		
		Vector2D vertice1 = new Vector2D(giro1.por(vector).suma(origen));
		Vector2D vertice2 = new Vector2D(giro2.por(vector).suma(origen));
		

		Vector2D vertice1RelativoADestino = destino.resta(vertice1);
		Vector2D vertice2RelativoADestino = destino.resta(vertice2);
		
		vertice1RelativoADestino = vertice1RelativoADestino.normalizar().mult(-TAMAÑO_FLECHA_VECTOR);
		vertice2RelativoADestino = vertice2RelativoADestino.normalizar().mult(-TAMAÑO_FLECHA_VECTOR);
		
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
	 * @param centro Centro de la espiral
	 * @param magnitud Determinará cuán larga se dibujará la espiral.
	 * @param g Gráfico donde se dibujará la espiral.
	 */
	public static void dibujarEspiral(Vector2D centro, double magnitud, Graphics g) {
		
		// Parametro de la curva en paramétricas. Empezamos en 2 Pi para no empezar pegados al centro de la entidad.
		double t;
		double incremento;
		
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
