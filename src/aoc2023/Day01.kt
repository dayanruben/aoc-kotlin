package aoc2023

import readInput
import readInputText

fun main() {
    val (year, day) = "2023" to "Day01"

    fun sumTopCalories(input: String, top: Int): Int {
        val cals = input.split("\n\n").map { it.lines().sumOf { cal -> cal.toInt() } }
        return cals.sortedDescending().take(top).sum()
    }

    fun part1(input: String) =
        sumTopCalories(input, 1)

    fun part2(input: String) =
        sumTopCalories(input, 3)

    val testInput = readInputText(name = "${day}_test", year = year)
    val input = readInputText(name = day, year = year)

    check(part1(testInput) == 24000)
    println(part1(input))

    check(part2(testInput) == 45000)
    println(part2(input))
}