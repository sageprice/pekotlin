/** https://projecteuler.net/problem=4 brute forcey way*/
fun main(args: Array<String>) {
  var max = Int.MIN_VALUE
  for (a in 100..999) {
    (100..a)
        .asSequence()
        .map { a * it }
        .filter { it.toString() == it.toString().reversed() && it > max }
        .forEach { max = it }
  }
  println(max)
}