# [Day 1: Not Quite Lisp](https://adventofcode.com/2015/day/1)

## Teil 1

Die zur Verfügung gestellten Anweisungen müssen ausgewertet werden, um herauszufinden in welchem Stockwerk Santa am Ende herauskommt.

Da die Reihenfolge der Anweisungen unerheblich ist, ist der naheliegendste Ansatz, dass man einfach zählt wie oft `(` vorkommt und davon die Anzahl der `)` abzieht.

Mit `input.count{ "(".contains(it) }` werden nur die Vorkommen von `(` gezählt. Für `)` wird der gleiche Aufruf verwendet.

## Teil 2

Jetzt muss herausgefunden werden mit welcher Anweisung Santa das erste Mal in den Untergrund geht.

Der Ansatz ist, dass eine laufende Summe gebildet wird, die die Position des Lifts darstellt. Wenn diese Summe zu irgendeinem Zeitpunkt unter 0 fällt, befindet sich der Lift unter der Erde. Die Position der Anweisung wird in einer Variablen gespeichert und Flag wird gesetzt, dass anzeigt, dass der Lift bereits unter der Erde war.

`input.foldIndexed(0)` bietet einen aktuellen Index an (beginnend bei 0) und startet mit Stockwerk 0. In jeder Iteration wird als Erstes geprüft, ob das Stopbedingung geprüft. Wenn die Summer unter 0 liegt UND der Lift noch nicht unter der Erde war, dann wird der aktuelle Index gespeichert und das Flag gesetzt. Danach wird basierend auf dem aktuellen Zeichen die Summer angepasst.

Da die Überprüfung bei jedem Durchlauf als Erstes stattfindet, ist der Index eigentlich einen Schritt zu weit. Da aber die Position der Anweisungen mit Anweisung 1 beginnt, wird das gleich berücksichtigt.

provides an index (starting at 0) and starts with a total of 0. First the "stopping" condition is checked. If the total is below 0 AND we haven't been underground, we save the current index and set the flag. Based on the current character the total is increased or decreased.

Since the underground check takes place at the start of the loop, 