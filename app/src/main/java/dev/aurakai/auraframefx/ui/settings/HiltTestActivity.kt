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
     * Called when the activity is created.
     *
     * Passes the provided savedInstanceState to the superclass and logs the greeting obtained
     * from the injected GreetingProvider under the "HiltTestActivity" tag.
     *
     * @param savedInstanceState If non-null, this activity is being re-initialized from a
     * previously saved state; otherwise null. */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HiltTestActivity", greetingProvider.getGreeting())
    }
}
