package jeu2d;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Game extends JPanel implements ActionListener, KeyListener {

    Timer timer;
    int playerX = 200, playerY = 200;
    int opponentX = 300, opponentY = 300;
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    
    List<Point> playerPath = new ArrayList<>();
    List<Point> opponentPath= new ArrayList<>();

    public Game() {
        timer = new Timer(1, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);

        try {
            socket = new Socket("localhost", 8080);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                while (true) {
                    try {
                        String message = in.readLine();
                        if (message != null) {
                            String[] coords = message.split(",");
                            opponentX = Integer.parseInt(coords[0]);
                            opponentY = Integer.parseInt(coords[1]);
                            
                            opponentPath.add(new Point(opponentX, opponentY));
                            

                       
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(playerX, playerY, 25, 25);
        g.fillRect(opponentX, opponentY, 25, 25);
        
        
        if(opponentPath.size()>1){
            g.setColor(Color.RED);
            for(int i=1;i<opponentPath.size();i++){
                Point p1= opponentPath.get(i-1);
                Point p2= opponentPath.get(i);
              g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        if (playerPath.size() > 1) {
            g.setColor(Color.BLUE);
            for (int i = 1; i < playerPath.size(); i++) {
                Point p1 = playerPath.get(i - 1);
                Point p2 = playerPath.get(i);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        
        }
        
    }
       

    

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            playerY -= 5;
        }
        if (key == KeyEvent.VK_DOWN) {
            playerY += 5;
        }
        if (key == KeyEvent.VK_LEFT) {
            playerX -= 5;
        }
        if (key == KeyEvent.VK_RIGHT) {
            playerX += 5;
        }

        if (out != null) {
            out.println(playerX + "," + playerY);
        }

        playerPath.add(new Point(playerX, playerY));
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
         frame.getContentPane().setBackground(Color.blue);
        Game game = new Game();

        JLabel label = new JLabel("FAITES QUELQUE CHOSE");
        label.setFont(new Font("Arial", Font.BOLD, 32));

        frame.add(label, BorderLayout.NORTH);
        frame.add(game, BorderLayout.CENTER);
        game.setBackground(Color.BLACK);

        frame.setSize(750, 750);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLUE);
        
    }
}
