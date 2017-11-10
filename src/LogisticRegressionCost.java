import optimization.Minimizable;
import java.lang.Math;
public class LogisticRegressionCost implements Minimizable{
	private double[][] x;
	private int[] y;
	private final double  EPSILON = 0.00001;
	private final double PRECISION = 1e-9;
	public LogisticRegressionCost(double[][] x, int[] y){
		this.x = x;
		this.y = y;
	}
	
	
	public static double sigmoid(double z){
		return 1.0/(1.0+Math.pow(Math.E,-z));
	}
	
	public double evaluateFunction(double[] theta, double[] gradient){
		double cost = 0;
		double[] h = new double[x.length];
		for (int i = 0; i < x[0].length; i++){
			gradient[i] = 0;
		}
		for (int i = 0; i < x.length; i++){
			//System.out.println(cost);
			h[i] = sigmoid(dotProduct(theta, x[i]));
			//System.out.println("h"+h[i]);
			if((y[i]==0&&h[i]==1.0)||(y[i]==1&&h[i]==0.0)){
				cost = cost+Double.POSITIVE_INFINITY;
			}else{
			cost = cost - y[i]*Math.log(h[i])-(1-y[i])*Math.log(1.000000001-h[i]);
			}
		}

		for (int j = 0; j < x[0].length; j++){
			double g = 0;
			for (int i = 0; i < x.length; i++){
				
				g += x[i][j]*(h[i] - y[i]);
			}
			gradient[j] = g;
			//System.out.println("g"+j+"="+g);
		}
		return cost;
	}
	
	public static double dotProduct(double[] m, double[] n){
		double p = 0;
		for (int i = 0; i < m.length; i++){
			p += m[i]*n[i];
		}
		return p;
	}
	
	// for *linear* regression, compute gradient numerically, check against analytic gradient



	public void checkLinearRegressionNumericalGradient(double[] theta) {



		System.out.println("in");

	// compute the analytic grad

	double[] grad = new double[theta.length];




	    double[] numgrad = new double[theta.length];

	    for (int i=0; i < theta.length; i++) {

	       

	        double[] ei = new double[theta.length];

	        ei[i] = EPSILON;

	       

	        // add a little bit to theta[i]

	        double[] thetaPlus = new double[theta.length];

	        for (int j=0; j < theta.length; j++) { thetaPlus[j] = theta[j] + ei[j]; }

	        double costPlus_i = evaluateFunction(thetaPlus, grad);

	       

	        // subtract a little bit from theta[i]

	        double[] thetaMinus = new double[theta.length];

	        for (int j=0; j < theta.length; j++) { thetaMinus[j] = theta[j] - ei[j]; }

	        double costMinus_i = evaluateFunction(thetaMinus, grad);



	        numgrad[i] = (costPlus_i - costMinus_i)/(2*EPSILON);

	    }

	   

	    // check the numerical gradient

	    evaluateFunction(theta, grad); // sets the analytic gradient

	    double avgDiff = 0;

	    for (int i=0; i < theta.length; i++) {

	    avgDiff += Math.abs(numgrad[i] - grad[i]);

	    }

	    avgDiff = avgDiff/theta.length;
	    
	    System.out.println(avgDiff);

	    assert(avgDiff < PRECISION);

	}

}
