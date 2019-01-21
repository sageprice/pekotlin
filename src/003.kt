/** https://projecteuler.net/problem=3 brute force solution*/
fun main(args: Array<String>) {
  var n = 600851475143
  var p: Long = 1
  do {
    p++
    while (n % p == 0L) {
      n /= p
    }
  } while (n > 1)
  println(p)
}