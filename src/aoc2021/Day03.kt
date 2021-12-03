package aoc2021

import readInput

fun main() {
    val (year, day) = "2021" to "Day03"

    fun part1(input: List<String>): Int {
        val length = input.first().length

        val gamma = (0 until length).map {
            input.countBits(it)
        }.joinToString("") { (zeros, ones) ->
            if (zeros > ones) "0" else "1"
        }

        val epsilon = gamma.map {
            if (it == '0') "1" else "0"
        }.joinToString("")

        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun part2(input: List<String>): Int {
        val length = input.first().length

        var oxygen = input.map { it }
        var i = 0
        while (oxygen.size > 1) {
            val (zeros, ones) = oxygen.countBits(i)
            oxygen = oxygen.filter { it[i] == if (zeros > ones) '0' else '1' }
            i = (i + 1) % length
        }

        var co2 = input.map { it }
        i = 0
        while (co2.size > 1) {
            val (zeros, ones) = co2.countBits(i)
            co2 = co2.filter { it[i] == if (zeros <= ones) '0' else '1' }
            i = (i + 1) % length
        }

        return oxygen.first().toInt(2) * co2.first().toInt(2)
    }

    val input = readInput(name = day, year = year)
    println(part1(input))
    println(part2(input))
}

fun List<String>.countBits(index: Int): Pair<Int, Int> {
    var zeros = 0
    var ones = 0
    this.forEach {
        when (it[index]) {
            '0' -> zeros++
            '1' -> ones++
        }
    }
    return zeros to ones
}