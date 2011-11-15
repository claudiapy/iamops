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

import java.util.ArrayList;
import py.una.ia.util.ConjuntoPareto;
import py.una.ia.util.Random;
import py.una.ia.util.Solucion;

/**
 * 
 * @author Maximiliano Báez <mxbg.py@gmail.com>
 */
public abstract class MOACS extends MOACO {

    /**
     * Peso para las feromonas.
     */
    private double alfa;
    /**
     * Peso para la visibilidad.
     */
    private double beta;
    /**
     * Coeficiente de evaporación.
     */
    private double rho;
    /*
     * Valor inicial para las tablas de feromonas.
     */
    private double taoInicial;
    /**
     * criterio = 1
    tiempoTotal = 10
    maxIteraciones = (10;
    this.cantidadHormigas = 10;
    this.alfa = 1;
    this.beta = 2;
    this.rho = 0.1;
    this.taoInicial = 1;
    this.q0 = 0.5;
    this.F1MAX = 150000;
    this.F2MAX = 150000;
    this.NORM1 = 3000;
    this.NORM2 = 3000;
     */
    protected TablaFeromonas tabla;
    protected double tao;
    protected double q0;
    protected double F1MAX; // utilizados para normalizacion
    protected double F2MAX;
    
    protected int hormigaActual; // utilizado para calcular los pesos lambda
    private int noLambdas;

    public abstract void start();

    public abstract void construirSolucion(int estOrigen, int onlineUpdate,
            Solucion solucion);
    /**
     * Este método se encarga de retornar el siguiente nodo a visitar, apartir
     * de nodo origen.
     *
     * @param origen nodo origen desde el cual se desa realizar la transición.
     * @param solucion solución actual.
     * @return el siguiente nodo a visitar
     */
    public int selectNextNode(int origen, Solucion solucion) {
        int sgteEstado;
        System.out.println("SRC " + solucion.getPath()[60]);
        problema.evaluar(solucion);
        Boolean[] visitados = new Boolean[problema.getSize()];
        double q;
        // marcar estados ya visitados, hallar el vecindario
        for (int i = 0; solucion.get(i) != Solucion.EMPTY; i++) {
            visitados[solucion.get(i)] = true;
        }
        q = Random.rand() / (double) Random.RAND_MAX;
        if (q <= q0) {
            sgteEstado = selectMax(origen, visitados);
        } else {
            sgteEstado = selectProbabilistict(origen, visitados);
        }

        return sgteEstado;
    }
    /**
     *
     * @param origen
     * @param visitados
     * @return
     */
    private int selectMax(int origen, Boolean[] visitados) {
        int sgteEstado = 0;
        double valorActual, mayorValor = -1;
        double lambda;
        // se calcula el valor de lambda
        lambda = calcularLambda();
        for (int i = 0; i < problema.getSize(); i++) {
            //nodo no visitado
            if (!visitados[i]) {
                valorActual = evaluarFormula(origen, i, lambda);
                //si el valor actual es el maximo
                if (valorActual > mayorValor) {
                    //se actuliza el máximo
                    mayorValor = valorActual;
                    //se esablece el siguiente nodo.
                    sgteEstado = i;
                }
            }
        }
        return sgteEstado;
    }
    /**
     * 
     * @param origen
     * @param visitados
     * @return
     */
    private int selectProbabilistict(int origen, Boolean[] visitados) {

        int next = 0;
        ArrayList<Double> productos = new ArrayList<Double>();
        ArrayList<Integer> indices = new ArrayList<Integer>();
        double suma = 0;
        double eval = 0;
        double lambda = calcularLambda();
        // hallar la suma y los productos
        for (int i = 0; i < problema.getSize(); i++) {
            if (!visitados[i]) {
                //se evalua las posibles soluciones
                eval = evaluarFormula(origen, i, lambda);
                //se añaden las evaluaciones a la lista
                productos.add(eval);
                //se añaden los indices a la lista
                indices.add(i);
                //Se calcula la sumatoria
                suma += eval;
            }
        }
        Double mejor = -1.0;
        //se busca la solucion que posea la mayor probabilidad
        for (int i = 0; i < productos.size(); i++) {
            eval = productos.get(i) / suma;
            if (eval > mejor) {
                mejor = eval;
                next = indices.get(i);
            }

        }

        return next;
    }
    /**
     * Este método se encaraga de evaluar la expresión definida de la siguente
     * forma :
     *      tau[i,j]*(n0[i,j]^lambda*B)*(n1[i,j]^(1-lambda)*B)
     * 
     * @param origen indice inicial de la tabla
     * @param destino indice final/destino de la tabla
     * @param lambda valor de lambda
     * @return el resultado de la aplicación de la fórmula.
     * @see #calcularLambda() 
     */
    private Double evaluarFormula(int origen, int destino, double lambda) {
        double firstVisibilidad, secondVisibilidad;
        double valorActual;
        //se calcula las visibildades del problema.
        firstVisibilidad = 1 / problema.getFirstValueAt(origen, destino);
        secondVisibilidad = 1 / problema.getSecondValueAt(origen, destino);
        //se calcula el volor actual
        valorActual = tabla.getValueAt(origen, destino)
                * Math.pow(firstVisibilidad, lambda * beta)
                * Math.pow(secondVisibilidad, (1 - lambda) * beta);

        return valorActual;
    }
    /**
     * Este metodo se encarga de calcular el valor de lambda definido por:
     * (t - 1)/(m - 1), donde t es indice de la hormiga actual y m la cantidad
     * total de hormigas.
     *
     * @return el valor de lambda calculado.
     */
    private Double calcularLambda() {
        return (hormigaActual - 1) / (cantidadHormigas - 1) * 1.0;
    }
    /**
     * 
     * @param solucion
     * @param deltaTau
     */
    protected void actualizarFeromonas(Solucion solucion, double deltaTau) {

        for (int i = 0; i < solucion.getPath().length - 1; i++) {
            int j = solucion.get(i);
            int k = solucion.get(i + 1);
            tabla.setValueAt(j, k, tabla.getValueAt(j, k) + deltaTau);
        }
    }

    /*================================ */
  
 
    protected double calcular_delta_tao(Solucion sol) {
        double delta;

        delta = 1.0 / ((sol.getEvaluacionValueAt(0)) / F1MAX + sol.getEvaluacionValueAt(1) / F2MAX); //normalizados
        return delta;
    }
    protected double calcular_tao_prima(double avr1, double avr2) {
        return (1.0 / (avr1 * avr2));
    }
    public void online_update(int origen, int destino) {
        double tau;
        tau = (1 - rho) * tabla.getValueAt(origen, destino) + rho * taoInicial;
        tabla.setValueAt(origen, destino, tau);
    }
      protected double calcular_average(int obj) {
        double avr = 0;
        for (int i = 0; i < pareto.size(); i++) {
            if (obj == 1) {
                avr += (pareto.get(i).getEvaluacionValueAt(0));
            } else {
                avr += pareto.get(i).getEvaluacionValueAt(1);
            }
        }

        return (avr / (double) pareto.size());
    }


   
}
