package Entidades;

import java.awt.Color;
import java.awt.Graphics;

import Exception.DimensionNoValidaException;
import Exception.EntidadDesconocidaException;
import Exception.PoligonoComplejoException;
import Mates.Matriz;
import Mates.Vector;
import Mates.Vector2D;

public class EntidadPoligono extends Entidad {

	// Almacena la posicion de los v�rtices entre ellos (sin aplicar giros ni
	// posicion del pol�gono)
	private Matriz verticesRelativos;

	// Almacena la posicion absoulta de los v�rtices (sobre la pantalla)
	private Matriz verticesAbsolutos;

	private int n;
	
	private boolean concavo;

	/**
	 * Constructor de Pol�gos b�sico (se omiten caracter�sticas de giro para la entidad)
	 * 
	 * @param puntos Matriz de puntos
	 * @param posicion Vector2D porsicion
	 * @param velocidad Vector2D velocidad
	 * @param aceleracion Vector2D aceleracion
	 * @param masa 
	 * @param color
	 * 
	 * @throws DimensionNoValidaException Si algun vector posicion, velocidad o aceleracion no tiene 2 componentes o si los puntos de la matriz no tienen 2 componentes.
	 * @throws PoligonoComplejoException Si los puntos pasados forman un pol�gono complejo
	 */
	public EntidadPoligono(Matriz puntos, Vector2D posicion, Vector2D velocidad,
			Vector2D aceleracion, double masa, Color color)
			throws IllegalArgumentException {
		super(posicion, velocidad, aceleracion, masa, color);
		
		if (EntidadPoligono.esComplejo(puntos.getDatos()))
			throw new PoligonoComplejoException(puntos);
		if (puntos.getFilas() !=2)
			throw new DimensionNoValidaException("La matriz de puntos debe tener 2 filas");
		
		this.n = puntos.getColumnas();
		this.verticesRelativos = puntos;
		this.refrescarVertices();
		this.concavo = EntidadPoligono.esConcavo(this);
	}

	/**
	 * Constructor de pol�gonos
	 * 
	 * @param puntos
	 * @param posicion
	 * @param velocidad
	 * @param aceleracion
	 * @param posicionAngular
	 * @param velocidadAngular
	 * @param aceleracionAngular
	 * @param masa
	 * @param color
	 * @throws DimensionNoValidaException Si algun vector posicion, velocidad o aceleracion no tiene 2 componentes o si los puntos de la matriz no tienen 2 componentes.
	 * @throws PoligonoComplejoException Si los puntos pasados forman un pol�gono complejo
	 */
	public EntidadPoligono(Matriz puntos, Vector2D posicion, Vector2D velocidad,
			Vector2D aceleracion, double posicionAngular,
			double velocidadAngular, double aceleracionAngular, double masa,
			Color color) throws IllegalArgumentException {
		super(posicion, velocidad, aceleracion, posicionAngular,
				velocidadAngular, aceleracionAngular, masa, color);
		
		if (EntidadPoligono.esComplejo(puntos.getDatos()))
			throw new PoligonoComplejoException(puntos);
		if (puntos.getFilas() != 2)
			throw new DimensionNoValidaException("La matriz de puntos debe tener 2 filas");
		
		this.n = puntos.getColumnas();
		this.verticesRelativos = puntos;
		this.refrescarVertices();
		this.concavo = EntidadPoligono.esConcavo(this);
	}

	/**
	 * Constructor de Pol�gos b�sico (se omiten caracter�sticas de giro para la entidad)
	 * 
	 * @param posicion
	 * @param velocidad
	 * @param aceleracion
	 * @param masa
	 * @param color
	 * @param puntos
	 * @throws DimensionNoValidaException Si algun vector posicion, velocidad o aceleracion no tiene 2 componentes o si los puntos de la matriz no tienen 2 componentes.
	 * @throws PoligonoComplejoException Si los puntos pasados forman un pol�gono complejo
	 */
	public EntidadPoligono(Vector2D posicion, Vector2D velocidad, Vector2D aceleracion,
			double masa, Color color, double[]... puntos)
			throws IllegalArgumentException {
		super(posicion, velocidad, aceleracion, masa, color);
		
		if (EntidadPoligono.esComplejo(puntos))
			throw new PoligonoComplejoException(puntos);
		if (puntos.length != 2)
			throw new DimensionNoValidaException(
					"Los puntos de un pol�gono han de tener 2 coordenads");
		if (puntos[0].length != puntos[1].length)
			throw new DimensionNoValidaException(
					"Se ha de especificar el mismo n�mero de coordenadas X e Y al construir una matriz de puntos");
		
		this.verticesRelativos = new Matriz(puntos);
		this.n = puntos[0].length;
		this.refrescarVertices();
		this.concavo = EntidadPoligono.esConcavo(this);
	}

	/**
	 * Constructor de pol�gonos
	 * 
	 * @param posicion
	 * @param velocidad
	 * @param aceleracion
	 * @param posicionAngular
	 * @param velocidadAngular
	 * @param aceleracionAngular
	 * @param masa
	 * @param color
	 * @param puntos
	 * @throws DimensionNoValidaException Si algun vector posicion, velocidad o aceleracion no tiene 2 componentes o si los puntos de la matriz no tienen 2 componentes.
	 * @throws PoligonoComplejoException Si los puntos pasados forman un pol�gono complejo
	 */
	public EntidadPoligono(Vector2D posicion, Vector2D velocidad, Vector2D aceleracion,
			double posicionAngular, double velocidadAngular,
			double aceleracionAngular, double masa, Color color,
			double[]... puntos) throws IllegalArgumentException {
		super(posicion, velocidad, aceleracion, posicionAngular,
				velocidadAngular, aceleracionAngular, masa, color);
		if (EntidadPoligono.esComplejo(puntos))
			throw new PoligonoComplejoException(puntos);
		
		if (puntos.length != 2)
			throw new DimensionNoValidaException(
					"Los puntos de un pol�gono han de tener 2 coordenads");
		if (puntos[0].length != puntos[1].length)
			throw new DimensionNoValidaException(
					"Se ha de especificar el mismo n�mero de coordenadas X e Y al construir una matriz de puntos");

		this.verticesRelativos = new Matriz(puntos);
		this.n = puntos[0].length;
		this.refrescarVertices();
		this.concavo = EntidadPoligono.esConcavo(this);
	}

	/**
	 * Devuelve el n�mero de lados del pol�gono
	 * 
	 * @return n�mero de lados del pol�gono
	 */
	public int getNumeroLados() {
		return this.n;
	}

	/**
	 * Devuelve el n�mero de v�rtices del pol�gono
	 * 
	 * @return n�mero de v�rtices del pol�gono
	 */
	public int getNumeroVertices() {
		return this.n;
	}


	/**
	 * Devuelve la matriz de puntos del pol�gono como si estuviera centrado en (0,0)
	 * 
	 * @return Matriz de puntos
	 */
	public Matriz getVerticesRelativos() {
		return this.verticesRelativos;
	}
	
	/**
	 * Devuelve la matriz de punto del pol�giono teniendo en cuenta su posici�n
	 * 
	 * @return Matri de puntos
	 */
	public Matriz getVerticesAbsolutos() {
		return this.verticesAbsolutos;
	}

	/**
	 * Cambia la matriz de putnos 
	 * 
	 * @param puntos Nueva matriz de puntos
	 * @throws DimensionNoValidaException Si la matriz no cumple las condiciones para ser una matriz de putnos (tener 2 filas)
	 */
	protected void setVertices(Matriz puntos) throws DimensionNoValidaException {
		if (puntos.getFilas() != 2) 
			throw new DimensionNoValidaException("Las coordenadas de los puntos de un poligono han de tener 2");
		this.verticesAbsolutos = puntos;
		this.n = puntos.getColumnas();
		this.refrescarVertices();
	}
	
	/**
	 * Devuelve un Vector2D con 2 componentes, X e Y de el vertice n�mero i del pol�gono.
	 * Las coordenadas del v�rtice est�n en forma relativa al centro del pol�gono, es decir
	 * como si este estuviera centrado en (0,0)
	 * 
	 * @param i N�mero de v�rtice a devolver
	 * @return Vector2D de 2 componentes representante del v�rtice
	 * @throws IndexOutOfBoundsException Si el pol�gono no tiene tantos v�rtices.
	 */
	public Vector2D getVerticeRelativo(int i) throws IndexOutOfBoundsException {
		return new Vector2D(this.verticesRelativos.getColumna(i));		
	}
	
	/**
	 * Devuelve un Vector2D con 2 componentes, X e Y de el vertice n�mero i del pol�gono.
	 * 
	 * @param i N�mero de v�rtice a devolver
	 * @return Vector2D de 2 componentes representante del v�rtice
	 * @throws IndexOutOfBoundsException Si el pol�gono no tiene tantos v�rtices.
	 */
	public Vector2D getVerticeAbsoluto(int i) throws IndexOutOfBoundsException {
		return new Vector2D(this.verticesAbsolutos.getColumna(i));
	}

	/* (non-Javadoc)
	 * @see Entidades.Entidad#calcularNuevasPosiciones(double)
	 */
	@Override
	public void calcularNuevasPosiciones(double dt) {
		super.calcularNuevasPosiciones(dt);
		this.refrescarVertices();
	}

	/**
	 * Actualiza la matriz {@link #verticesAbsolutos} 
	 */
	public void refrescarVertices() {
		Matriz matrizGiro = Matriz.getMatriz2x2Giro(this.getPosicionAngular());
		verticesAbsolutos = matrizGiro.por(verticesRelativos);
		Matriz matrizPosicion = new Matriz(2, n);
		for (int i = 0; i < n; i++) {
			matrizPosicion.set(0, i, this.getPosicion().getX());
			matrizPosicion.set(1, i, this.getPosicion().getY());
		}

		verticesAbsolutos = verticesAbsolutos.suma(matrizPosicion);
	}

	
	/* (non-Javadoc)
	 * @see Entidades.Entidad#pintar(java.awt.Graphics)
	 */
	public void pintar(Graphics g) {
		g.setColor(this.getColor());
		g.fillPolygon(this.getVerticesAbsolutos().getFila(0).getDatosInt(), 
				      this.getVerticesAbsolutos().getFila(1).getDatosInt(), 
				      this.getNumeroVertices());

	}

	/* (non-Javadoc)
	 * @see Entidades.Entidad#hayColision(Entidades.Entidad)
	 */
	public boolean hayColision(Entidad entidad) throws EntidadDesconocidaException {
		if (entidad instanceof EntidadPoligono) {
			EntidadPoligono entPolig = (EntidadPoligono) entidad;
			for (int i=0; i<this.getNumeroVertices();i++)
				if (EntidadPoligono.contiene(entPolig, this.getVerticeAbsoluto(i)))
					return true;
			for (int i=0; i<entPolig.getNumeroVertices();i++)
				if (EntidadPoligono.contiene(this, entPolig.getVerticeAbsoluto(i)))
					return true;
			return false;
		}
		else if (entidad instanceof EntidadCirculo){
			return entidad.hayColision(this);
		}
		else {
			throw new EntidadDesconocidaException("Imposible procesar colision con entidad desconocida" , entidad);
		}

	}
	
	/* (non-Javadoc)
	 * @see Entidades.Entidad#hayColisionX(int, int)
	 */
	public boolean hayColisionX(int Xinf, int Xsup) {
		Vector coordenadasX = verticesAbsolutos.getFila(0);
		for (int i = 0; i < coordenadasX.getDimension(); i++) {
			if (coordenadasX.get(i) < Xinf || coordenadasX.get(i) > Xsup) {
				return true;
			}
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see Entidades.Entidad#hayColisionY(int, int)
	 */
	public boolean hayColisionY(int Yinf, int Ysup) {
		Vector coordenadasY = verticesAbsolutos.getFila(1);
		for (int i = 0; i < coordenadasY.getDimension(); i++) {
			if (coordenadasY.get(i) < Yinf || coordenadasY.get(i) > Ysup) {
				return true;
			}	
		}
		return false;		
	}
	
	// No usar hasta reparar el m�todo est�tico...
	/**
	 * Devuelve TRUE si el pol�gono es convexo
	 * 
	 * @return TRUE si el pol�gono es convexo
	 */
	@Deprecated
	public boolean esConvexo() {
		return !this.concavo;
	}
	
	// No usar hasta reparar el m�todo est�tico...
	/**
	 * Devuelve TRUE si el pol�gono es concavo
	 * 
	 * @return TRUE si el pol�gono es concavo
	 */
	@Deprecated
	public boolean esConcavo() {
		return this.concavo;
	}
	
	/**
	 * Comprueba si un punto est� contenido en el pol�gono
	 *  
	 * @param punto Punto a comprobar
	 * @return TRUE: si el punto est� contenido
	 */
	public boolean contiene(Vector2D punto) {
		return EntidadPoligono.contiene(this, punto);
	}
	
	@Override
	public String toString() {
		return (super.toString() + "\n Poligono de " + n + "lados.");
	}

	/**
	 * Dada la matriz de puntos de un p�l�gono calcula su �rea.
	 * 
	 * @param puntos Matriz de puntos
	 * @return �rea del pol�gono con la matriz de putnos dada
	 */
	public static double areaPoligono(double[]... puntos) {
		if (puntos.length != 2 || (puntos[0].length != puntos[1].length))
			return -1;

		// Calculo por tri�ngulos con la f�rmula de Heron
		double area = 0;
		double semiperimetro;
		double ladoA = 0;
		double ladoB = 0;
		double ladoC = 0;

		for (int i = 1; i < puntos[0].length - 1; i++) {
			ladoA = new Vector2D(puntos[0][0], puntos[1][0])
					.distanciaA(new Vector2D(puntos[0][i], puntos[1][i]));
			ladoB = new Vector2D(puntos[0][0], puntos[1][0])
					.distanciaA(new Vector2D(puntos[0][i + 1],
							puntos[1][i + 1]));
			ladoC = new Vector2D(puntos[0][i], puntos[1][i])
					.distanciaA(new Vector2D(puntos[0][i + 1],
							puntos[0][i + 1]));

			semiperimetro = (ladoA + ladoB + ladoC) / 2;

			area += Math.sqrt(semiperimetro * (ladoA - semiperimetro)
					* (ladoB - semiperimetro) * (ladoC - semiperimetro));
		}

		return area;
	}

	/**
	 * Devuelve la masa de un pol�gono dados sus puntos y su altura
	 * 
	 * @param altura Altura 'imaginaria' del pol�gono para determinar su masa
	 * @param puntos Puntos del poligono
	 * @return masa del pol�gono
	 */
	public static double getMasa(double altura, double[]... puntos) {
		return altura * areaPoligono(puntos);
	}

	/**
	 * Devuelve la masa de un pol�gono dados sus puntos y suponiendo altura 1
	 * 
	 * @param puntos Puntos del pol�gono
	 * @return masa del pol�gono
	 */
	public static double getMasa(double[]... puntos) {
		return areaPoligono(puntos);
	}
	
	// TODO: Comprobar
	// TODO: Reparar (est� mal fijo)
	/**
	 * Comprueba si un pol�gono es c�ncavo o  no
	 * 
	 * @param polig Pol�gono
	 * @return TRUE: si es c�ncavo
	 */
	@Deprecated
	public static boolean esConcavo(EntidadPoligono polig) {
		
		/* Comprobamos el signo de los productos escalares de cada pares de vertices. 
		 * Si alguno tiene signo distinto, el pol�gono es concavo
		 */
		
		int n = polig.getNumeroVertices();
		
		if (n<4)
			return false;
		
		double productoEscalar = new Vector2D(polig.getVerticeAbsoluto(n-1),polig.getVerticeAbsoluto(0)).productoEscalar(new Vector2D(polig.getVerticeAbsoluto(0),polig.getVerticeAbsoluto(1)));
	
		for (int i=1;i<n-1;i++) {
			
			if (productoEscalar *
				new Vector2D(polig.getVerticeAbsoluto(i-1),polig.getVerticeAbsoluto(i)).productoEscalar(new Vector2D(polig.getVerticeAbsoluto(i),polig.getVerticeAbsoluto(i+1)))
				< 0) {
				return true;
			}
		}

		return false;
	}
	
	
	// TODO: Comprobar
	// From http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
	/**
	 * Comprueba si un pol�gono contiene o no un punto
	 * 
	 * @param polig Poligono
	 * @param punto punto
	 * @return TRUE: Si el pol�gono contiene al punto
	 */
	public static boolean contiene(EntidadPoligono polig, Vector2D punto) {
		int n = polig.getNumeroVertices();
		boolean estaDentro = false;
		for (int i=0, j=n-1; i < n; j = i++) {
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
		}
		return estaDentro;
	}
	
	/**
	 * Comprueba si el pol�gono definido por sus puntos contiene o otro punto
	 * 
	 * @param polig Array de vectores que definien los puntos
	 * @param punto punto
	 * @return TRUE: Si el pol�gono contiene al punto
	 */
	// TODO: Comprobar
	public static boolean contiene(Vector2D puntosPolig[], Vector2D punto) {
		int n = puntosPolig.length;
		boolean estaDentro = false;
		for (int i=0, j=n-1; i < n; j = i++) {
			if (
					(
						(
							puntosPolig[i].getY() > punto.getY()
						) 
						!= 
						(
							puntosPolig[j].getY() > punto.getY()
						)
					)
					&&
					(
							punto.getX() 
							< 
							(
								(puntosPolig[j].getX() - puntosPolig[i].getX()) 
								* 
								(punto.getY() - puntosPolig[i].getY()) 
								/ 
								(puntosPolig[j].getY() - puntosPolig[i].getY())
								+ puntosPolig[i].getX()
							)
				    )
			) {
			estaDentro = !estaDentro;
			}

		}
		return estaDentro;
	}
	
	//TODO: Implementar
	/**
	 * Comprueba si un pol�gono es complejo o no (si sus lados se cruzan)
	 * 
	 * @param puntos Vector2D de puntos del pol�gono
	 * @return TRUE: Si es complejo
	 */
	@Deprecated
	public static boolean esComplejo(double puntos[][]) {
		return false;
	}

}