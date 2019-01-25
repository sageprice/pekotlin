package lib

/** Adds the given increment to the current value associated with the given key. */
fun <K, M> M.incrementValueForKey(index: K, increment: Int) where M : MutableMap<K, Int> {
  this[index] = this.getOrDefault(index, 0) + increment
}

/** Adds the given increment to the current value associated with the given key. */
fun <K, M> M.incrementValueForKey(index: K, increment: Double) where M : MutableMap<K, Double> {
  this[index] = this.getOrDefault(index, 0.0) + increment
}

/** Adds the given increment to the current value associated with the given key. */
fun <K, M> M.incrementValueForKey(index: K, increment: Long) where M : MutableMap<K, Long> {
  this[index] = this.getOrDefault(index, 0) + increment
}
