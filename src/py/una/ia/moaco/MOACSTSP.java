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

import py.una.ia.util.ConjuntoPareto;
import py.una.ia.util.Solucion;

/**
 *
 * @author Maximiliano Báez <mxbg.py@gmail.com>
 */
public class MOACSTSP extends MOACS{

    
    public void start() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void construirSolucion(int estOrigen, int onlineUpdate, Solucion solucion) {
       int estVisitados = 0;
        int sgteEstado = 0;
        int estActual = estOrigen;
        solucion.set(estVisitados, estOrigen);
        estVisitados++;
        while (estVisitados < (problema.getSize())) {
            sgteEstado = selectNextNode(estActual, solucion);
            if (onlineUpdate != 0) {
                online_update(estActual, sgteEstado);
            }
            estActual = sgteEstado;
            solucion.set(estVisitados, sgteEstado);
            estVisitados++;
        }
        solucion.set(estVisitados, estOrigen);
    }

/*
    public void ejecutarTSP() {
        int generacion = 0;
        int estOrigen;
        double deltaTao;
        double taoPrima;
        long start;
        long end;
        Solucion[] sols = new Solucion[cantidadHormigas];
        for (int i = 0; i < cantidadHormigas; i++) {
            sols[i] = new Solucion(problema.getSize() + 1);
        }
        start = System.currentTimeMillis();
        end = start;
        tao = -1;
        while (condicion_parada(generacion) == 0) {
            generacion++;
            for (int i = 0; i < cantidadHormigas; i++) {
                estOrigen = rand() % (problema.getSize()); // colocar a la hormiga en un estado inicial aleatorio
                hormigaActual = i + 1; // utilizado en seleccionar_sgte_estado
                construir_solucionTSP(estOrigen, this, 1, sols[i]);
            }

            for (int i = 0; i < cantidadHormigas; i++) {
                if (pareto.agregarNoDominado(sols[i], prob) == 1) {
                    pareto.eliminarDominados(sols[i], prob);
                }
                //else
                sols[i].resetear();
            }

            taoPrima = calcular_tao_prima(calcular_average(1), calcular_average(2));

            if (taoPrima > tao) {
                // reiniciar tabla de feromonas
                tao = taoPrima;
                tabla1.reiniciar(tao);
            } else {
                // actualizan la tabla las soluciones del frente Pareto
                for (int i = 0; i < pareto.getSize(); i++) {
                    deltaTao = calcular_delta_tao(pareto.getSolucion(i));
                    actualizar_feromonas(pareto.getSolucion(i), pareto.getSolucion(i).getSize(), deltaTao);
                }
            }
            end = System.currentTimeMillis();
        }
        //	pareto->listarSoluciones(prob,"/home/fuentes/tsp.moacs.pareto");
    }
    
    */

  


}
