package aoc2021

import readInput

fun main() {
    val (year, day) = "2021" to "Day01"

    fun part1(input: List<Int>) =
        countIncreased(input)

    fun part2(input: List<Int>) =
        countIncreased(input.windowed(size = 3, step = 1).map { it.sum() })

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(name = day, year = year)
    check(testInput.size == 2000)

    val input = readInput(name = day, year = year).map { it.toInt() }
    println(part1(input))
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