package aoc2021

import readInput
import java.util.*

fun main() {
    val (year, day) = "2021" to "Day10"

    fun List<String>.toScores(corrupted: Boolean) = map { line ->
        val stack = LinkedList<Char>()
        line.forEach { c ->
            when (c) {
                ')' -> {
                    val match = stack.pop()
                    if (match != '(') {
                        return@map if (corrupted) 3 else 0
                    }
                }
                ']' -> {
                    val match = stack.pop()
                    if (match != '[') {
                        return@map if (corrupted) 57 else 0
                    }
                }
                '}' -> {
                    val match = stack.pop()
                    if (match != '{') {
                        return@map if (corrupted) 1197 else 0
                    }
                }
                '>' -> {
                    val match = stack.pop()
                    if (match != '<') {
                        return@map if (corrupted) 25137 else 0
                    }
                }
                else -> {
                    stack.push(c)
                }
            }
        }

        return@map if (corrupted) 0 else stack.map {
            when (it) {
                '(' -> 1L
                '[' -> 2L
                '{' -> 3L
                else -> 4L
            }
        }.reduce { acc, i -> acc * 5L + i }
    }

    fun part1(input: List<String>) =
        input.toScores(corrupted = true).sum()

    fun part2(input: List<String>): Long {
        val scores = input.toScores(corrupted = false)
            .filter { it != 0L }
            .sorted()
        return scores[scores.size / 2]
    }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 26397L)
    println(part1(input))

    check(part2(testInput) == 288957L)
    println(part2(input))
}