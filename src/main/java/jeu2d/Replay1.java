

package jeu2d;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;

import jeu2d.PointJoueurs;

public class Replay1 extends JPanel {

    private File joueurs;
    private List<String> players = new ArrayList<>();
    private int currentIndex = 0;
    private Timer timer;
    private int MAX;

    public Replay1(File joueurs) {
        this.joueurs = joueurs;
        this.MAX = calculTaille();
        dansArray();
        
         setBackground(Color.WHITE); 

        timer = new Timer(25, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < MAX) {
                    repaint();
                    currentIndex++;
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    public void setJoueurs(File joueurs) {
        this.joueurs = joueurs;
        this.currentIndex = 0;
        this.MAX = calculTaille();
        dansArray();
        timer.start();
    }

    public void dansArray() {
        try {
            Scanner sc = new Scanner(this.joueurs);

            while (sc.hasNextLine()) {
                players.add(sc.nextLine());
            }

            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Replay1() {
        this(new File("fichier.txt"));
        //this(new File("path/vers/votre/fichier.txt"));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        


        BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics bufferGraphics = buffer.getGraphics();

        ArrayList<PointJoueurs> arr = new ArrayList<>();

        for (int i = 0; i < currentIndex; i++) {
            int deuxpoints = players.get(i).indexOf(":");
            int virgule = players.get(i).indexOf(",");

            String quelJoueur = players.get(i).substring(0, deuxpoints);
            String x = players.get(i).substring(deuxpoints + 1, virgule);
            String y = players.get(i).substring(virgule + 1);

            PointJoueurs p = new PointJoueurs(quelJoueur, Integer.parseInt(x), Integer.parseInt(y));
            arr.add(p);
        }

        if (arr.size() > 1) {
            int i = 1;
            PointJoueurs sauvegarde = null; // pour les transitions
            PointJoueurs debut = null; // pour le début,

            for (; i < arr.size(); ) {
                PointJoueurs p1 = arr.get(i - 1);
                PointJoueurs p2 = arr.get(i);

                if (p1.j.equals("1") && p2.j.equalsIgnoreCase("1")) {
                    if (debut == null) {
                        bufferGraphics.setColor(Color.BLUE);
                        bufferGraphics.drawLine(p1.x, p1.y, p2.x, p2.y);
                        debut = p1;
                        i++;
                    } else {
                        bufferGraphics.setColor(Color.BLUE);
                        bufferGraphics.drawLine(p1.x, p1.y, p2.x, p2.y);
                        i++;
                    }

                } else if (p1.j.equals("2") && p2.j.equals("2")) {
                    if (debut == null) {
                        bufferGraphics.setColor(Color.RED);
                        bufferGraphics.drawLine(p1.x, p1.y, p2.x, p2.y);
                        debut = p1;
                        i++;
                    } else {
                        bufferGraphics.setColor(Color.RED);
                        bufferGraphics.drawLine(p1.x, p1.y, p2.x, p2.y);
                        i++;
                    }

                } else if (p1.j.equalsIgnoreCase("2") && p2.j.equalsIgnoreCase("1")) {
                    if (sauvegarde == null) {
                        sauvegarde = p1;
                        i++;
                    } else {
                        bufferGraphics.setColor(Color.BLUE);
                        bufferGraphics.drawLine(sauvegarde.x, sauvegarde.y, p2.x, p2.y);
                        sauvegarde = p1;
                        i++;
                    }

                } else if (p1.j.equals("1") && p2.j.equalsIgnoreCase("2")) {
                    if (sauvegarde == null) {
                        sauvegarde = p1;
                        i++;
                    } else {
                        bufferGraphics.setColor(Color.RED);
                        bufferGraphics.drawLine(sauvegarde.x, sauvegarde.y, p2.x, p2.y);
                        sauvegarde = p1;
                        i++;
                    }
                }
            }

            // Dessiner l'image tampon sur le composant
            g.drawImage(buffer, 0, 0, this);
        }
    }

    private int calculTaille() {
        dansArray();
        return players.size();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            Replay1 replay1 = new Replay1();
            replay1.setBackground(Color.black);
            JLabel label = new JLabel("Replay");
            label.setFont(new Font("Arial", Font.BOLD, 32));

            frame.add(label, BorderLayout.NORTH);
            frame.add(replay1, BorderLayout.CENTER);

            frame.setSize(750, 750);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
