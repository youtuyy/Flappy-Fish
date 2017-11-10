
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;


/**
 * Game GUI
 * Revised from Github code for FlappyBird by Zhaoyan Lin
 * @author https://github.com/john525/Flappy-Bird-Clone
 * @version May 6, 2016
 */
public class FlappyFish implements ActionListener, KeyListener {
    
    public static final int FPS = 60, WIDTH = 640, HEIGHT = 560;
    
    private Fish fish;
    private JFrame frame;
    private GamePanel panel;
    private ArrayList<Rectangle> rects;
    private int time, scroll;
    private Timer t;
    
    private boolean paused;
    
    public void go() {
        frame = new JFrame("Flappy Fish”);
        fish = new Fish();
        rects = new ArrayList<Rectangle>();
        panel = new GamePanel(this, fish, rects);
        frame.add(panel);
        
        frame.setSize(WIDTH+280, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(this);
        PointMouseListener pml = new PointMouseListener();
        panel.addMouseListener(pml);
        panel.addMouseMotionListener(pml);
        paused = true;
        
        t = new Timer(2000/FPS, this);
        t.start();
    }
    public static void main(String[] args) {
        new FlappyFish().go();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
        if(!paused) {
            fish.physics();
            if(panel.up){
        		fish.jump();
        	}else{
        		fish.dive();
        	}
            if(scroll % 120 == 0) {
                Rectangle r = new Rectangle(WIDTH, 0, GamePanel.PIPE_W, (int) ((Math.random()*HEIGHT)/5f + (0.2f)*HEIGHT));
                int h2 = (int) ((Math.random()*HEIGHT)/5f + (0.2f)*HEIGHT);
                Rectangle r2 = new Rectangle(WIDTH, HEIGHT - h2, GamePanel.PIPE_W, h2);
                rects.add(r);
                rects.add(r2);
            }
            ArrayList<Rectangle> toRemove = new ArrayList<Rectangle>();
            boolean game = true;
            for(Rectangle r : rects) {
                r.x-=3;
                if(r.x + r.width <= 0) {
                    toRemove.add(r);
                }
                if(r.contains(fish.x, fish.y)) {
                    JOptionPane.showMessageDialog(frame, "You lose!\n"+"Your score was: "+time+".");
                    game = false;
                }
            }
            rects.removeAll(toRemove);
            time++;
            scroll++;

            if(fish.y > HEIGHT || fish.y+Fish.RAD < 0) {
                game = false;
            }

            if(!game) {
                rects.clear();
                fish.reset();
                panel.clear();
                time = 0;
                scroll = 0;
                paused = true;
            }
        }
        else {
            
        }
    }
    
    public int getScore() {
        return time;
    }
    
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE) {
            paused = false;
        }else if(e.getKeyCode()==KeyEvent.VK_ENTER){
        	System.out.println(panel.classify());
        	panel.clear();
        }
    }
    public void keyReleased(KeyEvent e) {
        
    }
    public void keyTyped(KeyEvent e) {
        
    }
    
    public boolean paused() {
        return paused;
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
        	panel.setPoint(e.getX()-WIDTH, e.getY(), 1.0);
        	panel.repaint();
            
        }
        public void mouseMoved(MouseEvent e) {}
    }
}