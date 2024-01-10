package jeu2d;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Server {
    
    

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
         System.out.println("LANCEMENT du serveur");

        // Attend le premier client
        Socket player1Socket = serverSocket.accept();
        BufferedReader player1In = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
        PrintWriter player1Out = new PrintWriter(player1Socket.getOutputStream(), true);
        System.out.println("Premier joueur connecté ");

        // Attend le deuxième client
        Socket player2Socket = serverSocket.accept();
        BufferedReader player2In = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
        PrintWriter player2Out = new PrintWriter(player2Socket.getOutputStream(), true);
        System.out.println("Deuxième joueur connecté ");

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDate = date.format(formatter);

        String filePathPlayer1 = "C:\\Users\\User\\Desktop\\actions_joueur1_" + formattedDate + ".txt";
        String filePathPlayer2 = "C:\\Users\\User\\Desktop\\actions_joueur2_" + formattedDate + ".txt";

        // Crée un thread pour chaque client et pour l'enregistrement des actions
        Thread player1Thread = new Thread(() -> {
            try (PrintWriter fileWriterPlayer1 = new PrintWriter(new FileWriter(filePathPlayer1))) {
                while (true) {
                    String message = player1In.readLine();
                    if (message != null) {
                        // Affiche le message du premier joueur dans la console du serveur
                        System.out.println("Joueur 1 (hôte) : " + message);

                        // Enregistre l'action du premier joueur dans un fichier dédié
                        fileWriterPlayer1.println(message);
                        fileWriterPlayer1.flush();  // Assure l'écriture immédiate dans le fichier

                        // Transmet le message au deuxième joueur
                        player2Out.println(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        player1Thread.start();

        Thread player2Thread = new Thread(() -> {
            try (PrintWriter fileWriterPlayer2 = new PrintWriter(new FileWriter(filePathPlayer2))) {
                while (true) {
                    String message = player2In.readLine();
                    if (message != null) {
                        // Affiche le message du deuxième joueur dans la console du serveur
                        System.out.println("Joueur 2 : " + message);

                        // Enregistre l'action du deuxième joueur dans un fichier dédié
                        fileWriterPlayer2.println(message);
                        fileWriterPlayer2.flush();  // Assure l'écriture immédiate dans le fichier

                        // Transmet le message au premier joueur
                        player1Out.println(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        player2Thread.start();
    }
}


