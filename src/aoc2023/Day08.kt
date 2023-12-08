package aoc2023

import checkValue
import readInput

fun main() {
    val (year, day) = "2023" to "Day08"

    fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
    fun lcm(a: Long, b: Long): Long = (a * b) / gcd(a, b)
    fun List<Long>.lcm() = this.reduce { acc, n -> lcm(acc, n) }

    fun parse(input: List<String>): Pair<String, Map<String, Pair<String, String>>> {
        val inst = input.first()
        val networkMap = input.drop(2).associate { line ->
            val (node, lr) = line.split(" = ")
            val (left, right) = lr.drop(1).dropLast(1).split(", ")
            node to (left to right)
        }
        return inst to networkMap
    }

    fun minSteps(inst: String, networkMap: Map<String, Pair<String, String>>, start: String, isEnd: (String) -> Boolean): Long {
        var steps = 0
        var current = start
        while (!isEnd(current)) {
            current = when (inst[steps % inst.length]) {
                'L' -> networkMap[current]?.first ?: ""
                else -> networkMap[current]?.second ?: ""
            }
            steps++
        }
        return steps.toLong()
    }

    fun part1(input: List<String>): Long {
        val (inst, networkMap) = parse(input)
        return minSteps(inst, networkMap, "AAA") { it == "ZZZ" }
    }

    fun part2(input: List<String>): Long {
        val (inst, networkMap) = parse(input)
        return networkMap.keys.filter { it.last() == 'A' }.map { start ->
            minSteps(inst, networkMap, start) { it.last() == 'Z' }
        }.lcm()
    }

    val testInput1 = readInput(name = "${day}_p1_test1", year = year)
    val testInput2 = readInput(name = "${day}_p1_test2", year = year)
    val testInput3 = readInput(name = "${day}_p2_test", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput1), 2)
    checkValue(part1(testInput2), 6)
    println(part1(input))

    checkValue(part2(testInput3), 6)
    println(part2(input))
}