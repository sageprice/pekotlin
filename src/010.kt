/** https://projecteuler.net/problem=10 */
fun main(args: Array<String>) {
  val ps: Array<Boolean> = Array(2_000_000, { _ -> true })
  ps[0] = false
  ps[1] = false

  (2 until ps.size)
      .asSequence()
      .filter { ps[it] }
      .flatMap { (it + it until ps.size step it).asSequence() }
      .forEach { ps[it] = false }
  var s: Long = 0
  ps.forEachIndexed { i, p -> s += if (p) i else 0 }
  println(s)
}