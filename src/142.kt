/**
 * https://projecteuler.net/problem=142
 *
 * We can be a little bit smart about how we iterate:
 * 1. Compute a bunch of squares in advance. Use these for iteration and lookup so we don't have to recalculate.
 * 1. Only consider y and z which are a square away from x.
 * 1. Minor early optimization: check that x+y is a square before trying any z.
 *
 * Between these steps (and obvious bounds checks on y/z), this runs reasonably quickly. We could be even smarter by
 * using the relations given in the problem and exploiting the fact that we have some Pythagorean triples,
 *    e.g. x+z = a^2, y-z = b^2, x+y = c^2 ==> a^2 + b^2
 * to reduce or directly determine sets to consider.
 */
fun main() {
  // For efficient iteration with square steps
  val sList = (1L..10000).map { it * it }
  // For efficient look-ups
  val sSet = sList.toHashSet()

  outer@ for (x in 1L..1_000_000_000) {
    for (yInd in 0..sList.size) {
      val y = x - sList[yInd] // x-y is good, and x > y by construction
      if (y < 0) break
      if (!sSet.contains(x + y)) continue // x+y is checked
      for (zInd in yInd+1..sList.size) {
        val z = x - sList[zInd] // x-z is good, and y > z by construction
        if (z < 0) break
        if (!sSet.contains(x + z)) continue // x+z is good
        if (!sSet.contains(y + z)) continue // y+z is good
        if (!sSet.contains(y - z)) continue // y-z is good
        println("$x + $y + $z = ${x+y+z}")
        break@outer
      }
    }
  }
}