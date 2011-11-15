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

package py.una.ia.moaco;

import py.una.ia.problemas.Problema;
import py.una.ia.util.ConjuntoPareto;

/**
 *
 * @author Maximiliano Báez <mxbg.py@gmail.com>
 */
public class MOACO {
    /**
     * Este atributo define el número máximo de iteraciones para el algoritmo.
     */
    public static int MAX_ITERACIONES;
    /**
     * Este atributo representa el problema a solucionar.
     */
    public Problema problema;
    public static final int PARETO = 50;
    protected int criterio = 1 ;
    protected int tiempoTotal;
    protected int maxIteraciones;
    protected int cantidadHormigas=10;
    protected ConjuntoPareto pareto;


    /**
     * Este metodo evalua la condición de parada del moaco, en este caso se
     * utiliza como condición de parada un número máximo de iteraciones.
     *
     * @param iteracion la cantidad de iteraciones que ha realizado el algoritmo
     * @return true si se alcanzo el máximo de iteraciones, false en caso
     * contrario.
     */
    public boolean condicionParada(int iteracion) {
        return iteracion > MAX_ITERACIONES;
            
    }


}
