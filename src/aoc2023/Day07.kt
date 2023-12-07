package aoc2023

import checkValue
import readInput

fun main() {
    val (year, day) = "2023" to "Day07"

    fun totalWinning(input: List<String>, hasJoker: Boolean): Long {
        val size = input.size
        return input.map { line ->
            val (cards, bid) = line.split(" ").map { it.trim() }
            Hand(cards, hasJoker) to bid.toLong()
        }.sortedBy {
            it.first
        }.mapIndexed { index, (_, bid) ->
            (size - index) * bid
        }.sum()
    }

    fun part1(input: List<String>) = totalWinning(input, hasJoker = false)

    fun part2(input: List<String>) = totalWinning(input, hasJoker = true)

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput), 6440)
    println(part1(input))

    checkValue(part2(testInput), 5905)
    println(part2(input))
}

data class Hand(val cards: String, val hasJoker: Boolean = false) : Comparable<Hand> {

    private val cardOrder = if (!hasJoker) {
        listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
    } else {
        listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
    }

    private val cardsComparator: Comparator<String> = Comparator { hand1, hand2 ->
        for (i in hand1.indices) {
            val order1 = cardOrder.indexOf(hand1[i])
            val order2 = cardOrder.indexOf(hand2[i])
            if (order1 < order2) return@Comparator -1
            if (order1 > order2) return@Comparator 1
        }
        return@Comparator 0
    }

    private fun handType(cards: String): Int {
        val groups = cards.groupBy { it }.entries.map { (key, value) ->
            key to value.size
        }.sortedByDescending {
            it.second
        }.toMutableList()

        if (hasJoker) {
            val jokerIndex = groups.indexOfFirst { it.first == 'J' }
            val biggerIndex = groups.indexOfFirst { it.first != 'J' }
            if (jokerIndex >= 0 && biggerIndex >= 0) {
                groups[biggerIndex] = groups[biggerIndex].first to (groups[biggerIndex].second + groups[jokerIndex].second)
                groups.removeAt(jokerIndex)
            }
        }

        return when {
            groups.size == 1 -> 1
            groups.size == 2 && groups.first().second == 4 -> 2
            groups.size == 2 && groups.first().second == 3 -> 3
            groups.size == 3 && groups.first().second == 3 -> 4
            groups.size == 3 && groups.first().second == 2 -> 5
            groups.size == 4 && groups.first().second == 2 -> 6
            else -> 7
        }
    }

    override fun compareTo(other: Hand): Int {
        val handType = handType(this.cards)
        val otherHandType = handType(other.cards)
        return when {
            handType != otherHandType -> handType.compareTo(otherHandType)
            else -> cardsComparator.compare(cards, other.cards)
        }
    }
}