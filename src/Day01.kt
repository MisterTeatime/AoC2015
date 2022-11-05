
fun main() {
    fun part1(input: List<String>): Int {
        return input[0].count{"(".contains(it) } - input[0].count { ")".contains(it) }
    }

    fun part2(input: List<String>): Int {
        var position = -1
        var underground = false;
        
        input[0].foldIndexed(0) { index, total, c ->
            if (total < 0 && !underground) {
                position = index
                underground = true
            }
            when (c){
                '(' -> total + 1
                ')' -> total - 1
                else -> total
            }
        }
        
        return position
        
        
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

