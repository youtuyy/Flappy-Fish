import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Read in Theta file and do the classification for an array of pixels
 * @author Zhaoyan Lin
 * @version May 6, 2016
 */
public class GraphClassification {
    double[] mean;
    double[] std;
    double[][] g;
    double[] theta;
    int n;
    int h;
    int w;
    
    /** Constructor */
	public GraphClassification(double[][] graph){
		g = graph;
		h = g.length;
		w = g[0].length;
		n = h * w + 1;
		mean = new double[n-1];
		std = new double[n-1];
		theta = new double[n];
		learn();
	}
	
	/** Methods */
	public int classify(){
		double[] x = new double[h*w+1];
		x[0] = 1.0;
		for (int j = 0; j < h; j++){
			for (int k = 0; k < w; k++){
				int i = j*w+k+1;
				x[i] = (g[k][j]-mean[i-1])/(0.1+std[i-1]);
			}
		}
		return LogisticRegressionApp.classify(x, theta);
		
	}
	
	public void learn(){
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		try {
			int count = 0;
			br = new BufferedReader(new FileReader("/Users/zhaoyanlin/Documents/workspace/dl4j/Theta.csv"));
			while ((line = br.readLine()) != null) {
			        // use comma as separator
				String[] one = line.split(cvsSplitBy);
				if(count == 0){
					for (int i = 0; i < n; i++){
						theta[i] = Double.parseDouble(one[i]);
					}
				}else if(count == 1){
					for (int i = 1; i < n; i++){
						mean[i-1] = Double.parseDouble(one[i]);
					}
				}else if(count == 2){
					for (int i = 1; i < n; i++){
						std[i-1] = Double.parseDouble(one[i]);
					}
				}
				count++;
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
	}
}
