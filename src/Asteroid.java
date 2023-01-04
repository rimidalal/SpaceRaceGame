import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

public class Asteroid {
	private int x;
	private int y;
	private int xa;
	private int ya; 
	private Main main; 
	private BufferedImage asteroid = null;
	
	private static final int DIAMETER = 65;
	
	public Asteroid(Main main, int x, int y, int xa, int ya) {
        try {
            asteroid = ImageIO.read(new File("Asteroid.png"));
        } catch (IOException e) {
            System.out.println("No Image");
        }
		this.main = main;
		this.x = x;
		this.y = y;
		this.xa = xa;
		this.ya = ya;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getXA() {
		return xa;
	}
	public int getYA() {
		return ya;
	}

	public void move() {
		if ((x + xa < 0) || (x + xa > main.getWidth() - 90 - DIAMETER)) {
			xa *= -1;
		}
		if ((y + ya < 0) || (y + ya > main.getHeight() - 80 - DIAMETER)) {
			ya *= -1;
		}
		
		x = x + xa; // Adjust the x and y coordinates
		y = y + ya; // by the appropriate speeds
	}
	
	public void collision(Asteroid a) {
		 int dx = (x-a.x) + (xa-a.xa);
		 int dy = (y-a.y) + (ya-a.ya);
		 if (Math.sqrt(dx*dx+dy*dy)<=DIAMETER) {
			 //switch velocities
			 int tempxa = xa;
			 int tempya = ya;
			 xa = a.xa;
			 ya = a.ya;
			 a.xa = tempxa;
			 a.ya = tempya;
		 }
	}

	public void paint(Graphics2D g2d) {
		g2d.drawImage(asteroid, getX(), getY(), null);
	}
}
