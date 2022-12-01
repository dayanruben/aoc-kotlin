package aoc2022

import readInput

fun main() {
    val (year, day) = "2022" to "Day01"

    fun sumTopCalories(input: List<String>, top: Int): Int {
        var acc = 0
        val cals = mutableListOf<Int>()
        input.forEach {
            if (it.isEmpty()) {
                cals.add(acc)
                acc = 0
            }
            else {
                acc += it.toInt()
            }
        }
        cals.add(acc)
        return cals.sortedDescending().take(top).sum()
    }

    fun part1(input: List<String>) =
        sumTopCalories(input, 1)

    fun part2(input: List<String>) =
        sumTopCalories(input, 3)

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 24000)
    println(part1(input))

    check(part2(testInput) == 45000)
    println(part2(input))
}