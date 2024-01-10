/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jeu2d;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author User
 */
public class test {




  public static void main(String[] args) {
  	try {
      InetAddress adrLocale = InetAddress.getLocalHost(); 
      System.out.println("Adresse locale = "+adrLocale.getHostAddress());
      InetAddress adrServeur = InetAddress.getByName("java.sun.com"); 
      System.out.println("Adresse Sun = "+adrServeur.getHostAddress());
      InetAddress[] adrServeurs = InetAddress.getAllByName("www.microsoft.com");
      System.out.println("Adresses Microsoft : ");
      for (int i = 0; i < adrServeurs.length; i++) {
      	System.out.println("     "+adrServeurs[i].getHostAddress());
      }
    } catch (UnknownHostException e) {
  	  e.printStackTrace();	
  	}
	
  }
}
