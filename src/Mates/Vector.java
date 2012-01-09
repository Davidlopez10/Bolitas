package Mates;
public class Vector {
	
	public double x;
	public double y;
	
	public Vector(double x, double y) {
		this.x=x;
		this.y=y;
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double nuevoX) {
		this.x=nuevoX;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double nuevoY) {
		this.y=nuevoY;
	}
	
	public void setXY(double nuevoX, double nuevoY) {
		this.x=nuevoX;
		this.y=nuevoY;
	}
	
	public void invertirX() {
		x=-x;
	}
	
	public void invertirY() {
		y=-y;
	}
	
	public void invertirXY() {
		invertirX();
		invertirY();
	}
	
	public String toString() {
		return "Vector (" + x + "," + y + ")";
	}
	
	public double distanciaA(Vector v) {
		return Math.sqrt( (this.x-v.x)*(this.x-v.x) + (this.y-v.y)*(this.y-v.y));
	}
	
	public double norma() {
		return Math.sqrt( (this.x*this.x + this.y*this.y));
	}
	
	public Vector normalizar() {
		return new Vector(this.x / this.norma(), this.y / this.norma());
	}
	
	public double productoEscalar(Vector vect) {
		return this.x*vect.x + this.y*vect.y;
	}
	
	public double cosAngulo(Vector vect) {
		return this.productoEscalar(vect) / (this.norma() * vect.norma());
	}
	
	public static Vector getRandomVector(double limitex, double limitey) {
		return new Vector(Math.random() * limitex,Math.random() * limitey);
	}
}