package com.example.chineselistener.util

/** Wrap [degrees] into ([minInclusive], [maxInclusive]] using [period] steps (e.g. line angle ±90°, period 180). */
internal fun wrapDegrees(degrees: Double, minInclusive: Double, maxInclusive: Double, period: Double): Double {
    tailrec fun go(value: Double): Double =
        when {
            value <= minInclusive -> go(value + period)
            value > maxInclusive -> go(value - period)
            else -> value
        }
    return go(degrees)
}

/** First index in `start until beforeExclusive` stepping by [stride] where [predicate] is true, else [beforeExclusive]. */
internal fun scanForwardIndex(
    start: Int,
    beforeExclusive: Int,
    stride: Int,
    predicate: (Int) -> Boolean,
): Int =
    (start until beforeExclusive step stride.coerceAtLeast(1))
        .firstOrNull(predicate)
        ?: beforeExclusive

/** First index from [start] down toward [afterExclusive] stepping [stride] where [predicate] is true, else [afterExclusive]. */
internal fun scanBackwardIndex(
    start: Int,
    afterExclusive: Int,
    stride: Int,
    predicate: (Int) -> Boolean,
): Int =
    generateSequence(start) { it - stride }
        .takeWhile { it > afterExclusive }
        .firstOrNull(predicate)
        ?: afterExclusive

/** First index from [start] up while below [beforeExclusive] stepping 1 where [predicate] is true, else [beforeExclusive]. */
internal fun scanForwardByOne(start: Int, beforeExclusive: Int, predicate: (Int) -> Boolean): Int =
    (start until beforeExclusive).firstOrNull(predicate) ?: beforeExclusive

/** First index from [start] down while above [afterExclusive] where [predicate] is true, else [afterExclusive]. */
internal fun scanBackwardByOne(start: Int, afterExclusive: Int, predicate: (Int) -> Boolean): Int =
    (afterExclusive + 1..start).reversed().firstOrNull(predicate) ?: afterExclusive
