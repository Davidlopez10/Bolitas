package Mates;

import Exception.DimensionNoValidaException;

/**
 * Esta clase implementa matrices y sus operaciones básicas, y algunas operaciones con vectores
 * 
 * @author Jose Diaz
 *
 */
public class Matriz {

	private double matriz[][];
	private int filas;
	private int columnas;

	/**
	 * Construye la matriz nula con dimensiones dadas
	 * 
	 * @param filas número de filas de la matriz
	 * @param columnas número de columnas de la matriz
	 */
	public Matriz(int filas, int columnas) {
		this.filas = filas;
		this.columnas = columnas;
		matriz = new double[filas][columnas];
	}

	/**
	 * Construye una matriz a partir de sus datos
	 * 
	 * @param Datos con los que construir la matriz (tendrá las mismas dimensiones que este argumento)
	 */
	public Matriz(double datos[][]) {
		this.filas = datos.length;
		if (this.filas == 0)
			this.columnas=0;
		else
			this.columnas = datos[0].length;
		this.matriz = datos;
	}

	/**
	 * Cosntruye una matriz a partir de sus datos verificando que tenga la dimension adecuada
	 * 
	 * @param filas numero de filas de la matriz 
	 * @param columnas numero de columnas de la matriz
	 * @param datos Datos de la matriz, debe tener las dimensiones especificadas antse
	 * @throws DimensionNoValidaException Si las dimensiones de "datos" no coinciden con las especificadas
	 */
	public Matriz(int filas, int columnas, double... datos)
			throws DimensionNoValidaException {

		this.filas = filas;
		this.columnas = columnas;

		int i = 0;
		int j = 0;
		int totalValores = 0;

		matriz = new double[filas][columnas];

		for (double n : datos) {
			matriz[i][j] = n;

			totalValores++;
			if (j < columnas - 1)
				j++;
			else {
				j = 0;
				i++;
			}

			if (totalValores > filas * columnas)
				throw new DimensionNoValidaException(
						"No se esperaban tantos datos. Filas = " + filas
								+ " Columnas = " + columnas
								+ " numero no esperado de datos: "
								+ totalValores);
		}
	}

	/**
	 * Devuelve numero de filas
	 * 
	 * @return número de filas de la matriz
	 */
	public int getFilas() {
		return filas;
	}

	/**
	 * Devuelve el número de columnas
	 * 
	 * @return número de columnas de la matriz
	 */
	public int getColumnas() {
		return columnas;
	}

	/**
	 * Devuelve el valor de una componente de la matriz
	 * 
	 * @param i indice i (fila) de la componente
	 * @param j indice j (columna) de la componente
	 * @return Valor de dicha componente 
	 * @throws IndexOutOfBoundsException Si no existe esa componente
	 */
	public double get(int i, int j) throws IndexOutOfBoundsException {
		return matriz[i][j];
	}

	/**
	 * Devuelve la fila especficiada de una matriz
	 * 
	 * @param indice numero de la fila
	 * @return Vector con las componentes de la primera fila
	 * @throws IndexOutOfBoundsException Si la matriz no tiene tantas filas o se especifica un indice negativo
	 */
	public Vector getFila(int indice) throws IndexOutOfBoundsException {
		double datos[] = new double[this.getColumnas()];
		for (int j = 0; j < this.getColumnas(); j++) {
			datos[j] = this.matriz[indice][j];
		}

		return new Vector(datos);
	}

	/**
	 * Setea una nueva fila de una matriz
	 * 
	 * @param indice indice de la fila a cambiar
	 * @param fila Vector que ocupara la nueva fila
	 * @throws DimensionNoValidaException si la dimension del vector no coincide con la dimension que tienen los vectores fila de esa matriz
	 * @throws IndexOutOfBoundsException si la matriz no tiene tantas filas 
	 */
	public void setFila(int indice, Vector fila) throws RuntimeException {
		setFila(indice, fila.getDatos());
	}

	/**
	 * Setea una nueva fila de una matriz
	 * 
	 * @param indice indice de la fila a cambiar
	 * @param fila Array que ocupara la nueva fila
	 * @throws DimensionNoValidaException si la dimension del vector no coincide con la dimension que tienen los vectores fila de esa matriz
	 * @throws IndexOutOfBoundsException si la matriz no tiene tantas filas 
	 */
	public void setFila(int indice, double[] fila) throws RuntimeException {
		if (fila.length != this.getColumnas())
			throw new DimensionNoValidaException();

		for (int j = 0; j < fila.length; j++)
			this.set(indice, j, fila[j]);
	}

	/**
	 * Devuelve la columna especficiada de una matriz
	 * 
	 * @param indice numero de la columna
	 * @return Vector con las componentes de la columna especificada 
	 * @throws IndexOutOfBoundsException Si la matriz no tiene tantas filas o se especifica un indice negativo
	 */
	public Vector getColumna(int indice) throws IndexOutOfBoundsException {
		double datos[] = new double[this.getFilas()];
		for (int i = 0; i < this.getFilas(); i++) {
			datos[i] = this.matriz[i][indice];
		}

		return new Vector(datos);
	}
	
	/**
	 * Setea una nueva columna de una matriz
	 * 
	 * @param indice indice de la columna a cambiar
	 * @param columna Vector que ocupara la nueva columna
	 * @throws DimensionNoValidaException si la dimension del vector no coincide con la dimension que tienen los vectores columna de esa matriz
	 * @throws IndexOutOfBoundsException si la matriz no tiene tantas columnas 
	 */
	public void setColumna(int indice, Vector columna) throws RuntimeException {
		setColumna(indice, columna.getDatos());
	}

	/**
	 * Setea una nueva columna de una matriz
	 * 
	 * @param indice indice de la columna a cambiar
	 * @param columna Array que ocupara la nueva columna
	 * @throws DimensionNoValidaException si la dimension del vector no coincide con la dimension que tienen los vectores columna de esa matriz
	 * @throws IndexOutOfBoundsException si la matriz no tiene tantas columnas 
	 */
	public void setColumna(int indice, double[] columna) throws RuntimeException {
		if (columna.length != this.getFilas())
			throw new DimensionNoValidaException("Se intento setear a " + columna + "la columna " + indice + " de la matriz \n" + this);
		
		for (int i=0;i< columna.length; i++)
			this.set(i, indice, columna[i]);
	}
	
	/**
	 * Establece un nuevo valor para una componente de la matirz
	 * 
	 * @param i fila de la componente a establecer
	 * @param j columna de la componente a establecer
	 * @param nuevoValor nuevo valor para dicha componente
	 * @throws IndexOutOfBoundsException Si la matriz no tiene dicha componente
	 */
	public void set(int i, int j, double nuevoValor)
			throws IndexOutOfBoundsException {
		matriz[i][j] = nuevoValor;
	}

	/**
	 * Devuelve los datos de la matriz en forma de un array nativo de Java
	 * 
	 * @return array de arrays con los datos
	 */
	public double[][] getDatos() {
		return matriz;
	}
	
	/**
	 * Devuelve los datos de la matriz en forma de un array de enteros nativo de Java 
	 * 
	 * @return array de arrays de enteros con los datos
	 */
	public int[][] getDatosInt() {
		int datos[][] = new int[this.getFilas()][this.getColumnas()];
		for (int i = 0; i < this.getFilas(); i++)
			for (int j = 0; j < this.getColumnas(); j++)
				datos[i][j] = (int) this.get(i, j);
		return datos;
	}

	/**
	 * Devuelve una nueva matriz suma de ésta con la dada.
	 * 
	 * @param m Matriz que habrá que sumar
	 * @return Nueva matriz suma
	 * @throws DimensionNoValidaException Si la dimension de las matrices no coinciden
	 */
	public Matriz suma(Matriz m) throws DimensionNoValidaException {
		if (this.getColumnas() != m.getColumnas()
				|| this.getFilas() != m.getFilas())
			throw new DimensionNoValidaException(
					"Se intentó sumar la matriz \n" + this + "con \n" + m);

		double nuevaMatriz[][] = new double[this.getFilas()][this.getColumnas()];
		for (int i = 0; i < this.getFilas(); i++)
			for (int j = 0; j < this.getColumnas(); j++)
				nuevaMatriz[i][j] = this.get(i, j) + m.get(i, j);

		return new Matriz(nuevaMatriz);
	}

	/**
	 * Multiplica la matriz por un numero
	 * 
	 * @param n numero por el que multiplicamos la matriz
	 * @return Nueva matriz multiplicada
	 */
	public Matriz por(double n) {
		double nuevaMatriz[][] = new double[filas][columnas];

		for (int i = 0; i < this.getFilas(); i++)
			for (int j = 0; j < this.getColumnas(); j++)
				nuevaMatriz[i][j] = n * this.matriz[i][j];
		return new Matriz(nuevaMatriz);
	}

	/**
	 * Devuelve un nuevo vector producto de la matriz con el vector dado.
	 * 
	 * @param v Vector por el que hay que multiplicar la matriz
	 * @return Nuevo vector producto
	 * @throws DimensionNoValidaException Si las dimensiones no son adecuadas
	 */
	public Vector por(Vector v) throws DimensionNoValidaException {
		if (v.getDimension() != this.getColumnas())
			throw new DimensionNoValidaException(
					" No se puede multiplicar A= \n " + this + " \n por v= "
							+ v);

		double resultado[] = new double[this.getFilas()];

		for (int i = 0; i < this.getFilas(); i++)
			for (int j = 0; j < this.getColumnas(); j++)
				resultado[i] += v.get(j) * this.matriz[j][i];

		return new Vector(resultado);
	}


	/**
	 * Devuelve una nueva matriz producto de ésta con la dada.
	 * 
	 * @param m Matriz que por la que se ha de multiplicar
	 * @return Nueva matriz multiplicada
	 * @throws DimensionNoValidaException Si las dimensiones no son adecuadas
	 */
	public Matriz por(Matriz m) throws DimensionNoValidaException {
		if (this.getColumnas() != m.getFilas())
			throw new DimensionNoValidaException("Intentando multiplicar \n"
					+ this + " por \n" + m);

		double[][] matrizResultado = new double[this.getFilas()][m
				.getColumnas()];
		for (int i = 0; i < this.getFilas(); i++)
			for (int j = 0; j < m.getColumnas(); j++)
				for (int k = 0; k < this.getColumnas(); k++)
					matrizResultado[i][j] += this.get(i, k) * m.get(k, j);

		return new Matriz(matrizResultado);
	}

	/**
	 * Divide una matriz por un numero
	 * 
	 * @param n numero por el que dividir la matriz
	 * @return nueva matriz dividida
	 * @throws ArithmeticException Si se intenta dividir por cero.
	 */
	public Matriz entre(double n) throws ArithmeticException {
		if (n == 0)
			throw new ArithmeticException("Division entre 0");

		return this.por(1 / n);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String temp = "";
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++)
				temp += matriz[i][j] + " ";
			temp += "\n";
		}
		return temp;
	}

	/**
	 * Devuelve la matriz 2x2 de giro:
	 * (  cos(alpha), sin(alpha) )
	 * ( -sin(alpha), cos(alpha) )
	 * 
	 * @param angulo Angulo de la matriz de giro
	 * @return Nueva matriz de giro
	 */
	public static Matriz getMatriz2x2Giro(double angulo) {
		while (angulo > 2 * Math.PI)
			angulo -= 2 * Math.PI;
		
		return new Matriz(2, 2, Math.cos(angulo), Math.sin(angulo),
				-Math.sin(angulo), Math.cos(angulo));
	}

}