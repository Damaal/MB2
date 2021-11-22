/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miclienteSolarj;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author usuario
 */
public class MiClienteAddSolrj {

    public static void main(String[] args) throws IOException, SolrServerException {
        String Indice = "";
        String Autor = "";
        String Titulo = "";
        String Texto = "";
        
        
        
        
        String fileName = "CISI.ALL";
        //String fileName = "/home/usuario/micoleccion/CISI.ALL";

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
                    //System.out.println("\n\n\n\n\n");
                    String[] separar = line.split(" ");
                    //System.out.println("                INDEX(" + separar[1] + ")");
                    Indice = separar[1];                  
                    break;
                case ".T":                  
                    Titulo = scan.nextLine();                  
                    break;
                case ".A":                  
                    Autor = scan.nextLine();                    
                    break;
                case ".W":
                    while(!(scan.hasNext(".X")) && scan.hasNextLine()){
                        Texto += scan.nextLine() + " ";
                    }
                    SolrInputDocument doc = new SolrInputDocument();
                    HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/lacoleccion").build();
                    doc.addField("Index", Indice);
                    Indice = "";
                    doc.addField("Titulo", Titulo);
                    Titulo = "";
                    doc.addField("Autor", Autor);
                    Autor = "";
                    doc.addField("Texto", Texto);
                    Texto = "";
                    solr.add(doc);
                    solr.commit();                   
                    break;                             
                default:
                    
            }                      
        }

        
}

}
