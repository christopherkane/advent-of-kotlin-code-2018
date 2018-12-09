package puzzle05

import java.io.File

fun main(args: Array<String>) {

    val inputText = File("src/puzzle05/input.txt").reader().readText()

    var lowestCount = Int.MAX_VALUE

    for (i in 65..90) {
        val reduced = inputText.replace(i.toChar().toString(), "", true)
        val polymer = Polymer(reduced)

        polymer.anhialate()

        var count = -1;
        for (a in polymer)
            count ++

        if (count < lowestCount)
            lowestCount = count
    }

    println(lowestCount)
}