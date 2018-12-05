package puzzle01

import java.io.File

fun main(args: Array<String>) {
    var frequency: Int = 0
    File("src/puzzle01/input.txt").forEachLine {
        s -> frequency += s.toInt()
    }
    println("Final frequency: ${frequency}")
}