import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode.HALF_UP
import kotlin.math.min

private const val N = 1000
private val goal = BigDecimal.valueOf(1_000_000_000)
private val nck = (0..N).map { permutations(N, it) }
private val D = BigDecimal.valueOf(2).pow(N)
private val flipSums = (0..N).map { nck.subList(it, nck.size).reduce { a, b -> a + b } }

/**
 * https://projecteuler.net/problem=267
 *
 * Eh. So the thing to note is that the order of the coin flips doesn't matter, just how many come up heads/tails. Thus
 * the amount won can be calculated directly. Let f be the gambled fraction, k be the number of heads. Then earnings are
 *   (1-f)^(1000-k) * (1+2*f)^k
 * And the probability of this occurring is just the number of ways of flipping k heads divided by the total possible
 * sequences. Which is just
 *   (1000 choose k) / 2^1000
 * From this, we can directly calculate the odds of becoming a billionaire for a given f by summing the probabilities
 * of cases where earnings exceeds a billion. This leads to an interesting point: we only have 1001 possible answers. So
 * f can be anywhere in a range that results in this maximal probability, we just have to ensure that there are no other
 * possible values of f which would lead to a higher probability.
 *
 * So I used some test values, found that we hit what seems like a max around .14 (with 432+ heads leading to $$$$). We
 * can then just do a binary search down to some reasonable step distance to verify that there is no f where k = 431
 * works. This turns out to be the case, and if it didn't we could just plug in the probability for 431 on down anyway.
 */
fun main() {
  var f = .14
  var step = .01
  var bestP = BigDecimal(oddsGtBillion(f)).divide(D)
  while (step > .00001) {
    val downStepP = BigDecimal(oddsGtBillion(f-step)).divide(D)
    if (downStepP >= bestP) {
      f -= step
      bestP = downStepP
    }
    step *= .5
  }
  println(bestP.setScale(12, HALF_UP))
}

private fun oddsGtBillion(f: Double): BigInteger {
  for (flips in 0..N) {
    val winnings =
        BigDecimal.valueOf(1 - f).pow(N - flips) *
            BigDecimal.valueOf(1 + f + f).pow(flips)
    if (winnings > goal) {
      return flipSums[flips]
    }
  }
  println("never succeeded for $f")
  return BigInteger.ZERO
}

fun permutations(n: Long, k: Long): BigInteger {
  if (n < 0 || k < 0) error("Invalid input for n choose k: n=$n, k=$k")
  if (k == 0L || k == n) return BigInteger.ONE
  val k0 = min(k, n-k)
  var s = BigInteger.ONE
  for (i in 1..k0) {
    s = s * BigInteger.valueOf(n-i+1) / BigInteger.valueOf(i)
  }
  return s
}

fun permutations(n:Int, k: Int): BigInteger = permutations(n.toLong(), k.toLong())

