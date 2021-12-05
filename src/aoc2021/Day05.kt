package aoc2021

import readInput
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max

fun main() {
    val (year, day) = "2021" to "Day05"

    data class Point(val x: Int, val y: Int)
    data class Line(val p1: Point, val p2: Point)

    data class Info(val lines: List<Line>, val maxX: Int, val maxY: Int)

    fun parseInput(input: List<String>): Info {
        var maxX = 0
        var maxY = 0
        val lines = input.map { line ->
            val (p1, p2) = line.split("->").map { point ->
                val (x, y) = point.trim().split(",").map { it.toInt() }
                maxX = maxOf(maxX, x)
                maxY = maxOf(maxY, y)
                Point(x, y)
            }

            Line(p1, p2)
        }
        return Info(lines, maxX, maxY)
    }

    fun countIntersections(inputInfo: Info, countDiagonals: Boolean): Int {
        val grid = Array(inputInfo.maxY + 1) { Array(inputInfo.maxX + 1) { 0 } }

        var intersections = 0
        inputInfo.lines.filter { line ->
            line.p1.x == line.p2.x || line.p1.y == line.p2.y ||
            (countDiagonals && abs(line.p1.x - line.p2.x) == abs(line.p1.y - line.p2.y))
        }.forEach { line ->
            val (p1, p2) = line
            val (x1, y1) = p1
            val (x2, y2) = p2

            when {
                x1 == x2 -> {
                    for (y in min(y1, y2)..max(y1, y2)) {
                        if (++grid[y][x1] == 2) {
                            intersections++
                        }
                    }
                }
                y1 == y2 -> {
                    for (x in min(x1, x2)..max(x1, x2)) {
                        if (++grid[y1][x] == 2) {
                            intersections++
                        }
                    }
                }
                else -> {
                    if (countDiagonals) {
                        val d = abs(x1 - x2)
                        val dx = (x2 - x1) / d
                        val dy = (y2 - y1) / d
                        for (i in 0..d) {
                            if (++grid[y1 + dy * i][x1 + dx * i] == 2) {
                                intersections++
                            }
                        }
                    }
                }
            }
        }

        return intersections
    }

    fun part1(input: List<String>): Int {
        val inputInfo = parseInput(input)
        return countIntersections(inputInfo, countDiagonals = false)
    }

    fun part2(input: List<String>): Int {
        val inputInfo = parseInput(input)
        return countIntersections(inputInfo, countDiagonals = true)
    }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 5)
    println(part1(input))

    check(part2(testInput) == 12)
    println(part2(input))
}