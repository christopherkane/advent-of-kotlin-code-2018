import java.io.File

fun main(args: Array<String>) {
    var frequency: Int = 0
    File("src/input_01.txt").forEachLine {
        s -> frequency += s.toInt()
    }
    println("Final frequency: ${frequency}")
}