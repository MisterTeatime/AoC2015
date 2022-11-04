
fun main() {
    fun part1(input: List<String>): Int {
        return input[0].count{"(".contains(it) } - input[0].count { ")".contains(it) }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

