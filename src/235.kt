import java.math.BigDecimal
import java.math.BigDecimal.ONE
import java.math.RoundingMode.HALF_UP

const val n: Int = 5000
val a1: BigDecimal = BigDecimal(900 - 3*1)
val an1: BigDecimal = BigDecimal(900 - 3*(n+1))
val d: BigDecimal = BigDecimal(-3)
val target: BigDecimal = BigDecimal(-600_000_000_000)
val eps: BigDecimal = BigDecimal(0.000000000000000001)

/**
 * https://projecteuler.net/problem=235
 *
 * Binary search, using closed form expression for sum of terms in an arithmetico-geometric series:
 * https://en.wikipedia.org/wiki/Arithmetico%E2%80%93geometric_sequence#Sum_of_the_terms
 */
fun main() {
  var r0 = BigDecimal(0)
  var r1 = BigDecimal(0.999999)
  var s = amgmsum(r1)
  while ((r1-r0).abs() > eps) {
//    println("$r1: ${s.setScale(13, HALF_UP)}")
    val t = r0
    r0 = r1.setScale(20, HALF_UP)
    r1 = if (s - target > eps) {
      r1 + (t - r1).abs() / BigDecimal(2)
    } else {
      r1 - (t - r1).abs() / BigDecimal(2)
    }.setScale(20, HALF_UP)
    s = amgmsum(r1)
  }
  println("${r1.setScale(12, HALF_UP)}")
}

fun amgmsum(r: BigDecimal): BigDecimal {
  val omr = ONE - r
  val gn1: BigDecimal = r.pow(n)
  return ((a1 - an1*gn1) / omr) + (d*r*(ONE - gn1) / (omr*omr))
}