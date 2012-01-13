package Mates;

import Exception.DimensionNoValidaException;


// TODO: Agregar métodos entre vectores y matrices
public class Matriz {
	
	private double matriz[][]; 
	private int filas;
	private int columnas;
	
	public Matriz(int filas, int columnas) {
		this.filas=filas;
		this.columnas=columnas;
		matriz = new double[filas][columnas];
	}
	
	public Matriz(int filas, int columnas, double datos[][]) {
		this.filas=filas;
		this.columnas=columnas;
		this.matriz=datos;
	}
	
	public Matriz(int filas, int columnas, double ... datos) throws DimensionNoValidaException {
		
		this.filas = filas;
		this.columnas = columnas;
		
		int i=0;
		int j=0;
		int totalValores=0;
		
		matriz = new double[filas][columnas];
		
		for (double n : datos) {
			matriz[i][j]=n;
			
			totalValores++;
			if (j<columnas-1)
				j++;
			else {
				j=0;
				i++;
			}
			
			if (totalValores > filas * columnas)
				throw new DimensionNoValidaException("No se esperaban tantos datos. Filas = " + filas + " Columnas = " + columnas + " numero no esperado de datos: " + totalValores);	
		}
	}
	
	public int getFilas() {
		return filas;
	}
	
	public int getColumnas() {
		return columnas;
	}
	
	public double get(int i, int j) {
		return matriz[i][j];
	}
	
	public Vector getFila(int indice) throws IndexOutOfBoundsException {
		double datos[] = new double[this.getColumnas()];
		for (int j=0;j<this.getColumnas();j++) {
			datos[j] = this.matriz[indice][j];
		}
		
		return new Vector(datos,this.getColumnas());
	}
	
	public Vector getColumna(int indice) throws IndexOutOfBoundsException{
		double datos[] = new double[this.getFilas()];
		for (int i=0;i<this.getFilas();i++) {
			datos[i] = this.matriz[i][indice];
		}
		
		return new Vector(datos,this.getFilas());
		
	}
	
	public void set(int i,int j,int nuevoValor) throws IndexOutOfBoundsException {
		matriz[i][j]=nuevoValor;
	}
	
	public double[][] getMatrizNumeros() {
		return matriz;
	}
	
	public Matriz suma(Matriz m) throws DimensionNoValidaException {
		if (this.getColumnas() != m.getColumnas() ||
			this.getFilas() != m.getFilas())
				throw new DimensionNoValidaException("Se intentó sumar la matriz \n" + this + "con \n" + m);
		
		double nuevaMatriz[][] = new double[this.getFilas()][this.getColumnas()];
		for(int i=0;i<this.getFilas();i++)
			for (int j=0;j<this.getColumnas();j++)
				nuevaMatriz[i][j] = this.get(i, j) + m.get(i, j);
		
		return new Matriz(this.getFilas(),this.getColumnas(),nuevaMatriz);
	}
	
	public Matriz por(double n) {
		double nuevaMatriz[][] = new double[filas][columnas];
		
		for (int i=0;i<this.getFilas();i++)
			for (int j=0;j<this.getColumnas();j++)
				nuevaMatriz[i][j] = n * this.matriz[i][j];
		return new Matriz(this.getFilas(),this.getColumnas(),nuevaMatriz);
	}
	
	public Vector por(Vector v) throws DimensionNoValidaException {
		if (v.dimension() != this.getColumnas())
			throw new DimensionNoValidaException(" No se puede multiplicar A= \n " + this +  " \n por v= " + v);
		
		double resultado[] = new double[this.getFilas()];
		
		for (int i=0;i<this.getFilas();i++)
			for (int j=0;j<this.getColumnas();j++)
				resultado[i] += v.get(j)*this.matriz[j][i];
		
		return new Vector(resultado,this.getFilas());
	}
	
	public Matriz por(Matriz m) throws DimensionNoValidaException {
		if (this.getColumnas() != m.getFilas() )
			throw new DimensionNoValidaException("Intentando multiplicar \n" + this + " por \n" + m);
		
		double[][] matrizResultado = new double[this.getFilas()][m.getColumnas()];
		double a=0;
		double b=0;
		for (int i=0;i<this.getFilas();i++) 
			for (int j=0;j<m.getColumnas();j++) 
				for (int k=0;k<this.getColumnas();k++) {
					/*a = this.get(i,k);
					b = m.get(k, j);
					matrizResultado[i][j] += a*b;*/
					matrizResultado[i][j] += this.get(i, k)*m.get(k, j);
				}
				
			
		
		return new Matriz(this.getFilas(),m.getColumnas(),matrizResultado);
	}
	
	public Matriz entre(double n) {
		return this.por(1/n);
	}
	public String toString() {
		String temp="";
		for (int i=0;i<filas;i++) {
			for (int j=0;j<columnas;j++) 
				temp += matriz[i][j] + " ";
			temp += "\n";
		}
		return temp;
	}
}