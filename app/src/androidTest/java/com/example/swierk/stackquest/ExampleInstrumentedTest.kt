package com.example.swierk.stackquest


import androidx.test.InstrumentationRegistry.getTargetContext
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.swierk.stackquest.View.SearchActivity
import com.linkedin.android.testbutler.TestButler
import org.junit.AfterClass
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    @Rule
    @JvmField
    val rule  = ActivityTestRule(SearchActivity::class.java)
    private val queryToSearch = "java"



    @Test
    fun trySearchForAQuery(){
        onView((withId(R.id.search_query)))
            .perform(click())

        onView((withId(R.id.search_query)))
            .perform(typeText(queryToSearch))
        Thread.sleep(1000)

    }
    @Test
    fun trySearchWithNoInternetConnection() {
        TestButler.setWifiState(false)
        TestButler.setGsmState(false)

        onView((withId(R.id.search_query)))
            .perform(click())

        onView((withId(R.id.search_query)))
            .perform(typeText(queryToSearch))

        Thread.sleep(1000)

        onView((withId(R.id.alertTitle))).check(matches(withText(R.string.error_alert_title)))

    }


}
