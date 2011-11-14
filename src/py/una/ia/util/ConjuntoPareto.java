/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.una.ia.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mbaez
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
}
