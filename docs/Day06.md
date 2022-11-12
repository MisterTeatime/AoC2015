# [Day 6: Probably a Fire Hazard](https://adventofcode.com/2015/day/6)

## Übersicht

Anweisungen geben an wie die Lichter eines Lichternetzes mit 1000 x 1000 Lichtern aktiviert werden sollen. Eine Anweisung hat die Struktur `<Kommando> <LichtNW> through <LichtSO>`.

Für Teil 1 ist die Anzahl der leuchtenden Lichter nach Abarbeiten des Programms die Lösung.
Für Teil 2 ändert sich die Interpretation der Anweisungen und die Lösung ist die Gesamthelligkeit des Netzes

## Umsetzung

Für die Lösung müssen verschiedene Dinge erledigt werden:

* Die Eingabe in ein Programm umwandeln
* Das Programm entsprechend der jeweiligen Interpretation der Anweisungen ausführen
* Das Ergebnis ermitteln

### Überlegungen

* Größtes Problem dürfte die Anzahl der Lichter in Verbindung mit der Größe des Programms sein
  * Das Netz besteht aus 1.000.000 Lichtern und die Eingabe besteht aus 300 Anweisungen
  * FÜr Tests wird ein 6x6 Netz und 3 Anweisungen verwendet

#### Umwandlung der Eingabe

* Es gibt keine eindeutigen Trenner zwischen den einzelnen Teilen. Da es die Anweisungen `turn off` bzw. `turn on` gibt, scheidet das Leerzeichen aus
* Reguläre Ausdrücke scheinen hier die bessere Wahl zu sein. Mit Gruppen und Destructuring können die wichten Teile extrahiert werden
* Die verwendete Regex ist `(\D+) (\d+,\d+) through (\d+,\d+)`
  * `(\D+)` ist die erste Resultgruppe und sucht mindestens ein Zeichen, das keine Ziffer ist. Sie stellt das Kommando dar
  * `(\d+,\d+)` wird für die anderen beiden Resultgruppen und sucht nach mindestens einer Ziffer, gefolgt von einem `,`, gefolgt mindestens einer weiteren Ziffer. Diese Gruppe erkennt die Koordinaten der Eckpunkte des Bereichs
  * `through` wird buchstäblich genommen. Die Zeichenkette ist irrelevant und dient zusammen mit den Leerzeichen als Trenner der relevanten Gruppen
* Die Elemente der Eingabe werden mit der Regex in entsprechende *MatchResults* umgewandelt, wobei das erste Result verworfen wird, weil es immer der gesamte Ausdruck ist. Diese werden dann in *Command* Objekte umgewandelt, in dem die drei Results auf Variablen expandiert werden und diese als Eingabe für den Konstruktor von *Command* verwendet werden. Als Letztes werden `NOP` Anweisungen ausgefiltert
  * Der letzte Schritt ist nicht zwingend notwendig, da in der Eingabe keine unbekannten Kommandos verwendet werden. Der Vollständigkeit halber wird es aber gemacht

#### Command Klasse

Die Data Klasse nimmt die gefundene Anweisung sowie die Koordinaten für Start und Ende entgegen und setzt die Eigenschaften entsprechend:

* **instruction**: Eine Ausprägung der Enum *Inst*
* **startPoint**: Ein *Point2D*, für dessen Eigenschaften die Startkoordinaten am Komma gesplittet werden
* **endPoint**: Ein *Point2D*, für dessen Eigenschaften deie Endkoordinaten am Komma gesplittet werden

### Erster Versuch Teil 1

Aufgrund der Menge an Lichtern (Punkten) war die Überlegung, dass nur die Lichter gesammelt werden, die leuchten. Für jedes Kommando werden die betroffenen Lichter ermittelt und dann die Liste der leuchtenden Lichter entsprechend befüllt.

* **ON**: Alle betroffenen Punkte werden dem Set der leuchtenden Lichter hinzugefügt (da es ein Set ist, entstehen keine Duplikate)
* **OFF**: Alle betroffenen Punkte werden aus dem Set entfernt
* **TOGGLE**: Die Liste der betroffenen Punkte wird partitioniert, je nachdem, ob die Lichter bereits leuchten oder nicht. Die leuchtenden Lichter werden entfernt, die nicht leuchtenden hinzugefügt.

Das Vorgehen ist grundsätzlich erfolgreich, aber der Overhead ist so groß, dass Teil 1 ca. 30 Sekunden für die Abarbeitung braucht. Für Teil 2 ist der Ansatz nicht zu verwenden, weil die Lichter nicht mehr "binär" sind.

### Neuer Ansatz

Ein Teil des Overheads scheint darin zu liegen, dass das Set immer wieder durchsucht werden muss und die Punkte verglichen werden müssen. Außerdem muss das Set immer wieder in der Größe angepasst werden.

Die y Stellen einer x-Spalte könnten auch linear hintereinander abgespeichert werden. Der Index errechnet sich dann durch die Formel `dimX * x + y`. Dadurch entfällt die Verwendung von Punkten. Die Größenanpassung kann auch vermieden werden, in dem auf ein Array zurückgegriffen wird. Für Teil 1 würde ein BooleanArray genügen, für Teil 2 wird aber ein IntArray benötigt. Daher wird für Teil 1 einfach auf die Werte 0 und 1 beschränkt und die Implementierung kann für beide Teile verwendet werden.

Die Verarbeitung des Programms könnte zentral durch eine einzige Methode durchgeführt werden. Diese erhält die Eingabe, für Testzwecke die Dimensionen der Fläche, sowie drei Funktionen, die für die Fälle *ON*, *OFF* und *TOGGLE* auf den Licht-Array angewendet werden.

Das Programm wird durch `forEach()` abgearbeitet. Für jedes Kommando wird eine `IntRange()` über die X-Koordinaten von Start und Ende erzeugt. Wiederum mit `forEach()` wird für jedes x eine weitere `IntRange()` über die Y-Koordinaten von Start und Ende erzeugt. Für jedes y wird der Index des Lichts im Array errechnet und dessen Wert über die drei Funktionen verändert.

### Teil 1

Für die Lösung von Teil 1 wird die Verarbeitungsmethode mit diesen drei Funktionen gefüttert:

* **ON** = `{ 1 }`: Das Licht am Index wird auf 1 gesetzt und damit angeschaltet
* **OFF** = `{ 0 }`: Das Licht am Index wird auf 0 gesetzt und damit ausgeschaltet
* **TOGGLE** = `{ if (it == 0) 1 else 0 }`: Wenn der Wert am Index 0 ist, wird er auf 1 gesetzt, sonst auf 0. Die Lichter im Bereich wechseln alle ihren Status

Nach der Transformation wird die Anzahl der Elemente mit Wert 1 ausgegeben (also alle Lichter, die an sind)

### Teil 2

Für die Lösung von Teil 2 wird die Verarbeitungsmethode mit diesen drei Funktionen gefüttert:

* **ON** = `{ it + 1 }`: Die Helligkeitsstufe des Lichts am Index wird um 1 erhöht
* **OFF** = `{ if (it == 0) 0 else it - 1 }`: Die Helligkeitsstufe des Lichts am Index wird um 1 gesenkt. Sollte der Wert bereits 0 sein, dann wird die 0 beibehalten
* **TOGGLE** = `{ it + 2 }`: Die Helligkeitsstufe des Lichts am Index wird um 2 erhöht

Nach der Transformation wird die Summe über alle ELemente gebildet und so die Gesamthelligkeit des Lichtnetzes ermittelt.

## Testdaten

```
Teil 1           Teil 2

. . . . . .      0 0 0 0 0 0
. . . . . .      0 0 0 0 0 0
. . . . . .      0 0 0 0 0 0
. . . . . .      0 0 0 0 0 0
. . . . . .      0 0 0 0 0 0
. . . . . .      0 0 0 0 0 0

0 Lichter an     Helligkeit: 0

turn on 0,0 through 5,5

x x x x x x      1 1 1 1 1 1
x x x x x x      1 1 1 1 1 1
x x x x x x      1 1 1 1 1 1
x x x x x x      1 1 1 1 1 1
x x x x x x      1 1 1 1 1 1
x x x x x x      1 1 1 1 1 1
                 
36 Lichter an    Helligkeit: 36

turn off 1,1 through 4,4

x x x x x x      1 1 1 1 1 1
x . . . . x      1 0 0 0 0 1
x . . . . x      1 0 0 0 0 1
x . . . . x      1 0 0 0 0 1
x . . . . x      1 0 0 0 0 1
x x x x x x      1 1 1 1 1 1

20 Lichter an    Helligkeit: 20

toggle 0,0 through 5,5

. . . . . .      3 3 3 3 3 3
. x x x x .      3 2 2 2 2 3
. x x x x .      3 2 2 2 2 3
. x x x x .      3 2 2 2 2 3
. x x x x .      3 2 2 2 2 3
. . . . . .      3 3 3 3 3 3

16 Lichter an    Helligkeit: 92
```