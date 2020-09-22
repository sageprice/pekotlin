/**
 * https://projecteuler.net/problem=169
 *
 * Recursive/DP solution. There are smarter ways to do this but this is mine. Thought process as follows:
 *
 * Say we have some integer x. Then it has a binary representation b(x). Let's try this with x = 40, b(x) = 101000.
 * We can express b(x) as a sum of powers of 2 via this string representation: 101000 = 1*2^5 + 1*2^3. We can transform
 * this string to achieve other possible sums. E.g., if we have a substring "10", this is equal to "02", say
 *    101000 = 1*2^5 + 1*2^3 = 100200 = 1*2^5 + 2*2^2
 * Similarly, we can replace "20" with a "12",
 *    100200 = 100120 = 1*2^5 + 1*2^2 + 2*2^1
 * Now, we can look at this and note a few things:
 *  1. From the set of valid transforms, the number of twos cannot exceed the number of ones initially in the string.
 *  2. Twos must always appear to the right of the one they replace.
 *  3. When a two replaces a one, it can occupy any slots that were originally a zero after the replaced 1, or the slot
 *     of the first 1 after the replaced 1 (assuming the second one was transformed into an "02..", "012..", "0112..",
 *     "01112", etc., as this would open up an additional zero slot).
 * From these assertions, we can try to derive a recurrence for f(x).
 * So we have 101000. Let's focus on what happens if we transform the last 1. Valid options are:
 *  101000
 *  100200
 *  100120
 *  100112
 * Now we consider how many ways there are of transforming each of these. Since we've fixed everything after the last 1,
 * the only thing we need to look at is how many ways there are to transform the prefix before the end string. So this
 * is just:
 *  f(101000) = f(101)
 *  f(100200) = f(100)
 *  f(100120) = f(100)
 *  f(100112) = f(100)
 * Furthermore, adding a 1 to the end of a string does not increase/decrease the number of transformation since the 1
 * cannot be transformed, so
 *  f(101000) = f(101) = f(10)
 * And we can observe that the number of transforms for the final 1 equals the number of trailing 0s. Finally, we have
 * an algorithm for calculating f(x):
 *  1. Remove any trailing 1s in b(x), call this b'(x)
 *  2. Find the last 1 in b'(x). It has index k, and the count of zeros trailing it is z (= len(b(x) - k)
 *  3. b_l := b'(x)[0:k], b_r := b'(x)[0:k] + '0'
 *  4. f(x) = f(b_l) + k * f(b_r)
 * Applying this recursively gives us our solution.
 */
fun main() {
  // Binary string taken from Wolfram Alpha b/c I am lazy.
  val bString = "100001000101100101010001011000010100000000010100100001001010000000000000000000000000"
  val scoreCache =
      mutableMapOf(
          Pair("0", 1L),
          Pair("1", 1),
          Pair("10", 2),
          Pair("11", 1),
          Pair("100", 3),
          Pair("101", 2),
          Pair("110", 3),
          Pair("111", 1),
          Pair("1000", 4),
          Pair("10000", 5),
          Pair("100000", 6),
          Pair("1000000", 7))
  println(sum(bString, scoreCache))
}

fun sum(b: String, cache: MutableMap<String, Long>): Long {
  if (cache.containsKey(b)) {
    return cache[b]!!
  }
  val bDecomp = decompose(b)
  val lSum = sum(bDecomp.left, cache)
  val rSum = sum(bDecomp.right, cache)
  val bScore = lSum + rSum * bDecomp.rCount
  cache[b] = bScore
  return bScore
}

/**
 * For our purposes, to decompose a binary string is to break it into a couple components:
 * @param left all characters in the string up to the last '1' excluding the '1'
 * @param right all characters in the string up to the last '1', but replacing the final '1' with a '0'
 * @param rCount the number of 0s after the final '1'
 */
data class Decomposition(val left: String, val right: String, val rCount: Int)

fun decompose(b: String): Decomposition {
  val oneIndex = b.lastIndexOf('1')
  b.substring(0 until oneIndex)
  return Decomposition(
      left = b.substring(0 until oneIndex),
      right = b.substring(0 until oneIndex) + '0',
      rCount = b.length - oneIndex - 1)
}