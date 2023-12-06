package aoc2023

import checkValue
import readInput

fun main() {
    val (year, day) = "2023" to "Day06"

    fun countWays(times: List<Long>, distances: List<Long>) =
            times.mapIndexed { index, time ->
                (1L until time).map {
                    it * (time - it)
                }.count {
                    it > distances[index]
                }.toLong()
            }.reduce { acc, i -> acc * i }

    fun part1(input: List<String>): Long {
        val (times, distances) = input.map { line ->
            line.split("\\s+".toRegex()).drop(1).map { it.toLong() }
        }
        return countWays(times, distances)
    }

    fun part2(input: List<String>): Long {
        val (times, distances) = input.map { line ->
            listOf(line.split(":").last().replace("\\s+".toRegex(), "").toLong())
        }
        return countWays(times, distances)
    }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput), 288)
    println(part1(input))

    checkValue(part2(testInput), 71503)
    println(part2(input))
}