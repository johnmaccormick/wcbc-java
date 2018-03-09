/**
 * 
 */
package wcbc;

/**
 * A simple wrapper of the standard Java java.lang.Exception class.
 * 
 * WcbcException instances are used to indicate unexpected or unhandled
 * situations within the wcbc package. All the constructors use the default Java
 * Exception behavior. Please consult the documentation of java.lang.Exception for details.
 *
 */
public class WcbcException extends Exception {

	public WcbcException() {
		super();
	}

	public WcbcException(String message) {
		super(message);
	}

	public WcbcException(Throwable cause) {
		super(cause);
	}

	public WcbcException(String message, Throwable cause) {
		super(message, cause);
	}

	public WcbcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
