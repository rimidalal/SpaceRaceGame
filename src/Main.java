import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.EventListener;
import java.util.concurrent.TimeUnit;

public class Main extends JPanel {


    private final int[] gridSize = {16,20,25,30,36};
    private final int[] numRows = {4,4,5,5,6};
    private int level=1;
    private final ArrayList<Enemy> enemyArrayList = new ArrayList<>(gridSize[level-1]);
    private BufferedImage img = null;
    private final BufferedImage[] img_heart = new BufferedImage[3];
    private BufferedImage lives = null;
    private BufferedImage zero_png = null;
    private BufferedImage one_png = null;
    private BufferedImage two_png = null;
    private BufferedImage three_png = null;
    private BufferedImage four_png = null;
    private BufferedImage five_png = null;
    private BufferedImage six_png = null;
    private BufferedImage seven_png = null;
    private BufferedImage eight_png = null;
    private BufferedImage nine_png = null;

    private BufferedImage attempts_img = null;
    private final Spaceship s = new Spaceship(550, 600, 0, 0);
    private final ArrayList<PlayerBullet> playerBulletArrayList = new ArrayList<>();
    private final ArrayList<EnemyBullet> enemyBulletArrayList = new ArrayList<>();
    private static final int GRID_LENGTH = 1280;
    private static final int GRID_HEIGHT = 720;
    private int attempts = 90;
    private int enemyKills = 0;
    private int livesLeft = 3;
    private int direction = 1;
    private boolean startGame = false;
    private long lastBulletTime;


    public Main(){
        try {
            img = ImageIO.read(new File("background.png"));
            lives = ImageIO.read(new File("Lives.png"));
            attempts_img = ImageIO.read(new File("Attempts.png"));
            zero_png = ImageIO.read(new File("zero.png"));
            one_png = ImageIO.read(new File("one.png"));
            two_png = ImageIO.read(new File("two.png"));
            three_png = ImageIO.read(new File("three.png"));
            four_png = ImageIO.read(new File("four.png"));
            five_png = ImageIO.read(new File("five.png"));
            six_png = ImageIO.read(new File("six.png"));
            seven_png = ImageIO.read(new File("seven.png"));
            eight_png = ImageIO.read(new File("eight.png"));
            nine_png = ImageIO.read(new File("nine.png"));

            for (int i=0; i < 3; i++) {
                img_heart[i] = ImageIO.read(new File("heart.png"));
            }

        } catch (IOException e) {
            System.out.println("No Image");
        }
        addKeyListener(new KeyListener() {
            @Override
            public synchronized void keyTyped(KeyEvent e) {
            }

            @Override
            public synchronized void keyReleased(KeyEvent e) {
                s.keyReleased(e);
            }

            @Override
            public synchronized void keyPressed(KeyEvent e) {
                s.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    long now = System.currentTimeMillis();
                    if ((now - lastBulletTime) < 250) {
                        return;
                    }
                    playerBulletArrayList.add(new PlayerBullet(s));
                    lastBulletTime = now;
                    attempts -= 1;
                }
                for (PlayerBullet playerBullet : playerBulletArrayList) {
                    playerBullet.keyPressed(e);
                }

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    while(true) {
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", dialogButton);
                        if(option == JOptionPane.YES_OPTION)
                            System.exit(0);
                        else if(option == JOptionPane.NO_OPTION) {
                            break;
                        }
                    }
                }
            }
        });

        setFocusable(true);
        int x = 0, y = 80;
        for (int i = 0; i < gridSize[level-1]; i++) {
            enemyArrayList.add(new Enemy(x, y, 720, 1280));
            x += 80;
            if (x == numRows[level-1]*80) {
                y +=80;
                x = 0;
            }

        }
    }
    public void startGame() {
        int x = 0, y = 80;
        if (level < 6) {
        for (int i = 0; i < gridSize[level-1]; i++) {
            enemyArrayList.add(new Enemy(x, y, 720, 1280));
            x += 80;
            if (x == numRows[level - 1] * 80) {
                y += 80;
                x = 0;
            }
        }
        }
    }

    private void moveEnemiesVertically(){
        for (Enemy e : enemyArrayList) {
            e.setY(e.getY() + 10);
        }
    }
    private synchronized void move() {
        int min_x = 0, max_x = 0;
        for (Enemy e : enemyArrayList) {
            min_x = Math.min(min_x, e.getX());
            max_x = Math.max(max_x, e.getX());
        }
        if (min_x < 0) {
            direction = 1;
            moveEnemiesVertically();
        }
        if (max_x > (GRID_LENGTH - 80)) {
            direction = -1;
            moveEnemiesVertically();
        }

        //System.out.println("gridLen " + gridLength + " min " + min_x + " max " + max_x + " direction " + direction);
        for (Enemy e : enemyArrayList) {
            e.move(direction);
        }
        s.move();
        for (int i = 0; i < playerBulletArrayList.size(); i++) {
            playerBulletArrayList.get(i).move();
            if (playerBulletArrayList.get(i).getHit() || playerBulletArrayList.get(i).getY() < 50) {
                playerBulletArrayList.remove(i);
            }

        }
        for (int i = 0; i < enemyBulletArrayList.size(); i++) {
            enemyBulletArrayList.get(i).move();
            if (enemyBulletArrayList.get(i).getHit() || enemyBulletArrayList.get(i).getY() > 720) {
                enemyBulletArrayList.remove(i);
            }
        }
    }



    @Override
    public synchronized void paint(Graphics g) {

        super.paint(g); // Clears the panel, for a fresh start
        Graphics2D g2d = (Graphics2D) g;
        // Smooth’s out the movement
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(img, 0, 0, null);

        s.paint(g2d);

        for (Enemy enemy : enemyArrayList) {
            enemy.paint(g2d);
        }

        for (PlayerBullet playerBullet : playerBulletArrayList) {
            playerBullet.paint(g2d);
        }

        for (EnemyBullet enemyBullet : enemyBulletArrayList) {
            enemyBullet.paint(g2d);
        }
        g.drawImage(lives, 10, 25, null);
        for (int i=0; i < livesLeft; i++) {
            g.drawImage(img_heart[i], 50*i+180, 25, null);
        }
        g.drawImage(attempts_img, 500,25, null);
        if (attempts % 10 == 0) {g.drawImage(zero_png, 800, 25, null);}
        if (attempts / 10 == 1) {g.drawImage(one_png, 770, 25, null);}
        if (attempts / 10 == 2) {g.drawImage(two_png, 770, 25, null);}
        if (attempts / 10 == 3) {g.drawImage(three_png, 770, 25, null);}
        if (attempts / 10 == 4) {g.drawImage(four_png, 770, 25, null);}
        if (attempts / 10 == 5) {g.drawImage(five_png, 770, 25, null);}
        if (attempts / 10 == 6) {g.drawImage(six_png, 770, 25, null);}
        if (attempts / 10 == 7) {g.drawImage(seven_png, 770, 25, null);}
        if (attempts / 10 == 8) {g.drawImage(eight_png, 770, 25, null);}
        if (attempts / 10 == 9) {g.drawImage(nine_png, 770, 25, null);}
        if (attempts % 10 == 0) {g.drawImage(zero_png, 800, 25, null);}
        if (attempts % 10 == 1) {g.drawImage(one_png, 800, 25, null);}
        if (attempts % 10 == 2) {g.drawImage(two_png, 800, 25, null);}
        if (attempts % 10 == 3) {g.drawImage(three_png, 800, 25, null);}
        if (attempts % 10 == 4) {g.drawImage(four_png, 800, 25, null);}
        if (attempts % 10 == 5) {g.drawImage(five_png, 800, 25, null);}
        if (attempts % 10 == 6) {g.drawImage(six_png, 800, 25, null);}
        if (attempts % 10 == 7) {g.drawImage(seven_png, 800, 25, null);}
        if (attempts % 10 == 8) {g.drawImage(eight_png, 800, 25, null);}
        if (attempts % 10 == 9) {g.drawImage(nine_png, 800, 25, null);}


    }
    public void randomlyFire() {
        if (enemyKills < gridSize[level-1]) {
            int randNum = (int) (Math.random() * enemyArrayList.size());
            enemyBulletArrayList.add(new EnemyBullet(enemyArrayList.get(randNum)));
        }
    }

    public synchronized void collision() {
        //checks if player bullet hits enemy
        for (Enemy enemy : enemyArrayList) {
            for (PlayerBullet playerBullet : playerBulletArrayList) {
                if (playerBullet.collision(enemy)) {
                    enemyKills++;
                }
            }
        }
        //check if enemy bullet hits spaceship
        for (EnemyBullet enemyBullet : enemyBulletArrayList) {
            if (s.collision(enemyBullet)) {
                livesLeft--;
                if (livesLeft == 0) {
                }
            }
        }

    }

    public void changeLevel() {

        if (attempts == 0 || livesLeft == 0) {
            JOptionPane.showMessageDialog(null,"You lost, you either used up all your lives or used up your attempts");
            System.exit(0);
        }
        if (enemyKills == gridSize[level-1]) {
            level++;
            if (level == 6) {
                JOptionPane.showMessageDialog(null, "Good work, you have completed all the levels and have saved humanity from the evil aliens");
                System.exit(0);
            }
            playerBulletArrayList.clear();
            enemyBulletArrayList.clear();
            enemyArrayList.clear();
            attempts = 90;
            enemyKills = 0;
            livesLeft = 3;
            JOptionPane.showMessageDialog(null,"Good work, you have completed level " + (level-1) + " . Let's move on to the next one");
            startGame();
        }

    }

    public void menu() {
        int instructionsCount = 0;
        JFrame frame=new JFrame("Menu");

        // creates instance of JButton
        JLabel heading = new JLabel("Welcome to Space Race!", JLabel.CENTER);
        JLabel instructions = new JLabel("Instructions", JLabel.CENTER);
        JButton play = new JButton("Play");


        heading.setBounds(300,50, 200, 50);
        instructions.setBounds(300, 100, 200, 50);
        play.setBounds(360, 320, 80, 50);

        // setting close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // adds button in JFrame
        frame.add(heading);
        frame.add(instructions);
        JLabel directive1 = new JLabel("In this game, you control a spaceship and aim to defeat all aliens, you have 90 attempts", JLabel.LEFT);
        JLabel directive2 = new JLabel("The aliens will be randomly dropping bombs, the higher the level the faster the bombs will drop", JLabel.LEFT);
        JLabel directive3 = new JLabel("If you get hit by a bomb you will lose a life, you have 3 lives", JLabel.LEFT);
        JLabel directive4 = new JLabel("Here are the rules: ", JLabel.LEFT);
        JLabel directive5 = new JLabel("1: Control spaceship with left and right arrows", JLabel.LEFT);
        JLabel directive6 = new JLabel("2: Shoot at Aliens by pressing the spacebar", JLabel.LEFT);
        JLabel directive7 = new JLabel("3: Exit the game by pressing the esc button", JLabel.LEFT);
        directive1.setBounds(75,150,700,20);
        directive2.setBounds(75,170,700,20);
        directive3.setBounds(75,190,700,20);
        directive4.setBounds(75,210,700,20);
        directive5.setBounds(75,230,700,20);
        directive6.setBounds(75,250,700,20);
        directive7.setBounds(75,270,700,20);
        frame.add(directive1);
        frame.add(directive2);
        frame.add(directive3);
        frame.add(directive4);
        frame.add(directive5);
        frame.add(directive6);
        frame.add(directive7);
        frame.add(play, JLabel.CENTER);

        // sets 500 width and 600 height
        frame.setSize(800, 400);

//Add an action listener
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                startGame = true;

            }
        });

        // uses no layout managers
        frame.setLayout(null);

        // makes the frame visible
        frame.setVisible(true);
    }


    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Space Race");
        Main m = new Main();
        int frames = 0;
        int[] speed = {250, 225, 200, 175, 150};
        int level = m.level;
        boolean startGame = m.startGame;
        m.menu();
        while (m.startGame == false) {
            System.out.println();
        }
            frame.add(m);
            frame.setSize(GRID_LENGTH, GRID_HEIGHT);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            while (true) {
                m.collision();
                m.move();
                m.repaint();
                m.changeLevel();
                if ((frames++ % speed[level - 1]) == 100) {
                    m.randomlyFire();
                }
                Thread.sleep(10); // Pauses for a moment
        }
    }

}