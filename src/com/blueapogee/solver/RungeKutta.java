package com.blueapogee.solver;

import com.blueapogee.DerivFunction;
import com.blueapogee.exception.SolverException;


/**
* RK4 solver class. Basic Runge Kutta method.
*/
public class RungeKutta extends ODESolver {
	
	/**
	 * @see ODESolver
	 * */
	public RungeKutta(DerivFunction derivFunction, SolverParams params) {
		super(derivFunction,  params);
		this.solverName = "RK";
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

		/* Results array */
		double[] result = new double[numberOfOdes];

		/*
		 * Arrays for Runge Kutta algorithm
		 */
		double[] d1 = new double[numberOfOdes];
		double[] d2 = new double[numberOfOdes];
		double[] d3 = new double[numberOfOdes];

		/*
		 * Dynamically allocating arrays for 'double Xa[N],' and 'double X[N]'.
		 */
		double[] Xa = new double[numberOfOdes];
		double[] dX = new double[numberOfOdes];

		try {

			/* d1 = hF(x(t), t) */
			dX = derivFunction.value(X0);
			for (int i = 0; i <= numberOfOdes - 1; i++) {
				d1[i] = stepSize * dX[i];
				Xa[i] = X0[i] + 0.5 * d1[i];
			}

			/* d2 = hF(x(t) + d1 / 2, t + h / 2) */
			dX = derivFunction.value(Xa);
			for (int i = 0; i <= numberOfOdes - 1; i++) {
				d2[i] = stepSize * dX[i];
				Xa[i] = X0[i] + 0.5 * d2[i];
			}

			/* d3 = hF(x(t) + d2 / 2, t + h / 2) */
			dX = derivFunction.value(Xa);
			for (int i = 0; i <= numberOfOdes - 1; i++) {
				d3[i] = stepSize * dX[i];
				Xa[i] = X0[i] + d3[i];
			}

			/* x(t + h) = x(t) + d1 / 6 + d2 / 3 + d3 / 3 */
			dX = derivFunction.value(Xa);
			for (int i = 0; i <= numberOfOdes - 1; i++) {
				result[i] = X0[i] + (d1[i] + d2[i] * 2 + d3[i] * 2 + stepSize * dX[i]) / 6.0;
			}

		} catch (SolverException se) {
			System.err.println("Solver Exception: " + se.getMessage());
		} catch (Exception ex) {
			System.err.println("Exception: " + ex.getMessage());
		}

		return result;
	}

}
