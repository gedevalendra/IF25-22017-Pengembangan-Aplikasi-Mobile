import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// Hands-on 3: StateFlow untuk Counter

class CounterManager {
    // Membuat MutableStateFlow dengan nilai awal 0 (bisa diubah dari dalam class)
    private val _count = MutableStateFlow(0)

    // Mengekspos _count sebagai StateFlow (hanya bisa dibaca / read-only dari luar class)
    val count: StateFlow<Int> = _count.asStateFlow()

    fun increment() {
        _count.value += 1
    }

    fun decrement() {
        // Memastikan nilai tidak turun di bawah 0
        if (_count.value > 0) {
            _count.value -= 1
        }
    }

    fun reset() {
        _count.value = 0
    }
}

fun main() = runBlocking {
    val counter = CounterManager()

    // Collect di background
    val job = launch {
        counter.count.collect { println("Count: $it") }
    }

    delay(100)
    counter.increment() // Count: 1
    delay(100)
    counter.increment() // Count: 2
    delay(100)
    counter.decrement() // Count: 1
    delay(100)
    counter.reset()     // Count: 0
    delay(100)

    job.cancel()
}