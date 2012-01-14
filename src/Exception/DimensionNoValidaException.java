package Exception;


public class DimensionNoValidaException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DimensionNoValidaException() {
		super();
	}
	
	public DimensionNoValidaException(String mensaje) {
		super(mensaje);
	}	
}