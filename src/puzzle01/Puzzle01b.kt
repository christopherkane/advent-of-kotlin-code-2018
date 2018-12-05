package puzzle01

import java.io.File

fun main(args: Array<String>) {

    val inputFrequencyChanges: MutableList<Int> = mutableListOf()

    File("src/puzzle01/input.txt").forEachLine {
        s -> inputFrequencyChanges.add(s.toInt())
    }

    var frequency: Int = 0
    val computedFrequencies = HashSet<Int>()
    computedFrequencies.add(frequency)

    var found: Boolean = false
    do {
        for (frequencyChange in inputFrequencyChanges) {
            frequency += frequencyChange
            if (computedFrequencies.add(frequency) == false) {
                found = true
                break
            }
        }
    } while (found == false)

    println("First frequency reached twice: ${frequency}")
}