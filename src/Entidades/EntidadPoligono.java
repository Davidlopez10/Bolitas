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

	// Almacena la posicion de los vértices entre ellos (sin aplicar giros ni
	// posicion del polígono)
	private Matriz verticesRelativos;

	// Almacena la posicion absoulta de los vértices (sobre la pantalla)
	private Matriz verticesAbsolutos;

	private int n;
	
	private boolean concavo;

	/**
	 * Constructor de Polígos básico (se omiten características de giro para la entidad)
	 * 
	 * @param puntos Matriz de puntos
	 * @param posicion Vector2D porsicion
	 * @param velocidad Vector2D velocidad
	 * @param aceleracion Vector2D aceleracion
	 * @param masa 
	 * @param color
	 * 
	 * @throws DimensionNoValidaException Si algun vector posicion, velocidad o aceleracion no tiene 2 componentes o si los puntos de la matriz no tienen 2 componentes.
	 * @throws PoligonoComplejoException Si los puntos pasados forman un polígono complejo (con lados que se cruzan)
	 */
	public EntidadPoligono(Matriz puntos, Vector2D posicion, Vector2D velocidad,
			Vector2D aceleracion, double masa, Color color)
			throws IllegalArgumentException {
		this(puntos,posicion,velocidad,aceleracion,0,0,0,masa,color);
	}

	/**
	 * Constructor de polígonos
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
	 * @throws PoligonoComplejoException Si los puntos pasados forman un polígono complejo (con lados que se cruzan)
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
	 * Constructor de Polígos básico (se omiten características de giro para la entidad)
	 * 
	 * @param posicion
	 * @param velocidad
	 * @param aceleracion
	 * @param masa
	 * @param color
	 * @param puntos
	 * @throws DimensionNoValidaException Si algun vector posicion, velocidad o aceleracion no tiene 2 componentes o si los puntos de la matriz no tienen 2 componentes.
	 * @throws PoligonoComplejoException Si los puntos pasados forman un polígono complejo
	 */
	public EntidadPoligono(Vector2D posicion, Vector2D velocidad, Vector2D aceleracion,
			double masa, Color color, double[]... puntos)
			throws IllegalArgumentException {
		this(new Matriz(puntos), posicion,velocidad,aceleracion,masa,color);
	}

	/**
	 * Constructor de polígonos
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
	 * @throws PoligonoComplejoException Si los puntos pasados forman un polígono complejo
	 */
	public EntidadPoligono(Vector2D posicion, Vector2D velocidad, Vector2D aceleracion,
			double posicionAngular, double velocidadAngular,
			double aceleracionAngular, double masa, Color color,
			double[]... puntos) throws IllegalArgumentException {
		this(new Matriz(puntos), posicion,velocidad,aceleracion,posicionAngular,velocidadAngular,aceleracionAngular,masa,color);
	}

	/**
	 * Devuelve el número de vértices del polígono
	 * 
	 * @return número de vértices del polígono
	 */
	public int getNumeroVertices() {
		return this.n;
	}


	/**
	 * Devuelve la matriz de puntos del polígono como si estuviera centrado en (0,0)
	 * 
	 * @return Matriz de puntos
	 */
	public Matriz getVerticesRelativos() {
		return this.verticesRelativos;
	}
	
	/**
	 * Devuelve la matriz de punto del polígiono teniendo en cuenta su posición
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
	 * Devuelve un Vector2D con 2 componentes, X e Y de el vertice número i del polígono.
	 * Las coordenadas del vértice están en forma relativa al centro del polígono, es decir
	 * como si este estuviera centrado en (0,0)
	 * 
	 * @param i Número de vértice a devolver
	 * @return Vector2D de 2 componentes representante del vértice
	 * @throws IndexOutOfBoundsException Si el polígono no tiene tantos vértices.
	 */
	public Vector2D getVerticeRelativo(int i) throws IndexOutOfBoundsException {
		return new Vector2D(this.verticesRelativos.getColumna(i));		
	}
	
	/**
	 * Devuelve un Vector2D con 2 componentes, X e Y de el vertice número i del polígono.
	 * 
	 * @param i Número de vértice a devolver
	 * @return Vector2D de 2 componentes representante del vértice
	 * @throws IndexOutOfBoundsException Si el polígono no tiene tantos vértices.
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
		
		super.pintar(g);
	}

	/* (non-Javadoc)
	 * @see Entidades.Entidad#hayColision(Entidades.Entidad)
	 */
	public boolean hayColision(Entidad ent) throws EntidadDesconocidaException {
		if (ent instanceof EntidadCirculo) {
			return hayColision((EntidadCirculo) ent);
		}
		else if (ent instanceof EntidadPoligono) {
			return hayColision((EntidadPoligono) ent);
		}
		else {
			throw new EntidadDesconocidaException(ent);
		}	
	}
	
	/**
	 * Devuelve si hay colision o no con una {@link EntidadCirculo}
	 * 
	 * @param circulo {@link EntidadCirculo} con la que comprobar si hay colision o no
	 * @return TRUE si colisina, false en otro caso.
	 */
	private boolean hayColision(EntidadCirculo circulo) {	
		return circulo.hayColision(this);	
	}
	
	/**
	 * Devuelve si hay colision o no con una {@link EntidadPoligono}	
	 * 
	 * @param polig {@link EntidadPoligono} con el que colisiona
	 * @return TRUE si colisina, false en otro caso.
	 */
	private boolean hayColision(EntidadPoligono polig) {
		for (int i=0; i<this.getNumeroVertices();i++)
			if (EntidadPoligono.contiene(polig, this.getVerticeAbsoluto(i)))
				return true;
		for (int i=0; i<polig.getNumeroVertices();i++)
			if (EntidadPoligono.contiene(this, polig.getVerticeAbsoluto(i)))
				return true;
		return false;
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
	
	// No usar hasta reparar el método estático...
	/**
	 * Devuelve TRUE si el polígono es convexo
	 * 
	 * @return TRUE si el polígono es convexo
	 */
	@Deprecated
	public boolean esConvexo() {
		return !this.concavo;
	}
	
	// No usar hasta reparar el método estático...
	/**
	 * Devuelve TRUE si el polígono es concavo
	 * 
	 * @return TRUE si el polígono es concavo
	 */
	@Deprecated
	public boolean esConcavo() {
		return this.concavo;
	}
	
	/**
	 * Comprueba si un punto está contenido en el polígono
	 *  
	 * @param punto Punto a comprobar
	 * @return TRUE: si el punto está contenido
	 */
	public boolean contiene(Vector2D punto) {
		return EntidadPoligono.contiene(this, punto);
	}
	
	@Override
	public String toString() {
		return (super.toString() + "\n Poligono de " + n + "lados.");
	}

	/**
	 * Dada la matriz de puntos de un pólígono calcula su área.
	 * 
	 * @param puntos Matriz de puntos
	 * @return Área del polígono con la matriz de putnos dada
	 */
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
	 * Devuelve la masa de un polígono dados sus puntos y su altura
	 * 
	 * @param altura Altura 'imaginaria' del polígono para determinar su masa
	 * @param puntos Puntos del poligono
	 * @return masa del polígono
	 */
	public static double getMasa(double altura, double[]... puntos) {
		return altura * areaPoligono(puntos);
	}

	/**
	 * Devuelve la masa de un polígono dados sus puntos y suponiendo altura 1
	 * 
	 * @param puntos Puntos del polígono
	 * @return masa del polígono
	 */
	public static double getMasa(double[]... puntos) {
		return areaPoligono(puntos);
	}
	
	// TODO: Comprobar
	// TODO: Reparar (está mal fijo)
	/**
	 * Comprueba si un polígono es cóncavo o  no
	 * 
	 * @param polig Polígono
	 * @return TRUE: si es cóncavo
	 */
	@Deprecated
	public static boolean esConcavo(EntidadPoligono polig) {
		
		/* Comprobamos el signo de los productos escalares de cada pares de vertices. 
		 * Si alguno tiene signo distinto, el polígono es concavo
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
	 * Comprueba si un polígono contiene o no un punto
	 * 
	 * @param polig Poligono
	 * @param punto punto
	 * @return TRUE: Si el polígono contiene al punto
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
	 * Comprueba si el polígono definido por sus puntos contiene o otro punto
	 * 
	 * @param polig Array de vectores que definien los puntos
	 * @param punto punto
	 * @return TRUE: Si el polígono contiene al punto
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
	 * Comprueba si un polígono es complejo o no (si sus lados se cruzan)
	 * 
	 * @param puntos Vector2D de puntos del polígono
	 * @return TRUE: Si es complejo
	 */
	@Deprecated
	public static boolean esComplejo(double puntos[][]) {
		return false;
	}
	
	//TODO: Implementar
	/**
	 * Dados los puntos que definen a un polígono, devuelve el centro de masa del mismo, suponiendo densidad constante
	 * 
	 * @param puntos Puntos que definen al polígono
	 * @return Centro de masa
	 */
	@Deprecated
	public static Vector centroMasa(double puntos[][]) {
		return null;
	}
	
	/**
	 * Dada la matriz de puntos define a un polígono, devuelve el centro de masa del mismo, suponiendo densidad constante
	 * 
	 * @param puntos Matriz de puntos que define al polígono
	 * @return Centro de masa
	 */
	public static Vector cetroMasa(Matriz puntos) {
		return centroMasa(puntos.getDatos());
	}

	/* (non-Javadoc)
	 * @see Entidades.Entidad#tratarColisionEscenarioX()
	 */
	@Override
	public void tratarColisionEscenarioX() {
		this.getVelocidad().invertirX();
		this.invertirVelocidadAngular(); 
	}

	/* (non-Javadoc)
	 * @see Entidades.Entidad#tratarColisionEscenarioY()
	 */
	@Override
	public void tratarColisionEscenarioY() {
		this.getVelocidad().invertirY();
		this.invertirVelocidadAngular();
	}
	
	/* (non-Javadoc)
	 * @see Entidades.Entidad#tratarColision(Entidades.Entidad)
	 */
	public void tratarColision(Entidad e) throws EntidadDesconocidaException {
		if (e instanceof EntidadCirculo) {
			tratarColision((EntidadCirculo) e);
		}
		else if (e instanceof EntidadPoligono) {
			tratarColision((EntidadPoligono) e);
		}
		else {
		    throw new EntidadDesconocidaException(e);
		}
	}

	
	/**
	 * Trata colisiones de la entidad con un círculo
	 * 
	 * @param circulo Círculo con el que tratar la colision
	 */
	private void tratarColision(EntidadCirculo circulo) {
		circulo.tratarColision(this);
	}
	
	// TODO: Implementar convenientemente.
	/**
	 * Trata colisiones de la entidad con polígonos
	 * 
	 * @param polig Polígono con el que tratar la colisión
	 */
	private void tratarColision(EntidadPoligono polig) {
		try { 
			this.invertirVelocidadAngular();
			this.getVelocidad().invertirX();
			this.getVelocidad().invertirY();
			polig.invertirVelocidadAngular();
			polig.getVelocidad().invertirX();
			polig.getVelocidad().invertirY();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}