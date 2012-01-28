package Exception;

import Entidades.Entidad;

/**
 * Excepcion generada cuando se debe tratar una excepcion seg�n su tipo y no se puede
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
	 * @param e Entidad que causo la excepci�n
	 */
	public EntidadDesconocidaException(Entidad e) {
		super();
		this.entidadDesconocida=e;
	}
	
	/**
	 * Constructor con la entidad y el motivo
	 * 
	 * @param s causa de la excepci�n
	 * @param e entidad que caus� la excepci�n
	 */
	public EntidadDesconocidaException(String s, Entidad e) {
		super(s);
		this.entidadDesconocida = e;
	}
	
	/**
	 * Devuelve la entidad que caus� la excepci�n
	 * 
	 * @return Entidad que caus� la excepci�n
	 */
	public Entidad getEntidad() {
		return this.entidadDesconocida;
	}
	
}