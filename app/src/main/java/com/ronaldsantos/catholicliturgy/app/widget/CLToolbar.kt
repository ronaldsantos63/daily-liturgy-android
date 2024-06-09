package com.ronaldsantos.catholicliturgy.app.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyColors
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyTypography
import com.ronaldsantos.catholicliturgy.app.theme.navigationBackIconColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CLToolbar(
    @StringRes titleResId: Int,
    elevation: Dp = 4.dp,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Surface(shadowElevation = elevation) {
        TopAppBar(
            title = {
                Text(
                    stringResource(titleResId),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = CatholicLiturgyTypography.displayMedium
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = CatholicLiturgyColors.primary
            ),
            actions = actions,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CLToolbarWithNavIcon(
    @StringRes titleResId: Int,
    pressOnBack: () -> Unit,
    actions: @Composable RowScope.() -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                stringResource(titleResId),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
                style = CatholicLiturgyTypography.displayMedium
            )
        },
        navigationIcon = {
            Icon(
                rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowBack),
                contentDescription = null,
                tint = CatholicLiturgyColors.navigationBackIconColor,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { pressOnBack.invoke() }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CatholicLiturgyColors.primary
        ),
        actions = actions,
        modifier = Modifier.fillMaxWidth()
    )
}
