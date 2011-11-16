/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 */
package py.una.ia.util;

/**
 *
 * @author Maximiliano Báez <mxbg.py@gmail.com>
 */
public class Solucion {

    /**
     * Representa al camino de nodos a visitar para llegar al nodo final u objetivo
     * esta compuesto por los indices de las nodos visitados en orden de visita.
     */
    private Integer[] path;
    /**
     * Reperesenta la evaluación para la solucion en cuanto a los objetivos
     * definidos para el problema
     */
    private Double[] evaluacion;

    public static int EMPTY = -1;

    public Solucion(int pathLen, int evaluacionLen) {
        path = new Integer[pathLen];
        evaluacion = new Double[evaluacionLen];
    }

    public Solucion(int pathLen) {
        path = new Integer[pathLen];
        evaluacion = new Double[2];
        
        for (int i=0; i<pathLen; i++){
            path[i] = EMPTY;
        }
    }

    /**
     * Este método retorna el camino de  soluciones, compuesto por los indices
     * de los nodos ordenados por el orden de visita.
     *
     * @return el path de la soluciones
     */
    public Integer[] getPath() {
        return path;
    }

    /**
     * Este método esablece el camino a seguir, denotado por los indices de los
     * nodos ordenados por el orden de visita.
     * 
     * @param path El camino a seguir
     */
    public void setPath(Integer[] path) {
        System.arraycopy(path, 0, this.path, 0, path.length);
    }

    /**
     * Este método establece la evaluación para esta solución acorde a los
     * objetivos definidos para el problema especificado.
     * 
     * @param evaluacion la evaluación para el problema
     */
    public void setEvaluacion(Double[] evaluacion) {
        System.arraycopy(evaluacion, 0, this.evaluacion, 0, evaluacion.length);

    }

    /**
     *Este método retorna la evaluación para esta solución acorde a los
     *objetivos definidos para el problema especificado.
     *
     * @return la evaluación para el problema
     */
    public Double[] getEvaluacion() {
        return evaluacion;

    }

    /**
     * Este método controla si esta solución es dominada por otra solucion
     *
     * @param solucion solucion que se sera comparada con la solución actual
     * @return true si es que la solucion es dominada, false en caso contrario
     */
    public boolean domina(Solucion solucion) {
        Double[] testSolu = solucion.getEvaluacion();
        int mejor = 0;
        int igual = 0;
        int min = 10;
        int max = 5000;
        for (int i = 0; i < evaluacion.length; i++) {
            if (evaluacion[i] < testSolu[i] ) {
                mejor++;
            
            } else if (evaluacion[i] > testSolu[i] ) {
                return false;
            }
            
            /*if ((testSolu[i] - evaluacion[i] ) > max ) {
                mejor++;
            } else if ((testSolu[i] - evaluacion[i]) <= max  &&
                    (testSolu[i] - evaluacion[i]) > min) {
            //else if(evaluacion[i] == testSolu[i])   {
                igual ++;
            } else if (evaluacion[i] > (testSolu[i]) ) {
                return false;
            }*/
        }
        
        
        return !equals(solucion) && (mejor>0);
    }
    /**
     * Este método verifica si 2 soluciones son iguales, para ello compara la 
     * parte entera de las soluciones, para evitar que soluciones lijeramente.
     * 
     * @param solucion solución a se comparada.
     * @return true si son iguales, en caso contrario false.
     */
    public boolean equals(Solucion solucion){
        return evaluacion[0].intValue() == solucion.evaluacion[0].intValue() &&
               evaluacion[1].intValue() == solucion.evaluacion[1].intValue();
    }
    /**
     * Este metodo obtiene el valor del path de la posición index.
     * 
     * @param index indice del path de nodos.
     * @return el valor del nodo ubicado en la posición index.
     */
    public Integer get(int index){
        return path[index];
    }
    /**
     * Este metodo establece el valor del path de la posición index.
     *
     * @param index indice del path de nodos.
     * @param value el valor a esablecer al nodo en la posición index.
     */
    public void set(int index, int value){
       path[index] = value;
    }
    /**
     * Este método devuelve la evaluación del objetivo en número index de la
     * solucion actual.
     *
     * @param index indice del objetivo del cual se desea obtener el valor.
     * @return el valor del objetivo número index.
     */
    public Double getEvaluacionValueAt(int index){
        return evaluacion[index];
    }
    
    @Override
    public Solucion clone(){
        Solucion solucion= new Solucion(path.length);
        solucion.setEvaluacion(evaluacion);
        solucion.setPath(path);
        return solucion;
        
        
        
    }
    /**
     * 
     * @return 
     */
    public int size(){
        for(int i=0;i< path.length; i++){
            if(path[i] == EMPTY){
                return i;
            }
        }
        return path.length;
    }
    public String toString(){
        String str = evaluacion[0]+"\t"+ evaluacion[1] ;//+" {";
        /*for (int n : path){
            str+= n +", ";
        }*/
        return str.replace(".", ","); //+"}";
    }
}
