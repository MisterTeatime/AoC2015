fun main() {
    fun part1(input: List<String>): Int {
        return calculate(input.map { Relation.create(it) }.filterNot { it.Command == Mnemonic.NOP }, mutableMapOf(), "a")
    }

    fun part2(input: List<String>): Int {
        return calculate(input.map { Relation.create(it) }.filterNot { it.Command == Mnemonic.NOP }, mutableMapOf("b" to part1(input)), "a")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 72) //Wert an a
    check(part2(testInput) == 1)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))

    println("Ende")
}

fun calculate(relations: List<Relation>, results: MutableMap<String, Int>, target: String): Int {
    if (target matches """\d+""".toRegex())
        return target.toInt()

    if (target !in results) {
        val relation = relations.find { it.Result == target }!!
        results[target] = when (relation.Command) {
            Mnemonic.ASSIGN -> calculate(relations, results, relation.Operand1)
            Mnemonic.AND -> calculate(relations, results, relation.Operand1) and calculate(relations, results, relation.Operand2)
            Mnemonic.OR -> calculate(relations, results, relation.Operand1) or calculate(relations, results, relation.Operand2)
            Mnemonic.NOT -> calculate(relations, results, relation.Operand1).toUShort().inv().toInt()
            Mnemonic.RSHIFT -> calculate(relations, results, relation.Operand1) shr (calculate(relations, results, relation.Operand2))
            Mnemonic.LSHIFT -> calculate(relations, results, relation.Operand1) shl (calculate(relations, results, relation.Operand2))
            else -> error("unbekanntes Kommando")
        }
    }

    return results[target]!!
}

data class Relation (
    val Command: Mnemonic,
    val Result: String,
    val Operand1: String,
    val Operand2: String = ""
        ) {
    companion object {
        private val regexAssign = """^([a-z]+|[0-9]+) -> ([a-z]+)$""".toRegex()
        private val regexNot = """NOT ([a-z]+|[0-9]+) -> ([a-z]+)""".toRegex()
        private val regexAnd = """([a-z]+|[0-9]+) AND ([a-z]+|[0-9]+) -> ([a-z]+)""".toRegex()
        private val regexOr = """([a-z]+|[0-9]+) OR ([a-z]+|[0-9]+) -> ([a-z]+)""".toRegex()
        private val regexRShift = """([a-z]+|[0-9]+) RSHIFT ([a-z]+|[0-9]+) -> ([a-z]+)""".toRegex()
        private val regexLShift = """([a-z]+|[0-9]+) LSHIFT ([a-z]+|[0-9]+) -> ([a-z]+)""".toRegex()

        fun create(input: String) =
            when {
                input matches regexAssign -> {
                    val (op1, res) = regexAssign.find(input)!!.destructured
                    Relation(Mnemonic.ASSIGN, res, op1)
                }

                input matches regexNot -> {
                    val (op1, res) = regexNot.find(input)!!.destructured
                    Relation(Mnemonic.NOT, res, op1)
                }

                input matches regexAnd -> {
                    val (op1, op2, res) = regexAnd.find(input)!!.destructured
                    Relation(Mnemonic.AND, res, op1, op2)
                }

                input matches regexOr -> {
                    val (op1, op2, res) = regexOr.find(input)!!.destructured
                    Relation(Mnemonic.OR, res, op1, op2)
                }

                input matches regexRShift -> {
                    val (op1, op2, res) = regexRShift.find(input)!!.destructured
                    Relation(Mnemonic.RSHIFT, res, op1, op2)
                }

                input matches regexLShift -> {
                    val (op1, op2, res) = regexLShift.find(input)!!.destructured
                    Relation(Mnemonic.LSHIFT, res, op1, op2)
                }

                else -> Relation(Mnemonic.NOP, "","","")
            }
    }
}

enum class Mnemonic {
    ASSIGN, AND, OR, NOT, RSHIFT, LSHIFT, NOP
}
