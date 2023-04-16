package com.example.glucosereadings.utils

private val dexcomG6ValidPinEntries = listOf(
    "1234",
    "8888",
    "4321"
)

fun isPinValid(pin: String) = dexcomG6ValidPinEntries.contains(pin)
