fun main() {
    fun instructions2program(input: List<String>): List<Command> {
        val regex = """(\D+) (\d+,\d+) through (\d+,\d+)""".toRegex()

        return input
            .map { regex.find(it)!!.groupValues.drop(1) }
            .map { (instruction, start, end) -> Command(instruction, start, end) }
            .filterNot { it.instruction == Inst.NOP }
    }

    fun getAllLights(startPoint: Point2D, endPoint: Point2D) = IntRange(startPoint.x, endPoint.x).flatMap { x ->
        IntRange(startPoint.y, endPoint.y).map { y ->
            Point2D(x, y)
        }
    }.toSet()

    fun evolve(input: List<String>, dimX: Int, dimY: Int, on: (_: Int) -> Int, off: (_: Int) -> Int, toggle: (_: Int) -> Int): IntArray {
        val regex = """(\D+) (\d+,\d+) through (\d+,\d+)""".toRegex()
        val program = input
            .map { regex.find(it)!!.groupValues.drop(1) }
            .map { (instruction, start, end) -> Command(instruction, start, end) }
            .filterNot { it.instruction == Inst.NOP }

        val lights = IntArray(dimX * dimY)

        program.forEach { cmd ->
            IntRange(cmd.startPoint.x, cmd.endPoint.x).forEach { x ->
                IntRange(cmd.startPoint.y, cmd.endPoint.y).forEach { y ->
                    val index = dimX * x + y
                    lights[index] = when (cmd.instruction) {
                        Inst.ON -> on(lights[index])
                        Inst.OFF -> off(lights[index])
                        Inst.TOGGLE -> toggle(lights[index])
                        else -> lights[index]
                    }
                }
            }
        }

        return lights
    }

    fun part1FirstTry(input: List<String>): Int {

        //Eingabe in Instructions umwandeln
        val program = instructions2program(input)

        //Programm abarbeiten
        val activeLights = mutableSetOf<Point2D>()

        program.forEach { i ->
            when (i.instruction) {
                Inst.ON -> activeLights.addAll(getAllLights(i.startPoint, i.endPoint))
                Inst.OFF -> activeLights.removeAll(getAllLights(i.startPoint, i.endPoint))
                Inst.TOGGLE -> {
                    val splitLights = getAllLights(i.startPoint, i.endPoint).partition { activeLights.contains(it) }
                    activeLights.removeAll(splitLights.first.toSet())
                    activeLights.addAll(splitLights.second.toSet())
                }
                Inst.NOP -> {}
            }
        }
        return activeLights.size
    }

    fun part1(input: List<String>, dimX: Int, dimY: Int) = evolve(input, dimX, dimY,
        { 1 }, //on
        { 0 }, //off
        { if (it == 0) 1 else 0}) //toggle
        .count { it == 1 }

    fun part2(input: List<String>, dimX: Int, dimY: Int) = evolve(input, dimX, dimY,
        { it + 1 }, //on
        { if (it == 0) 0 else it - 1 }, //off
        { it + 2 }) //toggle
        .sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    //check(part1FirstTry(testInput) == 16)
    check(part1(testInput, 6, 6) == 16)
    check(part2(testInput, 6, 6) == 92)

    val input = readInput("Day06")
    //println(part1FirstTry(input)
    println(part1(input, 1000, 1000))
    println(part2(input, 1000, 1000))
}

data class Command(val instructionIn: String, val start: String, val end: String) {
    val instruction: Inst = when (instructionIn) {
        "turn on" -> Inst.ON
        "turn off" -> Inst.OFF
        "toggle" -> Inst.TOGGLE
        else -> Inst.NOP
    }
    val startPoint: Point2D
    val endPoint: Point2D

    init {
        val startCoords = start.split(",").map { it.toInt() }
        val endCoords = end.split(",").map { it.toInt() }

        startPoint = Point2D(startCoords[0], startCoords[1])
        endPoint = Point2D(endCoords[0], endCoords[1])
    }




}

enum class Inst {
    ON, OFF, TOGGLE, NOP
}