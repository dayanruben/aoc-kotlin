package aoc2024

import checkValue
import readInput

fun main() {
    val (year, day) = "2024" to "Day01"

    fun List<String>.test() = this.sumOf { line -> line }

    fun part1(input: List<String>) = input.test()

    fun part2(input: List<String>) = input.test()

    val testInput1 = readInput(name = "${day}_p1_test", year = year)
    val testInput2 = readInput(name = "${day}_p2_test", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput1), 0)
    println(part1(input))

    checkValue(part2(testInput2), 0)
    println(part2(input))
}