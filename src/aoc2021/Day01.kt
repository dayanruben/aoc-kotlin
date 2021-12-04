package aoc2021

import readInput

fun main() {
    val (year, day) = "2021" to "Day01"

    fun part1(input: List<Int>) =
        countIncreased(input)

    fun part2(input: List<Int>) =
        countIncreased(input.windowed(size = 3, step = 1).map { it.sum() })

    val testInput = readInput(name = "${day}_test", year = year).map { it.toInt() }
    val input = readInput(name = day, year = year).map { it.toInt() }

    check(part1(testInput) == 7)
    println(part1(input))

    check(part2(testInput) == 5)
    println(part2(input))
}

fun countIncreased(input: List<Int>) = when {
    input.isEmpty() -> 0
    else -> {
        var pivot = input.first()
        input.drop(1).count {
            val increased = it > pivot
            pivot = it
            increased
        }
    }
}