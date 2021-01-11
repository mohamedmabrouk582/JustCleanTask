package com.example.justcleantask.view.fragments

import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.example.justcleantask.R
import com.example.justcleantask.lunchedFragmentInHiltContainer
import com.example.justcleantask.view.adapter.PostsAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class PostsFragmentTest{
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun CLick_Pots_item_navigate_to_postDetails() {
        val navController = mock(NavHostController::class.java)
        lunchedFragmentInHiltContainer<PostsFragment> {
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition<PostsAdapter.Holder>(0,MyViewAction.clickItemWithId(R.id.posts_item))
        )

        Mockito.verify(navController).navigate(R.id.action_postsFragment_to_postDetailsFragment)
    }



}