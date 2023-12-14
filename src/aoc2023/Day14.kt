package aoc2023

import checkValue
import readInput

fun main() {
    val (year, day) = "2023" to "Day14"

    fun loadSum(input: List<String>, maxCycles: Int): Int {
        var state = PlatformState(input.map { it.toCharArray() }.toTypedArray())

        var totalLoad: Int? = null
        when (maxCycles) {
            0 -> state = state.tilt()

            else -> {
                val cycleForState = mutableMapOf(state to 0)
                val stateForCycle = mutableMapOf(0 to state)
                val loadForState = mutableMapOf(state to 0)
                for (currentCycle in 1..maxCycles) {
                    repeat(4) {
                        state = state.tilt().rotate()
                    }
                    if (state in cycleForState) {
                        val repeatedCycle = cycleForState.getValue(state)
                        val finalCycle = repeatedCycle - 1 + (maxCycles - currentCycle + 1) % (currentCycle - repeatedCycle)
                        val finalState = stateForCycle.getValue(finalCycle)
                        totalLoad = finalState.totalLoad()
                        break
                    } else {
                        cycleForState[state] = currentCycle
                        stateForCycle[currentCycle] = state
                        loadForState[state] = state.totalLoad()
                    }
                }
            }
        }

        return totalLoad ?: state.totalLoad()
    }

    fun part1(input: List<String>) = loadSum(input, maxCycles = 0)

    fun part2(input: List<String>) = loadSum(input, maxCycles = 1_000_000_000)

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput), 136)
    println(part1(input))

    checkValue(part2(testInput), 64)
    println(part2(input))
}

data class PlatformState(val state: Array<CharArray>) {
    fun tilt(): PlatformState {
        val state = this.state
        val numRows = state.size
        val numCols = state[0].size
        val cols = state.first().indices.map { colIndex ->
            state.mapIndexed { rowIndex, row ->
                row[colIndex] to rowIndex
            }.filter {
                it.first != '.'
            }
        }
        val newState = Array(numRows) { CharArray(numCols) { '.' } }
        cols.forEachIndexed { colIndex, col ->
            var row = 0
            for ((cell, rowIndex) in col) {
                when (cell) {
                    '#' -> {
                        newState[rowIndex][colIndex] = '#'
                        row = rowIndex + 1
                    }

                    else -> {
                        newState[row][colIndex] = 'O'
                        row++
                    }
                }
            }
        }
        return PlatformState(newState)
    }

    fun rotate(): PlatformState {
        val state = this.state
        val numRows = state.size
        val numCols = state[0].size
        val newState = Array(numCols) { CharArray(numRows) }
        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                newState[col][numRows - 1 - row] = state[row][col]
            }
        }
        return PlatformState(newState)
    }

    fun totalLoad(): Int = state.mapIndexed { index, row ->
        row.count { it == 'O' } * (state.size - index)
    }.sum()

    override fun equals(other: Any?): Boolean = (other as? PlatformState)?.state.contentDeepEquals(state)
    override fun hashCode(): Int = state.contentDeepHashCode()
    override fun toString() = state.map { it.joinToString("") }.joinToString("\n")
}