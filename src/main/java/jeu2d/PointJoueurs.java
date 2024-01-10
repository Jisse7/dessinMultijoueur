/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jeu2d;

import java.awt.Point;

/**
 *
 * @author User
 */
public class PointJoueurs extends Point {
    String j;
    int x;
    int y;
    
    
    
    public PointJoueurs(String j,int x, int y){
        this.j=j;
        this.x=x;
        this.y=y;
        
    }
    
    
   
    @Override
     public String toString() {
        return getClass().getName() +"[j="+ j+ "[x=" + x + ",y=" + y + "]";
    }
   
}
