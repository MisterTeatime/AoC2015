fun main() {

    fun part1(input: List<String>): Int {
        return input.fold(0) { total, gift ->
            val (length, width, height) = gift.split("x").map { it.toInt() }
            val side1 = length * width
            val side2 = width * height
            val side3 = height * length

            total + 2 * side1 + 2 * side2 + 2 * side3 + minOf(side1, side2, side3)
        }
    }

    fun part2(input: List<String>): Int {
        return input.fold(0) { total, gift ->
            val (length, width, height) = gift.split("x").map { it.toInt() }.sorted()
            total + (length + length + width + width) + (length * width * height)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 48)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
