package com.blueapogee.solver;

public class SolverParams {
	
	private double totalTime;
	
	private double stepSize;
	
	

	public SolverParams(double totalTime, double stepSize) {
		super();
		this.totalTime = totalTime;
		this.stepSize = stepSize;
	}

	public double getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}

	public double getStepSize() {
		return stepSize;
	}

	public void setStepSize(double stepSize) {
		this.stepSize = stepSize;
	}
	
	

}
