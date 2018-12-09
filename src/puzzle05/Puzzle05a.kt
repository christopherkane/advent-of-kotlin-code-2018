package puzzle05

import java.io.File

fun main(args: Array<String>) {

    val inputText = File("src/puzzle05/input.txt").reader().readText()

    val polymer = Polymer(inputText)

    polymer.anhialate()

    var count = -1;
    for (a in polymer) {
        count ++
    }

    println(count)
}

class Polymer(data: String): Iterable<Node?> {

    var root = Node(null, null, null)

    init {
        var previous = root
        for (c in data.toCharArray()) {
            var next = Node(c, previous, null)
            previous.next = next
            previous = next
        }
    }

    override fun iterator(): Iterator<Node?> {
        return PolymerIterator(root)
    }

    class PolymerIterator(root: Node): Iterator<Node?> {

        var current: Node? = root

        override fun hasNext(): Boolean {
            return current != null
        }

        override fun next(): Node? {
            val toReturn = current
            current = current?.next
            return toReturn
        }
    }

    fun anhialate() {

        var left = root.next
        var right = left?.next

        while (left != null && right != null) {
            if (left.willAnhilate(right)) {

                var newLeft = left.prev
                var newRight = right?.next

                newLeft?.next = newRight
                newRight?.prev = newLeft

                left.next = null
                left.prev = null

                right.next = null
                right.next = null

                left = newLeft
                right = newRight
            } else {
                left = right
                right = left?.next
            }
        }

    }

}

class Node(var data: Char?,
           var prev: Node? = null,
           var next: Node? = null) {

    fun willAnhilate(other: Node?): Boolean {
        if (other == null || data == null || other.data == null)
            return false

        if (other.data!!.toUpperCase() != data!!.toUpperCase())
            return false

        if (other.data!!.isUpperCase() && data!!.isUpperCase())
            return false

        if (other.data!!.isLowerCase() && data!!.isLowerCase())
            return false

        return true
    }
}