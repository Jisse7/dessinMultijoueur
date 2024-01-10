package jeu2d;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server1 {

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

        // Obtient le bureau de l'utilisateur
        String desktopPath = System.getProperty("user.home") + "\\Desktop";

        // Crée le dossier "replay" sur le bureau
        String replayFolderPath = desktopPath + "\\Replays";
        Path replayFolder = Paths.get(replayFolderPath);
        Files.createDirectories(replayFolder);

        String filePathPlayer1 = replayFolderPath + "\\actions_joueurs_" + formattedDate + ".txt";

        // Crée un verrou pour synchroniser l'écriture dans le fichier
        Lock fileWriteLock = new ReentrantLock();

        // Crée un thread pour chaque client et pour l'enregistrement des actions
        Thread player1Thread = new Thread(() -> {
            try {
                while (true) {
                    String message = player1In.readLine();
                    if (message != null) {
                        // Affiche le message du premier joueur dans la console du serveur
                        System.out.println("Joueur 1 (hôte) : " + message);

                        // Enregistre l'action du premier joueur dans un fichier dédié
                        writeToFile(fileWriteLock, filePathPlayer1, "1:" + message);

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
            try {
                while (true) {
                    String message = player2In.readLine();
                    if (message != null) {
                        // Affiche le message du deuxième joueur dans la console du serveur
                        System.out.println("Joueur 2 : " + message);

                        // Enregistre l'action du deuxième joueur dans un fichier dédié
                        writeToFile(fileWriteLock, filePathPlayer1, "2:" + message);

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

    private static void writeToFile(Lock lock, String filePath, String message) {
        lock.lock();
        try (PrintWriter fileWriter = new PrintWriter(new FileWriter(filePath, true))) {
            fileWriter.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
