import java.awt.Color;
import java.awt.Graphics;


public class Pelota extends Entidad {
	
	private double radio;
	
	public Pelota(double radio, Vector posicion, Vector velocidad, Vector aceleracion,Color color) {
		super(posicion,velocidad,aceleracion,color);
		this.radio=radio;
	}
	
	public double getRadio() {
		return this.radio;
	}
	
	public void setRadio(double nuevoRadio) {
		this.radio=nuevoRadio;
	}

	public void pintar(Graphics g) {
		g.setColor(color);
		g.fillOval((int) (posicion.x - radio), (int) (posicion.y - radio), (int) radio, (int) radio);
	}
	
	public boolean hayColision(Entidad ent) {
		if (ent instanceof Pelota) {
																
			if ( ((Pelota) ent).getRadio() + this.getRadio() > 2*this.getPosicion().distanciaA(ent.getPosicion())) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
	
	public boolean hayColisionX(int limite1, int limite2) {
		if (posicion.x - radio  < limite1 || posicion.x + radio > limite2)
			return true;
		else
			return false;
	}
	
	public boolean hayColisionY(int limite1, int limite2) {
		if (posicion.y - radio  < limite1 || posicion.y + radio > limite2)
			return true;
		else
			return false;
	}
	
}