package com.penguinpay.util


fun shouldFetch(): Boolean {
    if (savedTime() == 0L)
        return true
    if (getDifferenceInMinutes() >= TIMER)
        return true

    return false
}

private fun getDifferenceInMinutes(): Int {
    return ((getDifference() / 1000) / 60).toInt()
}

private fun getDifference(): Long {
    return currentTime() - savedTime()
}

private fun currentTime(): Long {
    return System.currentTimeMillis()
}

private fun savedTime(): Long {
    return PrefUtil().getLongFromPref(LAST_FETCH_KEY)
}