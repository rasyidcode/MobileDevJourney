package com.rasyidcode.lunchtray

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rasyidcode.lunchtray.model.OrderViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class OrderViewModelTests {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @Test
    fun set_entree_to_cauliflower() {
        val viewModel = OrderViewModel()
        viewModel.entree.observeForever { }
        viewModel.setEntree("cauliflower")
        assertEquals("cauliflower", viewModel.entree.value)
    }

}