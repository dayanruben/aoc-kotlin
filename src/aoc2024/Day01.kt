package aoc2024

import checkValue
import readInput
import kotlin.math.abs

fun main() {
    val (year, day) = "2024" to "Day01"

    fun part1(input: List<String>): Int {
        val (left, right) = parseInput(input)
        return left.zip(right).sumOf { (l, r) -> abs(l - r) }
    }

    fun part2(input: List<String>): Int {
        val (left, right) = parseInput(input)
        val counts = right.groupingBy { it }.eachCount()
        return left.sumOf { it * (counts[it] ?: 0) }
    }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput), 11)
    println(part1(input))

    checkValue(part2(testInput), 31)
    println(part2(input))
}

private fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
    val left = mutableListOf<Int>()
    val right = mutableListOf<Int>()

    input.forEach { line ->
        val (l, r) = line.split("\\s+".toRegex()).map { it.toInt() }
        left.add(l)
        right.add(r)
    }

    left.sort()
    right.sort()

    return left to right
}