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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maximiliano Báez <mxbg.py@gmail.com>
 */
public class ConjuntoPareto {
    /**
     * lista de soluciones no dominadas pertenecientes al conjunto pareto
     */
    private List<Solucion> soluciones;

    public ConjuntoPareto(){
        soluciones = new ArrayList<Solucion>();
    }

    /**
     * Este método trata de añadir una solucion al conjunto pareto. Esta
     * solución unicamnete sera añaidida al conjunto si es que ninguna solución
     * perteneciente al conjunto pareto no la domina.
     * 
     * @param solucion solucion que se trata de añadir al conjunto pareto
     * @return true si se pudo añadir la solucion, en caso contrario false
     */
    public boolean add(Solucion solucion){
        //lista de soluciones dominadas a ser eliminadas del conjunto
        List<Integer> toRemove = new ArrayList<Integer>();
        
        for(int i=0; i<soluciones.size(); i++){

            if (solucion.domina(soluciones.get(i))){
                //si la solucion actual es dominada, se añade al conjunto de
                //solucones a eliminar
                toRemove.add(i);    
            }else if(soluciones.get(i).domina(solucion)){
                //si la solucion actual es dominante retorna false
                return false;
            }

        }
        //Se eliniman todas las soluciones no dominadas del conjunto pareto
        for(Integer index :  toRemove){
            soluciones.remove(index);
        }
        //se añade la solucion al conjunto pareto
        this.soluciones.add(solucion);
        return true;

    }
    /**
     * Este metodo determina la cantidad de soluciones que se encuentran el el
     * conjunto pareto.
     *
     * @return el tamaño del conjutno pareto.
     */
    public int size(){
        return soluciones.size();
    }
    /**
     * Este método retorna la solución que se encuentra en la posición index del
     * conjunto pareto.
     *
     * @param index número de solución.
     * @return solción del conjunto pareto.
     */
    public Solucion get(int index){
        return soluciones.get(index);
    }
}
