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

    val narcolepticGuard = guardSleepingTimes.maxBy { r -> totalMinutesAsleep(r.component2()) }

    val minuteAsleepMost = narcolepticGuard!!.component2().maxBy { r -> r.value }

    val answer = narcolepticGuard!!.component1() * minuteAsleepMost!!.key

    println("Guard ID x Minute spent asleep most: ${answer}");
}

fun totalMinutesAsleep(sleepingTimes: MutableMap<Int, Int>): Int {
    var total = -1
    for (sleepingTime in sleepingTimes) {
        total += sleepingTime.value
    }
    return total
}

fun addGuardSleepingTimes(guard: Int,
                          start: LocalDateTime,
                          end: LocalDateTime,
                          guardSleepingTimes: MutableMap<Int, MutableMap<Int, Int>>) {
    val sleepingTimes = guardSleepingTimes.getOrDefault(guard, mutableMapOf())
    for (minute in start.minute .. end.minute) {
        val time = sleepingTimes.getOrDefault(minute, 0)
        sleepingTimes.set(minute, time.inc())
    }
    guardSleepingTimes.put(guard, sleepingTimes)
}

fun extractRecord(record: String,
                 recordConsumer: (Record) -> Boolean) {

    val destructuredRegex = "\\[(.*)\\] (.*)".toRegex()
    val matchResult = destructuredRegex.matchEntire(record)

    matchResult
        ?.destructured
        ?.let {
            (time, event) ->
                recordConsumer.invoke(Record(time, event))
        }
}

class Record {

    val time: LocalDateTime
    val eventType: EventType
    var guard = -1

    constructor(timestamp: String,
                event: String) {
        time = LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        if (event.equals("wakes up")) {
            eventType = EventType.WAKE_UP
        } else if (event.equals("falls asleep")) {
            eventType = EventType.FALL_ASLEEP
        } else {
            eventType = EventType.CLOCK_IN
            guard = ".*#([0-9]+).*".toRegex().matchEntire(event)!!.groupValues[1].toInt()
        }
    }
}

enum class EventType {
    CLOCK_IN, FALL_ASLEEP, WAKE_UP
}