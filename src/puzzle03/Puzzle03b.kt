package puzzle03

import java.io.File

fun main(args: Array<String>) {

    val claims: MutableSet<Claim> = mutableSetOf()

    File("src/puzzle03/input.txt").forEachLine {
        s -> extract(s, claims::add)
    }

    var claimedSqaures: MutableMap<Int, MutableSet<Claim>> = mutableMapOf()

    for (claim in claims) {
        val defaultClaimSet = mutableSetOf(claim)
        for (x in claim.xOffset .. claim.xOffset+claim.xLength-1) {
            for (y in claim.yOffset .. claim.yOffset+claim.yLength-1) {
                val k = (y*1000)+x // conceptually 2D array in a single dimension
                val current = claimedSqaures.getOrDefault(k, defaultClaimSet)
                current.add(claim)
                claimedSqaures.put(k, current)
            }
        }
    }

    val clashingClaims: MutableSet<Claim> = mutableSetOf()

    claimedSqaures.forEach() { entry ->
        if (entry.value.size > 1) {
            clashingClaims.addAll(entry.value)
        }
    }

    claims.removeAll(clashingClaims);

    println("Claim ${claims.stream().findFirst().get().id} is the only claim that does not clash!")
}
