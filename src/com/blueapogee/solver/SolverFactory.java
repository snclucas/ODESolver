package com.blueapogee.solver;

import com.blueapogee.DerivFunction;

/**
 * Simple factory for the solvers.
 */
public enum SolverFactory {

	/* Solver enums */
	EULER("Euler"), RK("Runge Kutta"), RKCK("Runge Kutta Cash Karp");

	/**
	 * Enum constructor
	 * @param name name of the solver. Helps in logging.
	 */
	private SolverFactory(String name) {
		this.name = name;
	}

	/**
	 * Gets the solver enum name
	 * @return name of the solver.
	 */
	public String toString() {
		return name;
	}

	/**
	 * Gets the specified solver
	 * 
	 * @param derivFunction  derivative function
     * @param totalTime  total time
     * @param h  derivative solver step size
	 * 
	 * @return solver.
	 * 
	 * @see ODESolver
	 */
	public ODESolver get(DerivFunction derivFunction, SolverParams params) {

		switch (this) {
		case EULER:
			return new Euler(derivFunction, params);
		case RK:
			return new RungeKutta(derivFunction, params);
		default:
			return new RungeKuttaCashKarp(derivFunction, params);
		}
	}

	/** Name of the solver */
	private String name;

}
