package com.ronaldsantos.catholicliturgy.app.component

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlin.math.max

@Composable
fun AutoResizeSingleLineText(
    text: String,
    modifier: Modifier = Modifier,
    maxFontSize: TextUnit = 16.sp,
    minFontSize: TextUnit = 10.sp,
    style: TextStyle = MaterialTheme.typography.titleMedium,
) {
    var currentFontSize by remember { mutableStateOf(maxFontSize) }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    BoxWithConstraints(modifier = modifier) {
        val boxWidth = this.maxWidth

        LaunchedEffect(textLayoutResult) {
            textLayoutResult?.let {
                val didOverflow = it.lineCount > 1
                if (didOverflow && currentFontSize > minFontSize) {
                    currentFontSize = max((currentFontSize.value - 1), minFontSize.value).sp
                }
            }
        }

        Text(
            text = text,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Clip, // evita ...
            onTextLayout = { textLayoutResult = it },
            style = style.copy(fontSize = currentFontSize),
        )
    }
}
