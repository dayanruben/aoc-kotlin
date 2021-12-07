package aoc2021

import readInput
import kotlin.math.abs

fun main() {
    val (year, day) = "2021" to "Day07"

    fun minFuel(input: List<String>, constantRate: Boolean): Int {
        val positions = input.first().split(",").map { it.toInt() }

        val min = positions.minOrNull() ?: 0
        val max = positions.maxOrNull() ?: 0
        return (min..max).minOfOrNull { alignPosition ->
            positions.sumOf { position ->
                val n = abs(alignPosition - position)
                if (constantRate) n else n * (n + 1) / 2
            }
        } ?: 0
    }

    fun part1(input: List<String>) = minFuel(input, constantRate = true)

    fun part2(input: List<String>) = minFuel(input, constantRate = false)

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 37)
    println(part1(input))

    check(part2(testInput) == 168)
    println(part2(input))
}