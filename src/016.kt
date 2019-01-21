import java.math.BigInteger

/** https://projecteuler.net/problem=16 */
fun main(args: Array<String>) {
  println(
      BigInteger.valueOf(2)
          .pow(1000)
          .toString()
          .map { c -> c.toString().toInt() }
          .sum())
}