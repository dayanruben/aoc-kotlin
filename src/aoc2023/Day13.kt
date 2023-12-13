package aoc2023

import checkValue
import readInputText

fun main() {
    val (year, day) = "2023" to "Day13"

    fun parseInput(input: String): List<Pair<List<Int>, List<Int>>> {
        return input.split("\n\n").map { line ->
            val pattern = line.split("\n")
            fun List<Char>.toBinInt() = this.map { if (it == '#') '1' else '0' }.joinToString("").toInt(radix = 2)
            val rows = pattern.first().indices.map { colIndex -> pattern.map { it[colIndex] }.toBinInt() }
            val cols = pattern.map { row -> row.map { it }.toBinInt() }
            rows to cols
        }
    }

    fun countDiffBits(n1: Int, n2: Int) = (n1 xor n2).countOneBits()

    fun List<Int>.reflectionIndex(hasFix: Boolean) = indices.filter { it != this.size - 1 }.firstOrNull { index ->
        val interval = 0..minOf(index, this.size - 2 - index)
        if (!hasFix) {
            interval.all { this[index - it] == this[index + 1 + it] }
        } else {
            interval.sumOf { countDiffBits(this[index - it], this[index + 1 + it]) } == 1
        }
    } ?: -1

    fun sumNotes(input: String, hasFix: Boolean) = parseInput(input).sumOf { (rows, cols) ->
        maxOf(rows.reflectionIndex(hasFix) + 1, (cols.reflectionIndex(hasFix) + 1) * 100)
    }

    fun part1(input: String) = sumNotes(input, hasFix = false)

    fun part2(input: String) = sumNotes(input, hasFix = true)

    val testInput = readInputText(name = "${day}_test", year = year)
    val input = readInputText(name = day, year = year)

    checkValue(part1(testInput), 405)
    println(part1(input))

    checkValue(part2(testInput), 400)
    println(part2(input))
}