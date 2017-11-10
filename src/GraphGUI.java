import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
     

/**
 * GUI to test and show the classification of 1 and 0
 * @author Zhaoyan Lin
 * @version May 6, 2016
 */

public class GraphGUI {
    /** Graph */
    JGraph g;
    JGraph s;
    

    /**
     *  Schedules a job for the event-dispatching thread
     *  creating and showing this application's GUI.
     */
    public static void main(String[] args) {

        final GraphGUI GUI = new GraphGUI();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    GUI.createAndShowGUI();
                }
            });
    }

    /** Sets up the GUI window */
    public void createAndShowGUI() {
        // Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Create and set up the window.
        JFrame frame = new JFrame("Graph GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add components
        createComponents(frame);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /** Puts content in the GUI window */
    public void createComponents(JFrame frame) {
        // graph display
        Container pane = frame.getContentPane();
        pane.setLayout(new FlowLayout());
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1,2));
        g = new JGraph(28, 28);
        s = new JGraph();
        
        PointMouseListener pml = new PointMouseListener();
        g.addMouseListener(pml);
        g.addMouseMotionListener(pml);
        panel1.add(g);
        panel1.add(s);

        // controls
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1,1));
        JButton clearButton = new JButton("Clear All");
        panel2.add(clearButton);
        clearButton.addActionListener(new clearListener());
        JButton learnButton = new JButton("Learn");
        panel2.add(learnButton);
        learnButton.addActionListener(new learnListener());
        pane.add(panel1);
        pane.add(panel2);
        
    }


    

 
    
    /** Listener for clear button */
    private class clearListener implements ActionListener {
        /** Event handler for clear button */
        public void actionPerformed(ActionEvent e) {	
            g.clear();
            s.clear();
            g.repaint();
            s.repaint();
        }
    }
    
    /** Listener for learn button */
    private class learnListener implements ActionListener {
        /** Event handler for clear button */
        public void actionPerformed(ActionEvent e) {
        	int result = g.classify();
        	if(result == 1){
        		s.setOne();
        	}else{
        		s.setZero();
        	}
        	s.repaint();
        }
    }
    

    
    
    /** Mouse listener for PointCanvas element */
    private class PointMouseListener extends MouseAdapter
        implements MouseMotionListener {

        /** Responds to click event depending on mode */
        public void mouseClicked(MouseEvent e) {
        	
        }

        /** Records point under mousedown event in anticipation of possible drag */
        public void mousePressed(MouseEvent e) {

        }

        /** Responds to mouseup event */
        public void mouseReleased(MouseEvent e) {

        }

        /** Responds to mouse drag event */
        public void mouseDragged(MouseEvent e) {
        	g.setPoint(e.getX(), e.getY(), 1.0);
        	g.repaint();
            
        }
        public void mouseMoved(MouseEvent e) {}
    }
}
