/**
 * https://projecteuler.net/problem=128
 *
 * Do some casework on the types of cells. Basic cases:
 * 1. The first cell in a ring (special due to adjacency to end of ring)
 * 1. The last cell in a ring (special due to adjacency to start of ring)
 * 1. Corner cells except the first cell in a ring
 * 1. "Side" cells other than the last cell in the ring
 *
 * Turns out, only the first and last cell in a ring can possibly have 3 adjacent neighbors with prime differences. From
 * there, we just need formulas to directly calculate the values in these cells and the differences to adjacent cells.
 */
fun main() {
  val targetCount = 2000

  val primes = sieve(1_000_000) // Note: this should be at least 12* the ring number. But this worked.
  val pd3s = mutableListOf(1L, 2)
  for (i in 2..100_000) {
    // First cell in a ring
    val a = 2L + 3L*i*(i-1)
    if (aSurround(i).count { primes[it] } == 3) {
      pd3s.add(a)
      if (pd3s.size == targetCount) break
    }

    // Last in the ring, to the right of the north point
    // TODO: handle innermost ring correctly.
    val z = 1L + 6*i + 3L*i*(i-1)
    if (zSurround(i).count { primes[it] } == 3) {
      pd3s.add(z)
      if (pd3s.size == targetCount) break
    }
  }
  println("${pd3s.size}, ${pd3s.last()}")
}

fun aSurround(ring: Int): List<Int> {
  // One of the other cells has a difference of only 1 so we can ignore it. That leaves us with 5 cells, but in order to
  // have 3 prime differences the cells must have odd parity when prime difference > 2. Thus we only have to consider
  // the odd differences.
  // down-right, up-left, up-right
  return listOf(6*ring - 1, 6*ring+1, 12*ring+5)
}

fun zSurround(ring: Int): List<Int> { // Does not work for inner-most full ring
  // Cells above and below are always different by a multiple of 6, so not prime. Down-right is a difference of 1. That
  // leaves us with only 3 cells to check.
  // down-left, up-left, up-right
  return listOf(12*ring - 7, 6*ring - 1, 6*ring+5)
}

fun sieve(lim: Int): Array<Boolean> {
  val primes = Array(lim) { true }
  primes[0] = false
  primes[1] = false
  for (i in 2 until primes.size) {
    if (primes[i]) {
      for (j in i+i until primes.size step i) {
        primes[j] = false
      }
    }
  }
  return primes
}