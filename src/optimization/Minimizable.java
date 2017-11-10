package optimization;

/**

 * evaluateFunction should take initial guess x, and return function value at x while updating the gradient

 * @author versley

 */

public interface Minimizable {

    public double evaluateFunction(double[] theta, double[] gradient);

}