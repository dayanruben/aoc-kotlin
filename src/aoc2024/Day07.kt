package aoc2024

import checkValue
import readInput

fun main() {
    val (year, day) = "2024" to "Day07"

    fun part1(input: List<String>): Long = calibrations(input)

    fun part2(input: List<String>): Long = calibrations(input, calculated = true)

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput), 3749)
    println(part1(input))

    checkValue(part2(testInput), 11387)
    println(part2(input))
}

private fun calibrations(input: List<String>, calculated: Boolean = false): Long =
    parseInput(input).filter { it.canBe(calculated) }.sumOf { it.testValue }

private data class Calibration(val testValue: Long, val numbers: List<Long>)

private fun parseInput(input: List<String>): List<Calibration> = input.map {
    val split = it.split(": ")
    Calibration(split[0].toLong(), split[1].split(" ").map(String::toLong))
}

private fun Calibration.canBe(canCombine: Boolean): Boolean =
    calculate(testValue, numbers, 1, numbers.first(), canCombine) != null

private fun calculate(testValue: Long, numbers: List<Long>, index: Int, total: Long, canCombine: Boolean): Long? {
    return when {
        total == testValue && index == numbers.size -> total
        index == numbers.size -> null
        else -> {
            val addition = calculate(testValue, numbers, index + 1, total + numbers[index], canCombine)
            if (addition != null) return addition

            val multiplication = calculate(testValue, numbers, index + 1, total * numbers[index], canCombine)
            if (multiplication != null) return multiplication

            if (canCombine) {
                val combined = "$total${numbers[index]}".toLong()
                val newNumbers = numbers.toMutableList()
                newNumbers.removeAt(index)
                newNumbers[index - 1] = combined
                val combination = calculate(testValue, newNumbers, index, combined, true)
                if (combination != null) return combination
            }

            null
        }
    }
}