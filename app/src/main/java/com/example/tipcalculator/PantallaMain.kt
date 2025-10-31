package com.example.tipcalculator

import android.icu.text.NumberFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.round


@Composable
fun PantallaMain(modifier: Modifier) {
    var estadoTextFieldBill by remember {
        mutableStateOf("")
    }
    var estadoTextFieldTipPercent by remember {
        mutableStateOf("")
    }
    val amount = estadoTextFieldBill.toDoubleOrNull() ?: 0.0

    val tipPercent = estadoTextFieldTipPercent.toDoubleOrNull() ?: 0.0

    val tip = calculateTip(amount, tipPercent)
    val roundedTip = calculateRoundedTip(amount, tipPercent)
//    var tip = tipPercent / 100 * amount
//    val roundedTip = round(tip)

    var checkedSwitch by remember {
        mutableStateOf(false)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Text(
                text = stringResource(R.string.calculate_tip),
                fontSize = 13.sp
            )
            Spacer(
                modifier = Modifier
                    .size(8.dp)
            )
            EditNumberField(
                value = estadoTextFieldBill,
                onValueChange = { estadoTextFieldBill = it },
                modifier = Modifier
            )
            Spacer(
                modifier = Modifier
                .size(20.dp)
            )
            TextField(
                value = estadoTextFieldTipPercent,
                onValueChange = {
                    estadoTextFieldTipPercent = it
                },
                label = {
                    Text(
                        text = stringResource(R.string.tip_percent)
                    )
                }
            )
            Spacer(
                modifier = Modifier
                    .size(25.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Round up tip?",
                fontSize = 13.sp
            )
            Switch(
                checked = checkedSwitch,
                onCheckedChange = {
                    checkedSwitch = it
                },
                modifier = Modifier
                    .scale(0.8f)
            )
        }
        Spacer(
            modifier = Modifier
                .size(30.dp)
        )
        if (checkedSwitch) {
            Text(
                text = stringResource(R.string.tip_amount, roundedTip),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                text = stringResource(R.string.tip_amount, tip),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = {
            Text(
                stringResource(R.string.bill_amount)
            )
        },
        modifier = modifier
    )
}

private fun calculateTip(amount: Double, tipPercent: Double): String {
    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)
}

private fun calculateRoundedTip(amount: Double, tipPercent: Double): String {
    val tip = tipPercent / 100 * amount
    val roundedTip = round(tip)
    return NumberFormat.getCurrencyInstance().format(roundedTip)
}