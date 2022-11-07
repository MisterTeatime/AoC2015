# [Day 3: Perfectly Spherical Houses in a Vacuum](https://adventofcode.com/2015/day/3)

## Teil 1

Santa liefert Geschenke an Häuser in einem 2-dimensionalen, unendlichen Raster aus. Bei jedem Haus (inkl. dem Starthaus), an dem er vorbeikommt lässt er ein Geschenk zurück unabhängig davon, ob er hier bereits eines abgelegt hat oder nicht. Geleitet wird er von einem Elf per Funk, der ihn anweist, in welche Himmelsrichtung das nächste Haus ist.

Die Häuser werden als `Point2D` dargestellt. Die aktuelle Position wird in einer Variablen vorgehalten und es wird ein Set mit den bereits besuchten Häusern gepflegt. Das Starthaus ist auf Position (0,0).

Entsprechend der Anweisung wird die Position verändert und dem Set hinzugefügt. Da ein Set keine Duplikate enthalten kann, kann jedes Haus nur einmal dort auftreten. Die Anzahl der Elemente im Set nach Abarbeiten der Anweisungen ist die gesuchte Zahl.

Um die Positionsänderung komfortabler zu gestalten wird eine Map verwendet, die jeder Anweisung einen entsprechenden Punkt zuordnet, der die erforderliche Änderung darstellt. Die aktuelle Position wird mit diesem Differentpunkt addiert und ergibt so das neue Haus.

## Teil 2

Im diesem Jahr hat Santa sich einen Helfer (Robot-Santa) gebaut, der zusammen mit ihm die Anweisungen abarbeitet. Santa führt dabei die ungeraden Anweisungen und Robot-Santa die gerade Anweisungen aus.

Die Position wird für Santa und Robot-Santa getrennt verfolgt, aber nach einer Bewegung werden die neuen Häuser im gleichen Set erfasst. Wenn einer der beiden irgendwann ein Haus besucht, bei dem der andere bereits war, verändert das die Anzahl der besuchten Häuser nicht. Wie in Teil 1 ist die Anzahl der Elemente im Set die gesuchte Lösung.

Abhängig vom Index der Anweisung wird entschieden wessen Positions aktualisiert wird.