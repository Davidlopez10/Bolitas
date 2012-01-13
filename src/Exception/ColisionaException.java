package Exception;

import Entidades.Entidad;

public class ColisionaException extends Exception {
	
	private Entidad entidadColisionada;
	
	public ColisionaException() {
		super();
		entidadColisionada=null;
	}
	
	public ColisionaException(Entidad e) {
		super();
		this.entidadColisionada=e;
	}
	
	public Entidad getEntidad() {
		return this.entidadColisionada;
	}
	
	public String getMessage() {
		return ("Colision al intentar spawnear " + this.entidadColisionada);
	}
}