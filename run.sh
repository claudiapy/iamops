#!/bin/sh

#se construye el proyecto
ant clean
ant compile
ant jar
#clear
max=100
gen=1000
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
