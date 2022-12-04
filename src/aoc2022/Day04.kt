package aoc2022

import readInput

fun main() {
    val (year, day) = "2022" to "Day04"

    fun List<String>.parsedPairs() = this.map { line ->
        line.split(",").map { pairs ->
            val (a, b) = pairs.split("-").map { it.toInt() }
            a..b
        }
    }

    fun part1(input: List<String>) =
        input.parsedPairs().count { (p1, p2) ->
            (p1.first in p2 && p1.last in p2) || (p2.first in p1 && p2.last in p1)
        }

    fun part2(input: List<String>) =
        input.parsedPairs().count { (p1, p2) ->
            p1.first in p2 || p1.last in p2 || p2.first in p1 || p2.last in p1
        }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 2)
    println(part1(input))

    check(part2(testInput) == 4)
    println(part2(input))
}