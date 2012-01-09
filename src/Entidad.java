import java.awt.Color;
import java.awt.Graphics;


public abstract class Entidad {
	
	protected Vector posicion;
	protected Vector velocidad;
	protected Vector aceleracion;
	
	protected Color color;
	
	public Entidad(Vector posicion,Vector velocidad, Vector aceleracion,Color color) {
		this.posicion = posicion;
		this.velocidad = velocidad;
		this.aceleracion = aceleracion;
		this.color = color;
	}
	
	public Vector getPosicion() {
		return posicion;
	}

	public void setPosicion(Vector posicion) {
		this.posicion = posicion;
	}

	public Vector getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(Vector velocidad) {
		this.velocidad = velocidad;
	}

	public Vector getAceleracion() {
		return aceleracion;
	}

	public void setAceleracion(Vector aceleracion) {
		this.aceleracion = aceleracion;
	}
	
	public void calcularNuevasPosiciones(double dt) {
		posicion.x  += velocidad.x *dt + 1/2 * aceleracion.x * dt * dt;
		posicion.y  += velocidad.y *dt + 1/2 * aceleracion.y * dt * dt;
		velocidad.x += aceleracion.x * dt;
		velocidad.y += aceleracion.y * dt;
	}
	
	public abstract void pintar(Graphics g);
	
	public abstract boolean hayColision(Entidad ent);
	
	public abstract boolean hayColisionX(int limite1, int limite2);
	
	public abstract boolean hayColisionY(int limite1, int limite2);

	
}