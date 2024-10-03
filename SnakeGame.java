import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class SnakeGame extends JFrame {
    public SnakeGame() {
        add(new GamePanel());
        setTitle("Snake Game");
        setResizable(false);
        pack(); // Adjust window size based on GamePanel size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SnakeGame();
    }
}

class GamePanel extends JPanel implements ActionListener {

    private final int PANEL_WIDTH = 600; // Increased width
    private final int PANEL_HEIGHT = 600; // Increased height
    private final int UNIT_SIZE = 20; // Increased unit size
    private final int TOTAL_UNITS = (PANEL_WIDTH * PANEL_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private final int DELAY = 150;

    private final int[] x = new int[TOTAL_UNITS]; // Snake's X positions
    private final int[] y = new int[TOTAL_UNITS]; // Snake's Y positions
    private int bodyParts = 3; // Initial snake size
    private int foodX;
    private int foodY;
    private int score = 0;

    private char direction = 'R'; // 'U' = up, 'D' = down, 'L' = left, 'R' = right
    private boolean running = false;
    private Timer timer;
    private Random random;

    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R') direction = 'L';
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L') direction = 'R';
                        break;
                    case KeyEvent.VK_UP:
                        if (direction != 'D') direction = 'U';
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'U') direction = 'D';
                        break;
                }
            }
        });

        random = new Random();
        startGame();
    }

    public void startGame() {
        spawnFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // Draw food
            g.setColor(Color.red);
            g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

            // Draw snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green); // Head of the snake
                } else {
                    g.setColor(new Color(45, 180, 0)); // Body of the snake
                }
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            // Draw score
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 30)); // Increased font size
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + score, (PANEL_WIDTH - metrics.stringWidth("Score: " + score)) / 2, g.getFont().getSize());

        } else {
            gameOver(g);
        }
    }

    public void spawnFood() {
        foodX = random.nextInt((int) (PANEL_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        foodY = random.nextInt((int) (PANEL_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            bodyParts++;
            score++;
            spawnFood();
        }
    }

    public void checkCollisions() {
        // Check if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

        // Check if head touches the borders
        if (x[0] < 0 || x[0] >= PANEL_WIDTH || y[0] < 0 || y[0] >= PANEL_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    public void gameOver(Graphics g) {
        // Display score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + score, (PANEL_WIDTH - metrics1.stringWidth("Score: " + score)) / 2, g.getFont().getSize());

        // Display Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (PANEL_WIDTH - metrics2.stringWidth("Game Over")) / 2, PANEL_HEIGHT / 2);
    }
}

