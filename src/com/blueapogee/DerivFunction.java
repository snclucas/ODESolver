package com.blueapogee;

import com.blueapogee.exception.SolverException;


/**
* Dummy class to hold a generic derivative function. Not implemented well, just enough to allow 
* use within this test.
* This should handle much more about the function and allow any function to be inputted.
*/
public class DerivFunction {

	/** Number of ODEs */
	private int N;
	
	/** Initial values for the functions*/
	private double[] initialValues;

	
	/**
        * Constructor.
     */
	public DerivFunction(int _N, double[] _initialValues) {
		this.N = _N;
		this.initialValues = _initialValues;
	}
	

	/**
     * Gets the intial values for the function.
     */
	public double[] getInitialValues() {
		return initialValues;
	}

	
	/**
     * Sets the intial values for the function.
     */
	public void setInitialValues(double[] initialValues) {
		this.initialValues = initialValues;
	}

	
	/**
     * Gets the number of ODEs.
     */
	public int getNumberODE() {
		return N;
	}




	/**
	 * Calculates the derivative function based on suppied inputs. Hardcoded for this example just for this test.
	 * Would ordinarily have a class to construct any user defined function
     *
     * @param inputs  array of inputs
     * 
     * @return array of outputs.
     *              
	 * @throws SolverException  If the supplied inputs array is larger that N 
	 */
	public double[] value(double inputs[]) throws SolverException
	{

		if(N != inputs.length) throw new SolverException("Invalid arguments");

		double[] derivs = new double[3];

		derivs[0] = 6.0e-3 * inputs[0] - 1.2e-2 * inputs[0] * inputs[1];
		derivs[1] = 1.2e-2 * inputs[0] * inputs[1] - 8.0e-3 * inputs[1];
		derivs[2] = 8.0e-3 * inputs[1];

		return derivs;
	}


}
