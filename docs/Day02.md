# [Day 2: I Was Told There Would Be No Math](https://adventofcode.com/2015/day/2)

## Teil 1

Der Gesamtbedarf an Packpapier für die Nachbestellung soll errechnet werden. Dazu muss für jedes Geschenk die Oberfläche und eine Zugabe errechnet werden. Die Summe ist das gewünschte Ergebnis.

Im Kern wird `input.fold(0)` ausgeführt. Jedes Geschenk trägt zu Gesamtsumme bei. Dazu wird die Zeile an den `x` aufgesplittet, die einzelnen Werte des Arrays dann in Integer umgewandelt und die drei Werte gleich auf Variablen *length*, *width* und *height* heruntergebrochen. Die Oberfläche wird mit `2 * side1 + 2 * side2 + 2 * side3` berechnet, die Zugabe (die kleinste Seite) wird mit `minOf(side1, side2, side3)` bestimmt. Oberfläche, Zugabe und Gesamtsumme zusammen ergeben die neue Gesamtsumme für den nächsten Schritt.

## Teil 2

Auch das Packband geht zu Ende und muss nachbestellt werden. Für ein Geschenk wird der kürzeste Umfang benötigt. Für die Schleife wird zusätzlich Band in der Länge wie das Volumen des Geschenks.

Auch hier wird wieder `input.fold(0)` verwendet. Nach dem Aufsplitten und der Umwandlung wird die Liste sortiert. Das führt dazu, dass die beiden niedrigsten Werte auf Index 0 *length* und 1 *width* zu finden sind.

Mit allen benötigten Werten vorbereitet kann die Gesamtsumme um das aktuellen Geschenk erweitert werden. Der kleinste Umfang ist `length + length + width + width` und das Volumen ist `length * width * height`