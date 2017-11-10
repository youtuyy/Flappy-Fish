import java.awt.*;
import javax.swing.*;

/**
 * Create panel for drawing and showing result
 * @author Zhaoyan Lin
 * @version May 6, 2016
 */

@SuppressWarnings("serial")
public class JGraph extends JComponent{
    /** Set up MapGrid */
    private GraphGrid arrC;
    /** Set up the window size to constant */
    private int winConstant = 280;


    /** Constructor */
    JGraph(GraphGrid arr) {
        super();
        arrC = arr;
    }
    
    JGraph(int h, int w) {
        super();
        arrC = new GraphGrid(h, w);
    }
    
    JGraph() {
        super();
        arrC = new GraphGrid(winConstant/10, winConstant/10);
    }


    /** Accessor for array*/
    public GraphGrid getGrid() {
    	return arrC;
    }
    
       
    /** Manipulator for array */
    public void setPoint(int x, int y, double c) {
    	arrC.setPoint((int)x/10, (int)y/10, c);
    }
 
    /** Clear */
    public void clear(){
    	arrC.clear();
    }
    
    /** Classify */
    public int classify(){
    	return arrC.classify();
    }

    /** Display one */
    public void setOne(){
    	arrC.setOne();
    }

    /** Display one */
    public void setZero(){
    	arrC.setZero();
    }
    

    /** Draw the array */
    public void paintComponent(Graphics g) {

        for (int i = 0; i < arrC.getArrHeight(); i++) {
            for (int j = 0; j < arrC.getArrWidth(); j++) {
            	int grey = 255 - (int)(arrC.getPoint(i, j)*255.0);
            	Color squareColor = new Color(grey,grey,grey);
                g.setColor(squareColor);
                g.fillRect(i*10-5, j*10-5, 10, 10);
            }
        }
    }

    /** The minimum dimension */
    public Dimension getMinimumSize() {
	return new Dimension(winConstant,winConstant);
    }

    /** The best-fit dimension */
    public Dimension getPreferredSize() {
	return new Dimension(winConstant,winConstant);
    }
}
