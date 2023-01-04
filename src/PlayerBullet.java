import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayerBullet {
    private final int ya;
    private final int BULLET_DIMS = 44;
    private boolean space = false;
    private BufferedImage bullet;
    private int x;
    private int y;
    private Spaceship s;
    private boolean hit;

    public PlayerBullet(Spaceship s) {
        this.hit = false;
        try {
            bullet = ImageIO.read(new File("PlayerBullet.png"));

        } catch (IOException e) {
            System.out.println("No Image");
        }
        this.space = false;
        this.x = s.getX();
        this.y = s.getY();
        this.ya = 7;
    }

    public boolean getHit() {
        return this.hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.setSpace(true);
        }
    }

    public void keyReleased(KeyEvent e) {
        //this.setSpace(!this.getSpace());
    }

    private boolean getSpace() {
        return this.space;
    }

    private void setSpace(boolean space) {
        this.space = space;
    }

    public int getX() {
        return this.x;
    }

    private void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return this.y;
    }

    private void setY(int y) {
        this.y = y;
    }


    public void paint(Graphics g) {
        g.drawImage(bullet, this.getX()+45, this.getY(), null);
    }

    public void move() {
        if (this.getSpace()) {
            this.setY(this.getY() - this.ya);
        }
    }

    public boolean collision(Enemy e) {
        final int enemyDims = 60;

        /*if (((this.getX() + enemyDims/2) > e.getX() && (this.getX() + enemyDims/2) < (e.getX() + enemyDims)) && (this.getY() <= e.getY()+enemyDims)) {
            System.out.println("enemy x " + e.getX() + " y " + e.getY());
            System.out.println("bullet x " + getX() + " y " + getY());
            System.out.println("Hit!");
            e.setHit(true);
            this.setHit(true);
            return true;
        }*/
        if (!e.getHit() && (this.getX() > (e.getX() - enemyDims) && this.getX() < (e.getX() - enemyDims/2)) && (this.getY() > (e.getY()) && this.getY() < (e.getY() + enemyDims))) {
            System.out.println("enemy x " + e.getX() + " y " + e.getY());
            System.out.println("bullet x " + getX() + " y " + getY());
            System.out.println("Hit!");
            e.setHit(true);
            this.setHit(true);
            return true;
        }
        return false;
    }
}
