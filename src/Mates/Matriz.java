package Mates;

import Exception.DimensionNoValidaException;

public class Matriz {

	private double matriz[][];
	private int filas;
	private int columnas;

	public Matriz(int filas, int columnas) {
		this.filas = filas;
		this.columnas = columnas;
		matriz = new double[filas][columnas];
	}

	public Matriz(int filas, int columnas, double datos[][]) {
		this.filas = filas;
		this.columnas = columnas;
		this.matriz = datos;
	}

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

	public int getFilas() {
		return filas;
	}

	public int getColumnas() {
		return columnas;
	}

	public double get(int i, int j) throws IndexOutOfBoundsException {
		return matriz[i][j];
	}

	public Vector getFila(int indice) throws IndexOutOfBoundsException {
		double datos[] = new double[this.getColumnas()];
		for (int j = 0; j < this.getColumnas(); j++) {
			datos[j] = this.matriz[indice][j];
		}

		return new Vector(datos);
	}

	public void setFila(int indice, Vector fila) throws Exception {
		setFila(indice, fila.getDatos());
	}

	public void setFila(int indice, double[] fila) throws Exception {
		if (fila.length != this.getColumnas())
			throw new DimensionNoValidaException();

		for (int j = 0; j < fila.length; j++)
			this.set(indice, j, fila[j]);
	}

	public void setColumna(int indice, Vector columna) throws Exception {
		setColumna(indice, columna.getDatos());
	}

	public void setColumna(int indice, double[] columna) throws Exception {
		if (columna.length != this.getFilas())
			throw new DimensionNoValidaException("Se intento setear a " + columna + "la columna " + indice + " de la matriz \n" + this);
		
		for (int i=0;i< columna.length; i++)
			this.set(i, indice, columna[i]);
	}

	public Vector getColumna(int indice) throws IndexOutOfBoundsException {
		double datos[] = new double[this.getFilas()];
		for (int i = 0; i < this.getFilas(); i++) {
			datos[i] = this.matriz[i][indice];
		}

		return new Vector(datos);
	}

	public void set(int i, int j, double nuevoValor)
			throws IndexOutOfBoundsException {
		matriz[i][j] = nuevoValor;
	}

	public double[][] getDatos() {
		return matriz;
	}

	public int[][] getDatosInt() {
		int datos[][] = new int[this.getFilas()][this.getColumnas()];
		for (int i = 0; i < this.getFilas(); i++)
			for (int j = 0; j < this.getColumnas(); j++)
				datos[i][j] = (int) this.get(i, j);
		return datos;
	}

	public Matriz suma(Matriz m) throws DimensionNoValidaException {
		if (this.getColumnas() != m.getColumnas()
				|| this.getFilas() != m.getFilas())
			throw new DimensionNoValidaException(
					"Se intentó sumar la matriz \n" + this + "con \n" + m);

		double nuevaMatriz[][] = new double[this.getFilas()][this.getColumnas()];
		for (int i = 0; i < this.getFilas(); i++)
			for (int j = 0; j < this.getColumnas(); j++)
				nuevaMatriz[i][j] = this.get(i, j) + m.get(i, j);

		return new Matriz(this.getFilas(), this.getColumnas(), nuevaMatriz);
	}

	public Matriz por(double n) {
		double nuevaMatriz[][] = new double[filas][columnas];

		for (int i = 0; i < this.getFilas(); i++)
			for (int j = 0; j < this.getColumnas(); j++)
				nuevaMatriz[i][j] = n * this.matriz[i][j];
		return new Matriz(this.getFilas(), this.getColumnas(), nuevaMatriz);
	}

	public Vector por(Vector v) throws DimensionNoValidaException {
		if (v.dimension() != this.getColumnas())
			throw new DimensionNoValidaException(
					" No se puede multiplicar A= \n " + this + " \n por v= "
							+ v);

		double resultado[] = new double[this.getFilas()];

		for (int i = 0; i < this.getFilas(); i++)
			for (int j = 0; j < this.getColumnas(); j++)
				resultado[i] += v.get(j) * this.matriz[j][i];

		return new Vector(resultado);
	}

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

		return new Matriz(this.getFilas(), m.getColumnas(), matrizResultado);
	}

	public Matriz entre(double n) throws ArithmeticException {
		if (n == 0)
			throw new ArithmeticException("Division entre 0");

		return this.por(1 / n);
	}

	public String toString() {
		String temp = "";
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++)
				temp += matriz[i][j] + " ";
			temp += "\n";
		}
		return temp;
	}

	public static Matriz getMatriz2x2Giro(double angulo) {
		while (angulo > 2 * Math.PI)
			angulo -= 2 * Math.PI;
		try {
			return new Matriz(2, 2, Math.cos(angulo), Math.sin(angulo),
					-Math.sin(angulo), Math.cos(angulo));
		} catch (DimensionNoValidaException e) {
			e.printStackTrace();
			return null;
		}
	}

}