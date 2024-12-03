package aoc2024

import checkValue
import readInput

fun main() {
    val (year, day) = "2024" to "Day03"

    fun List<String>.test() = this.sumOf { it.toInt() }

    fun part1(input: List<String>): Int = processMulCommands(input)

    fun part2(input: List<String>): Int = processMulCommands(input, partTwo = true)

    val testInput1 = readInput(name = "${day}_test1", year = year)
    val testInput2 = readInput(name = "${day}_test2", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput1), 161)
    println(part1(input))

    checkValue(part2(testInput2), 48)
    println(part2(input))
}

private val COMMAND_REGEX = Regex("""mul\((\d{1,3}),\s*(\d{1,3})\)|don't\(\)|do\(\)""")

private fun processMulCommands(lines: List<String>, partTwo: Boolean = false): Int {
    return lines.joinToString(" ")
        .let { COMMAND_REGEX.findAll(it) }
        .fold(Pair(0, true)) { (sum, enabled), match ->
            when {
                match.value.startsWith("mul") && enabled -> {
                    val a = match.groupValues[1].toInt()
                    val b = match.groupValues[2].toInt()
                    Pair(sum + a * b, enabled)
                }

                partTwo -> {
                    val newEnabled = when (match.value) {
                        ENABLE_COMMAND -> true
                        DISABLE_COMMAND -> false
                        else -> enabled
                    }
                    Pair(sum, newEnabled)
                }

                else -> Pair(sum, enabled)
            }
        }
        .first
}

private const val DISABLE_COMMAND = "don't()"
private const val ENABLE_COMMAND = "do()"