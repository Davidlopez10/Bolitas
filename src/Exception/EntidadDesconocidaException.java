package Exception;

import Entidades.Entidad;

/**
 * Excepcion generada cuando se debe tratar una excepcion según su tipo y no se puede
 * determinar 
 * 
 * @author Jose Diaz
 */
@SuppressWarnings("serial")
public class EntidadDesconocidaException extends IllegalArgumentException {
	
	private Entidad entidadDesconocida;
	
	/**
	 * Constructor por defecto
	 */
	public EntidadDesconocidaException() {
		super();
		entidadDesconocida=null;
	}
	
	/**
	 * Constructor con la causa
	 * 
	 * @param s causa de la excepcion
	 */
	public EntidadDesconocidaException(String s) {
		super(s);
	}
	
	/**
	 * Constructor con la entidad
	 * 
	 * @param e Entidad que causo la excepción
	 */
	public EntidadDesconocidaException(Entidad e) {
		super();
		this.entidadDesconocida=e;
	}
	
	/**
	 * Constructor con la entidad y el motivo
	 * 
	 * @param s causa de la excepción
	 * @param e entidad que causó la excepción
	 */
	public EntidadDesconocidaException(String s, Entidad e) {
		super(s);
		this.entidadDesconocida = e;
	}
	
	/**
	 * Devuelve la entidad que causó la excepción
	 * 
	 * @return Entidad que causó la excepción
	 */
	public Entidad getEntidad() {
		return this.entidadDesconocida;
	}
	
}