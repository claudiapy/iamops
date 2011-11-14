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

    public Solucion(int pathLen, int evaluacionLen) {
        path = new Integer[pathLen];
        evaluacion = new Double[pathLen];
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
        boolean mejor = false;
        boolean igual = false;
        for (int i = 0; i < evaluacion.length; i++) {
            if (evaluacion[i] < testSolu[i]) {
                mejor = true;
            } else if (evaluacion[i] == testSolu[i]) {
                igual = true;
            } else if (evaluacion[i] > testSolu[i]) {
                return false;
            }
        }
        return mejor && igual;
    }
}
