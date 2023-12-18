package aoc2023

import checkValue
import readInput
import kotlin.math.abs

fun main() {
    val (year, day) = "2023" to "Day18"

    fun parsePlan(line: String, withHex: Boolean): Pair<Pair<Int, Int>, Long> {
        val dir: Int
        val distance: Long
        if (withHex) {
            val (_, _, hexStr) = line.split(" ")
            val hex = hexStr.substring(2, hexStr.length - 1)
            dir = hex.last().toString().toInt(radix = 16)
            distance = hex.substring(0..4).toLong(radix = 16)
        } else {
            val (dirStr, distanceStr, _) = line.split(" ")
            dir = "RDLU".indexOf(dirStr)
            distance = distanceStr.toLong()
        }
        val dx = when (dir) {
            0 -> 0 to 1
            1 -> 1 to 0
            2 -> 0 to -1
            else -> -1 to 0
        }
        return dx to distance
    }

    fun polygonArea(holes: List<Hole>): Double {
        var area = 0.0
        var ref = holes.size - 1
        for ((index, hole) in holes.withIndex()) {
            area += (holes[ref].row + hole.row) * (holes[ref].col - hole.col)
            ref = index
        }
        return abs(area / 2.0)
    }

    fun interiorPoints(holes: List<Hole>): Long {
        val area = polygonArea(holes)
        return (area - holes.size / 2.0 + 1).toLong()
    }

    fun cubicMeters(input: List<String>, withHex: Boolean): Long {
        val borderHoles = mutableListOf<Hole>()
        var currentHole = Hole(0, 0)
        var minRow = Long.MAX_VALUE
        var minCol = Long.MAX_VALUE
        var maxRow = Long.MIN_VALUE
        var maxCol = Long.MIN_VALUE
        input.forEach { line ->
            val (dx, distance) = parsePlan(line, withHex)
            for (i in 1..distance) {
                currentHole = currentHole.move(dx.first, dx.second)
                borderHoles.add(currentHole)
                minRow = minOf(minRow, currentHole.row)
                maxRow = maxOf(maxRow, currentHole.row)
                minCol = minOf(minCol, currentHole.col)
                maxCol = maxOf(maxCol, currentHole.col)
            }
        }
        val interiorPoints = interiorPoints(borderHoles)
        return interiorPoints + borderHoles.size
    }

    fun part1(input: List<String>) = cubicMeters(input, withHex = false)

    fun part2(input: List<String>) = cubicMeters(input, withHex = true)

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput), 62)
    println(part1(input))

    checkValue(part2(testInput), 952408144115)
    println(part2(input))
}

data class Hole(val row: Long, val col: Long) {
    fun move(dr: Int, dc: Int) = Hole(row + dr, col + dc)
}