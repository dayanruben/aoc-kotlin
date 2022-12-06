package aoc2022

import readInput

fun main() {
    val (year, day) = "2022" to "Day06"

    fun findMarker(input: List<String>, size: Int) =
        input.first().windowed(size).indexOfFirst { it.toSet().size == size } + size

    fun part1(input: List<String>) = findMarker(input, 4)

    fun part2(input: List<String>) = findMarker(input, 14)

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 7)
    println(part1(input))

    check(part2(testInput) == 19)
    println(part2(input))
}