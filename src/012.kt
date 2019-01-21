/** https://projecteuler.net/problem=12 the brutest of force */
fun main(args: Array<String>) {
  // 20000 is an arbitrary bound, all that matters is that it
  // exceeds the value of the solution
  for (i in 2..20000) {
    val c = nChoose2(i)
    val f = factorCount(c)
    if (f > 500) {
      println(c)
      return
    }
  }
}

private fun nChoose2(n: Int): Long {
  return n.toLong() * (n - 1) / 2
}

private fun factorCount(n: Long): Int {
  return 2 * (1..intSqrt(n)).count { x -> n isMultipleOf x}
}

infix fun Long.isMultipleOf(n: Int) = this % n == 0.toLong()
fun intSqrt(n: Long) : Int = Math.sqrt(n.toDouble()).toInt()