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

/**
 * Esta clase es una representación de la tabla de feromonas para los MOACOS
 * @author Maximiliano Báez <mxbg.py@gmail.com>
 */
public class TablaFeromonas {

    /**
     * Tamaño del la tabla de feromonas.
     */
    private int size;
    /**
     * Matriz que representa el rastro de feromonas.
     */
    private Double[][] feromonas;

    /**
     * Constructor, que se encarga de inicializar la tabla de fermonas.
     * @param size tamaño de la tabla de fermomonas.
     */
    public TablaFeromonas(int size) {
        this.size = size;
        feromonas = new Double[size][size];
    }

    /**
     * Este método se encarga de retornar el valor de la tabla de fermomonas
     * para la posición especificada.
     *
     * @param origen inidice de la tabla de feromonas.
     * @param destino inidice de la tabla de feromonas.
     * @return valor de la tabla para la posición [origen, destino]
     */
    public Double getValueAt(int origen, int destino) {
        return feromonas[origen][destino];
    }

    /**
     * Este método se encarga de establecer el valor de la tabla en la posición
     * [origen, destino] con el valor tau especificado.
     * 
     * @param origen inidice de la tabla de feromonas.
     * @param destino inidice de la tabla de feromonas.
     * @param tau valor de rastro de feromonas para la posición [origen, destino]
     */
    public void setValueAt(int origen, int destino, double tau) {
        feromonas[origen][destino] = tau;
    }

    /**
     * Este método se encarga de reiniciar los valores de la tabla de feromonas
     * con el valor tau0.
     *
     * @param tau0 valor con el cual se reiniciará la tabla de feromonas.
     */
    public void reset(double tau0) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                feromonas[i][j] = tau0;
            }
        }
    }
}
