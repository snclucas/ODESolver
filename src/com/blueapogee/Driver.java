package com.blueapogee;

import com.blueapogee.exception.SolverException;
import com.blueapogee.solver.ODESolver;
import com.blueapogee.solver.SolverFactory;
import com.blueapogee.solver.SolverParams;

/**
 * Simple driver
 */
public class Driver {

	public Driver() {

		/* Initial values for solver */
		double[] initalValues = new double[3];

		initalValues[0] = 2.0;
		initalValues[1] = 0.1;
		initalValues[2] = 0.0;

		try {

			/* Solver function */
			DerivFunction derivFunction = new DerivFunction(3, initalValues);

			/* Compare solvers */
			
			double time = 4000;
			double step = 0.01;
			
			SolverParams solverParams = new SolverParams(time, step);
			
			ODESolver[] solvers = new ODESolver[]{
					SolverFactory.EULER.get(derivFunction, solverParams),
					SolverFactory.RK.get(derivFunction, solverParams), 
					SolverFactory.RKCK.get(derivFunction, solverParams)};
				
			for(ODESolver solver : solvers) {
				solver.solve();
				SolverResults results = solver.getResults();
				
				System.out.println("Solver results from solver "+ results.getComment());
				System.out.println("Max X: " + results.getMaximum(0));
				System.out.println("Max Y: " + results.getMaximum(1));
				System.out.println("Arb value at time 2000: X= "
						+ results.getValueAtTime(2000, 0) + " Y= "
						+ results.getValueAtTime(2000, 1));
				
				System.out.println("");
			}
			

			

		} catch (SolverException se) {
			System.err.println("Solver Exception: " + se.getMessage());
		} catch (Exception ex) {
			System.err.println("Exception: " + ex.getMessage());
		}

	}

	public static void main(String[] args) {
		new Driver();
	}

}
