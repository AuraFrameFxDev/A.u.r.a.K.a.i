package dev.aurakai.auraframefx.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity() {
    @Inject lateinit var greetingProvider: GreetingProvider

    /**
     * Logs a debug message with the greeting from the injected `GreetingProvider` when the activity is created.
     *
     * Hilt must inject `greetingProvider` before this method is invoked; accessing it otherwise will cause a runtime crash.
     *
     * @param savedInstanceState The activity's previously saved state, forwarded to `super.onCreate`.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HiltTestActivity", greetingProvider.getGreeting())
    }
}
