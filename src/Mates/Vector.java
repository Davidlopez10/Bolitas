package Mates;

import Exception.DimensionNoValidaException;

/**
 * Esta clase implementa Vectores de n componentes con sus operaciones matem�ticas b�sicas.
 * 
 * @author Jose D�az
 */
public class Vector {
	
	private int dimension;
	private double[] vector;
	
	/**
	 * Constructor por defecto
	 * 
	 * @param datos Vector de numeros que formar�n el vector. 
	 */
	public Vector(double ... datos) {
		vector = new double[datos.length];
		int i=0;
		for (double n : datos) {
			vector[i] = n;
			i++;
		}
		dimension=datos.length;
	}
	
	/**
	 * Construye un vector a partir de un punto origen y un punto destino.
	 * 
	 * @param p1 Origen
	 * @param p2 Destino
	 * 
	 * @throws DimensionNoValidaException Si Origen y Destino tienen dimensiones disintas
	 */
	public Vector(Vector p1, Vector p2) throws DimensionNoValidaException {
		Vector nuevoVector = p2.resta(p1);
		dimension = nuevoVector.getDimension();
		vector = nuevoVector.getDatos();
	}
	
	/**
	 * Devuelve la dimension (el n�mero de componentes) de un vector
	 * 
	 * @return Dimension del vector
	 */
	public int getDimension() {
		return this.dimension;
	}
	
	/**
	 * Devuelve una componente del vector
	 * 
	 * @param index Indice de la componente a devolver
	 * @return Valor de la componente solicitada
	 * 
	 * @throws IndexOutOfBoundsException
	 */
	public double get(int index) throws IndexOutOfBoundsException {
		return vector[index];
	}
	
	/**
	 * Devuelve un array con los datos del vector
	 * 
	 * @return array con los datos almacenados en el vector en el mismo orden
	 */
	public double[] getDatos() {
		return this.vector;
	}
	
	/**
	 * Devuelve un array con los datos del vector convertidos a entero
	 * 
	 * @return array con los datos almacenados en el vector convertidos a enteros en el mismo orden
	 */
	public int[] getDatosInt() {
		int[] vectorEntero = new int[this.getDimension()];
		for (int i=0;i<this.getDimension();i++)
			vectorEntero[i] = (int) this.vector[i];
		return vectorEntero;
	}
	
	/**
	 * Establece un nuevo valor para una componente del vector
	 * 
	 * @param index Indice de la componente del vector a establecer.
	 * @param nuevoValor Nuevo valor para dicha componente
	 * 
	 * @throws IndexOutOfBoundsException Si el vector no tiene componente de dicho �ndice.
	 */
	public void set(int index, double nuevoValor) throws IndexOutOfBoundsException {
		this.vector[index]=nuevoValor;
	}
	
	/**
	 * Cambia el signo de una componente del vector
	 * 
	 * @param index Indice de la componente a invertir
	 * 
	 * @throws IndexOutOfBoundsException Si no hay tantas componentes
	 */
	public void invertirComponente(int index) throws IndexOutOfBoundsException {
		vector[index] = -vector[index];
	}
	
	/**
	 * Invierte todas las componentes de un vector
	 */
	public void invertir() {
		for (int i=0; i < this.dimension; i++) {
			vector[i] = -vector[i];
		}
	}
	
	/**
	 * Compara un vector con el vector 0
	 * 
	 * @return TRUE sii el vector es el vector nulo.
	 */
	public boolean esCero() {
		for (int i=0; i < this.getDimension(); i++)
			if (vector[i] != 0)
				return false;
		return true;
	}
	
	
	/**
	 *  Devuelve la suma de dos vectores
	 * @param v vector a sumar
	 * @return Suma de los dos vectores
	 * @throws DimensionNoValidaException si las dimensiones de los dos vectores no coinciden
	 */
	public Vector suma(Vector v) throws DimensionNoValidaException {
		if (this.getDimension() != v.getDimension())
			throw new DimensionNoValidaException("Intentado sumar " + v + " a " + this );
	
		double[] vect = new double[this.dimension];
		for (int i=0;i<this.getDimension();i++)
			vect[i]=this.get(i)+v.get(i);
		
		return new Vector(vect);
	}
	
	/**
	 *  Devuelve la suma de dos vectores
	 * @param v vector a restar
	 * @return Resta de los vectores
	 * @throws DimensionNoValidaException si las dimensiones de los dos vectores no coinciden
	 */
	public Vector resta(Vector v) throws DimensionNoValidaException {
		if (this.getDimension() != v.getDimension())
			throw new DimensionNoValidaException("Intentado restar " + v + " a " + this );
	
		double[] vect = new double[this.dimension];
		for (int i=0;i<this.getDimension();i++)
			vect[i]=this.get(i)-v.get(i);
		
		return new Vector(vect);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (this.getDimension() == 0)
			return "Vector ()";
		
		String cadena="Vector (";
		for (int i=0;i<this.getDimension()-1;i++)
			cadena += this.get(i) +",";
		
		cadena += this.get(this.getDimension()-1) + ")";
		return cadena;
	}
	
	/**
	 * Devuelve la distancia can�nica hasta un punto 
	 * @param Punto al que hallar la distancia
	 * @return distancia entre los puntos
	 * @throws DimensionNoValidaException Si el punto no tiene la misma dimension que el del objeto desde el que se invoca
	 */
	public double distanciaA(Vector v) throws DimensionNoValidaException {
		if (this.getDimension() != v.getDimension())
			throw new DimensionNoValidaException("Intentando calcular distancia de " + v + "a " + this);
		
		double distancia=0;
		for (int i=0;i<this.getDimension();i++)
			distancia += Math.pow(this.get(i) - v.get(i), 2);
		return Math.sqrt(distancia);
	}
	
	/**
	 * Devuelve la norma o m�dulo del vector
	 * 
	 * @return norma o m�dulo del vector
	 */
	public double norma() {
		double norma=0;
		for (int i=0;i<this.getDimension();i++)
			norma += Math.pow(this.get(i), 2);
		return Math.sqrt(norma);
	}
	
	/**
	 * Devuelve el vector normalizado
	 * 
	 * @return nuevo Vector con la misma direccion y m�dulo 1.
	 */
	public Vector normalizar() {
		try {
			return this.div(this.norma());
		} catch (ArithmeticException e) {
			// El vector 0 normalizado lo devolveremos como 0:
			if (this.esCero())
					return this;
			else {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/**
	 * Proyecta el vector sobre otro dado.
	 * 
	 * @param u sobre el que proyectar
	 * @return nuevo Vector proyectado
	 * @throws ArithmeticException Si el vector sobre el que proyectar es nulo.
	 */
	public Vector proyectarSobre(Vector u) throws ArithmeticException {
		if (u.esCero())
			throw new ArithmeticException("proyeccion sobre (0,...,0)");
		return u.normalizar().mult(this.productoEscalar(u)/u.norma());
	}
	
	/**
	 * Devuelve el vector multiplicado por un escalar
	 * 
	 * @param multiplicador Numero por el que multiplicas el vector
	 * @return nuevo Vector multiplicado
	 */
	public Vector mult(double multiplicador) {
		double[] vect = new double[this.dimension];
		for (int i=0;i<this.getDimension();i++)
			vect[i]=this.get(i)*multiplicador;
		
		return new Vector(vect);
	}
	
	/**
	 * Devuelve el vector dividido por un escalar
	 * 
	 * @param divisor Numero por el que divides el vector
	 * @return nuevo Vector dividido
	 * @throws ArithmeticException Si se intenta dividir por 0
	 */
	public Vector div(double divisor) throws ArithmeticException {
		if (divisor==0)
			throw new ArithmeticException("Division entre 0");
		return this.mult(1/divisor);
	}
	
	/**
	 * Devuelve el producto escalar con otro vector
	 * 
	 * @param vect Otro vector con el que hacer el producto escalar
	 * @return producto escalar entre los dos vectores
	 * @throws DimensionNoValidaException Si los vectores no tienen la misma dimension
	 */
	public double productoEscalar(Vector vect) throws DimensionNoValidaException {
		if (this.getDimension() != vect.getDimension())
			throw new DimensionNoValidaException("Intentado multiplicar " + this + " � " + vect);
		
		double producto=0;
		for (int i=0;i<this.getDimension();i++) 
			producto += this.get(i)*vect.get(i);
		
		return producto;
	}
	
	/**
	 * Devuelve el �ngulo formado entre dos vectores
	 * 
	 * @param vect Vector con el que formar el �ngulo
	 * @return el coseno del angulo entre los dos vectores
	 * @throws DimensionNoValidaException Si los vectores no tienen la misma dimension
	 * @throws ArithmeticException Si alguno de los dos vectores es el nulo
	 */
	public double cosAngulo(Vector vect) throws RuntimeException {
		return this.productoEscalar(vect) / (this.norma() * vect.norma());
	}
	
	/**
	 * Devuelve un vector con componentes aleatorias entre los limites que se especifican
	 * 
	 * @param dimension n�mero de componentes del vector
	 * @param limiteinf l�mite superior para las componentes
	 * @param limitesup l�mite inferior para las componentes
	 * @return nuevo Vector aleatorio.
	 */
	public static Vector getRandomVector(int dimension, double limiteinf, double limitesup) {
		double datos[] = new double[dimension];
		for (int i=0;i<dimension;i++)
			datos[i] = Math.random()*(limitesup-limiteinf)-limiteinf;
		
		return new Vector(datos);
	}
}