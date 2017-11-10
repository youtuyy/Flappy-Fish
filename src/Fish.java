
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Fish class for the object
 * Revised from Github code for FlappyBird by Zhaoyan Lin
 * @author https://github.com/john525/Flappy-Bird-Clone
 * @version May 6, 2016
 */
public class Fish {
    public float x, y, vx, vy;
    public static final int RAD = 25;
    private Image img;
    public Fish() {
        x = FlappyFish.WIDTH/2;
        y = FlappyFish.HEIGHT/2-40;
        try {
            img = ImageIO.read(new File("/Users/zhaoyanlin/Documents/workspace/dl4j/src/main/java/splashyfish.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void physics() {
        x+=vx;
        y+=vy;
    }
    public void update(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawImage(img, Math.round(x-RAD),Math.round(y-RAD),2*RAD,2*RAD, null);
    }
    public void jump() {
        vy = -1;
    }
    public void dive() {
        vy = +1;
    }
    
    public void reset() {
        x = 320;
        y = 240;
        vx = vy = 0;
    }
}