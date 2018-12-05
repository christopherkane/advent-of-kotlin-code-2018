package puzzle02

import java.io.File

fun main(args: Array<String>) {
    var doubles = 0
    var triples = 0

    val incrementDoubles: () -> Unit = { doubles = doubles.inc() }
    val incrementTriples: () -> Unit = { triples = triples.inc() }

    File("src/puzzle02/input.txt").forEachLine {
        s -> detectDoubleTripleOccurrences(s, incrementDoubles, incrementTriples)
    }
    println("Checksum: ${doubles*triples}")
}

fun detectDoubleTripleOccurrences(id: String,
                                  doubleCallable: () -> Unit,
                                  tripleCallable: () -> Unit) {
    val uniqueIdCharacters = id.toCharArray().distinct()
    var doubleFound = false
    var tripleFound = false
    for (c in uniqueIdCharacters) {
        val count = id.count { char -> char.equals(c) }
        if (count == 2 && doubleFound.equals(false)) {
            doubleFound = true
            doubleCallable.invoke()
        } else if (count == 3 && tripleFound.equals(false)) {
            tripleFound = true
            tripleCallable.invoke()
        }
        if (doubleFound && tripleFound)
            return
    }
}
