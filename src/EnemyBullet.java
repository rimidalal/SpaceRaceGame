import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
public class EnemyBullet {
	private int x, y;
    private boolean fire;
    private BufferedImage img;
    private Enemy e;
    private boolean hit;
	public EnemyBullet(Enemy e) {
		try {
            img = ImageIO.read(new File("EnemyBullet.png"));
        } catch (IOException ignored) {
        }
        this.x = e.getX();
        this.y = e.getY();
        this.e = e;
        this.hit = false;
	}
	public void setX(int x) {
        this.x = x;
    }
    public int getX() {
        return this.x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getY() {
        return this.y;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean getHit() {
        return this.hit;
    }
	public void paint(Graphics2D g2d) {
        g2d.drawImage(this.img, this.x, this.y + 70, null);
    }
	
	public void move() {
        this.setY(this.getY() + 1);
    }

    public void collision(Spaceship s) {
        /*if (this.getX() >= s.getX()+50 && this.getX() <= s.getX()+150 && this.getY() >= s.getY()-50 && this.getY() <= s.getY() + 100) {
            System.out.println("hit");
            this.setHit(true);
        }*/
    }
}
