/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.una.ia.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import py.una.ia.problemas.QAP;
import py.una.ia.problemas.TSP;

/**
 *
 * @author mbaez
 */
public class FileManager {

    private BufferedReader file;
    private List<String> lines;

    public FileManager(String fileName) {
        lines = new ArrayList<String>();
        try {
            file = new BufferedReader(new FileReader(new File(fileName)));
            lines = new ArrayList<String>();
            load();
            file.close();
            file = null;
        } catch (Exception ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /**
     * Este método se encarga de cargar las lineas del archivo a un array list
     * para que puedan ser manipuladas posteriormente.
     *
     * @throws IOException si ocurrio un problema cuando al leer el archivo
     */
    private void load() throws IOException {
        String line;
        while ((line = file.readLine()) != null) {
            lines.add(line);
        }

    }
    /**
     * Este método se encarga de transformar una string a un array, donde cada
     * elemento del array se encuentra separado por un espacio.
     *
     * @param line linea a transformar
     * @return un array con los numeros definidos en la linea
     */
    private Double[] parseColumn(String line) {
        //se tokeniza la cadena linea, por el delimitador " " y se omite el
        //delimitador como token.
        StringTokenizer tokens = new StringTokenizer(line, " ", false);
        //se crea el vector
        Double[] column = new Double[tokens.countTokens()];
        int i = 0;
        //se evaluan todos los elementos
        while (tokens.hasMoreTokens()) {
            //se transforma cada elemento como a un double
            column[i] = Double.parseDouble(tokens.nextToken());
        }
        //se retorna la columna
        return column;

    }
    /**
     * Este metodo se encarga de procesar las lineas del archivo cargadas en
     * la lista. Cada linea de la lista es procesada de forma a tener un matriz
     * como la resultante. Si la linea porcesada es una linea en blanco, el 
     * algoritmo finaliza.
     *
     * @param length tamaño de la matriz cuadrada.
     * @return una matriz cuadrada representada por las lineas de la lista
     * @see #load()
     * @see #parseColumn(java.lang.String) 
     */
    private Double[][] parseRows(int length) {
        Double[][] matriz = new Double[length][];
        int i = 0;
        String line = "";
        while ( lines.size() >= 1 && (line = lines.remove(0)).length() > 1) {
            //se carga la matriz
            //System.out.println(line);
            matriz[i] = parseColumn(line);
            i++;
        }
        return matriz;
    }
    /**
     * Este método se encarga de procesar las lineas del archivo teniendo en
     * cuenta el siguiente formato:
     *     Primera Linea, cantidad de ciudades
     *     Segunda Linea, cantidad de objetivos (=cantidad de matrices)
     *     Matriz de adyacencia para el objetivo 1
     *     Una línea en blanco
     *     Matriz de adyacencia para el objetivo 2
     *
     * @param problem la representación del problema al cual se añadiran los
     * valores de establecidos en el archivo.
     */
    public void parse(QAP problem) {
        int localidades = Integer.parseInt(lines.remove(0));
        problem.setLocalidades(localidades);
        problem.setDistancia(parseRows(localidades));
        problem.setFlujoIda(parseRows(localidades));
        problem.setFlujoVuelta(parseRows(localidades));


    }
    /**
     * Este método se encarga de procesar las lineas del archivo teniendo en
     * cuenta el siguiente formato:
     *      Primera Linea, cantidad de localidades (= cantidad de edificios)
     *      Matriz de adyacencia para el objetivo 1 (flujo 1 e/ edificios)
     *      Una línea en blanco
     *      Matriz de adyacencia para el objetivo 2 (flujo 2 e/ edificios)
     *      Una línea en blanco
     *      Matriz de adyacencia para las distancias (distancia e/ localidades)
     * 
     * @param problem la representación del problema al cual se añadiran los
     * valores de establecidos en el archivo.
     */
    public void parse(TSP problem) {

        int ciudades = Integer.parseInt(lines.remove(0));
        String objetivos = lines.remove(0);
        problem.setCiudades(ciudades);
        problem.setDistancia(parseRows(ciudades));
        problem.setTiempo(parseRows(ciudades));

    }

   
}
