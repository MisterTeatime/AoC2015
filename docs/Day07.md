# [Day 7: Some Assembly Required](https://adventofcode.com/2015/day/7)

## Überlegungen

### Anweisungen

Folgende Befehle werden unterstützt:

| Command    | Kotlin  |
|------------|---------|
| `AND`      | `and`   |
| `OR`       | `or`    |
| `NOT`      | `inv()` |
| `LSHIFT x` | `lsh`   |
| `RSHIFT x` | `rsh`   |

### Umgang mit Zahlen

Bei einem normalen `Int` markiert das höchste Bit negative Zahlen. Eine Invertierung führt so zu einer negativen Zahl. Aus **123** wird so **-124**, anstatt erwartet `65412`.

Beim Invertieren (NOT) sollte deshalb auf `UShort` gewechselt werden, da hier der Wertebereich 16-Bit Zahlen umfasst und nicht überschritten werden kann. 

### Parsen der Anweisungen

#### Datenklasse **Relation**

```c#
data class Relation (
    val Command: Mnemonic,
    val Result: String,
    val Operand1: String,
    val Operand2: String = ""
        )
```

`Command` *Mnemonic*: Die auszuführende Aktion, definiert über eine Enumeration
`Result` *String*: Der Empfängerdraht der Relation. In der Eingabe der Teil nach `->`
`Operand1` *String*: Der erste Operand der Anweisung
`Operand2` *String*: Der zweite Operand der Anweisung (kann auch leer sein, wenn die Anweisung nur einen Operanden hat. Z.B. `NOT` oder `ASSIGN`)

Eine neue Relation kann direkt über einen Eingabestring mithilfe der Methode `create()` des Companion Objekts erzeugt werden und ist die empfohlene Methode. Die Eingabe wird mit verschiedenen Regex Ausdrücken getestet. Diese Ausdrücke sind so aufgebaut, dass sie Operanden und Resultat gleich für die weitere Verwendung bereitstellen. Bei einem Treffer wird die Relation mit der ermittelten Anweisung (über die Regex, die trifft), dem Resultat und den Operanden instanziiert und zurückgegeben.

#### Enumklasse Mnemonic

Die Klasse stellt Einträge für die möglichen Anweisungen `AND`, `OR`, `NOT`, `RSHIFT` und `LSHIFT` bereit. Zusätzlich gibt es noch `ASSIGN`, die für die direkte Zuordnung steht (also z.B. `123 -> a` oder `b -> x`), und `NOP` für nicht erkannte Anweisungen.

#### Methode calculate()

```c#
fun calculate(
    relations: List<Relation>,
    results: MutableMap<String, Int>,
    target: String
    ): Int
```

`relations` *List<Relation>*: Die Zuordnungen aus der Eingabe als Relationen
`results` *MutableMap<String, Int>*: Eine Map, die einem Resultat den ermittelten, endgültigen Wert zuordnet. So muss bereits geleistete Arbeit nicht wiederholt werden und die Laufzeit verkürzt werden
`target` *String*: Draht, dessen Wert berechnet werden soll

Wenn das `target` eine Zahl ist, wird diese direkt zurückgegeben. Wenn `target` noch nicht in `results` enthalten ist, dann wird es ermittelt. Anhand der Anweisung werden die Operanden durch einen rekursiven Aufruf von `calculate()` ermittelt und die entsprechende Operation anhand der Tabelle durchgeführt. Das Ergebnis wird dann in `results` eingetragen.

Am Ende wird der Eintrag aus `results` zurückgegeben.

## Teil 1

Die Zeilen der Eingabe werden zuerst in Relationen umgewandelt und die `NOP` Relationen ausgefiltert. Diese Liste wird als `relations` Parameter an `calculate()` übergeben. `results` ist eine leere Map und das `target` ist `a`.

## Teil 2

Wie in Teil 1 wird die Eingabe umgeformt und an `calculate` übergeben. Für `results` wird eine Map übergeben, die bereits dem Draht `b` den aus Teil 1 ermittelten Wert für `a` zuordnet.