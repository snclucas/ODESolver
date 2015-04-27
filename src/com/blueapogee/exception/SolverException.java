/**
 * 
 */
package com.blueapogee.exception;

/**
 * Exception thrown by the solver
 * @author sclucas
 *
 */
@SuppressWarnings("serial")
public class SolverException extends Exception {

	/**
	 * Constructor
	 */
	public SolverException() {
	}

	/**
	 * @param message
	 */
	public SolverException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SolverException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SolverException(String message, Throwable cause) {
		super(message, cause);
	}

}
