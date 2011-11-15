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

import py.una.ia.problemas.QAP;
import py.una.ia.util.ConjuntoPareto;
import py.una.ia.util.Random;
import py.una.ia.util.Solucion;

/**
 *
 * @author Maximiliano Báez <mxbg.py@gmail.com>
 */
public class MOACSQAP extends MOACS {

    public MOACSQAP(QAP qap){
            this.cantidadHormigas = 10;
    this.q0 = 0.5;
    this.F1MAX = 150000;
    this.F2MAX = 150000;
    this.pareto = new ConjuntoPareto();
    this.problema = qap;
    }
    public void start() {
        int generacion = 0;
        int estOrigen;
        double deltaTao;
        double taoPrima;
        Solucion[] sols = new Solucion[cantidadHormigas];
        for (int i = 0; i < cantidadHormigas; i++) {
            sols[i] = new Solucion(problema.getSize());
        }

        tao = -1;
        while (!condicionParada(generacion)) {
            generacion++;
            for (int i = 0; i < cantidadHormigas; i++) {
                estOrigen = Random.rand() % (problema.getSize());
                hormigaActual = i + 1;
                construirSolucion(estOrigen, 1, sols[i]);
            }

            for (int i = 0; i < cantidadHormigas; i++) {
                pareto.add(sols[i]);

                //else
                sols[i] = new Solucion(problema.getSize());
            }

            taoPrima = calcular_tao_prima(calcular_average(1), calcular_average(2));

            if (taoPrima > tao) {
                // reiniciar tabla de feromonas
                tao = taoPrima;
                tabla.reset(tao);
            } else {
                // actualizan la tabla las soluciones del frente Pareto
                for (int i = 0; i < pareto.size(); i++) {
                    deltaTao = calcular_delta_tao(pareto.get(i));
                    actualizarFeromonas(pareto.get(i), deltaTao);
                }

            }

        }
    }

    public void construirSolucion(int estOrigen, int onlineUpdate, Solucion solucion) {
        int estVisitados = 0;
        int sgteEstado;
        int estActual = estOrigen;
        
        solucion.set(estVisitados, estOrigen);
        estVisitados++;
        while (estVisitados < problema.getSize()) {

            sgteEstado = selectNextNode(estActual, solucion);

            if (onlineUpdate != 0) {
                online_update(estActual, sgteEstado);
            }
            estActual = sgteEstado;
            solucion.set(estVisitados, sgteEstado);
            estVisitados++;


        }

    }
}
