package aoc2021

import readInput

fun main() {
    val (year, day) = "2021" to "Day04"

    fun part1(input: List<String>): Int {
        val (numbers, boards) = parseInput(input)
        return playBingo(boards, numbers)
    }

    fun part2(input: List<String>): Int {
        val (numbers, boards) = parseInput(input)
        return playBingo(boards, numbers, firstFound = false)
    }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 4512)
    println(part1(input))

    check(part2(testInput) == 1924)
    println(part2(input))
}

fun parseInput(input: List<String>): Pair<List<Int>, List<List<MutableList<Int>>>> {
    val numbers = input.first().split(",").map { it.toInt() }

    val lines = input.drop(1).filter { it.isNotEmpty() }
    val boards = lines.windowed(5, 5).map {
        it.map { row ->
            row.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toMutableList()
        }.toMutableList()
    }.toMutableList()

    return numbers to boards
}

fun playBingo(
    boards: List<List<MutableList<Int>>>,
    numbers: List<Int>,
    firstFound: Boolean = true
): Int {
    val boardBingo = BooleanArray(boards.size)
    var bingoLeft = boards.size
    var markedNumbers = mutableSetOf<Int>()

    numbers.forEach { number ->
        markedNumbers += number

        boards.forEachIndexed { index, board ->
            if (boardBingo[index]) return@forEachIndexed

            val (bingo, sum) = board.checkBoard(markedNumbers)
            if (bingo) {
                boardBingo[index] = bingo
                if (firstFound || --bingoLeft == 0) {
                    return sum * number
                }
            }
        }
    }

    return 0
}

fun List<List<Int>>.sumBoard(markedNumbers: Set<Int>): Int {
    return sumOf { it.filter { number -> number !in markedNumbers }.sum() }
}

fun List<List<Int>>.checkBoard(markedNumbers: Set<Int>): Pair<Boolean, Int> {
    for (row in indices) {
        val bingo = this[row].all { it in markedNumbers }
        if (bingo) return Pair(true, sumBoard(markedNumbers))
    }

    for (col in this[0].indices) {
        val bingo = all { it[col] in markedNumbers }
        if (bingo) return Pair(true, sumBoard(markedNumbers))
    }

    return false to 0
}