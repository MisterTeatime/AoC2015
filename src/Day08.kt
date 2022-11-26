fun main() {
    fun part1(input: List<String>): Int {
        val regexEscapeSequences = """\\x[0-9a-fA-F]{2}|\\\\|\\\"""".toRegex()
        val codeLength = input.sumOf { it.length }

        val memoryLength = input
            .map { it.drop(1)} //Erstes Gänsefüßchen verwerfen
            .map { it.dropLast(1) } //Letztes Gänsefüßchen verwerfen
            .map { it.replace(regexEscapeSequences, "A")} //Escapesequenzen austauschen
            .sumOf { it.length } //Durchzählen

        return codeLength - memoryLength
    }

    fun part2(input: List<String>): Int {
        val codeLength = input.sumOf { it.length }

        val encodedCodeLength = input
            .map { it.replace("""\""", """\\""") } //Escape \
            .map { it.replace(""""""","""\"""") } // Escape "
            .map { s -> "\"$s\""}


        return encodedCodeLength.sumOf { it.length } - codeLength
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 12)
    check(part2(testInput) == 19)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
