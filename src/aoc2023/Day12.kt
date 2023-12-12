package aoc2023

import checkValue
import readInput

fun main() {
    val (year, day) = "2023" to "Day12"

    fun countArrangements(row: String, foldFactor: Int): Long {
        val (springsStr, groupsStr) = row.split(" ")
        val groupList = groupsStr.split(",").map { it.toInt() }

        val springs = (1..foldFactor).flatMap { listOf(springsStr) }.joinToString("?")
        val groups = (1..foldFactor).flatMap { groupList }
        val arrForState = mutableMapOf<CounterState, Long>()

        fun count(state: CounterState): Long {
            return if (state.springIndex >= springs.length) {
                when {
                    state.groupIndex >= groups.size || state.groupIndex == groups.size - 1 && state.damageCount == groups[state.groupIndex] -> 1
                    else -> 0
                }
            } else {
                when (state) {
                    in arrForState -> arrForState.getValue(state)
                    else -> {
                        var arr = 0L
                        when (springs[state.springIndex]) {
                            '?', '.' -> {
                                if (state.damageCount > 0 && groups[state.groupIndex] == state.damageCount) {
                                    arr += count(state.next(spring = true, group = true))
                                } else if (state.damageCount == 0) {
                                    arr += count(state.next(spring = true))
                                }
                            }
                        }
                        when (springs[state.springIndex]) {
                            '?', '#' -> {
                                if (state.groupIndex < groups.size && state.damageCount < groups[state.groupIndex]) {
                                    arr += count(state.next(spring = true, damage = true))
                                }
                            }
                        }
                        arrForState[state] = arr
                        arr
                    }
                }
            }
        }
        return count(CounterState())
    }

    fun sumArrangements(input: List<String>, unfolded: Boolean = false) =
            input.sumOf { countArrangements(row = it, foldFactor = if (unfolded) 5 else 1) }

    fun part1(input: List<String>) = sumArrangements(input, unfolded = false)

    fun part2(input: List<String>) = sumArrangements(input, unfolded = true)

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput), 21)
    println(part1(input))

    checkValue(part2(testInput), 525152)
    println(part2(input))
}

data class CounterState(val springIndex: Int = 0, val groupIndex: Int = 0, val damageCount: Int = 0) {
    fun next(spring: Boolean = false, group: Boolean = false, damage: Boolean = false) = CounterState(
            springIndex = springIndex + (if (spring) 1 else 0),
            groupIndex = groupIndex + (if (group) 1 else 0),
            damageCount = if (group) 0 else damageCount + (if (damage) 1 else 0),
    )
}