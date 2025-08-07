package com.ronaldsantos.catholicliturgy.app.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.BlockQuoteGutter
import com.halilibo.richtext.ui.ListStyle
import com.halilibo.richtext.ui.RichTextStyle
import com.halilibo.richtext.ui.TableStyle
import com.halilibo.richtext.ui.material3.RichText
import com.halilibo.richtext.ui.string.RichTextStringStyle
import com.ronaldsantos.catholicliturgy.app.theme.Baloo2Fonts
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyColors
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyTypography
import timber.log.Timber

@Composable
fun MarkdownContent(text: String, fontSize: TextUnit = 18.sp) {
    Timber.tag("MarkdownContent").d("Rendering Markdown content with font size: $fontSize")
    val defaultHeadingStyle = CatholicLiturgyTypography.bodyLarge
    val headingStyleMap = mapOf(
        1 to CatholicLiturgyTypography.headlineLarge,
        2 to CatholicLiturgyTypography.headlineMedium,
        3 to CatholicLiturgyTypography.titleLarge,
        4 to CatholicLiturgyTypography.titleMedium,
    )
    val blockQuoteColor = CatholicLiturgyColors.primary.copy(alpha = 0.4f)

    androidx.compose.runtime.key(fontSize) {
        RichText(
            style = RichTextStyle.Default.copy(
                paragraphSpacing = 12.sp,

                headingStyle = { level, baseStyle ->
                    val factor = when (level) {
                        1 -> 1.8f
                        2 -> 1.6f
                        3 -> 1.4f
                        4 -> 1.2f
                        else -> 1f
                    }
                    val style = headingStyleMap[level] ?: defaultHeadingStyle
                    baseStyle.merge(style.copy(fontSize = fontSize * factor))
                },

                listStyle = ListStyle.Default.copy(
                    contentsIndent = 24.sp,
                    markerIndent = 8.sp
                ),

                blockQuoteGutter = BlockQuoteGutter.BarGutter(
                    color = { blockQuoteColor },
                    barWidth = 4.sp

                ),

                tableStyle = TableStyle.Default.copy(
                    borderColor = CatholicLiturgyColors.outlineVariant,
                    borderStrokeWidth = 1f,
                    cellPadding = 6.sp
                ),

                stringStyle = RichTextStringStyle(
                    linkStyle = SpanStyle(
                        color = CatholicLiturgyColors.primary,
                        fontFamily = Baloo2Fonts,
                        fontSize = fontSize
                    ),
                    codeStyle = SpanStyle(
                        background = CatholicLiturgyColors.surfaceVariant,
                        fontSize = fontSize
                    ),
                )
            )
        ) {
            Markdown(content = text)
        }
    }
}
