fun main() {
    fun part1(input: String): Int {
        var i = 0
        do {
            i++
            val md5 = (input + i.toString()).md5()
        } while (md5.take(5) != "00000")

        return i
    }

    fun part2(input: String): Int {
        var i = 0
        do {
            i++
            val md5 = (input + i.toString()).md5()
        } while (md5.take(6) != "000000")

        return i
    }

    // test if implementation meets criteria from the description, like:
    val testInput = "abcdef"
    check(part1(testInput) == 609043)

    val input = "yzbqklnj"
    println(part1(input))
    println(part2(input))
}
