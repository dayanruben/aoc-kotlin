package aoc2024

import checkValue
import readInput

fun main() {
    val (year, day) = "2024" to "Day02"

    fun List<String>.test() = this.sumOf { it.toInt() }

    fun part1(input: List<String>): Int = parseInput(input).count { it.isSafe() }

    fun part2(input: List<String>): Int = parseInput(input).count { it.isDampened() }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput), 2)
    println(part1(input))

    checkValue(part2(testInput), 4)
    println(part2(input))
}

private fun parseInput(input: List<String>): List<List<Int>> =
    input.map { line -> line.split("\\s+".toRegex()).map { it.toInt() } }

private fun List<Int>.isSafe(): Boolean {
    if (size < 2) return true

    val increasing = this[0] < this[1]
    return zipWithNext().all { (prev, current) ->
        val diff = current - prev
        if (increasing) {
            diff in 1..3
        } else {
            diff < 0 && diff >= -3
        }
    }
}

private fun List<Int>.isDampened(): Boolean =
    isSafe() || indices.any { index ->
        filterIndexed { i, _ -> i != index }.isSafe()
    }


