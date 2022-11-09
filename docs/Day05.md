# [Day 5: Doesn't He Have Intern-Elves For This?](https://adventofcode.com/2015/day/5)

## Teil 1

Santa hat eine Liste mit Worten, die er in gute und böse Worte einteilt. Dazu gibt es Regeln, die definieren, ob ein Wort gut oder böse ist.

Ein Wort ist gut, wenn es...

* ...mindestens 3 Vokale (`a`, `e` ,`i`, `o`, `u`) enthält
* ...mindestens einen Doppel-Buchstaben besitzt. Also eine Gruppe aus 2 Buchstaben, die gleich sind
* ...keinen der Strings ab, cd, pq oder xy enthält. Auch dann nicht, wenn sie Teil einer der anderen Bedingungen sind

Die Eingabeliste wird gefiltert und am Ende die Anzahl der übriggebliebenen Elemente ausgegeben.

Die strengste Regel ist die mit den verbotenen Strings. Deswegen wird diese Bedingung als erstes geprüft. Über `filterNot` wird jeder String ausgefiltert, bei dem eine beliebige Zweiergruppe (`windowed(2)`) in der Liste der verbotenen Strings enthalten ist.

Für die "3 Vokale" Bedingung wird jeder String behalten, wenn die Anzahl der Zeichen, die in der Liste der Vokale stehen, größer gleich 3 ist (`count { vowels.contains(it) }`).

Der "Doppelbuchstabe" wird geprüft, in dem wieder Zweiergruppen durchgegangen werden. Wenn das erste Zeichen (`take(1)`) gleich dem letzten Zeichen (`drop(1)`) ist, dann wird der String behalten.

## Teil 2

Santa hat festgestellt, dass die Regeln nicht fair waren und hat neue Regeln aufgestellt.

Ein Wort ist gut, wenn es...

* ...ein Paar von zwei beliebigen Buchstaben enthält, das wiederholt wird ohne sich zu überlappen
* ...mindestens einen Buchstaben enthält, der sich wiederholt mit einem einem Buchstaben dazwischen. Es kann auch der gleiche Buchstabe sein

Auch dieses Mal werden die Eingabestrings gefiltert. Die Bedingungen werden dieses Mal über Reguläre Ausdrücke geprüft.

Der Reguläre Ausdruck für die Paarwiederholung ist `(..).*\1`. Es werden Gruppen von aufeinander folgenden Zeichen gesucht `(..)`, die sich wiederholen `\1` und 0 oder mehr Zeichen dazwischen haben.

Der Reguläre Ausdruck für die "Dopplung mit Abstand" ist `(.).\1`. Der Ansatz ist analog zur Paarwiederholung. Diesmal werden einzelne Zeichen gesucht `(.)`, die wiederholt werden `\1` und dabei genau ein Zeichen dazwischen ist `.`.

Wie zuvor auch wird gezählt wie viele Worte die Filter "überlebt haben" und die Anzahl zurückgegeben.

## Testdaten

| String           | Teil 1 | Teil 2 |
|------------------|--------|--------|
| ugknbfddgicrmopn | ✅      | ❌      |
| aaa              | ✅      | ❌      |
| jchzalrnumimnmhp | ❌      | ❌      |
| haegwjzuvuyypxyu | ❌      | ❌      |
| dvszwmarrgswjxmb | ❌      | ❌      |
| qjhvhtzxzqqjkmpb | ❌      | ✅      |
| xxyxx            | ❌      | ✅      |
| uurcxstgmygtbstg | ❌      | ❌      |
| ieodomkazucvgmuy | ❌      | ❌      |

