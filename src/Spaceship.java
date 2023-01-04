import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

public class Spaceship {
    private int x;
    private int y;
    private int xa;
    private int ya;
    private boolean hit;
    int hitCount = 3;
    private BufferedImage spaceship = null;
    private BufferedImage spaceshipExhaust = null;
    private BufferedImage spaceshipBack = null;   
    private BufferedImage spaceshipLeft = null;
    private BufferedImage spaceshipRight = null;
    private BufferedImage bullet = null;
    

    private boolean right = false, left = false;
    private Boolean up = false, down = false;

    private int counter = 1;

    public Spaceship(int x, int y, int xa, int ya) {
        try {
            spaceship = ImageIO.read(new File("SpaceshipDefault.png"));
            spaceshipLeft = ImageIO.read(new File("SpaceshipLeft.png"));
            spaceshipRight = ImageIO.read(new File("SpaceshipRight.png"));
        } catch (IOException e) {
            System.out.println("No Image");
        }

        this.x = x;
        this.y = y;
        this.xa = xa;
        this.ya = ya;
        this.hit = false;
    }

    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.setLeft(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        	this.setRight(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
        	this.setUp(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        	this.setDown(true);
        }
    }
    
    public void keyReleased(KeyEvent e) {
        // When the key is released, set the Boolean to false, and change
        // acceleration to 0
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        	this.left = false;
            xa = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        	this.right = false;
            xa = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
        	this.up = false;
            ya = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        	this.down = false;
            ya = 0;
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

    public boolean getUp() {
        return this.up;
    }
    
    public void setUp(boolean up) {
        this.up = up;
    }
    public boolean getDown() {
        return this.down;
    }
    
    public void setDown(boolean down) {
        this.down = down;
    }
    public boolean getLeft() {
        return this.left;
    }
    
    public void setLeft(boolean left) {
        this.left = left;
    }
    public boolean getRight() {
        return this.right;
    }
    
    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean getHit() {
        return this.hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }
    

    public void move() {

        if (this.right) {
            xa = 2;
        }
        if (this.left) {
            xa = -2;
        }
        /*if (this.down) {
            ya = 3;
        }
        if (this.up) {
            ya = -3;
        }*/
        this.setX(this.getX()+xa);
        this.setY(this.getY()+ya);
        
		//Check if spaceship hits border
        if ((x>=1280)||(x<=-80)) {
            this.setX(720);
        }
        else {
            this.x+=xa;
        }
    }

    public void paint(Graphics2D g) {
        //If spaceship is moving forward add exhaust trail
        /*if (getUp()) {
            g.drawImage(spaceshipExhaust, getX(), getY(), null);
        }
        else if (getDown()) {
            g.drawImage(spaceshipBack, getX(), getY(), null);       	
        }*/
        if (getLeft()) {
            g.drawImage(spaceshipLeft, getX(), getY(), null);
        }
        else if (getRight()) {
            g.drawImage(spaceshipRight, getX(), getY(), null);
        }
        else {
            g.drawImage(spaceship, getX(), getY(), null);
        }
    
    }
    public boolean collision(EnemyBullet eb) {

        if (eb.getX() >= this.getX()+40 && eb.getX() <= this.getX()+100 && eb.getY() >= this.getY()-40 && eb.getY() <= this.getY() + 100) {
            eb.setHit(true);
            this.setHit(true);
            return true;

        }
        return false;
    }
}