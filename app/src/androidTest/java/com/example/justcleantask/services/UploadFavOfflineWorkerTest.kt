package com.example.justcleantask.services

import android.content.Context
import android.util.Log
import androidx.test.InstrumentationRegistry
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.work.*
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.testing.WorkManagerTestInitHelper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@ExperimentalCoroutinesApi
class UploadFavOfflineWorkerTest{
    @get:Rule
    val hiltRule = HiltAndroidRule(this)
    private lateinit var context: Context

    @Before
    fun setUp(){
        hiltRule.inject()
        context = InstrumentationRegistry.getTargetContext()
        val config = Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .setExecutor(SynchronousExecutor())
                .build()

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    fun testUploadFavWorker(){
        val constrains=Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED).build()
        val request = OneTimeWorkRequestBuilder<UploadFavOfflineWorker>()
                .setConstraints(constrains)
                .build()

        val workManager = WorkManager.getInstance(context)

        workManager.enqueue(request).result.get()

        // Simulate constraints
        WorkManagerTestInitHelper.getTestDriver(context)?.setAllConstraintsMet(request.id)

        val workInfo = workManager.getWorkInfoById(request.id).get()
        assertThat(workInfo.state, `is`(WorkInfo.State.SUCCEEDED))
    }

}