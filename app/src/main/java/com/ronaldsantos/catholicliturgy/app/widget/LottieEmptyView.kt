package com.ronaldsantos.catholicliturgy.app.widget

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ronaldsantos.catholicliturgy.R
import com.ronaldsantos.catholicliturgy.app.component.LottieView
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyTheme
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyTypography

@Composable
fun LottieEmptyView(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieView(
            file = "empty.json",
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Text(
            text = stringResource(id = R.string.text_no_data_found),
            style = CatholicLiturgyTypography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
        )
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "Light Mode"
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = true,
    showBackground = true
)
@Composable
fun LottieEmptyViewPreview() {
    CatholicLiturgyTheme {
        LottieEmptyView()
    }
}
