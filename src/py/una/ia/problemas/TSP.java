/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.una.ia.problemas;

import py.una.ia.util.Solucion;

/**
 * Esta clase es una representación del problema del TSP
 * 
 * @author mbaez
 */
public class TSP {
    private int ciudades;
    private Double[][] distancia;
    private Double[][] tiempo;
    
    public TSP(){
        this.setCiudades(0);
    }
    /**
     * Este método establece la cantidad de ciudades
     * @param ciudades número de ciudades definidas para el problema
     */
    public void setCiudades(int ciudades){
        this.ciudades = ciudades;
    }
    /**
     * Este método retorna la cantidad de ciudades
     * @return el número de ciudades
     */
    public int getCiudades(){
        return ciudades;
    }
    /**
     * Este método establece la matriz de adyacencia para el objetivo de
     * distancia.
     * 
     * @param distancia matriz de adyacencia para el objetivo de distancia
     */
    public void setDistancia(Double [][]distancia){
        this.distancia = distancia;
    }
    /**
     * Este método retorna la matriz de adyacencia para el objetivo de
     * distancia.
     * 
     * @return la matriz de adyacencia para el objetivo de distancia
     */
    public Double [][] getDistancia(){
        return distancia;
    }
        /**
     * Este método establece la matriz de adyacencia para el objetivo de
     * tiempo.
     *
     * @param distancia matriz de adyacencia para el objetivo de tiempo
     */
    public void setTiempo(Double [][]tiempo){
        this.tiempo = tiempo;
    }
    /**
     * Este método retorna la matriz de adyacencia para el objetivo de
     * tiempo.
     *
     * @return la matriz de adyacencia para el objetivo de tiempo
     */
    public Double [][] getTiempo(){
        return tiempo;
    }
    /**
     * Este método evalua la solución segun los objetivos de distancia y tiempo
     * definidos par ael problema del TSP
     *
     * @param solucion solución a evaluar.
     */
    public void evaluar(Solucion solucion){
        
        Double localTime = 0.0;
        Double localLen = 0.0;
        //se obtine el conjuto de nodos visitados
        Integer [] path = solucion.getPath();

        for(int i=0; i<ciudades; i++){
            int localCity = path[i];
            int nextCity = path[0];

            if(i < ciudades-1){
                //si es la ultima ciudad se debe conservar el como la siguiente
                //ciudad la ciudad de origen, esto es devido a la definicion del
                //problema en si.
                nextCity = path[i+1];
            }
            //se evalua la solución para el objetivo de tiempo
            localTime += tiempo[localCity][nextCity];
            //se evalua la solución para el objetivo de distancia
            localLen += distancia[localCity][nextCity];
        }
        //se establecen las evaluaciones para la solucion acorde a los objetivos
        solucion.setEvaluacion(new Double[]{localTime, localLen});
    }
}
