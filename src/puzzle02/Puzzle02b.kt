package puzzle02

import java.io.File

fun main(args: Array<String>) {

    val ids: MutableList<String> = mutableListOf()

    File("src/puzzle02/input.txt").forEachLine { s -> ids.add(s) }

    val sortedIds = ids.sorted()

    for (i in 0 .. (sortedIds.size-2)) {
        val idx = compare(sortedIds[i], sortedIds[i+1])
        if (idx != -1) {
            val id = sortedIds[i]
            println(id.substring(0, idx) + id.substring(idx+1, id.length))
        }
    }

}

fun compare(first: String,
            second: String): Int {

    val a = first.toCharArray()
    val b = second.toCharArray()
    var singleDiscrepancyIndex = -1

    for (j in 0 .. (a.size-1)) {

        if (a[j] == b[j])
            continue

        if (singleDiscrepancyIndex == -1) {
            singleDiscrepancyIndex = j
            continue
        }

        return -1
    }

    return singleDiscrepancyIndex
}