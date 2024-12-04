package aoc2024

import checkValue
import readInput

fun main() {
    val (year, day) = "2024" to "Day04"

    fun List<String>.test() = this.sumOf { it.toInt() }

    fun part1(input: List<String>): Int = countXmas(parseInput(input))

    fun part2(input: List<String>): Int = countMAS(parseInput(input))

    val testInput1 = readInput(name = "${day}_test1", year = year)
    val testInput2 = readInput(name = "${day}_test2", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput1), 18)
    println(part1(input))

    checkValue(part2(testInput2), 9)
    println(part2(input))
}

private fun parseInput(input: List<String>) = input.map {
    it.toCharArray()
}

private val directions = listOf(
    Pair(1, 0),
    Pair(-1, 0),
    Pair(0, 1),
    Pair(0, -1),
    Pair(1, 1),
    Pair(1, -1),
    Pair(-1, 1),
    Pair(-1, -1),
)

private fun countXmas(grid: List<CharArray>): Int =
    grid.withIndex().sumOf { (y, row) ->
        row.withIndex().sumOf { (x, c) ->
            if (c != 'X') 0 else getXmasSequences(grid, x, y).count { it == "XMAS" }
        }
    }

private fun getXmasSequences(grid: List<CharArray>, x: Int, y: Int): List<String> =
    directions.mapNotNull { (dx, dy) ->
        val sequence = (0 until 4).mapNotNull { i ->
            val newX = x + dx * i
            val newY = y + dy * i
            grid.getOrNull(newY)?.getOrNull(newX)
        }.joinToString("")

        if (sequence.length == 4) sequence else null
    }

private fun countMAS(grid: List<CharArray>): Int =
    grid.withIndex().sumOf { (y, row) ->
        row.withIndex().count { (x, c) -> c == 'A' && isMAS(grid, x, y) }
    }

private fun isMAS(grid: List<CharArray>, x: Int, y: Int): Boolean {
    val adjacentPositions = listOf(
        Pair(x - 1, y - 1),
        Pair(x + 1, y - 1),
        Pair(x - 1, y + 1),
        Pair(x + 1, y + 1),
    )

    val neighbors = adjacentPositions.mapNotNull { (nx, ny) ->
        grid.getOrNull(ny)?.getOrNull(nx)
    }

    if (neighbors.size != 4) return false

    val uniqueChars = neighbors.toSet()
    if (uniqueChars.size != 2 || !uniqueChars.containsAll(listOf('M', 'S'))) return false

    return (neighbors[0] != neighbors[3]) && (neighbors[1] != neighbors[2])
}