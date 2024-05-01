import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.tools.Tool;

public class Main implements Runnable, KeyListener {
    final int WIDTH = 1000;
    final int HEIGHT = 800;
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    public boolean gamePlaying = false;
    public boolean gameStarted = false;
    public boolean gameOver = false;

    public boolean isPaused = false;

    public Image backgroundPic;

    public Image holdscreenPic;
    public Fruit strawberry;
    public Fruit watermelon;

    public Fruit cherries;
    public Snake[] dots;

    public Image strawberryPic;
    public Image watermelonPic;

    public Image cherriesPic;

    public Image bombPic;

    public Bomb bomb;

    public int random;

    public int randomm;
    public int aliveDot;

    public int score;

    public SoundFile eatSound;

    public SoundFile bombSound;

    public SoundFile goldSound;

    public SoundFile marioMusic;

    public SoundFile gameoverSound;






    public static void main(String[] args) {
        Main ex = new Main();
        new Thread(ex).start();
    }

    public Main() {
        setUpGraphics();
        canvas.addKeyListener(this);

        strawberry = new Fruit("strawberry", 1,0, 0);
        strawberryPic = Toolkit.getDefaultToolkit().getImage("strawberry.png");

        watermelon = new Fruit("watermelon", 5, 0, 0);
        watermelonPic = Toolkit.getDefaultToolkit().getImage("watermelon.png");

        cherries = new Fruit ("cherries", 10, 0,0);
        cherriesPic = Toolkit.getDefaultToolkit().getImage("cherries.png");

        bomb = new Bomb("bomb", 0, 0);
        bombPic = Toolkit.getDefaultToolkit().getImage("bomb2.png");

        backgroundPic = Toolkit.getDefaultToolkit().getImage("OQHXG00.jpg");
        holdscreenPic = Toolkit.getDefaultToolkit().getImage("unnamed.png");


        eatSound = new SoundFile("munchfx.wav");
        goldSound = new SoundFile("coinsfx.wav");
        bombSound = new SoundFile("Comical Metal Gong.wav");
        marioMusic = new SoundFile("mariopaint.wav");
        gameoverSound = new SoundFile("gameoversound.wav");

        marioMusic.play();

        restart();

    }

    public void run() {
        while (true) {
            if (gamePlaying == true && isPaused == false){
                moveThings();
                collisions();
            }
            render();
            pause(20);
        }
    }

    public void moveThings() {
        dots[0].move();
        for (int i = dots.length - 1; i > 0 ; i = i - 1) {
            dots[i].xpos = dots[i-1].xpos;
            dots[i].ypos = dots[i-1].ypos;
            dots[i].rec = new Rectangle (dots[i].xpos, dots[i].ypos, dots[i].width, dots[i].height);
        }


        if (gamePlaying == true && strawberry.isAlive == false && watermelon.isAlive == false && cherries.isAlive == false) {
            random = (int)(Math.random() * 200);
            if (random < 120) {
                strawberry.spawnn();
                strawberry.isAlive = true;
                watermelon.isAlive = false;
                cherries.isAlive = false;
            }
            if (random >= 120 && random < 180) {
                watermelon.spawnn();
                watermelon.isAlive = true;
                strawberry.isAlive = false;
                cherries.isAlive = false;
            }
            if (random >=180){
                cherries.spawnn();
                cherries.isAlive = true;
                watermelon.isAlive = false;
                strawberry.isAlive = false;
            }
        }

        if (gamePlaying == true && bomb.isAlive == false){
            randomm = (int)(Math.random()*100);
            if (randomm > 90){
                bomb.spawn();
                bomb.isAlive = true;
            }
            else{
                bomb.isAlive = false;
            }
        }
        if (gamePlaying && score <= 0){
            gameOver = true;
            gameoverSound.play();
        }

    }


    public void collisions() {
        for (int i = dots.length - 1; i > 0 ; i = i - 1) {
            if (strawberry.rec.intersects(dots[i].rec) && dots[i].isAlive == true && strawberry.isAlive == true) {
                strawberry.isAlive = false;
                eatSound.play();
                score = score + strawberry.points;
                dots[aliveDot].isAlive = true;
                aliveDot = aliveDot + 1;

            }
        }
        for (int i = dots.length - 1; i > 0 ; i = i - 1) {
            if (watermelon.rec.intersects(dots[i].rec) && dots[i].isAlive == true && watermelon.isAlive == true) {
                watermelon.isAlive = false;
                eatSound.play();
                score = score + watermelon.points;
                dots[aliveDot].isAlive = true;
                dots[aliveDot + 1].isAlive = true;
                dots[aliveDot + 2].isAlive = true;
                dots[aliveDot + 3].isAlive = true;
                dots[aliveDot + 4].isAlive = true;
                aliveDot = aliveDot + 5;
            }
        }
        for (int i = dots.length - 1; i > 0 ; i = i - 1) {
            if (cherries.rec.intersects(dots[i].rec) && dots[i].isAlive == true && cherries.isAlive == true) {
                cherries.isAlive = false;
                goldSound.play();
                score = score + cherries.points;
                dots[aliveDot].isAlive = true;
                dots[aliveDot + 1].isAlive = true;
                dots[aliveDot + 2].isAlive = true;
                dots[aliveDot + 3].isAlive = true;
                dots[aliveDot + 4].isAlive = true;
                dots[aliveDot + 5].isAlive = true;
                dots[aliveDot + 6].isAlive = true;
                dots[aliveDot + 7].isAlive = true;
                dots[aliveDot + 8].isAlive = true;
                dots[aliveDot + 9].isAlive = true;
                aliveDot = aliveDot + 10;
            }
        }
        for (int i = 0; i < dots.length ; i++) {
            if (bomb.rec.intersects(dots[i].rec) && dots[i].isAlive == true && bomb.isAlive == true) {
                bomb.isAlive = false;
                bombSound.play();
                dots[aliveDot].isAlive = false;
                dots[aliveDot - 1 ].isAlive = false;
                dots[aliveDot - 2 ].isAlive = false;
                dots[aliveDot - 3 ].isAlive = false;
                dots[aliveDot - 4 ].isAlive = false;
                score = score - 5;
                aliveDot = aliveDot - 5;
            }
        }

        for (int i = 0; i < dots.length ; i = i + 1) {
            if ( (dots[i].ypos < 0 ||
                    dots[i].ypos > 800 - dots[i].height ||
                    dots[i].xpos > 1000 - dots[i].width ||
                    dots[i].xpos < 0) && dots[i].isAlive == true ) {
                    dots[i].isAlive = false;
                    gameOver = true;
                    gameoverSound.play();
            }
        }
    }

    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        if(gamePlaying == false){ // start screen
            g.drawImage(holdscreenPic, 0, 0, WIDTH, HEIGHT, null); //
            g.setColor(Color.white);
            g.setFont(new Font("Times Roman", Font.PLAIN, 80));
            g.drawString("Extreme Snake", 250, 200);
            g.setFont(new Font("Times Roman", Font.PLAIN, 25));
            g.drawString("use arrow keys to move",350,300 );
            g.drawString("eat fruit to grow your snake",350,330 );
            g.drawString("avoid the bombs AND the wall!",350,360 );
            g.drawString("beat your high score!",350,390 );
            g.setFont(new Font("Times New Roman", Font.PLAIN, 45));
            g.drawString("- press spacebar to start -", 300,600 );
        }
        else if(gamePlaying == true && gameOver == false) {

            g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);
            g.setFont(new Font("Times New Roman", Font.BOLD, 40));
            g.drawString("score: " + score, 432, 110);

            if (strawberry.isAlive == true && watermelon.isAlive == false && cherries.isAlive == false) {
                g.drawImage(strawberryPic, strawberry.xpos, strawberry.ypos, strawberry.width, strawberry.height, null);
            }

            if (watermelon.isAlive == true && strawberry.isAlive == false && cherries.isAlive == false) {
                g.drawImage(watermelonPic, watermelon.xpos, watermelon.ypos, watermelon.width, watermelon.height, null);
            }

            if (cherries.isAlive == true && watermelon.isAlive == false && strawberry.isAlive == false){
                g.drawImage(cherriesPic,cherries.xpos,cherries.ypos,cherries.width,cherries.height, null);
            }

            if (bomb.isAlive == true) {
                g.drawImage(bombPic, bomb.xpos, bomb.ypos, bomb.width, bomb.height, null);
            }

            for (int x = 0; x < dots.length; x++) {
                if (dots[x].isAlive == true) {
                    g.drawImage(dots[x].pic, dots[x].xpos, dots[x].ypos, dots[x].width, dots[x].height, null);
                }
            }
        }

        else {
            g.drawImage(holdscreenPic, 0, 0, WIDTH, HEIGHT, null);// points total
            g.setColor(Color.yellow);
            g.setFont(new Font("Times New Roman", Font.BOLD, 40));
            if (score > 0){
            g.drawString ("score: " + score, 432, 110);}
            if (score <= 0){
            g.drawString ("score: 0", 432, 110);}
            g.setFont(new Font("Times New Roman", Font.PLAIN, 45));
            g.drawString("- press spacebar to play again -", 250,600 );
            }

        g.dispose();
        bufferStrategy.show();
    }

    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    private void setUpGraphics() {
        frame = new JFrame("Application Template");

        panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        int keyCode = e.getKeyCode();
        System.out.println("Key pressed: " + key + ", Key code: " + keyCode);
        if (keyCode == 38) {
            for (int x = 0; x < dots.length; x++) {
                dots[x].upIsPressed = true;
                dots[x].leftIsPressed = false;
                dots[x].rightIsPressed = false;
                dots[x].downIsPressed = false;
            }
        }// up
        if (keyCode == 40) {
            for (int x = 0; x < dots.length; x++) {
                dots[x].upIsPressed = false;
                dots[x].leftIsPressed = false;
                dots[x].rightIsPressed = false;
                dots[x].downIsPressed = true;
            }
        } // down
        if (keyCode == 37) {
            for (int x = 0; x < dots.length; x++) {
                dots[x].upIsPressed = false;
                dots[x].leftIsPressed = true;
                dots[x].rightIsPressed = false;
                dots[x].downIsPressed = false;
            }
        } // left
        if (keyCode == 39) {
            for (int x = 0; x < dots.length; x++) {
                dots[x].upIsPressed = false;
                dots[x].leftIsPressed = false;
                dots[x].rightIsPressed = true;
                dots[x].downIsPressed = false;
            }
        } // right

        if (gamePlaying == false && keyCode == 32) {

            gamePlaying = true;
            gameStarted = true;
            gameOver = false;
        } // start the game

        if (gameOver == true && keyCode == 32) {
            gamePlaying = true;
            gameStarted = true;
            gameOver = false;
            restart();

        } // restart

    }


    public void keyReleased (KeyEvent e) {
    }

    public void restart() {
        dots = new Snake[999];
        for (int x = 0; x < dots.length ; x++) {
            dots[x] = new Snake(x * 100 + 200, 400);
            dots[x].pic = Toolkit.getDefaultToolkit().getImage("character red.png");
        }
        dots[0].isAlive = true;
        dots[1].isAlive = true;
        dots[2].isAlive = true;
        aliveDot = 3;
        score = 3;
    }
}


