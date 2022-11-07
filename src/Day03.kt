fun main() {
    val movements = mapOf(
        '>' to Point2D(1,0),
        '<' to Point2D(-1,0),
        '^' to Point2D(0,1),
        'v' to Point2D(0,-1)
    )

    fun part1(input: List<String>): Int {
        var position = Point2D(0,0)

        val visitedHouses = mutableSetOf<Point2D>(position)

        input[0].forEach {
            position += movements[it]!!
            visitedHouses.add(position)
        }

        return visitedHouses.size
    }

    fun part2(input: List<String>): Int {
        var positionSanta = Point2D(0,0)
        var positionRobotSanta = Point2D(0,0)
        val visitedHouses = mutableSetOf<Point2D>(positionSanta)

        input[0].forEachIndexed { index, c ->
            when (index % 2) {
                0 -> {
                    positionSanta += movements[c]!!
                    visitedHouses.add(positionSanta)
                }
                1 -> {
                    positionRobotSanta += movements[c]!!
                    visitedHouses.add(positionRobotSanta)
                }
            }
        }

        return visitedHouses.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    //check(part1(testInput) == 4)
    check(part2(testInput) == 3)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
