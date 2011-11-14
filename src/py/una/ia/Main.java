/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.una.ia;

import py.una.ia.problemas.QAP;
import py.una.ia.problemas.TSP;
import py.una.ia.util.FileManager;

/**
 *
 * @author mbaez
 */
public class Main {

    public static void main(String args[]) {
       String name = "instancias/qapUni.75.0.1.qap.txt";
       FileManager file = new FileManager(name);
       QAP qap = new QAP();
       file.parse(qap);
       System.out.println(qap.getFlujoVuelta()[qap.getLocalidades()-1].length);
       name = "instancias/kroac100.tsp.txt";
       file = new FileManager(name);
       TSP tsp = new TSP();
       file.parse(tsp);
       System.out.println(tsp.getTiempo()[tsp.getCiudades()-1].length);

    }
}
