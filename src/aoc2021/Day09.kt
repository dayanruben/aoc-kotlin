package aoc2021

import readInput

fun main() {
    val (year, day) = "2021" to "Day09"

    data class Point(val x: Int, val y: Int, val number: Int, var visited: Boolean = false) {
        val directions = arrayOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)

        fun adjacent(mx: Int, my: Int) =
            directions.mapNotNull { (dx, dy) ->
                if (x + dx < 0 || x + dx >= mx || y + dy < 0 || y + dy >= my) null
                else (x + dx) to (y + dy)
            }
    }

    fun List<String>.toPoints() = flatMapIndexed { x, row -> row.mapIndexed { y, col -> Point(x, y, col - '0') } }

    fun part1(input: List<String>): Int {
        val mx = input.size
        val my = input.first().length
        val points = input.toPoints()

        return points.sumOf { point ->
            val low = point.adjacent(mx, my).all { (ax, ay) ->
                points[ax * my + ay].number > point.number
            }
            if (low) point.number + 1 else 0
        }
    }

    fun part2(input: List<String>): Int {
        val mx = input.size
        val my = input.first().length
        val points = input.toPoints()

        fun dfsSum(x: Int, y: Int): Int = when {
            x < 0 || x >= mx || y < 0 || y >= my || points[x * my + y].visited || points[x * my + y].number == 9 -> 0
            else -> {
                points[x * my + y].visited = true
                1 + dfsSum(x + 1, y) + dfsSum(x - 1, y) + dfsSum(x, y + 1) + dfsSum(x, y - 1)
            }
        }

        val basinSums = points.mapNotNull { (x, y, number, visited) ->
            if (visited || number == 9) null
            else dfsSum(x, y)
        }

        return basinSums.sortedDescending().take(3).reduce { acc, i -> acc * i }
    }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 15)
    println(part1(input))

    check(part2(testInput) == 1134)
    println(part2(input))
}