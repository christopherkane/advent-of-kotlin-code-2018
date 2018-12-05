package puzzle03

import java.io.File

fun main(args: Array<String>) {

    val claims: ArrayList<Claim> = arrayListOf()

    File("src/puzzle03/input.txt").forEachLine {
        s -> extract(s, claims::add)
    }

    var claimedSqaures: MutableMap<Int, Int> = mutableMapOf()

    for (claim in claims) {
        for (x in claim.xOffset .. claim.xOffset+claim.xLength-1) {
            for (y in claim.yOffset .. claim.yOffset+claim.yLength-1) {
                val k = (y*1000)+x // conceptually 2D array in a single dimension
                val count = claimedSqaures.getOrDefault(k, 0)
                claimedSqaures.put(k, count.inc())
            }
        }
    }

    val clashingSquares = claimedSqaures.filter { entry -> entry.value > 1 }

    println("${clashingSquares.size} squares in clashing claims")
}

fun extract(claim: String,
            claimConsumer: (Claim) -> Boolean) {

    val destructuredRegex = "#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)".toRegex()

    val matchEntire = destructuredRegex.matchEntire(claim)

    matchEntire
            ?.destructured
            ?.let {
                (id, xOffset, yOffset, xLength, yLength) ->
                claimConsumer.invoke(
                        Claim(id.toInt(), xOffset.toInt(), yOffset.toInt(), xLength.toInt(), yLength.toInt())
                )
            }
}

data class Claim (
    val id: Int,
    val xOffset: Int,
    val yOffset: Int,
    val xLength: Int,
    val yLength: Int
)