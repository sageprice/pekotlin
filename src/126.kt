/**
 * https://projecteuler.net/problem=126
 *
 * Idea: analyze one slice of the cuboid and look at how many cubes are added for each layer. After doing this for the
 * first few layers, we can derive a formula for the number of cubes in a single layer. Say dimensions of the cuboid are
 * x,y,z. Then for an <x,y> slice of the cuboid, the first layer adds 2x+2y cubes. This has to happen for all z layer,
 * then we add xy cubes to cover the top and xy cubes to cover the bottom.
 *
 * So L0 = (2x + 2y)*z + 2*xy = 2*(xy+yz+zx)
 *
 * We can repeat this analysis for the next layer, but we now have to note that the top/bottom slices are different from
 * the intermediate slices of the shape. However, the top/bottom are the same as the original cuboid slices, so the
 * analysis is the same there. Then for the intermediates, we need to add 2x+2y+4 (4 to cover the gaps at the corners).
 * Finally, add top and bottom slices matching the original cuboid slices.
 *
 * So L1 = (2x + 2y + 4*1)*z + 2(2x+2y) + 2*xy = 2(xy+yz+zx) + 4(x+y+z)
 *
 * We can repeat this analysis for the next layer, again using our knowledge of how previous slice work.
 *
 * L2 = (2x + 2y + 4*2)*z + 2(2x + 2y + 4*1) + 2 * (2x + 2y) + 2*xy = 2(xy+yz+zx) + 8(x+y+z) + 8
 *
 * At this point we are starting to see a pattern, and can do one more layer to verify:
 *
 * L3 = 2(xy+yz+zx) + 12(x+y+z) + 24
 *
 * The examples from the problem confirm these values, so after some analysis we can derive a formula for the nth layer:
 *
 * L_n = 2*(xy+yz+zx) + 4n*(x+y+z) + 8*T_n-1
 *
 * Where T_i is the ith triangular number. Now that we have that, it's just a matter of calculation... In this case,
 * brute force is sufficient -- though I had to play around with some bounds to speed things up.
 */
fun main() {
  val maxLayer = 100
  val maxCuboidDimension = 5000
  val stopThreshold = 20000 // optimize early stop for loops

  // Map from count of cubes in a layer to number of times that count occurs.
  val lsums = mutableMapOf<Long, Int>()
  val ts = getTriangularNumbers(maxLayer.toLong())

  for (x in 1L..maxCuboidDimension) {
    for (y in 1..x) {
      for (z in 1..y) {
        // Calculate these early to save repetition in the per-layer loop
        val s = x + y + z
        val cycs = x*y + y*z + z*x
        // Post-optimization: we know the solution is less than this, so we can continue on to next cuboid
        if (cycs > stopThreshold) break
        for (i in 0..maxLayer) {
          var cs = 2*cycs + 4*i*s
          if (i > 0) cs += 8*ts[i-1]
          // We know the solution is less than this, so we can continue on to next cuboid
          if (cs > stopThreshold) break
          lsums.compute(cs) { _, a -> if (a == null) 1 else a + 1 }
        }
      }
    }
  }
  println(lsums.entries.sortedBy { x -> x.key }.filter {it.value == 1000 }.minByOrNull { it.key }?.key)
}

fun getTriangularNumbers(n: Long): List<Long> {
  return (0..n).map { x -> x * (x+1) / 2 }
}