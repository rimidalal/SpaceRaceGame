import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


//The specifications for the aliens are defined in this class

public class Enemy {

    //The fields such as velocity, and coordinates are defined and initialized below
    public int x, y;
    private int gridLength, gridWidth;
    private int xa;
    private int ya;
    private boolean fire;
    private BufferedImage img1 = null;
    private BufferedImage img2 = null;
    private boolean hit;

    public Enemy(int x, int y, int w, int l) {
        this.x = x;
        this.y = y;
        this.gridWidth = w;
        this.gridLength = l;
        this.xa = 1;
        this.ya = 0;
        this.fire = false;
        this.hit = false;
        try {
            this.img1 = ImageIO.read(new File("enemy.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void setYa(int ya) {
        this.ya = ya;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

    public boolean getHit() {
        return this.hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }


    public void paint(Graphics2D g2d) {
        if (!this.getHit()) {
            g2d.drawImage(img1, x, y, null);
        }
    }


    public void move(int direction) {
        x += xa * direction;

    }

    /*public void collision(PlayerBullet pb) {
        final int half_width = 40;
        final int width = 80;
        if ((pb.getX() > this.getX() - half_width && pb.getX() < this.getX() + half_width) && (pb.getY() > this.y - half_width && pb.getY() < this.y + half_width)) {
            this.setHit(true);
            pb.setHit(true);
        }
    }*/

} 