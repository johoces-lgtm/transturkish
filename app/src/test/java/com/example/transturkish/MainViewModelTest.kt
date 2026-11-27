package com.example.transturkish

import com.example.transturkish.ui.navigation.NavigationEvent
import com.example.transturkish.ui.navigation.Screen
import com.example.transturkish.viewmodel.MainViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest : StringSpec({

    beforeTest {
        Dispatchers.setMain(
            StandardTestDispatcher()
        )
    }

    afterTest {
        Dispatchers.resetMain()
    }

    "Navegar al perfil emite el evento correcto" {

        runTest {

            val viewModel = MainViewModel()

            val evento = async(
                start = CoroutineStart.UNDISPATCHED
            ) {
                viewModel.navigationEvents.first()
            }

            viewModel.navigateTo(Screen.Profile)

            evento.await() shouldBe
                    NavigationEvent.NavigateTo(
                        route = Screen.Profile
                    )
        }
    }

    "Volver atras emite el evento correcto" {

        runTest {

            val viewModel = MainViewModel()

            val evento = async(
                start = CoroutineStart.UNDISPATCHED
            ) {
                viewModel.navigationEvents.first()
            }

            viewModel.navigateBack()

            evento.await() shouldBe
                    NavigationEvent.PopBackStack
        }
    }

    "Navegar hacia arriba emite el evento correcto" {

        runTest {

            val viewModel = MainViewModel()

            val evento = async(
                start = CoroutineStart.UNDISPATCHED
            ) {
                viewModel.navigationEvents.first()
            }

            viewModel.navigateUp()

            evento.await() shouldBe
                    NavigationEvent.NavigateUp
        }
    }
})
