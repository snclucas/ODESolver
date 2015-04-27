package com.blueapogee.solver;

import com.blueapogee.DerivFunction;
import com.blueapogee.SolverResults;
import com.blueapogee.exception.SolverException;

/**
 * Abstract solver class.
 */
public abstract class ODESolver {
	
	/** Name of the solver */
	protected String solverName;
	
	/** Derivative function to be solved */
	protected DerivFunction derivFunction;
	
	/** Total time for the solver */
	protected double totalTime;
	
	/** Solver step size */
	protected double stepSize;
	
	/** Number of ODEs within the derivative function */
	protected int numberOfOdes;
	
	/** Results array */
	double[] results;
	
	/**Results to send to the solver results*/
	double solvResults[][];
	
	/** Solver completed*/
	protected boolean solverCompleted = false;
	
	/**
	 * Constructor.
     *
     * @param derivFunction  derivative function
     * @param totalTime  total time
     * @param h  derivative solver step size
	 */
	public ODESolver(DerivFunction derivFunction, SolverParams params) {
		this.derivFunction = derivFunction;
		numberOfOdes = derivFunction.getNumberODE();
		this.totalTime = params.getTotalTime();
		this.stepSize = params.getStepSize();
		solvResults = new double[(int)(totalTime/stepSize)][numberOfOdes];
	}
	
	
	/**
	 * Solves 
	 */
	public void solve() {

		/* Total time steps */
		int totalSteps = (int) (totalTime / stepSize);

		/* Results array */
		 results = new double[numberOfOdes];

		/* Get and set the initial values */
		double[] values = derivFunction.getInitialValues();

		/* Solver loop */
		for (int i = 0; i < totalSteps; i++) {
			values = doStep(values);
			/* Set the next inputs to the last values */
			solvResults[i] = values;
		}
		solverCompleted = true;
	}
	
	
	/**
	 * Performs a solver step.
     *
     * @param X0  input values
     * 
     * @return array of outputs.
     *              
	 */
	protected abstract double[] doStep(double[] X0);
	
	
	
	public SolverResults getResults() throws SolverException{
		
		if(solverCompleted) {
			return new SolverResults(solvResults, stepSize, totalTime, this.solverName + " {Total time: " + this.totalTime + ", steps: " + this.stepSize + "}");
		}
		else {
			throw new SolverException("Solver has not completed");
		}
		
	}
	

	
	
	
	
	
	
	/**
     * Gets the name of the solver.
     */
	public String getName() {
		return solverName;
	}
	
	/**
     * Sets the name of the solver.
     */
	public void setName(String _name) {
		this.solverName = _name;
	}
	
	
	/**
     * Gets the derivative function.
     */
	public DerivFunction getDerivFunction() {
		return derivFunction;
	}
	
	/**
     * Sets the derivative function.
     */
	public void setDerivFunction(DerivFunction _derivFunction) {
		this.derivFunction = _derivFunction;
	}
	
}


