package aoc2021

import readInput

fun main() {
    val (year, day) = "2021" to "Day06"

    fun lanternfishsAfterDays(input: List<String>, days: Int): Long {
        val state = input.single().split(",").map { it.toInt() }

        val timers = LongArray(9)
        state.forEach { timers[it]++ }

        repeat(days) {
            val zeros = timers[0]
            (1..8).forEach { timers[it - 1] = timers[it] }
            timers[6] += zeros
            timers[8] = zeros
        }

        return timers.sum()
    }

    fun part1(input: List<String>) = lanternfishsAfterDays(input, 80)

    fun part2(input: List<String>) = lanternfishsAfterDays(input, 256)

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput).toInt() == 5934)
    println(part1(input))

    check(part2(testInput) == 26984457539)
    println(part2(input))
}