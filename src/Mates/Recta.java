package Mates;

import Exception.DimensionNoValidaException;


public class Recta {
	
	private Vector punto;
	private Vector direccion;
	
	public Recta(Vector p1, Vector p2) {
		this.punto = p1;
		try {
			this.direccion = (p1.resta(p2)).normalizar();
		} catch (DimensionNoValidaException e) {
			e.printStackTrace();
		}
	}
	
	public double distanciaA(Vector punto) {
		try {
			Vector distancia = this.punto.resta(punto);
			return (distancia.resta(distancia.proyectarSobre(direccion))).norma();
		} catch (ArithmeticException e) {
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
}