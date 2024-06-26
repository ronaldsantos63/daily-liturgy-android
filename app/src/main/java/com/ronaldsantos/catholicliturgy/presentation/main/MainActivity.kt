package com.ronaldsantos.catholicliturgy.presentation.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.ronaldsantos.catholicliturgy.R
import com.ronaldsantos.catholicliturgy.app.component.SegmentText
import com.ronaldsantos.catholicliturgy.app.component.SegmentedControl
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyColors
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyTheme
import com.ronaldsantos.catholicliturgy.library.framework.extension.toast
import com.ronaldsantos.catholicliturgy.provider.language.LanguageProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @Inject
    lateinit var languageProvider: LanguageProvider

    private var backPressed = 0L

    private val finish: () -> Unit = {
        if (backPressed + 3000 > System.currentTimeMillis()) {
            finishAndRemoveTask()
        } else {
            toast(getString(R.string.app_exit_label))
        }
        backPressed = System.currentTimeMillis()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            languageProvider.setLocale(languageProvider.getLanguageCode(), LocalContext.current)
            MainRoot(finish = finish)
        }
    }
}

@Composable
fun SegmentedDemo() {
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("SEGMENTS", style = MaterialTheme.typography.bodySmall)

        val fourSegments = remember { listOf("1ª Leitura", "Salmo", "2ª Leitura", "Evangelho") }
        var selectedTwoSegment by remember { mutableStateOf(fourSegments.first()) }
        SegmentedControl(
            fourSegments,
            selectedTwoSegment,
            onSegmentSelected = { selectedTwoSegment = it },
        ) {
            SegmentText(it)
        }

        val threeSegments = remember { listOf("1ª Leitura", "Salmo", "Evangelho") }
        var selectedThreeSegment by remember { mutableStateOf(threeSegments.first()) }
        SegmentedControl(
            threeSegments,
            selectedThreeSegment,
            onSegmentSelected = { selectedThreeSegment = it }
        ) {
            SegmentText(it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CatholicLiturgyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CatholicLiturgyColors.background
        ) {
            SegmentedDemo()
        }
    }
}