package com.blueapogee.solver;

import com.blueapogee.DerivFunction;
import com.blueapogee.exception.SolverException;


/**
* Euler solver class. Simple Euler method.
*/
public class Euler extends ODESolver {

	
	
	/**
	 * @see ODESolver
	 * */
	public Euler(DerivFunction derivFunction, SolverParams params) {
		super(derivFunction,  params);
		this.solverName = "Euler";
	}


	
	
	/**
	 * Performs a solver step.
     *
     * @param X0  input values
     * 
     * @return array of outputs.
     *              
	 */
	protected double[] doStep(double[] X0) {
		double[] result = new double[numberOfOdes];

		try {
			double[] output = derivFunction.value(X0);

			for (int i = 0; i <= numberOfOdes - 1; i++) {
				result[i] = X0[i] + output[i] * stepSize;
			}

		} catch (SolverException se) {
			System.err.println("Solver Exception: " + se.getMessage());
		} catch (Exception ex) {
			System.err.println("Exception: " + ex.getMessage());
		}

		return result;
	}

}
