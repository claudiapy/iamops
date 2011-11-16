#!/bin/sh
# Autor : Maximiliano Báez <mxbg.py@gmail.com>
#       This program is free software; you can redistribute it and/or modify
#       it under the terms of the GNU General Public License as published by
#       the Free Software Foundation; either version 2 of the License, or
#       (at your option) any later version.
#
#       This program is distributed in the hope that it will be useful,
#       but WITHOUT ANY WARRANTY; without even the implied warranty of
#       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#       GNU General Public License for more details.
#
#       You should have received a copy of the GNU General Public License
#       along with this program; if not, write to the Free Software
#       Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
#       MA 02110-1301, USA.
#
# Autor : Maximiliano Báez <mxbg.py@gmail.com>
# Date : 16/11/2011
#se construye el proyecto
ant clean
ant compile
ant jar
#clear
max=100
gen=1000

if [ $# -ge 1 ]
then
    max=$1;
    gen=$2;
fi
i=0
#se inicia la ejecucion
for i in 1 2 3 4 5; do

    echo "Starting kroac100 ..." $i;
    java -jar dist/iamops.jar 'instancias/kroac100.tsp.txt' 'TSP' $max $gen> result/tsp1$i.csv;

    echo "Done...";

    echo "Starting KROAB100 ..."$i;
    java -jar dist/iamops.jar 'instancias/KROAB100.TSP.TXT' 'TSP' $max $gen> result/tsp2$i.csv;
    echo "Done...";
    #se ejecuta para qap

    echo "Starting qapUni.75.p75.1 ..."$i;
    java -jar dist/iamops.jar 'instancias/qapUni.75.p75.1.qap.txt' 'QAP' $max $gen> result/qap1$i.csv;
    echo "Done...";

    echo "Starting qapUni.75.0.1..."$i;
    java -jar dist/iamops.jar 'instancias/qapUni.75.0.1.qap.txt' 'QAP' $max $gen> result/qap2$i.csv;
    echo "Done..."
done;
