package Mates;

import Exception.DimensionNoValidaException;

// TODO: Generaliza a vectores de n componentes
// TODO: Implementar métodos entre matrices y vectores

public class Vector {
	
	private int dimension;
	private double[] vector;
	
	public Vector(double ... datos) {
		vector= new double[datos.length];
		int i=0;
		for (double n : datos) {
			vector[i] = n;
			i++;
		}
		dimension=datos.length;
	}
	
	public Vector(double datos[],int dimension) {
		this.vector=datos;
		this.dimension=dimension;
	}
	
	public int dimension() {
		return this.dimension;
	}
	
	public double get(int index) throws IndexOutOfBoundsException {
		return vector[index];
	}
	
	public double[] getDatos() {
		return this.vector;
	}
	
	public int[] getDatosInt() {
		int[] vectorEntero = new int[this.dimension()];
		for (int i=0;i<this.dimension();i++)
			vectorEntero[i] = (int) this.vector[i];
		return vectorEntero;
	}
	
	public void set(int index, double nuevoValor) throws IndexOutOfBoundsException {
		this.vector[index]=nuevoValor;
	}
	
	public void invertirComponente(int index) throws IndexOutOfBoundsException {
		vector[index] = -vector[index];
	}
	
	// Los siguientes getters facilitan tratar con vectores de 2 o 3 componentes (los mas habituales)
	
	public double getX() throws DimensionNoValidaException {
		if (this.dimension() > 3 || this.dimension() <1)
			throw new DimensionNoValidaException("Intentado acceder a X de " + this + "(no aconsejado si hay más de tres componentes)");
		return this.get(0);	
	}
	
	public void setX(double nuevoValor) throws DimensionNoValidaException {
		if (this.dimension > 3 || this.dimension() < 1)
			throw new DimensionNoValidaException("Intentado setear X de " + this + "(no aconsejado si hay más de tres componentes)");
		
		set(0,nuevoValor);
	}
	
	public double getY() throws DimensionNoValidaException {
		if (this.dimension()>3 || this.dimension() < 2)
			throw new DimensionNoValidaException("Intentado acceder a Y de " + this + "(no aconsejado si hay más de tres componentes)");
		return this.get(1);	
	}
	
	public void setY(double nuevoValor) throws DimensionNoValidaException {
		if (this.dimension > 3 || this.dimension() < 1)
			throw new DimensionNoValidaException("Intentado setear Y de " + this + "(no aconsejado si hay más de tres componentes)");
		
		set(1,nuevoValor);
	}
	
	public double getZ() throws DimensionNoValidaException {
		if (this.dimension()>3 || this.dimension() < 3)
			throw new DimensionNoValidaException("Intentado acceder a Z de " + this + "(no aconsejado si hay más de tres componentes)");
		return this.get(2);	
	}
	
	public void setZ(double nuevoValor) throws DimensionNoValidaException {
		if (this.dimension > 3 || this.dimension() < 1)
			throw new DimensionNoValidaException("Intentado acceder Z de " + this + "(no aconsejado si hay más de tres componentes)");
		
		set(2,nuevoValor);
	}
	
	public void invertirX() throws DimensionNoValidaException {
		if (this.dimension > 3 || this.dimension() < 1)
			throw new DimensionNoValidaException("Intentado invertir X de " + this + "(no aconsejado si hay más de tres componentes)");
		invertirComponente(0);
	}
	
	public void invertirY() throws DimensionNoValidaException {
		if (this.dimension > 3 || this.dimension() < 2)
			throw new DimensionNoValidaException("Intentado invertir Y de " + this + "(no aconsejado si hay más de tres componentes)");
		invertirComponente(1);
	}
	
	public void invertirZ() throws DimensionNoValidaException {
		if (this.dimension > 3 || this.dimension() < 3)
			throw new DimensionNoValidaException("Intentado invertir Z de " + this + "(no aconsejado si hay más de tres componentes)");
		invertirComponente(2);
	}
	
	public Vector suma(Vector v) throws DimensionNoValidaException {
		if (this.dimension() != v.dimension())
			throw new DimensionNoValidaException("Intentado sumar " + v + " y " + this );
	
		double[] vect = new double[this.dimension];
		for (int i=0;i<this.dimension();i++)
			vect[i]=this.get(i)+v.get(i);
		
		return new Vector(vect,this.dimension);
	}
	
	public Vector resta(Vector v) throws DimensionNoValidaException {
		if (this.dimension() != v.dimension())
			throw new DimensionNoValidaException("Intentado sumar " + v + " y " + this );
	
		double[] vect = new double[this.dimension];
		for (int i=0;i<this.dimension();i++)
			vect[i]=this.get(i)-v.get(i);
		
		return new Vector(vect,this.dimension);
	}
	
	public String toString() {
		String cadena="(";
		for (int i=0;i<this.dimension()-1;i++)
			cadena += this.get(i) +",";
		cadena += this.get(this.dimension()-1) + ")";
		return cadena;
	}
	
	public double distanciaA(Vector v) {
		double distancia=0;
		for (int i=0;i<this.dimension();i++)
			distancia += Math.pow(this.get(i) - v.get(i), 2);
		return Math.sqrt(distancia);
	}
	
	public double norma() {
		double norma=0;
		for (int i=0;i<this.dimension();i++)
			norma += Math.pow(this.get(i), 2);
		return Math.sqrt(norma);
	}
	
	public Vector normalizar() {
		return this.div(this.norma());
	}
	
	public Vector proyectarSobre(Vector u) throws DimensionNoValidaException {
		if (u.norma() == 0)
			System.out.println("ERROR PROYECTANDO" + this + u);
		// productoEscalar puede lanzar DimensionNoValidaException
		return u.normalizar().mult(this.productoEscalar(u)/u.norma());
	}
	
	public Vector mult(double multiplicador) {
		double[] vect = new double[this.dimension];
		for (int i=0;i<this.dimension();i++)
			vect[i]=this.get(i)*multiplicador;
		
		return new Vector(vect,this.dimension);
	}
	
	public Vector div(double divisor) {
		return this.mult(1/divisor);
	}
	
	public double productoEscalar(Vector vect) throws DimensionNoValidaException {
		
		if (this.dimension() != vect.dimension())
			throw new DimensionNoValidaException("Intentado multiplicar " + this + " · " + vect);
		
		double producto=0;
		for (int i=0;i<this.dimension();i++) 
			producto += this.get(i)*vect.get(i);
		
		return producto;
	}
	
	public double cosAngulo(Vector vect) throws DimensionNoValidaException {
		return this.productoEscalar(vect) / (this.norma() * vect.norma());
	}
	
	public static Vector getRandomVector(double limitex, double limitey) {
		return new Vector(Math.random() * limitex,Math.random() * limitey);
	}
}