package dev.aurakai.auraframefx.ui.settings

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GreetingProvider @Inject constructor() {
    /**
 * Provides the application's greeting identifier.
 *
 * Always returns the fixed string "A.u.r.a.K.a.i".
 *
 * @return the greeting string.
 */
fun getGreeting(): String = "A.u.r.a.K.a.i"
}

