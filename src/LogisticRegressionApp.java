import optimization.Minimizable;
import riso.numerical.LBFGS;
import riso.numerical.LBFGS.ExceptionWithIflag;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.commons.lang.ArrayUtils;

/**
 * Application to test the result of logistic regression
 * write a csv file of thetas
 * @author Zhaoyan Lin
 * @version May 6, 2016
 */

public class LogisticRegressionApp {
	// parameters we must choose for LBFGS

	private static final int MAX_ITER = 10000;

	private static final int M_LBFGS = 5;        // num corrections (3 <= m <= 7), larger will increase running time, m=5 supported by other implementations

	private static final boolean DIAGCO = false; // we're not providing the diagonal for the co-variance matrix (?) other implementations support "false"

	private static final int[] iPRINT = {1, 1}; // first value: 1 for info at each iteration, 0 for only first and last iteration info, -1 for no output

	private static final int[] iFLAG = {0};      // start off with flag 0

	private static final double EPS = 1e-5;      // precision of the output, supported by other implementations

	private static final double XTOL = 1e-16;    // machine precision
	
    public static double[] mean;
    
    public static double[] std;
    
    public static double[] t;	
    
    public static boolean trained = false;
    
	public static void main(String[] args){
		if (!trained){
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		LinkedList<LinkedList<Double>> x = new LinkedList<LinkedList<Double>>();
		LinkedList<Integer> y = new LinkedList<Integer>();
		try {

			br = new BufferedReader(new FileReader("/Users/zhaoyanlin/Documents/workspace/dl4j/src/main/java/mnist_train.csv"));
			while ((line = br.readLine()) != null) {

			        // use comma as separator
				String[] one = line.split(cvsSplitBy);
				
				if(one[0].equals("0")||one[0].equals("1")){
					y.add(Integer.parseInt(one[0]));
					LinkedList<Double> example = new LinkedList<Double>();
					for (int i = 0; i < one.length; i++){
						//example.add(Double.parseDouble(one[i]));
						if(i == 0){
							example.add(1.0);
						}else{
							example.add(Double.parseDouble(one[i])/255.0);
						}
					
					}
					x.add(example);
				}

				
			}

			} catch (FileNotFoundException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
			} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			}
		int n = x.getFirst().size();
		int m = x.size();
		double[][] xArray = new double[m][n];
		double[][] pixel = new double[n-1][m];
		int[] yArray = ArrayUtils.toPrimitive(y.toArray(new Integer[m]));
		for (int i = 0; i < m; i++){
			xArray[i] = ArrayUtils.toPrimitive(x.get(i).toArray(new Double[n]));
			//System.out.println(xArray[i][1]);
		}
		for (int i = 0; i < m; i++){
			for (int j = 1; j < n; j++){
				pixel[j-1][i] = xArray[i][j];
			}
		}
		
			mean = new double[n-1];
			std = new double[n-1];
			for (int i = 0; i < n-1; i++){
				Statistic p = new Statistic(pixel[i]);
				mean[i] = p.getMean();
				std[i] = p.getStdDev();
				for(int j = 0; j < m; j++){
					xArray[j][i+1] = (xArray[j][i+1]-mean[i])/(std[i]+0.1);
				}
			}


		LinkedList<LinkedList<Double>> tx = new LinkedList<LinkedList<Double>>();
		LinkedList<Integer> ty = new LinkedList<Integer>();
		try {

			br = new BufferedReader(new FileReader("/Users/zhaoyanlin/Documents/workspace/dl4j/src/main/java/mnist_test.csv"));
			while ((line = br.readLine()) != null) {

			        // use comma as separator
				String[] tone = line.split(cvsSplitBy);

				if(tone[0].equals("0")||tone[0].equals("1")){
					ty.add(Integer.parseInt(tone[0]));
					LinkedList<Double> texample = new LinkedList<Double>();
					for (int i = 0; i < tone.length; i++){
						if(i == 0){
							texample.add(1.0);
						}else{
							texample.add(Double.parseDouble(tone[i])/255.0);
						}
					
					}
					tx.add(texample);
				}

				
			}
			

			} catch (FileNotFoundException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
			} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			}
		int tn = tx.getFirst().size();
		int tm = tx.size();
		double[][] testx = new double[tm][tn];
		int[] testy = ArrayUtils.toPrimitive(ty.toArray(new Integer[tm]));
		for (int i = 0; i < tm; i++){
			testx[i] = ArrayUtils.toPrimitive(tx.get(i).toArray(new Double[tn]));
		}
		for (int i = 0; i < tn-1; i++){
			for(int j = 0; j < tm; j++){
				testx[j][i+1] = (testx[j][i+1]-mean[i])/(std[i]+0.1);
			}
		}

		
		try {
			t = new double[n];
			t = train(xArray, yArray);
			trained = true;
			test(testx, t, testy);
			// Set up the FileWriter with our file name.
	        FileWriter saveFile = new FileWriter("Theta.csv");

	        // Write the data to the file.
	        String ths = "";
	        String means = "1";
	        String stds = "1";
	        for (double th: t){
	        	ths = ths + th + cvsSplitBy;
	        }
	        for (double mn: mean){
	        	means = means + cvsSplitBy + mn;
	        }
	        for (double st: std){
	        	stds = stds + cvsSplitBy + st;
	        }
	        saveFile.write(ths + "\n");
	        saveFile.write(means + "\n");
	        saveFile.write(stds + "\n");
	        // All done, close the FileWriter.
	        saveFile.close();
		} catch (ExceptionWithIflag e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	}
	

	public static double[] normal (double[] x, double[] mean, double[] std){
		for (int i = 0; i < x.length; i++){
				x[i] = (x[i]-mean[i])/(std[i]+0.1);
		}
		return x;
	}

	
	public static double[] train(double[][] x, int[] y) throws ExceptionWithIflag{
		// define autoencoder cost


		Minimizable cost = new LogisticRegressionCost(x, y);
		
		int m = x.length;
		
		int n = x[0].length;
		
		double[] theta = new double[n];
		
		double[] grad = new double[n];
		
		for (int i = 0; i < n; i++){
			theta[i] = Math.random()*0.001;
			//grad[i] = 0;
		}
		
		//((LogisticRegressionCost) cost).checkLinearRegressionNumericalGradient(theta);
		
		double[] diag = new double[m];
		
		// train autoencoder using LBFGS

		int nIter = 0;

		iFLAG[0] = 0; // reinitialize iFLAG - this is important since we're doing multiple optimization routines in the same function

		        while (nIter < MAX_ITER) {

		            double f = cost.evaluateFunction(theta, grad);

		            System.out.println(f);
		            LBFGS.lbfgs(n, M_LBFGS, theta, f, grad, DIAGCO, diag, iPRINT, EPS, XTOL, iFLAG);

		            if (iFLAG[0] <= 0) break; // we have finished

		            nIter++;

		        }

		       

		        // print the best (lowest) cost - since theta has been overwritten each time, can just evaluate at theta

		        System.out.println("finished training in " + nIter + " function evaluations");

		        System.out.println("cost: " + cost.evaluateFunction(theta, grad));
		        
		        return theta;
		        
		       
		}
	
	public static void test(double[][] x, double[] t, int[] y){
		int accurate = 0;
		for (int i = 0; i < x.length; i++){
			int r = classify(x[i],t);
			if(r == y[i]){
				accurate++;
			}
		}
		double accuracy = accurate/(double)y.length;
		System.out.println("The accuracy is "+accuracy*100+"%");
	
	}
	
	public static int classify(double[] x, double[] t){
		return (int)Math.rint(LogisticRegressionCost.sigmoid(LogisticRegressionCost.dotProduct(t,x)));
	}
}

