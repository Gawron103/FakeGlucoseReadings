package com.example.glucosereadings.models

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class Cgm {

    private var egvLimit = 0
    private lateinit var type: SensorType

    fun getEgvObservable(): Observable<Int> {
        return Observable
            .interval(2000L, TimeUnit.MILLISECONDS)
            .flatMap {
                return@flatMap Observable.create<Int> { emitter ->
                    emitter.onNext(generateRandomGlucoseValue())
                }
            }
    }

    fun setEgvLimit(newEgvLimit: Int) {
        egvLimit = newEgvLimit
    }

    fun setSensorType(type: SensorType) {
        this.type = type
    }

    private fun generateRandomGlucoseValue(): Int {
        return (egvLimit - 5..egvLimit + 5).shuffled().first()
    }

}