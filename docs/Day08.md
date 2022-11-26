# [Day 8: Matchsticks](https://adventofcode.com/2015/day/8)

## übersicht

### Escaped Characters

| Escapesequenz | Bedeutung                                              |
|---------------|--------------------------------------------------------|
| `\\`          | `\` - Backslash                                        |
| `\"`          | `"`- Gänsefüßchen                                      |
| `\xHH`        | ASCII-Character der durch den Hexcode HH definiert ist |

D.h. Das Ergebnis einer Escapesequenz ist immer genau ein Zeichen

### Testdaten

| String       | Länge im Code | Länge im Memory |
|--------------|---------------|-----------------|
| `""`         | 2             | 0               |
| `"abc"`      | 5             | 3               |
| `"aaa\"aaa"` | 10            | 7               |
| `"\x27"`     | 6             | 1               |
| **SUMME**    | **23**        | **11**          |


## Teil 1

Gesucht wird die Differenz zwischen der Länge aller Einträge im Code und der Länge aller Eintrag im Memory. Mit den Testdaten als 

## Teil 2

