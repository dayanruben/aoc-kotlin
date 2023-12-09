package aoc2023

import checkValue
import readInput

fun main() {
    val (year, day) = "2023" to "Day09"

    fun extrapolate(seq: List<Long>, backguard: Boolean): Long {
        var value = 0L
        var factor = 1
        var current = seq
        while (current.any { it != 0L }) {
            if (backguard) {
                value += current.first() * factor
                factor *= -1
            } else {
                value += current.last()
            }
            current = current.windowed(2).map { (a, b) -> b - a }
        }
        return value
    }

    fun extrapolateSum(input: List<String>, backguard: Boolean) = input.map { line ->
        line.split("\\s+".toRegex()).map { it.toLong() }
    }.sumOf {
        extrapolate(it, backguard)
    }

    fun part1(input: List<String>) = extrapolateSum(input, false)

    fun part2(input: List<String>) = extrapolateSum(input, true)

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput), 114)
    println(part1(input))

    checkValue(part2(testInput), 2)
    println(part2(input))
}