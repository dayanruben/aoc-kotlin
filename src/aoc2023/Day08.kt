package aoc2023

import checkValue
import readInput

fun main() {
    val (year, day) = "2023" to "Day08"

    fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
    fun lcm(a: Long, b: Long): Long = (a * b) / gcd(a, b)
    fun LongArray.lcm() = this.reduce { acc, n -> lcm(acc, n) }

    fun parse(input: List<String>): Pair<String, Map<String, Pair<String, String>>> {
        val inst = input.first()
        val networkMap = mutableMapOf<String, Pair<String, String>>()
        input.drop(2).forEach { line ->
            val (node, lr) = line.split(" = ")
            val (left, right) = lr.drop(1).dropLast(1).split(", ")
            networkMap[node] = left to right
        }
        return inst to networkMap
    }

    fun part1(input: List<String>): Int {
        val (inst, networkMap) = parse(input)
        var steps = 0
        var current = "AAA"
        while (current != "ZZZ") {
            current = when (inst[steps % inst.length]) {
                'L' -> networkMap[current]?.first ?: ""
                else -> networkMap[current]?.second ?: ""
            }
            steps++
        }
        return steps
    }

    fun part2(input: List<String>): Long {
        val (inst, networkMap) = parse(input)
        var steps = 0
        var current = networkMap.keys.filter { it.last() == 'A' }
        val mins = LongArray(current.size) { -1 }
        while (mins.any { it < 0 }) {
            current = when (inst[steps % inst.length]) {
                'L' -> current.map { networkMap[it]?.first ?: "" }
                else -> current.map { networkMap[it]?.second ?: "" }
            }
            steps++
            current.forEachIndexed { index, node ->
                if (node.last() == 'Z' && mins[index] < 0) {
                    mins[index] = steps.toLong()
                }
            }
        }
        return mins.lcm()
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