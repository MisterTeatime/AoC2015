fun main() {
    val vowels = listOf('a', 'e', 'i', 'o', 'u')
    val forbidden = listOf("ab", "cd", "pq", "xy")

    fun part1(input: List<String>): Int {
        return input
            .filterNot { s -> s.windowed(2).any { forbidden.contains(it) }  } //Filter "Verbotene Zeichenketten"
            .filter { s -> s.count { vowels.contains(it) } >= 3 } //Filter "3 Vokale"
            .filter { s -> s.windowed(2).any { it.take(1) == it.drop(1) } }//Filter "Doppelung"
            .size
    }

    fun part2(input: List<String>): Int {
        val repeatRegex = Regex("""(..).*\1""")
        val spacedRegex = Regex("""(.).\1""")

        return input
            .filter { repeatRegex.containsMatchIn(it) } //Filter "Paarwiederholung"
            .filter { spacedRegex.containsMatchIn(it) } //Filter "Dopplung mit Abstand"
            .size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 2)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
