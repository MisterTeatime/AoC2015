# [Day 4: The Ideal Stocking Stuffer](https://adventofcode.com/2015/day/4)

## Teil 1

Ein Geheimschlüssel soll um eine positive Ganzzahl erweitert werden, so dass der MD5 Hash mit 5 Nullen beginnt. Gesucht ist die kleinste Ganzzahl, die die Anforderung erfüllt.

Da MD5 eine nicht umkehrbare Funktion ist, bleibt hier nur der "Brute Force" Ansatz und die Hoffnung, dass der Rechner leistungsstark genug ist, um das Ergebnis schnell zu finden.

Ein Zählvariable wird mit `0` initialisiert und danach eine Schleife begonnen, die prüft, ob die Anforderung erfüllt ist. Dazu werden vom generierten MD5 Hashwert mit `take(5)` die ersten 5 Zeichen genommen und geprüft, ob sie gleich `00000` sind.

In der Schleife wird der MD5 Hashwert für die konkatenierte Zeichenkette aus Schlüssel und aktueller Zähler generiert. 

## Teil 2

Die Aufgabe ist quasi die gleiche wie in Teil 1. Der MD5 Hashwert muss jetzt mit 6 Nullen beginnen. Es wird wieder die kleinste, positive Ganzzahl gesucht, die die Anforderung erfüllt.