/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RowScope.TimeItem(
    num: String,
    unit: String,
    highlight: Boolean = true
) {
    val textColor = if (highlight) {
        MaterialTheme.colors.primary
    } else {
        Color.LightGray
    }
    Text(
        text = num,
        modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        textAlign = TextAlign.Center,
        color = textColor,
        fontSize = 48.sp
    )
    if (unit != "s") {
        Text(
            text = ":",
            modifier = Modifier.width(16.dp).align(Alignment.CenterVertically),
            color = textColor,
            fontSize = 24.sp
        )
    }
}

@Preview
@Composable
fun TimeItemPreview() {
    Row {
        TimeItem(num = "00", unit = "h")
        TimeItem(num = "00", unit = "m")
        TimeItem(num = "00", unit = "s")
    }
}
