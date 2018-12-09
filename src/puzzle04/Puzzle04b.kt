package puzzle04

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main(args: Array<String>) {

    val records: ArrayList<Record> = arrayListOf()

    File("src/puzzle04/input.txt").forEachLine {
        s -> extractRecord(s, records::add)
    }

    records.sortBy { r -> r.time }

    var guard = -1
    var start = LocalDateTime.MIN
    var end = LocalDateTime.MIN

    var guardSleepingTimes: MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()

    for (record in records) {
        if (record.eventType == EventType.CLOCK_IN) {
            guard = record.guard
        } else if (record.eventType == EventType.FALL_ASLEEP) {
            start = record.time
        } else {
            end = record.time
            addGuardSleepingTimes(guard, start, end, guardSleepingTimes)
        }
    }

    val narcolepticGuard = guardSleepingTimes.maxBy { r -> maxBySameMinute(r.component2()) }

    val minuteAsleepMost = narcolepticGuard!!.component2().maxBy { r -> r.value }

    val answer = narcolepticGuard!!.component1() * minuteAsleepMost!!.key

    println("Guard ID x Minute spent asleep most: ${answer}");
}

fun maxBySameMinute(sleepingTimes: MutableMap<Int, Int>): Int {
    return sleepingTimes.maxBy { r -> r.value }!!.value
}