package jeu2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Replay extends JPanel {

    static File joueur1 = new File("C:\\Users\\User\\Desktop\\actions_joueur2_20231201_001540.txt");
    static File joueur2 = new File("C:\\Users\\User\\Desktop\\actions_joueur1_20231201_001540.txt");

    static List<String> player1 = new ArrayList<>();
    static List<String> player2 = new ArrayList<>();

    private int currentIndex = 0;
    private Timer timer;
    
    final  int MAX= calculTaille();
    final  int MAX2=calculTaille() ;
    
    
      public static void dansArray() {
        try {
            Scanner sc = new Scanner(joueur1);
            Scanner sc2 = new Scanner(joueur2);

            while (sc.hasNext() && sc2.hasNext()) {
                player1.add(sc.next());
                player2.add(sc2.next());
            }

            sc.close();
            sc2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Replay() {
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // System.out.println(player1.size());
                if (currentIndex < Math.min(MAX, MAX2)) {
                    repaint();
                    currentIndex++;
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }

  

    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);

        ArrayList<Point> j1 = new ArrayList<>();
        ArrayList<Point> j2 = new ArrayList<>();

        for (int i = 0; i < currentIndex; i++) {
            String[] coords1 = player1.get(i).split(",");
            String[] coords2 = player2.get(i).split(",");

            Point p1 = new Point(Integer.parseInt(coords1[0]), Integer.parseInt(coords1[1]));
            Point p2 = new Point(Integer.parseInt(coords2[0]), Integer.parseInt(coords2[1]));

            j1.add(p1);
            j2.add(p2);
        }
       
     
        if (j1.size() > 1) {
              Point erase = new Point();
                Point erase2 = new Point();
            for (int i = 1; i < j1.size(); i++) {
                Point p1 = j1.get(i - 1);
                Point p2 = j1.get(i);
                 g.setColor(Color.RED);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
             
                erase.x=p1.x;
                erase.y=p1.y;
                
                erase2.x=p2.x;
                erase2.y=p2.y;
                
               // g.setColor(Color.BLACK);
                 //   g.fillRect(p1.x, p1.y, 50, 50);
       
                //delay(1);  // Ajouter un délai de 100 millisecondes (0.1 seconde)
            }
               
                
                
        }
        if (j2.size() > 1) {
            
            for (int i = 1; i < j2.size(); i++) {
                Point p1 = j2.get(i - 1);
                Point p2 = j2.get(i);
                g.setColor(Color.BLUE);
                
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
                //g.setColor(Color.BLACK);
                //g.fillRect(p1.x, p1.y, 50, 50);
               delay(1);  // Ajouter un délai de 100 millisecondes (0.1 seconde)
            }
        }
        
    }

    private void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public int calculTaille(){
        dansArray();
        
        return player2.size();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Replay replay = new Replay();

        frame.add(replay);

        frame.setSize(800, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
