package dev.aurakai.auraframefx.ui.settings

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GreetingProvider @Inject constructor() {
    /**
 * Returns the constant greeting used by the settings UI.
 *
 * @return The greeting string "A.u.r.a.K.a.i".
 */
fun getGreeting(): String = "A.u.r.a.K.a.i"
}

