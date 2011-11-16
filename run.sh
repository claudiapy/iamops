#!/bin/sh

#se construye el proyecto
ant clean
ant compile
ant jar
clear
max=100
gen=1000
#se inicia la ejecucion
echo "Starting kroac100 ..."
java -jar dist/iamops.jar 'instancias/kroac100.tsp.txt' 'TSP' $max $gen> result/tsp1.csv
echo "Done..."

echo "Starting KROAB100 ..."
java -jar dist/iamops.jar 'instancias/KROAB100.TSP.TXT' 'TSP' $max $gen> result/tsp2.csv
echo "Done..."
#se ejecuta para qap

echo "Starting qapUni.75.p75.1 ..."
java -jar dist/iamops.jar 'instancias/qapUni.75.p75.1.qap.txt' 'QAP' $max $gen> result/qap1.csv
echo "Done..."

echo "Starting qapUni.75.0.1..."
java -jar dist/iamops.jar 'instancias/qapUni.75.0.1.qap.txt' 'QAP' $max $gen> result/qap2.csv
echo "Done..."
