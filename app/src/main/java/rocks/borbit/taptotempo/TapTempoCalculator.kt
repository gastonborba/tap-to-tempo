package rocks.borbit.taptotempo

import android.os.SystemClock

class TapTempoCalculator(
    private val smoothingAlpha: Double = 0.3,
    private val maxIntervalMillis: Long = 2000L
) {
    private val taps = mutableListOf<Long>()
    private var smoothedBpm: Double? = null

    fun tap(): Double? {
        val now = SystemClock.elapsedRealtime()

        if (taps.isNotEmpty() && now - taps.last() > maxIntervalMillis) {
            reset()
        }

        taps.add(now)

        if (taps.size < 2) return null

        val intervals = taps.zipWithNext { a, b -> b - a }
        val avgInterval = intervals.average().takeIf { it > 0 } ?: return null

        val currentBpm = 60000.0 / avgInterval

        smoothedBpm = smoothedBpm?.let {
            smoothingAlpha * currentBpm + (1 - smoothingAlpha) * it
        } ?: currentBpm

        return smoothedBpm
    }

    fun reset() {
        taps.clear()
        smoothedBpm = null
    }
}
