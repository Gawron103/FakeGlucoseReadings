package com.example.glucosereadings.data.model

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class Cgm {

    private var egvLimit = 0
    private var isEgvTransmissionActive = true

    fun startEgvTransmission(): Observable<Int> {
        return Observable
            .interval(0, 2000L, TimeUnit.MILLISECONDS)
            .takeWhile { isEgvTransmissionActive }
            .map { generateRandomGlucoseValue() }
    }

    fun stopEgvTransmission() {
        isEgvTransmissionActive = false
    }

    fun setEgvLimit(newEgvLimit: Int) {
        egvLimit = newEgvLimit
    }

    private fun generateRandomGlucoseValue(): Int {
        return (egvLimit - 5..egvLimit + 5).shuffled().first()
    }

}