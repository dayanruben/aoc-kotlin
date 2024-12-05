package aoc2024

import checkValue
import readInput
import utils.indexOfOrNull
import utils.toPair

fun main() {
    val (year, day) = "2024" to "Day05"

    fun part1(rawInput: List<String>): Int {
        val input = parseInput(rawInput)
        return input.pages
            .filter { isOrderedByRules(input.orderRules, it) }
            .sumOf { it[it.size / 2] }
    }

    fun part2(rawInput: List<String>): Int {
        val input = parseInput(rawInput)
        return input.pages
            .filterNot { isOrderedByRules(input.orderRules, it) }
            .map { orderByRules(input.orderRules, it) }
            .sumOf { it[it.size / 2] }
    }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput), 143)
    println(part1(input))

    checkValue(part2(testInput), 123)
    println(part2(input))
}

private data class Data(
    val orderRules: List<Pair<Int, Int>>,
    val pages: List<List<Int>>,
)

private fun parseInput(lines: List<String>): Data {
    val orderRules = mutableListOf<Pair<Int, Int>>()
    val pages = mutableListOf<List<Int>>()

    for (line in lines) {
        when {
            "|" in line -> {
                val (first, second) = line.split("|").map(String::toInt).toPair()
                orderRules += first to second
            }

            "," in line -> {
                pages += line.split(",").map(String::toInt)
            }
        }
    }

    return Data(orderRules, pages)
}

private fun isOrderedByRules(orderRules: List<Pair<Int, Int>>, page: List<Int>): Boolean {
    for ((firstValue, secondValue) in orderRules) {
        val firstIndex = page.indexOf(firstValue) ?: continue
        val secondIndex = page.indexOfOrNull(secondValue) ?: continue
        if (secondIndex < firstIndex) return false
    }
    return true
}

private fun orderByRules(orderRules: List<Pair<Int, Int>>, page: List<Int>): List<Int> {
    return page.sortedWith { a, b ->
        val rule = orderRules.find { it.first == a && it.second == b }
            ?: orderRules.find { it.first == b && it.second == a }
        when {
            rule == null -> 0
            a == rule.first -> -1
            else -> 1
        }
    }
}