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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme

@Composable
fun Input(onStart: (Int) -> Unit) {

    var input by remember { mutableStateOf(listOf<Int>()) }
    val (h, m, s) = remember(input) {
        mutableListOf<Int>().run {
            repeat(6 - input.size) {
                add(0)
            }
            addAll(input)
            Triple("${get(0)}${get(1)}", "${get(2)}${get(3)}", "${get(4)}${get(5)}")
        }
    }
    val timeReady: Boolean = remember(input) { input.isNotEmpty() }
    val onNumClick = remember {
        { num: Int ->
            if (!(input.isEmpty() && num == 0) && input.size < 6) {
                input = input.toMutableList() + num
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf(h to "h", m to "m", s to "s").forEach {
                    TimeItem(it.first, it.second, timeReady)
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        listOf((1..3), (4..6), (7..9)).forEach {
            Row(
                modifier = Modifier
                    .height(108.dp)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                it.forEach {
                    NumItem(it) { num -> onNumClick(num) }
                }
            }
        }
        Row(
            modifier = Modifier
                .height(108.dp)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (timeReady) {
                FloatingActionButton(
                    modifier = Modifier
                        .wrapContentSize()
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    onClick = {
                        if (timeReady) {
                            input = input
                                .toMutableList()
                                .apply { removeLast() }
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Start")
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
            NumItem(0) { onNumClick(0) }
            if (timeReady) {
                FloatingActionButton(
                    modifier = Modifier
                        .wrapContentSize()
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    onClick = {
                        val seconds = h.toInt() * 3600 + m.toInt() * 60 + s.toInt()
                        onStart(seconds)
                    }
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Start")
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputPreview() {
    MyTheme(darkTheme = false) {
        Input {}
    }
}
