package dev.aurakai.auraframefx.ui.settings

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HiltTestActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testGreetingInjection() {
        // This will launch the activity and trigger the greeting log
        ActivityScenario.launch(HiltTestActivity::class.java)
        
        // The test will pass if the activity is launched without Hilt injection errors
        // Check Logcat for the greeting message: "HiltTestActivity: A.u.r.a.K.a.i"
    }
}
