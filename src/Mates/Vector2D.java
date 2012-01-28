package Mates;

import Exception.DimensionNoValidaException;

public class Vector2D extends Vector{
	
	
	public Vector2D (double ... componentes) throws DimensionNoValidaException {
		super(componentes);
		if (componentes.length != 2)
			throw new DimensionNoValidaException("Un Vector2D tiene que tener dos componentes");	
	}
	
	public Vector2D(Vector vector) throws DimensionNoValidaException {
		super(vector.getDatos());
		if (vector.getDimension() != 2)
			throw new DimensionNoValidaException("No se puede crear un Vector2D a partir de un Vector que no tiene 2 componentes");
	}
	
	public Vector2D(Vector2D origen, Vector2D destino) {
		super(origen,destino);
	}

	/**
	 * Devuelve la primera componente de un vector (componente X)
	 * 
	 * @return Primera componente de un vector
	*/
	public double getX() {
		return this.get(0);	
	}
	
	/**
	 * Establece un nuevo valor para la primera componente de un vector (componente X)
	 * 
	 * @param nuevoValor Nuevo valor para la primera componente
	 */	
	public void setX(double nuevoValor) {
		set(0,nuevoValor);
	}
	
	/**
	 * Devuelve la segunda componente de un vector (componente Y)
	 * 
	 * @return Primera componente de un vector
	 */
	public double getY() {
		return this.get(1);	
	}
	
	/**
	 * Establece un nuevo valor para la segunda componente de un vector (componente Y)
	 * 
	 * @param nuevoValor Nuevo valor para la segunda componente
	 */	
	public void setY(double nuevoValor) {
		set(1,nuevoValor);
	}
	
	
	/**
	 * Cmabia el signo de la componente X (la primera) del vector 
	 */
	public void invertirX() {
		invertirComponente(0); 
	}
	
	/**
	 * Cmabia el signo de la componente Y (la segunda) del vector 
	 */
	public void invertirY() {
		invertirComponente(1);
	}
	
	
	
	/**
	 *  Devuelve la suma de dos vectores
	 * @param v vector a sumar
	 * @return Suma de los dos vectores
	 * @throws DimensionNoValidaException si las dimensiones de los dos vectores no coinciden
	 */
	public Vector2D suma(Vector2D v) {	
		double[] vect = new double[2];
		for (int i=0;i<this.getDimension();i++)
			vect[i]=this.get(i)+v.get(i);
		
		return new Vector2D(vect);
	}
	
	/**
	 *  Devuelve la suma de dos vectores
	 * @param v vector a restar
	 * @return Resta de los vectores
	 * @throws DimensionNoValidaException si las dimensiones de los dos vectores no coinciden
	 */
	@Override
	public Vector2D resta(Vector v) {	
		double[] vect = new double[2];
		for (int i=0;i<this.getDimension();i++)
			vect[i]=this.get(i)-v.get(i);
		
		return new Vector2D(vect);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (this.getDimension() == 0)
			return "Vector ()";
		
		String cadena="Vector2D (";
		for (int i=0;i<this.getDimension()-1;i++)
			cadena += this.get(i) +",";
		
		cadena += this.get(this.getDimension()-1) + ")";
		return cadena;
	}

	/**
	 * Devuelve el vector normalizado
	 * 
	 * @return nuevo Vector con la misma direccion y módulo 1.
	 */
	@Override
	public Vector2D normalizar() {
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
	public Vector2D proyectarSobre(Vector2D u) throws ArithmeticException {
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
	@Override
	public Vector2D mult(double multiplicador) {
		double[] vect = new double[2];
		for (int i=0;i<this.getDimension();i++)
			vect[i]=this.get(i)*multiplicador;
		
		return new Vector2D(vect);
	}
	
	/**
	 * Devuelve el vector dividido por un escalar
	 * 
	 * @param divisor Numero por el que divides el vector
	 * @return nuevo Vector dividido
	 * @throws ArithmeticException Si se intenta dividir por 0
	 */
	@Override
	public Vector2D div(double divisor) throws ArithmeticException {
		if (divisor==0)
			throw new ArithmeticException("Division entre 0");
		return this.mult(1/divisor);
	}
}