
/**
 * Grid for panel
 * @author Zhaoyan Lin
 * @version May 6, 2016
 */

public class GraphGrid {
    private double[][] arrColor;
    
    
    /** Constructor */
    public GraphGrid(int h, int w){
        arrColor= new double[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                arrColor[i][j] = 0.0;
            }
        }
    }

    /** Accessor for height */
    public int getArrHeight() {
	return arrColor.length;
    }
    
    /** Accessor for width */
    public int getArrWidth() {
	return arrColor[0].length;
    }

    /** Accessor for square */
    public double getPoint(int h, int w) {
	return arrColor[h][w];
    }
    
    /** Accessor for square */
    public double[][] getArray() {
	return arrColor;
    }
        
     /** Manipulator for square */
    public void setPoint(int h, int w, double c) {
    	if (arrColor[h][w]!=1.0){
    	arrColor[h][w] += c;
    	}
    }
    
    
    /** Display 1 */
    public void setOne(){
    	for (int i = 0; i < getArrHeight(); i++) {
            for (int j = 0; j < getArrWidth(); j++) {
                arrColor[i][j] = 0.0;
                if((4 <= j) && (j <= (getArrHeight()-4)) && (13 <= i) && (i <= 14) ){
                	arrColor[i][j] = 1.0;
                }
            }
        }
    }
    
    /** Display 1 */
    public void setZero(){
    	for (int i = 0; i < getArrHeight(); i++) {
            for (int j = 0; j < getArrWidth(); j++) {
                arrColor[i][j] = 0.0;
                if((((j == 4) || (j == getArrWidth()-4)) && (i <= (getArrHeight()-8)) && (8 <= i) )||
                (((i == 8) || (i == getArrHeight()-8)) && (j <= (getArrWidth()-4)) && (4 <= j) ) ){
                	arrColor[i][j] = 1.0;
                }
            }
        }
    }
    
    
    /** Clear */
    public void clear(){
    	for (int i = 0; i < arrColor.length; i++) {
            for (int j = 0; j < arrColor[0].length; j++) {
                arrColor[i][j] = 0.0;
            }
        }
    }
    
    /** Classify */
    public int classify(){
    	GraphClassification gc = new GraphClassification(arrColor);
    	return gc.classify();
    }
    
    
}

