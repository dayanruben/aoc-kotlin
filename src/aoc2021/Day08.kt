package aoc2021

import readInput

fun main() {
    val (year, day) = "2021" to "Day08"

    fun part1(input: List<String>) = input.map {
        it.split(" | ").last()
    }.flatMap {
        it.split(" ")
    }.count {
        it.length in listOf(2, 3, 4, 7)
    }

    fun part2(input: List<String>) = input.sumOf { line ->
        val (before, after) = line.split(" | ").map {
            it.split(" ").map { words -> words.toSet() }
        }
        val sizes = before.groupBy { it.size }
        val number = Array(10) { setOf<Char>() }
        number[1] = sizes[2]!!.first()
        number[4] = sizes[4]!!.first()
        number[7] = sizes[3]!!.first()
        number[8] = sizes[7]!!.first()
        number[6] = sizes[6]!!.first { number[8] == number[1] + it }
        number[5] = sizes[5]!!.first { number[8] != number[6] + it }
        number[2] = sizes[5]!!.first { number[8] == number[5] + it }
        number[9] = sizes[6]!!.first { number[8] != number[4] + it }
        number[0] = sizes[6]!!.first { number[8] == number[5] + it }
        number[3] = sizes[5]!!.first { it !in number }
        after.map { number.indexOf(it) }.joinToString("").toInt()
    }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 26)
    println(part1(input))

    check(part2(testInput) == 61229)
    println(part2(input))
}