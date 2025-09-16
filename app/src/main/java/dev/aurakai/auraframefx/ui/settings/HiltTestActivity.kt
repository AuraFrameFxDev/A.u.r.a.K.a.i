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
     * Initializes the activity and logs a greeting provided by the injected GreetingProvider.
     *
     * @param savedInstanceState If non-null, this Activity is being re-constructed from a previous saved state contained in this Bundle.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HiltTestActivity", greetingProvider.getGreeting())
    }
}
