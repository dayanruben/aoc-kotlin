package utils

fun <T> List<T>.indexOfOrNull(value: T): Int? {
    val index = indexOf(value)
    return if (index >= 0) index else null
}

fun <T> List<T>.toPair(): Pair<T, T> {
    require(size == 2) { "List must contain exactly two elements to convert to a pair." }
    return this[0] to this[1]
}