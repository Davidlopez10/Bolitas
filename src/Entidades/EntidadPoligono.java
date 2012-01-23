package Entidades;

import java.awt.Color;
import java.awt.Graphics;

import Exception.DimensionNoValidaException;
import Mates.Matriz;
import Mates.Vector;

public class EntidadPoligono extends Entidad {

	// Almacena la posicion de los vértices entre ellos (sin aplicar giros ni
	// posicion del polígono)
	private Matriz verticesRelativos;

	// Almacena la posicion absoulta de los vértices (sobre la pantalla)
	private Matriz verticesAbsolutos;

	private int n;
	
	private boolean concavo;

	protected EntidadPoligono(Matriz puntos, Vector posicion, Vector velocidad,
			Vector aceleracion, double masa, Color color)
			throws DimensionNoValidaException {
		super(posicion, velocidad, aceleracion, masa, color);
		this.n = puntos.getColumnas();
		this.verticesRelativos = puntos;
		this.refrescarVertices();
		this.concavo = EntidadPoligono.esConcavo(this);
	}

	public EntidadPoligono(Matriz puntos, Vector posicion, Vector velocidad,
			Vector aceleracion, double posicionAngular,
			double velocidadAngular, double aceleracionAngular, double masa,
			Color color) throws DimensionNoValidaException {
		super(posicion, velocidad, aceleracion, posicionAngular,
				velocidadAngular, aceleracionAngular, masa, color);
		this.n = puntos.getColumnas();
		this.verticesRelativos = puntos;
		this.refrescarVertices();
		this.concavo = EntidadPoligono.esConcavo(this);
	}

	public EntidadPoligono(Vector posicion, Vector velocidad, Vector aceleracion,
			double masa, Color color, double[]... puntos)
			throws DimensionNoValidaException {
		super(posicion, velocidad, aceleracion, masa, color);
		if (puntos.length != 2)
			throw new DimensionNoValidaException(
					"Los puntos de un polígono han de tener 2 coordenads");
		if (puntos[0].length != puntos[1].length)
			throw new DimensionNoValidaException(
					"Se han de especificar las mismas coordenads X que Y al construir un poligono");
		this.verticesRelativos = new Matriz(puntos.length, puntos[0].length,
				puntos);
		this.n = puntos[0].length;
		this.refrescarVertices();
		this.concavo = EntidadPoligono.esConcavo(this);
	}

	public EntidadPoligono(Vector posicion, Vector velocidad, Vector aceleracion,
			double posicionAngular, double velocidadAngular,
			double aceleracionAngular, double masa, Color color,
			double[]... puntos) throws DimensionNoValidaException {
		super(posicion, velocidad, aceleracion, posicionAngular,
				velocidadAngular, aceleracionAngular, masa, color);
		if (puntos.length != 2)
			throw new DimensionNoValidaException(
					"Los puntos de un polígono han de tener 2 coordenads");
		if (puntos[0].length != puntos[1].length)
			throw new DimensionNoValidaException(
					"Se han de especificar las mismas coordenads X que Y al construir un poligono");
		this.verticesRelativos = new Matriz(puntos.length, puntos[0].length,
				puntos);
		this.n = puntos[0].length;
		this.refrescarVertices();
		this.concavo = EntidadPoligono.esConcavo(this);
	}

	public int getNumeroLados() {
		return this.n;
	}

	public int getNumeroVertices() {
		return this.n;
	}

	protected void setN(int nuevoN) {
		this.n = nuevoN;
	}

	public Matriz getVerticesRelativos() {
		return this.verticesRelativos;
	}
	
	public Matriz getVerticesAbsolutos() {
		return this.verticesAbsolutos;
	}

	protected void setVertices(Matriz puntos) throws DimensionNoValidaException {
		if (puntos.getFilas() != 2) 
			throw new DimensionNoValidaException("Las coordenadas de los puntos de un poligono han de tener 2");
		this.verticesAbsolutos = puntos;
		this.n = puntos.getColumnas();
		this.refrescarVertices();
	}
	
	public Vector getVerticeRelativo(int i) throws IndexOutOfBoundsException {
		return this.verticesRelativos.getColumna(i);		
	}
	
	public Vector getVerticeAbsoluto(int i) throws IndexOutOfBoundsException {
		return this.verticesAbsolutos.getColumna(i);
	}
	

	@Override
	public void calcularNuevasPosiciones(double dt) {
		super.calcularNuevasPosiciones(dt);
		this.refrescarVertices();
	}

	public void refrescarVertices() {
		try {
			Matriz matrizGiro = Matriz.getMatriz2x2Giro(this
					.getPosicionAngular());
			verticesAbsolutos = matrizGiro.por(verticesRelativos);
			Matriz matrizPosicion = new Matriz(2, n);
			for (int i = 0; i < n; i++) {
				matrizPosicion.set(0, i, this.getPosicion().getX());
				matrizPosicion.set(1, i, this.getPosicion().getY());
			}

			verticesAbsolutos = verticesAbsolutos.suma(matrizPosicion);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void pintar(Graphics g) {
		g.setColor(this.getColor());
		g.fillPolygon(this.getVerticesAbsolutos().getFila(0).getDatosInt(), this
				.getVerticesAbsolutos().getFila(1).getDatosInt(), n);

	}

	
	public boolean hayColisionX(int limite1, int limite2) {
		Vector coordenadasX = verticesAbsolutos.getFila(0);
		for (int i = 0; i < coordenadasX.dimension(); i++)
			if (coordenadasX.get(i) < limite1 || coordenadasX.get(i) > limite2) {
				return true;
			}
		return false;
	}


	public boolean hayColisionY(int limite1, int limite2) {
		Vector coordenadasY = verticesAbsolutos.getFila(1);
		for (int i = 0; i < coordenadasY.dimension(); i++)
			if (coordenadasY.get(i) < limite1 || coordenadasY.get(i) > limite2) {
				return true;
			}
		return false;

	}

	public void tratarColisionX() {
		try {
			this.getVelocidad().invertirX();
			this.invertirVelocidadAngular();
		} catch (DimensionNoValidaException e) {
			// No debería pasar ya que controlamos los vectores al construir
			e.printStackTrace();
		}
	}

	public void tratarColisionY() {
		try {
			this.getVelocidad().invertirY();
			this.invertirVelocidadAngular();
		} catch (DimensionNoValidaException e) {
			// No debería pasar ya que controlamos los vectores al construir
			e.printStackTrace();
		}
	}
	
	public boolean esConvexo() {
		return !this.concavo;
	}
	
	public boolean esConcavo() {
		return this.concavo;
	}
	
	@Override
	public String toString() {
		return (super.toString() + "\n Poligono de " + n + "lados.");
	}

	public static double areaPoligono(double[]... puntos) {
		if (puntos.length != 2 || (puntos[0].length != puntos[1].length))
			return -1;

		// Calculo por triángulos con la fórmula de Heron
		double area = 0;
		double semiperimetro;
		double ladoA = 0;
		double ladoB = 0;
		double ladoC = 0;

		for (int i = 1; i < puntos[0].length - 1; i++) {
			try {
				ladoA = new Vector(puntos[0][0], puntos[1][0])
						.distanciaA(new Vector(puntos[0][i], puntos[1][i]));
				ladoB = new Vector(puntos[0][0], puntos[1][0])
						.distanciaA(new Vector(puntos[0][i + 1],
								puntos[1][i + 1]));
				ladoC = new Vector(puntos[0][i], puntos[1][i])
						.distanciaA(new Vector(puntos[0][i + 1],
								puntos[0][i + 1]));
			} catch (DimensionNoValidaException e) {
				e.printStackTrace();
			}

			semiperimetro = (ladoA + ladoB + ladoC) / 2;

			area += Math.sqrt(semiperimetro * (ladoA - semiperimetro)
					* (ladoB - semiperimetro) * (ladoC - semiperimetro));
		}

		return area;
	}

	public static double getMasa(double altura, double[]... puntos) {
		return altura * areaPoligono(puntos);
	}

	public static double getMasa(double[]... puntos) {
		return areaPoligono(puntos);
	}
	
	// TODO: Comprobar
	public static boolean esConcavo(EntidadPoligono polig) {
		
		/* Comprobamos los productos escalares de cada pares de vertices. Todos han de ser
		 * o positivos, o negativos. Si alguno tiene signo distinto, el polígono es concavo
		 */
		
		int n = polig.getNumeroVertices();
		
		if (n<4)
			return false;
		
		try {
			double productoEscalar = new Vector(polig.getVerticeAbsoluto(n-1),polig.getVerticeAbsoluto(0)).productoEscalar(new Vector(polig.getVerticeAbsoluto(0),polig.getVerticeAbsoluto(1)));
		
			for (int i=1;i<n-1;i++) {
				
				if (productoEscalar *
					new Vector(polig.getVerticeAbsoluto(i-1),polig.getVerticeAbsoluto(i)).productoEscalar(new Vector(polig.getVerticeAbsoluto(i),polig.getVerticeAbsoluto(i+1)))
					< 0) {
					return true;
				}
			}
		} catch (DimensionNoValidaException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	
	// TODO: Comprobar
	// From http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
	public static boolean contiene(EntidadPoligono polig, Vector punto) {
		int n = polig.getNumeroVertices();
		boolean estaDentro = false;
		for (int i=0, j=n-1; i < n; j = i++) {
			try {
				if (
						(
							(
								polig.getVerticeAbsoluto(i).getY() > punto.getY()
							) 
							!= 
							(
								polig.getVerticeAbsoluto(j).getY() > punto.getY()
							)
						)
						&&
						(
								punto.getX() 
								< 
								(
									(polig.getVerticeAbsoluto(j).getX() - polig.getVerticeAbsoluto(i).getX()) 
									* 
									(punto.getY() - polig.getVerticeAbsoluto(i).getY()) 
									/ 
									(polig.getVerticeAbsoluto(j).getY() - polig.getVerticeAbsoluto(i).getY())
									+ polig.getVerticeAbsoluto(i).getX()
								)
					    )
				) {
				estaDentro = !estaDentro;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return estaDentro;
	}
}