package aoc2023

import checkValue
import readInputText

fun main() {
    val (year, day) = "2023" to "Day15"

    fun String.toSequence() = replace("\n", "").split(",")

    fun String.hash() = fold(0) { hash, ch ->
        ((hash + ch.code) * 17) % 256
    }

    fun part1(input: String) = input.toSequence().sumOf { it.hash() }

    fun part2(input: String): Long {
        val sequence = input.toSequence()
        val boxes = Array(256) { mutableListOf<Lens>() }
        for (step in sequence) {
            val operation = if (step.contains('-')) '-' else '='
            val (label, focal) = step.split(operation)
            val hash = label.hash()
            when (operation) {
                '=' -> {
                    val index = boxes[hash].indexOfFirst { it.label == label }
                    val lens = Lens(label, focal.toInt())
                    if (index >= 0) {
                        boxes[hash].removeAt(index)
                        boxes[hash].add(index, lens)
                    } else {
                        boxes[hash].add(lens)
                    }
                }

                '-' -> {
                    boxes[hash].indexOfFirst { it.label == label }.takeIf { it >= 0 }?.also {
                        boxes[hash].removeAt(it)
                    }
                }
            }
        }
        return boxes.withIndex().sumOf { (boxIndex, lenses) ->
            lenses.foldIndexed(0L) { lensIndex, acc, lens ->
                acc + (boxIndex + 1) * (lensIndex + 1) * lens.focal
            }
        }
    }

    val testInput = readInputText(name = "${day}_test", year = year)
    val input = readInputText(name = day, year = year)

    checkValue(part1(testInput), 1320)
    println(part1(input))

    checkValue(part2(testInput), 145)
    println(part2(input))
}

data class Lens(val label: String, val focal: Int)