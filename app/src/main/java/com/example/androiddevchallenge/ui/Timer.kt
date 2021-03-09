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

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Timer(seconds: Int, onCancel: () -> Unit) {
    var trigger by remember { mutableStateOf(seconds) }
    val elapsed by animateIntAsState(
        targetValue = trigger * 1000,
        animationSpec = tween(seconds * 1000, easing = LinearEasing)
    )
    val (h, m, s) = remember(elapsed / 1000) {
        val elapsedInSec = elapsed / 1000
        val h = elapsedInSec / 3600
        val m = elapsedInSec / 60 - h * 60
        val s = elapsedInSec % 60
        Triple(h, m, s)
    }
    DisposableEffect(Unit) {
        trigger = 0
        onDispose { }
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
                listOf(
                    String.format("%02d", h) to "h",
                    String.format("%02d", m) to "m",
                    String.format("%02d", s) to "s"
                ).forEach {
                    TimeItem(it.first, it.second, true)
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        CircleLoop(elapsed)
        Spacer(modifier = Modifier.height(32.dp))
        FloatingActionButton(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally),
            onClick = { onCancel() }
        ) {
            Icon(imageVector = Icons.Default.Stop, contentDescription = "Stop")
        }
    }
}
