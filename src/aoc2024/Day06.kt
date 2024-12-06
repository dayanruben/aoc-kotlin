package aoc2024

import checkValue
import readInput

fun main() {
    val (year, day) = "2024" to "Day06"

    fun part1(input: List<String>): Int = countPositions(input)

    fun part2(input: List<String>): Int = countPositions(input, partTwo = true)

    val testInput1 = readInput(name = "${day}_test1", year = year)
    val testInput2 = readInput(name = "${day}_test2", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput1), 41)
    println(part1(input))

    checkValue(part2(testInput2), 6)
    println(part2(input))
}

private fun countPositions(input: List<String>, partTwo: Boolean = false): Int {
    if (input.isEmpty()) return 0

    val numRows = input.size
    val numCols = input[0].length
    val directions = listOf(
        '^' to (-1 to 0),
        '>' to (0 to 1),
        'v' to (1 to 0),
        '<' to (0 to -1),
    )
    val dirMap = directions.toMap()

    fun turnRight(dir: Char): Char = when (dir) {
        '^' -> '>'
        '>' -> 'v'
        'v' -> '<'
        '<' -> '^'
        else -> dir
    }

    val (startRow, startCol, startDir) = input.withIndex().flatMap { (r, row) ->
        row.withIndex().mapNotNull { (c, ch) ->
            if (ch in listOf('^', '>', 'v', '<')) Triple(r, c, ch) else null
        }
    }.first()

    if (!partTwo) {
        var guardRow = startRow
        var guardCol = startCol
        var guardDir = startDir
        val visited = mutableSetOf(guardRow to guardCol)

        while (true) {
            val (dr, dc) = dirMap[guardDir]!!
            val nextRow = guardRow + dr
            val nextCol = guardCol + dc
            if (nextRow !in 0 until numRows || nextCol !in 0 until numCols) break
            val nextCell = input[nextRow][nextCol]
            if (nextCell == '#') {
                guardDir = turnRight(guardDir)
            } else {
                guardRow = nextRow
                guardCol = nextCol
                visited.add(guardRow to guardCol)
            }
        }

        return visited.size
    } else {
        fun simulateWithObstacle(obstacleRow: Int, obstacleCol: Int): Boolean {
            val modifiedMap = input.map { it.toCharArray() }
            modifiedMap[obstacleRow][obstacleCol] = '#'

            var guardRow = startRow
            var guardCol = startCol
            var guardDir = startDir
            val seenStates = mutableSetOf<Triple<Int, Int, Char>>()

            while (true) {
                val state = Triple(guardRow, guardCol, guardDir)
                if (state in seenStates) return false
                seenStates.add(state)

                val (dr, dc) = dirMap[guardDir]!!
                val nextRow = guardRow + dr
                val nextCol = guardCol + dc
                if (nextRow !in 0 until numRows || nextCol !in 0 until numCols) return true
                val nextCell = modifiedMap[nextRow][nextCol]
                if (nextCell == '#') {
                    guardDir = turnRight(guardDir)
                } else {
                    guardRow = nextRow
                    guardCol = nextCol
                }
            }
        }

        var count = 0
        for (r in 0 until numRows) {
            for (c in 0 until numCols) {
                if (r == startRow && c == startCol) continue
                val cell = input[r][c]
                if (cell != '#') {
                    if (!simulateWithObstacle(r, c)) count++
                }
            }
        }

        return count
    }
}