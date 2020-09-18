/**
 * https://projecteuler.net/problem=149
 *
 * DP yo
 */
fun main() {
//  val grid = arrayListOf(arrayOf(-2L, 5, 3, 2), arrayOf(9L, -6, 5, 1), arrayOf(3L, 2, 7 ,3), arrayOf(-1L, 8, -4, 8))

  val grid = constructGrid()
  //  printGrid(grid)
  val neMax = largestEntry(dynamicNortheast(grid))
  val hMax = largestEntry(dynamicHorizontals(grid))
  val vMax = largestEntry(dynamicVerticals(grid))
  val seMax = largestEntry(dynamicSoutheast(grid))
  val maxes = listOf(neMax, hMax, vMax, seMax)
//  println(maxes)
  println(maxes.maxOrNull())
}

private fun constructGrid(): ArrayList<Array<Long>> {
  // Start with list containing 0 just to make index-based access more straightforward.
  val ks = mutableListOf<Long>(0)
  (1..55L).forEach { k ->
    ks.add(((100_003L - 200_003 * k + 300_007 * k * k * k) % 1_000_000) - 500_000)
  }
  (56..4_000_000L).forEach { k ->
    ks.add(((ks[k.toInt() - 24] + ks[k.toInt() - 55] + 1_000_000) % 1_000_000) - 500_000)
  }
  ks.removeAt(0)
  val chunks = ks.chunked(2_000).map { it.toTypedArray() }
  val grid = arrayListOf<Array<Long>>()
  chunks.forEach { grid.add(it) }
  return grid
}

fun dynamicHorizontals(g: ArrayList<Array<Long>>): ArrayList<Array<Long>> {
  val hSums = copyGrid(g)
  for (r in g.indices) {
    for (c in 1 until g[r].size) {
      if (g[r][c] + hSums[r][c-1] > hSums[r][c]) {
        hSums[r][c] = g[r][c] + hSums[r][c-1]
      }
    }
  }
  return hSums
}

fun dynamicVerticals(g: ArrayList<Array<Long>>): ArrayList<Array<Long>> {
  val vSums = copyGrid(g)
  for (c in g[0].indices) {
    for (r in 1 until g.size) {
      if (g[r][c] + vSums[r-1][c] > vSums[r][c]) {
        vSums[r][c] = g[r][c] + vSums[r-1][c]
      }
    }
  }
  return vSums
}

fun dynamicSoutheast(g: ArrayList<Array<Long>>): ArrayList<Array<Long>> {
  val seSums = copyGrid(g)
  for (offset in 1 until g.size) {
    for (i in 0 until g.size) {
      if (i + offset == g.size) break
      if (g[i+offset][offset] + seSums[i+offset-1][offset-1] > seSums[i+offset][offset]) {
        seSums[i+offset][offset] = g[i+offset][offset] + seSums[i+offset-1][offset-1]
      }
      if (g[offset][i+offset] + seSums[offset-1][i+offset-1] > seSums[offset][i+offset]) {
        seSums[offset][i+offset] = g[offset][i+offset] + seSums[offset-1][i+offset-1]
      }
    }
  }
  return seSums
}

fun dynamicNortheast(g: ArrayList<Array<Long>>): ArrayList<Array<Long>> {
  val nwSums = copyGrid(g)
  val l = g.size - 1
  for (offset in 1 until g.size) {
    for (i in 0 until g.size) {
      var r = l - (i + offset)
      var c = offset
      if (r >= 0 && c > 0 && g[r][c] + nwSums[r+1][c-1] > nwSums[r][c]) {
        nwSums[r][c] = g[r][c] + nwSums[r+1][c-1]
      }
      r = l - offset
      c = i
      if (r >= 0 && c > 0 && g[r][c] + nwSums[r+1][c-1] > nwSums[r][c]) {
        nwSums[r][c] = g[r][c] + nwSums[r+1][c-1]
      }
    }
  }
  return nwSums
}

fun copyGrid(g: ArrayList<Array<Long>>): ArrayList<Array<Long>> {
  val copy = arrayListOf<Array<Long>>()
  for (r in g.indices) {
    copy.add(g[r].copyOf())
  }
  return copy
}

fun largestEntry(g: ArrayList<Array<Long>>): Long {
  return g.mapNotNull { it.maxOrNull() }.maxOrNull()!!
}

fun printGrid(g: ArrayList<Array<Long>>) {
  println("++++++++++++++++++++++++++++++++++++++++")
  for (r in g.indices) {
    print("[\t")
    for (c in g[r].indices) {
      print("${g[r][c]}\t")
    }
    println("]")
  }
}