/** https://projecteuler.net/problem=15 */
fun main(args: Array<String>) {
  // Directly calculate 40 choose 20.
  println((40L downTo 21).reduce{l: Long, i -> l * i / (40 - i + 1)})
}