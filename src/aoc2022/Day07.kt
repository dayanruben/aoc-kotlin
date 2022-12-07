package aoc2022

import readInputText

fun main() {
    val (year, day) = "2022" to "Day07"

    fun directorySizes(input: String): List<Int> {
        var currentDir = Directory("/")
        val directories = mutableListOf(currentDir)
        input.split("$ ").drop(2).forEach { entry ->
            val details = entry.split("\n").filter { it.isNotBlank() }
            when (val command = details.first()) {
                "ls" -> {
                    if (!currentDir.visited) {
                        currentDir.visited = true
                        details.drop(1).map { it.split(" ") }.forEach { (a, b) ->
                            when (a) {
                                "dir" -> {
                                    with(Directory(name = b, parent = currentDir)) {
                                        directories.add(this)
                                        currentDir.directories.add(this)
                                    }
                                }

                                else -> currentDir.files.add(File(name = b, size = a.toInt()))
                            }
                        }
                    }
                }

                else -> {
                    val (_, dirName) = command.split(" ")
                    currentDir = when (dirName) {
                        ".." -> currentDir.parent ?: currentDir
                        else -> currentDir.directories.first { it.name == dirName }
                    }
                }
            }
        }
        return directories.map { it.size }
    }

    fun part1(input: String) = directorySizes(input).filter { it <= 100000 }.sum()

    fun part2(input: String): Int {
        val sizes = directorySizes(input)
        val needed = 30000000 - (70000000 - sizes.maxOf { it })
        return sizes.filter { it >= needed }.minOf { it }
    }

    val testInput = readInputText(name = "${day}_test", year = year)
    val input = readInputText(name = day, year = year)

    check(part1(testInput) == 95437)
    println(part1(input))

    check(part2(testInput) == 24933642)
    println(part2(input))
}

data class Directory(
    val name: String,
    val parent: Directory? = null,
    val directories: MutableList<Directory> = mutableListOf(),
    val files: MutableList<File> = mutableListOf(),
    var visited: Boolean = false
) {
    val size: Int
        get() = files.sumOf { it.size } + directories.sumOf { it.size }

    override fun toString() = "name=$name size=$size"
}

data class File(val name: String, val size: Int)