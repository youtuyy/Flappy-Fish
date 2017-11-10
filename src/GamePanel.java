/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * GamePanel to display game window and input window
 * Revised from Github code for FlappyBird by Zhaoyan Lin
 * @author https://github.com/john525/Flappy-Bird-Clone
 * @version May 6, 2016
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {
    /** Set up GraphGrid */
    private GraphGrid arrC;
    
    /** Set up Fish Object */
    private Fish fish;
    
    /** Other parameters for the panel */
    private ArrayList<Rectangle> rects;
    private FlappyFish fb;
    private Font scoreFont, pauseFont;
    public static final Color bg = new Color(0, 130, 158);
    public static final int PIPE_W = 50, PIPE_H = 30;
    private Image pipeHead, pipeLength;
    public boolean up;

    /** Constrctor */
    public GamePanel(FlappyFish fb, Fish fish, ArrayList<Rectangle> rects) {
    	super();
    	this.fb = fb;
        this.fish = fish;
        this.rects = rects;
        scoreFont = new Font("Comic Sans MS", Font.BOLD, 18);
        pauseFont = new Font("Arial", Font.BOLD, 48);
        
        arrC = new GraphGrid(28, 28);
        try {
        	pipeHead = ImageIO.read(new File("/Users/zhaoyanlin/Documents/workspace/dl4j/src/main/java/78px-Pipe.png"));
        	pipeLength = ImageIO.read(new File("/Users/zhaoyanlin/Documents/workspace/dl4j/src/main/java/pipe_part.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
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
    	int result = arrC.classify();
    	if(result == 1){
    		up = true;
    	}else{
    		up = false;
    	}
    	return result;
    }


    @Override
    public void paintComponent(Graphics g) {
    	for (int i = 0; i < arrC.getArrHeight(); i++) {
            for (int j = 0; j < arrC.getArrWidth(); j++) {
            	int grey = 255 - (int)(arrC.getPoint(i, j)*255.0);
            	Color squareColor = new Color(grey,grey,grey);
                g.setColor(squareColor);
                g.fillRect(FlappyFish.WIDTH+i*10-5, j*10-5, 10, 10);
            }
        }
        g.setColor(bg);
        g.fillRect(0,0,FlappyFish.WIDTH,FlappyFish.HEIGHT);
        fish.update(g);
        g.setColor(Color.RED);
        for(Rectangle r : rects) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.GREEN);
            //g2d.fillRect(r.x, r.y, r.width, r.height);
            AffineTransform old = g2d.getTransform();
            g2d.translate(r.x+PIPE_W/2, r.y+PIPE_H/2);
            if(r.y < FlappyFish.HEIGHT/2) {
                g2d.translate(0, r.height);
                g2d.rotate(Math.PI);
            }
            g2d.drawImage(pipeHead, -PIPE_W/2, -PIPE_H/2, GamePanel.PIPE_W, GamePanel.PIPE_H, null);
            g2d.drawImage(pipeLength, -PIPE_W/2, PIPE_H/2, GamePanel.PIPE_W, r.height, null);
            g2d.setTransform(old);
        }
        g.setFont(scoreFont);
        g.setColor(Color.BLACK);
        g.drawString("Score: "+fb.getScore(), 10, 20);
        if(up){
        	g.drawString("Mode: UP", 10, 40);
        }else{
        	g.drawString("Mode: DOWN", 10, 40);
        }
        g.drawString("Instruction:", 650, 300);
        g.drawString("write 1 to go UP", 650, 340);
        g.drawString("0 to go DOWN", 650, 380);
        g.drawString("ENTER to set", 650, 420);
        
        if(fb.paused()) {
            g.setFont(pauseFont);
            g.setColor(new Color(0,0,0,170));
            g.drawString("PAUSED", FlappyFish.WIDTH/2-100, FlappyFish.HEIGHT/2-100);
            g.drawString("PRESS SPACE TO BEGIN", FlappyFish.WIDTH/2-300, FlappyFish.HEIGHT/2+50);
        }
    }
}