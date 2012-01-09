import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;



public class Escenario extends JComponent {
	
	private static final long serialVersionUID = 1L;
	private int alto;
	private int ancho;
	
	private int maxEntidades;
	private int numEntidades;
	private Entidad[] listaEntidades;
	
	private Color colorFondo;
	
	public Escenario(int alto, int ancho, int maxEntidades, Color colorFondo) {
		this.alto=alto;
		this.ancho=ancho;
		this.numEntidades=0;
		this.maxEntidades=maxEntidades;
		this.listaEntidades = new Entidad[maxEntidades];
		
		this.colorFondo = colorFondo;
		
		setPreferredSize(new Dimension(ancho, alto));
	}
	
	public double getAlto() {
		return this.alto;
	}
	
	public double getAncho() {
		return this.ancho;
	}
	
	public int getNumEntidades() {
		return numEntidades;
	}
	
	public int getMaxEntidades() {
		return maxEntidades;
	}
	
	public boolean insertarEntidad(Entidad ent) {
		int i=0;
		while (listaEntidades[i]!=null)
			i++;
		if (i==maxEntidades) {
			return false;
		}
		else {
			listaEntidades[i] = ent;
			numEntidades++;
			return true;
		}
		
	}
	
	public void paint(Graphics g) {
		// Rectángulo del fondo
		g.setColor(colorFondo);
		g.fillRect(0,0,ancho,alto);
		
		// Entidades
		for (int i=0;i<numEntidades;i++) {
			listaEntidades[i].pintar(g);
		}
	}
	
	public void calculaFisica(double dt) {
		// Movimiento normal
		for (int i=0;i<numEntidades;i++)
			listaEntidades[i].calcularNuevasPosiciones(dt);
		
		boolean colision;
		// Cálculo colisiones
		for (int i=0;i<numEntidades;i++) {
			
			// Colisiones con los bordes
			if (listaEntidades[i].hayColisionX(0,ancho)) 
				listaEntidades[i].getVelocidad().invertirX();
			
			if (listaEntidades[i].hayColisionY(0,alto)) 
				listaEntidades[i].getVelocidad().invertirY();
			
			
			
			// Colisiones con otros objetos
			colision=false;
			for (int j=0;j<numEntidades;j++) {
				if (i==j)
					j++;
				if (j==numEntidades)
					break;
				if (listaEntidades[i].hayColision(listaEntidades[j])) {
					colision=true;					
				}
			}
			if (colision==true) {
				listaEntidades[i].getVelocidad().invertirXY();
			}
		}
		
		
	}
	
	public void dibuja() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				paintImmediately(0,0,ancho,alto);
			}
		});
	}
	
	public void accion() {
		System.out.println("He entrado en accion");
		float dt;
		while (true) {
			System.out.println(listaEntidades[0].getPosicion());
			dt=1/100f;
			calculaFisica(dt);
			try {
				dibuja();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}