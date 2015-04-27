package com.blueapogee.solver;

import com.blueapogee.DerivFunction;
import com.blueapogee.exception.SolverException;

/**
 * RK solver class. Improved Runge Kutta method.
 * Adapted from http://www.ee.ucl.ac.uk/~mflanaga/java/RungeKutta.java
 */
public class RungeKuttaCashKarp extends ODESolver {
	
	/**
	 * @see ODESolver
	 * */
	public RungeKuttaCashKarp(DerivFunction derivFunction, SolverParams params) {
		super(derivFunction,  params);
		this.solverName = "Improved RK";
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

		double[] res = new double[numberOfOdes];

		/*
		 * Arrays for Runge Kutta algorithm
		 */
		double[] k1 = new double[numberOfOdes];
		double[] k2 = new double[numberOfOdes];
		double[] k3 = new double[numberOfOdes];
		double[] k4 = new double[numberOfOdes];
		double[] k5 = new double[numberOfOdes];
		double[] k6 = new double[numberOfOdes];
		double[] y = new double[numberOfOdes];
		double[] y6 = new double[numberOfOdes];
		double[] y5 = new double[numberOfOdes];
		double[] yd = new double[numberOfOdes];

		double[] dX = new double[numberOfOdes];
	
		/* Step error, supress as I am not goign to do anything with the erro for this simple test */
		@SuppressWarnings("unused")
		double error = 0.0D;
		
		try {

			y = X0;

			dX = derivFunction.value(X0);
			for (int i = 0; i <= numberOfOdes - 1; i++) {
				k1[i] = stepSize * dX[i];
				yd[i] = y[i] + k1[i] / 5.0;
			}

			dX = derivFunction.value(yd);
			for (int i = 0; i <= numberOfOdes - 1; i++) {
				k2[i] = stepSize * dX[i];
				yd[i] = y[i] + (3.0 * k1[i] + 9.0 * k2[i]) / 40.0;
			}

			dX = derivFunction.value(yd);
			for (int i = 0; i <= numberOfOdes - 1; i++) {
				k3[i] = stepSize * dX[i];
				yd[i] = y[i] + (3.0 * k1[i] - 9.0 * k2[i] + 12.0 * k3[i])/ 10.0;
			}

			dX = derivFunction.value(yd);
			for (int i = 0; i <= numberOfOdes - 1; i++) {
				k4[i] = stepSize * dX[i];
				yd[i] = y[i] - 11.0 * k1[i] / 54.0 + 5.0 * k2[i] / 2.0 - 70.0
						* k3[i] / 27.0 + 35.0 * k4[i] / 27.0;
			}

			dX = derivFunction.value(yd);
			for (int i = 0; i <= numberOfOdes - 1; i++) {
				k5[i] = stepSize * dX[i];
				yd[i] = y[i] + 1631.0 * k1[i] / 55296.0 + 175.0 * k2[i] / 512.0
						+ 575.0 * k3[i] / 13824.0 + 44275.0 * k4[i] / 110592.0
						+ 253.0 * k5[i] / 4096.0;
			}

			dX = derivFunction.value(yd);
			for (int i = 0; i <= numberOfOdes - 1; i++) {
				k6[i] = stepSize * dX[i];
			}

			for (int i = 0; i <= numberOfOdes - 1; i++) {
				y5[i] = y[i] + 2825.0 * k1[i] / 27648.0 + 18575.0 * k3[i]
						/ 48384.0 + 13525.0 * k4[i] / 55296.0 + 277.0 * k5[i]
						/ 14336.0 + k6[i] / 4.0;
				y6[i] = y[i] + 37 * k1[i] / 378.0 + 250.0 * k3[i] / 621.0
						+ 125.0 * k4[i] / 594.0 + 512.0 * k6[i] / 1771.0;
				
				/*Calc error (but don't do anything with it in this simple test*/
				error = Math.abs(y6[i] - y5[i]);

			}

			return y5;

		} catch (SolverException se) {
			System.err.println(" - " + se.getMessage());
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}

		return res;
	}

}
