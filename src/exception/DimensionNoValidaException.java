package exception;

/**
 * Excepcion generada cuando se intenta hacer una operacion entre vector y/o matrices y las dimensioens
 * no son adecuadas.
 * 
 * @author Jose Diaz
 */
@SuppressWarnings("serial")
public class DimensionNoValidaException extends IllegalArgumentException {

	/**
	 * Constructor sin argumentos
	 */
	public DimensionNoValidaException() {
		super();
	}
	
	/**
	 * Constructor con causa de la excepcion
	 * 
	 * @param mensaje causa de la excepcion
	 */
	public DimensionNoValidaException(String mensaje) {
		super(mensaje);
	}	
}