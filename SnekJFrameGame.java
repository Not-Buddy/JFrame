import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class SnekJFrameGame extends JFrame {
    public SnekJFrameGame() {
        add(new GamePanel());
        setTitle("Snake Game");
        setResizable(false);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SnekJFrameGame::new);
    }
}

class GamePanel extends JPanel implements Runnable, KeyListener {

    private static final int PANEL_WIDTH = 900;
    private static final int PANEL_HEIGHT = 900;
    private static final int UNIT_SIZE = 30;
    private static final int GAME_UNITS = (PANEL_WIDTH * PANEL_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final int DELAY = 150; // Increased delay for slower movement

    private final int[] x = new int[GAME_UNITS];
    private final int[] y = new int[GAME_UNITS];
    private int bodyParts = 6;
    private int foodEaten;
    private int foodX;
    private int foodY;
    private char direction = 'R';
    private boolean running = false;
    private Random random;
    private Thread gameThread;
    private Queue<Character> inputQueue = new LinkedList<>();

    public GamePanel() {
        random = new Random();
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);
        startGame();
    }

    public void startGame() {
        newFood();
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.red);
            g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(new Color(45, 180, 0));
                }
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 60));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + foodEaten, (PANEL_WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void newFood() {
        foodX = random.nextInt(PANEL_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        foodY = random.nextInt(PANEL_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        // Process any queued inputs
        while (!inputQueue.isEmpty()) {
            char newDirection = inputQueue.poll();
            if ((newDirection == 'L' && direction != 'R') ||
                (newDirection == 'R' && direction != 'L') ||
                (newDirection == 'U' && direction != 'D') ||
                (newDirection == 'D' && direction != 'U')) {
                direction = newDirection;
                break;
            }
        }

        switch (direction) {
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
        }
    }

    public void checkFood() {
        if ((x[0] == foodX) && (y[0] == foodY)) {
            bodyParts++;
            foodEaten++;
            newFood();
        }
    }

    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }

        if (x[0] < 0 || x[0] >= PANEL_WIDTH || y[0] < 0 || y[0] >= PANEL_HEIGHT) {
            running = false;
        }

        if (!running) {
            gameThread.interrupt();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 120));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (PANEL_WIDTH - metrics1.stringWidth("Game Over")) / 2, PANEL_HEIGHT / 2);

        g.setFont(new Font("Ink Free", Font.BOLD, 80));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + foodEaten, (PANEL_WIDTH - metrics2.stringWidth("Score: " + foodEaten)) / 2, g.getFont().getSize() + PANEL_HEIGHT / 2);
    }

    @Override
    public void run() {
        while (running) {
            move();
            checkCollisions();
            checkFood();
            repaint();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> inputQueue.offer('L');
            case KeyEvent.VK_RIGHT -> inputQueue.offer('R');
            case KeyEvent.VK_UP -> inputQueue.offer('U');
            case KeyEvent.VK_DOWN -> inputQueue.offer('D');
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
