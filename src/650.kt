import lib.incrementValueForKey

/**
 * https://projecteuler.net/problem=650
 */
fun main(args: Array<String>) {

//  val lim = 20000
  val lim = 100

  val factorMaps: MutableList<HashMap<Int, Int>> = getPrimeFactorizations(lim)

  var s = 1L
  var b = mapOf<Int, Int>()
  (2..lim).forEach {
    b = nextB(b, it, factorMaps)
    val x = longPowMapSum(b)
    s = (s + x).rem(MODULUS)
//    println("$i: $s, $x")
  }

  println(s)
}

private fun getPrimeFactorizations(lim: Int): MutableList<HashMap<Int, Int>> {
  val factorMaps: MutableList<HashMap<Int, Int>> = MutableList(lim) { HashMap<Int, Int>() }
  val nums = MutableList(lim + 1) { i -> i }

  for (i in 2..lim) {
    for (n in i..lim step i) {
      while (nums[n].rem(i) == 0) {
        val factorMap = factorMaps[n - 1]
        factorMap.incrementValueForKey(i, 1)
        nums[n] /= i
      }
    }
  }
  return factorMaps
}

private fun nextB(b0: Map<Int, Int>, n: Int, factorizations: List<Map<Int, Int>>): Map<Int, Int> {
  val result = HashMap<Int, Int>(b0)
  factorizations[n - 1].forEach { f, p -> result.incrementValueForKey(f, n * p) }
  for (i in 2..n) {
    factorizations[i - 1].forEach { f, p -> result.incrementValueForKey(f, -p) }
  }
  return result
}

fun longPowMapSum(pMap: Map<Int, Int>): Long {
  return pMap.entries.stream().map { e ->
    longPSum(e.key.toLong(), e.value)
  }.reduce { a, b -> (a * b).rem(MODULUS) }.get()
}

fun longPSum(b: Long, p: Int): Long {
  var s = 1L
  var bigB = b

  for (i in 1..p) {
    s = (s + bigB).rem(MODULUS)
    bigB = (bigB * b).rem(MODULUS)
  }
  return s
}

const val MODULUS = 1_000_000_007L