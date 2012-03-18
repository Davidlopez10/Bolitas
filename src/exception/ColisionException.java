package exception;

import entidades.Entidad;

/**
 * Excepcion que se genera cuando vas a spawnear una entidad y entra en colision.
 * 
 * @author Jose Diaz
 */
@SuppressWarnings("serial")
public class ColisionException extends Exception {
	
	private Entidad entidadColisionada;
	
	/**
	 * Constructor sin argumentos
	 */
	public ColisionException() {
		super();
		entidadColisionada=null;
	}
	
	/**
	 * Constructor con causa
	 * 
	 * @param s causa de la excepcion
	 */
	public ColisionException(String s) {
		super(s);
	}
	
	/**
	 * Constructor con la entidad
	 * 
	 * @param e Entidad que causo la excepcion
	 */
	public ColisionException(Entidad e) {
		super();
		this.entidadColisionada=e;
	}
	
	/**
	 * Constructor con entidad y causa
	 * 
	 * @param s causa de la excepcion
	 * @param e entidad que causo la excepcion
	 */
	public ColisionException(String s, Entidad e) {
		super(s);
		this.entidadColisionada = e;
	}
	
	/**
	 * Devuelve la entidad que causo la excepcion
	 * 
	 * @return entidad que causo la excepcion
	 */
	public Entidad getEntidad() {
		return this.entidadColisionada;
	}
}