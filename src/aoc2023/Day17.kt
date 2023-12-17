package aoc2023

import checkValue
import readInput
import java.util.*

fun main() {
    val (year, day) = "2023" to "Day17"

    fun minHeatLoss(map: List<String>, minSteps: Int, maxSteps: Int): Int {
        val input = map.map { line -> line.map { it.digitToInt() } }
        val rows = input.size
        val cols = input.first().size
        val hitLossForPos = Array(rows) { row ->
            IntArray(cols) { col ->
                if (row == 0 && col == 0) 0 else Int.MAX_VALUE
            }
        }
        val visitedState = mutableSetOf<MoveState>()
        val stateQueue = PriorityQueue<MoveState>()
        stateQueue.add(MoveState())

        while (stateQueue.isNotEmpty()) {
            val state = stateQueue.remove()
            if (state in visitedState) continue
            visitedState.add(state)

            val (row, col, avoidDir) = state
            for (dir in listOf(Right, Left, Up, Down)) {
                var (moveRow, moveCol) = row to col
                var hitLoss = state.hitLoss
                if (!(row == 0 && col == 0 || dir != avoidDir && dir != avoidDir.inv())) continue

                for (move in 1..maxSteps) {
                    moveRow += dir.dr
                    moveCol += dir.dc
                    if (moveRow !in 0..<rows || moveCol !in 0..<cols) continue
                    hitLoss += input[moveRow][moveCol]
                    if (move !in minSteps..maxSteps) continue
                    hitLossForPos[moveRow][moveCol] = minOf(hitLoss, hitLossForPos[moveRow][moveCol])
                    val movedState = MoveState(moveRow, moveCol, dir).apply {
                        this.hitLoss = hitLoss
                    }
                    stateQueue.add(movedState)
                }
            }
        }
        return hitLossForPos.last().last()
    }

    fun part1(input: List<String>) = minHeatLoss(map = input, minSteps = 1, maxSteps = 3)

    fun part2(input: List<String>) = minHeatLoss(map = input, minSteps = 4, maxSteps = 10)

    val testInput1 = readInput(name = "${day}_test1", year = year)
    val testInput2 = readInput(name = "${day}_test2", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput1), 102)
    println(part1(input))

    checkValue(part2(testInput1), 94)
    checkValue(part2(testInput2), 71)
    println(part2(input))
}

data class MoveState(
        val row: Int = 0,
        val col: Int = 0,
        val avoidDir: MoveDirection = Up,
) : Comparable<MoveState> {
    var hitLoss: Int = 0
    override fun compareTo(other: MoveState) = hitLoss.compareTo(other.hitLoss)
}

sealed class MoveDirection(val dr: Int, val dc: Int) {
    fun inv() = when (this) {
        Down -> Up
        Left -> Right
        Right -> Left
        Up -> Down
    }
}

data object Right : MoveDirection(0, 1)
data object Left : MoveDirection(0, -1)
data object Up : MoveDirection(-1, 0)
data object Down : MoveDirection(1, 0)