/** Returns the solution to https://projecteuler.net/problem=2 . */
fun main(args: Array<String>) {
  var f1 = 1
  var f2 = 1
  var t: Int
  var s = 0
  while (f1 < 4000000) {
    t = f2
    f2 += f1
    f1 = t
    if (f1 % 2 == 0) {
      s += f1
    }
  }
  println(s)
}