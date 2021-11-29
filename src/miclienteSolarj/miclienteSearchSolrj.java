/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miclienteSolarj;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

/**
 *
 * @author usuario
 */
public class miclienteSearchSolrj {

    public static void main(String[] args) throws IOException, SolrServerException {
        String fileName = "CISI.QRY";
        String Texto = "";
        int qryIndex=1;
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
            //OBTENGO LAS 5 PRIMERAS PALABRAS IGNORANDO TABULACIONES
            if (aux.equals(".W")) {
                int i = 0;
                while (scan.hasNextLine() && i < 5) {
                    if (!scan.hasNext(" ")) {
                        Texto = Texto + scan.next() + " ";
                        i++;
                    }
                }
                //PARA ELIMINAR PARÉNTESIS
                Texto = Texto.replace("(","");
                Texto = Texto.replace(")","");
                //**************************
                //EJECUTO CONSULTA CON LAS 5 PALABRAS
                System.out.println("Busquedas para consulta (" + qryIndex++ +"): " + Texto);
                HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/lacoleccion").build();
                SolrQuery query = new SolrQuery();
                query.set("q", "Texto:" + Texto);
                //query.addFilterQuery("Texto:"+Texto);
                query.setFields("Index", "score");
                QueryResponse rsp = solr.query(query);
                SolrDocumentList docs = rsp.getResults();
                for (int j = 0; j < docs.size(); ++j) {
                    System.out.println(docs.get(j));
                    
                }
                //***********************
                Texto = "";
            }
        }

    }
}
