package dev.aurakai.auraframefx.ui.settings

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GreetingProvider @Inject constructor() {
    fun getGreeting(): String = "A.u.r.a.K.a.i"
}

