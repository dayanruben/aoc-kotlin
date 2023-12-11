package aoc2023

import checkValue
import readInput
import kotlin.math.abs

fun main() {
    val (year, day) = "2023" to "Day11"

    fun sumPaths(input: List<String>, expansion: Int): Long {
        val galaxies = mutableListOf<Galaxy>()
        val galaxyRows = mutableSetOf<Int>()
        val galaxyCols = mutableSetOf<Int>()
        val rows = input.size
        val cols = input.first().length
        for ((row, line) in input.withIndex()) {
            for ((col, cell) in line.withIndex()) {
                if (cell == '#') {
                    galaxies += Galaxy(row, col)
                    galaxyRows += row
                    galaxyCols += col
                }
            }
        }

        val emptyRows = (0 until rows).filter { row -> row !in galaxyRows }
        val emptyCols = (0 until cols).filter { col -> col !in galaxyCols }
        for (galaxy in galaxies) {
            val expandedRows = (galaxy.row downTo 0).count { it in emptyRows } * (expansion - 1)
            galaxy.row += expandedRows
            val expandedCols = (galaxy.col downTo 0).count { it in emptyCols } * (expansion - 1)
            galaxy.col += expandedCols
        }

        return galaxies.allPairs().sumOf { (gal1, gal2) ->
            (abs(gal1.row - gal2.row) + abs(gal1.col - gal2.col)).toLong()
        }
    }

    fun part1(input: List<String>) = sumPaths(input, 2)

    fun part2(input: List<String>, expansion: Int = 1_000_000) = sumPaths(input, expansion)

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput), 374)
    println(part1(input))

    checkValue(part2(testInput, 10), 1030)
    checkValue(part2(testInput, 100), 8410)
    println(part2(input))
}

data class Galaxy(var row: Int, var col: Int)

fun <T> List<T>.allPairs(): List<Pair<T, T>> {
    val pairs = mutableListOf<Pair<T, T>>()
    for (i in 0..<this.size - 1) {
        for (j in i + 1..<this.size) {
            pairs.add(Pair(this[i], this[j]))
        }
    }
    return pairs
}