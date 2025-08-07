package com.ronaldsantos.catholicliturgy.app.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AutoResizeText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default.copy(fontSize = 16.sp, fontWeight = FontWeight.Medium),
    maxLines: Int = 1,
    minFontSize: TextUnit = 10.sp,
    maxFontSize: TextUnit = style.fontSize,
) {
    val measurer = rememberTextMeasurer()
    var calculatedFontSize by remember { mutableStateOf(maxFontSize) }

    Layout(
        content = {
            Text(
                text = text,
                style = style.copy(fontSize = calculatedFontSize),
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier
    ) { measurables, constraints ->

        var fontSize = maxFontSize

        // Laço para encontrar o maior fontSize que cabe dentro das constraints
        while (fontSize > minFontSize) {
            val result = measurer.measure(
                text = AnnotatedString(text),
                style = style.copy(fontSize = fontSize),
                constraints = constraints
            )

            if (result.size.width > constraints.maxWidth || result.size.height > constraints.maxHeight) {
                fontSize = (fontSize.value - 1).sp
            } else {
                break
            }
        }

        calculatedFontSize = fontSize

        val placeable = measurables.first().measure(constraints)
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }
}
