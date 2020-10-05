/**
 * https://projecteuler.net/problem=719
 *
 * Brute force is good enough.
 */
fun main() {
  println("${(2L..1_000_000).filter { canDigitsSumTo(it, toIntArray(it*it), 0, 0, 0) }.map { it*it }.sum()}")
}

// WARNING: won't work for doubles, floats, etc. Also inefficient.
fun toIntArray(x: Number): IntArray {
  return x.toString().toCharArray().map { it - '0' }.toIntArray()
}

fun canDigitsSumTo(x: Long, xDigits: IntArray, i: Int, s: Long, carry: Int): Boolean {
  if (s > x) return false
  if (i == xDigits.size) {
    return s+carry == x
  }
  return canDigitsSumTo(x, xDigits, i+1, s+carry, xDigits[i])
      || canDigitsSumTo(x, xDigits, i+1, s, carry*10 + xDigits[i])
}

