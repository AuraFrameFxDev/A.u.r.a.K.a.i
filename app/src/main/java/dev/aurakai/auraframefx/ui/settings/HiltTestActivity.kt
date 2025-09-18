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
     * Initialize the activity and log a greeting obtained from the injected GreetingProvider.
     *
     * Requires Hilt to have injected [greetingProvider] before this method is called;
     * accessing the lateinit property earlier will throw [UninitializedPropertyAccessException].
     *
     * @param savedInstanceState The activity's previously saved state, if any, forwarded to `super.onCreate`.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HiltTestActivity", greetingProvider.getGreeting())
    }
}
