import java.math.BigInteger

/** https://projecteuler.net/problem=16 */
fun main() {
  println(
      BigInteger.valueOf(2)
          .pow(1000)
          .toString()
          .sumBy { c -> c - '0' })
}