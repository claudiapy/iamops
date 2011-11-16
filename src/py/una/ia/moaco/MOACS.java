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
import py.una.ia.problemas.Problema;
import py.una.ia.problemas.QAP;
import py.una.ia.problemas.TSP;
import py.una.ia.util.ConjuntoPareto;
import py.una.ia.util.Random;
import py.una.ia.util.Solucion;

/**
 * 
 * @author Maximiliano Báez <mxbg.py@gmail.com>
 */
public class MOACS extends MOACO {

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
     * Tabla de feromonas
     */
    protected TablaFeromonas tabla;
    /**
     * Valor de Tao
     */
    protected double tao;
    /**
     *
     */
    protected double q0;
    /**
     * Horiga que esta siendo actualmente procesada, utilizado para calcular 
     * los pesos lambda
     */
    protected int hormigaActual;

    /**
     * Constructor de la calse moacs.
     * 
     * @param problema instancia del problema a resolver.
     * @see {@link TSP}
     * @see {@link QAP}
     */
    public MOACS(Problema problema) {
        this.alfa = 1;
        this.beta = 3.333;
        this.rho = 0.333;
        this.taoInicial = 0.1;
        this.q0 = 0.5;
        this.pareto = new ConjuntoPareto();

        tabla = new TablaFeromonas(problema.getSize());
        this.problema = problema;

        tabla.reset(taoInicial);
    }

    /**
     * Este método arranca el algoritmo.
     */
    public void start() {
        int generacion = 0;
        int estOrigen;
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
                construirSolucion(estOrigen, sols[i]);
            }
            for (int i = 0; i < cantidadHormigas; i++) {
                if (pareto.add(sols[i])) {
                    pareto.reduce();
                }
                sols[i] = new Solucion(problema.getSize());
            }

            taoPrima = calcularTaoPrima(calcularAverage());

            if (taoPrima > tao) {
                // reiniciar tabla de feromonas
                tao = taoPrima;
                tabla.reset(tao);
            } else {
                // actualizan la tabla las soluciones del frente Pareto
                for (int i = 0; i < pareto.size(); i++) {
                    actualizarFeromonas(pareto.get(i));
                }

            }

        }
    }

    /**
     * Este método se encarga de construir las soluciones, a partir del estado
     * origen.
     * @param estOrigen estado inicial
     * @param solucion varible en donde se almacenará la solución construida.
     */
    public void construirSolucion(int estOrigen, Solucion solucion) {
        int estVisitados = 0;
        int sgteEstado;
        int estActual = estOrigen;

        solucion.set(estVisitados, estOrigen);
        estVisitados++;

        while (estVisitados < problema.getSize()) {

            sgteEstado = selectNextNode(estActual, solucion);

            onlineUpdate(estActual, sgteEstado);
            estActual = sgteEstado;
            solucion.set(estVisitados, sgteEstado);
            estVisitados++;

        }
        problema.evaluar(solucion);

    }

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
        //problema.evaluar(solucion);
        Boolean[] visitados = new Boolean[problema.getSize()];
        double q;
        // marcar estados ya visitados, hallar el vecindario
        for (int i = 0; solucion.get(i) != Solucion.EMPTY; i++) {

            visitados[solucion.get(i)] = true;
        }
        for (int i = 0; i < visitados.length; i++) {
            if (visitados[i] == null) {
                visitados[i] = false;
            }
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
            //System.out.print( "Visitados "+ visitados[i]);
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
            //System.out.print( "Visitados "+ visitados[i]);
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
     * <p>
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
     * <p>
     * lambda = (t - 1)/(m - 1)
     * <p>
     * Donde t es indice de la hormiga actual y m la cantidad total de hormigas.
     *
     * @return el valor de lambda calculado.
     */
    private Double calcularLambda() {
        return (hormigaActual - 1) / (cantidadHormigas - 1) * 1.0;
    }

    /**
     * Este método se encarga de actualizar las feromonas, teniendo en cuenta
     * la siguiente formula:
     * <p>
     * T(i,j) = (1 - rho) . T(i,j) + rho / F1*F2
     * 
     * @param solucion solución con la cual se actualiza las feromonas.
     */
    protected void actualizarFeromonas(Solucion solucion) {
        double value = 0.0;
        double[] avg = calcularAverage();
        for (int i = 0; i < solucion.getPath().length - 1; i++) {
            int j = solucion.get(i);
            int k = solucion.get(i + 1);
            value = (1 - rho) * tabla.getValueAt(j, k) + rho / (avg[0] * avg[1]);
            tabla.setValueAt(j, k, value);
        }
    }

    /**
     * Este método se calcula el valor para To' (deltaTao) de la siguiente forma:
     * <p>
     * To' = 1 / sqrt ( n . F1avg . F2avg ). 
     * <p>
     * Donde  n es el nro de nodos (para el TSP es constante y no influye en 
     * esta fórmula). Es decir son un indicador de la calidad del Conjunto 
     * Pareto en ese momento.
     * 
     * @param avr es un array que contiene los valores de F1avg , F2avg y n.
     * @return el valor de To' teniendo en cuenta el Conjunto Pareto.
     * @see #calcularAverage() 
     */
    private double calcularTaoPrima(double[] avr) {

        return (1.0 / Math.sqrt(avr[0] * avr[1] * avr[2]) * 1.0);
    }

    /**
     * Este método se encarga de actualizar las feromonas. Metodo de actualización
     * paso a paso, teniendo en cuenta :
     * la siguiente formula:
     * <p>
     * T(i,j) = (1 - rho)* T(i,j) + rho* To.
     * @param origen
     * @param destino 
     */
    public void onlineUpdate(int origen, int destino) {
        double tau;
        tau = (1 - rho) * tabla.getValueAt(origen, destino) + rho * taoInicial;
        tabla.setValueAt(origen, destino, tau);
    }

    /**
     * Este método calula los promedios de las evaluaciones de la 1ra (F1) y 
     * 2da (F2) funciones objetivos teniendo en cuenta solo las soluciones que 
     * pertenecen en ese momento al Conjunto Pareto.
     * 
     * @return un array que contiene los valores de F1avg , F2avg y navg
     */
    private double[] calcularAverage() {

        double avr1 = 0.0, avr2 = 0.0, avr3 = 0.0;

        for (int i = 0; i < pareto.size(); i++) {
            avr1 += (pareto.get(i).getEvaluacionValueAt(0));
            avr2 += pareto.get(i).getEvaluacionValueAt(1);
            avr3 += pareto.get(i).size();
        }
        double size = pareto.size() * 1.0;
        return new double[]{avr1 / size, avr2 / size, avr3 / size};
    }
}
