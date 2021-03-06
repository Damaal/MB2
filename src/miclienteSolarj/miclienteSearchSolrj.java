/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miclienteSolarj;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author usuario
 */
public class miclienteSearchSolrj {

    public static void main(String[] args) throws IOException, SolrServerException {
        String fileName = "CISI.QRY";
        FileWriter fichero = null;
        PrintWriter pw = null;
        fichero = new FileWriter("c:/Users/Daniel Marian/Desktop/RESULTADOS.TREC");
        pw = new PrintWriter(fichero);
        String Texto = "";
        String Autor = "";
        String Titulo = "";
        String toTREC = "";
        String Consulta = "";
        int qryIndex = 1;
        //LEO FICHERO QRY
        Scanner scan = new Scanner(new File(fileName));

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String aux;
            if (line.length() > 2) {
                aux = line.substring(0, 2);
            } else {
                aux = line;
            }
            //System.out.println(line);
            switch (aux) {
                case ".I":
                    //no hago nada                  
                    break;
                case ".T":
                    while (!scan.hasNext(".A")) {
                        Titulo += scan.nextLine();
                    }
                    break;
                case ".A":
                    while (!scan.hasNext(".W")) {
                        Autor += scan.nextLine();
                    }
                    break;
                case ".W":
                    while (!scan.hasNext(".I") && !scan.hasNext(".B") && scan.hasNextLine()) {
                        Texto += scan.nextLine() + " ";
                    }
                    //TRATAMIENTO DEL TEXTO DE VARIABLES DE ENTRADA PARA REALIZAR LAS CONSULTAS
                    Texto = Texto.replace("(", "");
                    Texto = Texto.replace(")", "");
                    Texto = Texto.replace(":", "");
                    Titulo = Titulo.replace(":", "");
                    Autor = Autor.replace(":", "");
                    //**************************

                    HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/lacoleccion").build();
                    SolrQuery query = new SolrQuery();
                                        /*(PARA VERSION 2.0)
                    //ESTABLEZCO PRIORIDADES PARA REALIZAR LA B??SQUEDA 
                    if (!"".equals(Titulo) && !"".equals(Autor)) {  //BUSQUEDA POR TITULO Y AUTOR
                        Consulta = "Titulo:" + Titulo + " Autor:" + Autor;
                    } else {
                        if (!"".equals(Autor)) {                    //BUSQUEDA POR AUTOR Y TEXTO
                            Consulta = "Autor:" + Autor + " Texto:" + Texto;
                        } else {                                      //BUSQUEDA POR TEXTO
                            Consulta = "Texto:" + Texto;
                        }
                        if (!"".equals(Titulo)) {                     //BUSQUEDA POR TITULO
                            Consulta = "Titulo:" + Titulo + " Texto:" + Texto;
                        } else {                                      //BUSQUEDA POR TEXTO
                            Consulta = "Texto:" + Texto;
                        }                 
                    }*/                   //(PARA VERSION 2.0)
                    //RALIZACI??N DE LA B??SQUEDA POR TEXTO COMPLETO
                    System.out.println(Texto);
                    query.set("q", "Texto:" + Texto);
                    //EJECUTO LA QUERY Y GENERO EL FICHERO .TREC CON LA ESTRUCTURA NECESARIA
                    //query.addFilterQuery("Texto:"+Texto);
                    query.setFields("Index", "score");
                    QueryResponse rsp = solr.query(query);
                    SolrDocumentList docs = rsp.getResults();
                    for (int j = 0; j < docs.size(); ++j) {
                        String retValue = docs.get(j).toString().replace("SolrDocument{Index=[", "");
                        retValue = retValue.replace("], score=", " ");
                        retValue = retValue.replace("}", "");
                        String[] Spliteo = retValue.split(" ");
                        toTREC = qryIndex + " I0 " + Spliteo[0] + " " + j + " " + Spliteo[1] + " DAM";
                        pw.print(toTREC + "\n");
                    }
                    //***********************
                    //RESETEO VARIABLES 
                    Texto = "";
                    Autor = "";
                    Titulo = "";
                    Consulta = "";
                    qryIndex++;
                    break;
                default:
            }
        }
        fichero.close();  
    }
}
