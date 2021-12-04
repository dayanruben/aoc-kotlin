package aoc2021

import readInput

fun main() {
    val (year, day) = "2021" to "Day02"

    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0

        input.forEach {
            val (direction, value) = it.split(" ")
            val x = value.toInt()
            when (direction) {
                "forward" -> horizontal += x
                "down" -> depth += x
                "up" -> depth -= x
            }
        }

        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0

        input.forEach {
            val (direction, value) = it.split(" ")
            val x = value.toInt()
            when (direction) {
                "down" -> aim += x
                "up" -> aim -= x
                "forward" -> {
                    horizontal += x
                    depth += aim * x
                }
            }
        }

        return horizontal * depth
    }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 150)
    println(part1(input))

    check(part2(testInput) == 900)
    println(part2(input))
}