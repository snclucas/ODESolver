package com.blueapogee;

import com.blueapogee.exception.SolverException;

/**
 * Class to hold solver outputs. Should be generic. TO allow the rest of the
 * codebase to use doubles and not Doubles, I have implemented a basic loop to
 * find the max/min instead of using Collections.Max etx which would require
 * Double arrays.
 * 
 * In addition, the approach to build up the results as the solver progresses is
 * not sued to allow maximum performance of the solver.
 */
public class SolverResults {

	/** Rank or variables */
	private int rank;

	/** Data array of results from the solver */
	private double[][] solverOutput;

	/** Stepsize used in the solver */
	private double stepSize;

	/** Total time used in the solver */
	private double totalTime;

	/** Comment for the results */
	private String comment;

	/**
	 * Constructor
	 * 
	 * @param solverOutput
	 *            the resulrs from the solver
	 * @param stepSize
	 *            stepsize used by the solver
	 * @param totalTime
	 *            the totalTime used by the solver
	 * @param comment
	 *            the comment for the results
	 * 
	 * 
	 * @return the maximum value
	 * */
	public SolverResults(double[][] solverOutput, double stepSize,
			double totalTime, String comment) {
		this.rank = solverOutput.length;
		this.solverOutput = solverOutput;
		this.stepSize = stepSize;
		this.totalTime = totalTime;
		this.comment = comment;
	}

	/**
	 * Gets the maximum value of that rank/dimension
	 * 
	 * @param rank
	 *            the rank
	 * 
	 * @return the maximum value
	 * */
	public double getMaximum(int rank) {
		double maxValue = 0.0;
		for (int i = 0; i <= solverOutput.length - 1; i++) {
			maxValue = Math.max(solverOutput[i][rank], maxValue);
		}
		return maxValue;
	}

	/**
	 * Gets the minimum value of that rank/dimension
	 * 
	 * @param rank
	 *            the rank
	 * 
	 * @return the minimum value
	 * */
	public double getMinimum(int rank) {
		double minValue = solverOutput[0][rank];

		for (int i = 0; i <= solverOutput.length - 1; i++) {
			minValue = Math.max(solverOutput[i][rank], minValue);
		}
		return minValue;
	}

	/**
	 * Gets the value of the rank dimention at a specified step
	 * 
	 * @param step
	 *            the step
	 * @param rank
	 *            the rank
	 * 
	 * @return the value of the rank at that step
	 * */
	public double getValueAtStep(int step, int rank) throws SolverException {

		int maxSteps = (int) (totalTime / stepSize);

		if (step > maxSteps)
			throw new SolverException("Outside solver time.");

		return solverOutput[step][rank];
	}

	
	/**
	 * Prints the data of rank dimension
	 * 
	 * @param rank
	 *            the rank
	 * */
	public void print(int rank) {

		int maxSteps = (int) (totalTime / stepSize);

		for (int i = 0; i <= maxSteps - 1; i++) {

			System.out.println(i + " " + solverOutput[i][rank]);

		}

	}

	/**
	 * Gets the value of the rank dimention at a specified time
	 * 
	 * @param time
	 *            the time
	 * @param rank
	 *            the rank
	 * 
	 * @return the value of the rank at that time
	 * */
	public double getValueAtTime(double time, int rank) throws SolverException {

		if (time > totalTime)
			throw new SolverException("Outside solver time.");

		int step = (int) (time / stepSize);
		return solverOutput[step][rank];
	}

	/**
	 * Gets the rank of the results
	 * */
	public int getRank() {
		return rank;
	}

	/**
	 * Gets the step size of the solver that produced the results
	 * */
	public double getStepSize() {
		return stepSize;
	}

	/**
	 * Gets the total time of the solver that produced the results
	 * */
	public double getTotalTime() {
		return totalTime;
	}

	/**
	 * Gets the results comment
	 * */
	public String getComment() {
		return comment;
	}

}
