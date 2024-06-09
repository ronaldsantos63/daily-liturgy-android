package com.ronaldsantos.catholicliturgy.app.component

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.ronaldsantos.catholicliturgy.R
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyColors
import com.ronaldsantos.catholicliturgy.library.framework.extension.asDateString
import java.util.Calendar

private const val daysAHead = 6

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogWrapper(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val dateFormatter = remember {
        DatePickerDefaults.dateFormatter(
            yearSelectionSkeleton = "yMMMM",
            selectedDateSkeleton = "dd/MM/yyyy",
            selectedDateDescriptionSkeleton = "dd/MM/yyyy"
        )
    }

    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val future = Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_MONTH, daysAHead)
                }
                return utcTimeMillis <= future.timeInMillis
            }

            override fun isSelectableYear(year: Int): Boolean {
                return year <= Calendar.getInstance().get(Calendar.YEAR)
            }
        },
    )

    val selectedDates: String = datePickerState.selectedDateMillis?.asDateString.orEmpty()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onDateSelected.invoke(selectedDates)
                onDismiss.invoke()
            }) {
                Text(text = stringResource(id = R.string.text_ok))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.text_cancel))
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            dateFormatter = dateFormatter,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = CatholicLiturgyColors.onSurfaceVariant,
                selectedDayContentColor = CatholicLiturgyColors.primary,
                disabledDayContentColor = CatholicLiturgyColors.onPrimary.copy(
                    alpha = 0.38f
                )
            ),
        )
    }
}

