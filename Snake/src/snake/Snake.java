/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

/**
 *
 * @author Lore
 */
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

public class Snake extends JFrame {

    int width = 640;
    int height = 480;
    Point snake;
    Point comida;
    boolean gameOver = false;
    ArrayList<Point> lista = new ArrayList<Point>();
    int WIDTHPoint = 10;
    int HEIGHTPoint = 10;
    ImagenSnake imagenSnake;
    //int direccion = KeyEvent.VK_LEFT;
    long frecuencia = 40;
    String direccion = "RIGHT";

    public Snake() {
        setTitle("Snake");
        startGame();
        imagenSnake = new ImagenSnake();
        this.getContentPane().add(imagenSnake);
        setSize(width, height);
        Teclas teclas = new Teclas();
        this.addKeyListener(teclas);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        snake = new Point(width / 2, height / 2);

        setVisible(true);
        Momento momento = new Momento();
        Thread trid = new Thread(momento);
        trid.start();
    }

    public void startGame() {
        comida = new Point(200, 100);
        snake = new Point(320, 240);
        lista = new ArrayList<Point>();
        lista.add(snake);
        //crearComida();
    }

    public void crearComida() {
        Random rnd = new Random();
        comida.x = (rnd.nextInt(width)) + 5;
        if ((comida.x % 5) > 0) {
            comida.x = comida.x - (comida.x % 5);

        }
        if (comida.x < 5) {
            comida.x = comida.x + 10;
        }
        if (comida.x > width) {
            comida.x = comida.x - 10;
        }
        comida.y = (rnd.nextInt(height) + 5);
        if ((comida.y % 5) > 0) {
            comida.y = comida.y - (comida.y % 5);

        }
        if (comida.y < 0) {
            comida.y = comida.y + 10;
        }
        if (comida.y > height) {
            comida.y = comida.y - 10;
        }

    }

    public static void main(String[] args) throws Exception {
        Snake s = new Snake();
    }

    public void actualizar() {
        
        lista.add(0, new Point(snake.x, snake.y));
        lista.remove((lista.size() - 1));
        for (int i = 1; i < lista.size(); i++) {
            Point punto = lista.get(i);
            if (snake.x == punto.x & snake.y == punto.y) {
                gameOver = true;
            }
        }
        if ((snake.x > (comida.x - 10)) & (snake.x < (comida.x + 10)) & (snake.y > (comida.y - 10)) & (snake.y > (comida.y + 10))) {
            lista.add(0, new Point(snake.x, snake.y));
            crearComida();
        }
        imagenSnake.repaint();
    }

    

    public class ImagenSnake extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
      
                g.setColor(new Color(0,0,255));
                g.fillRect(snake.x, snake.y, WIDTHPoint, HEIGHTPoint);
                for (int i = 0; i < lista.size(); i++) {
                    Point point = (Point) lista.get(i);
                    g.fillRect(point.x, point.y, WIDTHPoint, HEIGHTPoint);

                }
                g.setColor(new Color(255, 0, 0));
                g.fillRect(comida.x, comida.y, WIDTHPoint, HEIGHTPoint);
            

            if (gameOver) {
                g.setFont(new Font("TimesRoman", Font.BOLD, 40));
                g.drawString("GAME OVER", 300, 200);
            }
            

        }
    }

    public class Teclas extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                if (direccion != "LEFT") {
                    direccion = "RIGHT";

                }
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (direccion != "RIGHT") {
                    direccion = "LEFT";
                }
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (direccion != "DOWN") {
                    direccion = "UP";
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (direccion != "UP") {
                    direccion = "DOWN";
                }

            } else if (e.getKeyCode() == KeyEvent.VK_N) {
                gameOver = false;
                startGame();
            }
        }

    }

    public class Momento extends Thread {

        long last = 0;

        public void run() {
            while (true) {
                if ((java.lang.System.currentTimeMillis() - last > frecuencia)) {
                    if (!gameOver) {

                        if (direccion == "RIGHT") {
                            snake.x = snake.x + WIDTHPoint;
                            if (snake.x > width) {
                                snake.x = 0;
                            }
                        } else if (direccion == "LEFT") {
                            snake.x = snake.x - WIDTHPoint;
                            if (snake.x < 0) {
                                snake.x = width - WIDTHPoint;
                            }
                        } else if (direccion == "UP") {
                            snake.y = snake.y - HEIGHTPoint;
                            if (snake.y < 0) {
                                snake.y = height;
                            }
                        } else if (direccion == "DOWN") {
                            snake.y = snake.y + HEIGHTPoint;
                            if (snake.y > height) {
                                snake.y = 0;
                            }
                        }
                    }
                    actualizar();
                    last = java.lang.System.currentTimeMillis();
                }
            }

        }
    }

}
