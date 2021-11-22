/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miclienteSolarj;

import java.io.*;
import java.util.*;

/**
 *
 * @author usuario
 */
public class readCISI {
    public static void main(String[] args) throws IOException {
        String fileName = "/home/usuario/micoleccion/CISI.ALL";
        Scanner scan = new Scanner(new File(fileName));
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String aux;
            if(line.length()> 2)
                aux = line.substring(0,2);
            else
                aux = line;
            //System.out.println(line);
            switch(aux){
                case ".I":
                    System.out.println("\n\n\n\n\n");
                    String[] separar = line.split(" ");
                    System.out.println("                INDEX(" + separar[1] + ")");
                    break;
                case ".T":
                    System.out.println("TITTLE");
                    break;
                case ".A":
                    System.out.println("AUTHOR");
                    break;
                case ".W":
                    System.out.println("WORK");
                    break;
                case ".X":
                    System.out.println("TABLE");
                    break;
                default:
                    System.out.println("    "+line);
            }
            
            
        }
    }
}
