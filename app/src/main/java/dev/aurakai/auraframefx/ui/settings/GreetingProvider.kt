package dev.aurakai.auraframefx.ui.settings

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GreetingProvider @Inject constructor() {
    /**
 * Returns the app's stylized greeting.
 *
 * @return The fixed greeting string "A.u.r.a.K.a.i".
 */
fun getGreeting(): String = "A.u.r.a.K.a.i"
}

